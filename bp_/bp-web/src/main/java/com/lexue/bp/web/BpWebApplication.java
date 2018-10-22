package com.lexue.bp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
(basePackages = {
        "com.lexue.account.service",
        "com.lexue.edu",
        "com.lexue.member",
        "com.lexue.coupon",
        "com.lexue.base",
        "com.lexue.appconfcenter",
        "com.lexue.bp.web.inf.spec"
})
@ComponentScan({"com.lexue.bp.web","com.lexue.bp.common"})
@EntityScan({ "com.lexue.bp.common.entity" })
@EnableJpaRepositories("com.lexue.bp.common.repository")
@EnableMongoRepositories("com.lexue.bp.common.repository.mongo")
@EnableEurekaClient//
public class BpWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpWebApplication.class, args);
	}
}
