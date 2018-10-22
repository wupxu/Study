package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.DataShareEntity;

public interface DataShareRepository extends JpaRepository<DataShareEntity, Long> {
	
	DataShareEntity findByUidAndBusinessId(long uid,String businessId);
	
	List<DataShareEntity> findByStatusAndShareTimeGreaterThanEqual(byte status,long time);
	
	@Modifying(clearAutomatically=true)
	@Query("update DataShareEntity s set  s.status = ?2 where s.id = ?1")
	int updateStatus(Long id, byte status);
}
