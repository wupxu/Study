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
public class PushMessageBodySurivivalInfo {
	/**
	 * 核保任务序号
	 */
	@XmlElement(name = "Note_seq")
	private String noteSeq = "";
	/**
	 * 契调结果
	 */
	@XmlElement(name = "Survival_note_report")
	private String survivalNoteReport = "";
	/**
	 * 调查员编号
	 */
	@XmlElement(name = "Survival_no")
	private String survivalNo = "";
	/**
	 * 核保任务号
	 */
	@XmlElement(name = "Taskcode")
	private String taskCode = "";

	public String getNoteSeq() {
		return noteSeq;
	}

	public void setNoteSeq(String noteSeq) {
		this.noteSeq = noteSeq;
	}

	public String getSurvivalNoteReport() {
		return survivalNoteReport;
	}

	public void setSurvivalNoteReport(String survivalNoteReport) {
		this.survivalNoteReport = survivalNoteReport;
	}

	public String getSurvivalNo() {
		return survivalNo;
	}

	public void setSurvivalNo(String survivalNo) {
		this.survivalNo = survivalNo;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

}
