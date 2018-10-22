package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 积分消费流水
 * @author bc
 *
 */
@Entity
@Table(name = "bp_consume")
@Data
public class ConsumeEntity {

	/** 系统主键 */
	@Id
	private Long id;
	
	/** 用户编号 */
	private long uid;
	
	/** 模块编号 */
	@Column(name="module_id")
	private int moduleId;
	
	/** 消耗渠道类型 */
	@Column(name="consume_channel")
	private int consumeChannel;
	
	/** 消耗业务编号 */
	@Column(name="consume_bizid")
	private String consumeBizid;
	
	/** 消耗积分 */
	@Column(name="consume_score")
	private int consumeScore;
	
	/**
	 * 后台扣减积分的原因
	 * 
	 * 备注	
	 */
	private String remark;
	
	/** 创建时间 */
	private long cTime;

}
