package com.hualife.wxhb.domain.entity;

public class TNoteHealthNote {
    private String healthNoteId;

    private String clientName;

    private Integer clientAge;

    private String clientSex;

    private String specialDesc;

    private String otherInvest;

    private String agentRemarkDesc;

    public String getHealthNoteId() {
        return healthNoteId;
    }

    public void setHealthNoteId(String healthNoteId) {
        this.healthNoteId = healthNoteId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientAge() {
        return clientAge;
    }

    public void setClientAge(Integer clientAge) {
        this.clientAge = clientAge;
    }

    public String getClientSex() {
        return clientSex;
    }

    public void setClientSex(String clientSex) {
        this.clientSex = clientSex;
    }

    public String getSpecialDesc() {
        return specialDesc;
    }

    public void setSpecialDesc(String specialDesc) {
        this.specialDesc = specialDesc;
    }

    public String getOtherInvest() {
        return otherInvest;
    }

    public void setOtherInvest(String otherInvest) {
        this.otherInvest = otherInvest;
    }

    public String getAgentRemarkDesc() {
        return agentRemarkDesc;
    }

    public void setAgentRemarkDesc(String agentRemarkDesc) {
        this.agentRemarkDesc = agentRemarkDesc;
    }
}