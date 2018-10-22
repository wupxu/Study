package com.lexue.bp.common.domain.request;

import com.lexue.bp.common.domain.PageAsk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理查询 
 * @author wpx
 *
 */
@Data
public class UserManagementQueryRequest {
	
	/**
	 * 用户编号，为0时表示无需通过用户编号过滤
	 */
	@ApiModelProperty("用户编号")
	private long uid;
	
	/**
	 * 开始乐豆数(总剩余乐豆数)
	 */
	@ApiModelProperty("开始乐豆数")
	private int startScore = 0;
	
	/**
	 * 结束乐豆数(总剩余乐豆数)
	 */
	@ApiModelProperty("结束乐豆数")
	private int endScore = 0;

	/**
	 * 模块编码 必填
	 */
	@ApiModelProperty("模块编码 必填")
	private int moduleId;
	
    /**
     * 分页
     */
	@ApiModelProperty("分页")
    private PageAsk pageAsk;
}
