package com.hualife.wxhb.api.rest.message.pojo;

import java.util.List;

/**
 * @description 契调任务初始化返回报文实-我的任务
 * @author wangt
 * @date 2017-08-07
 */
public class SurvivalNoteMyTask {
	
	private String survival_note_id;			//契调函ID
	private String client_name;					//客户姓名
	private String survival_note_status_desc;	//契调函状态描述
	private String is_finish;					//契调报告是否完成
	private List<SurvivalNoteTaskList> myTaskLists;	//任务信息
	
	
	public String getSurvival_note_id() {
		return survival_note_id;
	}
	public void setSurvival_note_id(String survival_note_id) {
		this.survival_note_id = survival_note_id;
	}
	public String getSurvival_note_status_desc() {
		return survival_note_status_desc;
	}
	public void setSurvival_note_status_desc(String survival_note_status_desc) {
		this.survival_note_status_desc = survival_note_status_desc;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public List<SurvivalNoteTaskList> getMyTaskLists() {
		return myTaskLists;
	}
	public void setMyTaskLists(List<SurvivalNoteTaskList> myTaskLists) {
		this.myTaskLists = myTaskLists;
	}
	public String getIs_finish() {
		return is_finish;
	}
	public void setIs_finish(String is_finish) {
		this.is_finish = is_finish;
	}	
	
}
