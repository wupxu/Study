package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.DataPostHotEntity;

public interface DataPostHotRepository extends JpaRepository<DataPostHotEntity, Long> {

	DataPostHotEntity findByPostId(String postId);

	List<DataPostHotEntity> findByStatusAndPostTimeLessThanEqual(byte status,long postTime);
	
	@Query(value="SELECT a.* FROM bp_data_post_hot a ,bp_data_post b WHERE a.post_id = b.post_id AND a.STATUS =?1 AND b.post_time<=?2",nativeQuery=true)
	List<DataPostHotEntity> findDataPostHot(byte status,long postTime);
	
	@Modifying(clearAutomatically=true)
	@Query("update DataPostHotEntity t set  t.status = ?2 where t.id=?1")
	int updateStatus(long id,byte status);

	DataPostHotEntity findByUidAndModuleIdAndPostId(long uid, int moduleId, String postId);

	@Modifying(clearAutomatically=true)
	@Query("update DataPostHotEntity t set  t.status = ?4 where t.uid =?1 and t.moduleId =?2 and t.postId =?3")
	int updateStatusToIgnore(long uid, int moduleId, String postId, byte status);
}
