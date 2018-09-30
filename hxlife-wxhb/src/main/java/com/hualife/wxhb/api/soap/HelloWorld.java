package com.hualife.wxhb.api.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name= "hellowebservice",targetNamespace="http://wxhb.hxlife.com/soap-api")
public interface HelloWorld {
	
	@WebMethod(action = "sayHi")
	String sayHi(@WebParam(name = "text",targetNamespace = "http://wxhb.hxlife.com/soap-api") String text);
	
//	@WebMethod(action = "testObject")
//	DemoRequestMessage testObject(@WebParam(name = "tDemoRequestMessage",targetNamespace = "http://wxhb.hxlife.com/soap-api") DemoRequestMessage tDemoRequestMessage);
}
