package com.hualife.wxhb.api.rest.message.request;

/**
 * @description : 次品单影像上传请求参数（包括客户层函件次品单和问题件次品单，两个接口合二为一）
 * @author : linyongtao
 * @time : 2017-08-22  10:45:00
 */
public class NotQualifiedSaveRequestMessage {
	/**
	 * 函件id
	 * **/
	private String note_id;
	/**
	 * 函件类型
	 * **/
	private String note_type;
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
	
}




