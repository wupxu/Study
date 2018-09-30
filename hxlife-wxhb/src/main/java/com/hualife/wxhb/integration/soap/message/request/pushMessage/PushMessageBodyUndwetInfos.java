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
public class PushMessageBodyUndwetInfos {

	@XmlElement(name="Count")
	private String count = "0";
	
	@XmlElement(name="UndwrtInfo")
	private List<PushMessageBodyUndwetInfoInfo> undwetInfoInfoList = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<PushMessageBodyUndwetInfoInfo> getUndwetInfoInfoList() {
		return undwetInfoInfoList;
	}

	public void setUndwetInfoInfoList(List<PushMessageBodyUndwetInfoInfo> undwetInfoInfoList) {
		this.undwetInfoInfoList = undwetInfoInfoList;
	}

}
