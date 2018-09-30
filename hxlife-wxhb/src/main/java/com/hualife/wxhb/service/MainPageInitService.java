package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;  

import com.hualife.wxhb.api.rest.message.request.MainPageInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.MainPageInitResponseMessage;

/**
 * @descrption : 业务待处理数据初始化service
 * @author linyongtao
 * @date : 2017.08.11
 */

@Service
public interface MainPageInitService {
	/**
	 * @descrption :业务待处理数据初始化方法--mainPageInfo()
	 * @author :linyongtao
	 */
	public MainPageInitResponseMessage mainPageInfo(MainPageInitRequestMessage mainPageInitRequestMessage);
}
