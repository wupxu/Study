package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * @author bc
 *
 */
@Getter
public enum DataStatusEnums {
	
	PENDING((byte)0,"待处理"),
	
	
	/**
	 *对于订单，观看等来说，成功生成待领积分
	 *对于退款，成功扣减积分
	 */
	HANDLED((byte)1,"已处理"),
	
	/**
	 * 不满足条件
	 */
	IGNORE((byte)2,"已处理但忽略"),
	
	
	EXCEPTION((byte)3,"处理但异常"),
	;
	private byte code;
	private String name;
	private DataStatusEnums(byte code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Byte, DataStatusEnums> map = new HashMap<>();

	static {
		for (DataStatusEnums value : DataStatusEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static DataStatusEnums parser(byte code) {
		DataStatusEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("DataStatusEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
