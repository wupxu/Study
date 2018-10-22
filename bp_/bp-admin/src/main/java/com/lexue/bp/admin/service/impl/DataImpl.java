package com.lexue.bp.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lexue.bp.admin.service.DataService;
import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataShareEntity;
import com.lexue.bp.common.entity.DataWatchEntity;
import com.lexue.bp.common.enums.DataStatusEnums;
import com.lexue.bp.common.enums.ExceptionEnums;
import com.lexue.bp.common.exception.BizException;
import com.lexue.bp.common.repository.DataOrderRepository;
import com.lexue.bp.common.repository.DataPostHotRepository;
import com.lexue.bp.common.repository.DataPostRepository;
import com.lexue.bp.common.repository.DataRefundRepository;
import com.lexue.bp.common.repository.DataShareRepository;
import com.lexue.bp.common.repository.DataWatchRepository;

@Component
public class DataImpl implements DataService {

	@Autowired
	private DataOrderRepository dataOrderRepository;
	@Autowired
	private DataRefundRepository dataRefundRepository;
	@Autowired
	private DataPostRepository dataPostRepository;
	@Autowired
	private DataShareRepository dataShareRepository;
	@Autowired
	private DataWatchRepository dataWatchRepository;
	@Autowired
	private DataPostHotRepository dataPostHotRepository;

	@Override
	public List<DataOrderEntity> getOrders(long sDate, long eDate) {
		List<DataOrderEntity> orders = dataOrderRepository
				.findByStatusAndPayTimeBetween(DataStatusEnums.PENDING.getCode(), sDate, eDate);
		if (CollectionUtils.isEmpty(orders)) {
			return new ArrayList<DataOrderEntity>();
		}
		return orders;
	}

	@Override
	public int updateDataOrderStatus(long id, byte status, String remark) {
		return dataOrderRepository.updateStatus(id, status, remark);
	}

	@Override
	public DataOrderEntity getOrder(String orderId) {
		return dataOrderRepository.findByOrderId(orderId);
	}

	@Override
	public List<DataRefundEntity> getRefundEntity(long sDate, long eDate, String orderId) {
		return dataRefundRepository.findByOrderIdAndStatusAndRefundTimeBetween(orderId,
				DataStatusEnums.PENDING.getCode(), sDate, eDate);
	}

	@Override
	public List<DataRefundEntity> getRefundEntity() {
		List<DataRefundEntity> dataRefundEntities = dataRefundRepository
				.findByStatus(DataStatusEnums.PENDING.getCode());
		if (CollectionUtils.isEmpty(dataRefundEntities)) {
			throw new BizException(ExceptionEnums.NO_PENDING_DATA);
		}
		return dataRefundEntities;
	}

	@Override
	public int updateDataRefundStatus(long id, byte status, String remark) {
		return dataRefundRepository.updateStatus(id, status, remark);
	}

	@Override
	public List<DataPostEntity> getPosts(long afterDateLong) {
//		List<DataPostEntity>  result = dataPostRepository.findByStatusAndPostTimeLessThanEqual(DataStatusEnums.HANDLED.getCode(),afterDateLong);
		List<DataPostEntity>  result = dataPostRepository.findByStatusAndPostTimeLessThanEqual(DataStatusEnums.HANDLED.getCode(),DataStatusEnums.IGNORE.getCode(),DataStatusEnums.PENDING.getCode(),afterDateLong);
		if (result == null) {
			result = new ArrayList<DataPostEntity>();
		}
		return result;

	}

	@Override
	public int updateDataPostStatus(Long id, byte status) {
		return dataPostRepository.updateStatus(id, status);
	}

	@Override
	public List<DataShareEntity> getShares(long time) {
		return dataShareRepository.findByStatusAndShareTimeGreaterThanEqual(DataStatusEnums.PENDING.getCode(), time);
	}

	@Override
	public int updateDataShareStatus(Long id, byte status) {
		return dataShareRepository.updateStatus(id, status);
	}

	@Override
	public List<DataWatchEntity> getWatchs( long eDate) {
		List<DataWatchEntity> watchs = dataWatchRepository
				.findByStatusAndWatchTimeLessThan(DataStatusEnums.PENDING.getCode(), eDate);
		return watchs==null?new ArrayList<DataWatchEntity>():watchs;
	}

	@Override
	public int updateDataWatchStatus(Long id, byte status) {
		return dataWatchRepository.updateStatus(id, status);
	}

	@Override
	public List<DataPostHotEntity> getPostHots(long afterDateLong) {
		List<DataPostHotEntity> postHots = dataPostHotRepository
				.findDataPostHot(DataStatusEnums.PENDING.getCode(), afterDateLong);
		return postHots==null?new ArrayList<DataPostHotEntity>():postHots;
	}

	@Override
	public int updateDataPostHotStatus(Long id, byte status) {
		return dataPostHotRepository.updateStatus(id, status);
	}

	@Override
	public DataPostEntity getPost(String postId) {
		return dataPostRepository.findByPostId(postId);
	}

	@Override
	public DataPostHotEntity getPostHots(long uid, int moduleId, String postId) {
		return dataPostHotRepository.findByUidAndModuleIdAndPostId(uid,moduleId,postId);
	}

	@Override
	public int updateDataPostHotStatusToIgnore(long uid, int moduleId, String postId, byte status) {
		return dataPostHotRepository.updateStatusToIgnore(uid,moduleId,postId,status);
	}

	@Override
	public List<DataPostEntity> getPostList(long time) {
		List<DataPostEntity>  result = dataPostRepository.findByStatusAndPostTimeLessThanEqual(DataStatusEnums.PENDING.getCode(),time);

		return result;
	}

}
