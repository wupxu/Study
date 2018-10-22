package com.lexue.bp.web.inf.response;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户剩余乐豆
 * @author wpx
 *
 */
@Data
public class UserScoreResponse {
	
	/** 总剩余积分 */
	@JsonProperty("tr")
	@ApiModelProperty("剩余积分")
	private int totalRemain;
	
	/** 总获得积分 */
	@JsonProperty("tp")
	@ApiModelProperty("获得积分")
	private int totalProduce;
	
	/** 总消耗积分 */
	@JsonProperty("tc")
	@ApiModelProperty("消耗积分")
	private int totalConsume;
	
	/** 公告内容 */
	@JsonProperty("nc")
	@ApiModelProperty("公告内容")
	private String noticeContent;
	
	/** 头像url */
	@JsonProperty("hurl")
	@ApiModelProperty("头像url")
	private String headSculptureUrl;
	
	/** 攻略url */
	@JsonProperty("surl")
	@ApiModelProperty("攻略url")
	private String strategyUrl;
	
	/** 换好礼url*/
	@JsonProperty("curl")
	@ApiModelProperty("换好礼url")
	private String courtesyUrl;
	
	/** 任务说明 */
	@JsonProperty("trp")
	@ApiModelProperty("任务说明")
	private List<TaskResponse> taskResponse;
	
	
	
}
