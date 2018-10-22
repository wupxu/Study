package com.lexue.bp.admin.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexue.bp.admin.inf.domain.req.RuleRequest;
import com.lexue.bp.admin.inf.domain.res.RuleResponse;
import com.lexue.bp.admin.inf.spec.RuleServiceSpec;
import com.lexue.bp.admin.service.RuleService;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.util.ResponseUtil;

@RestController("ruleControllerV1")
public class RuleController implements RuleServiceSpec {
	
	@Autowired
	private RuleService ruleService;

	@Override
	public ResponseDto<List<RuleResponse>> getAllRules(@RequestParam(defaultValue = "8193") int moduleId) {
		List<RuleResponse> ruleEntities = ruleService.getAllRule(moduleId);
		return ResponseUtil.generateSuccessResponse(ruleEntities);
	}

	@Override
	public ResponseDto<RuleResponse> getRuleById(@RequestParam("ruleId") int ruleId,@RequestParam(defaultValue = "8193") int moduleId) {
		RuleResponse ruleResponse = ruleService.getRuleResponse(ruleId,moduleId);
		return ResponseUtil.generateSuccessResponse(ruleResponse);
	}

	@Override
	public ResponseDto<Void> saveRule(@RequestBody RuleRequest ruleRequest) {
		ruleService.saveRule(ruleRequest);
		return ResponseUtil.generateSuccessResponse();
	}

}
