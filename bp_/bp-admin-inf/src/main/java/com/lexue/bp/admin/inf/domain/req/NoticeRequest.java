package com.lexue.bp.admin.inf.domain.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 后台公告请求类
 * 
 * @author wpx
 *
 */
@Data
public class NoticeRequest {

	/**
	 * 0/1 开启/关闭 状态
	 */
	@ApiModelProperty("0/1开启/关闭 状态")
	private int status;

	/** 公告内容 */
	@ApiModelProperty("公告内容")
	private String content;
	
	/** 公告内容 */
	@ApiModelProperty("公告内容")
	private String noticeName;
	
	/**操作员编号*/
	@ApiModelProperty("操作员编号")
	private long operatorId;

	/** 操作员姓名 */
	@ApiModelProperty("操作员姓名")
	private String operatorName;

	/* 模版id */
	@ApiModelProperty("模版id")
	private int moduleId;

	/** 更新时间 */
	/*@Column(name = "update_time")
	private Date updateTime;*/
}
