package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bp_data_share",indexes={@Index(name="idx_s_share",columnList="status,share_time")})
@Data
public class DataShareEntity {
	   /** 系统主键 */
       @Id
	   private Long id;
       
	   /** 业务编号 */
       @Column(name="business_id")
	   private String businessId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号*/
	   @Column(name="module_id")
	   private int moduleId;
	   
	   /** 分享时间 毫秒 */
	   @Column(name="share_time")
	   private long shareTime;
	   
	   /**
	    * 处理状态 */
	   @Column(name="status")
	   private byte status;
	   
	   /** 创建时间 */
	   private long cTime;
}
