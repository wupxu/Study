package com.hualife.wxhb.api.rest.message.response;

import com.hualife.wxhb.api.rest.message.pojo.Letters;

/**
 * @author yangpeixin
 * @description 返回参数
 * @date 2017-08-04
 */

public class NoteInfoResponseMessage {
	/**
	 * 企业号
	 */
	private String appid;
	/**
	 * 客户姓名
	 */
	private String client_name;
	/**
	 * 电话号码
	 */
	private String applicant_phone;
	/**
	 * 代理人姓名
	 */
	private String agent_name;
	/**
	 * 代理人电话
	 */
	private String agent_phone;
	/**
	 * 是否验证过
	 */
	private String phone_success;
	/**
	 * 函件对象
	 */
	private Letters letters = new Letters();

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Letters getLetters() {
		return letters;
	}

	public void setLetters(Letters letters) {
		this.letters = letters;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getApplicant_phone() {
		return applicant_phone;
	}

	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_phone() {
		return agent_phone;
	}

	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}

	public String getPhone_success() {
		return phone_success;
	}

	public void setPhone_success(String phone_success) {
		this.phone_success = phone_success;
	}

}
