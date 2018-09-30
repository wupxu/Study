package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TNoteProblemDetail {
    private String problemObjectId;

    private String problemSeq;

    private String problemDesc;

    private String problemAnswer;

    private String problemColumn;

    private Date createdDate;

    public String getProblemObjectId() {
        return problemObjectId;
    }

    public void setProblemObjectId(String problemObjectId) {
        this.problemObjectId = problemObjectId;
    }

    public String getProblemSeq() {
        return problemSeq;
    }

    public void setProblemSeq(String problemSeq) {
        this.problemSeq = problemSeq;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getProblemAnswer() {
        return problemAnswer;
    }

    public void setProblemAnswer(String problemAnswer) {
        this.problemAnswer = problemAnswer;
    }

    public String getProblemColumn() {
        return problemColumn;
    }

    public void setProblemColumn(String problemColumn) {
        this.problemColumn = problemColumn;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}