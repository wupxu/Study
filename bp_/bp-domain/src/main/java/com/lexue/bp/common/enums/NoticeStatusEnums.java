package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

@Getter
public enum NoticeStatusEnums {
	
	OPEN((byte)1,"开启"),
	CLOSE((byte)2,"关闭"),
	
	;
	private byte code;
	private String name;
	private NoticeStatusEnums(byte code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Byte, NoticeStatusEnums> map = new HashMap<>();

	static {
		for (NoticeStatusEnums value : NoticeStatusEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static NoticeStatusEnums parser(byte code) {
		NoticeStatusEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("DataStatusEnums parser error, value=[%d].", code));
		}
		return status;
	}
}