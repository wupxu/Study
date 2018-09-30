package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年8月31日
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageBodyProblemObject {
	/**
	 * 问题序号
	 */
	@XmlElement(name = "Problem_seq")
	private String problemSeq = "";
	/**
	 * 问题回复
	 */
	@XmlElement(name = "Problem_answer")
	private String problemAnswer = "";
	/**
	 * 回复人
	 */
	@XmlElement(name = "Problem_object")
	private String problemObject = "";

	public String getProblemSeq() {
		return problemSeq;
	}

	public void setProblemSeq(String problemSeq) {
		this.problemSeq = problemSeq;
	}

	public String getProblemAnswer() {
		return problemAnswer;
	}

	public void setProblemAnswer(String problemAnswer) {
		this.problemAnswer = problemAnswer;
	}

	public String getProblemObject() {
		return problemObject;
	}

	public void setProblemObject(String problemObject) {
		this.problemObject = problemObject;
	}

}
