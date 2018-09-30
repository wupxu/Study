package com.hualife.wxhb.integration.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://esb.hxlife.com", name = "PosTrans0221")
public interface PosTrans0221 {
	 @WebMethod(action = "com.hxlife.esb.inner.kernel.PosTrans0221.posTrans0221")
	 public java.lang.String posTrans0221(@WebParam(name = "esb:in") java.lang.String in);
}
