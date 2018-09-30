package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.FinaOccuInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.FinaOccuInfoResponseMessage;

/** 
 * @author 吴培旭 
 * @description 财务函初始化
 * @time 创建时间：2017年8月5日   
 */
public interface FinaOccuInfoService {
	/** 
	 * @author 吴培旭 
	 * @description 财务函初始化
	 * @time 创建时间：2017年8月5日   
	 */
	FinaOccuInfoResponseMessage finaOccuInfo(FinaOccuInfoRequestMessage finaOccuInfoRequestMessage);

}
