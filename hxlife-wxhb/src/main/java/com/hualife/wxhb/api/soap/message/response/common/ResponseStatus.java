package com.hualife.wxhb.api.soap.message.response.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseStatus {
	
	@XmlElement(name="ReturnCode")
	private String ReturnCode = "";//返回码
	
	@XmlElement(name="Desc")
	private String Desc = ""; //返回信息描述
	
	@XmlElement(name="Location")
	private String Location = "";//发生异常/错误的具体log日志或位置
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	
}
