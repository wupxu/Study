package com.lexue.bp.common.service;

import com.lexue.bp.common.entity.mongo.UserMongoEntity;

public interface UserMongoService {
	
	
	UserMongoEntity findByUidAndModuleId(long uid,int moduleId);

	/**
	 * 增加积分
	 * @param uid
	 * @param moduleId
	 * @param score
	 * @return
	 */
	void addScore(long uid,int moduleId,int score);
	
	/**
	 * 扣减积分
	 * @param uid
	 * @param moduleId
	 * @param score
	 * @return
	 */
	void subScore(long uid,int moduleId,int score);
	
	/**
	 * 增加待领取积分值
	 * @param uid
	 * @param moduleId
	 * @param channelCode
	 * @param score
	 */
	void addUnclaimedScore(long uid,int moduleId,int channelCode,int score);
	
	/**
	 * 领取积分后,对待领积分清零
	 * @param uid
	 * @param moduleId
	 * @param channelCode
	 */
	void clearUnclaimedScore(long uid,int moduleId,int channelCode);
}
