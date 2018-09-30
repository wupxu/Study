package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyFinaOocuInfo {

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
	 * 是否有高额件业务员报告书
	 */
	@XmlElement(name="Is_agent_report")
	private String is_agent_report = "";

	/**
	 * 下发时间
	 */
	@XmlElement(name="Deciding_date")
	private String Deciding_date = "";

	/**
	 * 被保人证件类型
	 */
	@XmlElement(name="Insured_id_type")
	private String insured_id_type = "";

	/**
	 * 被保人证件号码
	 */
	@XmlElement(name="Insured_id_no")
	private String insured_id_no = "";

	/**
	 * 被保人电话
	 */
	@XmlElement(name="Insured_phone")
	private String insured_phone = "";

	/**
	 * 财务问卷
	 */
	@XmlElement(name="FinaItems")
	private  NoteFromCoreBodyFinaItems FinaItems = new NoteFromCoreBodyFinaItems();
	
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
	public String getIs_agent_report() {
		return is_agent_report;
	}
	public void setIs_agent_report(String is_agent_report) {
		this.is_agent_report = is_agent_report;
	}
	public String getDeciding_date() {
		return Deciding_date;
	}
	public void setDeciding_date(String deciding_date) {
		Deciding_date = deciding_date;
	}
	public NoteFromCoreBodyFinaItems getFinaItems() {
		return FinaItems;
	}
	public void setFinaItems(NoteFromCoreBodyFinaItems finaItems) {
		FinaItems = finaItems;
	}
	public String getInsured_id_type() {
		return insured_id_type;
	}
	public void setInsured_id_type(String insured_id_type) {
		this.insured_id_type = insured_id_type;
	}
	public String getInsured_id_no() {
		return insured_id_no;
	}
	public void setInsured_id_no(String insured_id_no) {
		this.insured_id_no = insured_id_no;
	}
	public String getInsured_phone() {
		return insured_phone;
	}
	public void setInsured_phone(String insured_phone) {
		this.insured_phone = insured_phone;
	}
	
	
	
}
