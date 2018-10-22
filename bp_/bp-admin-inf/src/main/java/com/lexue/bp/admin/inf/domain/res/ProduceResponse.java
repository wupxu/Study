package com.lexue.bp.admin.inf.domain.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积分累积流水
 * @author bc
 *
 */
@Data
public class ProduceResponse {


	@ApiModelProperty("记录序号")
	private long id;

	/** 用户编号 */
	@ApiModelProperty("用户编号")
	private long uid;
	
	/** 累积渠道编号 */
	@ApiModelProperty("累积渠道编号")
	private int produceChannel;
	
	/** 累积积分数 */
	@ApiModelProperty("累积积分数 ")
	private int produceScore;
	
	/** 生效日期 毫秒 */
	@ApiModelProperty("生效日期 毫秒")
	private long effectiveDate;


}
