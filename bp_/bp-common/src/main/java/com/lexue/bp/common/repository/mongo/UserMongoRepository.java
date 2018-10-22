package com.lexue.bp.common.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lexue.bp.common.entity.mongo.UserMongoEntity;

public interface UserMongoRepository extends MongoRepository<UserMongoEntity, String> {
	UserMongoEntity findByUidAndModuleId(long uid,int moduleId);
}
