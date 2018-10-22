package com.lexue.bp.admin.inf.spec;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lexue.bp.admin.inf.domain.res.RuleResponse;
import com.lexue.bp.common.domain.ResponseDto;

public interface TestSpec {
	
	@GetMapping(value="/bp/admin/v1/rule/order")
	ResponseDto<Void> getOrder(@RequestParam(value = "date") String date);
	
	@GetMapping(value="/bp/admin/v1/rule/watch")
	ResponseDto<Void> getWatch(@RequestParam(value = "date") String date);
	
	@GetMapping(value="/bp/admin/v1/rule/post")
	ResponseDto<Void> getPost(@RequestParam(value = "date") String date);
	
	@GetMapping(value="/bp/admin/v1/rule/postHot")
	ResponseDto<Void> getPostHot(@RequestParam(value = "date") String date);
	
}
