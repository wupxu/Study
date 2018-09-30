package com.hualife.wxhb.domain.dto;

/**
 * @author zhanyilong
 * @deprecation 查询代理人函件状态
 * @date 2017-08-23
 */
public class ProblemNoteAnswerInfo {
	/**
	 * 问题对象id
	 */
    private String problem_object_id; 
    /**
     * 问题回答内容
     */
    private String problem_answer;
    /**
     * 问题编号
     */
    private String problem_seq;

	public String getProblem_object_id() {
		return problem_object_id;
	}

	public void setProblem_object_id(String problem_object_id) {
		this.problem_object_id = problem_object_id;
	}

	public String getProblem_answer() {
		return problem_answer;
	}

	public void setProblem_answer(String problem_answer) {
		this.problem_answer = problem_answer;
	}

	public String getProblem_seq() {
		return problem_seq;
	}

	public void setProblem_seq(String problem_seq) {
		this.problem_seq = problem_seq;
	}
	
}
