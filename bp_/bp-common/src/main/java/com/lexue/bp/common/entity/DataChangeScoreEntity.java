package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/**
 * 系统变更积分表
 * @author wpx
 *
 */
@Entity
@Table(name = "bp_data_change_score")
@Data
public class DataChangeScoreEntity {
	
	/** 系统主键 */
	@Id
	private Long id;

	/** 用户编号 */
	private long uid;

	/** 操作人编号 */
	@Column(name = "operator_id")
	private long operatorId;

	/** 操作人姓名 */
	@Column(name = "operator_name")
	private String operatorName;
	
	/**系统增加或扣减
	 * 1加/2减
	 * */
	@Column(name = "system_channel")
	private int systemChannel;
	
	/**系统变更途径(手动修改。。)*/
	@Column(name = "change_channel")
	private String changeChannel;

	/**
	 * 系统变更积分
	 *
	 */
	private int score;

	/** 系统增加积分原因 */
	@Column(name = "add_reason",length = 1500)
	private String addReason;

	/** 变更时间 */
	private long updateTime;

	/* 模块id*/
	@Column(name = "mdid")
	private int moduleId;
}
