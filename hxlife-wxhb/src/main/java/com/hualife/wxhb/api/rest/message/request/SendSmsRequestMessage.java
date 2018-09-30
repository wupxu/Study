package com.hualife.wxhb.api.rest.message.request;

/**
 * @author yangpeixin
 * @description 验证码发送
 * @date 2017-08-04
 */
public class SendSmsRequestMessage {
	/**
	 * 电话号码
	 */
	private String applicant_phone;
	/**
	 * 函件ID
	 */
	private String note_id;

	public String getApplicant_phone() {
		return applicant_phone;
	}

	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
	}

	public String getNote_id() {
		return note_id;
	}

	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}

}
