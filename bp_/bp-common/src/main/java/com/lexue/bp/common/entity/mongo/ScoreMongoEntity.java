package com.lexue.bp.common.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 积分流水,包括累积和消耗
 * @author bc
 *
 */
@Document(collection="score")
@TypeAlias("score")
@CompoundIndexes({@CompoundIndex(name="score_detail_idx",def="{'uid':1,'moduleId':1,'cTime':-1}")})
@Data

public class ScoreMongoEntity {
	/** 系统主键 */
	@Id
	private String id;
	
	/** 用户编号 */
	private long uid;
	
	/** 模块编号 */
	private int moduleId;
	
	/** 渠道编号，包括累积和消费的渠道编码 */
	private int channelCode;
	
	/** 渠道分类，包括两类：1:累积 2:消费 */
	private int channelType;
	
	/** 累积积分数 */
	private int score;
	
	/** 创建时间 */
	private long cTime;
}
