package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.NotQualifiedImagesInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NotQualifiedImagesInitResponseMessage;
import com.hualife.wxhb.service.NotQualifiedImagesInitService;
/**
 * @description : 次品单-客户上传影像资料
 * @author : linyongtao
 * @date : 2017-08-21 
 */
@RestController
public class NotQualifiedImagesInitController {
	@Autowired
	private NotQualifiedImagesInitService notQualifiedImagesInitService;
	@RequestMapping("/notQualifiedInit")
	public ResponseResult<NotQualifiedImagesInitResponseMessage> notQualifiedInit(@RequestBody NotQualifiedImagesInitRequestMessage notQualifiedImagesInitRequestMessage){
		NotQualifiedImagesInitResponseMessage notQualifiedImagesInitResponseMessage = notQualifiedImagesInitService.notQualifiedInit(notQualifiedImagesInitRequestMessage);
		return ResponseResultUtil.success(notQualifiedImagesInitResponseMessage);
		
	}
}
