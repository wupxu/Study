package com.hualife.wxhb.api.rest.message.pojo;

/**
 * @author yangpeixin
 * @description 返回参数
 * @date 2017-08-04
 */

public class Physical {
	/**
	 * 函件ID
	 */
	private String physical_note_id;
	/**
	 * 函件状态
	 */
	private String note_client_status;
	/**
	 * 函件状态描述
	 */
	private String note_client_status_desc;
	/**
	 * 函件类型描述
	 */
	private String note_type_dec;
	/**
	 * 函件类型
	 */
	
	private String note_type;
	
	/**
	 * 客户选择是否免陪
	 */
	private String client_choose_type;
	
	
	

	public String getClient_choose_type() {
		return client_choose_type;
	}

	public void setClient_choose_type(String client_choose_type) {
		this.client_choose_type = client_choose_type;
	}

	public String getPhysical_note_id() {
		return physical_note_id;
	}

	public void setPhysical_note_id(String physical_note_id) {
		this.physical_note_id = physical_note_id;
	}

	public String getNote_type() {
		return note_type;
	}

	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}

	public String getNote_client_status() {
		return note_client_status;
	}

	public void setNote_client_status(String note_client_status) {
		this.note_client_status = note_client_status;
	}

	public String getNote_client_status_desc() {
		return note_client_status_desc;
	}

	public void setNote_client_status_desc(String note_client_status_desc) {
		this.note_client_status_desc = note_client_status_desc;
	}

	public String getNote_type_dec() {
		return note_type_dec;
	}

	public void setNote_type_dec(String note_type_dec) {
		this.note_type_dec = note_type_dec;
	}

}
