package com.lexue.bp.admin.service;


import java.util.List;

import com.lexue.bp.admin.inf.domain.req.RuleRequest;
import com.lexue.bp.admin.inf.domain.res.RuleResponse;
import com.lexue.bp.common.entity.RuleEntity;

public interface RuleService {
	
	RuleEntity saveRule(RuleRequest ruleRequest);
	
	List<RuleResponse> getAllRule(int moduleId);

	/**
	 * 存在且有效，否则抛出异常
	 * @param ruleId
	 * @return
	 */
	RuleResponse getRuleResponse(int ruleId,int moduleId);
	

	RuleEntity getByRuleIdAndModuleId(int ruleId,int moduleId);

	List<RuleEntity> getByRuleId(int ruleId);


}
