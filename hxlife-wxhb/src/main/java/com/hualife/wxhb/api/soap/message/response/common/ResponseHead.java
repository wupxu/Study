package com.hualife.wxhb.api.soap.message.response.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseHead {
	
	@XmlElement(name = "Version")
	private String Version = "";//报文版本 目前固定值填1.0
	
	@XmlElement(name = "Cn2utf8")
	private String Cn2utf8 = "";//汉字采用utf8转义序列传输标志 1/0(是/否),默认0

	@XmlElement(name = "Msg")
	private ResponseMsg Msg = new ResponseMsg();//报文控制信息
	
	@XmlElement(name = "Status")
	private ResponseStatus Status = new ResponseStatus();
	
	@XmlElement(name = "Ext")
	private String Ext = "";//自行定义  存放未事先定义的通用控制信息  
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getCn2utf8() {
		return Cn2utf8;
	}
	public void setCn2utf8(String cn2utf8) {
		Cn2utf8 = cn2utf8;
	}
	public String getExt() {
		return Ext;
	}
	public void setExt(String ext) {
		Ext = ext;
	}
	
	public ResponseMsg getMsg() {
		return Msg;
	}
	public void setMsg(ResponseMsg msg) {
		Msg = msg;
	}
	public ResponseStatus getStatus() {
		return Status;
	}
	public void setStatus(ResponseStatus status) {
		Status = status;
	} 
	
}
