package com.hualife.wxhb.integration.soap.message.response.chooseTypePush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChooseTypePushReponseBodyStatus {
	private String ReturnCode;
	private String Taskcode;
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	public String getTaskcode() {
		return Taskcode;
	}
	public void setTaskcode(String taskcode) {
		Taskcode = taskcode;
	}
}
