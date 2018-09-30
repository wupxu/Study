package com.hualife.wxhb.domain.dto;

/**
 * @author 吴培旭
 * @description 批处理推送函件信息组织输入报文时--查询出的结果类
 * @time 创建时间：2017年8月22日
 */
public class PushMessage {

	private String noteId;// 主键

	private String noteType;// 函件类型

	private String applyBarCode;// 投保单号

	private String noteSeq;// 核保序列号
	
	private String isNotQualifiedNote;//是否为次品单
	
	private String isSecondNote;//是否为重新下发

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getApplyBarCode() {
		return applyBarCode;
	}

	public void setApplyBarCode(String applyBarCode) {
		this.applyBarCode = applyBarCode;
	}

	public String getNoteSeq() {
		return noteSeq;
	}

	public void setNoteSeq(String noteSeq) {
		this.noteSeq = noteSeq;
	}

	public String getIsNotQualifiedNote() {
		return isNotQualifiedNote;
	}

	public void setIsNotQualifiedNote(String isNotQualifiedNote) {
		this.isNotQualifiedNote = isNotQualifiedNote;
	}

	public String getIsSecondNote() {
		return isSecondNote;
	}

	public void setIsSecondNote(String isSecondNote) {
		this.isSecondNote = isSecondNote;
	}

	
	

}
