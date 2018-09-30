package com.hualife.wxhb.integration.soap.message.request.image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * @deprecation 影像上载请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageUpLoadPage {
	
	@XmlElement(name = "PAGENO")
	private String  pageno= "";			//页码
	
	@XmlElement(name = "PAGENAME")
	private String  pagename= "";		//文件名
	
	@XmlElement(name = "PAGEPATH")
	private String  pagepath= "";		//文件存放地址

	public String getPageno() {
		return pageno;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
			
}
