package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.rest.message.request.QuestionNaireSaveResquestMessage; 
/**
 * @descrption : 问卷和报告提交service
 * @author : linyongtao
 * @date : 2017-08-24
 * **/
@Service
public interface QuestionNaireSaveService {
	/**
	 * @descrption : 问卷和报告提交方法survivalQuesAns
	 * @author : linyongtao
	 * @date : 2017-08-24
	 * **/
	public void  survivalQuesAns(QuestionNaireSaveResquestMessage questionNaireSaveResquestMessage);

}
