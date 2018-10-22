package com.lexue.bp.web.inf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ApplicationINF {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationINF.class, args);
	}

}