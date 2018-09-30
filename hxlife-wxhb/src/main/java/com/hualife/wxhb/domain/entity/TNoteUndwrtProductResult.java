package com.hualife.wxhb.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TNoteUndwrtProductResult {
    private String noteUndwrtId;

    private String productCode;

    private String productName;

    private String undwrtResult;

    private String undwrtResultReason;

    private BigDecimal totalPrem;

    private BigDecimal totalOccuAddPrem;

    private BigDecimal totalHealthAddPrem;

    private String addExtraTremPeriodType;

    private Integer addExtraTremPeriod;

    private String exclustion;

    private BigDecimal cutFaceAmount;

    private BigDecimal limitdFaceAmount;

    private Integer limitdUnits;

    private String postponedPeriodType;

    private Integer postponedPeriod;

    private Date createdDate;

    public String getNoteUndwrtId() {
        return noteUndwrtId;
    }

    public void setNoteUndwrtId(String noteUndwrtId) {
        this.noteUndwrtId = noteUndwrtId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUndwrtResult() {
        return undwrtResult;
    }

    public void setUndwrtResult(String undwrtResult) {
        this.undwrtResult = undwrtResult;
    }

    public String getUndwrtResultReason() {
        return undwrtResultReason;
    }

    public void setUndwrtResultReason(String undwrtResultReason) {
        this.undwrtResultReason = undwrtResultReason;
    }

    public BigDecimal getTotalPrem() {
        return totalPrem;
    }

    public void setTotalPrem(BigDecimal totalPrem) {
        this.totalPrem = totalPrem;
    }

    public BigDecimal getTotalOccuAddPrem() {
        return totalOccuAddPrem;
    }

    public void setTotalOccuAddPrem(BigDecimal totalOccuAddPrem) {
        this.totalOccuAddPrem = totalOccuAddPrem;
    }

    public BigDecimal getTotalHealthAddPrem() {
        return totalHealthAddPrem;
    }

    public void setTotalHealthAddPrem(BigDecimal totalHealthAddPrem) {
        this.totalHealthAddPrem = totalHealthAddPrem;
    }

    public String getAddExtraTremPeriodType() {
        return addExtraTremPeriodType;
    }

    public void setAddExtraTremPeriodType(String addExtraTremPeriodType) {
        this.addExtraTremPeriodType = addExtraTremPeriodType;
    }

    public Integer getAddExtraTremPeriod() {
        return addExtraTremPeriod;
    }

    public void setAddExtraTremPeriod(Integer addExtraTremPeriod) {
        this.addExtraTremPeriod = addExtraTremPeriod;
    }

    public String getExclustion() {
        return exclustion;
    }

    public void setExclustion(String exclustion) {
        this.exclustion = exclustion;
    }

    public BigDecimal getCutFaceAmount() {
        return cutFaceAmount;
    }

    public void setCutFaceAmount(BigDecimal cutFaceAmount) {
        this.cutFaceAmount = cutFaceAmount;
    }

    public BigDecimal getLimitdFaceAmount() {
        return limitdFaceAmount;
    }

    public void setLimitdFaceAmount(BigDecimal limitdFaceAmount) {
        this.limitdFaceAmount = limitdFaceAmount;
    }

    public Integer getLimitdUnits() {
        return limitdUnits;
    }

    public void setLimitdUnits(Integer limitdUnits) {
        this.limitdUnits = limitdUnits;
    }

    public String getPostponedPeriodType() {
        return postponedPeriodType;
    }

    public void setPostponedPeriodType(String postponedPeriodType) {
        this.postponedPeriodType = postponedPeriodType;
    }

    public Integer getPostponedPeriod() {
        return postponedPeriod;
    }

    public void setPostponedPeriod(Integer postponedPeriod) {
        this.postponedPeriod = postponedPeriod;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}