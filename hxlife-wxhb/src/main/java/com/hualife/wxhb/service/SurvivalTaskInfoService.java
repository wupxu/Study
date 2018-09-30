package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.SurvivalTaskRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalTaskResponseMessage;
/**
* @author zhangweiwei
* @deprecation 返回契调函的影像信息service
* @date 2017-08-04
*/
public interface SurvivalTaskInfoService {
	/**
	 * 
	 * 返回契调函的影像信息
	 */
	public  SurvivalTaskResponseMessage getImageList(SurvivalTaskRequestMessage survivalTaskRequestMessage); 
}
