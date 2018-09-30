package com.hualife.bootstrap;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.integration.soap.message.response.notePrintPush.NotePrintPushResponseMessage;
import com.hualife.wxhb.service.NotePrintPushService;
import com.hualife.wxhb.service.PushMessageRequestService;
import com.hualife.wxhb.service.PushMessageService;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年9月13日
 */
@RestController
public class TestController {
	@Autowired
	private NotePrintPushService notePrintPushService;
	@Autowired
	private PushMessageRequestService pushMessageRequestService;
	@Autowired
	private TestSevice testSevice;
	@Autowired
	private PushMessageService pushMessageService;

	@RequestMapping("/test")
	public ResponseResult<?> test() {
		testSevice.test();
		return ResponseResultUtil.success();
	}

}
