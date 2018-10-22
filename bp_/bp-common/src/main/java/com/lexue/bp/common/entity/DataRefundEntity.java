package com.lexue.bp.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

/**
 * 退单数据
 * @author bc
 *
 */
@Entity
@Table(name="bp_data_refund",indexes={@Index(name="idx_s_refund",columnList="status,refund_time")})
@Data
public class DataRefundEntity {
	 
	/** 系统主键 */
	   @Id
	   private Long id;
	   
	   /** 退款编号 */
	   @Column(name="refund_id")
	   private String refundId;
	   
	   /** 原订单编号 */
	   @Column(name="order_id")
	   public String orderId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号 */
	   @Column(name="module_id")
	   private int moduleId;
	   
	   /** 退款金额 */
	   private BigDecimal amount = BigDecimal.ZERO;
	   
	   /** 退款时间 */
	   @Column(name="refund_time")
	   private long refundTime;
	   
	   /**
	    * 处理状态 */
	   @Column(name="status")
	   private byte status;
	   
	   /**
	    * 备注
	    */
	   private String remark;
	   
	   /** 创建时间 */
	   private long cTime;
}
