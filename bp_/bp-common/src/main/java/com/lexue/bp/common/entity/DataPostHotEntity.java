package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="bp_data_post_hot",indexes={@Index(name="idx_s_hotpost",columnList="status,post_time")})
@Data
public class DataPostHotEntity {
	  /**系统主键 */
	   @Id
	   public Long id;
	   
	   /** 用户编号 */
	   public long uid;
	   
	   /** 模块编号 */
	   @Column(name="module_id")
	   private int moduleId;
	   	   
	   /** 帖子编号 */
	   @Column(name="post_id")
	   private String postId;
	   
	   /** 评为热帖时间 */
	   @Column(name="post_time")
	   public long postTime;
	   
	   /** 
	    * 
	    *处理状态 */
	   @Column(name="status")
	   public byte status;
	   
	   /** 创建时间*/
	   public long cTime;
	
}
