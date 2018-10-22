package com.lexue.bp.admin.inf.domain.res;


import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentResponse {

	/** 名称 
	@ApiModelProperty("名称")
	private String contentName;*/
	// 名称
	@ApiModelProperty("标识")
	private int id;

	/** 更新时间 */
	@ApiModelProperty("更新时间")
	private long updateTime;

	/**操作员姓名*/
	@ApiModelProperty("操作员姓名")
	private String operatorName;
}
