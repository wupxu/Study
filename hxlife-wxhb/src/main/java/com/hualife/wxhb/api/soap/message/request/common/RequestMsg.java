package com.hualife.wxhb.api.soap.message.request.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RequestMsg {
	/**
	 * 服务id，报文编号，交易码
	 */
	@XmlElement(name="ServiceID")
	private String ServiceID = "";
	/**
	 * 服务/交易类型
	 */
	@XmlElement(name="ServiceType")
	private String ServiceType = "";
	/**
	 * 调用模式  SYN同步，ASYN异步
	 */
	@XmlElement(name="CallType")
	private String CallType = "";
	/**
	 * 交易流水号
	 */
	@XmlElement(name="TransNo")
	private String TransNo = "";
	/**
	 * 客户端IP
	 */
	@XmlElement(name="ClientIP")
	private String ClientIP = "";
	/**
	 * 服务发起方应用系统编号
	 */
	@XmlElement(name="FromSystemkey")
	private String FromSystemkey = "";
	/**
	 * 请求密码
	 */
	@XmlElement(name="Password")
	private String Password = "";
	/**
	 * 发起日期 
	 *  格式：20170101 表示2017-01-01
	 */
	@XmlElement(name="TransDate")
	private String TransDate = "";
	/**
	 * 发起时间 
	 * 格式：090807表示  09时08分07秒
	 */
	@XmlElement(name="TransTime")
	private String TransTime = "";
	/**
	 * 服务接收方应用系统编号  核心系统CBS
	 */
	@XmlElement(name="ToSystemKey")
	private String ToSystemKey = "";
	/**
	 * 有效时间  
	 * 建议同步请求设置，单位为S
	 */
	@XmlElement(name="EffectiveTime")
	private String EffectiveTime = "";
	
	public String getServiceID() {
		return ServiceID;
	}
	public void setServiceID(String serviceID) {
		ServiceID = serviceID;
	}
	public String getServiceType() {
		return ServiceType;
	}
	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}
	public String getCallType() {
		return CallType;
	}
	public void setCallType(String callType) {
		CallType = callType;
	}
	public String getTransNo() {
		return TransNo;
	}
	public void setTransNo(String transNo) {
		TransNo = transNo;
	}
	public String getClientIP() {
		return ClientIP;
	}
	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}
	public String getFromSystemkey() {
		return FromSystemkey;
	}
	public void setFromSystemkey(String fromSystemkey) {
		FromSystemkey = fromSystemkey;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getTransDate() {
		return TransDate;
	}
	public void setTransDate(String transDate) {
		TransDate = transDate;
	}
	public String getTransTime() {
		return TransTime;
	}
	public void setTransTime(String transTime) {
		TransTime = transTime;
	}
	public String getToSystemKey() {
		return ToSystemKey;
	}
	public void setToSystemKey(String toSystemKey) {
		ToSystemKey = toSystemKey;
	}
	public String getEffectiveTime() {
		return EffectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		EffectiveTime = effectiveTime;
	}
	
	
	
}
