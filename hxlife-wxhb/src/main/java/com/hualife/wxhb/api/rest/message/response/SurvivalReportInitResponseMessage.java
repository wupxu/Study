package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.AppliInfo;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContent;

/**
 * @description : 契调报告初始化返回报文
 * @author : linyongtao
 * @time : 2017-08-17
 */
public class SurvivalReportInitResponseMessage {
	/**
	 * 函件id
	 * **/
	private String note_id;
	/**
	 * 投保人信息，投保人+被保人+投保单号
	 * **/
	private List<AppliInfo> appliInfo;
	/**
	 * 客戶上传的影像资料
	 * **/
	private List<ImageInfo> imageInfo;
	/**
	 * 契调问题
	 * **/
	private List<ProblemContent> problemContent;
		
	public String getNote_id() {
		return note_id;
	}

	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}

	public List<AppliInfo> getAppliInfo() {
		return appliInfo;
	}

	public void setAppliInfo(List<AppliInfo> appliInfo) {
		this.appliInfo = appliInfo;
	}

	public List<ImageInfo> getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(List<ImageInfo> imageInfo) {
		this.imageInfo = imageInfo;
	}

	public List<ProblemContent> getProblemContent() {
		return problemContent;
	}

	public void setProblemContent(List<ProblemContent> problemContent) {
		this.problemContent = problemContent;
	}


	
}
