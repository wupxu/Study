package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;

/**  
 * @Description: 核心推送函件信息报文
 * @author zhanglong 
 * @date 2017年9月7日 下午5:44:14  
 */
@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageRequestMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private PushMessageBody body = new PushMessageBody();

	public RequestHead getHead() {
		return head;
	}

	public void setHead(RequestHead head) {
		this.head = head;
	}

	public PushMessageBody getBody() {
		return body;
	}

	public void setBody(PushMessageBody body) {
		this.body = body;
	}
	
}
