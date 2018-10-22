package com.lexue.bp.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lexue.bp.common.entity.DataSignEntity;

public interface DataSignRepository extends JpaRepository<DataSignEntity, Long> {

	/*List<DataSignEntity> findByUid(long uid);

	@Query("select t from DataSignEntity t where t.uid=?1 and t.moduleId=?2 and t.signTime=?3 ")
	DataSignEntity findByUidAndModuleIdAndSignTime(long uid, int moduleId, long SignTime);

	List<DataSignEntity> findByUidAndModuleId(long uid, int moduleId);*/

}
