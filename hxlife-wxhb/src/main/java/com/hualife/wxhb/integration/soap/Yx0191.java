package com.hualife.wxhb.integration.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**  
 * @Description: 影像上传接口（互联网交易平台）
 * @author zhanglong 
 * @date 2017年9月18日 下午2:56:12  
 */
@WebService(name = "Yx0191", targetNamespace="http://esb.hxlife.com")
public interface Yx0191 {
	@WebMethod(action = "com.hxlife.esb.inner.yx.Yx0191.yx0191")
	@WebResult(targetNamespace="http://esb.hxlife.com")
	public java.lang.String yx0191(@WebParam(name = "in") java.lang.String in);
}
