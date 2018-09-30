package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalInvestInfoRequestMeassage;
import com.hualife.wxhb.api.rest.message.response.SurvivalInvestInfoResponseMessage;
import com.hualife.wxhb.service.SurvivalInvestInfoService;

/**
 * @description:初始化契调通知书信息控制类
 * @author yangpeixin
 * @date 2017-08-11
 */
@RestController
public class SurvivalInvestInfoController {	
	@Autowired
	private SurvivalInvestInfoService survivalInvestInfoService;
	 /**
     * 契调通知书信息查
     * @param 
     * @return
     */	
	@RequestMapping(path = "/survivalInvestInfo")  
	public ResponseResult<SurvivalInvestInfoResponseMessage> survivalInvestInfo(@RequestBody SurvivalInvestInfoRequestMeassage survivalInvestInfoRequestMeassage) {
		SurvivalInvestInfoResponseMessage survivalInvestInfoResponseMessage = survivalInvestInfoService.survivalInvestInfo(survivalInvestInfoRequestMeassage);
	  	return ResponseResultUtil.success(survivalInvestInfoResponseMessage);
	}
	
}
