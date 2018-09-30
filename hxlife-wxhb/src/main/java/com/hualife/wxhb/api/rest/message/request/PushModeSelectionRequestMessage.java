package com.hualife.wxhb.api.rest.message.request;

/**
 * @author 吴培旭
 * @description rest接口下发方式入参对象
 * @time 创建时间：2017年8月5日 
 */
public class PushModeSelectionRequestMessage {

	private String note_id;//函件id
	
	private String push_type;//函件下发方式

	public String getNote_id() {
		return note_id;
	}

	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}

	public String getPush_type() {
		return push_type;
	}

	public void setPush_type(String push_type) {
		this.push_type = push_type;
	}

	

}
