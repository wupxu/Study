package com.lexue.bp.admin.inf.domain.req;


import com.lexue.bp.common.domain.PageAsk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 统计请求类
 * 
 * @author wpx
 *
 */
@Data
public class UserScoreRequest {

	/**
	 * 查询开始时间
	 */
	@ApiModelProperty("查询开始时间")
	private long startDate;
	
	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private int status;
	
	/**
	 * 乐学号
	 */
	@ApiModelProperty("乐学号")
	private long uid;

	/** 查询结束时间*/
	@ApiModelProperty("查询结束时间")
	private long endDate;
	
	 /**
     * 分页
     */
	@ApiModelProperty("分页")
    private PageAsk pageAsk;
	
}
