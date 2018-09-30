package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.Applicant;

/**
 * @author yangpeixin
 * @description 返回对象
 * @date 2017-08-04
 */
public class SurvivalInvestInfoResponseMessage {
	/**
	 * 客户姓名
	 */
	private String client_name;
	/**
	 * 身份证号
	 */
	private String client_idno;
	/**
	 * 投保人对象
	 */
	private List<Applicant> applicant;
	/**
	 * 代理人姓名
	 */
	private String agent_name;
	/**
	 * 地址
	 */
	private String agent_branch_address;
	/**
	 * 代理人号
	 */
	private String agent_no;
	/**
	 * 契调原因
	 */
	private String survival_reason;
	/**
	 * 核保员特别说明
	 */
	private String special_desc;
	/**
	 * 验证码
	 */
	private String survival_mode;
	/**
	 * 契调内容
	 */
	private List<String> note_item_type;
	/**
	 * 下发日期
	 */
	private String survival_date;

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getClient_idno() {
		return client_idno;
	}

	public void setClient_idno(String client_idno) {
		this.client_idno = client_idno;
	}

	public List<Applicant> getApplicant() {
		return applicant;
	}

	public void setApplicant(List<Applicant> applicant) {
		this.applicant = applicant;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_branch_address() {
		return agent_branch_address;
	}

	public void setAgent_branch_address(String agent_branch_address) {
		this.agent_branch_address = agent_branch_address;
	}

	public String getAgent_no() {
		return agent_no;
	}

	public void setAgent_no(String agent_no) {
		this.agent_no = agent_no;
	}

	public String getSurvival_reason() {
		return survival_reason;
	}

	public void setSurvival_reason(String survival_reason) {
		this.survival_reason = survival_reason;
	}

	public String getSpecial_desc() {
		return special_desc;
	}

	public void setSpecial_desc(String special_desc) {
		this.special_desc = special_desc;
	}

	public String getSurvival_mode() {
		return survival_mode;
	}

	public void setSurvival_mode(String survival_mode) {
		this.survival_mode = survival_mode;
	}

	public List<String> getNote_item_type() {
		return note_item_type;
	}

	public void setNote_item_type(List<String> note_item_type) {
		this.note_item_type = note_item_type;
	}

	public String getSurvival_date() {
		return survival_date;
	}

	public void setSurvival_date(String survival_date) {
		this.survival_date = survival_date;
	}

}
