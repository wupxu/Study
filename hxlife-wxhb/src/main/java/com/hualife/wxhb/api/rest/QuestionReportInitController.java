package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.QuestionRopertInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.QuestionReportInitResponseMessage;
import com.hualife.wxhb.service.QuestionReportInitService;
/**
 * @description: 问卷和报告初始化接口控制类
 * @author : linyongtao
 * @date : 2017.08.31
 */
@RestController
public class QuestionReportInitController {
	@Autowired
	private QuestionReportInitService questionReportInitService;
	@RequestMapping("/questionReportInit")
	public ResponseResult<QuestionReportInitResponseMessage> finaOccuSave(@RequestBody QuestionRopertInitRequestMessage questionRopertInitRequestMessage) {
		QuestionReportInitResponseMessage questionReportInitResponseMessage = questionReportInitService.questionReportInit(questionRopertInitRequestMessage);
		return ResponseResultUtil.success(questionReportInitResponseMessage);
	}
}
