package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TNoteTrace {
    private String everyNoteId;

    private String noteId;

    private String noteType;

    private String trackSeq;

    private String trackDesc;

    private Date createdDate;

    public String getEveryNoteId() {
        return everyNoteId;
    }

    public void setEveryNoteId(String everyNoteId) {
        this.everyNoteId = everyNoteId;
    }

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

    public String getTrackSeq() {
        return trackSeq;
    }

    public void setTrackSeq(String trackSeq) {
        this.trackSeq = trackSeq;
    }

    public String getTrackDesc() {
        return trackDesc;
    }

    public void setTrackDesc(String trackDesc) {
        this.trackDesc = trackDesc;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}