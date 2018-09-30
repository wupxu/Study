package com.hualife.wxhb.api.rest.message.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Head {
	
	@XmlElement(name = "TranDate")
	private String TranDate = ""; //交易日期
	@XmlElement(name = "TranTime")
	private String TranTime = ""; //交易时间
	@XmlElement(name = "Status")
	private String Status = "";  //状态
	public String getTranDate() {
		return TranDate;
	}
	public void setTranDate(String tranDate) {
		TranDate = tranDate;
	}
	public String getTranTime() {
		return TranTime;
	}
	public void setTranTime(String tranTime) {
		TranTime = tranTime;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	
	
}
