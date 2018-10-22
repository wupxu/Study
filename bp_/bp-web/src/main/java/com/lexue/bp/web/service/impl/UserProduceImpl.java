package com.lexue.bp.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lexue.bp.common.domain.PageReply;
import com.lexue.bp.common.domain.request.ProduceRequest;
import com.lexue.bp.common.entity.DataShareEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.entity.mongo.ScoreMongoEntity;
import com.lexue.bp.common.entity.mongo.UnclaimedScore;
import com.lexue.bp.common.entity.mongo.UserMongoEntity;
import com.lexue.bp.common.enums.ChannelTypeEnums;
import com.lexue.bp.common.enums.ConsumeChannelEnums;
import com.lexue.bp.common.enums.DataStatusEnums;
import com.lexue.bp.common.enums.ExceptionEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.RuleStatusEnums;
import com.lexue.bp.common.exception.BizException;
import com.lexue.bp.common.repository.DataShareRepository;
import com.lexue.bp.common.service.CoreService;
import com.lexue.bp.common.service.ScoreMongoService;
import com.lexue.bp.common.service.UserMongoService;
import com.lexue.bp.common.util.CommonUtil;
import com.lexue.bp.web.inf.request.DataShareRequest;
import com.lexue.bp.web.inf.response.ScoreDetailResponse;
import com.lexue.bp.web.inf.response.UnclaimedProduceResponse;
import com.lexue.bp.web.service.UserProduceService;
import com.lexue.bp.web.service.WebRuleService;


@Service
public class UserProduceImpl implements UserProduceService {

	@Autowired
	private DataShareRepository dataShareRepository;
	@Autowired
	private WebRuleService ruleService;
	@Autowired
	private CoreService coreService;
	@Autowired
	private UserMongoService userMongoService;
	@Autowired
	private ScoreMongoService scoreMongoService;


	@Override
	public void addDataShareEntity(DataShareRequest dsr, long uid, int moduleId) {
		RuleEntity rn = ruleService.getByRuleIdAndModuleId(ProduceChannelEnums.SHARE.getCode(),moduleId);
		if(rn!=null) {
			if(rn.getStatus()==RuleStatusEnums.DISABLE.getCode()) {
				return;
			}
		}
		DataShareEntity dataShareEntity = dataShareRepository.findByUidAndBusinessId(uid, dsr.getBusinessId());
		if (dataShareEntity == null) {
			DataShareEntity result = new DataShareEntity();
			result.setCTime(Calendar.getInstance().getTimeInMillis());
			result.setId(CommonUtil.generateId());
			result.setModuleId(moduleId);
			result.setBusinessId(dsr.getBusinessId());
			result.setShareTime(Calendar.getInstance().getTimeInMillis());
			result.setStatus(DataStatusEnums.PENDING.getCode());
			result.setUid(uid);
			dataShareRepository.save(result);
		} else {
			throw new BizException(ExceptionEnums.SHARE);
		}

	}

	@Override
	public UserMongoEntity receiveScoreByChannel(long uid, int moduleId, int produceChannelCode) {
		coreService.UnclaimedToUnusedScore(uid, moduleId, ProduceChannelEnums.parser(produceChannelCode));
		return findUserInfo(uid, moduleId);
	}

	@Override
	public UserMongoEntity findUserInfo(long uid, int moduleId) {
		UserMongoEntity userMongoEntity = userMongoService.findByUidAndModuleId(uid, moduleId);
		return userMongoEntity;
	}

	@Override
	public List<UnclaimedProduceResponse> getUnclaimedScore(long uid, int moduleId) {
		UserMongoEntity userMongoEntity = findUserInfo(uid,moduleId);
		List<UnclaimedProduceResponse> unclaimedProduceResponseList = new ArrayList<>();
		if (CollectionUtils.isEmpty(userMongoEntity.getScores())) {
			return unclaimedProduceResponseList;
		}
		for (UnclaimedScore unclaimedScore : userMongoEntity.getScores()) {
			if (unclaimedScore.getScore() <= 0) {
				continue;
			}
			
			RuleEntity re = null;
			if (unclaimedScore.getChannelCode()==ProduceChannelEnums.POSTHOT.getCode()) {
				re = ruleService.getByRuleIdAndModuleId(ProduceChannelEnums.POST.getCode(),moduleId);
			}else {
				re = ruleService.getByRuleIdAndModuleId(unclaimedScore.getChannelCode(),moduleId);
			}
			UnclaimedProduceResponse unclaimedProduceResponse = new UnclaimedProduceResponse();
			if (re==null) {
				unclaimedProduceResponse.setProduceChannel(ProduceChannelEnums.parser(unclaimedScore.getChannelCode()).getName());// 渠道类型名称
				unclaimedProduceResponse.setProduceChannelCode(ProduceChannelEnums.parser(unclaimedScore.getChannelCode()).getCode());
				unclaimedProduceResponse.setProduceScore(unclaimedScore.getScore());
			}else {
				if (re.getStatus()==RuleStatusEnums.DISABLE.getCode()) {
					unclaimedProduceResponse=null;
				}else {
					if (unclaimedScore.getChannelCode()==ProduceChannelEnums.POSTHOT.getCode()) {
						unclaimedProduceResponse.setProduceChannel(ProduceChannelEnums.parser(unclaimedScore.getChannelCode()).getName());// 渠道类型名称
					}else {
						unclaimedProduceResponse.setProduceChannel(re.getRuleName());// 渠道类型名称
					}
					unclaimedProduceResponse.setProduceChannelCode(unclaimedScore.getChannelCode());
					unclaimedProduceResponse.setProduceScore(unclaimedScore.getScore());
				}
			}
			
			
			unclaimedProduceResponseList.add(unclaimedProduceResponse);
		}
		return unclaimedProduceResponseList;
	}
			
	@Override
	public PageReply<ScoreDetailResponse> userScoreDetail(long uid, int moduleId, int pageNumber, int pageSize) {
		PageReply<ScoreDetailResponse> result = new PageReply<ScoreDetailResponse>();
		Page<ScoreMongoEntity> scoreMongoPage = scoreMongoService.findByUidAndModuleId(uid, moduleId, pageNumber, pageSize);
		List<ScoreDetailResponse> datas = new ArrayList<>();
		if (!CollectionUtils.isEmpty(scoreMongoPage.getContent())) {
			for(ScoreMongoEntity scoreMongoEntity:scoreMongoPage.getContent()) {
				ScoreDetailResponse sdr = new ScoreDetailResponse();
				sdr.setEffectiveDate(scoreMongoEntity.getCTime());
				if (scoreMongoEntity.getChannelType() == ChannelTypeEnums.PRODUCE.getCode()) {
					sdr.setProduceChannelName(ProduceChannelEnums.parser(scoreMongoEntity.getChannelCode()).getName());
					sdr.setProduceScore(scoreMongoEntity.getScore());
				} else if (scoreMongoEntity.getChannelType() == ChannelTypeEnums.CONSUME.getCode()) {
					sdr.setProduceChannelName(ConsumeChannelEnums.parser(scoreMongoEntity.getChannelCode()).getName());
					sdr.setProduceScore(-scoreMongoEntity.getScore());
				} else {
					continue;
				}
				datas.add(sdr);
			}
		}
		result.setNext(scoreMongoPage.hasNext());
		result.setNumber(scoreMongoPage.getNumber());
		result.setPrevious(scoreMongoPage.hasPrevious());
		result.setSize(scoreMongoPage.getSize());
		result.setTotalElements(scoreMongoPage.getTotalElements());
		result.setTotalPages(scoreMongoPage.getTotalPages());
		result.setDatas(datas);
		return result;
	}

	@Override
	public void addScore(ProduceRequest produceRequest) {
		if (produceRequest == null) {
			throw new BizException(ExceptionEnums.ARGS_ERROR);
		}
		coreService.addUnusedScore(produceRequest);
	}

}

