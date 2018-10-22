/**
 * 
 */
package com.lexue.bp.admin.stream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.admin.service.RuleService;
import com.lexue.bp.admin.stream.converter.ConvertService;
import com.lexue.bp.admin.stream.domain.DataOrderRequest;
import com.lexue.bp.admin.stream.domain.DataPostHotRequest;
import com.lexue.bp.admin.stream.domain.DataPostRequest;
import com.lexue.bp.admin.stream.domain.DataRefundRequest;
import com.lexue.bp.admin.stream.domain.DataWatchRequest;
import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataWatchEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.enums.DataStatusEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.RuleStatusEnums;
import com.lexue.bp.common.repository.DataOrderRepository;
import com.lexue.bp.common.repository.DataPostHotRepository;
import com.lexue.bp.common.repository.DataPostRepository;
import com.lexue.bp.common.repository.DataRefundRepository;
import com.lexue.bp.common.repository.DataWatchRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * @author bc
 *
 */
@Service
@Slf4j
public class MessageHandlerImpl implements MessageHandlerService {

	@Autowired
	private DataOrderRepository dataOrderRepository;
	@Autowired
	private DataPostRepository dataPostRepository;
	@Autowired
	private DataPostHotRepository dataPostHotRepository;
	@Autowired
	private DataRefundRepository dataRefundRepository;
	@Autowired
	private DataWatchRepository dataWatchRepository;
	@Autowired
	private ConvertService convertService;
	@Autowired
	private RuleService ruleService;
	
	
	/**
	 * 若规则为开启，则返回true
	 * @param ruleId
	 * @param moduleId
	 * @return
	 */
	private boolean isRuleOpen(int ruleId,int moduleId) {
		RuleEntity ruleEntity = ruleService.getByRuleIdAndModuleId(ruleId, moduleId);
		if (ruleEntity == null || ruleEntity.getStatus() == RuleStatusEnums.ENABLE.getCode()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	@Transactional
	public void handleOrderMessage(DataOrderRequest dor) {
		
		if (!isRuleOpen(ProduceChannelEnums.ORDER.getCode(), dor.getModuleId())) {
			log.info("规则关闭，忽略订单：[{}]",dor.getOrderId());
			return;
		}
		
		DataOrderEntity dataOrderEntity = dataOrderRepository.findByOrderId(dor.getOrderId());
		if (dataOrderEntity == null) {
			DataOrderEntity doe = convertService.convertDataOrder(dor);
			dataOrderRepository.save(doe);
		} else if (dataOrderEntity.getStatus() == DataStatusEnums.PENDING.getCode()) {
			dataOrderEntity.setAmount(dor.getAmount());
			dataOrderEntity.setPayTime(dor.getPayTime());
			dataOrderRepository.save(dataOrderEntity);
		} else {
			log.info("忽略订单：[{}]",dor.getOrderId());
		}
		
	}


	/**
	 * 支持多次退款
	 */
	@Override
	@Transactional
	public void handleRefundMessage(DataRefundRequest drr) {
		if (!isRuleOpen(ProduceChannelEnums.ORDER.getCode(), drr.getModuleId())) {
			log.info("规则关闭，忽略退款订单号：[{}]",drr.getRefundId());
			return;
		}
		
		DataOrderEntity dataOrderEntity = dataOrderRepository.findByOrderId(drr.getOrderId());
		if (dataOrderEntity == null) {
			log.info("不存在原订单：[{}]，忽略退款订单：[{}]",drr.getOrderId(),drr.getRefundId());
		} else {
			drr.setModuleId(dataOrderEntity.getModuleId());
			drr.setUid(dataOrderEntity.getUid());
			DataRefundEntity dre = convertService.convertDataRefund(drr);
			dataRefundRepository.save(dre);
		}
	}


	@Override
	@Transactional
	public void handlePostMessage(DataPostRequest dpr) {
		
		if (!isRuleOpen(ProduceChannelEnums.POST.getCode(), Integer.parseInt(dpr.getModuleId()))) {
			log.info("规则关闭，忽略帖子：[{}]",dpr.getPostId());
			return;
		}
		
		DataPostEntity dataPostEntity = dataPostRepository.findByPostId(dpr.getPostId());
		if (dataPostEntity == null) {
			DataPostEntity dpe = convertService.convertDataPost(dpr);
			dataPostRepository.save(dpe);
		} else  {
			//即使状态是已处理的，也应更新删除状态，热帖会使用
			dataPostEntity.setIsDelete((byte)dpr.getDeleteFlag());
			dataPostRepository.save(dataPostEntity);
		} 
		
	}


	@Override
	@Transactional
	public void handlePostHotMessage(DataPostHotRequest dphr) {
		if (!isRuleOpen(ProduceChannelEnums.POST.getCode(), dphr.getModuleId())) {
			log.info("规则关闭，忽略热帖：[{}]",dphr.getPostId());
			return;
		}
		DataPostHotEntity dataPostHotEntity = dataPostHotRepository.findByPostId(dphr.getPostId());
		if (dataPostHotEntity==null) {
			DataPostHotEntity dphe = convertService.convertDataPostHot(dphr);
			dataPostHotRepository.save(dphe);
		} else  {
			log.info("忽略热帖：[{}]",dphr.getPostId());
		} 
	}

	//改为单独处理
	/*@Override
	@Transactional
	public void handleShareMessage(DataShareRequest dpr) {
		DataShareEntity dataShareEntity = dataShareRepository.findByBusinessId(dpr.getBusinessId());
		if (dataShareEntity==null) {
			DataShareEntity dse = convertService.convertDataShare(dpr);
			dataShareRepository.save(dse);
		} else  {
			log.info("忽略分享：[{}]",dpr.getBusinessId());
		} 
		
		
	}*/


	@Override
	@Transactional
	public void handleWatchMessage(DataWatchRequest dwr) {
		if (!isRuleOpen(ProduceChannelEnums.WATCH.getCode(), dwr.getModuleId())) {
			log.info("规则关闭，忽略观看：[{}]",dwr.getBusinessId());
			return;
		}
		DataWatchEntity dataWatchEntity = dataWatchRepository.findByBusinessId(dwr.getBusinessId());
		if (dataWatchEntity==null) {
			DataWatchEntity dwe = convertService.convertDataWatch(dwr);
			dataWatchRepository.save(dwe);
		} else {
			log.info("忽略观看：[{}]",dwr.getBusinessId());
		}
	
	}

	

}
