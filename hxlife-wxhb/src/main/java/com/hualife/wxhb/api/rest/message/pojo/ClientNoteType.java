package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @descrption : 业务待处理页面--客户层函件---函件类型及其状态
 * @author : linyongtao
 * @time : 2017.08.14
 */
public class ClientNoteType {
	/**
	 *函件类型 
	 * **/
	private String note_type;
	/**
	 *函件类型描述
	 * **/
	private String note_type_desc;
	/**
	 *函件下发原因 
	 * **/
	private String note_reason;
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	public String getNote_reason() {
		return note_reason;
	}
	public void setNote_reason(String note_reason) {
		this.note_reason = note_reason;
	}
	public String getNote_type_desc() {
		return note_type_desc;
	}
	public void setNote_type_desc(String note_type_desc) {
		this.note_type_desc = note_type_desc;
	}

	
}
