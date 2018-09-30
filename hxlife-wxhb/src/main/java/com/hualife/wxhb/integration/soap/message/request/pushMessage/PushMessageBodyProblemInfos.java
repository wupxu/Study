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
public class PushMessageBodyProblemInfos {

	@XmlElement(name = "Count")
	private String count = "0";

	@XmlElement(name = "ProblemInfo")
	List<PushMessageBodyProblemInfo> problemInfoList = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<PushMessageBodyProblemInfo> getProblemInfoList() {
		return problemInfoList;
	}

	public void setProblemInfoList(List<PushMessageBodyProblemInfo> problemInfoList) {
		this.problemInfoList = problemInfoList;
	}

}
