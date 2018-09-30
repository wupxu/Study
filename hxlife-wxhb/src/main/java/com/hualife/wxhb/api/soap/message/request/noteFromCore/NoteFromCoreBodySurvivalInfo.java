package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodySurvivalInfo {

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
	 * 客户证件号码
	 */
	@XmlElement(name="Client_idno")
	private String client_idno = "";

	/**
	 * 需要业务员提供的资料
	 */
	@XmlElement(name="Agent_file")
	private String agent_file = "";

	/**
	 * 其他调查内容
	 */
	@XmlElement(name="Other_invest")
	private String other_invest = "";

	/**
	 * 契调原因
	 */
	@XmlElement(name="Survival_reason")
	private String survival_reason = "";

	/**
	 * 契调方式
	 */
	@XmlElement(name="Survival_mode")
	private String survival_mode = "";

	/**
	 * 核保员特别说明
	 */
	@XmlElement(name="Special_desc")
	private String special_desc = "";

	/**
	 * 契调函原来所在省
	 */
	@XmlElement(name="Survival_branch_province")
	private String survival_branch_province = "";

	/**
	 * 契调函所属机构
	 */
	@XmlElement(name="Survival_branch_code")
	private String survival_branch_code = "";

	/**
	 * 业务员所在机构地址
	 */
	@XmlElement(name="Agent_branch_address")
	private String agent_branch_address = "";

	/**
	 * 下发契调日期
	 */
	@XmlElement(name="Survival_date")
	private String survival_date ="";

	/**
	 * 下发时间
	 */
	@XmlElement(name="Deciding_date")
	private String Deciding_date = "";

	/**
	 * 契调函内容
	 */
	@XmlElement(name="SurvivalItems")
	private NoteFromCoreBodySurvivalItems SurvivalItems = new NoteFromCoreBodySurvivalItems();
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
	public String getClient_idno() {
		return client_idno;
	}
	public void setClient_idno(String client_idno) {
		this.client_idno = client_idno;
	}
	public String getAgent_file() {
		return agent_file;
	}
	public void setAgent_file(String agent_file) {
		this.agent_file = agent_file;
	}
	public String getOther_invest() {
		return other_invest;
	}
	public void setOther_invest(String other_invest) {
		this.other_invest = other_invest;
	}
	public String getSurvival_reason() {
		return survival_reason;
	}
	public void setSurvival_reason(String survival_reason) {
		this.survival_reason = survival_reason;
	}
	public String getSurvival_mode() {
		return survival_mode;
	}
	public void setSurvival_mode(String survival_mode) {
		this.survival_mode = survival_mode;
	}
	public String getSpecial_desc() {
		return special_desc;
	}
	public void setSpecial_desc(String special_desc) {
		this.special_desc = special_desc;
	}
	public String getSurvival_branch_province() {
		return survival_branch_province;
	}
	public void setSurvival_branch_province(String survival_branch_province) {
		this.survival_branch_province = survival_branch_province;
	}
	public String getSurvival_branch_code() {
		return survival_branch_code;
	}
	public void setSurvival_branch_code(String survival_branch_code) {
		this.survival_branch_code = survival_branch_code;
	}
	public String getAgent_branch_address() {
		return agent_branch_address;
	}
	public void setAgent_branch_address(String agent_branch_address) {
		this.agent_branch_address = agent_branch_address;
	}
	
	public String getSurvival_date() {
		return survival_date;
	}
	public void setSurvival_date(String survival_date) {
		this.survival_date = survival_date;
	}
	public NoteFromCoreBodySurvivalItems getSurvivalItems() {
		return SurvivalItems;
	}
	public void setSurvivalItems(NoteFromCoreBodySurvivalItems survivalItems) {
		SurvivalItems = survivalItems;
	}
	public String getDeciding_date() {
		return Deciding_date;
	}
	public void setDeciding_date(String deciding_date) {
		Deciding_date = deciding_date;
	}
	
}
