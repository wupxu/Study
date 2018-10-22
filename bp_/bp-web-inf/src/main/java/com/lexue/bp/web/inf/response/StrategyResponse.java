package com.lexue.bp.web.inf.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class StrategyResponse {
	
	/** 什么是乐豆 */
	@JsonProperty("bd")
	@ApiModelProperty("什么是乐豆")
	private String beanDescribe;

	/** 如何使用乐豆 */
	@JsonProperty("um")
	@ApiModelProperty("如何使用乐豆")
	private String useMethod;

	/** 乐豆有效期 */
	@JsonProperty("td")
	@ApiModelProperty("乐豆有效期")
	private String timeDescribe;

	/** 其他 */
	@JsonProperty("oth")
	@ApiModelProperty("其他")
	private String other;

}
