package com.lexue.bp.web.inf.response;



import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
/**
 * 攻略说明
 * @author wpx
 *
 */
@Data
public class BeanMethodResponse {
	
	/**
	 * 规则名称
	 */
	@JsonProperty("rn")
	@ApiModelProperty("规则名称(购买，观看，咖啡厅发帖...)")
	private String ruleName;
	
	/**
	 * 规则id
	 */
	@JsonProperty("ri")
	@ApiModelProperty("规则id(1/2/3/4 购买商品/观看课程/分享课程/咖啡厅发帖)")
	private int ruleId;
	
	/**攻略说明*/
	@ApiModelProperty("攻略说明")
	@JsonProperty("spf")
	private String strategySpecification;
	
}
