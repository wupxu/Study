package com.hualife.wxhb.api.rest.message.request;

/**
 * @author 张卫卫
 * @description 问题件提交报文对象
 * @date 2017-08-15
 * **/
public class ProblemSaveRequestMessage {
	/**
	 * 问题件id
	 */
	 private String problem_note_id;

	public String getProblem_note_id() {
		return problem_note_id;
	}

	public void setProblem_note_id(String problem_note_id) {
		this.problem_note_id = problem_note_id;
	}
	 
}
