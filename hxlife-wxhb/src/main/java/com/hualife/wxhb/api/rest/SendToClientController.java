package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SendToClientRequestMessage;
import com.hualife.wxhb.service.SendToClientService;
/**
 * @author 张卫卫
 * @description 转发给客户
 * @date 2017-08-08
 */
@RestController
public class SendToClientController {
	
	@Autowired
	private SendToClientService sendToClientService;
	
	@RequestMapping("/sendToClient")
	public ResponseResult<?> sendToClient(@RequestBody SendToClientRequestMessage  sendToClientRequestMessage){
		sendToClientService.sendToCLient(sendToClientRequestMessage);
        return ResponseResultUtil.success();
	}
}
