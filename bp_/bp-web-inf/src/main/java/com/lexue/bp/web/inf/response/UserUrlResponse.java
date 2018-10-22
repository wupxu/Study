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
public class UserUrlResponse {
	
	
	/** 公告内容 */
	@JsonProperty("nc")
	@ApiModelProperty("公告内容")
	private String noticeContent;
	
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
