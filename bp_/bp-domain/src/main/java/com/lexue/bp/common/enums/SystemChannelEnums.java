package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * 系统操作编号
 * @author wpx
 *
 */
@Getter
public enum SystemChannelEnums {
	
	ADD(1,"系统增加"),	 
	SUB(2,"系统扣减"),
	HAND(3,"手动修改"),
	;
	private int code;
	private String name;
	private SystemChannelEnums(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Integer, SystemChannelEnums> map = new HashMap<>();

	static {
		for (SystemChannelEnums value : SystemChannelEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static SystemChannelEnums parser(int code) {
		SystemChannelEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("ProduceChannelEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
