package com.hualife.wxhb.api.rest.message.pojo;

/**
 * @author yangpeixin
 * @description 返回参数
 * @date 2017-08-04
 */

public class Health {
	/**
	 * 函件ID
	 */
	private String  health_note_id;
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
	private String   note_type;
	
	

	public String getHealth_note_id() {
		return health_note_id;
	}

	public void setHealth_note_id(String health_note_id) {
		this.health_note_id = health_note_id;
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
