package com.hualife.wxhb.api.soap.message.request.noteFinishDeal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * @author zhangweiwei
 * @deprecation 核心回销消息报文体
 * @date 2017-08-27
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFinishDealBody {
	/**
	 * 核保任务号
	 */
	@XmlElement(name="Taskcode")
	private String Taskcode = "";
	/**
	 * 核保任务序号
	 */
	@XmlElement(name="Note_seq")
	private String note_seq="";
	/**
	 * 函件类型
	 * @return
	 */
	@XmlElement(name="Note_type")
	private String note_type="";
	
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
