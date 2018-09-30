package com.hualife.wxhb.api.rest.message.request;

/**
 * @author yangpeixin
 * @description 健康函提交
 * @date 2017-08-04
 */
public class HealthSubmitRequestMessage {
	/**
	 * 健康函ID
	 */
	private String health_note_id;
	/**
	 * 选择方式
	 */
	private String health_choose_type;

	/**
	 * 函件提交人员类型--是客户/代理人在提交调用接口
	 **/
	private String health_submit_type;
	/**
	 * 业务员备注描述
	 **/
	private String agent_remark_desc;



	public String getHealth_submit_type() {
		return health_submit_type;
	}

	public void setHealth_submit_type(String health_submit_type) {
		this.health_submit_type = health_submit_type;
	}

	public String getAgent_remark_desc() {
		return agent_remark_desc;
	}

	public void setAgent_remark_desc(String agent_remark_desc) {
		this.agent_remark_desc = agent_remark_desc;
	}

	public String getHealth_note_id() {
		return health_note_id;
	}

	public void setHealth_note_id(String health_note_id) {
		this.health_note_id = health_note_id;
	}

	public String getHealth_choose_type() {
		return health_choose_type;
	}

	public void setHealth_choose_type(String health_choose_type) {
		this.health_choose_type = health_choose_type;
	}

}
