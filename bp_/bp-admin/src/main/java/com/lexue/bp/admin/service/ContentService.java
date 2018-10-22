package com.lexue.bp.admin.service;



import com.lexue.bp.admin.inf.domain.req.NoticeRequest;
import com.lexue.bp.admin.inf.domain.req.StrategyRequest;
import com.lexue.bp.admin.inf.domain.res.NoticeResponse;
import com.lexue.bp.admin.inf.domain.res.ProduceResponse;
import com.lexue.bp.admin.inf.domain.res.StrategyResponse;
import com.lexue.bp.admin.inf.domain.res.UserManagementResponse;
import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.request.ProduceQueryRequest;
import com.lexue.bp.common.domain.request.UserManagementQueryRequest;
import com.lexue.bp.common.entity.NoticeEntity;
import com.lexue.bp.common.entity.StrategyEntity;

public interface ContentService {

	NoticeEntity saveNotice(NoticeRequest NoticeRequest);
	
	StrategyEntity saveStrategy(StrategyRequest strategyRequest);

	NoticeEntity getNotice(int moduleId);
	
	StrategyEntity getStrategy(int moduleId);
	
	/**
	 * 获取用户管理详情
	 * @param umqr
	 * @return
	 */
	PageReply<UserManagementResponse> findUserManagementDetail(UserManagementQueryRequest umqr);

	/**
	 * 获取用户剩余积分
	 * @param uid
	 * @return
	 */
	long getUserTotalRemain(long uid,int moduleId);
	
	/**
	 * 查询累积明细
	 * @param produceQueryRequest
	 * @return
	 */
	PageReply<ProduceResponse> findProduceDetail(ProduceQueryRequest produceQueryRequest);
	
	/**查询公告内容*/
	NoticeResponse findNotice(int moduleId);

	/**查询乐豆用途信息*/
	StrategyResponse findStrategy(int moduleId);
	
}
