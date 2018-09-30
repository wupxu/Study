package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProblemObject {
	/**
	 * 问题对象
	 */
	@XmlElement(name="Problem_object")
	private String problem_object = "";
	/**
	 * 对象需要回答的问题
	 */
	@XmlElement(name="Problems")
	private NoteFromCoreBodyProblems Problems = new NoteFromCoreBodyProblems();
	public String getProblem_object() {
		return problem_object;
	}
	public void setProblem_object(String problem_object) {
		this.problem_object = problem_object;
	}
	public NoteFromCoreBodyProblems getProblems() {
		return Problems;
	}
	public void setProblems(NoteFromCoreBodyProblems problems) {
		Problems = problems;
	}
	
}
