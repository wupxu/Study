package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodySurvivalInfos {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";

	/**
	 * 契调函信息集合
	 */
	@XmlElement(name="SurvivalInfo")
	private List<NoteFromCoreBodySurvivalInfo> SurvivalInfo = new ArrayList<NoteFromCoreBodySurvivalInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodySurvivalInfo> getSurvivalInfo() {
		return SurvivalInfo;
	}
	public void setSurvivalInfo(List<NoteFromCoreBodySurvivalInfo> survivalInfo) {
		SurvivalInfo = survivalInfo;
	}
	
}
