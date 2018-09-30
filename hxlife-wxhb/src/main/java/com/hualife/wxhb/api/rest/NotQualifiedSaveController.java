package com.hualife.wxhb.api.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.NotQualifiedSaveRequestMessage;
import com.hualife.wxhb.service.NotQualifiedSaveService;
/**
 * @description : 次品单提交
 * @author : linyongtao
 * @date : 2017-08-22 16:36:00
 */
@RestController
public class NotQualifiedSaveController {
	@Autowired
	private NotQualifiedSaveService cNotQualifiedSaveService;
	@RequestMapping("/notQualifiedSave")
	public ResponseResult<?> notQualifiedSave(@RequestBody NotQualifiedSaveRequestMessage notQualifiedSaveRequestMessage){
		cNotQualifiedSaveService.notQualifiedSave(notQualifiedSaveRequestMessage);		
		return ResponseResultUtil.success();
	}	
}
