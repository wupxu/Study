package com.lexue.bp.common.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="bp_data_post",indexes={@Index(name="idx_s_post",columnList="status,post_time")})
@Data

public class DataPostEntity {
	   /** 系统主键 */
	   @Id
	   private Long id;
	   
	   /** 帖子编号 */
	   @Column(name="post_id")
	   private String postId;
	   
	   /** 用户编号 */
	   private long uid;
	   
	   /** 模块编号 */
	   @Column(name="module_id")
	   private int moduleId;

	   /**
	    * 0/1
	    * 未删除/已删除
	    * 是否删除
	    */
	   @Column(name="is_delete")
	   private byte isDelete;
	   
	   /** 发帖时间 毫秒 */
	   @Column(name="post_time")
	   private long postTime;
	   
	   /**
	    * 
	    * 处理状态 */
	   @Column(name="status")
	   private byte status;
	   
	   /** 创建时间 */
	   private long cTime;
	   
}
