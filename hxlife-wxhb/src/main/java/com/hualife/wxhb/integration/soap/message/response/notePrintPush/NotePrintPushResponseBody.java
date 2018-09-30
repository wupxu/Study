package com.hualife.wxhb.integration.soap.message.response.notePrintPush;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 
 * @author 吴培旭 
 * @description 批处理函件打印核心返回报文
 * @time 创建时间：2017年8月18日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NotePrintPushResponseBody {

	@XmlElement(name="Count")
	private String count = "";
	
	@XmlElement(name="Notes")
	private List<NotePrintPushResponseBodyNote> Notes = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<NotePrintPushResponseBodyNote> getNotes() {
		return Notes;
	}

	public void setNotes(List<NotePrintPushResponseBodyNote> notes) {
		Notes = notes;
	}
	
	

	
}
