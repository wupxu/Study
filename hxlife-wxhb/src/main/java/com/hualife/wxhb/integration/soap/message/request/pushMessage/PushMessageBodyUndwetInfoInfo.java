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
public class PushMessageBodyUndwetInfoInfo {
	/**
	 * 核保任务序号
	 */
	@XmlElement(name = "Note_seq")
	private String noteSeq = "";
	/**
	 * 客户回复信息
	 */
	@XmlElement(name = "ClientOption")
	private String clientAnswerResult = "";
	
	@XmlElement(name ="Apply_no")
	private String applyBarCode = "";
	/**
	 * 核保任务号
	 */
	@XmlElement(name = "Taskcode")
	private String taskCode = "";

	public String getApplyBarCode() {
		return applyBarCode;
	}

	public void setApplyBarCode(String applyBarCode) {
		this.applyBarCode = applyBarCode;
	}

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

	public String getClientAnswerResult() {
		return clientAnswerResult;
	}

	public void setClientAnswerResult(String clientAnswerResult) {
		this.clientAnswerResult = clientAnswerResult;
	}



}
