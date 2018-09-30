package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @description 健康函初始化返回报文-健康问卷
 * @author wangt
 * @date 2017-08-08
 */
public class HealthNoteItem {
	
	private String note_item_id;		//健康问卷子项id
	private String note_item_type;		//健康问卷类型
	private String note_item_type_name;	//健康问卷名称
	private String note_item_status;	//问卷回答状态
	
	public String getNote_item_id() {
		return note_item_id;
	}
	public void setNote_item_id(String note_item_id) {
		this.note_item_id = note_item_id;
	}
	public String getNote_item_type() {
		return note_item_type;
	}
	public void setNote_item_type(String note_item_type) {
		this.note_item_type = note_item_type;
	}
	public String getNote_item_type_name() {
		return note_item_type_name;
	}
	public void setNote_item_type_name(String note_item_type_name) {
		this.note_item_type_name = note_item_type_name;
	}
	public String getNote_item_status() {
		return note_item_status;
	}
	public void setNote_item_status(String note_item_status) {
		this.note_item_status = note_item_status;
	}
}
