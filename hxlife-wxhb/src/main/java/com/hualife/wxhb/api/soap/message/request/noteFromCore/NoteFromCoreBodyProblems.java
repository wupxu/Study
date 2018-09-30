package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProblems {
	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";
	/**
	 * 问题列表
	 */
	@XmlElement(name="")
	private List<NoteFromCoreBodyProblem> Problem = new ArrayList<NoteFromCoreBodyProblem>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyProblem> getProblem() {
		return Problem;
	}
	public void setProblem(List<NoteFromCoreBodyProblem> problem) {
		Problem = problem;
	}
	
}
