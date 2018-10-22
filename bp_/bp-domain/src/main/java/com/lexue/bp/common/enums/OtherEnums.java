package com.lexue.bp.common.enums;

import lombok.Getter;

@Getter
public enum OtherEnums {
	
	STRATEGY("http://esfiledev.lexue.com/active/gk/2018/09/gk_lebean_strategy/index.html"),
	COURTESY(""),
	IOS("lexuegaokao://huanhaolijustforfun"),
	;
 
	private String msg;
	
	private OtherEnums(String msg) {
		this.msg = msg;
	}
	
}
