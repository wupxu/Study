package com.hualife.wxhb.domain.dto;
/** 
 * @author 吴培旭 
 * @description 查询结果类
 * @time 创建时间：2017年8月26日   
 */
public class ProblemImage {

	private String imageId;
	
	private String imageName;
	
	private String imageFile;
	
	private String imageType;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
}
