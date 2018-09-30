package com.hualife.wxhb.integration.soap.message.request.notePrintPush;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description 函件打印批处理输入参数报文类
 * @time 创建时间：2017年8月18日
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NotePrintPushBody {

	@XmlElement(name = "Count")
	private String count = "0";

	@XmlElement(name = "Notes")
	private List<NotePrintPushBodyNotes> notes = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<NotePrintPushBodyNotes> getNotes() {
		return notes;
	}

	public void setNotes(List<NotePrintPushBodyNotes> notes) {
		this.notes = notes;
	}

}
