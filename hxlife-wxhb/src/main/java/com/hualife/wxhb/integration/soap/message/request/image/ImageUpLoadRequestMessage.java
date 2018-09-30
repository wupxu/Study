package com.hualife.wxhb.integration.soap.message.request.image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
/**
 * @deprecation 影像上载请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
@XmlRootElement(name = "InputData")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageUpLoadRequestMessage {
	/**
	 * 报文头
	 */
	@XmlElement(name="Head")
	private RequestHead head = new RequestHead();
	/**
	 * 报文体
	 */
	@XmlElement(name="Body")
	private ImageUpLoadBody body = new ImageUpLoadBody();
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public ImageUpLoadBody getBody() {
		return body;
	}
	public void setBody(ImageUpLoadBody body) {
		this.body = body;
	}
	
	
}
