package com.hualife.wxhb.domain.dto;
/** 
 * @author 吴培旭 
 * @description 推送函件信息所用中间表类
 * @time 创建时间：2017年8月26日   
 */
public class PushnotePrint {

	private String noteId;//noteid
	
	private String taskCode;//核保任务号
	
	private String noteType;//客户层函件的类型
	
	private String noteSeq;//核保任务序号
	
	private String notePushStatus;//函件推送状态
	
	private String createDate;//创建时间
	
	private String updateDate;//修改时间

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getNoteSeq() {
		return noteSeq;
	}

	public void setNoteSeq(String noteSeq) {
		this.noteSeq = noteSeq;
	}

	public String getNotePushStatus() {
		return notePushStatus;
	}

	public void setNotePushStatus(String notePushStatus) {
		this.notePushStatus = notePushStatus;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	
	
	
}
