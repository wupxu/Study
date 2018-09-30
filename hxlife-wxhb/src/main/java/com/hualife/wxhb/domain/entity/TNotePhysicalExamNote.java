package com.hualife.wxhb.domain.entity;

public class TNotePhysicalExamNote {
    private String physicalNoteId;

    private String clientChooseType;

    private String isUploadSelfImage;

    private String remarkDesc;

    private String isSelfHealth;

    private String agentChooseType;

    private String agentRemarkDesc;

    public String getPhysicalNoteId() {
        return physicalNoteId;
    }

    public void setPhysicalNoteId(String physicalNoteId) {
        this.physicalNoteId = physicalNoteId;
    }

    public String getClientChooseType() {
        return clientChooseType;
    }

    public void setClientChooseType(String clientChooseType) {
        this.clientChooseType = clientChooseType;
    }

    public String getIsUploadSelfImage() {
        return isUploadSelfImage;
    }

    public void setIsUploadSelfImage(String isUploadSelfImage) {
        this.isUploadSelfImage = isUploadSelfImage;
    }

    public String getRemarkDesc() {
        return remarkDesc;
    }

    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    public String getIsSelfHealth() {
        return isSelfHealth;
    }

    public void setIsSelfHealth(String isSelfHealth) {
        this.isSelfHealth = isSelfHealth;
    }

    public String getAgentChooseType() {
        return agentChooseType;
    }

    public void setAgentChooseType(String agentChooseType) {
        this.agentChooseType = agentChooseType;
    }

    public String getAgentRemarkDesc() {
        return agentRemarkDesc;
    }

    public void setAgentRemarkDesc(String agentRemarkDesc) {
        this.agentRemarkDesc = agentRemarkDesc;
    }
}