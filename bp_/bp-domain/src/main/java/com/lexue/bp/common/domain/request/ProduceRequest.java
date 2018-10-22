package com.lexue.bp.common.domain.request;

import com.lexue.bp.common.enums.ProduceChannelEnums;

import lombok.Data;

/**
 * 生成待领取积分请求
 * @author bc
 *
 */
@Data
public class ProduceRequest {
	
	/** 用户编号 */
	private long uid;
	
	/** 模块编号 */
	private int moduleId;
		
	/** 累积渠道编号 */
	private ProduceChannelEnums produceChannelEnums;
	
	/** 累积业务编号 */
	private String produceBizid;
	
	/** 累积积分数 */
	private int produceScore;
	
	/**
	 * 后台增加积分的原因
	 * 
	 * 备注
	 */
	private String remark;


}
