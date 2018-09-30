package com.hualife.wxhb.api.rest.message.request;

/**
 * @author zhangweiwei
 * @deprecation 影像上传的请求报文
 * @date 2017-08-22
 */
public class NoteImgRequestMessage {
	/**
	 * 函件id
	 */
	private String note_id;
	/**
	 * 函件类型
	 */
	private String note_type;
	/**
	 * 影像类型
	 */
	private String image_type;
	/**
	 * 微信媒体id
	 */
	private String media_id;// 服务id
	/**
	 * 影像签名流
	 */
	private String medias;

	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getMedias() {
		return medias;
	}
	public void setMedias(String medias) {
		this.medias = medias;
	}
	
}
