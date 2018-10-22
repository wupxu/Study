package com.lexue.bp.admin.inf.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计需求类：最后行为时间
 * 
 * @author wpx
 *
 */
@Data
public class StatisticFinalBehavior {

	/** 已发放数量(时间) */
	@ApiModelProperty("已发放数量(时间)")
	private long unUseDate;
	
	/** 待发放数量(时间)*/
	@ApiModelProperty("待发放数量(时间)")
	private long unclaimedDate;
	
	/** 已使用数量(时间) */
	@ApiModelProperty("已使用数量(时间)")
	private long consumeDate;

	/** 乐学号 */
	@ApiModelProperty("乐学号")
	private long uid;

	@ApiModelProperty("渠道类型")
	private int produceChannel;

}
