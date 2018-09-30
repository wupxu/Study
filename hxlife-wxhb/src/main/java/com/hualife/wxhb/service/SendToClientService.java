package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.SendToClientRequestMessage;
/**
 * @author 张卫卫
 * @description 转发给客户service
 * @date 2017-08-08
 */
public interface SendToClientService {
	/**
	 * 转发给客户
	 */
	public void sendToCLient(SendToClientRequestMessage sendToClientRequestMessage);
}
