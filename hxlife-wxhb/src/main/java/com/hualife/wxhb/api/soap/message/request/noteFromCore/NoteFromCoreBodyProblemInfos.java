package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProblemInfos {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";

	/**
	 * 问题件集合
	 */
	@XmlElement(name="ProblemInfo")
	private List<NoteFromCoreBodyProblemInfo> ProblemInfo = new ArrayList<NoteFromCoreBodyProblemInfo>();
	
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyProblemInfo> getProblemInfo() {
		return ProblemInfo;
	}
	public void setProblemInfo(List<NoteFromCoreBodyProblemInfo> problemInfo) {
		ProblemInfo = problemInfo;
	}
	
}
