package com.hualife.wxhb.service.Impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
import com.hualife.wxhb.api.soap.message.request.common.RequestMsg;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.service.CreateCBSWebserviceHead;
import com.hualife.wxhb.service.GetMaxNo;

/**  
 * @Description: 组织访问核心请求报文的报文头Impl
 * @author zhanglong 
 * @date 2017年9月1日 下午2:22:07  
 */
@Service
public class CreateCBSWebserviceHeadImpl implements CreateCBSWebserviceHead {
	
	@Autowired
	private GetMaxNo getMaxNo;
	@Override
	public RequestHead createCBSWebserviceHead(String ServiceID) {
		RequestHead requestHead = new RequestHead();
		requestHead.setVersion("1.0");//
		requestHead.setCn2utf8("1");//
		
		RequestMsg requestMsg = new RequestMsg();//
		requestMsg.setServiceID(ServiceID);//服务id，报文编号，交易码
		requestMsg.setServiceType("");//服务/交易类型
		requestMsg.setCallType("SYN");// 调用模式    SYN同步，ASYN异步
		requestMsg.setTransNo(getMaxNo.getMaxNo());//交易流水号
		InetAddress address;
		String clientIP = "";
		try {
			address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
			clientIP = address.getHostAddress();//192.168.0.121
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		requestMsg.setClientIP(clientIP);//客户端IP
		requestMsg.setFromSystemkey(Constant.enterprise_QUERY_USER_INFOR_TRADE_SOURCE);//服务发起方应用系统编号
		requestMsg.setPassword("");//请求密码
		requestMsg.setTransDate(DateFormatUtil.formatDate("yyyyMMdd", new Date()));//发起日期
		requestMsg.setTransTime(DateFormatUtil.formatDate("HHmmss", new Date()));//发起时间
		requestMsg.setToSystemKey("CBS");//服务接收方应用系统编号
		requestMsg.setEffectiveTime("");//有效时间
		requestHead.setMsg(requestMsg);
		requestHead.setExt("");
		
		return requestHead;
	}

}
