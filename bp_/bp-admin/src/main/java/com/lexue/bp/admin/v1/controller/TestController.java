package com.lexue.bp.admin.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lexue.bp.admin.inf.domain.req.RuleRequest;
import com.lexue.bp.admin.inf.domain.res.RuleResponse;
import com.lexue.bp.admin.inf.spec.RuleServiceSpec;
import com.lexue.bp.admin.inf.spec.TestSpec;
import com.lexue.bp.admin.service.RuleService;
import com.lexue.bp.admin.service.TaskService;
import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.util.ResponseUtil;

@RestController("testControllerV1")
public class TestController implements TestSpec {
	
	@Autowired
	private TaskService taskService;

	@Override
	public ResponseDto<Void> getOrder(String date) {
		taskService.order(date);
		return ResponseUtil.generateSuccessResponse();
	}

	@Override
	public ResponseDto<Void> getWatch(String date) {
		taskService.watch(date);
		return ResponseUtil.generateSuccessResponse();
	}

	@Override
	public ResponseDto<Void> getPost(String date) {
		taskService.post(date);
		return ResponseUtil.generateSuccessResponse();
	}

	@Override
	public ResponseDto<Void> getPostHot(String date) {
		taskService.postHot(date);
		return ResponseUtil.generateSuccessResponse();
	}

}
