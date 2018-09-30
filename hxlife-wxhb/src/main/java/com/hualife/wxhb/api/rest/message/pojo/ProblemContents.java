package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @descrption : 公共的问卷/调查 -接口的请求参数类
 * @author : linyongtao
 * @time : 2017-08-11 09:14:00
 */
public class ProblemContents {
	/**
	 * id
	 * **/
	private String item_question_id;
	/**
	 * 问题id
	 * **/
	private String item_question_name;
	/**
	 * 回答结果（如果选项是：是/否，则为Y/N，如果选项不是则为：01/02/03...）
	 * **/
	private String item_question_answer;
	/**
	 * 回答描述 （根据实际情况考虑)
	 * **/
	private String item_question_desc;

	/**
	 * 契调函id
	 * **/
	private String survival_note_id;
	/**
	 * 问题答案是否有效
	 * **/
	private String is_valid;
	/**
	 * 高额业务员报告书id
	 * **/
	private String note_report_id;
	/**
	 * 问卷id
	 * **/
	private String note_item_id;
	/**
	 * 契调提交id
	 * **/
	private String survival_answer_id;
	
	public String getSurvival_answer_id() {
		return survival_answer_id;
	}
	public void setSurvival_answer_id(String survival_answer_id) {
		this.survival_answer_id = survival_answer_id;
	}
	public String getItem_question_id() {
		return item_question_id;
	}
	public void setItem_question_id(String item_question_id) {
		this.item_question_id = item_question_id;
	}
	public String getItem_question_answer() {
		return item_question_answer;
	}
	public void setItem_question_answer(String item_question_answer) {
		this.item_question_answer = item_question_answer;
	}
	public String getItem_question_desc() {
		return item_question_desc;
	}
	public void setItem_question_desc(String item_question_desc) {
		this.item_question_desc = item_question_desc;
	}

	
	public String getItem_question_name() {
		return item_question_name;
	}
	public void setItem_question_name(String item_question_name) {
		this.item_question_name = item_question_name;
	}
	public String getSurvival_note_id() {
		return survival_note_id;
	}
	public void setSurvival_note_id(String survival_note_id) {
		this.survival_note_id = survival_note_id;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getNote_report_id() {
		return note_report_id;
	}
	public void setNote_report_id(String note_report_id) {
		this.note_report_id = note_report_id;
	}
	public String getNote_item_id() {
		return note_item_id;
	}
	public void setNote_item_id(String note_item_id) {
		this.note_item_id = note_item_id;
	}

}
