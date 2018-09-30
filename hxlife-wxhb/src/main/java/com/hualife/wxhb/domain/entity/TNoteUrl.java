package com.hualife.wxhb.domain.entity;

public class TNoteUrl {
    private String noteId;

    private String noteType;

    private String noteImageUrl;

    private String noteOssUrl;

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

    public String getNoteImageUrl() {
        return noteImageUrl;
    }

    public void setNoteImageUrl(String noteImageUrl) {
        this.noteImageUrl = noteImageUrl;
    }

    public String getNoteOssUrl() {
        return noteOssUrl;
    }

    public void setNoteOssUrl(String noteOssUrl) {
        this.noteOssUrl = noteOssUrl;
    }
}