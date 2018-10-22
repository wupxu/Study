package com.lexue.bp.common.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.lexue.bp.common.entity.mongo.ScoreMongoEntity;

public interface ScoreMongoRepository extends MongoRepository<ScoreMongoEntity, String> {

	Page<ScoreMongoEntity> findByUidAndModuleId(long uid,int moduleId,Pageable page);
}
