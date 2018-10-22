package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * @author bc
 *
 */
@Getter
public enum RuleStatusEnums {
	
	ENABLE((byte)1,"启用"),  
	DISABLE((byte)2,"禁用"),
	;
	private byte code;
	private String name;
	private RuleStatusEnums(byte code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Byte, RuleStatusEnums> map = new HashMap<>();

	static {
		for (RuleStatusEnums value : RuleStatusEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static RuleStatusEnums parser(byte code) {
		RuleStatusEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("DetailStatusEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
