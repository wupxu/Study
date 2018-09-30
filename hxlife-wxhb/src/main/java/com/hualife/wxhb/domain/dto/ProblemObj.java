package com.hualife.wxhb.domain.dto;

import java.util.List;

import com.hualife.wxhb.domain.dto.ProblemDetail;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年8月9日
 */
public class ProblemObj {

	private String problemObjectStatus;// 问题处理状态

	private String problemObject;// 问题对象类型

	private List<ProblemDetail> problem_descs;// 问题集合

	public List<ProblemDetail> getProblem_descs() {
		return problem_descs;
	}

	public void setProblem_descs(List<ProblemDetail> problem_descs) {
		this.problem_descs = problem_descs;
	}

	public String getProblemObjectStatus() {
		return problemObjectStatus;
	}

	public void setProblemObjectStatus(String problemObjectStatus) {
		this.problemObjectStatus = problemObjectStatus;
	}

	public String getProblemObject() {
		return problemObject;
	}

	public void setProblemObject(String problemObject) {
		this.problemObject = problemObject;
	}

}
