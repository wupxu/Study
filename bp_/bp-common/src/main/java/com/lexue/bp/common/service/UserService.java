package com.lexue.bp.common.service;

import com.lexue.bp.common.entity.UserEntity;

@Deprecated
public interface UserService {
	
	UserEntity findByUidAndModuleId(long uid,int moduleId);

	/**
	 * 增加积分
	 * @param uid
	 * @param moduleId
	 * @param score
	 * @return
	 */
	UserEntity addScore(long uid,int moduleId,int score);
	
	/**
	 * 扣减积分
	 * @param uid
	 * @param moduleId
	 * @param score
	 * @return
	 */
	UserEntity subScore(long uid,int moduleId,int score);
}
