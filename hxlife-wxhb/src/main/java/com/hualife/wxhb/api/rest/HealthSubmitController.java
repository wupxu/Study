package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.HealthSubmitRequestMessage;
import com.hualife.wxhb.service.HealthSubmitService;

/**
 * @author yangpeixin
 * @description 健康函提交
 * @date 2017-08-04
 */
@RestController
public class HealthSubmitController {

	@Autowired
	private HealthSubmitService healthSubmitService;

	/**
	 * 健康函提交接口
	 * 
	 * @param healthSubmitRequestMessage
	 * @return
	 */

	@RequestMapping(path = "/healthNoteSave")
	public ResponseResult<?> healthNoteSave(@RequestBody HealthSubmitRequestMessage healthSubmitRequestMessage) {
		healthSubmitService.healthNoteSave(healthSubmitRequestMessage);
		return ResponseResultUtil.success();
	}

}
