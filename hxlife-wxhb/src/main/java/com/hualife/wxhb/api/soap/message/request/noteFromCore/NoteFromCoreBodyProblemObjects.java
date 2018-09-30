package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProblemObjects {
	
	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String count = "0";
	
	/**
	 * 问题件对象列表
	 */
	@XmlElement(name="ProblemObject")
	private List<NoteFromCoreBodyProblemObject> problemObject = new ArrayList<NoteFromCoreBodyProblemObject>();
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<NoteFromCoreBodyProblemObject> getProblemObject() {
		return problemObject;
	}
	public void setProblemObject(List<NoteFromCoreBodyProblemObject> problemObject) {
		this.problemObject = problemObject;
	}
	
	
}
