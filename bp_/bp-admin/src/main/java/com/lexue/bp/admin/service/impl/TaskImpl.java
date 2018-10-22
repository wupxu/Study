package com.lexue.bp.admin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lexue.bp.admin.service.DataService;
import com.lexue.bp.admin.service.RuleService;
import com.lexue.bp.admin.service.TaskBusinessService;
import com.lexue.bp.admin.service.TaskLogService;
import com.lexue.bp.admin.service.TaskService;
import com.lexue.bp.common.entity.DataOrderEntity;
import com.lexue.bp.common.entity.DataPostEntity;
import com.lexue.bp.common.entity.DataPostHotEntity;
import com.lexue.bp.common.entity.DataRefundEntity;
import com.lexue.bp.common.entity.DataShareEntity;
import com.lexue.bp.common.entity.DataWatchEntity;
import com.lexue.bp.common.entity.RuleEntity;
import com.lexue.bp.common.entity.TaskExecEntity;
import com.lexue.bp.common.enums.ConsumeChannelEnums;
import com.lexue.bp.common.enums.ProduceChannelEnums;
import com.lexue.bp.common.exception.BizException;
import com.lexue.bp.common.properties.BPProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bc
 *
 */
@Service
@Slf4j
public class TaskImpl implements TaskService {

	@Autowired
	private RuleService ruleService;
	@Autowired
	private DataService dataService;
	@Autowired
	private TaskBusinessService taskBusinessService;
	@Autowired
	private TaskLogService taskLogService;
	@Autowired
	@Qualifier("bp-config")
	private BPProperties bpProperties;// 使用@Autowired报重复注入有两个bean

	

	@Override
	@Transactional
	public TaskExecEntity order(String curDate) {

		TaskExecEntity result = null;
		Date startTaskDate = Calendar.getInstance().getTime();// 任务开始执行时间

		try {
			Date dateCurrent = DateUtils.parseDate(curDate, "yyyy-MM-dd");// 参数时间
			Date dateBefore = DateUtils.addDays(dateCurrent, -bpProperties.getOrderDay());// 订单查询的开始时间(三天前的时间)
			log.info("订单任务 参数时间：[{}],订单查询开始时间：[{}]", dateCurrent, dateBefore);

			// 读取规则
			List<RuleEntity> ruleEntityList = ruleService.getByRuleId(ProduceChannelEnums.ORDER.getCode());
			log.info("订单任务读取规则=====================：" + ruleEntityList);
			// 获取未处理且在时间范围内的数据,
			List<String> errorIds = new ArrayList<String>();
			List<DataOrderEntity> orders = dataService.getOrders(dateBefore.getTime(),
					DateUtils.addDays(dateBefore, 1).getTime());// 订单查询的开始和结束时间

			log.info("订单任务获取未处理且在时间范围内的数据orders=====================：" + orders);
			for (DataOrderEntity order : orders) {
				try {
					RuleEntity ruleEntity = getRule(ruleEntityList, order.getModuleId());
					// 单独事务
					taskBusinessService.Order(dateBefore.getTime(), dateCurrent.getTime(), order, ruleEntity);
				} catch (Exception e) {
					log.error("处理订单失败,订单号:" + order.getOrderId(), e);
					errorIds.add(order.getOrderId());
				}
			}

			// 生成运行情况
			result = taskLogService.generateSuccessTaskExecEntity(orders.size(), errorIds,
					ProduceChannelEnums.ORDER.getName(), startTaskDate);

		} catch (ParseException e) {
			log.error("处理订单数据时，日期解析异常", e);
		} catch (BizException e) {
			result = taskLogService.generateExceptionTaskExecEntity(e, ProduceChannelEnums.ORDER.getName());
		} catch (Exception e) {
			log.error("处理订单数据异常", e);
		}
		return result;
	}

	/**
	 * 查询所有的退款数据,满足:退款数据为待处理且订单数据状态为已处理
	 * 
	 * 对应的订单数据状态为已处理(成功生成待领积分)时，且已领取，扣减积分。 对应的订单数据状态为已处理但忽略或异常时，将退款记录变更状态为“已处理但忽略”。
	 */
	@Override
	@Transactional
	public TaskExecEntity refund() {
		TaskExecEntity result = null;
		Date startTaskDate = Calendar.getInstance().getTime();// 任务开始执行时间
		try {
			// 退款数据为待处理
			List<String> errorIds = new ArrayList<String>();
			List<DataRefundEntity> dataRefundEntities = dataService.getRefundEntity();
			log.info("退款任务查询待处理退款数据====================：" + dataRefundEntities);
			for (DataRefundEntity dataRefundEntity : dataRefundEntities) {
				try {
					// 单独事务
					taskBusinessService.Refund(dataRefundEntity, bpProperties.getOrderDay());
				} catch (Exception e) {
					log.error("处理退单失败,退单号:" + dataRefundEntity.getRefundId(), e);
					errorIds.add(dataRefundEntity.getRefundId());
				}
			}

			// 生成运行情况
			result = taskLogService.generateSuccessTaskExecEntity(dataRefundEntities.size(), errorIds,
					ConsumeChannelEnums.REFUND.getName(), startTaskDate);

		} catch (BizException e) {
			result = taskLogService.generateExceptionTaskExecEntity(e, ProduceChannelEnums.ORDER.getName());
		} catch (Exception e) {
			log.error("处理退单数据异常", e);
		}

		return result;
	}

	/**
	 * 帖子的数据是24小时内数据,
	 */
	@Override
	@Transactional
	public TaskExecEntity post(String curDate) {
		TaskExecEntity result = null;
		Date startTaskDate = Calendar.getInstance().getTime();// 任务开始执行时间
		try {
			Date date = DateUtils.parseDate(curDate, "yyyy-MM-dd");

			// 读取规则
			List<RuleEntity> ruleEntityList = ruleService.getByRuleId(ProduceChannelEnums.POST.getCode());
			log.info("帖子任务读取规则====================：[{}}]", ruleEntityList);

			// 获取24小时之后的帖子数据集合
			List<String> errorIds = new ArrayList<String>();
			List<DataPostEntity> posts = dataService.getPostList(date.getTime());
			log.info("帖子总数===================：[{}]", posts.size());
			for (DataPostEntity dataPostEntity : posts) {
				try {
					RuleEntity ruleEntity = getRule(ruleEntityList, dataPostEntity.getModuleId());
					// 单独事务
					taskBusinessService.post(dataPostEntity, ruleEntity);
				} catch (Exception e) {
					log.error("处理帖子失败,帖子号:" + dataPostEntity.getPostId(), e);
					errorIds.add(dataPostEntity.getPostId());
				}
			}

			// 生成运行情况
			result = taskLogService.generateSuccessTaskExecEntity(posts.size(), errorIds,
					ProduceChannelEnums.POST.getName(), startTaskDate);

		} catch (ParseException e) {
			log.error("处理订单数据时，日期解析异常", e);
		} catch (Exception e) {
			log.error("处理帖子数据异常", e);
		}
		return result;
	}

	/**
	 * 72小时后被评为热帖的数据
	 */
	@Override
	@Transactional
	public TaskExecEntity postHot(String curDate) {

		TaskExecEntity result = null;
		Date startTaskDate = Calendar.getInstance().getTime();// 任务开始执行时间
		try {
			Date date = DateUtils.parseDate(curDate, "yyyy-MM-dd");
			long afterDateLong = DateUtils.addHours(date, 24 - bpProperties.getPostHotHour()).getTime();// 72小时之前

			log.info("热帖时间结束点:[{}]", new SimpleDateFormat("yyyy-MM-dd")
					.format(DateUtils.addHours(date,  24 - bpProperties.getPostHotHour())));
			// 读取规则
			List<RuleEntity> ruleEntityList = ruleService.getByRuleId(ProduceChannelEnums.POST.getCode());
			log.info("热帖任务读取规则===================：[{}]", ruleEntityList);

			// 获取72小时之后的热帖数据集合
			List<String> errorIds = new ArrayList<String>();
			List<DataPostHotEntity> postHots = dataService.getPostHots(afterDateLong);
			log.info("热帖任务时间===================：" + afterDateLong);
			log.info("热帖任务 获取72小时之后的热帖数据集合===================：" + postHots);
			for (DataPostHotEntity postHotEntity : postHots) {
				try {
					RuleEntity ruleEntity = getRule(ruleEntityList, postHotEntity.getModuleId());
					// 单独事务
					taskBusinessService.postHot(postHotEntity, ruleEntity);
				} catch (Exception e) {
					log.error("处理热帖失败,帖子号:" + postHotEntity.getPostId(), e);
					errorIds.add(postHotEntity.getPostId());
				}
			}
			// 生成运行情况
			result = taskLogService.generateSuccessTaskExecEntity(postHots.size(), errorIds,
					ProduceChannelEnums.POSTHOT.getName(), startTaskDate);
		} catch (ParseException e) {
			log.error("处理订单数据时，日期解析异常", e);
		} catch (Exception e) {
			log.error("处理帖子数据异常", e);
		}
		return result;
	}

	/**
	 * 分享数据，实时到账
	 */
	@Override
	@Transactional
	public TaskExecEntity share(String curDate) {

		TaskExecEntity result = null;
		Date startTaskDate = Calendar.getInstance().getTime();// 任务开始执行时间
		try {
			Date date = DateUtils.parseDate(curDate, "yyyy-MM-dd");// 分享时间
			long time = date.getTime();

			// 读取规则
			List<RuleEntity> ruleEntityList = ruleService.getByRuleId(ProduceChannelEnums.SHARE.getCode());
			log.info("分享任务读取规则===================：" + ruleEntityList);

			// 获取分享条数
			List<String> errorIds = new ArrayList<String>();
			List<DataShareEntity> shares = dataService.getShares(time);
			log.info("分享当前时间===================：" + time);
			log.info("分享任务获取分享条数===================：" + shares);
			for (DataShareEntity dataShareEntity : shares) {
				try {
					RuleEntity ruleEntity = getRule(ruleEntityList, dataShareEntity.getModuleId());
					// 单独事务
					taskBusinessService.share(dataShareEntity, ruleEntity);
				} catch (Exception e) {
					log.error("处理分享失败,分享业务号:" + dataShareEntity.getBusinessId(), e);
					errorIds.add(dataShareEntity.getBusinessId());
				}
				// 生成运行情况
				result = taskLogService.generateSuccessTaskExecEntity(shares.size(), errorIds,
						ProduceChannelEnums.SHARE.getName(), startTaskDate);
			}
		} catch (Exception e) {
			log.error("处理分享数据异常", e);
		}
		return result;
	}

	@Override
	@Transactional
	public TaskExecEntity watch(String curDate) {

		TaskExecEntity result = null;
		Date startTaskDate = Calendar.getInstance().getTime();// 任务开始执行时间
		try {
			Date date = DateUtils.parseDate(curDate, "yyyy-MM-dd");

			// 读取规则
			List<RuleEntity> ruleEntityList = ruleService.getByRuleId(ProduceChannelEnums.WATCH.getCode());
			log.info("观看任务读取规则====================：" + ruleEntityList);
			// 获取查询时间内的数据
			List<String> errorIds = new ArrayList<String>();
			List<DataWatchEntity> watchs = dataService.getWatchs(date.getTime());

			log.info("观看任务获取查询时间内的数据====================：" + watchs);
			for (DataWatchEntity dataWatchEntity : watchs) {
				try {
					RuleEntity ruleEntity = getRule(ruleEntityList, dataWatchEntity.getModuleId());
					// 单独事务
					taskBusinessService.watch(dataWatchEntity, ruleEntity);
				} catch (Exception e) {
					log.error("处理观看课程失败,观看视频业务号:" + dataWatchEntity.getBusinessId(), e);
					errorIds.add(dataWatchEntity.getBusinessId());
				}
			}
			// 生成运行情况
			result = taskLogService.generateSuccessTaskExecEntity(watchs.size(), errorIds,
					ProduceChannelEnums.WATCH.getName(), startTaskDate);

		} catch (Exception e) {
			log.error("处理观看视频数据异常", e);
		}
		return result;
	}

	@Override
	public void invalid(long curDate) {
		taskBusinessService.invalid(curDate);
	}

	/**
	 * 选出等于参数moduleId的RuleEntity
	 * 
	 * @param ruleEntityList
	 * @param moduleId
	 * @return
	 */
	private RuleEntity getRule(List<RuleEntity> ruleEntityList, int moduleId) {
		if (CollectionUtils.isEmpty(ruleEntityList)) {
			return null;
		}
		for (RuleEntity rule : ruleEntityList) {
			if (rule.getModuleId() == moduleId) {
				return rule;
			}
		}
		return null;
	}
}
