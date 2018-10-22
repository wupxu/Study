package com.lexue.bp.admin.inf.spec;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lexue.bp.admin.inf.domain.req.RuleRequest;
import com.lexue.bp.admin.inf.domain.res.RuleResponse;
import com.lexue.bp.common.domain.ResponseDto;


@FeignClient(name="bp-admin-${platform_type}")
public interface RuleServiceSpec {
	
	/**
	 * 获取所有规则
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/rule/all")
	ResponseDto<List<RuleResponse>> getAllRules(@RequestParam(value = "moduleId",defaultValue = "8193") int moduleId);
	
	/**
	 * 根据规则编号，获取指定规则
	 * @param ruleId
	 * @return
	 */
	@GetMapping(value="/bp/admin/v1/rule/byId")
	ResponseDto<RuleResponse> getRuleById(@RequestParam("ruleId") int ruleId,@RequestParam(value = "moduleId",defaultValue = "8193") int moduleId);
	
	
	/**
	 * 保存规则
	 * @param ruleRequest
	 * @return
	 */
	@PostMapping(value="/bp/admin/v1/rule/save")
	ResponseDto<Void> saveRule(@RequestBody RuleRequest ruleRequest);	
	
	
}
