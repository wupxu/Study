package com.lexue.bp.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.common.entity.mongo.UnclaimedScore;
import com.lexue.bp.common.entity.mongo.UserMongoEntity;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.repository.mongo.UserMongoRepository;
import com.lexue.bp.common.service.UserMongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserMongoImpl implements UserMongoService {

	@Autowired
	private UserMongoRepository userMongoRepository;
	@Autowired
	private MongoOperations mongoOperations;

//	private List<UnclaimedScore> initUnclaimedScoreList() {
//		List<UnclaimedScore> result = new ArrayList<UnclaimedScore>();
//		for (ProduceChannelEnums pce : ProduceChannelEnums.values()) {
//			result.add(new UnclaimedScore(pce.getCode(), 0));
//		}
//		return result;
//	}

	/**
	 * 检查渠道编码是否存在
	 * 
	 * @param userMongoEntity
	 * @param channelCode
	 */
	private boolean checkChannel(UserMongoEntity userMongoEntity, int channelCode) {
		if (CollectionUtils.isEmpty(userMongoEntity.getScores())) {
			return false;
		}
		for (UnclaimedScore us : userMongoEntity.getScores()) {
			if (us.getChannelCode() == channelCode) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public UserMongoEntity findByUidAndModuleId(long uid, int moduleId) {
		UserMongoEntity userMongoEntity = userMongoRepository.findByUidAndModuleId(uid, moduleId);
		if (userMongoEntity == null) {
			userMongoEntity = new UserMongoEntity();
			userMongoEntity.setModuleId(moduleId);
			userMongoEntity.setTotalConsume(0);
			userMongoEntity.setTotalProduce(0);
			userMongoEntity.setTotalRemain(0);
			userMongoEntity.setUid(uid);
			userMongoEntity = userMongoRepository.save(userMongoEntity);
		}
		return userMongoEntity;
	}

	@Override
	@Transactional
	public void addScore(long uid, int moduleId, int score) {
		findByUidAndModuleId(uid, moduleId);
		Update update = new Update();
		update.inc("totalProduce", score).inc("totalRemain", score);
		mongoOperations.updateFirst(Query.query(Criteria.where("uid").is(uid).and("moduleId").is(moduleId)), update,
				UserMongoEntity.class);
	}

	@Override
	@Transactional
	public void subScore(long uid, int moduleId, int score) {
		findByUidAndModuleId(uid, moduleId);
		Update update = new Update();
		update.inc("totalConsume", score).inc("totalRemain", -score);
		mongoOperations.updateFirst(Query.query(Criteria.where("uid").is(uid).and("moduleId").is(moduleId)), update,
				UserMongoEntity.class);
	}

	@Override
	@Transactional
	public void addUnclaimedScore(long uid, int moduleId, int channelCode, int score) {
		UserMongoEntity userMongoEntity = findByUidAndModuleId(uid, moduleId);
		if (checkChannel(userMongoEntity, channelCode)) {
			Update update = new Update();
			update.inc("scores.$.score", score);
			WriteResult wr = mongoOperations.updateFirst(Query.query(Criteria.where("uid").is(uid).and("moduleId")
					.is(moduleId).and("scores.channelCode").is(channelCode)), update, UserMongoEntity.class);
			if (!wr.isUpdateOfExisting()) {
				log.error("增加待领积分失败,uid:[{}],moduleId:[{}],channelCode:[{}],score:[{}]", uid, moduleId, channelCode,
						score);
			}
		} else {
			// 直接增加
			Update update = new Update();
			update.push("scores", new UnclaimedScore(channelCode, score));
			WriteResult wr = mongoOperations.updateFirst(
					Query.query(Criteria.where("uid").is(uid).and("moduleId").is(moduleId)), update,
					UserMongoEntity.class);
			if (!wr.wasAcknowledged()) {
				log.error("增待领积分失败,uid:[{}],moduleId:[{}],channelCode:[{}],score:[{}]", uid, moduleId, channelCode,
						score);
			}
		}
	}

	@Override
	public void clearUnclaimedScore(long uid, int moduleId, int channelCode) {
		Update update = new Update();
		update.pull("scores", new BasicDBObject("channelCode", channelCode));
		WriteResult wr = mongoOperations.updateFirst(Query.query(Criteria.where("uid").is(uid).and("moduleId")
				.is(moduleId)), update, UserMongoEntity.class);
		if (!wr.isUpdateOfExisting()) {
			log.error("待领积分清零失败,uid:[{}],moduleId:[{}],channelCode:[{}]", uid, moduleId, channelCode);
		}
		
	}

}
