package com.lexue.bp.common.service;

import org.springframework.data.domain.Page;

import com.lexue.bp.common.entity.mongo.ScoreMongoEntity;

public interface ScoreMongoService {

	void saveScoreDetail(long uid,int moduleId,int channelCode,int channelType,int score);
	
	Page<ScoreMongoEntity> findByUidAndModuleId(long uid,int moduleId,int pageNumber,int size);
}
