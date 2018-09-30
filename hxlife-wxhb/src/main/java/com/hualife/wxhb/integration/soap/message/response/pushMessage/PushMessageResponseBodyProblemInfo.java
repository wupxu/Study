package com.hualife.wxhb.integration.soap.message.response.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年8月31日
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBodyProblemInfo {

	@XmlElement(name = "Taskcode")
	private String taskCode = "";// 核保任务号

	@XmlElement(name = "Note_seq")
	private String noteSeq = "";// 核保任务序号
	
	@XmlElement(name = "Apply_no")
	private String ApplyNo= "";//投保单号

	@XmlElement(name = "Result_type")
	private String resultType = "";// 返回结果(0-成功 1-失败)
	
	

	public String getApplyNo() {
		return ApplyNo;
	}

	public void setApplyNo(String applyNo) {
		ApplyNo = applyNo;
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

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

}
