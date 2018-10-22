package com.lexue.bp.common.service.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.lexue.bp.common.domain.request.ConsumeRequest;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.entity.ConsumeEntity;
import com.lexue.bp.common.entity.ProduceEntity;
import com.lexue.bp.common.enums.ChannelTypeEnums;
import com.lexue.bp.common.enums.ConsumeChannelEnums;
import com.lexue.bp.common.enums.ExceptionEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.ProduceStatusEnums;
import com.lexue.bp.common.exception.BizException;
import com.lexue.bp.common.repository.ConsumeRepository;
import com.lexue.bp.common.repository.ProduceRepository;
import com.lexue.bp.common.repository.UserRepository;
import com.lexue.bp.common.service.CoreService;
import com.lexue.bp.common.service.ScoreMongoService;
import com.lexue.bp.common.service.UserMongoService;
import com.lexue.bp.common.service.UserService;
import com.lexue.bp.common.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CoreImpl implements CoreService {

	@Autowired
	private ProduceRepository produceRepository;
	@Autowired
	private ConsumeRepository consumeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMongoService userMongoService;
	@Autowired
	private ScoreMongoService scoreMongoService;

	/**
	 * 供后台任务使用，增加待领积分
	 */
	@Override
	@Transactional
	public ProduceEntity addUnclaimedScore(ProduceRequest pr) {
		ProduceEntity de = produceRepository.findProduceEntity(pr.getProduceBizid(), pr.getUid(), pr.getModuleId(),
				pr.getProduceChannelEnums().getCode(), ProduceStatusEnums.UNCLAIMED.getCode());
		if (de == null) {
			de = new ProduceEntity();
			de.setCTime(Calendar.getInstance().getTimeInMillis());
			de.setId(CommonUtil.generateId());
			de.setModuleId(pr.getModuleId());
			de.setProduceBizid(pr.getProduceBizid());
			de.setProduceChannel(pr.getProduceChannelEnums().getCode());
			de.setProduceScore(pr.getProduceScore());
			de.setRemark(pr.getRemark());
			de.setStatus(ProduceStatusEnums.UNCLAIMED.getCode());
			de.setUid(pr.getUid());
		} else {
			de.setProduceScore(pr.getProduceScore());
			de.setRemark(pr.getRemark());
			de.setCTime(Calendar.getInstance().getTimeInMillis());
		}
		ProduceEntity result = produceRepository.save(de);
		
		//同时保存待领积分到mongo
		userMongoService.addUnclaimedScore(pr.getUid(), pr.getModuleId(), pr.getProduceChannelEnums().getCode(), pr.getProduceScore());
		
		return result;
	}

	@Override
	@Transactional
	public ProduceEntity addUnusedScore(ProduceRequest pr) {
		ProduceEntity de = produceRepository.findProduceEntity(pr.getProduceBizid(), pr.getUid(), pr.getModuleId(),
				pr.getProduceChannelEnums().getCode(), ProduceStatusEnums.UNUSED.getCode());
		if (de == null) {
			de = new ProduceEntity();
			de.setCTime(Calendar.getInstance().getTimeInMillis());
			de.setId(CommonUtil.generateId());
			de.setModuleId(pr.getModuleId());
			de.setProduceBizid(pr.getProduceBizid());
			de.setProduceChannel(pr.getProduceChannelEnums().getCode());
			de.setProduceScore(pr.getProduceScore());
			de.setRemark(pr.getRemark());
			de.setStatus(ProduceStatusEnums.UNUSED.getCode());
			de.setUid(pr.getUid());
			de.setEffectiveDate(Calendar.getInstance().getTimeInMillis());
			de.setInvalidDate(CommonUtil.addYear());
		} else {
			de.setProduceScore(pr.getProduceScore());
			de.setRemark(pr.getRemark());
			de.setCTime(Calendar.getInstance().getTimeInMillis());
		}
		ProduceEntity result = produceRepository.save(de);
		// 累积用户积分
		userService.addScore(pr.getUid(), pr.getModuleId(), pr.getProduceScore());
		userMongoService.addScore(pr.getUid(), pr.getModuleId(), pr.getProduceScore());
		
		//写积分明细
		scoreMongoService.saveScoreDetail(pr.getUid(), pr.getModuleId(), pr.getProduceChannelEnums().getCode(), 
				ChannelTypeEnums.PRODUCE.getCode(),  pr.getProduceScore());

		return result;
	}

	@Override
	@Transactional
	public void UnclaimedToUnusedScore(long uid, int moduleId, ProduceChannelEnums produceChannelEnums) {
		// 更新积分明细
		long effectiveDate = Calendar.getInstance().getTimeInMillis();
		long invalidDate = CommonUtil.addYear();

		int totalProduce = 0;
		String totalStr = produceRepository.countUnclaimedScore(uid, moduleId, produceChannelEnums.getCode(), ProduceStatusEnums.UNCLAIMED.getCode());
		if (totalStr != null ) {
			totalProduce = Integer.parseInt(totalStr);
			if (totalProduce == 0) {
				return;
			}
			produceRepository.update(uid, moduleId, produceChannelEnums.getCode(),
					ProduceStatusEnums.UNCLAIMED.getCode(), effectiveDate, invalidDate,
					ProduceStatusEnums.UNUSED.getCode());

			// 累积用户积分
			userService.addScore(uid, moduleId, totalProduce);
			userMongoService.addScore(uid, moduleId, totalProduce);
			
			//写积分明细
			scoreMongoService.saveScoreDetail(uid, moduleId, produceChannelEnums.getCode(), 
					ChannelTypeEnums.PRODUCE.getCode(),  totalProduce);
		} 
		
		//清空此渠道的待领积分
		userMongoService.clearUnclaimedScore(uid, moduleId,produceChannelEnums.getCode());
	}



	/**
	 * 1.判断是否已扣减 2.插入消耗总表 3.在累积表中对“已发放”的积分按创建时间扣减，并将积分变更为“已使用”，若为部分扣减，则状态仍然是“已发放”
	 * 4.更新用户积分表
	 */
	@Override
	@Transactional
	public void consume(ConsumeRequest consumeRequest) {
		ConsumeEntity consumeEntity = consumeRepository.findByUidAndModuleIdAndConsumeBizidAndConsumeChannel(
				consumeRequest.getUid(), consumeRequest.getModuleId(), consumeRequest.getConsumeBizid(),
				consumeRequest.getConsumeChannel().getCode());
		if (consumeEntity != null) {
			log.info("重复的扣减请求 [{}]", consumeRequest);
			return;
		}

		consumeEntity = new ConsumeEntity();
		consumeEntity.setConsumeBizid(consumeRequest.getConsumeBizid());
		consumeEntity.setConsumeChannel(consumeRequest.getConsumeChannel().getCode());
		consumeEntity.setConsumeScore(consumeRequest.getConsumeScore());
		consumeEntity.setCTime(Calendar.getInstance().getTimeInMillis());
		consumeEntity.setId(CommonUtil.generateId());
		consumeEntity.setModuleId(consumeRequest.getModuleId());
		consumeEntity.setRemark(consumeRequest.getRemark());
		consumeEntity.setUid(consumeRequest.getUid());

		// 写入消耗总表
		consumeRepository.save(consumeEntity);
		
		// 扣减累积表积分
		subtractProduce(consumeRequest.getUid(), consumeRequest.getModuleId(), consumeRequest.getConsumeScore());

		// 更新用户积分
		userService.subScore(consumeRequest.getUid(), consumeRequest.getModuleId(), consumeRequest.getConsumeScore());
		userMongoService.subScore(consumeRequest.getUid(), consumeRequest.getModuleId(), consumeRequest.getConsumeScore());
		
		//写积分明细
		scoreMongoService.saveScoreDetail(consumeRequest.getUid(), consumeRequest.getModuleId(),consumeRequest.getConsumeChannel().getCode(), 
						ChannelTypeEnums.CONSUME.getCode(),  consumeRequest.getConsumeScore());
	}

	/**
	 * 从用户的累积表中扣减积分 1.积分不够的可以扣减成负数 2.若不存在有效的积分记录，则提示异常
	 * 
	 * @param uid
	 * @param moduleId
	 * @param score
	 */
	@Transactional
	private void subtractProduce(long uid, int moduleId, int score) {

		// 锁定，保证有效的积分列表剩余积分值正确,采用@version实现
		List<ProduceEntity> produceEntities = produceRepository.findValidProduce(uid, moduleId,
				ProduceStatusEnums.UNUSED.getCode(), Calendar.getInstance().getTimeInMillis());
		if (CollectionUtils.isEmpty(produceEntities)) {
			throw new BizException(ExceptionEnums.PRODUCE_INVALID);
		}

		// 待扣积分
		int waitSubTotal = score;
		for (int i = 0; i < produceEntities.size(); i++) {
			ProduceEntity produceEntity = produceEntities.get(i);

			if (waitSubTotal <= 0) {
				break;
			}

			// 最后一条记录,
			if ((i == produceEntities.size() - 1)) {
				updateProduce(produceEntity, waitSubTotal);
				break;
			}

			if (produceEntity.getRemainScore() >= waitSubTotal) {
				updateProduce(produceEntity, waitSubTotal);
				break;
			} else {
				// 剩余积分小于待扣积分，直接扣减,状态设置成used
				updateProduce(produceEntity, produceEntity.getRemainScore());
				waitSubTotal = waitSubTotal - produceEntity.getRemainScore();
			}
		}

	}

	@Transactional
	private void updateProduce(ProduceEntity produceEntity, int score) {
		if (produceEntity.getRemainScore() - score <= 0) {
			produceRepository.update(produceEntity.getId(), score, ProduceStatusEnums.USED.getCode());
			if (produceEntity.getRemainScore() < score) {
				log.info("用户累积明细扣减成负数 id:[{}]", produceEntity.getId());
			}
		} else {
			produceRepository.update(produceEntity.getId(), score, ProduceStatusEnums.UNUSED.getCode());
		}
	}

	@Override
	public int getProduceUnclaimedCount(long uid, int moduleId) {
		int channelCode = ProduceChannelEnums.ORDER.getCode();
		return produceRepository.countByUidAndModuleIdAndStatusAndProduceChannel(uid, moduleId, ProduceStatusEnums.UNCLAIMED.getCode(),channelCode);
	}

	@Override
	public int getScoreCurDay(long uid, int moduleId, int produceChannel) {
		int result = 0;
		Date start = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH);
		Date end = DateUtils.addDays(start, 1);
		List<ProduceEntity> produceEntities = produceRepository
				.findByUidAndModuleIdAndProduceChannelAndCTimeBetween(uid, moduleId, produceChannel,
						start.getTime(), end.getTime());
		if (!CollectionUtils.isEmpty(produceEntities)) {
			for (ProduceEntity produceEntity : produceEntities) {
				result = result + produceEntity.getProduceScore();
			}
		}
		
		return result;
	}

	@Override
	public ProduceEntity findProduceEntityByOrderId(String orderId) {
		return produceRepository.findByProduceBizidAndProduceChannel(orderId, ProduceChannelEnums.ORDER.getCode());
	}

	@Override
	public void updateProduceUnclaimedScore(ProduceEntity produceEntity, int subtractScore) {
		produceRepository.updateProduceScore(produceEntity.getId(), subtractScore);

	}

	@Override
	public int getProduceUnclaimedCount(long uid, int moduleId, int produceChannelCode) {
		return produceRepository.countByUidAndModuleIdAndStatusAndProduceChannel(uid, moduleId,
				ProduceStatusEnums.UNCLAIMED.getCode(), produceChannelCode);
	}

	@Override
	public int getProduceShareUnclaimedCount(long uid, int moduleId, int produceChannel) {
		return produceRepository.countByUidAndModuleIdAndStatusAndProduceChannel(uid, moduleId,
				ProduceStatusEnums.UNCLAIMED.getCode(), produceChannel);
	}

	@Override
	public int getProduceWatchUnclaimedCount(long uid, int moduleId, int produceChannel,int resultValue,int conditionValue) {
		List<ProduceEntity> produceEntities = produceRepository.findByUidAndModuleIdAndStatusAndProduceChannel(uid, moduleId, ProduceStatusEnums.UNCLAIMED.getCode(), produceChannel);
		if (CollectionUtils.isEmpty(produceEntities)) {
			return 0;
		} else {
			int score = 0;
			for(ProduceEntity pe:produceEntities) {
				score = score + pe.getProduceScore();
			}
			int result = (int)((score*conditionValue)/(resultValue*3600)) ;//时长，单位：小时
			return result;
		}
	}

	
	@Override
	@Transactional
	public void invalidScore(long date) {
		while (true) {
			List<ProduceEntity> produceEntities = produceRepository.findProduceEntityInvalidLimit(date, ProduceStatusEnums.UNUSED.getCode(), 1000);
			if (CollectionUtils.isEmpty(produceEntities)) {
				return;
			}
			for (ProduceEntity pe:produceEntities) {
				//更新状态
				produceRepository.updateProdeceEntityStatus(pe.getId(),ProduceStatusEnums.INVALID.getCode());
				
				//消费明细?
				
				// 更新用户积分
				userService.subScore(pe.getUid(), pe.getModuleId(), pe.getRemainScore());
				userMongoService.subScore(pe.getUid(), pe.getModuleId(), pe.getRemainScore());
				
				//写积分明细 
				scoreMongoService.saveScoreDetail(pe.getUid(), pe.getModuleId(), ConsumeChannelEnums.INVALID.getCode(), 
								ChannelTypeEnums.CONSUME.getCode(),  pe.getRemainScore());
			}
		}
	}
	

}
