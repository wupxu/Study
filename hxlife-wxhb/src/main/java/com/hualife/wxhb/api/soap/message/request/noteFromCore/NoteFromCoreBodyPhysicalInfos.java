package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyPhysicalInfos {
	
	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";
	
	/**
	 * 体检函信息对象
	 */
	@XmlElement(name="PhysicalInfo")
	private List<NoteFromCoreBodyPhysicalInfo> PhysicalInfo = new ArrayList<NoteFromCoreBodyPhysicalInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyPhysicalInfo> getPhysicalInfo() {
		return PhysicalInfo;
	}
	public void setPhysicalInfo(List<NoteFromCoreBodyPhysicalInfo> physicalInfo) {
		PhysicalInfo = physicalInfo;
	}
	
}
