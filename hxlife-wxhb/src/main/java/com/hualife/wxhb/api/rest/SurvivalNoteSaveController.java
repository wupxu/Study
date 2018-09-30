package com.hualife.wxhb.api.rest;
/** 
 * @author 吴培旭 
 * @description 契调报告保存
 * @time 创建时间：2017年8月4日   
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalSaveRequestMessage;
import com.hualife.wxhb.service.SurvivalNoteSaveService;
@RestController
public class SurvivalNoteSaveController {
	@Autowired
	private SurvivalNoteSaveService survivalNoteSaveService;
	@RequestMapping("/survivalNoteSave")
	public ResponseResult<?> survivalNoteSave(@RequestBody SurvivalSaveRequestMessage survivalSaveRequestMessage){
		//契调报告保存
		survivalNoteSaveService.survivalNoteSave(survivalSaveRequestMessage);
		return ResponseResultUtil.success();
		
	}
}
