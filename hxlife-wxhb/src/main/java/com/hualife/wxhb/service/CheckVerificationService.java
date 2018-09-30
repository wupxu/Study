package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.CheckVerificationRequestMessage;

/**
 * @author yangpeixin
 * @description 验证码校验
 * @date 2017-08-04
 */
public interface CheckVerificationService {
	/**
	 * 短信验证
	 **/
	public void checkVerification(CheckVerificationRequestMessage checkVerificationRequest);
}
