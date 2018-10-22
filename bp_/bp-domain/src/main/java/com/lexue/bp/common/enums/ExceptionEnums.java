package com.lexue.bp.common.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnums {
	
	NOT_FOUND(10,"查询对象不存在"),
	ALREADY_DEL(11,"已删除"),
//	REPEAT_SIGN(12,"今日已签到"),
	NO_LOGIN(12,"用户未登录"),
	SHARE(13,"已分享"),
	ARGS_ERROR(14,"参数出错"),
	
	PRODUCE_INVALID(20,"不存在有效积分"),
	DATE_INVALID(21,"日期无效"),
	RULE_DISABLE(22,"规则禁用"),
	NO_PENDING_DATA(23,"不存在待处理数据"),
	;

	private int code;
	private String msg;
	
	private ExceptionEnums(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
