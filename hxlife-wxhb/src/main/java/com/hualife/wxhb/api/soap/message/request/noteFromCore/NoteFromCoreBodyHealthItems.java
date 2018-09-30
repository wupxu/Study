package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyHealthItems {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";

	/**
	 * 健康问卷
	 */
	@XmlElement(name="HealthItem")
	private List<NoteFromCoreBodyHealthItem> HealthItem = new ArrayList<NoteFromCoreBodyHealthItem>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyHealthItem> getHealthItem() {
		return HealthItem;
	}
	public void setHealthItem(List<NoteFromCoreBodyHealthItem> healthItem) {
		HealthItem = healthItem;
	}
	
}
