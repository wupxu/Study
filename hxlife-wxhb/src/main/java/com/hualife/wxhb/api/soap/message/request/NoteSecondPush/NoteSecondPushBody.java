package com.hualife.wxhb.api.soap.message.request.NoteSecondPush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteSecondPushBody {
	/**
	 * 核保任务号
	 */
	@XmlElement(name="TaskCode")
	private String Taskcode = "";//核保任务号
	/**
	 * 核保任务序号
	 */
	@XmlElement(name="Note_seq")
	private String note_seq = "";//核保任务序号
	/**
	 * 函件类型
	 */
	@XmlElement(name="Note_type")
	private String note_type = "";//函件类型
	/**
	 * 重新下发原因
	 */
	@XmlElement(name="Note_second_reason")
	private String note_second_reason = "";//重新下发原因
	
	
	public String getNote_second_reason() {
		return note_second_reason;
	}
	public void setNote_second_reason(String note_second_reason) {
		this.note_second_reason = note_second_reason;
	}
	public String getTaskcode() {
		return Taskcode;
	}
	public void setTaskcode(String taskcode) {
		Taskcode = taskcode;
	}
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
	
	
}
