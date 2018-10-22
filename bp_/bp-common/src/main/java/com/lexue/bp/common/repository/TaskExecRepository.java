package com.lexue.bp.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lexue.bp.common.entity.TaskExecEntity;

public interface TaskExecRepository extends JpaRepository<TaskExecEntity, Long> ,JpaSpecificationExecutor<TaskExecEntity> {
	
}
