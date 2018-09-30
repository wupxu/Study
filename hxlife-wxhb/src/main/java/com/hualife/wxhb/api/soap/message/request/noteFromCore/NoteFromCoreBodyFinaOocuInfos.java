package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyFinaOocuInfos {
	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";
	
	/**
	 * 财务函信息
	 */
	@XmlElement(name="FinaOocuInfo")
	private List<NoteFromCoreBodyFinaOocuInfo> FinaOocuInfo = new ArrayList<NoteFromCoreBodyFinaOocuInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyFinaOocuInfo> getFinaOocuInfo() {
		return FinaOocuInfo;
	}
	public void setFinaOocuInfo(List<NoteFromCoreBodyFinaOocuInfo> finaOocuInfo) {
		FinaOocuInfo = finaOocuInfo;
	}
	
}
