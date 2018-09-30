package com.hualife.wxhb.api.rest.message.request;
/**
 * @description :次品单-客户上传影像资料请求参数
 * @author : linyongtao
 * @time : 2017-08-21  17:58:00
 */
public class NotQualifiedImagesInitRequestMessage {
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
