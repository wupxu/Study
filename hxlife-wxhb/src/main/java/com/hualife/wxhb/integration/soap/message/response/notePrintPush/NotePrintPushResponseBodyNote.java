package com.hualife.wxhb.integration.soap.message.response.notePrintPush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description 批处理函件下发
 * @time 创建时间：2017年8月18日 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NotePrintPushResponseBodyNote {

	@XmlElement(name = "Note_seq")
	private String note_seq = "";// 核保任务序号

	@XmlElement(name = "Note_type")
	private String note_type = "";// 函件类型

	@XmlElement(name = "Note_image_url")
	private String Note_image_url = "";// 通知书路径

	@XmlElement(name = "Taskcode")
	private String taskcode = "";// 核保任务号

	@XmlElement(name = "Print_result")
	private String printResult = "";// 打印结果

	public String getNote_seq() {
		return note_seq;
	}

	public void setNote_seq(String note_seq) {
		this.note_seq = note_seq;
	}

	public String getNote_type() {
		return note_type;
	}

	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}

	public String getNote_image_url() {
		return Note_image_url;
	}

	public void setNote_image_url(String note_image_url) {
		Note_image_url = note_image_url;
	}

	public String getTaskcode() {
		return taskcode;
	}

	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	public String getPrintResult() {
		return printResult;
	}

	public void setPrintResult(String printResult) {
		this.printResult = printResult;
	}

}
