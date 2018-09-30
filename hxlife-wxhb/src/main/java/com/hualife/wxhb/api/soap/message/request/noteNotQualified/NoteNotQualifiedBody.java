package com.hualife.wxhb.api.soap.message.request.noteNotQualified;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteNotQualifiedBody {
	/**
	 * 核保任务号
	 */
	@XmlElement(name="Taskcode")
	private String Taskcode = "";//核保任务号
	/**
	 * 核保任务序号
	 */
	@XmlElement(name="Note_seq")
	private String note_seq = "";//核保任务序号
	/**
	 * /函件类型
	 */
	@XmlElement(name="Note_type")
	private String note_type = "";//函件类型
	
	/**
	 * 次品单原因
	 */
	@XmlElement(name="Image_failed_reason")
	private String image_failed_reason = "";//次品单原因
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
	public String getImage_failed_reason() {
		return image_failed_reason;
	}
	public void setImage_failed_reason(String image_failed_reason) {
		this.image_failed_reason = image_failed_reason;
	}
	

}
