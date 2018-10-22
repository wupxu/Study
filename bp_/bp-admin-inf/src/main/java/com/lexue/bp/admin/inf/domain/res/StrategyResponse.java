package com.lexue.bp.admin.inf.domain.res;


import lombok.Data;

/**
 * 获得攻略接收类
 * @author wpx
 *
 */
@Data
public class StrategyResponse {


	/** 攻略名称 
	private String strategyName;*/

	/** 什么是乐豆 */
	private String beanDescribe;

	/** 如何使用乐豆 */
	private String useMethod;

	/** 乐豆有效期 */
	private String timeDescribe;

	/** 其他 */
	private String other;

	/** 操作员姓名 */
	private String operatorName;
}
