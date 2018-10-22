package com.lexue.bp.common.domain.request;

import com.lexue.bp.common.enums.ConsumeChannelEnums;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConsumeRequest {
	
	/** 用户编号 */
	private long uid;
	
	/** 模块编号 */
	private int moduleId;
	
	/** 消耗渠道类型 */
	private ConsumeChannelEnums consumeChannel;
	
	/** 消耗业务编号 */
	private String consumeBizid;
	
	/** 消耗积分 */
	private int consumeScore;
	
	/**
	 * 后台扣减积分的原因
	 * 
	 * 备注
	 */
	private String remark;
	
}
