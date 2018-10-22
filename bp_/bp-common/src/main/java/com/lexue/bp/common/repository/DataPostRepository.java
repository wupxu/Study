package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;

public interface DataPostRepository extends JpaRepository<DataPostEntity, Long> {

	DataPostEntity findByPostId(String postId);
	
	List<DataPostEntity> findByStatusAndPostTimeLessThanEqual(byte status,long afterDateLong);
	
	@Modifying(clearAutomatically=true)
	@Query("update DataPostEntity t set  t.status = ?2 where  t.id = ?1")
	int updateStatus(long id,byte status);

	@Query(value ="select * from bp_data_post t where  (t.status = ?1 or t.status = ?2 or t.status = ?3) and  t.post_time <= ?4",nativeQuery = true)
	List<DataPostEntity> findByStatusAndPostTimeLessThanEqual(byte status, byte stat, byte sta,long afterDateLong);

	
}
