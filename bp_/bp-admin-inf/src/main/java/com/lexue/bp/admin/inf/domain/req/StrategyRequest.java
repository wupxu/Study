package com.lexue.bp.admin.inf.domain.req;




import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 后台攻略请求类
 * @author wpx
 *
 */
@Data
public class StrategyRequest {
	
	/** 攻略名称 
	@ApiModelProperty("攻略名称")
	private String strategyName;*/

	/** 什么是乐豆 */

	@ApiModelProperty("什么是乐豆")
	private String beanDescribe;

	/** 如何使用乐豆 */
	@ApiModelProperty("如何使用乐豆")
	private String useMethod;

	/** 乐豆有效期 */
	@ApiModelProperty("乐豆有效期")
	private String timeDescribe;

	/** 其他 */
	@ApiModelProperty("其他")
	private String other;

	/**操作人编号*/
	@ApiModelProperty("操作人编号")
	private long operatorId;
	
	/** 操作员姓名 */
	@ApiModelProperty("操作员姓名")
	private String operatorName;

	/* 模版id */
	@ApiModelProperty("模版id")
	private int moduleId;

	/** 更新时间 *//*
	@ApiModelProperty("更新时间")
	private Date updateTime;*/
}
