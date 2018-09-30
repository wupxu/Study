package com.hualife.wxhb.integration.soap.message.request.image;

import javax.xml.bind.annotation.XmlRootElement;

import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
/**
 * @deprecation 影像删除请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
@XmlRootElement(name = "InputData")
public class ImageDeleteRequestMessage {
	private RequestHead head = new RequestHead();
	private ImageDeleteBody body = new ImageDeleteBody();
	
	public RequestHead getHead() {
		return head;
	}
	public void setHead(RequestHead head) {
		this.head = head;
	}
	public ImageDeleteBody getBody() {
		return body;
	}
	public void setBody(ImageDeleteBody body) {
		this.body = body;
	}
		
	
}
