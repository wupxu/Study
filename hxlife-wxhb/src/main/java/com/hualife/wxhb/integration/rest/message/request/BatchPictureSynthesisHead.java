package com.hualife.wxhb.integration.rest.message.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author yangpeixin
 * @description 参数
 * @date @date 2017-09-01
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchPictureSynthesisHead {
	/**
	 * 机构
	 */
	@XmlElement(name = "ManageCom")
	private String ManageCom = "";
	/**
	 * 渠道
	 */
	@XmlElement(name = "SaleChnl")
	private String SaleChnl = "";
	/**
	 * 日期
	 */
	@XmlElement(name = "TranDate")
	private String TranDate = "";
	/**
	 * 时间
	 */
	@XmlElement(name = "TranTime")
	private String TranTime = "";
	/**
	 * MD5
	 */
	@XmlElement(name = "MD5")
	private String MD5 = "";

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getManageCom() {
		return ManageCom;
	}

	public void setManageCom(String manageCom) {
		ManageCom = manageCom;
	}

	public String getSaleChnl() {
		return SaleChnl;
	}

	public void setSaleChnl(String saleChnl) {
		SaleChnl = saleChnl;
	}

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

}
