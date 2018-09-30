package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.UndwrtEndDealRequestMessage;

/**
 * @author zhangweiwei
 * @deprecation 代理人删除延期、拒保的核保函service
 * @date 2017-08-04
 **/
public interface UndwrtEndDealService {
	/**
	 * 代理人删除延期、拒保的核保函
	 **/
	public void undwrtEndDeal(UndwrtEndDealRequestMessage undwrtEndDealRequestMessage);
}
