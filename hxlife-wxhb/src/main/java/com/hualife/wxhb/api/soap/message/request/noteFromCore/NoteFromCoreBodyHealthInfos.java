package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyHealthInfos {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";

	/**
	 * 健康函
	 */
	@XmlElement(name="HealthInfo")
	private List<NoteFromCoreBodyHealthInfo> HealthInfo = new ArrayList<NoteFromCoreBodyHealthInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyHealthInfo> getHealthInfo() {
		return HealthInfo;
	}
	public void setHealthInfo(List<NoteFromCoreBodyHealthInfo> healthInfo) {
		HealthInfo = healthInfo;
	}
	
}
