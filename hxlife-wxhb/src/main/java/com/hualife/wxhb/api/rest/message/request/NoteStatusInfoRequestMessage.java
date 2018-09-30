package com.hualife.wxhb.api.rest.message.request;
/**
 * 函件状态(代理人)的请求报文
 * @author zhangweiwei
 * @date 2017-08-04
 */
public class NoteStatusInfoRequestMessage {
	/**
	 * 函件id
	 */
	public String note_id;
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
}
