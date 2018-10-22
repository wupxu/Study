package com.lexue.bp.admin.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lexue.bp.admin.service.DetailService;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.request.ConsumeRequest;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.domain.request.ProduceUpdateRequest;
import com.lexue.bp.common.domain.request.ScoreChangeRequest;
import com.lexue.bp.common.entity.DataChangeScoreEntity;
import com.lexue.bp.common.entity.UserEntity;
import com.lexue.bp.common.enums.ConsumeChannelEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.SystemChannelEnums;
import com.lexue.bp.common.repository.DataChangeScoreRepository;
import com.lexue.bp.common.service.CoreService;
import com.lexue.bp.common.service.UserService;
import com.lexue.bp.common.util.CommonUtil;
import com.lexue.bp.common.util.PageableUtil;
import com.lexue.bp.common.util.UserManagementSpecs;

@Service
public class DetailImpl implements DetailService {
	@Autowired
	private UserService userService;
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private DataChangeScoreRepository dataChangeScoreRepository;

	@Override
	@Transactional
	public void updateScore(ProduceUpdateRequest produceUpdateRequest) {

		// 查询当前乐豆
		UserEntity userEntity = userService.findByUidAndModuleId(produceUpdateRequest.getUid(), produceUpdateRequest.getModuleId());
		// 系统增加
		if (SystemChannelEnums.ADD.getCode() == produceUpdateRequest.getChoose()) {

			// 系统积分变更表添加数据
			DataChangeScoreEntity dataAddEntity = new DataChangeScoreEntity();
			dataAddEntity.setId(CommonUtil.generateId());
			dataAddEntity.setUid(produceUpdateRequest.getUid());
			dataAddEntity.setOperatorId(produceUpdateRequest.getOperatorId());
			dataAddEntity.setOperatorName(produceUpdateRequest.getOperatorName());
			dataAddEntity.setChangeChannel(SystemChannelEnums.HAND.getName());
			dataAddEntity.setSystemChannel(SystemChannelEnums.ADD.getCode());
			dataAddEntity.setScore(produceUpdateRequest.getScore());
			dataAddEntity.setAddReason(produceUpdateRequest.getRemark());
			dataAddEntity.setUpdateTime(Calendar.getInstance().getTimeInMillis());
			dataAddEntity.setModuleId(produceUpdateRequest.getModuleId());
			dataChangeScoreRepository.save(dataAddEntity);
			
			// 增加积分
			ProduceRequest pr = new ProduceRequest();
			pr.setModuleId(produceUpdateRequest.getModuleId());
			pr.setProduceBizid(String.valueOf(dataAddEntity.getId()));
			pr.setProduceChannelEnums(ProduceChannelEnums.CMS);
			pr.setProduceScore(produceUpdateRequest.getScore());
			pr.setRemark(produceUpdateRequest.getRemark());
			pr.setUid(produceUpdateRequest.getUid());
			coreService.addUnusedScore(pr);
		}

		// 系统扣减
		if (SystemChannelEnums.SUB.getCode() == produceUpdateRequest.getChoose()) {
			// 系统积分变更表添加数据
			DataChangeScoreEntity dataSubEntity = new DataChangeScoreEntity();
			dataSubEntity.setId(CommonUtil.generateId());
			dataSubEntity.setUid(produceUpdateRequest.getUid());
			dataSubEntity.setOperatorId(produceUpdateRequest.getOperatorId());
			dataSubEntity.setOperatorName(produceUpdateRequest.getOperatorName());
			dataSubEntity.setChangeChannel(SystemChannelEnums.HAND.getName());
			dataSubEntity.setSystemChannel(SystemChannelEnums.SUB.getCode());
			dataSubEntity.setScore(-produceUpdateRequest.getScore());
			dataSubEntity.setUpdateTime(Calendar.getInstance().getTimeInMillis());
			dataSubEntity.setAddReason(produceUpdateRequest.getRemark());
			dataSubEntity.setModuleId(produceUpdateRequest.getModuleId());
			dataChangeScoreRepository.save(dataSubEntity);

			//扣减积分
			ConsumeRequest cr = new ConsumeRequest();
			cr.setConsumeBizid(String.valueOf(dataSubEntity.getId()));
			cr.setConsumeChannel(ConsumeChannelEnums.CMSSUB);
			cr.setConsumeScore(produceUpdateRequest.getScore());
			cr.setModuleId(produceUpdateRequest.getModuleId());
			cr.setRemark(produceUpdateRequest.getRemark());
			cr.setUid(produceUpdateRequest.getUid());
			coreService.consume(cr);
		}
	}


	@Override
	public List<DataChangeScoreEntity> getScoreDetail(long uid,int moduleId) {
		List<DataChangeScoreEntity> dataChangeScoreList = dataChangeScoreRepository.findByUidAndModuleId(uid,moduleId);
		return dataChangeScoreList;
	}
	
	@Override
	public PageReply<DataChangeScoreEntity> findChangeScoreDetail(ScoreChangeRequest scoreChangeRequest) {
		Pageable pageable = PageableUtil.generatePageable(scoreChangeRequest.getPageAsk());
		Page<DataChangeScoreEntity> pageData = dataChangeScoreRepository
				.findAll(UserManagementSpecs.queryChangeScoreDetail(scoreChangeRequest), pageable);

		PageReply<DataChangeScoreEntity> result = PageableUtil.pageToPageReply(pageData);
		return result;
	}

}
