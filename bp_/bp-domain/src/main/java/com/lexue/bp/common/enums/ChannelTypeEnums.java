package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * 渠道类型：累积和消耗
 * @author bc
 *
 */
@Getter
public enum ChannelTypeEnums {
	
	PRODUCE(1,"累积"), 
	CONSUME(2,"消耗"),
	;
	private int code;
	private String name;
	private ChannelTypeEnums(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Integer, ChannelTypeEnums> map = new HashMap<>();

	static {
		for (ChannelTypeEnums value : ChannelTypeEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static ChannelTypeEnums parser(int code) {
		ChannelTypeEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("ChannelTypeEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
