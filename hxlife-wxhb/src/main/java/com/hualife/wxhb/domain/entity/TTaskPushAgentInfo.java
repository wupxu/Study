package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TTaskPushAgentInfo {
    private String noteId;

    private String modeId;

    private String clientNoteId;

    private String pushObjectNo;

    private String pushDesc;

    private String pushStatus;

    private Date pushDate;

    private Date createdDate;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

    public String getClientNoteId() {
        return clientNoteId;
    }

    public void setClientNoteId(String clientNoteId) {
        this.clientNoteId = clientNoteId;
    }

    public String getPushObjectNo() {
        return pushObjectNo;
    }

    public void setPushObjectNo(String pushObjectNo) {
        this.pushObjectNo = pushObjectNo;
    }

    public String getPushDesc() {
        return pushDesc;
    }

    public void setPushDesc(String pushDesc) {
        this.pushDesc = pushDesc;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}