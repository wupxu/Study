package com.hualife.wxhb.domain.entity;

public class TNoteProblemObject {
	
    private String problemNoteId;

    private String problemObjectId;

    private String problemObject;

    private String problemObjectStatus;

    public String getProblemNoteId() {
        return problemNoteId;
    }

    public void setProblemNoteId(String problemNoteId) {
        this.problemNoteId = problemNoteId;
    }

    public String getProblemObjectId() {
        return problemObjectId;
    }

    public void setProblemObjectId(String problemObjectId) {
        this.problemObjectId = problemObjectId;
    }

    public String getProblemObject() {
        return problemObject;
    }

    public void setProblemObject(String problemObject) {
        this.problemObject = problemObject;
    }

    public String getProblemObjectStatus() {
        return problemObjectStatus;
    }

    public void setProblemObjectStatus(String problemObjectStatus) {
        this.problemObjectStatus = problemObjectStatus;
    }
}