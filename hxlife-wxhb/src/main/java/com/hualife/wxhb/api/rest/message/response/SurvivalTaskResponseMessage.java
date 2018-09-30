package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
/**
 * @author zhangweiwei
 * @deprecation契调报告返回报文类
 * @date 2017-08-04
 */
public class SurvivalTaskResponseMessage {
	/**
	 * 函件id
	 */
	private String note_id;
	/**
	 * 返回影像列表信息
	 */
	private  List<ImageInfo> imageInfos;
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public List<ImageInfo> getImageInfos() {
		return imageInfos;
	}
	public void setImageInfos(List<ImageInfo> imageInfos) {
		this.imageInfos = imageInfos;
	}
	
}
