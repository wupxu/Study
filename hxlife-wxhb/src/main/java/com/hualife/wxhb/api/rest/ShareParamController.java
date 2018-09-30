package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.response.ShareParamInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ShareParamInfoResponseMessage;
import com.hualife.wxhb.service.ShareParamService;

/**
 * @author zhanyilong
 * @description 调用分享接口时，传递的参数和首页计划分享保存
 * @date 2017-08-31
 */
@RestController
public class ShareParamController {

	@Autowired
	private ShareParamService shareParamService;

	@RequestMapping("/shareparam")
	public ResponseResult<ShareParamInfoResponseMessage> shareparam(@RequestBody ShareParamInfoRequestMessage shareParamInfoRequestMessage){
		ShareParamInfoResponseMessage hareParamInfoResponseMessage = shareParamService.shareparam(shareParamInfoRequestMessage);
        return ResponseResultUtil.success(hareParamInfoResponseMessage);
	}

}
