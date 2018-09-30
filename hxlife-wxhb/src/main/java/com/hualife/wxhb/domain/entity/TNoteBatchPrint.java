package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TNoteBatchPrint {
    private String batchId;

    private String clientNoteId;

    private String noteBarCode;

    private String printStatus;

    private Date pushPrintDate;

    private Date printDate;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getClientNoteId() {
        return clientNoteId;
    }

    public void setClientNoteId(String clientNoteId) {
        this.clientNoteId = clientNoteId;
    }

    public String getNoteBarCode() {
        return noteBarCode;
    }

    public void setNoteBarCode(String noteBarCode) {
        this.noteBarCode = noteBarCode;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public Date getPushPrintDate() {
        return pushPrintDate;
    }

    public void setPushPrintDate(Date pushPrintDate) {
        this.pushPrintDate = pushPrintDate;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }
}