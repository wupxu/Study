/**
 * 
 */
package com.lexue.bp.admin.stream.converter;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.lexue.bp.admin.stream.domain.DataOrderRequest;
import com.lexue.bp.admin.stream.domain.DataPostHotRequest;
import com.lexue.bp.admin.stream.domain.DataPostRequest;
import com.lexue.bp.admin.stream.domain.DataRefundRequest;
import com.lexue.bp.admin.stream.domain.DataWatchRequest;
import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataWatchEntity;
import com.lexue.bp.common.enums.DataStatusEnums;
import com.lexue.bp.common.util.CommonUtil;

/**
 * 从消息中间件收到对象后，转换成JPA实体对象
 * @author bc
 */
@Service
public class ConvertService {

	/**
	 * 转换订单实体对象
	 * @param dor
	 * @return
	 */
	public DataOrderEntity convertDataOrder(DataOrderRequest dor){
		DataOrderEntity result = new DataOrderEntity();
	    result.setAmount(dor.getAmount());
	    result.setCTime(Calendar.getInstance().getTimeInMillis());
	    result.setId(CommonUtil.generateId());
	    result.setModuleId(dor.getModuleId());
	    result.setOrderId(dor.getOrderId());
	    result.setPayTime(dor.getPayTime());
	    result.setStatus(DataStatusEnums.PENDING.getCode());
	    result.setUid(dor.getUid());
		return result;
	}
	
	/**
	 * 退款
	 * @param drr
	 * @return
	 */
	public DataRefundEntity convertDataRefund(DataRefundRequest drr){
		DataRefundEntity result = new DataRefundEntity();
	    result.setAmount(drr.getAmount());
	    result.setCTime(Calendar.getInstance().getTimeInMillis());
	    result.setId(CommonUtil.generateId());
	    result.setModuleId(drr.getModuleId());
	    result.setOrderId(drr.getOrderId());
	    result.setRefundId(drr.getRefundId());
	    result.setRefundTime(drr.getRefundTime());
	    result.setStatus(DataStatusEnums.PENDING.getCode());
	    result.setUid(drr.getUid());
		return result;
	} 
	
	/**
	 * 帖子
	 * @param dpr
	 * @return
	 */
	public DataPostEntity convertDataPost(DataPostRequest dpr){
		DataPostEntity result = new DataPostEntity();
	    result.setCTime(Calendar.getInstance().getTimeInMillis());
	    result.setId(CommonUtil.generateId());
	    result.setModuleId(Integer.parseInt(dpr.getModuleId()));
	    result.setIsDelete((byte)dpr.getDeleteFlag());
	    result.setPostId(dpr.getPostId());
	    result.setPostTime(Long.parseLong(dpr.getPostTime()));
	    result.setStatus(DataStatusEnums.PENDING.getCode());
	    result.setUid(Long.parseLong(dpr.getUid()));
		return result;
	} 
	
	/**
	 * 热帖
	 * @param dphr
	 * @return
	 */
	public DataPostHotEntity convertDataPostHot(DataPostHotRequest dphr){
		DataPostHotEntity result = new DataPostHotEntity();
	    result.setCTime(Calendar.getInstance().getTimeInMillis());
	    result.setId(CommonUtil.generateId());
	    result.setModuleId(dphr.getModuleId());
	    result.setPostId(dphr.getPostId());
	    result.setPostTime(dphr.getPostTime());
	    result.setStatus(DataStatusEnums.PENDING.getCode());
	    result.setUid(dphr.getUid());
		return result;
	} 
	
	/**
	 * 分享
	 * @param dsr
	 * @return
	 */
	//改为单独处理
	/*public DataShareEntity convertDataShare(DataShareRequest dsr){
		DataShareEntity result = new DataShareEntity();
	    result.setCTime(Calendar.getInstance().getTimeInMillis());
	    result.setId(CommonUtil.generateId());
	    result.setModuleId(dsr.getModuleId());
	    result.setBusinessId(dsr.getBusinessId());
	    result.setShareTime(dsr.getShareTime());
	    result.setStatus(DataStatusEnums.PENDING.getCode());
	    result.setUid(dsr.getUid());
		return result;
	} */
	
	/**
	 * 观看视频
	 * @param dwr
	 * @return
	 */
	public DataWatchEntity convertDataWatch(DataWatchRequest dwr){
		DataWatchEntity result = new DataWatchEntity();
	    result.setCTime(Calendar.getInstance().getTimeInMillis());
	    result.setId(CommonUtil.generateId());
	    result.setModuleId(dwr.getModuleId());
	    result.setBusinessId(dwr.getBusinessId());
	    result.setDuration(dwr.getDuration());
	    result.setWatchTime(dwr.getWatchTime());
	    result.setStatus(DataStatusEnums.PENDING.getCode());
	    result.setUid(dwr.getUid());
		return result;
	} 


}
