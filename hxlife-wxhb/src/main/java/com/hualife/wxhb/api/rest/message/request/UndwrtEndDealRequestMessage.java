package com.hualife.wxhb.api.rest.message.request;

/**
 * @author zhangweiwei
 * @description核保函(代理人/客户)初始化请求报文实体类
 * @date 2017-08-12
 */
public class UndwrtEndDealRequestMessage {
	/**
	 * 核保函id
	 */
	private String note_undwrt_id;	
	
	public String getNote_undwrt_id() {
		return note_undwrt_id;
	}
	public void setNote_undwrt_id(String note_undwrt_id) {
		this.note_undwrt_id = note_undwrt_id;
	}	
	
}
