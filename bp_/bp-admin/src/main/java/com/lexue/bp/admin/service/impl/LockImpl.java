package com.lexue.bp.admin.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lexue.bp.admin.service.LockService;
import com.lexue.bp.common.entity.LockEntity;
import com.lexue.bp.common.repository.LockRepository;

@Service
public class LockImpl implements LockService {

	@Autowired
	private LockRepository lockRepository;

	@Override
	@Transactional
	public void methodLockSave(LockEntity lockEntity) {
		lockRepository.insert(lockEntity.getMethodName(), lockEntity.getCreateTime());

	}

	@Override
	public void deleteByMethodName(String methodName) {
		lockRepository.delete(methodName);
	}

	@Override
	public LockEntity findByMethodName(String methodName) {
		return lockRepository.findOne(methodName);
	}



}
