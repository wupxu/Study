
package com.lexue.bp.admin.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.lexue.bp.admin.service.DataService;
import com.lexue.bp.admin.service.TaskBusinessService;
import com.lexue.bp.common.domain.request.ConsumeRequest;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataShareEntity;
import com.lexue.bp.common.entity.DataWatchEntity;
import com.lexue.bp.common.entity.ProduceEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.enums.ConsumeChannelEnums;
import com.lexue.bp.common.enums.DataPostStatusEnums;
import com.lexue.bp.common.enums.DataStatusEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.ProduceStatusEnums;
import com.lexue.bp.common.properties.BPProperties;
import com.lexue.bp.common.service.CoreService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TaskBusinessImpl implements TaskBusinessService {
	@Autowired
	private DataService dataService;
	@Autowired
	private CoreService coreService;
	@Autowired
	@Qualifier("bp-config")
	private BPProperties bpProperties;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void Order(long sDate, long afterDateLong, DataOrderEntity order, RuleEntity ruleEntity) {
		// 判断在3天内是否有退款，在部分退时，减去部分退的金额后，再按规则执行
		List<DataRefundEntity> refunds = dataService.getRefundEntity(sDate, afterDateLong, order.getOrderId());
		log.info("订单任务获取退款数据refunds=====================：" + refunds);
		if (CollectionUtils.isEmpty(refunds)) {
			// 没有退款
			orderScoreGenerate(order, ruleEntity, order.getAmount());
		} else {
			BigDecimal totalRefund = BigDecimal.ZERO;
			for (DataRefundEntity refund : refunds) {
				// 计算总的退款额
				totalRefund = totalRefund.add(refund.getAmount());
				// 变更退款数据的状态
				dataService.updateDataRefundStatus(refund.getId(), DataStatusEnums.HANDLED.getCode(), "三日内退款");
			}
			// 生成积分
			BigDecimal actualAmount = order.getAmount().subtract(totalRefund);
			orderScoreGenerate(order, ruleEntity, actualAmount);
		}
	}

	/**
	 * 1.判断订单总金额 2.判断是否待领订单数是否超过限制 3.判断是否超过当日已发积分数的限制
	 * 
	 * @param order
	 * @param ruleEntity
	 * @param actualAmount
	 */
	@Transactional
	private void orderScoreGenerate(DataOrderEntity order, RuleEntity ruleEntity, BigDecimal actualAmount) {
		// 例：单件实际支付金额大于等于50 时，可获得乐豆回馈。回馈值为实际支付金额数值的10%。如支付500 元，回馈50
		// 个乐豆。实际支付金额取10的整数倍计算。
		if (actualAmount.intValue() >= ruleEntity.getConditionValue()) {

			// 判断是否待领订单数是否超过限制
			int unclaimedCount = coreService.getProduceUnclaimedCount(order.getUid(), order.getModuleId());// 待领订单数
			log.info("订单任务判断是否待领订单数是否超过限制=====================：" + unclaimedCount);
			// 判断是否超过当日已发积分数的限制
			int scoreCurDay = coreService.getScoreCurDay(order.getUid(), order.getModuleId(),
					ProduceChannelEnums.ORDER.getCode());// 今日已发总积分
			log.info("订单任务判断是否超过当日已发积分数的限制=====================：" + unclaimedCount);
			if (unclaimedCount >= ruleEntity.getMaxCount() || scoreCurDay >= ruleEntity.getMaxScore()) {
				dataService.updateDataOrderStatus(order.getId(), DataStatusEnums.IGNORE.getCode(), "超出当日或最大订单数限制");
			} else {
				// 发放待领积分
				int score = actualAmount.divide(new BigDecimal(String.valueOf(ruleEntity.getResultValue())), 2, BigDecimal.ROUND_HALF_UP).intValue();// 待发积分
			    int scoreActual = computeScore(score, scoreCurDay, ruleEntity.getMaxScore());
				ProduceRequest pr = new ProduceRequest();
				pr.setModuleId(order.getModuleId());
				pr.setProduceBizid(order.getOrderId());
				pr.setProduceChannelEnums(ProduceChannelEnums.ORDER);
				pr.setProduceScore(scoreActual);
				pr.setUid(order.getUid());
				coreService.addUnclaimedScore(pr);
				// 修改数据处理的状态
				dataService.updateDataOrderStatus(order.getId(), DataStatusEnums.HANDLED.getCode(), scoreActual==score?"":"发放至最大积分数");
			}

		} else {
			// 不满足金额条件
			dataService.updateDataOrderStatus(order.getId(), DataStatusEnums.IGNORE.getCode(), "不满足金额条件");
		}
	}
	
	private int computeScore(int scorePrepare,int scoreCurDay,int maxScore) {
		int scoreActual = 0;
		int scoreTotalToday = scorePrepare + scoreCurDay;//本次预发积分与今日已发积分的合计
		if (scoreTotalToday >= maxScore) {
			scoreActual = maxScore-scoreCurDay;
		} else {
			scoreActual = scorePrepare;
		}
		return scoreActual;
	}
	
	private int computeWatchScore(int scorePrepare,int scoreCurDay,int maxScore, int ruleMaxScore) {
		int scoreActual = 0;
		int scoreTotalToday = scorePrepare + scoreCurDay;//本次预发积分与今日已发积分的合计
		if (scoreTotalToday >= maxScore) {
			scoreActual = maxScore-scoreCurDay;
			if (scoreActual<=0) {
				scoreActual=ruleMaxScore-scoreCurDay;
			}
		} else {
			scoreActual = scorePrepare;
		}
		return scoreActual;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void Refund(DataRefundEntity dataRefundEntity,int days) {
		// 获取对应的订单数据
		DataOrderEntity dataOrderEntity = dataService.getOrder(dataRefundEntity.getOrderId());
		if (dataOrderEntity == null) {
			dataService.updateDataOrderStatus(dataRefundEntity.getId(), DataStatusEnums.IGNORE.getCode(), "对应的订单不存在");
		} else  {
			Date payTimeAfter = DateUtils.addDays(new Date(dataOrderEntity.getPayTime()), days);
			Date refundTime   = new Date(dataRefundEntity.getRefundTime());
			int compare = DateUtils.truncatedCompareTo(payTimeAfter, refundTime, Calendar.DAY_OF_MONTH);
		    if (compare<=0) {
		    	// 修改退款数据状态
				dataService.updateDataOrderStatus(dataRefundEntity.getId(), DataStatusEnums.IGNORE.getCode(), "三日后退款");
		    }
		}
			
	}

	/**
	 *因超过3日后退款，不扣积分，因此此方法不用了。
	 */
	@Deprecated
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void Refund(DataRefundEntity dataRefundEntity, RuleEntity ruleEntity) {
		// 获取对应的订单数据
		DataOrderEntity dataOrderEntity = dataService.getOrder(dataRefundEntity.getOrderId());
		log.info("退款任务获取对应的订单数据====================："+dataOrderEntity);
		if (dataOrderEntity == null) {
			dataService.updateDataOrderStatus(dataRefundEntity.getId(), DataStatusEnums.IGNORE.getCode(), "对应的订单不存在");
		} else if (dataOrderEntity.getStatus() == DataStatusEnums.HANDLED.getCode()) {
			// 是否领取
			ProduceEntity pe = coreService.findProduceEntityByOrderId(dataOrderEntity.getOrderId());
			log.info("退款任务是否领取====================："+pe);
			if (pe == null) {
				dataService.updateDataOrderStatus(dataRefundEntity.getId(), DataStatusEnums.IGNORE.getCode(),
						"对应的积分累积数据不存在");
			} else {
				int subtractScore = dataRefundEntity.getAmount().divide(new BigDecimal(String.valueOf(ruleEntity.getResultValue())), 2, BigDecimal.ROUND_HALF_UP).intValue();//待扣减积分
				log.info("退款任务待扣减积分=========================："+subtractScore);
				if (pe.getStatus() == ProduceStatusEnums.UNCLAIMED.getCode()) {
					// 未领取,修改领取的积分数
					coreService.updateProduceUnclaimedScore(pe, subtractScore);
				} else {
					// 已领取,扣减用户积分
					ConsumeRequest cr = new ConsumeRequest();
					cr.setConsumeBizid(dataRefundEntity.getRefundId());
					cr.setConsumeChannel(ConsumeChannelEnums.REFUND);
					cr.setConsumeScore(subtractScore);
					cr.setModuleId(dataRefundEntity.getModuleId());
					cr.setRemark("订单退款，系统自动扣减");
					cr.setUid(dataRefundEntity.getUid());
					coreService.consume(cr);
				}
				// 修改退款数据状态
				dataService.updateDataOrderStatus(dataRefundEntity.getId(), DataStatusEnums.HANDLED.getCode(), "");
			}

		} else if (dataOrderEntity.getStatus() == DataStatusEnums.PENDING.getCode()) {
			return;
		} else if (dataOrderEntity.getStatus() == DataStatusEnums.IGNORE.getCode()
				|| dataOrderEntity.getStatus() == DataStatusEnums.EXCEPTION.getCode()) {
			dataService.updateDataOrderStatus(dataRefundEntity.getId(), DataStatusEnums.IGNORE.getCode(), "对应的订单未发放积分");
		}

	}

	/**
	 * 1：删除贴直接变为已处理 
	 * 2:判断是否超过发帖数 
	 * 3:判断是否超过帖子封顶数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void post(DataPostEntity post, RuleEntity ruleEntity) {
		byte deleteStatus = DataPostStatusEnums.DELETE.getCode();
		//如果帖子为删除的帖子，直接变更数据状态为“已处理”
		if (post.getIsDelete() == deleteStatus) {
			dataService.updateDataPostStatus(post.getId(), DataStatusEnums.IGNORE.getCode());
			return;
		}
		
		// 判断是否是超过待领帖子数上限
		int unclaimedCount = coreService.getProduceUnclaimedCount(post.getUid(), post.getModuleId(),ProduceChannelEnums.POST.getCode());// 待领帖子数
		log.info("帖子待领数：[{}]",unclaimedCount);
		
		// 判断是否超过当日已发积分数的限制
		int scoreCurDay = coreService.getScoreCurDay(post.getUid(), post.getModuleId(),ProduceChannelEnums.POST.getCode());//今日帖子已发总积分
		log.info("帖子当日发放数：[{}]",scoreCurDay);
		
		if (unclaimedCount >= ruleEntity.getMaxCount() || scoreCurDay >= ruleEntity.getMaxScore()) {
			dataService.updateDataPostStatus(post.getId(), DataStatusEnums.IGNORE.getCode());
		} else {
			// 发放待领积分
			int score = ruleEntity.getResultValue();// 待发积分
			int scoreActual = computeScore(score, scoreCurDay, ruleEntity.getMaxScore());
			ProduceRequest pr = new ProduceRequest();
			pr.setModuleId(post.getModuleId());
			pr.setProduceBizid(post.getPostId());
			pr.setProduceChannelEnums(ProduceChannelEnums.POST);
			pr.setProduceScore(scoreActual);
			pr.setUid(post.getUid());
			coreService.addUnclaimedScore(pr);
			// 修改帖子数据处理的状态
			dataService.updateDataPostStatus(post.getId(), DataStatusEnums.HANDLED.getCode());
		}
	}
	
	/**
	 * 热帖状态处理，积分生成
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void postHot(DataPostHotEntity dataPostHotEntity, RuleEntity ruleEntity) {
		//若对应的帖子是删除状态且发帖时间与评为热帖时间在72小时内，则不加积分
		DataPostEntity dpe = dataService.getPost(dataPostHotEntity.getPostId());
		if (dpe != null && dpe.getIsDelete()==DataPostStatusEnums.DELETE.getCode() ) {
			Date postTimeAfter = DateUtils.addHours(new Date(dpe.getPostTime()), bpProperties.getPostHotHour());
			Date hotPostTime   = new Date(dataPostHotEntity.getPostTime());
			int compare = DateUtils.truncatedCompareTo(postTimeAfter, hotPostTime, Calendar.DAY_OF_MONTH);
		    if (compare<=0) {
				dataService.updateDataPostHotStatus(dataPostHotEntity.getId(), DataStatusEnums.IGNORE.getCode());
		    }
		    return;
		}
		// 发放及时生效积分
		ProduceRequest pr = new ProduceRequest();
		pr.setModuleId(dataPostHotEntity.getModuleId());
		pr.setProduceBizid(dataPostHotEntity.getPostId());
		pr.setProduceChannelEnums(ProduceChannelEnums.POSTHOT);
		pr.setProduceScore(ruleEntity.getHotValue());
		pr.setUid(dataPostHotEntity.getUid());
		coreService.addUnclaimedScore(pr);
		// 修改帖子数据处理的状态
		dataService.updateDataPostHotStatus(dataPostHotEntity.getId(), DataStatusEnums.HANDLED.getCode());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void share(DataShareEntity dataShareEntity, RuleEntity ruleEntity) {
		// 分享一条获得3乐豆，每日9乐豆封顶，及时发放
		// 积分处理，生成
		shareScoreGenerate(dataShareEntity, ruleEntity);
	}

	/**
	 * 1：判断是否是超过分享数上限 2：判断是否超过当日已发积分数的限制
	 * 
	 * @param dataShareEntity
	 * @param ruleEntity
	 * @param shareCount
	 */
	@Transactional
	private void shareScoreGenerate(DataShareEntity dataShareEntity, RuleEntity ruleEntity) {
		// 判断是否是超过分享数上限
		int sharedCount = coreService.getProduceShareUnclaimedCount(dataShareEntity.getUid(),
				dataShareEntity.getModuleId(), ProduceChannelEnums.SHARE.getCode());// 分享条数
		log.info("分享任务判断是否是超过分享数上限===================："+sharedCount);
		// 判断是否超过当日已发积分数的限制
		int scoreCurDay = coreService.getScoreCurDay(dataShareEntity.getUid(), dataShareEntity.getModuleId(),
				ProduceChannelEnums.SHARE.getCode());// 今日已发总积分
		log.info("分享任务判断是否超过当日已发积分数的限制===================："+scoreCurDay);
		if (sharedCount >= ruleEntity.getMaxCount() || scoreCurDay >= ruleEntity.getMaxScore()) {
			dataService.updateDataShareStatus(dataShareEntity.getId(), DataStatusEnums.IGNORE.getCode());
		} else {
			// 发放待领积分
			int score = ruleEntity.getResultValue();// 待发积分
			int scoreActual = computeScore(score, scoreCurDay, ruleEntity.getMaxScore());
			log.info("分享任务待领积分===================："+score);
			log.error("分享任务待领积分===================："+score);
			ProduceRequest pr = new ProduceRequest();
			pr.setModuleId(dataShareEntity.getModuleId());
			pr.setProduceBizid(dataShareEntity.getBusinessId());
			pr.setProduceChannelEnums(ProduceChannelEnums.SHARE);
			pr.setProduceScore(scoreActual);
			pr.setUid(dataShareEntity.getUid());
			coreService.addUnclaimedScore(pr);
			// 修改分享数据处理的状态
			dataService.updateDataShareStatus(dataShareEntity.getId(), DataStatusEnums.HANDLED.getCode());
		}

	}

	/**
	 * 1：判断观看时长是否大于一小时 2：判断是否超出当日观看时长上限 3：判断是否超出当日乐豆封顶数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void watch(DataWatchEntity dataWatchEntity, RuleEntity ruleEntity) {
		// 若观看时长(秒)小于设置的最低条件
		if (dataWatchEntity.getDuration() < ruleEntity.getConditionValue() * 60) {
			dataService.updateDataWatchStatus(dataWatchEntity.getId(), DataStatusEnums.IGNORE.getCode());
			return;
		}
		// 判断是否超出待领观看时长上限
		int watchedCount = coreService.getProduceWatchUnclaimedCount(dataWatchEntity.getUid(),
				dataWatchEntity.getModuleId(), ProduceChannelEnums.WATCH.getCode(),ruleEntity.getResultValue(),ruleEntity.getConditionValue() * 60);
		log.info("观看任务判断是否超出当日观看时长上限====================："+watchedCount);
		// 判断是否超过当日已发积分数的限制
		int scoreCurDay = coreService.getScoreCurDay(dataWatchEntity.getUid(), dataWatchEntity.getModuleId(),
				ProduceChannelEnums.WATCH.getCode());// 今日已发总积分
		log.info("观看任务判断是否超过当日已发积分数的限制====================："+scoreCurDay);
		if (watchedCount >= ruleEntity.getMaxCount() || scoreCurDay >= ruleEntity.getMaxScore()) {
			dataService.updateDataWatchStatus(dataWatchEntity.getId(), DataStatusEnums.IGNORE.getCode());
		} else {
			// 发放待领积分
			int watchTimes = (int)(dataWatchEntity.getDuration() / (ruleEntity.getConditionValue()*60)); //配置的规则不能为0,单位为秒
			int score = (int) (watchTimes * ruleEntity.getResultValue());// 待发积分
			//计算乐豆发放上限次数对应的积分与每日积分数上限，取其较小值
			int maxCountScore = (int)(((ruleEntity.getMaxCount()*60)/ruleEntity.getConditionValue())*ruleEntity.getResultValue());
			int scoreActual = computeWatchScore(score, scoreCurDay,maxCountScore<ruleEntity.getMaxScore()?maxCountScore:ruleEntity.getMaxScore(),ruleEntity.getMaxScore());
			log.info("观看任务待领积分====================："+scoreActual);
			ProduceRequest pr = new ProduceRequest();
			pr.setModuleId(dataWatchEntity.getModuleId());
			pr.setProduceBizid(dataWatchEntity.getBusinessId());
			pr.setProduceChannelEnums(ProduceChannelEnums.WATCH);
			pr.setProduceScore(scoreActual);
			pr.setUid(dataWatchEntity.getUid());
			coreService.addUnclaimedScore(pr);
			// 修改分享数据处理的状态
			dataService.updateDataWatchStatus(dataWatchEntity.getId(), DataStatusEnums.HANDLED.getCode());
		}
	}

	
	@Override
	public void invalid(long startTime) {
		coreService.invalidScore(startTime);
	}
	
}

