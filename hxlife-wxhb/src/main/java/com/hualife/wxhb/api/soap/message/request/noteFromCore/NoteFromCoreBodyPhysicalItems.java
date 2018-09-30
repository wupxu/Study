package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyPhysicalItems {
	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";
	/**
	 * 体检函题目列表
	 */
	@XmlElement(name="PhysicalItem")
	private List<NoteFromCoreBodyPhysicalItem> PhysicalItem = new ArrayList<NoteFromCoreBodyPhysicalItem>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyPhysicalItem> getPhysicalItem() {
		return PhysicalItem;
	}
	public void setPhysicalItem(List<NoteFromCoreBodyPhysicalItem> physicalItem) {
		PhysicalItem = physicalItem;
	}
	
}
