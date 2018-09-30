package com.hualife.wxhb.domain.dto;
/**
 * @author 吴培旭
 * @description 批处理推送函件信息 其他类函件所用结果类
 * @time 创建时间：2017年8月31日
 */
public class PushMotion {

	private String noteType;// 函件类型

	private String isSecondNote;// 是否重新下发

	private String isNotQualifiedNote;// 是否次品单

	private String NoteSeq;// 核保任务序号

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getIsSecondNote() {
		return isSecondNote;
	}

	public void setIsSecondNote(String isSecondNote) {
		this.isSecondNote = isSecondNote;
	}

	public String getIsNotQualifiedNote() {
		return isNotQualifiedNote;
	}

	public void setIsNotQualifiedNote(String isNotQualifiedNote) {
		this.isNotQualifiedNote = isNotQualifiedNote;
	}

	public String getNoteSeq() {
		return NoteSeq;
	}

	public void setNoteSeq(String noteSeq) {
		NoteSeq = noteSeq;
	}

}
