package com.hualife.wxhb.api.rest.message.request;

/**
 * @description 核保函(代理人/客户)初始化请求报文实体类
 * @author wangt
 * @date 2017-08-09
 */
public class UndwrtAgentNoteInfoRequestMessage {

	private String note_undwrt_id;	//核保涵ID
	
	public String getNote_undwrt_id() {
		return note_undwrt_id;
	}
	public void setNote_undwrt_id(String note_undwrt_id) {
		this.note_undwrt_id = note_undwrt_id;
	}	
	
}
