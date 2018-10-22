package com.lexue.bp.common.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.LockEntity;

public interface LockRepository extends JpaRepository<LockEntity, String> {

	@Modifying
	@Query(value = "insert into bp_lock(method_name,create_time) values(?1,?2)",nativeQuery = true)
	int insert(String methodName,Date createTime);
 
}
