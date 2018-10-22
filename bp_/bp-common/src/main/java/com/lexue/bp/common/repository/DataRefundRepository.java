package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.DataRefundEntity;

public interface DataRefundRepository extends JpaRepository<DataRefundEntity, Long> {

	List<DataRefundEntity> findByOrderIdAndStatusAndRefundTimeBetween(String orderId,byte status,long sDate,long eDate);
	
	List<DataRefundEntity> findByStatus(byte status);
	
	@Modifying(clearAutomatically=true)
	@Query("update DataRefundEntity t set  t.status = ?2,t.remark=?3 where  t.id = ?1")
	int updateStatus(long id,byte status,String remark);
}
