package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalNoteTaskInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalNoteTaskInfoResponseMessage;
import com.hualife.wxhb.service.SurvivalNoteTaskInfoService;

/**
 * @description 契调任务初始化
 * @author wangt
 * @date 2017-08-07
 */
@RestController
public class SurvivalNoteTaskInfoController {

	@Autowired
	private SurvivalNoteTaskInfoService survivalNoteTaskInfoService;

	@RequestMapping("/survivalNoteTaskInfo")
	public ResponseResult<SurvivalNoteTaskInfoResponseMessage> survivalNoteTaskInfo(@RequestBody SurvivalNoteTaskInfoRequestMessage survivalNoteTaskInfoRequestMessage) {
		SurvivalNoteTaskInfoResponseMessage survivalNoteTaskInfoResponseMessage = survivalNoteTaskInfoService.survivalNoteTaskInfo(survivalNoteTaskInfoRequestMessage);
		return ResponseResultUtil.success(survivalNoteTaskInfoResponseMessage);

	}
}
