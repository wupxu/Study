package com.hualife.wxhb.integration.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**  
 * @Description:函件系统向核心推送下发方式的接口
 * @author zhanglong 
 * @date 2017年9月1日 上午9:54:46  
 */
@WebService(name = "UWTrans0238", targetNamespace="http://esb.hxlife.com")
public interface UWTrans0238 {
	
	@WebMethod(action = "com.hxlife.esb.inner.kernel.UWTrans0238.uWTrans0238")
	@WebResult(targetNamespace="http://rpc2UWService.elecletters.intf.hxlife.com/")
	public java.lang.String uWTrans0238(@WebParam(name = "in") java.lang.String in);
}
