package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.PushModeSelectionRequestMessage;
import com.hualife.wxhb.service.PushModeSelectionService;

/**
 * @author 吴培旭
 * @description 函件下发方式选择
 * @time 2017-08-04
 */
@RestController
public class PushModeSelectionController {
	@Autowired
	private PushModeSelectionService pushModeSelectionService;

	@RequestMapping("/clientChooseType")
	public ResponseResult<?> clientChooseType(@RequestBody PushModeSelectionRequestMessage pushModeSelectionRequestMessage) {
		// 函件下发方式选择业务逻辑处理
		pushModeSelectionService.pushModeSelection(pushModeSelectionRequestMessage);
		return ResponseResultUtil.success();
	}

}
