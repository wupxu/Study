package com.hualife.wxhb.api.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

/** 
 * @author 吴培旭 
 * @description 批处理函件下发
 * @time 创建时间：2017年8月17日   
 */
@WebService(serviceName= "LccontService",targetNamespace="http://localhost:8080/com/hualife/wxhb/webservice")
public interface ChooseTypeClient {

	@WebMethod
	public String sayhi(String hi);
	
	
}
