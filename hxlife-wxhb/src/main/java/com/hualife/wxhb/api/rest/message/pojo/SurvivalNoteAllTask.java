package com.hualife.wxhb.api.rest.message.pojo;

import java.util.List;
/**
 * @description 契调任务初始化返回报文-所有任务
 * @author wangt
 * @date 2017-08-07
 */
public class SurvivalNoteAllTask {
	
	private String survival_note_id;				//契调函id
	private String client_name;						//客户姓名
	private String survival_no;						//契调人工号
	private String survival_name;					//契调人姓名
	private String survival_reason;					//契调原因
	private String survival_branch_province;			//契调函原来所在省
	private List<SurvivalNoteTaskList> allTaskLists;	//任务信息
	
	public String getSurvival_reason() {
		return survival_reason;
	}
	public void setSurvival_reason(String survival_reason) {
		this.survival_reason = survival_reason;
	}
	
	public String getSurvival_note_id() {
		return survival_note_id;
	}
	public void setSurvival_note_id(String survival_note_id) {
		this.survival_note_id = survival_note_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getSurvival_no() {
		return survival_no;
	}
	public void setSurvival_no(String survival_no) {
		this.survival_no = survival_no;
	}
	public List<SurvivalNoteTaskList> getAllTaskLists() {
		return allTaskLists;
	}
	public void setAllTaskLists(List<SurvivalNoteTaskList> allTaskLists) {
		this.allTaskLists = allTaskLists;
	}
	public String getSurvival_branch_province() {
		return survival_branch_province;
	}
	public void setSurvival_branch_province(String survival_branch_province) {
		this.survival_branch_province = survival_branch_province;
	}
	public String getSurvival_name() {
		return survival_name;
	}
	public void setSurvival_name(String survival_name) {
		this.survival_name = survival_name;
	}
		
}
