package com.lexue.bp.common.domain.request;

import com.lexue.bp.common.domain.PageAsk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 乐豆修改记录查询
 * @author wpx
 *
 */
@Data
public class ScoreChangeRequest {
	
	/**
	 * 用户编号，为0时表示无需通过用户编号过滤
	 */
	@ApiModelProperty("用户编号，为0时表示无需通过用户编号过滤")
	private long uid;
	
	/**
	 * 开始时间：修改时间（秒）
	 */
	@ApiModelProperty("开始时间")
	private long startDate = 0;
	
	/**
	 * 结束时间：修改时间（秒）
	 */
	@ApiModelProperty("结束时间")
	private long endDate = Long.MAX_VALUE;

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
