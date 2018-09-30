package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.HealthSubmitRequestMessage;
/**
 * @author yangpeixin
 * @description 健康函提交
 * @date 2017-08-04
 */
public interface HealthSubmitService {
	/**
	 * 健康提交
	 * **/
	public void healthNoteSave(HealthSubmitRequestMessage healthSubmitRequest);
		
	
}
