package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * springcloud服务调用方式有两种实现方式：
　　1：restTemplate+ribbon,
　　2：feign：Feign默认集成了ribbon。
 * @author lexue
 *
 */
@SpringBootApplication
//作为服务注册到eureka中
@EnableDiscoveryClient
public class RibbonApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RibbonApplication.class, args);
	}

}
