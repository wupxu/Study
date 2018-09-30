package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodySurvivalItem {

	/**
	 * 问卷类型
	 */
	@XmlElement(name="Note_item_type")
	private String note_item_type = "";

	public String getNote_item_type() {
		return note_item_type;
	}

	public void setNote_item_type(String note_item_type) {
		this.note_item_type = note_item_type;
	}
	
}
