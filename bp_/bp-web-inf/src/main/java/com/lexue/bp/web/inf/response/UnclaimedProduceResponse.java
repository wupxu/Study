package com.lexue.bp.web.inf.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 各渠道累积待领取乐豆数
 * 
 * @author wpx
 *
 */
@Data
public class UnclaimedProduceResponse {
	
	/** 渠道编码 */
	@JsonProperty("pcc")
	@ApiModelProperty("渠道编码 1:订单 2:观看视频 3:分享课程 4:发贴 5:评为热贴")
	private int produceChannelCode;
	
	/** 渠道名字 */
	@JsonProperty("pcn")
	@ApiModelProperty("渠道名字")
	private String produceChannel;

	/** 积分数 */
	@JsonProperty("ps")
	@ApiModelProperty("积分数")
	private int produceScore;
}
