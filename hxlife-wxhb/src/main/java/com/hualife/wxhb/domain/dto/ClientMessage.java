package com.hualife.wxhb.domain.dto;
/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年9月18日   
 */
public class ClientMessage {

	private String note_type;//客户层函件类型
	
	private String client_note_id;//id

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
