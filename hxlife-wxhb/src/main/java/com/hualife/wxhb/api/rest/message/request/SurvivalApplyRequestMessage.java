package com.hualife.wxhb.api.rest.message.request;
/**
 * @description : 契调任务申请/放弃：业务员对任务进行申请-请求参数
 * @author : linyongtao
 * @date : 2017.08.14
 */
public class SurvivalApplyRequestMessage {	
	/**
	 * 契调函id
	 * **/
	private String survival_note_id;
	/**
	 * 当前契调人工号	
	 * **/
	private String survival_no;
	/**
	 * 契调类型（申请/放弃）
	 * **/
	private String survival_type;
	
	
	public String getSurvival_type() {
		return survival_type;
	}
	public void setSurvival_type(String survival_type) {
		this.survival_type = survival_type;
	}
	public String getSurvival_note_id() {
		return survival_note_id;
	}
	public void setSurvival_note_id(String survival_note_id) {
		this.survival_note_id = survival_note_id;
	}
	public String getSurvival_no() {
		return survival_no;
	}
	public void setSurvival_no(String survival_no) {
		this.survival_no = survival_no;
	}
	
}
