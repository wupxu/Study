package com.lexue.bp.admin.service;

import java.util.List;

import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataShareEntity;
import com.lexue.bp.common.entity.DataWatchEntity;

public interface DataService {

	List<DataOrderEntity> getOrders(long sDate,long eDate);
	
	List<DataPostEntity> getPosts(long afterDateLong);
	
	DataPostEntity getPost(String postId);
	
	DataOrderEntity getOrder(String orderId);
	
	int updateDataOrderStatus(long id,byte status,String remark);
	
	
	
	List<DataRefundEntity> getRefundEntity(long sDate,long eDate,String orderId);

	//获取待处理的退款数据
	List<DataRefundEntity> getRefundEntity();
	
	int updateDataRefundStatus(long id,byte status,String remark);
	/**
	 * 修改帖子状态
	 * @param id
	 * @param code
	 * @param remark
	 */
	int updateDataPostStatus(Long id, byte code);
	/**
	 * 获取分享数据
	 * @param sDate
	 * @return
	 */
	List<DataShareEntity> getShares(long time);

	int updateDataShareStatus(Long id, byte status);

	/**
	 * 获取观看视频的数据
	 * @param eDate 
	 * @return
	 */
	List<DataWatchEntity> getWatchs( long eDate);

	int updateDataWatchStatus(Long id, byte status);

	/**
	 * 获取热贴数据
	 * @param afterDateLong
	 * @return
	 */
	List<DataPostHotEntity> getPostHots(long afterDateLong);

	int updateDataPostHotStatus(Long id, byte status);

	DataPostHotEntity getPostHots(long uid, int moduleId, String postId);

	int updateDataPostHotStatusToIgnore(long uid, int moduleId, String postId, byte code);

	List<DataPostEntity> getPostList(long time);
	
	
	
	
	
}
