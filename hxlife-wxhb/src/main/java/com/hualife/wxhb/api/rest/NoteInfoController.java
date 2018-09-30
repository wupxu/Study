package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.NoteInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteInfoResponseMessage;
import com.hualife.wxhb.service.NoteInfoService;

/**
 * @description:函件初始化控制类
 * @author yangpeixin
 * @date 2017-08-04
 */
@RestController
public class NoteInfoController {
	@Autowired
	private NoteInfoService noteInfoService;

	/**
	 * 函件初始化
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(path = "/noteClientInfo")
	public ResponseResult<NoteInfoResponseMessage> noteClientInfo(@RequestBody NoteInfoRequestMessage noteInfoRequestMessage) {
		NoteInfoResponseMessage noteInfoResponseMessage = noteInfoService.noteClientInfo(noteInfoRequestMessage);
		return ResponseResultUtil.success(noteInfoResponseMessage);
	}
}