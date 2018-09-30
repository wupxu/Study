package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyPhysicalItem {
	
	/**
	 * 体检项目
	 */
	@XmlElement(name="Note_item_type")
	private String Note_item_type = "";

	public String getNote_item_type() {
		return Note_item_type;
	}

	public void setNote_item_type(String note_item_type) {
		Note_item_type = note_item_type;
	}
	
}
