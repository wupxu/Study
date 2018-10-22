package com.lexue.bp.common.domain.request;

import com.lexue.bp.common.domain.PageAsk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积分累积查询
 * @author bc
 *
 */
@Data
public class ProduceQueryRequest {
	
	/**
	 * 规则编号,为0时表示全部
	 */
	@ApiModelProperty("规则编号,为0时表示全部")
	private int ruleId;
	
	
	/**
	 * 模块编码 必填
	 */
	@ApiModelProperty("模块编码 必填")
	private int moduleId;
	
	/**
	 * 用户编号，为0时表示无需通过用户编号过滤
	 */
	@ApiModelProperty("用户编号，为0时表示无需通过用户编号过滤")
	private long uid;
	
	/**
	 * 开始时间：到账时间（秒）
	 */
	@ApiModelProperty("开始时间")
	private long startDate = 0;
	
	/**
	 * 结束时间：到账时间（秒）
	 */
	@ApiModelProperty("结束时间")
	private long endDate = Long.MAX_VALUE;
	
    /**
     * 分页
     */
	@ApiModelProperty("分页")
    private PageAsk pageAsk;
}
