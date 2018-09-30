package com.hualife.wxhb.integration.soap.message.request.notePrintPush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class NotePrintPushRequestMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private NotePrintPushBody body = new NotePrintPushBody();

	public RequestHead getHead() {
		return head;
	}

	public void setHead(RequestHead head) {
		this.head = head;
	}

	public NotePrintPushBody getBody() {
		return body;
	}

	public void setBody(NotePrintPushBody body) {
		this.body = body;
	}
	
}
