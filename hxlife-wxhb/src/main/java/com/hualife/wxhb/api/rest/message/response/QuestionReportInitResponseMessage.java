package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;

/**
 * @description : 问卷和报告初始化返回报文
 * @author : linyongtao
 * @date : 2017.08.31
 */
public class QuestionReportInitResponseMessage {
	/**
	 * 是否完成问卷
	 * **/
	private String isFinish;
	private List<ImageInfo> imageInfo;

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	public List<ImageInfo> getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(List<ImageInfo> imageInfo) {
		this.imageInfo = imageInfo;
	}
	
}
