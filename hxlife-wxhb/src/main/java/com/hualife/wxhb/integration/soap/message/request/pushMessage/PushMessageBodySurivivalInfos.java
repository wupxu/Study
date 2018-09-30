package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月22日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageBodySurivivalInfos {

	@XmlElement(name="Count")
	private String count = "0";
	
	@XmlElement(name="SurivivalInfo")
	private List<PushMessageBodySurivivalInfo> surivivalInfoList = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<PushMessageBodySurivivalInfo> getSurivivalInfoList() {
		return surivivalInfoList;
	}

	public void setSurivivalInfoList(List<PushMessageBodySurivivalInfo> surivivalInfoList) {
		this.surivivalInfoList = surivivalInfoList;
	}
}
