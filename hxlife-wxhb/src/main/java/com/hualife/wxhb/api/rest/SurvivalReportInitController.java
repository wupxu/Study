package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalReportInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalReportInitResponseMessage;
import com.hualife.wxhb.service.SurvivalReportInitService;

/**
 * @descrption : 契调报告初始化接口
 * @author : linyongtao
 * @date : 2017-08-19
 */
@RestController
public class SurvivalReportInitController {
	@Autowired
	private SurvivalReportInitService survivalReportInitservice;

	@RequestMapping("/survivalReportInit")
	public ResponseResult<SurvivalReportInitResponseMessage> survivalReportInit(
			@RequestBody SurvivalReportInitRequestMessage survivalReportInitRequestMessage) {
		SurvivalReportInitResponseMessage survivalReportInitResponseMessage = survivalReportInitservice
				.selectSurvivalReport(survivalReportInitRequestMessage);
		return ResponseResultUtil.success(survivalReportInitResponseMessage);
	}

}
