package com.lexue.bp.common.entity.mongo;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="user")
@TypeAlias("user")
@CompoundIndexes({@CompoundIndex(name="user_idx",def="{'uid':1,'moduleId':1}")})
@Data
public class UserMongoEntity {

	/** 系统主键 */
	@Id
	private String id;
	
	/** 用户编号  */
	private long uid;
	
	/** 模块编号 */
	private int moduleId;
	
	/** 总累积积分*/
	private int totalProduce;
	
	/** 总消耗积分*/
	private int totalConsume;
	
	/** 总剩余积分 */
	private int totalRemain;
	
	/** 待领积分*/
	private List<UnclaimedScore> scores;

}
