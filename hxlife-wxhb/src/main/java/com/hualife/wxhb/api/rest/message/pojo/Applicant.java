package com.hualife.wxhb.api.rest.message.pojo;

/**
 * @author yangpeixin
 * @description 返回对象
 * @date 2017-08-04
 */
public class Applicant {
	/**
	 * 保单号
	 */
	private String apply_bar_code;
	/**
	 * 投保人姓名
	 */
	private String application_name;
	/**
	 * 投保人电话
	 */
	private String applicant_phone;

	public String getApplicant_phone() {
		return applicant_phone;
	}

	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
	}

	public String getApply_bar_code() {
		return apply_bar_code;
	}

	public void setApply_bar_code(String apply_bar_code) {
		this.apply_bar_code = apply_bar_code;
	}

	public String getApplication_name() {
		return application_name;
	}

	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}

}
