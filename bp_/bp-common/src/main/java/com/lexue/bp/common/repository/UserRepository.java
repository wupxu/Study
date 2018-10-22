package com.lexue.bp.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.UserEntity;

@Deprecated
public interface UserRepository extends JpaRepository<UserEntity, Long> ,JpaSpecificationExecutor<UserEntity>{

	UserEntity findByUidAndModuleId(long uid, int moduleId);
	
	@Modifying(clearAutomatically=true)
	@Query("update UserEntity t set  t.totalProduce = t.totalProduce + ?3,t.totalRemain = t.totalRemain + ?3 where t.uid=?1 and t.moduleId = ?2")
	int updateTotalProduceAndTotalRemain(long uid, int moduleId, int score);
	
	@Modifying(clearAutomatically=true)
	@Query("update UserEntity t set  t.totalConsume = t.totalConsume + ?3,t.totalRemain = t.totalRemain - ?3 where t.uid=?1 and t.moduleId = ?2")
	int updateTotalConsumeAndTotalRemain(long uid, int moduleId, int score);

	@Modifying(clearAutomatically=true)
	@Query("update UserEntity t set  t.totalProduce = t.totalProduce + ?2,t.totalRemain = t.totalRemain + ?2 where t.uid = ?1")
	int updateTotalProduceAndTotalRemain(long uid, int score);
	
	@Query("select  t from UserEntity t where t.uid = ?1 and t.moduleId = ?2")
	UserEntity findTotalByUidAndModuleId(long uid,int moduleId);

	UserEntity findByUid(long uid);

}
