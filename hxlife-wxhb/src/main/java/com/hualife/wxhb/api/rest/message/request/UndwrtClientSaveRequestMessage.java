package com.hualife.wxhb.api.rest.message.request;
/**
 * @author wangt
 * @deprecation 客户提交核保涵请求报文实体类
 * @date 2017-08-08
 */
public class UndwrtClientSaveRequestMessage {
	
	private String note_undwrt_id;				//核保涵id
	private String client_answer_result;		//客户回答结果


	public String getNote_undwrt_id() {
		return note_undwrt_id;
	}

	public void setNote_undwrt_id(String note_undwrt_id) {
		this.note_undwrt_id = note_undwrt_id;
	}

	public String getClient_answer_result() {
		return client_answer_result;
	}

	public void setClient_answer_result(String client_answer_result) {
		this.client_answer_result = client_answer_result;
	}
	
}
