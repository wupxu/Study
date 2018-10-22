package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * 积分消耗渠道编号
 * @author bc
 *
 */
@Getter
public enum ConsumeChannelEnums {
	
	SHIP(1,"商城"), 
    GAME(2,"摇奖"),
	CMSSUB(3,"后台扣减"),
	REFUND(4,"退款"),
	INVALID(5,"积分失效"),
	;
	private int code;
	private String name;
	private ConsumeChannelEnums(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Integer, ConsumeChannelEnums> map = new HashMap<>();

	static {
		for (ConsumeChannelEnums value : ConsumeChannelEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static ConsumeChannelEnums parser(int code) {
		ConsumeChannelEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("ProduceChannelEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
