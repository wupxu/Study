package com.lexue.bp.common.enums;

import lombok.Getter;

@Getter
public enum ResponseStatusEnums {
	OK(0,"成功"),
	ERROR(1,"失败")
	;

	private int code;
	private String msg;
	
	private ResponseStatusEnums(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
