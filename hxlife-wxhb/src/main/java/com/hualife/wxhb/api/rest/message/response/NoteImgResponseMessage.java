package com.hualife.wxhb.api.rest.message.response;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
/**
 * @author zhangweiwei
 * @deprecation影像上传返回报文类
 * @date 2017-08-04
 */
public class NoteImgResponseMessage {
	/**
	 * 返回影像信息
	 */
	private ImageInfo imageInfo;

	public ImageInfo getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(ImageInfo imageInfo) {
		this.imageInfo = imageInfo;
	}
	
}
