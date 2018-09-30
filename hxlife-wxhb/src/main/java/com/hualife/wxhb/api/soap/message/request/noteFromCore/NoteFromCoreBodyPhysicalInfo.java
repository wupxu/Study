package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyPhysicalInfo {
	/**
	 * 核保任务序号
	 */
	@XmlElement(name="Note_seq")
	private String note_seq = "";
	
	/**
	 * 函件条形码
	 */
	@XmlElement(name="Note_bar_code")
	private String note_bar_code = "";
	
	/**
	 * 函件下发原因
	 */
	@XmlElement(name="Note_reason")
	private String note_reason = "";
	
	/**
	 * 客户是否可以免陪检
	 */
	@XmlElement(name="Is_self_health")
	private String is_self_health = "";
	
	/**
	 * 下发时间
	 */
	@XmlElement(name="Deciding_date")
	private String Deciding_date = "";

	/**
	 * 体检函项目列表对象
	 */
	@XmlElement(name="PhysicalItems")
	private NoteFromCoreBodyPhysicalItems PhysicalItems = new NoteFromCoreBodyPhysicalItems();
	
	public String getNote_seq() {
		return note_seq;
	}
	public void setNote_seq(String note_seq) {
		this.note_seq = note_seq;
	}
	public String getNote_bar_code() {
		return note_bar_code;
	}
	public void setNote_bar_code(String note_bar_code) {
		this.note_bar_code = note_bar_code;
	}
	public String getNote_reason() {
		return note_reason;
	}
	public void setNote_reason(String note_reason) {
		this.note_reason = note_reason;
	}
	public String getIs_self_health() {
		return is_self_health;
	}
	public void setIs_self_health(String is_self_health) {
		this.is_self_health = is_self_health;
	}
	public NoteFromCoreBodyPhysicalItems getPhysicalItems() {
		return PhysicalItems;
	}
	public void setPhysicalItems(NoteFromCoreBodyPhysicalItems physicalItems) {
		PhysicalItems = physicalItems;
	}
	public String getDeciding_date() {
		return Deciding_date;
	}
	public void setDeciding_date(String deciding_date) {
		Deciding_date = deciding_date;
	}
	
}
