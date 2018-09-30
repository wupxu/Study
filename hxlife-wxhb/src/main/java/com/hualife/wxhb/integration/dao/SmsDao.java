package com.hualife.wxhb.integration.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository

public interface SmsDao {

	/**
	 * 查询手机验证码
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public String phoneCode(Map<String, Object> map);

	/**
	 * 更新状态
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public void updateMain(Map<String, Object> map);

	/**
	 * 更新状态
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public void updateCode(Map<String, Object> map);

	/**
	 * 查询手机号
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public String findPhone(String id);

	/**
	 * 更新 发送状态
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public void updateSms(Map<String, Object> map);

	/**
	 * 存表
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public void addSms(Map<String, Object> map);

	/**
	 * 获取时间
	 * 
	 * @author : yangpeixin
	 * @return
	 */
	public Date findTime(Map<String, Object> map);

}
