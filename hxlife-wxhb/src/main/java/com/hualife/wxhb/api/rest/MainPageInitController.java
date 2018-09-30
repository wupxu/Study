package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.MainPageInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.MainPageInitResponseMessage;
import com.hualife.wxhb.service.MainPageInitService;

/**
 * @description : 主页面初始化
 * @author :linyongtao
 * @date : 2017.08.14
 */
@RestController
public class MainPageInitController {
	@Autowired
	private MainPageInitService mainPageInitService;

	@RequestMapping("/mainPageInfos")
	public ResponseResult<MainPageInitResponseMessage> mainPageInfos(
			@RequestBody MainPageInitRequestMessage mainPageInitRequestMessage) {
		MainPageInitResponseMessage mainPageInitResponseMessage = mainPageInitService
				.mainPageInfo(mainPageInitRequestMessage);
		return ResponseResultUtil.success(mainPageInitResponseMessage);

	}
}
