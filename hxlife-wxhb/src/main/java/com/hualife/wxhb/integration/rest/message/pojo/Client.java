package com.hualife.wxhb.integration.rest.message.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Client {
	/**
	 * 名称
	 */
	private String fileUrl = "";
	/**
	 * 地址
	 */
	@XmlElement(name = "HttpUrl")
	private String HttpUrl = "";

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getHttpUrl() {
		return HttpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		HttpUrl = httpUrl;
	}

}
