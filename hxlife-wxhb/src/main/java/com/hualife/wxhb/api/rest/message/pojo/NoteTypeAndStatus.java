package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @descrption : 财务函提交时，查看其他函件的状态
 * @author linyongtao
 * @time : 2017-08-14
 */
public class NoteTypeAndStatus {
	/**
	 * 客户层函件各个函件的状态
	 * **/
	private String note_status;
	/**
	 * 客户层函件各个函件的类型
	 * **/
	private String note_type;
	/**
	 * 具体函件id
	 * **/
	private String client_note_id;
	
	public String getNote_status() {
		return note_status;
	}

	public void setNote_status(String note_status) {
		this.note_status = note_status;
	}

	public String getNote_type() {
		return note_type;
	}

	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}

	public String getClient_note_id() {
		return client_note_id;
	}

	public void setClient_note_id(String client_note_id) {
		this.client_note_id = client_note_id;
	}


	
}
