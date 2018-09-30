package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProblem {
	/**
	 * 问题编号
	 */
	@XmlElement(name="Problem_seq")
	private String problem_seq = "";
	/**
	 * 问题内容描述
	 */
	@XmlElement(name="Problem_desc")
	private String problem_desc = "";
	/**
	 * 问题字段
	 */
	@XmlElement(name="Problem_Column")
	private String problem_Column = "";
	
	public String getProblem_seq() {
		return problem_seq;
	}
	public void setProblem_seq(String problem_seq) {
		this.problem_seq = problem_seq;
	}
	public String getProblem_desc() {
		return problem_desc;
	}
	public void setProblem_desc(String problem_desc) {
		this.problem_desc = problem_desc;
	}
	public String getProblem_Column() {
		return problem_Column;
	}
	public void setProblem_Column(String problem_Column) {
		this.problem_Column = problem_Column;
	}
	
}
