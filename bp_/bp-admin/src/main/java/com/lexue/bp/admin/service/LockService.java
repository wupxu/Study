package com.lexue.bp.admin.service;

import com.lexue.bp.common.entity.LockEntity;

/**
 * 对任务调度锁操作类
 * @author lexue
 *
 */
public interface LockService {

	void methodLockSave(LockEntity lockEntity);
	
	void deleteByMethodName(String methodName);
	
	LockEntity findByMethodName(String methodName);

}
