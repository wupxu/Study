package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.SendSmsRequestMessage;

/**
 * @author yangpeixin
 * @description 验证码发送
 * @date 2017-08-04
 */
public interface SendSmsServiece {
	/**
	 * 短信发送
	 **/
	public void sendSms(SendSmsRequestMessage sendSmsRequestMessage);
}
