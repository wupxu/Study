package com.hualife.wxhb.api.rest.message.request;
/**
 * @description 体检函提交的请求报文
 * @author zhangweiwei
 * @date 2017-08-03
 * 
 */
public class PhysicalNoteSaveRequestMessage {
	/**
	 * 体检函id
	 */
	private String physical_note_id;
	/**
	 * 客户选择方式
	 */
	private String client_choose_type;
	/**
	 * 代理人选择方式
	 */
	private String agent_choose_type;
	/**
	 * 代理人回答备注
	 */
	private String agent_remark_desc;

	public String getPhysical_note_id() {
		return physical_note_id;
	}

	public void setPhysical_note_id(String physical_note_id) {
		this.physical_note_id = physical_note_id;
	}

	public String getClient_choose_type() {
		return client_choose_type;
	}

	public void setClient_choose_type(String client_choose_type) {
		this.client_choose_type = client_choose_type;
	}

	public String getAgent_choose_type() {
		return agent_choose_type;
	}

	public void setAgent_choose_type(String agent_choose_type) {
		this.agent_choose_type = agent_choose_type;
	}

	public String getAgent_remark_desc() {
		return agent_remark_desc;
	}

	public void setAgent_remark_desc(String agent_remark_desc) {
		this.agent_remark_desc = agent_remark_desc;
	}
}