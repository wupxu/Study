package com.lexue.bp.common.service;


import com.lexue.bp.common.domain.request.ConsumeRequest;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.entity.ProduceEntity;
import com.lexue.bp.common.enums.ProduceChannelEnums;

public interface CoreService {
	
	/**
	 * 增加待领取的积分
	 *  生成规则：考虑封顶次数 ， 封顶每日积分 在生成业务中实现，不在此实现
	 * @param pr
	 * @return
	 */
	ProduceEntity addUnclaimedScore(ProduceRequest pr);
	
	/**
	 * 增加未使用的积分,即生成即时生效的积分
	 * @param pr
	 * @return
	 */
	ProduceEntity addUnusedScore(ProduceRequest pr);
	

	/**
	 * 按累积渠道领取,
	 * 修改累积明细表，状态为“已发放”，生效和失效时间
	 * 修改用户积分表
	 * @param uid
	 * @param moduleId
	 * @param produceChannelEnums
	 * @return
	 */
	void UnclaimedToUnusedScore(long uid,int moduleId,ProduceChannelEnums produceChannelEnums);

	/**
	 * 扣减积分
	 * @param consumeRequest
	 */
	void consume(ConsumeRequest consumeRequest);
	

	/**
	 * 直接修改待领积分数
	 * @param produceEntity
	 * @param subtractScore 扣减累积积分的数量
	 */
	void updateProduceUnclaimedScore(ProduceEntity produceEntity,int subtractScore);
	
	
	/**
	 * 根据订单号（业务编号）
	 * @param orderId
	 * @return
	 */
	ProduceEntity findProduceEntityByOrderId(String orderId);
	
	/**
	 * 获取用户待领订单数
	 * @param uid
	 * @return
	 */
	int getProduceUnclaimedCount(long uid,int moduleId);
	

	/**
	 * 获取当日生成的待领积分的总值
	 * @param uid
	 * @param moduleId
	 * @param produceChannel
	 * @return
	 */
	int getScoreCurDay(long uid,int moduleId,int produceChannel);

	/**
	 * 获取总发帖数（乐豆数）
	 * @param uid
	 * @param moduleId
	 * @param code
	 * @return
	 */
	int getProduceUnclaimedCount(long uid, int moduleId, int produceChannelCode);
	
	/**
	 * 获取总分享数
	 * @param uid
	 * @param moduleId
	 * @param code
	 * @return
	 */
	int getProduceShareUnclaimedCount(long uid, int moduleId, int produceChannel);

	/**
	 * 获取总时长数
	 * @param uid
	 * @param moduleId
	 * @param produceChannel
	 * @return
	 */
	int getProduceWatchUnclaimedCount(long uid, int moduleId, int produceChannel,int resultValue,int conditionValue);

	/**
	 * 使积分失效 ,时间以天有单位，精确到毫秒
	 * @param date
	 */
	void invalidScore(long date);

}
