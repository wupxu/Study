package com.hualife.wxhb.api.rest.message.request;
/***
 * 
 * @description : 体检函初始化请求参数
 * @author linyongtao
 * @date : 2017.08.14
 */
public class PhysicalLetterInitRequestMessage {
//	/**
//	 * 函件id
//	 * **/
//	private String note_id;
	/**
	 * 体检函id
	 * **/
	private String physical_note_id;
//	public String getNote_id() {
//		return note_id;
//	}
//	public void setNote_id(String note_id) {
//		this.note_id = note_id;
//	}
	public String getPhysical_note_id() {
		return physical_note_id;
	}
	public void setPhysical_note_id(String physical_note_id) {
		this.physical_note_id = physical_note_id;
	}
	
}
