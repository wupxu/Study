package com.hualife.wxhb.integration.soap.message.request.chooseTypePush;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/** 
 * @author 吴培旭 
 * @description 函件下发选择为机构打印时批处理输入参数报文类
 * @time 创建时间：2017年8月18日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ChooseTypePushRequestBody {

	@XmlElement(name="Taskcode")
	private String taskcode = "";//核保任务号
	
	@XmlElement(name="Note_Clients")
	private ChooseTypePushRequestBodyNoteClients Note_Clients = new ChooseTypePushRequestBodyNoteClients();

	public String getTaskcode() {
		return taskcode;
	}

	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	public ChooseTypePushRequestBodyNoteClients getNote_Clients() {
		return Note_Clients;
	}

	public void setNote_Clients(ChooseTypePushRequestBodyNoteClients note_Clients) {
		Note_Clients = note_Clients;
	}

	
	
	
}
