package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.NoteStatusInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteStatusInfoResponseMessage;
import com.hualife.wxhb.service.NoteStatusInfoService;


/**
 * @author zhangweiwei
 * @desrecation 查询代理人函件状态
 * @date 2017-08-04
 */
@RestController
public class NoteStatusInfoController {
	
	@Autowired
	private NoteStatusInfoService noteStatusInfoService;
	
	@RequestMapping("/noteStatusInfo")
    public ResponseResult<NoteStatusInfoResponseMessage> noteStatusInfo(@RequestBody NoteStatusInfoRequestMessage noteStatusInfoRequestMessage) {	    
		NoteStatusInfoResponseMessage noteStatusInfoResponseMessage = noteStatusInfoService.getNoteStatus(noteStatusInfoRequestMessage);
    	return ResponseResultUtil.success(noteStatusInfoResponseMessage);
    }
}
