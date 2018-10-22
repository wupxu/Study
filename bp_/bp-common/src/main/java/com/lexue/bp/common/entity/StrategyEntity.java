package com.lexue.bp.common.entity;


import javax.persistence.*;

import com.lexue.bp.common.entity.pk.StrategyPK;
import lombok.Data;

@Entity
@Table(name = "bp_strategy")
@IdClass(StrategyPK.class)
@Data
public class StrategyEntity {

	/** 系统主键 */
	@Id
	private long id;

	/* 模版id */
	@Id
	@Column(name = "mdid")
	private int moduleId;

	/** 攻略名称 
	private String strategyName;*/

	/** 什么是乐豆 */
	@Column(length = 1500)
	private String beanDescribe;

	/** 如何使用乐豆 */
	@Column(length = 1500)
	private String useMethod;

	/** 乐豆有效期 */
	@Column(name = "time_describe",length = 1500)
	private String timeDescribe;

	/** 其他 */
	@Column(length = 1500)
	private String other;

	/** 操作员姓名 */
	private String operatorName;

	/** 更新时间 */
	@Column(name = "update_time")
	private long updateTime;



}