package com.hualife.wxhb.domain.entity;

import java.util.Date;

public class TNoteImage {
    private String imageId;

    private String imageType;

    private String noteId;

    private String noteType;
    
    private String professionalId;

    private String imageName;

    private String imageFile;

    private Date createdDate;

    private String localhostUrl;

    private String isSendSuss;

    private String imageStatus;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

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

	public String getProfessionalId() {
		return professionalId;
	}

	public void setProfessionalId(String professionalId) {
		this.professionalId = professionalId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getLocalhostUrl() {
		return localhostUrl;
	}

	public void setLocalhostUrl(String localhostUrl) {
		this.localhostUrl = localhostUrl;
	}

	public String getIsSendSuss() {
		return isSendSuss;
	}

	public void setIsSendSuss(String isSendSuss) {
		this.isSendSuss = isSendSuss;
	}

	public String getImageStatus() {
		return imageStatus;
	}

	public void setImageStatus(String imageStatus) {
		this.imageStatus = imageStatus;
	}
 
}