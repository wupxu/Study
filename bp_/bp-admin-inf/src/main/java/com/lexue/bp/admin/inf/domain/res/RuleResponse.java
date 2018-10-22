package com.lexue.bp.admin.inf.domain.res;

import java.util.Date;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RuleResponse {

	/**
	 * 规则编号
	 * 1/2/3/4 购买商品/观看课程/分享课程/咖啡厅发帖
	 */
	@ApiModelProperty("规则编号:1/2/3/4 购买商品/观看课程/分享课程/咖啡厅发帖/")
	private Integer ruleId;
	
	/**
	 * 规则名称
	 */
	@ApiModelProperty("规则名称")
	private String ruleName;
	
	/**
	 * 图片
	 */
	@ApiModelProperty("图片url")
	private String imageUrl;
	
	/**任务说明*/
	@ApiModelProperty("任务说明")
	private String taskSpecification;
	
	/**攻略说明*/
	@ApiModelProperty("攻略说明")
	private String strategySpecification;
	
	
	/**条件值 */
	@ApiModelProperty("条件值")
	private int conditionValue;
	
	/** 结果值 */
	@ApiModelProperty("条件值")
	private int resultValue;

	/** 结果值 */
	@ApiModelProperty("评为热帖值")
	private int hotValue;
	
	/** 封顶积分值 */
	@ApiModelProperty("封顶积分值")
	private int maxScore;
	
	/** 封顶次数值 */
	@ApiModelProperty("封顶次数值 ")
	private int maxCount;
	
	/**
	 * 1/2 开启/关闭
	 * 状态
	 */
	@ApiModelProperty("1/2 开启/关闭 ")
	private int status;
	
	/** 更新时间 */
	@ApiModelProperty("更新时间 ")
	private long updateTime;

	/** 操作员姓名 */
	private String operatorName;

	/** 操作员编号 */
	private long operatorId;


}
