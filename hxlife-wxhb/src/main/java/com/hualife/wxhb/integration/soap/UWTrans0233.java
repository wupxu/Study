package com.hualife.wxhb.integration.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @Description:函件系统向核心系统推送函件打印信息的接口
 * @author zhanglong 
 * @date 2017年9月1日 上午9:22:26
 */
@WebService(name = "UWTrans0233", targetNamespace="http://esb.hxlife.com")
public interface UWTrans0233 {
	@WebMethod(action = "com.hxlife.esb.inner.kernel.UWTrans0233.uWTrans0233")
	@WebResult(targetNamespace="http://rpc2UWService.elecletters.intf.hxlife.com/")
	public java.lang.String uWTrans0233(@WebParam(name = "in") java.lang.String in);
}
