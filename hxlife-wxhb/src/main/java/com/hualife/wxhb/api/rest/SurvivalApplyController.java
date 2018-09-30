package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalApplyRequestMessage;
import com.hualife.wxhb.service.SurvivalApplyService;

/**
 * @description:契调任务申请：契调业务员对任务进行申请
 * @author : linyongtao
 * @date : 2017-08-04 
 */

@RestController
public class SurvivalApplyController {
	@Autowired
	private SurvivalApplyService survivalApplyService;

	@RequestMapping("/survivalTaskApply")
	public ResponseResult<?> survivalTaskApply(
			@RequestBody SurvivalApplyRequestMessage survivalApplyRequestMessage) {
		survivalApplyService.survivalStateUpdate(survivalApplyRequestMessage);
		return ResponseResultUtil.success();
	}
}
