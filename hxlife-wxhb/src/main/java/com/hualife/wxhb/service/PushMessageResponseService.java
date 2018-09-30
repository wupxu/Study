package com.hualife.wxhb.service;

import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseBody;

/** 
 * @author 吴培旭 
 * @description 批处理推送函件信息处理核心返回报文的接口
 * @time 创建时间：2017年8月22日   
 */
public interface PushMessageResponseService {
	/** 
	 * @author 吴培旭 
	 * @description 批处理推送函件信息处理核心返回报文的接口
	 * @time 创建时间：2017年8月22日   
	 */
	public void handlMessage(PushMessageResponseBody pushResponseMessageBody);
}
