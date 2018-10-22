package com.lexue.bp.common.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改乐豆请求类
 * 
 * @author wpx
 *
 */
@Data
public class ProduceUpdateRequest {

	/**
	 * 1/2 系统增加/系统扣减
	 */
	@ApiModelProperty("1/2 系统增加/系统扣减")
	private int choose;

	/** 用户编号 */
	@ApiModelProperty("用户编号")
	private long uid;
	
	/** 操作人编号 */
	@ApiModelProperty("操作人编号")
	private long operatorId;

	/** 操作人姓名 */
	@ApiModelProperty("操作人姓名")
	private String operatorName;
	
	/**模块id*/
	@ApiModelProperty("模块id")
	private int moduleId;

	/** 增加或扣减乐豆 */
	@ApiModelProperty("增加或扣减乐豆")
	private int score;

	/**
	 * 后台变更原因
	 */
	@ApiModelProperty("后台变更原因")
	private String remark;
}
