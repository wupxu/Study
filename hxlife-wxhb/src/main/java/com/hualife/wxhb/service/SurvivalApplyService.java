package com.hualife.wxhb.service;

import org.springframework.stereotype.Repository; 

import com.hualife.wxhb.api.rest.message.request.SurvivalApplyRequestMessage;
/**
 * @descrption : 契调任务申请：业务员对任务进行申请service
 * @author linyongtao
 * @date : 2017-08-04 16:28:00
 */
@Repository
public interface SurvivalApplyService {
	/**
	 * @descrption : 契调任务申请方法
	 * @author linyongtao
	 * @date : 2017-08-04 
	 * **/
	 public void survivalStateUpdate (SurvivalApplyRequestMessage survivalApplyRequestMessage);
}
