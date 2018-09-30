package com.hualife.wxhb.integration.soap.message.response.pushMessage;
/** 
 * @author 吴培旭 
 * @description 问题件
 * @time 创建时间：2017年8月30日   
 */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBodyProblemInfos {

	@XmlElement(name = "Count")
	private String Count = "0";

	@XmlElement(name = "ProblemInfo")
	List<PushMessageResponseBodyProblemInfo> ProblemInfoList = new ArrayList<>();

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

	public List<PushMessageResponseBodyProblemInfo> getProblemInfoList() {
		return ProblemInfoList;
	}

	public void setProblemInfoList(List<PushMessageResponseBodyProblemInfo> problemInfoList) {
		ProblemInfoList = problemInfoList;
	}

}
