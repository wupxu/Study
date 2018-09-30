package com.hualife.wxhb.api.soap.message.response.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**  
 * @Description: 返回公共报文体
 * @author zhanglong 
 * @date 2017年9月7日 下午3:19:23  
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseBody {
	@XmlElement(name="ReturnCode")
	private String ReturnCode = "";//返回码
	
	@XmlElement(name="Desc")
	private String Desc = ""; //返回信息描述

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
	
}
