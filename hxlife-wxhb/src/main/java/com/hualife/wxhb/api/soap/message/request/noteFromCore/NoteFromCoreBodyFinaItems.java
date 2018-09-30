package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyFinaItems {
	/**
	 * 条数
	 */
	@XmlElement(name="")
	private String Count = "0";
	/**
	 * 财务函问卷类型列表
	 */
	@XmlElement(name="")
	private List<NoteFromCoreBodyFinaItem> FinaItem = new ArrayList<NoteFromCoreBodyFinaItem>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyFinaItem> getFinaItem() {
		return FinaItem;
	}
	public void setFinaItem(List<NoteFromCoreBodyFinaItem> finaItem) {
		FinaItem = finaItem;
	}
}
