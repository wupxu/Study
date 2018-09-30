package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.UndwrtClientSaveRequestMessage;
import com.hualife.wxhb.api.rest.message.response.UndwrtClientSaveResponseMessage;
import com.hualife.wxhb.service.UndwrtClientSaveService;

/**
 * @description 核保涵提交
 * @author wangt
 * @date 2017-08-03
 */
@RestController
public class UndwrtClientSaveController {

	@Autowired
	private UndwrtClientSaveService undwrtClientSaveService;

	@RequestMapping("/undwrtClientSave")
	public ResponseResult<UndwrtClientSaveResponseMessage> undwrtClientSave(@RequestBody UndwrtClientSaveRequestMessage undwrtClientSaveRequestMessage) {
		UndwrtClientSaveResponseMessage undwrtClientSaveResponseMessage = undwrtClientSaveService.undwrtClientSave(undwrtClientSaveRequestMessage);
		return ResponseResultUtil.success(undwrtClientSaveResponseMessage);

	}

}
