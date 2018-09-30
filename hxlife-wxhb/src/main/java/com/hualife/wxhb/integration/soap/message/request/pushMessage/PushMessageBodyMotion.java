package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年8月22日
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageBodyMotion {
	/**
	 * 核保任务序号
	 */
	@XmlElement(name = "Note_seq")
	private String note_seq = "";
	/**
	 * 函件类型
	 */
	@XmlElement(name = "Note_type")
	private String note_type = "";
	/**
	 * 核保任务号
	 */
	@XmlElement(name = "Taskcode")
	private String taskCode = "";
	/**
	 * 回销类型(0-普通 1-次品单 2 重新下发)
	 */
	@XmlElement(name = "Buyback_type")
	private String buybackType = "";

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getBuybackType() {
		return buybackType;
	}

	public void setBuybackType(String buybackType) {
		this.buybackType = buybackType;
	}

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
