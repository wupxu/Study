 package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.ProblemSaveRequestMessage;

/**
 * @author 张卫卫
 * @description 问题件提交service类
 * @date 2017-08-15
 * **/
public interface ProblemSaveService {
	/**
	 * 保存问题件信息
	 * **/
	public  void saveProblemSave(ProblemSaveRequestMessage problemSaveRequestMessage);

}
