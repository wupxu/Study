package com.lexue.bp.common.enums;

import lombok.Getter;

@Getter
public enum LockEnums {
	
	RUNORDER_METHOD("runOrder"),
	RUNREFUND_METHOD("runRefund"),
	RUNPOST_METHOD("runPost"),
	RUNPOSTHOT_METHOD("runPostHot"),
	RUNSHARE_METHOD("runShare"),
	RUNWATCH_METHOD("runWatch"),
	RUNINVALID_METHOD("runInvalid"),
	;

	private String msg;
	
	private LockEnums(String msg) {
		this.msg = msg;
	}
	
}
