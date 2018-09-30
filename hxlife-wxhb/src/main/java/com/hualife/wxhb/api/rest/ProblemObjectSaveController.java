package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.ProblemObjectSaveRequestMessage;
import com.hualife.wxhb.service.ProblemObjectSaveService;
/**
 * @author 张卫卫
 * @description 问题件对象提交
 * @date 2017-08-08
 */
@RestController
public class ProblemObjectSaveController {
	
	@Autowired
	private ProblemObjectSaveService problemObjectSaveService;
	
	@RequestMapping("/problemObjectSave")
    public ResponseResult <?> problemObjectSave(@RequestBody ProblemObjectSaveRequestMessage problemObjectSaveRequestMessage) {	    
		problemObjectSaveService.problemObjectSave(problemObjectSaveRequestMessage);
    	return ResponseResultUtil.success();
    }
}
