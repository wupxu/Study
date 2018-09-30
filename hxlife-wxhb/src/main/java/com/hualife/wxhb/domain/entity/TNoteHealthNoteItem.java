package com.hualife.wxhb.domain.entity;

public class TNoteHealthNoteItem {
    private String healthNoteId;

    private String noteItemId;

    private String noteItemType;

    private String noteItemStatus;

    public String getHealthNoteId() {
        return healthNoteId;
    }

    public void setHealthNoteId(String healthNoteId) {
        this.healthNoteId = healthNoteId;
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

    public String getNoteItemStatus() {
        return noteItemStatus;
    }

    public void setNoteItemStatus(String noteItemStatus) {
        this.noteItemStatus = noteItemStatus;
    }
}