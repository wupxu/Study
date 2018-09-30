package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.SurvivalInvestInfoRequestMeassage;
import com.hualife.wxhb.api.rest.message.response.SurvivalInvestInfoResponseMessage;

/**
 * @description:初始化契调通知书信息控制类
 * @author yangpeixin
 * @date 2017-08-11
 */
public interface SurvivalInvestInfoService {
	 /**
     * 契调通知书信息查
     * @param 
     * @return
     */	
	public SurvivalInvestInfoResponseMessage survivalInvestInfo(SurvivalInvestInfoRequestMeassage survivalInvestInfoRequest);
}
