package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bp_data_watch",indexes={@Index(name="idx_s_watch",columnList="status,watch_time")})
@Data
public class DataWatchEntity {
	
	   /** 系统主键 */
	   @Id
	   private Long id;
	   
	   /** 业务编号 */
	   @Column(name="business_id")
	   private String businessId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号 */
	   @Column(name="module_id")
	   private int moduleId;
	   
	   /** 观看时长（秒） */
	   private long duration;
	   
	   /** 观看时间点 */
	   @Column(name="watch_time")
	   private long watchTime;
	
	   /**
	    * 处理状态 */
	   @Column(name="status")
	   private byte status;
	   
	   /** 创建时间 */
	   private long cTime;
	   
}
