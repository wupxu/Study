package com.lexue.bp.common.domain;

import java.io.Serializable;

import com.lexue.bp.common.entity.ConsumeEntity;
import com.lexue.bp.common.entity.ProduceEntity;

import lombok.Data;
/**
 * 最后一次行为时间查询接收类
 * @author wpx
 *
 */
@Data
public class StatisticViewInfo implements Serializable {
	
	private static final long serialVersionUID = -6347911007178390219L;
	
	private ProduceEntity produceEntity;

	private ConsumeEntity consumeEntity;
	
	public StatisticViewInfo(ProduceEntity produceEntity, ConsumeEntity consumeEntity) {
		super();
		this.produceEntity = produceEntity;
		this.consumeEntity = consumeEntity;
	}
	public StatisticViewInfo() {
		super();
	}
	
	
	
	
}
