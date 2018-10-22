package com.lexue.bp.common.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.common.entity.UserEntity;
import com.lexue.bp.common.repository.UserRepository;
import com.lexue.bp.common.service.UserService;
import com.lexue.bp.common.util.CommonUtil;

@Deprecated
@Service
public class UserImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserEntity findByUidAndModuleId(long uid, int moduleId) {
		UserEntity userEntity = userRepository.findByUidAndModuleId(uid, moduleId);
		if (userEntity == null) {
			userEntity = new UserEntity();
			userEntity.setId(CommonUtil.generateId());
			userEntity.setModuleId(moduleId);
			userEntity.setTotalConsume(0);
			userEntity.setTotalProduce(0);
			userEntity.setTotalRemain(0);
			userEntity.setUid(uid);
			userRepository.save(userEntity);
		}
		return userEntity;
	}

	@Override
	@Transactional
	public UserEntity addScore(long uid, int moduleId, int score) {
		findByUidAndModuleId(uid,moduleId);
		//update 触发锁
		userRepository.updateTotalProduceAndTotalRemain(uid, moduleId, score);
		return findByUidAndModuleId(uid,moduleId);
	}

	@Override
	public UserEntity subScore(long uid, int moduleId, int score) {
		findByUidAndModuleId(uid,moduleId);
		userRepository.updateTotalConsumeAndTotalRemain(uid, moduleId, score);
		return findByUidAndModuleId(uid,moduleId);
	}

}
