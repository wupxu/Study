package com.lexue.bp.common.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.ConsumeEntity;

public interface ConsumeRepository extends JpaRepository<ConsumeEntity, Long>,JpaSpecificationExecutor<ConsumeEntity> {
	
	ConsumeEntity findByUidAndModuleIdAndConsumeBizidAndConsumeChannel(long uid,int moduleId,String bizid,int consumeChannel);

	/**需求统计：已使用统计*/
	List<ConsumeEntity> findBycTimeBetweenOrderByCTime(long startUsedDate, long endUsedDate);
	
	/*  统计待发放*/
	@Query(value = "SELECT t.id,t.cTime,t.consumeChannel" +
			"sum(t.consumeScore)  as consumeScore FROM ConsumeEntity t" +
			"  where FROM_UNIXTIME(t.cTime/1000)  >= ?1 and FROM_UNIXTIME(t.cTime/1000) <?2 and t.status =?3 group by t.cTime,t.consumeChannel limit ?4,?5 ",nativeQuery = true)
	List<ConsumeEntity> findAllConsumeScore(String startTime, String endTime, long status,int pageNo,int pageSize);
}
