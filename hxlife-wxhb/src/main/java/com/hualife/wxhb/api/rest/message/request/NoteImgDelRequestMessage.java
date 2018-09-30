package com.hualife.wxhb.api.rest.message.request;

/**
 * @author zhangweiwei
 * @deprecation 影像上传的请求报文
 * @date 2017-08-22
 */
public class NoteImgDelRequestMessage {
	/**
	 * 影像id
	 */
	private String image_id;

	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	
}
