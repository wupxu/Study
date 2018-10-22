package com.lexue.bp.web.inf.response;




import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户剩余乐豆
 * @author wpx
 *
 */
@Data
public class UserResponse {
	
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
	
	/** 头像url */
	@JsonProperty("hurl")
	@ApiModelProperty("头像url")
	private String headSculptureUrl;
	
	/** 乐学id*/
	@JsonProperty("lid")
	@ApiModelProperty("乐学id")
	private String lId;
	
	/** 样式*/
	@JsonProperty("styl")
	@ApiModelProperty("瓶子样式 1:玻璃瓶 2:水晶瓶 3:紫光瓶 4:红宝石瓶 5:蓝宝石瓶  6:翡翠瓶 7:五彩瓶")
	private String style;
	
	/** 样式id*/
	@JsonProperty("styid")
	@ApiModelProperty("1:玻璃瓶 2:水晶瓶 3:紫光瓶 4:红宝石瓶 5:蓝宝石瓶  6:翡翠瓶 7:五彩瓶")
	private int styleId;
	
	
	
	
	
}
