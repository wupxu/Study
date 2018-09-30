package com.hualife.wxhb.integration.soap.message.request.chooseTypePush;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年8月29日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ChooseTypePushRequestBodyNoteClients {

	@XmlElement(name="Count")
	private String count = "0";
	
	@XmlElement(name="Note_client")
	private List<ChooseTypePushRequestBodyNoteClient> Note_client = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<ChooseTypePushRequestBodyNoteClient> getNote_client() {
		return Note_client;
	}

	public void setNote_client(List<ChooseTypePushRequestBodyNoteClient> note_client) {
		Note_client = note_client;
	}
	
}
