package com.hualife.wxhb.integration.soap.message.response.notePrintPush;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月18日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NotePrintPushResponseBodyNotes {

	@XmlElement(name="Count")
	private String count = "0";
	
	@XmlElement(name="Note")
	private List<NotePrintPushResponseBodyNote> note = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<NotePrintPushResponseBodyNote> getNote() {
		return note;
	}

	public void setNote(List<NotePrintPushResponseBodyNote> note) {
		this.note = note;
	}
	
	
}
