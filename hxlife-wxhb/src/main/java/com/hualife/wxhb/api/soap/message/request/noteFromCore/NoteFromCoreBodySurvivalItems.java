package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodySurvivalItems {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";

	/**
	 * 契调函条数集合
	 */
	@XmlElement(name="SurvivalItem")
	private List<NoteFromCoreBodySurvivalItem> SurvivalItem = new ArrayList<NoteFromCoreBodySurvivalItem>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodySurvivalItem> getSurvivalItem() {
		return SurvivalItem;
	}
	public void setSurvivalItem(List<NoteFromCoreBodySurvivalItem> survivalItem) {
		SurvivalItem = survivalItem;
	}
	
	
}
