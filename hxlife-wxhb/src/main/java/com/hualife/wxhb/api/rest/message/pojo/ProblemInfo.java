package com.hualife.wxhb.api.rest.message.pojo;

/**
 * 
 * @author zhangweiwei
 * @description 问题信息对象
 * @date 2017-08-03
 * 
 */
public class ProblemInfo {
	/**
	 * 问题编号
	 */
	private String 	problem_seq;
	/**
	 * 问题答案
	 */
	private String problem_answer;
	public String getProblem_seq() {
		return problem_seq;
	}
	public void setProblem_seq(String problem_seq) {
		this.problem_seq = problem_seq;
	}
	public String getProblem_answer() {
		return problem_answer;
	}
	public void setProblem_answer(String problem_answer) {
		this.problem_answer = problem_answer;
	}
	
}
