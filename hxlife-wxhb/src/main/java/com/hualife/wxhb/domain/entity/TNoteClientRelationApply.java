package com.hualife.wxhb.domain.entity;

public class TNoteClientRelationApply {
    private String noteId;

    private String applyBarCode;

    private String applicantName;

    private String applicantPhone;

    private String insuredName;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getApplyBarCode() {
        return applyBarCode;
    }

    public void setApplyBarCode(String applyBarCode) {
        this.applyBarCode = applyBarCode;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }
}