package com.hualife.wxhb.integration.soap.message.response.chooseTypePush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.response.common.ResponseHead;

@XmlRootElement(name = "OutputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChooseTypePushReponseMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private ResponseHead head = new ResponseHead();
	
	/**
	 * 报文头
	 */
	@XmlElement(name="Body")
	private ChooseTypePushReponseBody body  = new ChooseTypePushReponseBody();

	public ResponseHead getHead() {
		return head;
	}

	public void setHead(ResponseHead head) {
		this.head = head;
	}

	public ChooseTypePushReponseBody getBody() {
		return body;
	}

	public void setBody(ChooseTypePushReponseBody body) {
		this.body = body;
	}
}
