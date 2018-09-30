package com.hualife.wxhb.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.ProblemInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ProblemInfoResponseMessage;
import com.hualife.wxhb.service.ProblemNotelistInfoService;

/**
 * @author 吴培旭
 * @description 问题件列表初始化
 * @time 创建时间：2017年8月8日
 */
@RestController
public class ProblemNotelistInfoController {

	@Autowired
	private ProblemNotelistInfoService problemNotelistInfoService; 

	@RequestMapping("/problemNotelistInfo")
	public ResponseResult<ProblemInfoResponseMessage> problemNotelistInfo(@RequestBody ProblemInfoRequestMessage problemInfoRequestMessage) {
		// 声明返回报文
		ProblemInfoResponseMessage problemInfoResponseMessage = problemNotelistInfoService.problemNotelistInfo(problemInfoRequestMessage);
		return ResponseResultUtil.success(problemInfoResponseMessage);
	}
}
