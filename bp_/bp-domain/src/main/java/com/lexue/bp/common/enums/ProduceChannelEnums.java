package com.lexue.bp.common.enums;

import java.util.HashMap;

import lombok.Getter;

/**
 * 积分累积渠道编号
 * @author bc
 *
 */
@Getter
public enum ProduceChannelEnums {
	
	ORDER(1,"购买商品"), 
	WATCH(2,"观看课程"),
	SHARE(3,"分享商品"),
	POST(4,"咖啡厅发贴"),
	POSTHOT(5,"评为热贴"),
	CMS(6,"后台增加"),
	ACTIVITY(7,"活动"),
	
	;
	private int code;
	private String name;
	private ProduceChannelEnums(int code,String name) {
		this.code = code;
		this.name = name;
	}
	
	static HashMap<Integer, ProduceChannelEnums> map = new HashMap<>();

	static {
		for (ProduceChannelEnums value : ProduceChannelEnums.values()) {
			map.put(value.getCode(), value);
		}
	}

	public static ProduceChannelEnums parser(int code) {
		ProduceChannelEnums status = map.get(code);
		if (status == null) {
			throw new IllegalArgumentException(String.format("ProduceChannelEnums parser error, value=[%d].", code));
		}
		return status;
	}
}
