package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @description : 业务待处理页面--客户层函件---投保人信息
 * @author : linyongtao
 * @date : 2017.08.14
 */
public class AppliInfo {
	/**
	 * 关联投保单号
	 * **/
	private String apply_bar_code;
	/**
	 * 投保人姓名
	 * **/
	private String applicant_name;
	/**
	 * 投保人姓名
	 * **/
	private String insured_name;

	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
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
	

}
