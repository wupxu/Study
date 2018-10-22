package com.lexue.bp.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.lexue.bp.admin.inf.domain.req.RuleRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.admin.inf.domain.res.RuleResponse;
import com.lexue.bp.admin.service.RuleService;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.properties.BPProperties;
import com.lexue.bp.common.repository.RuleRepository;

@Service
public class RuleImpl implements RuleService {
	
	@Autowired
	private RuleRepository ruleRepository;
	@Autowired
	@Qualifier("bp-config")
	private BPProperties bPProperties;

	@Override
	@Transactional
	public RuleEntity saveRule(RuleRequest ruleRequest) {
		RuleEntity entity = getByRuleIdAndModuleId(ruleRequest.getProduceChannelEnums().getCode(),ruleRequest.getModuleId());
		if(entity == null){
			entity = new RuleEntity();
			entity.setRuleId(ruleRequest.getProduceChannelEnums().getCode());
		}
		RuleEntity ruleEntity = ruleRepository.findByRuleIdAndModuleIdAndStatus(ruleRequest.getProduceChannelEnums().getCode(), ruleRequest.getModuleId(),ruleRequest.getRuleStatusEnums().getCode());
		if (ruleRequest.getImageUrl()==null ||ruleRequest.getImageUrl()=="") {
			ruleRequest.setImageUrl(ruleEntity.getImageUrl());
		}else {
			String imageUrl = ruleRequest.getImageUrl().replace(bPProperties.getPrefix(),bPProperties.getPrefix());
			if (imageUrl.indexOf(bPProperties.getPrefix())==-1) {
				ruleRequest.setImageUrl(bPProperties.getPrefix()+imageUrl);
			}else {
				ruleRequest.setImageUrl(imageUrl);
			}
		}
		BeanUtils.copyProperties(ruleRequest,entity);
		entity.setStatus(ruleRequest.getRuleStatusEnums().getCode());
		entity.setUpdateTime(Calendar.getInstance().getTimeInMillis());

		return ruleRepository.save(entity);
	}
	
	@Override
	public List<RuleResponse> getAllRule(int moduleId) {
		Sort sort = new Sort("ruleId");
		List<RuleEntity> findAll = ruleRepository.findByModuleId(moduleId,sort);
		List<RuleResponse> rsList = new ArrayList<>();
		for (RuleEntity ruleEntity : findAll) {
			RuleResponse rResponse = new RuleResponse();
			rResponse.setRuleId(ruleEntity.getRuleId());
			rResponse.setRuleName(ruleEntity.getRuleName());
			rResponse.setStatus(ruleEntity.getStatus());
			rResponse.setUpdateTime(ruleEntity.getUpdateTime());
			rResponse.setOperatorName(ruleEntity.getOperatorName());
			rResponse.setOperatorId(ruleEntity.getOperatorId());
			rsList.add(rResponse);
		}
		return rsList;
	}

	@Override
	public RuleResponse getRuleResponse(int ruleId,int moduleId) {
		RuleEntity ruleEntity = getByRuleIdAndModuleId(ruleId,moduleId);
		RuleResponse rResponse = new RuleResponse();
		if(ruleEntity == null){
			rResponse.setRuleId(ruleId);
		}else{
			rResponse.setRuleId(ruleEntity.getRuleId());
			rResponse.setRuleName(ruleEntity.getRuleName());
			rResponse.setStatus(ruleEntity.getStatus());
			rResponse.setUpdateTime(ruleEntity.getUpdateTime());
			rResponse.setConditionValue(ruleEntity.getConditionValue());
			rResponse.setImageUrl(ruleEntity.getImageUrl());
			rResponse.setMaxCount(ruleEntity.getMaxCount());
			rResponse.setMaxScore(ruleEntity.getMaxScore());
			rResponse.setHotValue(ruleEntity.getHotValue());
			rResponse.setResultValue(ruleEntity.getResultValue());
			rResponse.setStrategySpecification(ruleEntity.getStrategySpecification());
			rResponse.setTaskSpecification(ruleEntity.getTaskSpecification());
			rResponse.setOperatorId(ruleEntity.getOperatorId());
			rResponse.setOperatorName(ruleEntity.getOperatorName());
		}


		return rResponse;
	}
	
	@Override
	public RuleEntity getByRuleIdAndModuleId(int ruleId,int moduleId){
		RuleEntity ruleEntity = ruleRepository.findByRuleIdAndModuleId(ruleId,moduleId);
		return ruleEntity;
	}

@Override
	public List<RuleEntity> getByRuleId(int ruleId) {
		List<RuleEntity> ruleEntityList = ruleRepository.findByRuleId(ruleId);
		return ruleEntityList;
	}
}
