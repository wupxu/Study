package com.lexue.bp.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lexue.bp.common.entity.DataOrderEntity;

public interface DataOrderRepository extends JpaRepository<DataOrderEntity, Long> {

	List<DataOrderEntity> findByStatusAndPayTimeBetween(byte status,long sDate,long eDate);
	
	DataOrderEntity findByOrderId(String orderId);
	
	@Modifying(clearAutomatically=true)
	@Query("update DataOrderEntity t set  t.status = ?2,t.remark=?3 where t.id = ?1")
	int updateStatus(long id,byte status,String remark);
}

