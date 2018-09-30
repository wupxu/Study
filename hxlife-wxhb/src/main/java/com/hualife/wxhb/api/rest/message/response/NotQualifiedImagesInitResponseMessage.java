package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;

/**
 * @desc :次品单-客户上传影像资料返回报文
 * @author : linyongtao
 * @time : 2017-08-21  17:58:00
 */
public class NotQualifiedImagesInitResponseMessage {
	/**
	 * 次品单下发原因
	 * **/
	private String is_not_qualified_note;
	/**
	 * 影像资料
	 * **/
	private List<ImageInfo> imageInfo;
	public String getIs_not_qualified_note() {
		return is_not_qualified_note;
	}
	public void setIs_not_qualified_note(String is_not_qualified_note) {
		this.is_not_qualified_note = is_not_qualified_note;
	}
	public List<ImageInfo> getImageInfo() {
		return imageInfo;
	}
	public void setImageInfo(List<ImageInfo> imageInfo) {
		this.imageInfo = imageInfo;
	}
	
	
	
}
