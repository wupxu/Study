package com.lexue.bp.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/**
 * 定时任务锁类
 * @author wpx
 *
 */
@Entity
@Table(name ="bp_lock")
@Data
public class LockEntity {

	/** 方法名 */
	@Id
	@Column(name = "method_name")
	private String methodName;


	/** 创建时间*/
	@Column(name = "create_time")
	private Date createTime;
}
