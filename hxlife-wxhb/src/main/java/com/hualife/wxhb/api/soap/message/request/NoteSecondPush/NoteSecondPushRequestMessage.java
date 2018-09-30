package com.hualife.wxhb.api.soap.message.request.NoteSecondPush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;

@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteSecondPushRequestMessage {
	/**
	 *报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private NoteSecondPushBody body = new NoteSecondPushBody();
	
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public NoteSecondPushBody getBody() {
		return body;
	}
	public void setBody(NoteSecondPushBody body) {
		this.body = body;
	}
	
	
}
