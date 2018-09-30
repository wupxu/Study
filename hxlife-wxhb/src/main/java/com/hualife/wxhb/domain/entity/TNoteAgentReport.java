package com.hualife.wxhb.domain.entity;

public class TNoteAgentReport {
    private String noteReportId;

    private String reportQuestionId;

    private String reportQuestionAnswer;

    private String reportQuestionDesc;

    public String getNoteReportId() {
		return noteReportId;
	}

	public void setNoteReportId(String noteReportId) {
		this.noteReportId = noteReportId;
	}

	public String getReportQuestionId() {
        return reportQuestionId;
    }

    public void setReportQuestionId(String reportQuestionId) {
        this.reportQuestionId = reportQuestionId;
    }

    public String getReportQuestionAnswer() {
        return reportQuestionAnswer;
    }

    public void setReportQuestionAnswer(String reportQuestionAnswer) {
        this.reportQuestionAnswer = reportQuestionAnswer;
    }

    public String getReportQuestionDesc() {
        return reportQuestionDesc;
    }

    public void setReportQuestionDesc(String reportQuestionDesc) {
        this.reportQuestionDesc = reportQuestionDesc;
    }
}