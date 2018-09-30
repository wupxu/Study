package com.hualife.wxhb.api.soap.message.response.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OutputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonResponseMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private ResponseHead head = new ResponseHead();
	
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private ResponseBody Body = new ResponseBody();
	
	public ResponseHead getHead() {
		return head;
	}

	public void setHead(ResponseHead head) {
		this.head = head;
	}

	public ResponseBody getBody() {
		return Body;
	}

	public void setBody(ResponseBody body) {
		Body = body;
	}
	
}
