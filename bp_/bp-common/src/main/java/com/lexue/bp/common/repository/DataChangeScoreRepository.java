package com.lexue.bp.common.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lexue.bp.common.entity.DataChangeScoreEntity;


public interface DataChangeScoreRepository extends JpaRepository<DataChangeScoreEntity, Long>,JpaSpecificationExecutor<DataChangeScoreEntity>  {

	
	List<DataChangeScoreEntity> findByUid(long uid);

	List<DataChangeScoreEntity> findByUidAndModuleId(long uid,int moduleId);

}
