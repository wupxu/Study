package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.PhysicalNoteSaveRequestMessage;
import com.hualife.wxhb.service.PhysicalNoteSaveService;
/**
 * @author zhangweiwei
 * @description 体检函提交
 * @date 2017-08-05
 */
@RestController
public class PhysicalNoteSaveConroller {

	@Autowired
	private PhysicalNoteSaveService physicalNoteSaveService;
	
	@RequestMapping("/physicalNoteSave")
    public ResponseResult<?> physicalNoteSave(@RequestBody PhysicalNoteSaveRequestMessage physicalNoteSaveRequestMessage) {	    
		physicalNoteSaveService.updatePhysicalNote(physicalNoteSaveRequestMessage);
    	return ResponseResultUtil.success();
    }
}
