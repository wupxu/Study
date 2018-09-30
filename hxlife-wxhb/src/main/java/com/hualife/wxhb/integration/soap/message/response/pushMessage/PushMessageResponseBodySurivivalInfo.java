package com.hualife.wxhb.integration.soap.message.response.pushMessage;
/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月31日   
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBodySurivivalInfo {

	@XmlElement(name = "Taskcode")
	private String taskCode = "";// 核保任务号

	@XmlElement(name = "Note_seq")
	private String noteSeq = "";// 核保任务序号

	@XmlElement(name = "Result_type")
	private String resultType = "";// 返回结果(0-成功 1-失败)

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
