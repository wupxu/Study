package com.hualife.wxhb.api.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * @author "张龙"
 * @Description 对外曝露交易接口
 * @date 2017年8月11日 上午10:02:35
 */
@WebService(name= "tradeService",targetNamespace="http://wxhb.hxlife.com/soap-api")
public interface TradeService {
	/**
	 * 核心推送函件信息
	 */
	@WebMethod(action = "hj0234")
	String noteFromCore(@WebParam(name = "Reqxml",targetNamespace = "http://wxhb.hxlife.com/soap-api")String ReqXml);
	/**
	 * 次品单
	 */
	@WebMethod(action = "hj0232")
	String noteNotQualified(@WebParam(name = "Reqxml",targetNamespace = "http://wxhb.hxlife.com/soap-api")String ReqXml);
	
	/**
	 * 重新下发
	 */
	@WebMethod(action = "hj0235")
	String noteSecondPush(@WebParam(name = "Reqxml",targetNamespace = "http://wxhb.hxlife.com/soap-api")String ReqXml);
	
	/**
	 * 回销信息推送
	 */
	@WebMethod(action = "hj0236")
	String noteFinishDeal(@WebParam(name = "Reqxml",targetNamespace = "http://wxhb.hxlife.com/soap-api")String ReqXml);
}
