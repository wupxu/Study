package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 *
 */
@Getter
public enum DataPostStatusEnums {
	
	NODELETE((byte)0,"未删除"),  
	DELETE((byte)1,"已删除"),
	;
	private byte code;
	private String name;
	private DataPostStatusEnums(byte code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Byte, DataPostStatusEnums> map = new HashMap<>();

	static {
		for (DataPostStatusEnums value : DataPostStatusEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static DataPostStatusEnums parser(byte code) {
		DataPostStatusEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("DetailStatusEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
