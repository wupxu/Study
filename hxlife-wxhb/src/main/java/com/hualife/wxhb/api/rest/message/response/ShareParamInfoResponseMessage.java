package com.hualife.wxhb.api.rest.message.response;
/**
 * @descrption :　分享配置参数接口返回报文
 * @author : zhanyilong
 * @date : 2017-08-31
 */
public class ShareParamInfoResponseMessage {
	/**
	 * 企业号appid
	 * **/
	private String appId;
	/**
	 * timestamp时间戳
	 * **/
	private long timestamp;
	/**
	 * nonceStr签名随机字符串
	 * **/
	private String nonceStr;
	/**
	 * signature签名识别标志
	 * **/
	private String signature;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
}
