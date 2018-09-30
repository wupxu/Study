package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalTaskRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalTaskResponseMessage;
import com.hualife.wxhb.service.SurvivalTaskInfoService;
/**
 * @author zhangweiwei
 * @deprecation 契调函报告初始化
 * @date 2017-08-05
 */
@RestController
public class SurvivalTaskInfoController {
	
	@Autowired
	private SurvivalTaskInfoService survivalTaskInfoService;
	
	@RequestMapping("/survivalTaskInfo")
	public ResponseResult<SurvivalTaskResponseMessage> survivalTaskInfo(@RequestBody SurvivalTaskRequestMessage survivalTaskRequestMessage) {	    
		SurvivalTaskResponseMessage survivalTaskResponseMessage=survivalTaskInfoService.getImageList(survivalTaskRequestMessage);
	    return ResponseResultUtil.success(survivalTaskResponseMessage);
	}
	
}
