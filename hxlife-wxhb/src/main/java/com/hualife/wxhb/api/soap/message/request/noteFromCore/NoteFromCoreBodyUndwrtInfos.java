package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyUndwrtInfos {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";

	/**
	 * 核保函集合
	 */
	@XmlElement(name="UndwrtInfo")
	private List<NoteFromCoreBodyUndwrtInfo> UndwrtInfo = new ArrayList<NoteFromCoreBodyUndwrtInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyUndwrtInfo> getUndwrtInfo() {
		return UndwrtInfo;
	}
	public void setUndwrtInfo(List<NoteFromCoreBodyUndwrtInfo> undwrtInfo) {
		UndwrtInfo = undwrtInfo;
	}
	
}
