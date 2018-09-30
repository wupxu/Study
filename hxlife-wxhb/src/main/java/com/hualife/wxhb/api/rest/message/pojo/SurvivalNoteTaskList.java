package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @description 契调任务初始化返回报文实-任务-任务列表
 * @author wangt
 * @date 2017-08-07
 */
public class SurvivalNoteTaskList {
	
	private String apply_bar_code;			//投保单号
	private String applicant_name;		//投保人姓名
	

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
