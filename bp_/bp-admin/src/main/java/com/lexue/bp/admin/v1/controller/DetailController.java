package com.lexue.bp.admin.v1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexue.bp.admin.inf.domain.res.DataChangeScoreResponse;
import com.lexue.bp.admin.inf.domain.res.ProduceResponse;
import com.lexue.bp.admin.inf.domain.res.UserManagementResponse;
import com.lexue.bp.admin.inf.spec.DetailServiceSpec;
import com.lexue.bp.admin.service.ContentService;
import com.lexue.bp.admin.service.DetailService;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.domain.request.ProduceQueryRequest;
import com.lexue.bp.common.domain.request.ProduceUpdateRequest;
import com.lexue.bp.common.domain.request.ScoreChangeRequest;
import com.lexue.bp.common.domain.request.UserManagementQueryRequest;
import com.lexue.bp.common.entity.DataChangeScoreEntity;
import com.lexue.bp.common.util.ResponseUtil;

@RestController("detailControllerV1")
public class DetailController implements DetailServiceSpec {
	
	@Autowired
	private DetailService detailService;
	@Autowired
	private ContentService contentService;
	
	@Override
	public ResponseDto<PageReply<ProduceResponse>> getProduceDetail(@RequestBody ProduceQueryRequest pqr) {
		PageReply<ProduceResponse> result = contentService.findProduceDetail(pqr);
		return ResponseUtil.generateSuccessResponse(result);
	}
	
	@Override
	public ResponseDto<PageReply<DataChangeScoreResponse>> getChangeScoreDetail(@RequestBody ScoreChangeRequest scoreChangeRequest) {
		PageReply<DataChangeScoreResponse> result = new PageReply<>();
		PageReply<DataChangeScoreEntity> findChangeScoreDetail = detailService.findChangeScoreDetail(scoreChangeRequest);
		List<DataChangeScoreResponse> dataList = new ArrayList<>(); 
		findChangeScoreDetail.getDatas().forEach(dataChangeScoreEntity -> {
		  DataChangeScoreResponse dsr = new DataChangeScoreResponse();
		  BeanUtils.copyProperties(dataChangeScoreEntity, dsr);
		  dataList.add(dsr);
		});
		result.setDatas(dataList);
		result.setNumber(findChangeScoreDetail.getNumber());
		result.setSize(findChangeScoreDetail.getSize());
		result.setTotalElements(findChangeScoreDetail.getTotalElements());
		result.setTotalPages(findChangeScoreDetail.getTotalPages());
		return ResponseUtil.generateSuccessResponse(result);
	}

	@Override
	public ResponseDto<PageReply<UserManagementResponse>> getUserManagement(@RequestBody UserManagementQueryRequest umqr) {
		PageReply<UserManagementResponse> result = contentService.findUserManagementDetail(umqr);
		return ResponseUtil.generateSuccessResponse(result);
	}

	@Override
	public ResponseDto<Long> getUserTotalRemain(@RequestParam("uid") long uid,@RequestParam(value = "moduleId",defaultValue = "8193")int moduleId) {
		return ResponseUtil.generateSuccessResponse(contentService.getUserTotalRemain(uid,moduleId));
	}

	@Override
	public ResponseDto<Void> updateScore(@RequestBody ProduceUpdateRequest produceUpdateRequest) {
		detailService.updateScore(produceUpdateRequest);
		return ResponseUtil.generateSuccessResponse();
	}

	@Override
	public ResponseDto<List<DataChangeScoreResponse>> getScoreDetail(@RequestParam("uid") long uid,@RequestParam(value = "moduleId",defaultValue = "8193")int moduleId) {
		List<DataChangeScoreEntity> dcsEntity = detailService.getScoreDetail(uid,moduleId);
		List<DataChangeScoreResponse> dcsrList = new ArrayList<>();
		for (DataChangeScoreEntity dataChangeScoreEntity : dcsEntity) {
			
			DataChangeScoreResponse dcsResponse = new DataChangeScoreResponse();
			dcsResponse.setAddReason(dataChangeScoreEntity.getAddReason());
			dcsResponse.setChangeChannel(dataChangeScoreEntity.getChangeChannel());
			dcsResponse.setOperatorId(dataChangeScoreEntity.getOperatorId());
			dcsResponse.setOperatorName(dataChangeScoreEntity.getOperatorName());
			dcsResponse.setScore(dataChangeScoreEntity.getScore());
			dcsResponse.setSystemChannel(dataChangeScoreEntity.getSystemChannel());
			dcsResponse.setUid(dataChangeScoreEntity.getUid());
			dcsResponse.setUpdateTime(dataChangeScoreEntity.getUpdateTime());
			dcsrList.add(dcsResponse);
		}
		return ResponseUtil.generateSuccessResponse(dcsrList);
	}

}
