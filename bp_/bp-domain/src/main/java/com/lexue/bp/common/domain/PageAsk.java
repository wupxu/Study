package com.lexue.bp.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("分页模型")
@AllArgsConstructor
@NoArgsConstructor
public class PageAsk {
	@ApiModelProperty(value="页码",example="1")
	private int page;
	@ApiModelProperty(value="每页记录数",example="20")
	private int size;
	@ApiModelProperty(value="排序",example="cTime,desc&title,asc")
	private String sort;

}
