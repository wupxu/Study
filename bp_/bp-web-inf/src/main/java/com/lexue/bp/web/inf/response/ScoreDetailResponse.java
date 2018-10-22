package com.lexue.bp.web.inf.response;



import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 乐豆明细
 * @author wpx
 *
 */
@Data
public class ScoreDetailResponse {
	/**渠道名称*/
	@JsonProperty("pcn")
	@ApiModelProperty("渠道名称")
	private String produceChannelName;
	
	/**积分数 */
	@JsonProperty("pc")
	@ApiModelProperty("积分数")
	private int produceScore;
	
	/** 时间 毫秒 */
	@JsonProperty("date")
	@ApiModelProperty("时间")
	private long effectiveDate;
}
