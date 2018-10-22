package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.DataWatchEntity;

public interface DataWatchRepository extends JpaRepository<DataWatchEntity, Long> {
	
	DataWatchEntity findByBusinessId(String businessId);

	List<DataWatchEntity> findByStatusAndWatchTimeLessThan(byte code, long eDate);

	@Modifying(clearAutomatically=true)
	@Query("update DataWatchEntity w set  w.status = ?2 where w.id = ?1")
	int updateStatus(Long id, byte status);

}
