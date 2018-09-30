package com.hualife.wxhb.api.rest.message.request;


/**
 * @description:财务函提交请求参数
 * @author : 林永涛 
 * @date :2017-08-03 15:00:00
 */
public class FinaLetterSaveRequestMessage {
	/**
	 * 财务函id
	 * **/
	private String fina_note_id;
	/**
	 * 资料上传方式
	 * **/
	private String fina_choose_type;
	/**
	 * 函件提交人员类型--是客户/代理人在提交调用接口
	 * **/
	private String fina_submit_type;
	/**
	 * 业务员备注描述
	 * **/
	private String agent_remark_desc;
	public String getFina_note_id() {
		return fina_note_id;
	}
	public void setFina_note_id(String fina_note_id) {
		this.fina_note_id = fina_note_id;
	}
	public String getFina_choose_type() {
		return fina_choose_type;
	}
	public void setFina_choose_type(String fina_choose_type) {
		this.fina_choose_type = fina_choose_type;
	}
	public String getFina_submit_type() {
		return fina_submit_type;
	}
	public void setFina_submit_type(String fina_submit_type) {
		this.fina_submit_type = fina_submit_type;
	}
	public String getAgent_remark_desc() {
		return agent_remark_desc;
	}
	public void setAgent_remark_desc(String agent_remark_desc) {
		this.agent_remark_desc = agent_remark_desc;
	}

		
	
}
