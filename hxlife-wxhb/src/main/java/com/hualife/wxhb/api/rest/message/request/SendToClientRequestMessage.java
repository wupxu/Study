package com.hualife.wxhb.api.rest.message.request;

/*@author zhangweiwei
* @description 契调任务初始化的请求报文
* @date 2017-08-04
*/
public class SendToClientRequestMessage {
	/**
	 * /函件id
	 */
	private String note_id;

	public String getNote_id() {
		return note_id;
	}

	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
}
