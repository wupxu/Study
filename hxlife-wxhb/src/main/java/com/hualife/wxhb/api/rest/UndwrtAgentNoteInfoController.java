package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.UndwrtAgentNoteInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.UndwrtAgentNoteInfoResponseMessage;
import com.hualife.wxhb.service.UndwrtAgentNoteInfoService;

/**
 * @description 核保涵初始化
 * @author wangt
 * @date 2017-08-09
 */
@RestController
public class UndwrtAgentNoteInfoController {

	@Autowired
	private UndwrtAgentNoteInfoService undwrtAgentNoteInfoService;

	@RequestMapping("/undwrtAgentNoteInfo")
	public ResponseResult<UndwrtAgentNoteInfoResponseMessage> undwrtAgentNoteInfo(@RequestBody UndwrtAgentNoteInfoRequestMessage undwrtAgentNoteInfoRequestMessage) {
		UndwrtAgentNoteInfoResponseMessage undwrtAgentNoteInfoResponseMessage = undwrtAgentNoteInfoService.undwrtAgentNoteInfo(undwrtAgentNoteInfoRequestMessage);
		return ResponseResultUtil.success(undwrtAgentNoteInfoResponseMessage);

	}

}
