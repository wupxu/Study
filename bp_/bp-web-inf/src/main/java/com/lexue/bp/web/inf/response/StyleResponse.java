package com.lexue.bp.web.inf.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class StyleResponse {
	
	/** 总乐豆 */  
	@JsonProperty("pds")
	@ApiModelProperty("总乐豆")
	private int produceScore;
	
	/** 总消耗积分*/
	@JsonProperty("tc")
	@ApiModelProperty("总消费")
	private int totalConsume;
	
	/** 总剩余积分 */
	@JsonProperty("tr")
	@ApiModelProperty("总剩余")
	private int totalRemain;
	
	/** 样式*/
	@JsonProperty("styl")
	@ApiModelProperty("瓶子样式 1:玻璃瓶 2:水晶瓶 3:紫光瓶 4:红宝石瓶 5:蓝宝石瓶  6:翡翠瓶 7:五彩瓶")
	private String style;
	
	/** 样式id*/
	@JsonProperty("styid")
	@ApiModelProperty("1:玻璃瓶 2:水晶瓶 3:紫光瓶 4:红宝石瓶 5:蓝宝石瓶  6:翡翠瓶 7:五彩瓶")
	private int styleId;
}
