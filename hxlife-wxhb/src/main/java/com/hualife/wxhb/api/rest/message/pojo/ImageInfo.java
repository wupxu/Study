package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @author zhangweiwei
 * @description 影像信息类
 * @date 2017-08-04
 */
public class ImageInfo {
	/**
	 * 影像编号
	 */
	private String image_id;
	/**
	 * 影像类型
	 */
	private String image_type;
	/**
	 * 影像地址
	 */
	private String image_url;
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	
}
