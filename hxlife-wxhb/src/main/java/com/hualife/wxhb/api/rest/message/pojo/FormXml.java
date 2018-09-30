package com.hualife.wxhb.api.rest.message.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlRootElement(name = "TranData")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormXml {
	/**
	 * head
	 */
	@XmlElement(name="Head")
	private Head head = new Head();
	/**
	 * body
	 */
	@XmlElement(name="Body")
	private Body body = new Body();

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

}
