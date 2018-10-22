package com.lexue.bp.admin.inf.domain.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 系统变更积分接收类
 * @author wpx
 *
 */
@Data
public class DataChangeScoreResponse {

	/** id */
	@ApiModelProperty("id")
	private long id;

	/** 用户编号 */
	@ApiModelProperty("用户编号")
	private long uid;

	/** 操作人编号 */
	@ApiModelProperty("操作人编号")
	private long operatorId;

	/** 操作人姓名 */
	@ApiModelProperty("操作人姓名 ")
	private String operatorName;
	
	/**系统扣减或增加
	 * 1/2
	 * */
	@ApiModelProperty("系统扣减或增加1/2 ")
	private int systemChannel;
	
	/**系统变更途径(手动修改。。)*/
	@ApiModelProperty("系统变更途径(手动修改..)")
	private String changeChannel;

	/**
	 * 系统变更积分
	 *
	 */
	@ApiModelProperty("系统变更积分")
	private int score;

	/** 系统增加积分原因 */
	@ApiModelProperty("系统增加积分原因 ")
	private String addReason;

	/** 变更时间 */
	@ApiModelProperty("变更时间 ")
	private long updateTime;
}
