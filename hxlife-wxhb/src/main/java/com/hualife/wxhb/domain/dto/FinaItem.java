package com.hualife.wxhb.domain.dto;

/**
 * @author 吴培旭
 * @description 财务问卷
 * @time 创建时间：2017年8月5日
 */
public class FinaItem {
    
	private String noteItemId;//财务问卷id
	
	private String noteItemType;//财务问卷类型
	
	private String noteItemStatus;//问卷状态
	
	private String noteItemTypeName;//财务问卷名称
	
	public String getNoteItemStatus() {
		return noteItemStatus;
	}

	public void setNoteItemStatus(String noteItemStatus) {
		this.noteItemStatus = noteItemStatus;
	}

	public String getNoteItemId() {
		return noteItemId;
	}

	public void setNoteItemId(String noteItemId) {
		this.noteItemId = noteItemId;
	}

	public String getNoteItemType() {
		return noteItemType;
	}

	public void setNoteItemType(String noteItemType) {
		this.noteItemType = noteItemType;
	}

	public String getNoteItemTypeName() {
		return noteItemTypeName;
	}

	public void setNoteItemTypeName(String noteItemTypeName) {
		this.noteItemTypeName = noteItemTypeName;
	}
	
}