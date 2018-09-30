package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.CheckVerificationRequestMessage;
import com.hualife.wxhb.service.CheckVerificationService;

/**
 * @author yangpeixin
 * @description 验证码校验
 * @date 2017-08-04
 */
@RestController
public class CheckVerificationController {

	@Autowired
	private CheckVerificationService checkVerificationService;

	/**
	 * 短信验证码接口
	 * 
	 * @param CheckVerificationRequestMessage
	 * @return
	 */

	@RequestMapping(path = "/smsCheck")

	public ResponseResult<?> smsCheck(@RequestBody CheckVerificationRequestMessage checkVerificationRequestMessage) {
		checkVerificationService.checkVerification(checkVerificationRequestMessage);
		return ResponseResultUtil.success();
	}
}