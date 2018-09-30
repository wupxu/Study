package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteAllTask;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteMyTask;

/**
 * @description 契调任务初始化返回报文实体类
 * @author wangt
 * @date 2017-08-07
 */
public class SurvivalNoteTaskInfoResponseMessage {
	
	private String survival_branch_name;					//契调人所属机构名称
	private List<SurvivalNoteAllTask> allTasks;				//所有任务列表
	private List<SurvivalNoteMyTask> myTasks;				//我的任务列表
	

	public String getSurvival_branch_name() {
		return survival_branch_name;
	}
	public void setSurvival_branch_name(String survival_branch_name) {
		this.survival_branch_name = survival_branch_name;
	}
	public List<SurvivalNoteAllTask> getAllTasks() {
		return allTasks;
	}
	public void setAllTasks(List<SurvivalNoteAllTask> allTasks) {
		this.allTasks = allTasks;
	}
	public List<SurvivalNoteMyTask> getMyTasks() {
		return myTasks;
	}
	public void setMyTasks(List<SurvivalNoteMyTask> myTasks) {
		this.myTasks = myTasks;
	}
	
	
}
