package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreRequestMessage {
	
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private NoteFromCoreBody Body = new NoteFromCoreBody();
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public NoteFromCoreBody getBody() {
		return Body;
	}
	public void setBody(NoteFromCoreBody body) {
		Body = body;
	}
	
}
