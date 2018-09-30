package com.hualife.wxhb.api.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.UndwrtEndDealRequestMessage;
import com.hualife.wxhb.service.UndwrtEndDealService;
/**
 * @author zhangweiwei
 * @deprecation 代理人删除核保函
 * @date 2017-08-07
 */
@RestController
public class UndwrtEndDealController {
	
	@Autowired
	private UndwrtEndDealService undwrtEndDealService;
	
	@RequestMapping("/undwrtEndDeal")
	public ResponseResult <?> undwrtEndDeal(@RequestBody UndwrtEndDealRequestMessage undwrtEndDealRequestMessage) {	    
		undwrtEndDealService.undwrtEndDeal(undwrtEndDealRequestMessage);
	    return ResponseResultUtil.success();
	}
}
