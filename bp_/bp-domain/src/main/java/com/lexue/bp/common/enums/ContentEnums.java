package com.lexue.bp.common.enums;

import lombok.Getter;

@Getter
public enum ContentEnums {
	
	NOTICE(1,"公告"),
	STRATEGY(2,"攻略"),
	;

	private int code;
	private String msg;
	
	private ContentEnums(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
