package com.hualife.wxhb.api.rest.message.pojo;

import java.util.List;

/**
 * @description :客户层函件的报文
 * @author : linyongtao
 * @date : 2017.08.14
 */
public class ClientNoteInfos {
	/**
	 * 客户层函件id
	 * **/
	private String note_id;
	/**
	 * 函件关联客户姓名
	 * **/
	private String client_name;
	/**
	 * 函件状态
	 * **/
	private String note_status;
	/**
	 * 函件状态描述
	 * **/
	private String note_status_desc;
	/**
	 * 函件下发方式
	 * **/
	private String push_type;
	/**
	 * 投保人信息
	 * **/
	private List<AppliInfo> appliInfo;
	/**
	 * 函件状态及其类型
	 * **/
	private List<ClientNoteType> clientNoteType;
	
	public String getPush_type() {
		return push_type;
	}
	public void setPush_type(String push_type) {
		this.push_type = push_type;
	}
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getNote_status() {
		return note_status;
	}
	public void setNote_status(String note_status) {
		this.note_status = note_status;
	}
	public List<AppliInfo> getAppliInfo() {
		return appliInfo;
	}
	public void setAppliInfo(List<AppliInfo> appliInfo) {
		this.appliInfo = appliInfo;
	}
	public List<ClientNoteType> getClientNoteType() {
		return clientNoteType;
	}
	public void setClientNoteType(List<ClientNoteType> clientNoteType) {
		this.clientNoteType = clientNoteType;
	}
	public String getNote_status_desc() {
		return note_status_desc;
	}
	public void setNote_status_desc(String note_status_desc) {
		this.note_status_desc = note_status_desc;
	}

	
}
