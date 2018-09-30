package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TTaskPushNotePrint {
    private String noteId;

    private String noteSeq;

    private String noteType;

    private String taskCode;

    private String notePushStatus;

    private Date createDate;

    private Date updateDate;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteSeq() {
        return noteSeq;
    }

    public void setNoteSeq(String noteSeq) {
        this.noteSeq = noteSeq;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getNotePushStatus() {
        return notePushStatus;
    }

    public void setNotePushStatus(String notePushStatus) {
        this.notePushStatus = notePushStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}