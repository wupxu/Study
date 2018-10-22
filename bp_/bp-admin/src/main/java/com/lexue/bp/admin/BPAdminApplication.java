package com.lexue.bp.admin;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lexue.bp.common.properties.BPProperties;

@SpringBootApplication
@EnableScheduling
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
@EnableCircuitBreaker
@ComponentScan({"com.lexue.bp.admin","com.lexue.bp.common"})
@EntityScan({"com.lexue.bp.common.entity"})
@EnableJpaRepositories("com.lexue.bp.common.repository") 
@EnableMongoRepositories("com.lexue.bp.common.repository.mongo")
@EnableConfigurationProperties({BPProperties.class})
public class BPAdminApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BPAdminApplication.class, args);
	}
	
}
