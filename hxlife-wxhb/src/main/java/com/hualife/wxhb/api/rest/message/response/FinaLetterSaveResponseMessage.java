package com.hualife.wxhb.api.rest.message.response;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
/**
 * @description : 财务函提交返回报文
 * @author linyongtao
 * @date : 2017.08.14
 */

public class FinaLetterSaveResponseMessage {
	private static final Logger LOGGER = LoggerFactory.getLogger(FinaLetterSaveResponseMessage.class);	
	/**
	 * 交易结果码
	 * **/
	private String resultMsg;	
	/**
	 * 交易结果描述
	 * **/
	private String resultCode;
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public static Logger getLogger() {
		return LOGGER;
	}
	
	
}
