package com.lexue.bp.common.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="bp_task_exec")
@Data
public class TaskExecEntity {
	/**
	 * 系统主键
	 */
	@Id
	private Long id;
	
	/**
	 * 类别（买课、看课...）
	 */
	private String type;
	
	/**
	 *任务执行起始时间 
	 */
	private Date startDate;
	
	/**
	 * 任务执行结束时间
	 */
	private Date endDate;
	
	/**
	 * 总记录数
	 */
	private int  totalRecord;
	
	/**
	 * 成功数
	 */
	private int successRecord;
	
	/**
	 * 失败数
	 */
	private int failureRecord;
	
	/**
	 * 处理异常的ID集合
	 */
	private String ids;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建日期
	 */
	private Date cTime;
}
