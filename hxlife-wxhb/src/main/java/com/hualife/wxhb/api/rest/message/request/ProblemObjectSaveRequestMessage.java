package com.hualife.wxhb.api.rest.message.request;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ProblemInfo;

/**
 * 
 * @author zhangweiwei
 * @description 问题件对象提交接口报文录入报文body对象
 * @date 2017-08-03 
 * **/
public class ProblemObjectSaveRequestMessage {
	/**
	 * 问题件id
	 */
	 private String problem_note_id;
	 /**
	  * 问题件对象类型
	  */
	 private String problem_object;
	 /**
	  * 问题件对象列表信息
	  */
	 private List<ProblemInfo> problemInfos;
	public String getProblem_note_id() {
		return problem_note_id;
	}
	public void setProblem_note_id(String problem_note_id) {
		this.problem_note_id = problem_note_id;
	}
	public String getProblem_object() {
		return problem_object;
	}
	public void setProblem_object(String problem_object) {
		this.problem_object = problem_object;
	}
	public List<ProblemInfo> getProblemInfos() {
		return problemInfos;
	}
	public void setProblemInfos(List<ProblemInfo> problemInfos) {
		this.problemInfos = problemInfos;
	}
	
}
