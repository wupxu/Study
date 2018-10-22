package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bp_data_sign")
@Data
public class DataSignEntity {
	
	   /** 系统主键 */
	   @Id
	   public Long id;
	   /** 用户编码 */
	   public long uid;
	   /** 模块编号 */
	   @Column(name="module_id")
	   public int moduleId;
	   /** 签到时间 */
	   @Column(name="sign_time")
	   public long signTime; 
	   /** 连续签到次数 */
	   @Column(name="sign_num")
	   public int signNum; 
	   /**处理状态 */
	   public byte status;
	   /** 创建时间 */
	   public long cTime;

}
