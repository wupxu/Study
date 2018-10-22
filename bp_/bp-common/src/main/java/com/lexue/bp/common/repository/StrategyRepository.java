package com.lexue.bp.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.StrategyEntity;

public interface StrategyRepository extends JpaRepository<StrategyEntity, Long>{
	
	/*@Modifying(clearAutomatically=true)
	@Query("update StrategyEntity t set t.beanDescribe=?2,t.timeDescribe=?3,t.other=?4,t.updateTime=?5,t.useMethod=?6 where t.id=?1")
	void updateById(Long id, String beanDescribe, String timeDescribe, String other, long updateTime, String useMethod);*/

	@Query("select t from StrategyEntity t")
	StrategyEntity findStrategy();

	StrategyEntity findByModuleId(int moduleId);

	
}
