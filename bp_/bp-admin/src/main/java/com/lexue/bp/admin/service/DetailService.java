package com.lexue.bp.admin.service;

import java.util.List;

import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.request.ProduceUpdateRequest;
import com.lexue.bp.common.domain.request.ScoreChangeRequest;
import com.lexue.bp.common.entity.DataChangeScoreEntity;

public interface DetailService {
	
	
	/**
	 * 后台变更积分
	 * @param produceUpdateRequest
	 */
	void updateScore(ProduceUpdateRequest produceUpdateRequest);

	/**
	 * 获取乐豆用户详情
	 * @param uid
	 * @return
	 */
	List<DataChangeScoreEntity> getScoreDetail(long uid,int moduleId);
	

	/**
	 * 获取用户乐豆修改记录
	 * @param scoreChangeRequest
	 * @return
	 */
	PageReply<DataChangeScoreEntity> findChangeScoreDetail(ScoreChangeRequest scoreChangeRequest);

}
