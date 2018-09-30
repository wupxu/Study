package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.RemoteSignatureRequestMessage;
import com.hualife.wxhb.api.rest.message.response.RemoteSignatureResponseMessage;
/**
 * @author zhangweiwei
 * @deprecation 投保人查看被保人的异地签名Service
 * @date 2017-09-11
 */
public interface RemoteSignatureService {

	public RemoteSignatureResponseMessage getRemoteSignature(RemoteSignatureRequestMessage remoteSignatureRequestMessage);

}
