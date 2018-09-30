package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SendSmsRequestMessage;
import com.hualife.wxhb.service.SendSmsServiece;

/**
 * @author yangpeixin
 * @description 验证码发送
 * @date 2017-08-04
 */
@RestController
public class SendSmsController {
	@Autowired
	private SendSmsServiece sendSmsServiece;

	/**
	 * 短信验证发送
	 * @param SendSmsControllerRequestMessage
	 * @return
	 */

	@RequestMapping(path = "/smsSendToClient")

	public ResponseResult<?> smsSendToClient(@RequestBody SendSmsRequestMessage sendSmsRequestMessage) {
		sendSmsServiece.sendSms(sendSmsRequestMessage);
		return ResponseResultUtil.success();
	}
}