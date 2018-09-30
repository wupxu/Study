package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.rest.message.request.QuestionRopertInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.QuestionReportInitResponseMessage;
/**
 * @description : 问卷和报告初始化service
 * @author : linyongtao
 * @date : 2017.08.31
 */
@Service
public interface QuestionReportInitService {
	/**
	 *  问卷和报告初始化
	 * **/
	public QuestionReportInitResponseMessage questionReportInit(QuestionRopertInitRequestMessage questionRopertInitRequestMessage); 
	
}
