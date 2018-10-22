package com.lexue.bp.admin.service;

import com.lexue.bp.common.entity.TaskExecEntity;

/**
 * 签到、退单、评热评：积分实时到账，不需要领取
 * 签到数据不从队列中获取
 * 
 * @author bc
 *
 */
public interface TaskService {

	/**
	 * 按规则，将订单数据生成待领积分
	 * 处理的数据支付时间在curDate当天内
	 * @param curDate 开始时间，格式：yyyy-MM-dd 
	 * @return
	 */
	TaskExecEntity order(String curDate);
	
	TaskExecEntity refund();
	
	/**
	 * 根据发帖数据，生成待领积分。
	 * 其中参数格式为:yyyy-MM-dd ,表示执行的时间。
	 * @param curDate
	 * @return
	 */
	TaskExecEntity post(String curDate);
	
	TaskExecEntity postHot(String curDate);
	
	TaskExecEntity share(String curDate);
	
	TaskExecEntity watch(String curDate);
	
	/*积分按失效时间失效*/
	void invalid(long curDate);
}
