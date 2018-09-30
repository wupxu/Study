package com.hualife.wxhb.api.rest.message.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author yangpeixin
 * @description 参数
 * @date 2017-09-01
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PhysicalTif {
	/**
	 * 名称
	 */
	@XmlElement(name = "fileUrl")
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
