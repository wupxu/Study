package com.hualife.wxhb.integration.soap.message.response.pushMessage;
/** 
 * @author 吴培旭 
 * @description 契调函
 * @time 创建时间：2017年8月30日   
 */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBodySurivivalInfos {

	@XmlElement(name="Count")
	private String Count = "0";
	
	@XmlElement(name="SurivivalInfo")
	List<PushMessageResponseBodySurivivalInfo> surivivalInfoList = new ArrayList<>();

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

	public List<PushMessageResponseBodySurivivalInfo> getSurivivalInfoList() {
		return surivivalInfoList;
	}

	public void setSurivivalInfoList(List<PushMessageResponseBodySurivivalInfo> surivivalInfoList) {
		this.surivivalInfoList = surivivalInfoList;
	}
	
	
}
