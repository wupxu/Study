package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyRelevanceInfos {
	
	/**
	 * 次数
	 */
	@XmlElement(name="Count")
	private String Count = "0";
	
	/**
	 * 保单对象
	 */
	@XmlElement(name="RelevanceInfo")
	private List<NoteFromCoreBodyRelevanceInfo> RelevanceInfo = new ArrayList<NoteFromCoreBodyRelevanceInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyRelevanceInfo> getRelevanceInfo() {
		return RelevanceInfo;
	}
	public void setRelevanceInfo(List<NoteFromCoreBodyRelevanceInfo> relevanceInfo) {
		RelevanceInfo = relevanceInfo;
	}
	
}
