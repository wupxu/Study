package com.lexue.bp.common.entity;


import javax.persistence.*;

import com.lexue.bp.common.entity.pk.RulePK;
import lombok.Data;

@Entity
@Table(name = "bp_rule")
@IdClass(RulePK.class)
@Data
public class RuleEntity {

	/**
	 * 规则编号
	 * 1/2/3/4 购买商品/观看课程/分享课程/咖啡厅发帖
	 */
	@Id
	@Column(name="rule_id")
	private int ruleId;



	/** 模块编号 */
	@Id
	@Column(name="module_id")
	private int moduleId;
	
	/**
	 * 规则名称
	 */
	@Column(name="rule_name")
	private String ruleName;
	
	/**
	 * 图片
	 */
	private String imageUrl;
	
	/**任务说明*/
	@Column(name="task_specification")
	private String taskSpecification;
	
	/**攻略说明*/
	@Column(name="strategy_specification",length = 1500)
	private String strategySpecification;
	
	/**条件值 */
	@Column(name="condition_value")
	private int conditionValue;
	
	/** 结果值 */
	@Column(name="result_value")
	private int resultValue;

	/** 评为热帖值 */
	@Column(name="hot_value")
	private int hotValue;
	
	/** 封顶积分值 */
	@Column(name="max_score")
	private int maxScore;
	
	/** 封顶次数值 */
	@Column(name="max_count")
	private int maxCount;
	
	/**
	 * 1/2 开启/关闭
	 * 状态
	 */
	private int status;
	
	/** 更新时间 */
	@Column(name="update_time")
	private long updateTime;

	/** 操作员姓名 */
	private String operatorName;

	/** 操作员编号 */
	private long operatorId;

	/**
	 * 
	 */
//	@OneToMany(fetch=FetchType.EAGER,mappedBy="ruleEntity")
//	private List<RuleSignEntity> ruleSignList;

}
