package com.hualife.wxhb.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.ImageCallBackRequestMessage;

/**  
 * @Description: 影像回调系统
 * @author zhanglong 
 * @date 2017年9月19日 下午6:00:06  
 */
@RestController
public class ImageCallBackController {
	
	@RequestMapping("/imageCallBack")  
	public ResponseResult<?> imageCallBack(@RequestParam(name = "data") 
		ImageCallBackRequestMessage imageCallBackRequestMessage){
		
		return ResponseResultUtil.success();
	}
}
