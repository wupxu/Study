package com.hualife.wxhb.api.rest.message.pojo;
/**
 * 业务待处理页面--问题件函件
 * @author : linyongtao
 * @time : 2017.08.14
 */
public class ProblemNoteInfos {
	/**
	 * 函件id
	 * **/
	private String note_id;
	/**
	 * 问题件id
	 * **/
	private String problem_note_id;
	/**
	 * 投保单号
	 * **/
	private String apply_bar_code;
	/**
	 * 投保人姓名
	 * **/
	private String applicant_name;
	/**
	 * 被保险人姓名
	 * **/
	private String insured_name;
	/**
	 * 函件状态
	 * **/
	private String note_status;
	/**
	 * 函件状态描述
	 * **/
	private String note_status_desc;
	
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}	
	public String getProblem_note_id() {
		return problem_note_id;
	}
	public void setProblem_note_id(String problem_note_id) {
		this.problem_note_id = problem_note_id;
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
	public String getApplication_name() {
		return applicant_name;
	}
	public void setApplication_name(String application_name) {
		this.applicant_name = application_name;
	}
	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
	public String getNote_status() {
		return note_status;
	}
	public void setNote_status(String note_status) {
		this.note_status = note_status;
	}
	public String getNote_status_desc() {
		return note_status_desc;
	}
	public void setNote_status_desc(String note_status_desc) {
		this.note_status_desc = note_status_desc;
	}			
}
