package com.lexue.bp.admin.stream.domain;

import java.math.BigDecimal;

/**
 * 退单数据
 * @author bc
 *
 */
public class DataRefundRequest {
	 
	
	   
	   /** 退款编号 */
	   private String refundId;
	   
	   /** 原订单编号 */
	   public String orderId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号 */
	   private int moduleId;
	   
	   /** 退款金额 */
	   private BigDecimal amount = BigDecimal.ZERO;
	   
	   /** 退款时间 */
	   private long refundTime;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(long refundTime) {
		this.refundTime = refundTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	   
	
}
