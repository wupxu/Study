package com.hualife.wxhb.api.soap.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.soap.HelloWorld;


@WebService(serviceName="hellowebservice",targetNamespace="http://wxhb.hxlife.com/soap-api",
endpointInterface = "com.hualife.wxhb.api.soap.HelloWorld")
@Service
public class HelloWorldImpl implements HelloWorld {
	
	public String sayHi(String text) {
		System.out.println("sayHi called"+text);
		
		return "Hello " + text;
	}
	
//	public DemoRequestMessage testObject(DemoRequestMessage tDemoRequestMessage){
//		
//		System.out.println("sayHi testObject");
//		List<DemoClientInfo> ClientInfo = tDemoRequestMessage.getClientInfo();
//		int i = 0 ;
//		for(DemoClientInfo tDemoClientInfo : ClientInfo){			
//			System.out.println("第"+i+"次进入");
//			i++;
//		}
//		
//		return tDemoRequestMessage;
//	}
}
