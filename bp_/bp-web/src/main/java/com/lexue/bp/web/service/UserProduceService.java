package com.lexue.bp.web.service;

import java.util.List;


import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.entity.mongo.UserMongoEntity;
import com.lexue.bp.web.inf.request.DataShareRequest;
import com.lexue.bp.web.inf.response.ScoreDetailResponse;
import com.lexue.bp.web.inf.response.UnclaimedProduceResponse;

/**
 * web接口
 * @author wpx
 *
 */
public interface UserProduceService {
	
	
	/**用户领取积分*/
	UserMongoEntity receiveScoreByChannel(long uid,int moduleId,int produceChannelCode);
	
	/**获取用户信息*/
	UserMongoEntity findUserInfo(long uid,int moduleId);
	
	/**查询各渠道待领取乐豆数*/
	List<UnclaimedProduceResponse> getUnclaimedScore(long uid,int moduleId);
	
	/**查询用户积分明细*/
	PageReply<ScoreDetailResponse> userScoreDetail(long uid,int moduleId,int pageNumber,int pageSize);
	
	/**增加用户积分*/
	void addScore(ProduceRequest produceRequest);

	/**添加分享数据到表*/
	void addDataShareEntity(DataShareRequest dsr,long uid,int moduleId);
	

}
