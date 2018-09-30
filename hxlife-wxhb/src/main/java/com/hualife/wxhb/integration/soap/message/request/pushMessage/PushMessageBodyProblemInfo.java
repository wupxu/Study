package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月22日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageBodyProblemInfo {
	/**
	 * 核保任务号
	 */
	@XmlElement(name="Taskcode")
	private String taskCode = "";
	/**
	 * 核保任务序号
	 */
	@XmlElement(name="Note_seq")
	private String noteSeq =  "";
	/**
	 * 投保单号
	 */
	@XmlElement(name="Apply_no")
	private String ApplyNo = "";
	/**
	 * 回销类型
	 */
	@XmlElement(name="Buyback_type")
	private String buybackType = "";
	
	@XmlElement(name="ProblemObjects")
	private PushMessageBodyProblemObjects pushMessageBodyProblemObjects = new PushMessageBodyProblemObjects();//问题件问题相关类

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getNoteSeq() {
		return noteSeq;
	}

	public void setNoteSeq(String noteSeq) {
		this.noteSeq = noteSeq;
	}

	public String getApplyNo() {
		return ApplyNo;
	}

	public void setApplyNo(String applyNo) {
		ApplyNo = applyNo;
	}

	public String getBuybackType() {
		return buybackType;
	}

	public void setBuybackType(String buybackType) {
		this.buybackType = buybackType;
	}

	public PushMessageBodyProblemObjects getPushMessageBodyProblemObjects() {
		return pushMessageBodyProblemObjects;
	}

	public void setPushMessageBodyProblemObjects(PushMessageBodyProblemObjects pushMessageBodyProblemObjects) {
		this.pushMessageBodyProblemObjects = pushMessageBodyProblemObjects;
	}
}
