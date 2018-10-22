package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * 积分累积流水状态
 * 1、2、3、4、5 待领取、未使用、已使用、已失效、已过期。
 * 积分部分使用时，积分的状态仍为未使用状态
 * @author bc
 *
 */
@Getter
public enum ProduceStatusEnums {
	
	UNCLAIMED((byte)1,"待领取"), 
	UNUSED((byte)2,"未使用"),
	USED((byte)3,"已使用"),
	INVALID((byte)4,"已失效"),
//	EXPIRED((byte)5,"已过期"),
	;
	private byte code;
	private String name;
	private ProduceStatusEnums(byte code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Byte, ProduceStatusEnums> map = new HashMap<>();

	static {
		for (ProduceStatusEnums value : ProduceStatusEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static ProduceStatusEnums parser(byte code) {
		ProduceStatusEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("DetailStatusEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
