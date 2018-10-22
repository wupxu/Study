package com.lexue.bp.admin.stream.domain;

import java.math.BigDecimal;

public class DataOrderRequest {
	   
	   /** 订单编号 */
	   public String orderId;
	   
	   /** 用户编号 */
	   public long uid;
	   
	   /** 模块编号*/
	   public int moduleId;
	   
	   /** 支付金额*/
	   public BigDecimal amount = BigDecimal.ZERO;
	   
	   /** 支付时间 */
	   public long payTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public long getPayTime() {
		return payTime;
	}

	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}
	   
	   
	
}
