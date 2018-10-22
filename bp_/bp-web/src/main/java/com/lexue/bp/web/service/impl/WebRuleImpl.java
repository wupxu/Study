package com.lexue.bp.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.enums.ExceptionEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.enums.RuleStatusEnums;
import com.lexue.bp.common.exception.BizException;
import com.lexue.bp.common.repository.RuleRepository;
import com.lexue.bp.web.service.WebRuleService;

@Service
public class WebRuleImpl implements WebRuleService {
	
	@Autowired
	private RuleRepository ruleRepository;

	@Override
	@Transactional
	public RuleEntity saveRule(RuleEntity ruleEntity) {
		return ruleRepository.save(ruleEntity);
	}

	@Override
	public List<RuleEntity> getAllRule() {
		Sort sort = new Sort("ruleId");
		return ruleRepository.findAll(sort);
	}

	@Override
	public RuleEntity getByRuleId(int ruleId) {
		RuleEntity ruleEntity = ruleRepository.findOne(ruleId);
		if (ruleEntity == null) {
			throw new BizException(ExceptionEnums.NOT_FOUND,"编号："+ProduceChannelEnums.ORDER.getCode());
		} else if (ruleEntity.getStatus() == RuleStatusEnums.DISABLE.getCode()) {
			throw new BizException(ExceptionEnums.RULE_DISABLE);
		}
		return ruleEntity;
	}

	@Override
	public List<RuleEntity> getRuleByModuleId(int moduleId) {
		Sort sort = new Sort("ruleId");
		List<RuleEntity> rlList = ruleRepository.findByModuleIdAndStatus(moduleId,RuleStatusEnums.ENABLE.getCode(),sort);
		return rlList;
	}

	@Override
	public RuleEntity getByRuleIdAndModuleId(int ruleId, int moduleId) {
		RuleEntity re = ruleRepository.findByRuleIdAndModuleId(ruleId, moduleId);
		return re;
	}

	



}