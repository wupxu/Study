package com.lexue.bp.common.domain;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageReply<T> {
	
	/**
	 * 总页数
	 */
	@ApiModelProperty("总页数")
	private int totalPages;
	
	/**
	 * 总记录数
	 */
	@ApiModelProperty("总记录数")
	private long totalElements;
	
	/**每页记录数**/
	@ApiModelProperty("每页记录数")
	private int size;
	
	/**当前页数**/
	@ApiModelProperty("当前页数")
	private int number;
	
	/**是否能向后翻**/
	@ApiModelProperty("是否能向后翻")
	private boolean next;

	/**是否能向前翻**/
	@ApiModelProperty("是否能向前翻")
	private boolean previous;

	/**
	 * 数据集合
	 */
	@ApiModelProperty("数据集合")
	private List<T> datas;
	
}
