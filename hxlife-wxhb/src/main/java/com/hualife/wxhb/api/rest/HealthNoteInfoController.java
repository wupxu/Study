package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.HealthNoteInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.HealthNoteInitResponseMessage;
import com.hualife.wxhb.service.HealthNoteInfoService;

/**
 * @description 健康函初始化
 * @author wangt 
 * @date 2017-08-08
 */
@RestController
public class HealthNoteInfoController {

	@Autowired
	private HealthNoteInfoService healthNoteInfoService;
	
	@RequestMapping("/healthNoteInfo")
	public ResponseResult<HealthNoteInitResponseMessage> healthNoteInfo(@RequestBody HealthNoteInitRequestMessage healthNoteInitRequestMessage) {
		HealthNoteInitResponseMessage healthNoteInitResponseMessage =healthNoteInfoService.healthNoteInfo(healthNoteInitRequestMessage);		
		return ResponseResultUtil.success(healthNoteInitResponseMessage);
		
	}
}
