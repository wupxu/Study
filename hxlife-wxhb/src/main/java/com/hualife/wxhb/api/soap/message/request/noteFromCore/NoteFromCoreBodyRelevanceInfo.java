package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyRelevanceInfo {
	
	/**
	 * 关联投保单号
	 */
	@XmlElement(name="Apply_bar_code")
	private String apply_bar_code  = "";
	/**
	 * 投保人姓名
	 */
	@XmlElement(name="Applicant_name")
	private String applicant_name  = "";
	/**
	 * 投保人手机号
	 */
	@XmlElement(name="Applicant_phone")
	private String applicant_phone = "";
	/**
	 * 被保人姓名
	 */
	@XmlElement(name="Insured_name")
	private String insured_name = "" ;
	
	public String getApply_bar_code() {
		return apply_bar_code;
	}
	public void setApply_bar_code(String apply_bar_code) {
		this.apply_bar_code = apply_bar_code;
	}
	public String getApplicant_name() {
		return applicant_name;
	}
	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}
	
	public String getApplicant_phone() {
		return applicant_phone;
	}
	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
	}
	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
	
}
