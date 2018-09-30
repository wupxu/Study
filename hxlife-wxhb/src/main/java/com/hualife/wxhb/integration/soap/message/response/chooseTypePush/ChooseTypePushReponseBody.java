package com.hualife.wxhb.integration.soap.message.response.chooseTypePush;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChooseTypePushReponseBody {
	List<ChooseTypePushReponseBodyStatus> result = new ArrayList<ChooseTypePushReponseBodyStatus>();

	public List<ChooseTypePushReponseBodyStatus> getResult() {
		return result;
	}

	public void setResult(List<ChooseTypePushReponseBodyStatus> result) {
		this.result = result;
	}
	
}
