package com.hualife.wxhb.service;

import com.hualife.wxhb.integration.soap.message.response.notePrintPush.NotePrintPushResponseBody;

/** 
 * @author 吴培旭 
 * @description 批处理函件打印返回报文后的逻辑处理
 * @time 创建时间：2017年8月18日   
 */
public interface NotePrintResponseService {

	/** 
	 * @author 吴培旭 
	 * @description 批处理函件打印返回报文后的逻辑处理
	 * @time 创建时间：2017年8月18日   
	 */
	public void setChooseTypeClientResponse(NotePrintPushResponseBody chooseTypeClientRespBody);

	
}
