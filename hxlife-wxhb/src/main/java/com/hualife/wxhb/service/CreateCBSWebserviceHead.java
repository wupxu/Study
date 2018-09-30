package com.hualife.wxhb.service;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;

/**  
 * @Description: 组织访问核心请求报文的报文头Service
 * @author zhanglong 
 * @date 2017年9月1日 下午2:12:05  
 */
public interface CreateCBSWebserviceHead {
	RequestHead createCBSWebserviceHead(String ServiceID);
}
