package com.lexue.bp.web.service;

import java.util.List;

import com.lexue.bp.common.entity.RuleEntity;

public interface WebRuleService {
	
	RuleEntity saveRule(RuleEntity ruleEntity);
	
	List<RuleEntity> getAllRule();

	/**
	 * 存在且有效，否则抛出异常
	 * @param ruleId
	 * @return
	 */
	RuleEntity getByRuleId(int ruleId);
	
//	List<RuleEntity> getByRuleId(int ruleId);

	List<RuleEntity> getRuleByModuleId(int moduleId);

	RuleEntity getByRuleIdAndModuleId(int channelCode, int moduleId);


}