package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月31日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageBodyProblemObjects {

	@XmlElement(name="Count")
	private String Count = "0";
	
	@XmlElement(name="ProblemObject")
	private List<PushMessageBodyProblemObject> problemObjectList = new ArrayList<>();

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

	public List<PushMessageBodyProblemObject> getProblemObjectList() {
		return problemObjectList;
	}

	public void setProblemObjectList(List<PushMessageBodyProblemObject> problemObjectList) {
		this.problemObjectList = problemObjectList;
	}

}
