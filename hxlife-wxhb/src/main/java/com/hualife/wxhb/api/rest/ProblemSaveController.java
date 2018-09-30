package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.ProblemSaveRequestMessage;
import com.hualife.wxhb.service.ProblemSaveService;

/**
 * @author 张卫卫
 * @description 问题件提交
 * @date 2017-08-15
 */
@RestController
public class ProblemSaveController {
	
	@Autowired
	private ProblemSaveService problemSaveService;
	
	@RequestMapping("/problemSave")
    public ResponseResult <?> problemSave(@RequestBody ProblemSaveRequestMessage problemSaveRequestMessage) {	    
		problemSaveService.saveProblemSave(problemSaveRequestMessage);
    	return ResponseResultUtil.success();
    }
}
