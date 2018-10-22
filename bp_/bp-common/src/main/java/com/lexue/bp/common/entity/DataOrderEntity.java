package com.lexue.bp.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="bp_data_order",indexes={@Index(name="idx_s_pay",columnList="status,pay_time")})
@Data
public class DataOrderEntity {
	   
	   /** 系统主键 */
	   @Id
	   private Long id;
	   
	   /** 订单编号 */
	   @Column(name="order_id")
	   private String orderId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号*/
	   @Column(name="module_id")
	   private int moduleId;
	   
	   /** 支付金额 */
	   private BigDecimal amount = BigDecimal.ZERO;
	   
	   /** 支付时间 毫秒 */
	   @Column(name="pay_time")
	   private long payTime;
	   
	   /**
	    * 
	    * 处理状态 */
	   @Column(name="status")
	   private byte status;
	   
	   
	   /**
	    * 备注
	    */
	   private String remark;
	   
	   /** 创建时间 毫秒*/
	   private long cTime;
}
