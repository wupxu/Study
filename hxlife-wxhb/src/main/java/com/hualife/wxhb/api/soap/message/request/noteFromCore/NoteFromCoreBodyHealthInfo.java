package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyHealthInfo {

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
	 * 核保员特别说明
	 */
	@XmlElement(name="Special_desc")
	private String special_desc = "";

	/**
	 * 其他核保师的说明
	 */
	@XmlElement(name="Other_invest")
	private String other_invest = "";

	/**
	 * 下发时间
	 */
	@XmlElement(name="Deciding_date")
	private String Deciding_date = "";

	/**
	 * 健康问卷集合
	 */
	@XmlElement(name="HealthItems")
	private NoteFromCoreBodyHealthItems HealthItems = new NoteFromCoreBodyHealthItems();
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
	public String getSpecial_desc() {
		return special_desc;
	}
	public void setSpecial_desc(String special_desc) {
		this.special_desc = special_desc;
	}
	public String getOther_invest() {
		return other_invest;
	}
	public void setOther_invest(String other_invest) {
		this.other_invest = other_invest;
	}
	public NoteFromCoreBodyHealthItems getHealthItems() {
		return HealthItems;
	}
	public void setHealthItems(NoteFromCoreBodyHealthItems healthItems) {
		HealthItems = healthItems;
	}
	public String getDeciding_date() {
		return Deciding_date;
	}
	public void setDeciding_date(String deciding_date) {
		Deciding_date = deciding_date;
	}
	
}
