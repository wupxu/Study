package com.lexue.bp.common.enums;

import lombok.Getter;

@Getter
public enum PageEnums {
	
	PAGENO(0),
	PAGESIZE(20),
	;

	private int code;
	
	private PageEnums(int code) {
		this.code = code;
	}
	
}
