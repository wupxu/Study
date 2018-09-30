package com.hualife.wxhb.api.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hualife.mesiframework.web.result.ResponseResult;
import com.hualife.mesiframework.web.util.ResponseResultUtil;
import com.hualife.wxhb.api.rest.message.request.QuestionNaireSaveResquestMessage;
import com.hualife.wxhb.service.QuestionNaireSaveService;
/**
 * @description : 问卷和调查报告提交
 * @author : linyongtao
 * @date : 2017-08-04 
 */
@RestController
public class QuestionNaireSaveController {
	@Autowired
	private QuestionNaireSaveService questionNaireSaveService;
	@RequestMapping("/answerSave")
	public ResponseResult<?> answerSave(@RequestBody QuestionNaireSaveResquestMessage questionNaireSaveResquestMessage){
		
		questionNaireSaveService.survivalQuesAns(questionNaireSaveResquestMessage);		
		return ResponseResultUtil.success( );
		
	}
}
