package com.didispace;

import com.didispace.filter.AccessFilter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
/**
 * zuul网关过滤器功能
 * @author wpx
 */
//zuul启用
@EnableZuulProxy
@SpringCloudApplication
public class GateWayApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(GateWayApplication.class).web(true).run(args);
	}

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

}
