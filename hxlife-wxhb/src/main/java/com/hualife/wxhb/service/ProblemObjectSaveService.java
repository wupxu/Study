 package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.ProblemObjectSaveRequestMessage;

/***
 * @author zhangweiwei
 * @description 代理人、客户填写问卷 service
 * @date 2017-08-03
 * */
public interface ProblemObjectSaveService {
	
	/**
	 * 保存问题件对象
	 * **/
	public  void problemObjectSave(ProblemObjectSaveRequestMessage objectSaveRequestMessage);

}
