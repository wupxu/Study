package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProblemInfo {

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
	 * 投保单号
	 */
	@XmlElement(name="Apply_bar_code")
	private String apply_bar_code = "";

	/**
	 * 投保人客户号
	 */
	@XmlElement(name="Application_no")
	private String application_no = "";

	/**
	 * 投保人姓名
	 */
	@XmlElement(name="Application_name")
	private String application_name = "";

	/**
	 * 投保人手机号
	 */
	@XmlElement(name="Application_phone")
	private String application_phone = "";

	/**
	 * 被保人客户号
	 */
	@XmlElement(name="Insured_no")
	private String insured_no = "";

	/**
	 * 被保人姓名
	 */
	@XmlElement(name="Insured_name")
	private String insured_name = "";

	/**
	 * 被保人年龄
	 */
	@XmlElement(name="Insured_age")
	private String insured_age = "";

	/**
	 * 下发问题件原因
	 */
	@XmlElement(name="Note_reason")
	private String note_reason = "";

	/**
	 * 代理人编码
	 */
	@XmlElement(name="Agent_no")
	private String agent_no = "";

	/**
	 * 代理人姓名
	 */
	@XmlElement(name="Agent_name")
	private String agent_name = "";

	/**
	 * 代理人手机号
	 */
	@XmlElement(name="Agent_phone")
	private String agent_phone = "";
	
	/**
	 * 代理人(代理机构)所属渠道
	 */
	@XmlElement(name="Channel_type")
	private String channel_type = "";

	/**
	 * 营业机构编码
	 */
	@XmlElement(name="Branch_code")
	private String branch_code = "";
	
	/**
	 * 营业机构编码
	 */
	@XmlElement(name="Branch_name")
	private String branch_name = "";

	/**
	 * 下发时间
	 */
	@XmlElement(name="Deciding_date")
	private String Deciding_date = "";

	/**
	 * 问题件对象集合
	 */
	@XmlElement(name="ProblemObjects")
	private NoteFromCoreBodyProblemObjects ProblemObjects = new NoteFromCoreBodyProblemObjects();
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
	public String getApply_bar_code() {
		return apply_bar_code;
	}
	public void setApply_bar_code(String apply_bar_code) {
		this.apply_bar_code = apply_bar_code;
	}
	public String getApplication_no() {
		return application_no;
	}
	public void setApplication_no(String application_no) {
		this.application_no = application_no;
	}
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	public String getInsured_no() {
		return insured_no;
	}
	public void setInsured_no(String insured_no) {
		this.insured_no = insured_no;
	}
	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
	public String getInsured_age() {
		return insured_age;
	}
	public void setInsured_age(String insured_age) {
		this.insured_age = insured_age;
	}
	public String getNote_reason() {
		return note_reason;
	}
	public void setNote_reason(String note_reason) {
		this.note_reason = note_reason;
	}
	public String getAgent_no() {
		return agent_no;
	}
	public void setAgent_no(String agent_no) {
		this.agent_no = agent_no;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getBranch_code() {
		return branch_code;
	}
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	public NoteFromCoreBodyProblemObjects getProblemObjects() {
		return ProblemObjects;
	}
	public void setProblemObjects(NoteFromCoreBodyProblemObjects problemObjects) {
		ProblemObjects = problemObjects;
	}
	public String getApplication_phone() {
		return application_phone;
	}
	public void setApplication_phone(String application_phone) {
		this.application_phone = application_phone;
	}
	public String getAgent_phone() {
		return agent_phone;
	}
	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}
	public String getDeciding_date() {
		return Deciding_date;
	}
	public void setDeciding_date(String deciding_date) {
		Deciding_date = deciding_date;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	
}
