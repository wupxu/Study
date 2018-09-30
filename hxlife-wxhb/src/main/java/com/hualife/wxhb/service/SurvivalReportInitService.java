package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.rest.message.request.SurvivalReportInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalReportInitResponseMessage;
/**
 * @descrption : 契调报告初始化service
 * @author : linyongtao
 * @date : 2017-08-17
 */

@Service
public interface SurvivalReportInitService {
	/**
	 * @descrption : 契调报告初始化
	 * @author : linyongtao
	 * @date : 2017-08-17
	 * **/
	public SurvivalReportInitResponseMessage  selectSurvivalReport(SurvivalReportInitRequestMessage  survivalReportInitRequestMessage);
}
