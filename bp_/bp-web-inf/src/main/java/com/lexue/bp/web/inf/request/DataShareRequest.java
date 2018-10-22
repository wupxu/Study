package com.lexue.bp.web.inf.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class DataShareRequest {

	/** 业务编号 */
	@JsonProperty("bi")
	@ApiModelProperty("业务编号 (1:帖子id，2:商品id,3表示网页)")
	private String businessId;

	/** 业务类型 */
	@JsonProperty("bt")
	@ApiModelProperty("业务类型(1:帖子,2:商品,3表示网页)")
	private int businessType;
	
	/** 业务类型 */
	@JsonProperty("pf")
	@ApiModelProperty("分享平台，如微信..")
	private String platform;
	/** 业务类型 */
	@JsonProperty("url")
	@ApiModelProperty("分享的网页url")
	private String url;


}