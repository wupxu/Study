package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TTaskPushMerge {
	private String clientNoteId;
	private String NoteFtpImageUrl;
	private String NoteFtpImageFileName;
	private String phoneFtpUrl;
	private String phoneFtpFileName;
	private String pushStatus;
	private Date createDate;
	private Date updateDate;
	private String manageCom;
	private String channelType;

	
	
	public String getNoteFtpImageFileName() {
		return NoteFtpImageFileName;
	}

	public void setNoteFtpImageFileName(String noteFtpImageFileName) {
		NoteFtpImageFileName = noteFtpImageFileName;
	}

	public String getPhoneFtpFileName() {
		return phoneFtpFileName;
	}

	public void setPhoneFtpFileName(String phoneFtpFileName) {
		this.phoneFtpFileName = phoneFtpFileName;
	}

	public String getManageCom() {
		return manageCom;
	}

	public void setManageCom(String manageCom) {
		this.manageCom = manageCom;
	}

	public String getClientNoteId() {
		return clientNoteId;
	}

	public void setClientNoteId(String clientNoteId) {
		this.clientNoteId = clientNoteId;
	}

	public String getNoteFtpImageUrl() {
		return NoteFtpImageUrl;
	}

	public void setNoteFtpImageUrl(String noteFtpImageUrl) {
		NoteFtpImageUrl = noteFtpImageUrl;
	}

	public String getPhoneFtpUrl() {
		return phoneFtpUrl;
	}

	public void setPhoneFtpUrl(String phoneFtpUrl) {
		this.phoneFtpUrl = phoneFtpUrl;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

}
