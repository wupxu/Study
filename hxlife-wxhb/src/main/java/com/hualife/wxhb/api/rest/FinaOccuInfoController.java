package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.FinaOccuInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.FinaOccuInfoResponseMessage;
import com.hualife.wxhb.service.FinaOccuInfoService;

/**
 * @author 吴培旭
 * @description 财务函初始化
 * @time 创建时间：2017年8月5日
 */
@RestController
public class FinaOccuInfoController {

	@Autowired
	private FinaOccuInfoService finaOccuInfoService;

	@RequestMapping("/finaOccuInfo")
	public ResponseResult<FinaOccuInfoResponseMessage> finaOccuInfo(@RequestBody FinaOccuInfoRequestMessage finaOccuInfoRequestMessage) {
		
		FinaOccuInfoResponseMessage finaOccuInfoRespMessage =finaOccuInfoService.finaOccuInfo(finaOccuInfoRequestMessage);
		return ResponseResultUtil.success(finaOccuInfoRespMessage);
	}
}
