package com.hualife.wxhb.api.rest.message.pojo;

/**
 * @author zhangweiwei
 * @deprecation 函件状态信息类
 * @date 2017-08-04
 */
public class NoteInfo {
	/**
	 * 客户层函件id
	 */
	private String client_note_id;
	/**
	 * 函件类型
	 */
	private String   noteType;
	/**
	 * 函件类型描述
	 */
	private String noteTypeDesc;
	/**
	 * 代理人查看函件状态
	 */
	private String note_agent_status;
	/**
	 * 代理人查看函件状态描述
	 */
	private String note_agent_status_desc;
	/**
	 * 次品单原因
	 */
	private String not_qualified_reason;
	public String getClient_note_id() {
		return client_note_id;
	}
	public void setClient_note_id(String client_note_id) {
		this.client_note_id = client_note_id;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public String getNoteTypeDesc() {
		return noteTypeDesc;
	}
	public void setNoteTypeDesc(String noteTypeDesc) {
		this.noteTypeDesc = noteTypeDesc;
	}
	public String getNote_agent_status() {
		return note_agent_status;
	}
	public void setNote_agent_status(String note_agent_status) {
		this.note_agent_status = note_agent_status;
	}
	public String getNote_agent_status_desc() {
		return note_agent_status_desc;
	}
	public void setNote_agent_status_desc(String note_agent_status_desc) {
		this.note_agent_status_desc = note_agent_status_desc;
	}
	public String getNot_qualified_reason() {
		return not_qualified_reason;
	}
	public void setNot_qualified_reason(String not_qualified_reason) {
		this.not_qualified_reason = not_qualified_reason;
	}

	
}
