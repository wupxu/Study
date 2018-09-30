package com.hualife.wxhb.api.rest.message.request;
/**
 * @description 健康函初始化请求报文实体类
 * @author wangt
 * @date 2017-08-08
 */
public class HealthNoteInitRequestMessage {
	
	private String health_note_id;			//健康函ID
	
	public String getHealth_note_id() {
		return health_note_id;
	}
	public void setHealth_note_id(String health_note_id) {
		this.health_note_id = health_note_id;
	}
	
}
