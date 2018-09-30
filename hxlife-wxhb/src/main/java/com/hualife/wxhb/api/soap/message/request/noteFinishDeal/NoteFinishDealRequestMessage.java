package com.hualife.wxhb.api.soap.message.request.noteFinishDeal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
/**
 * @author zhangweiwei
 * @deprecation 核心回销消息请求报文
 * @date 2017-08-27
 */
@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFinishDealRequestMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private NoteFinishDealBody body = new NoteFinishDealBody();
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public NoteFinishDealBody getBody() {
		return body;
	}
	public void setBody(NoteFinishDealBody body) {
		this.body = body;
	}
	
}
