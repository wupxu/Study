package com.hualife.wxhb.api.soap.message.request.noteNotQualified;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteNotQualifiedRequestMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private NoteNotQualifiedBody body = new NoteNotQualifiedBody();
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public NoteNotQualifiedBody getBody() {
		return body;
	}
	public void setBody(NoteNotQualifiedBody body) {
		this.body = body;
	}
	
}
