package com.hualife.wxhb.api.rest.message.response;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;

/**
 * @author zhangweiwei
 * @deprecation 投保人查看被保人的异地签名的返回报文
 * @date 2017-09-11
 */
public class RemoteSignatureResponseMessage {
	private ImageInfo imageInfo;

	public ImageInfo getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(ImageInfo imageInfo) {
		this.imageInfo = imageInfo;
	}
	
}
