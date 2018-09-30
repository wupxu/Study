package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.RemoteSignatureRequestMessage;
import com.hualife.wxhb.api.rest.message.response.RemoteSignatureResponseMessage;
import com.hualife.wxhb.service.RemoteSignatureService;

/**
 * @author zhangweiwei
 * @description 投保人查看被保人的异地签名
 * @date 2017-09-11
 */
@RestController
public class RemoteSignatureController {
	
	@Autowired
	private RemoteSignatureService remoteSignatureService;
	
	@RequestMapping(path = "/remoteSignature")
	public ResponseResult<RemoteSignatureResponseMessage> remoteSignature(@RequestBody RemoteSignatureRequestMessage remoteSignatureRequestMessage) {
		RemoteSignatureResponseMessage remoteSignatureResponseMessage=remoteSignatureService.getRemoteSignature(remoteSignatureRequestMessage);
		return ResponseResultUtil.success(remoteSignatureResponseMessage);
	}
}
