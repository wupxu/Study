package com.lexue.bp.admin.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lexue.bp.admin.service.LockService;
import com.lexue.bp.admin.service.TaskService;
import com.lexue.bp.common.entity.LockEntity;
import com.lexue.bp.common.entity.TaskExecEntity;
import com.lexue.bp.common.enums.LockEnums;
import com.lexue.bp.common.repository.TaskExecRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 各个渠道任务调度类
 * 
 * @author wpx
 *
 */
@Slf4j
@Component
public class Task {

	@Autowired
	private TaskService taskService;
	@Autowired
	private LockService lockService;
	@Autowired
	private TaskExecRepository taskExecRepository;

	@Scheduled(cron = "0 0 6 * * ?")
	public void runOrder() {
		// 任务开始执行，在方法锁表里插入任务数据，lockmethod表中methodName做了唯一性约束
		log.info("runOrder任务开始========================");
    	if (!check(LockEnums.RUNORDER_METHOD.getMsg())) { 
    		log.info("本机不执行订单任务");
    	    return; 
        } 
	 
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		TaskExecEntity order = taskService.order(curDate);
		if (order!=null) {
			taskExecRepository.save(order);
		} 
		// 任务执行完成，删除方法锁里的数据
		 lockService.deleteByMethodName(LockEnums.RUNORDER_METHOD.getMsg());
		log.info("runOrder任务结束========================");
	}

	@Scheduled(cron = "0 0 6 * * ?")
	public void runRefund() {
		log.info("runRefund任务开始========================");
		log.error("runRefund任务开始========================");
		if (!check(LockEnums.RUNREFUND_METHOD.getMsg())) { 
    		log.info("本机不执行退款任务");
    	    return; 
        } 
		TaskExecEntity refund = taskService.refund();
		if (refund!=null) {
			taskExecRepository.save(refund);
		}

		// 任务执行完成，删除方法锁里的数据
		 lockService.deleteByMethodName(LockEnums.RUNREFUND_METHOD.getMsg());
		log.info("runRefund任务结束========================");
		log.error("runRefund任务结束========================");

	}

	@Scheduled(cron = "0 0 6 * * ?")
	public void runPost() {
		log.info("runPost任务开始========================");
		if (!check(LockEnums.RUNPOST_METHOD.getMsg())) { 
    		log.info("本机不执行帖子任务");
    	    return; 
        } 
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	
		TaskExecEntity post = taskService.post(curDate);
		if (post!=null) {
			taskExecRepository.save(post);
		}

		// 任务执行完成，删除方法锁里的数据
		 lockService.deleteByMethodName(LockEnums.RUNPOST_METHOD.getMsg());
		log.info("runPost任务结束========================");
	}

	@Scheduled(cron = "0 0 6 * * ?")
	public void runPostHot() {
		log.info("runPostHot任务开始========================");
		if (!check(LockEnums.RUNPOSTHOT_METHOD.getMsg())) { 
    		log.info("本机不执行热帖任务");
    	    return; 
        } 
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

		TaskExecEntity postHot = taskService.postHot(curDate);
		if (postHot!=null) {
			taskExecRepository.save(postHot);
		}

		// 任务执行完成，删除方法锁里的数据
		 lockService.deleteByMethodName(LockEnums.RUNPOSTHOT_METHOD.getMsg());
		log.info("runPostHot任务结束========================");
		log.error("runPostHot任务结束========================");

	}

	@Scheduled(fixedRate = 60000)
	public void runShare() {
		log.info("runShare任务开始========================");
		log.error("runShare任务开始========================");
		if (!check(LockEnums.RUNSHARE_METHOD.getMsg())) { 
    		log.info("本机不执行分享任务");
    	    return; 
        } 
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		TaskExecEntity share = taskService.share(curDate);
		if (share!=null) {
			taskExecRepository.save(share);
		}

		// 任务执行完成，删除方法锁里的数据
		 lockService.deleteByMethodName(LockEnums.RUNSHARE_METHOD.getMsg());
		log.info("runShare任务结束========================");
		log.error("runShare任务结束========================");
	}

	@Scheduled(cron = "0 0 6 * * ?")
	public void runWatch() {
		log.info("runWatch任务开始========================");
		log.error("runWatch任务开始========================");
		if (!check(LockEnums.RUNWATCH_METHOD.getMsg())) { 
    		log.info("本机不执行观看视频任务");
    	    return; 
        } 
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		TaskExecEntity watch = taskService.watch(curDate);
		if (watch!=null) {
			taskExecRepository.save(watch);
		}

		// 任务执行完成，删除方法锁里的数据
		 lockService.deleteByMethodName(LockEnums.RUNWATCH_METHOD.getMsg());
		log.info("runWatch任务结束========================");
		log.error("runWatch任务结束========================");
	}

	/* 使积分失效 */
	@Scheduled(cron = "0 0 6 * * ?")
	public void invalid() {
		if (!check(LockEnums.RUNINVALID_METHOD.getMsg())) { 
    		log.info("本机不执行失效乐豆任务");
    	    return; 
        } 
		log.info("invalid任务开始========================");
		Date date = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH);
		taskService.invalid(date.getTime());
		lockService.deleteByMethodName(LockEnums.RUNINVALID_METHOD.getMsg());
		log.info("invalid任务结束========================");
	}

	/** 判断任务是否可以获得锁 */
	private boolean check(String methodName) {
		
		LockEntity dbLockEntity = lockService.findByMethodName(methodName);
		if (dbLockEntity != null) {
			Date cTime = DateUtils.addDays(dbLockEntity.getCreateTime(), 1);
			int c = DateUtils.truncatedCompareTo(cTime, new Date(), Calendar.DAY_OF_MONTH);
			if (c<=0) {
				lockService.deleteByMethodName(methodName);
			}
		}

		LockEntity lockEntity = new LockEntity();
		lockEntity.setMethodName(methodName);
		lockEntity.setCreateTime(new Date());
		try {
			lockService.methodLockSave(lockEntity);
		} catch (Throwable t) {
			return false;
		}
		return true;
	}
}
