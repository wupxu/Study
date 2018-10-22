package com.lexue.bp.web.inf.response;



import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
/**
 * 功能说明
 * @author wpx
 *
 */
@Data
public class TaskResponse {
	
	/**
	 * 规则名称
	 */
	@JsonProperty("rn")
	@ApiModelProperty("规则名称(购买，观看，咖啡厅发帖...)")
	private String ruleName;
	
	/**
	 * 图片url
	 */
	@JsonProperty("imur")
	@ApiModelProperty("图片url")
	private String imageUrl;
	
	/**功能说明*/
	@JsonProperty("tsf")
	@ApiModelProperty("功能说明")
	private String taskSpecification;
	
}
