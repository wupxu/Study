package com.lexue.bp.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author bc
 *
 */
@Data
@Configuration("bp-config")
@ConfigurationProperties(prefix="bp")
public class BPProperties {
	
	
	/**
	 * 订单数据等待的时间，之后生成积分，单位：天 
	 */
	private int orderDay = 3;
	
	/**
	 * 帖子数据等待的时间，之后生成积分，单位：小时
	 */
	private int postHour = 24;
	
	/**
	 * 热帖数据等待的时间，之后生成积分，单位：小时
	 */
	private int postHotHour = 72;
	
	/**
	 * 图片前缀 
	 */
	private String prefix;
	

}
