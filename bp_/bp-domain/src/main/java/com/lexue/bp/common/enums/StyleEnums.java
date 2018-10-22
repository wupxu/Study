package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;


/**
 * 积分累积渠道编号
 * @author bc
 *
 */
@Getter
public enum StyleEnums {
	
	GLASS(1,"玻璃瓶"), 
	CRYSTAL(2,"水晶瓶"),
	PURPLE(3,"紫光瓶"),
	RED(4,"红宝石瓶"),
	BLUE(5,"蓝宝石瓶"),
	EMERALD(6,"翡翠瓶"),
	COLOURFUL(7,"五彩瓶"),
	;
	private int code;
	private String name;
	private StyleEnums(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Integer, StyleEnums> map = new HashMap<>();

	static {
		for (StyleEnums value : StyleEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static StyleEnums parser(int code) {
		StyleEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("StyleEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
