package com.lexue.bp.common.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.lexue.bp.common.entity.mongo.ScoreMongoEntity;
import com.lexue.bp.common.repository.mongo.ScoreMongoRepository;
import com.lexue.bp.common.service.ScoreMongoService;

@Service
public class ScoreMongoImpl implements ScoreMongoService {
	
	@Autowired
	private ScoreMongoRepository scoreMongoRepository;

	@Override
	public void saveScoreDetail(long uid, int moduleId, int channelCode, int channelType, int score) {
		ScoreMongoEntity scoreMongoEntity = new ScoreMongoEntity();
		scoreMongoEntity.setChannelCode(channelCode);
		scoreMongoEntity.setChannelType(channelType);
		scoreMongoEntity.setCTime(Calendar.getInstance().getTimeInMillis());
		scoreMongoEntity.setModuleId(moduleId);
		scoreMongoEntity.setScore(score);
		scoreMongoEntity.setUid(uid);
		scoreMongoRepository.save(scoreMongoEntity);
	}

	@Override
	public Page<ScoreMongoEntity> findByUidAndModuleId(long uid, int moduleId, int pageNumber, int size) {
		if (size==0) {
			size=20;
		}
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Sort.Direction.DESC, "cTime"));
		Sort sort = new Sort(orders);
		PageRequest pageRequest = new PageRequest(pageNumber, size, sort);
		Page<ScoreMongoEntity> result = scoreMongoRepository.findByUidAndModuleId(uid, moduleId, pageRequest);
		return result;
	}
}
