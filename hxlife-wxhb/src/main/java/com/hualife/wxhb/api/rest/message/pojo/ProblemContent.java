package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @description : 契调报告问题内容
 * @author : linyongtao
 * @time : 2017-08-17
 */
public class ProblemContent {
	/**
	 * id
	 * **/
	private String id;
	/**
	 * 问题id
	 * **/
	private String item_question_id;
	/**
	 * 回答结果
	 * **/
	private String item_question_answer;
	/**
	 * 回答描述（根据实际情况考虑） 
	 * **/
	private String item_question_desc;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	
}
