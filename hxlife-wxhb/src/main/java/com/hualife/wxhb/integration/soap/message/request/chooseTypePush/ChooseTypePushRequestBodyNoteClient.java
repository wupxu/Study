package com.hualife.wxhb.integration.soap.message.request.chooseTypePush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年8月29日
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ChooseTypePushRequestBodyNoteClient {
	/**
	 * 函件类型
	 */
	@XmlElement(name = "Note_seq")
	private String note_seq = "";
	/**
	 * 核保任务序号
	 */
	@XmlElement(name = "Note_type")
	private String note_type = "";

	public String getNote_seq() {
		return note_seq;
	}

	public void setNote_seq(String note_seq) {
		this.note_seq = note_seq;
	}

	public String getNote_type() {
		return note_type;
	}

	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}

}
