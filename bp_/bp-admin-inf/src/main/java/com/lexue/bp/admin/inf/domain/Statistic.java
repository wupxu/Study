package com.lexue.bp.admin.inf.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计需求类：按类型统计
 * 
 * @author wpx
 *
 */
@Data
public class Statistic {

	/** 时间 */
	@ApiModelProperty("时间")
	private long date;

	/** 渠道类型 */
	@ApiModelProperty("渠道类型")
	private int type;

	/** 渠道总分数 */
	@ApiModelProperty("渠道总分数")
	private int countScore;
	

}
