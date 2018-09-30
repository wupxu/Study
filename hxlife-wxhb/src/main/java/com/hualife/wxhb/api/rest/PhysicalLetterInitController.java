package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;   
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.PhysicalLetterInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.PhysicalLetterInitResponseMessage;
import com.hualife.wxhb.service.PhysicalLetterService;

/**
 * @description : 体检函初始化控制类
 * @author : linyongtao
 * @date : 2017-08-04 
 */
@RestController
public class PhysicalLetterInitController {
	@Autowired
	private PhysicalLetterService physicalLetterService;
	
	@RequestMapping("/physicalNoteInfo")
	public 	ResponseResult<PhysicalLetterInitResponseMessage> physicalNoteInfo(@RequestBody PhysicalLetterInitRequestMessage physicalLetterInitRequestMessage){
		PhysicalLetterInitResponseMessage physicalLetterInitResponseMessage = physicalLetterService.selectPhysicalLetter(physicalLetterInitRequestMessage);		
		return ResponseResultUtil.success(physicalLetterInitResponseMessage);		
	}
}
