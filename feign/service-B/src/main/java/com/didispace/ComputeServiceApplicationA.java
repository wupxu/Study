package com.didispace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 另一个服务service-A
 * @author wpx
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ComputeServiceApplicationA {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ComputeServiceApplicationA.class).web(true).run(args);
	}

}
