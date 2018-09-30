package com.hualife.wxhb.integration.soap.message.response.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.response.common.ResponseHead;

/**  
 * @Description: 函件信息推送返回信息
 * @author zhanglong 
 * @date 2017年9月7日 下午6:07:50  
 */
@XmlRootElement(name = "OutputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private ResponseHead head = new ResponseHead();
	
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private PushMessageResponseBody body = new PushMessageResponseBody();

	public ResponseHead getHead() {
		return head;
	}

	public void setHead(ResponseHead head) {
		this.head = head;
	}

	public PushMessageResponseBody getBody() {
		return body;
	}

	public void setBody(PushMessageResponseBody body) {
		this.body = body;
	}
	
}
