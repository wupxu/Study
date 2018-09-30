package com.hualife.wxhb.api.rest.message.request;
/**
 * @description:问卷和报告初始请求参数
 * @author : linyongtao
 * @date : 2017.08.30
 */
public class QuestionRopertInitRequestMessage {
	/**
	 * 函件id
	 * **/
	private String note_id;
	/**
	 * 函件类型
	 * **/
	private String note_type;
	/**
	 * 问卷类型
	 * **/
	private String note_item_type;
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	public String getNote_item_type() {
		return note_item_type;
	}
	public void setNote_item_type(String note_item_type) {
		this.note_item_type = note_item_type;
	}

}
