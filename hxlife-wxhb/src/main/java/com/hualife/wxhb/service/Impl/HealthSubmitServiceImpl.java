package com.hualife.wxhb.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.request.HealthSubmitRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.service.HealthSubmitService;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.TaskPushInfoService;

/**
 * @author yangpeixin
 * @description 健康函提交
 * @date 2017-08-04
 */

@Service
public class HealthSubmitServiceImpl implements HealthSubmitService {

	private final Logger logger = LoggerFactory.getLogger(HealthSubmitServiceImpl.class);

	@Autowired
	private HealthDao healthDao;
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private TaskPushInfoService taskPushInfoService;

	/**
	 * 处理提交
	 **/
	@Transactional
	public void healthNoteSave(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		// 检测入参
		this.checkResData(healthSubmitRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthSubmitRequestMessage.getHealth_note_id(), "开始处理数据");
		String healthSubmitType = healthSubmitRequestMessage.getHealth_submit_type();
		if (healthSubmitType.equals(Constant.client_note_AGENT_SUBMID)) {
			agentSubmit(healthSubmitRequestMessage);
			// 更新主表状态
			updateNoteMain(healthSubmitRequestMessage);

		} else {
			// 更新代理人查看状态
			updateNoteAgentStatus(healthSubmitRequestMessage);
			// 更新客户状态
			updateNoteClientStatus(healthSubmitRequestMessage);
			// 更新逻辑状态
			updateNoteStatus(healthSubmitRequestMessage);

		}
	}

	/**
	 * 主表状态
	 **/
	public void updateNoteMain(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		long startTime = 0;
		long endTime = 0;
		String healthid = healthSubmitRequestMessage.getHealth_note_id();
		// 查询各个函件在客户状态
		String noteid = healthDao.getNoteId(healthid);
		// 查询财务
		Map<String, Object> finaMap = new HashMap<>();
		finaMap.put("noteid", noteid);
		finaMap.put("noteType", Constant.client_type_FINA);
		startTime = System.currentTimeMillis();
		String fina = healthDao.findClinetstatus(finaMap);		
		endTime = System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
				"查询财务函状态耗时:" + (endTime - startTime) + "ms");	
		// 查询契调函
		Map<String, Object> survivalMap = new HashMap<>();
		survivalMap.put("noteid", noteid);
		survivalMap.put("noteType", Constant.client_type_SURVIVAL);
		String survival = healthDao.findClinetstatus(survivalMap);
		// 查询体检函件
		Map<String, Object> physicalMap = new HashMap<>();
		physicalMap.put("noteid", noteid);
		physicalMap.put("noteType", Constant.client_type_PHYSICAL);
		String physical = healthDao.findClinetstatus(physicalMap);
		if (!StringUtils.isEmpty(fina) && !fina.equals(Constant.fina_note_status_DOWN)) {
			return;
		}

		if (!StringUtils.isEmpty(survival) && !survival.equals(Constant.survival_note_status_DOWN)) {
			return;
		}
		if (!StringUtils.isEmpty(physical) && !physical.equals(Constant.physical_or_health_note_status_DOWN)) {
			return;
		}

		// 更新主表
		Map<String, Object> updatemianMap = new HashMap<>();
		updatemianMap.put("noteid", noteid);
		updatemianMap.put("status", Constant.note_status_DOWN_DESC);

		startTime = System.currentTimeMillis();
		healthDao.update_mian(updatemianMap);
		endTime = System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
				"更新主表状态耗时:" + (endTime - startTime) + "ms");
		taskPushInfoService.saveAuthorizeAgentHandleNote(noteid,healthid, Constant.client_type_HEALTH);
	}

	/**
	 * 客户状态
	 **/
	public void updateNoteClientStatus(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		String healthid = healthSubmitRequestMessage.getHealth_note_id();
		long startTime = 0;
		long endTime = 0;
		Map<String, Object> map = new HashMap<>();
		map.put("noteid", healthid);
		map.put("clientstatus", Constant.note_client_status_FININSHED);
		map.put("clientstatusDesc", Constant.note_client_status_FININSHED_DESC);

		startTime = System.currentTimeMillis();
		healthDao.update_note_client_status(map);
		endTime = System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
				"更新客户状态耗时:" + (endTime - startTime) + "ms");

	}

	/**
	 * 代理人状态
	 **/
	public void updateNoteAgentStatus(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		long startTime = 0;
		long endTime = 0;
		String healthid = healthSubmitRequestMessage.getHealth_note_id();
		String type = healthSubmitRequestMessage.getHealth_choose_type();
		if (type.equals(Constant.client_note_client_choose_type_TRANSFER_TO_BRANCH)) {

			Map<String, Object> updateagentMap = new HashMap<>();
			updateagentMap.put("noteid", healthid);
			updateagentMap.put("agentstatus", Constant.note_agent_status_DEALING);
			updateagentMap.put("agentstatusDesc", Constant.note_agent_status_DEALING_DESC);
			// 更新耗时
			startTime = System.currentTimeMillis();
			healthDao.update_agent(updateagentMap);
			endTime = System.currentTimeMillis();
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
					"更新代理人查看状态耗时:" + (endTime - startTime) + "ms");
			String noteid = healthDao.getNoteId(healthid);
			taskPushInfoService.saveAuthorizeAgentHandleNote(noteid, healthid, Constant.client_type_PHYSICAL);
		} else {

			Map<String, Object> updateagentMap = new HashMap<>();
			updateagentMap.put("noteid", healthid);
			updateagentMap.put("agentstatus", Constant.note_agent_status_FINISH);
			updateagentMap.put("agentstatusDesc", Constant.note_agent_status_FINISH_DESC);
			// 更新耗时
			startTime = System.currentTimeMillis();
			healthDao.update_agent(updateagentMap);
			endTime = System.currentTimeMillis();
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
					"更新代理人查看状态耗时:" + (endTime - startTime) + "ms");
			String noteid = healthDao.getNoteId(healthid);
			taskPushInfoService.saveAuthorizeAgentHandleNote(noteid, healthid, Constant.client_type_PHYSICAL);
		}

	}

	/**
	 * 逻辑状态
	 **/
	public void updateNoteStatus(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		long startTime = 0;
		long endTime = 0;
		String healthid = healthSubmitRequestMessage.getHealth_note_id();
		String type = healthSubmitRequestMessage.getHealth_choose_type();
		if (type.equals(Constant.client_note_client_choose_type_TRANSFER_TO_BRANCH)) {
			String noteid = healthDao.getNoteId(healthid);
			Map<String, Object> map = new HashMap<>();
			map.put("noteid", healthid);
			map.put("status", Constant.physical_or_health_note_status_AUTHORIZATION);
			map.put("statusDesc", Constant.physical_or_health_note_status_AUTHORIZATION_DESC);

			startTime = System.currentTimeMillis();
			healthDao.update_status(map);
			endTime = System.currentTimeMillis();
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
					"更新逻辑状态耗时:" + (endTime - startTime) + "ms");
			noteTraceService.saveNoteTrace(noteid, healthid, Constant.client_type_HEALTH,
					Constant.physical_or_health_note_status_AUTHORIZATION_DESC);
		} else {
			String noteid = healthDao.getNoteId(healthid);
			Map<String, Object> map = new HashMap<>();
			map.put("noteid", healthid);
			map.put("status", Constant.physical_or_health_note_status_DOWN);
			map.put("statusDesc", Constant.physical_or_health_note_status_DOWN_DESC);
			startTime = System.currentTimeMillis();
			healthDao.update_status(map);
			endTime = System.currentTimeMillis();
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
					"更新逻辑状态耗时:" + (endTime - startTime) + "ms");
			// 更新主表状态
			updateNoteMain(healthSubmitRequestMessage);
			// 轨迹表
			noteTraceService.saveNoteTrace(noteid, healthid, Constant.client_type_HEALTH,
					Constant.physical_or_health_note_status_DOWN_DESC);
		}
	}

	/**
	 * 入参信息
	 **/
	public void checkResData(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		Assert.notNull(healthSubmitRequestMessage, "传入对象不能为空");
		Assert.notEmpty(healthSubmitRequestMessage.getHealth_note_id(), "健康函ID不能为空");
		Assert.notEmpty(healthSubmitRequestMessage.getHealth_choose_type(), "客户选择不能为空");
		Assert.notEmpty(healthSubmitRequestMessage.getHealth_submit_type(), "操作人类型不能为空");
	}

	/**
	 * 代理人提交
	 **/
	public void agentSubmit(HealthSubmitRequestMessage healthSubmitRequestMessage) {
		long startTime = 0;
		long endTime = 0;
		String noteid = healthDao.getNoteId(healthSubmitRequestMessage.getHealth_note_id());
		String healthChooseType = healthSubmitRequestMessage.getHealth_choose_type();
		// 获取业务员说明备注---agent_remark_desc
		String agentRemarkDesc = healthSubmitRequestMessage.getAgent_remark_desc();
		// 代理人自己上传客户的财务函资料
		Map<String, Object> healthMap = new HashMap<>();
		if (healthChooseType.equals(Constant.client_note_agent_choose_type_SELF_UPLOAD_IMAGE)) {
			// 函件的状态+代理人看到的状态+描述
			healthMap.put("clientNoteId", healthSubmitRequestMessage.getHealth_note_id());
			healthMap.put("noteStatus", Constant.physical_or_health_note_status_DOWN);
			healthMap.put("noteStatusDesc", Constant.physical_or_health_note_status_DOWN_DESC);
			healthMap.put("noteAgentStatus", Constant.note_agent_status_FINISH);
			healthMap.put("noteAgentStatusDesc", Constant.note_agent_status_FINISH_DESC);
			healthMap.put("agentRemarkDesc", agentRemarkDesc);
			// 代理人选择将客户的财务函资料交给机构
		} else if (healthChooseType.equals(Constant.client_note_agent_choose_type_TRANSFER_TO_BRANCH)) {
			// 函件的状态+状态描述+代理人看到的状态+代理人描述备注
			healthMap.put("clientNoteId", healthSubmitRequestMessage.getHealth_note_id());
			healthMap.put("noteStatus", Constant.physical_or_health_note_status_LINEDOWN);
			healthMap.put("noteStatusDesc", Constant.physical_or_health_note_status_LINEDOWN_DESC);
			healthMap.put("noteAgentStatus", Constant.note_agent_status_FINISH);
			healthMap.put("noteAgentStatusDesc", Constant.note_agent_status_FINISH_DESC);
			healthMap.put("agentRemarkDesc", agentRemarkDesc);
		}
		startTime = System.currentTimeMillis();
		healthDao.healthSubmitAgent(healthMap);
		endTime = System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康id:" + healthSubmitRequestMessage.getHealth_note_id(),
				"更新状态耗时:" + (endTime - startTime) + "ms");
		//添加轨迹
		if(healthChooseType.equals(Constant.client_note_agent_choose_type_SELF_UPLOAD_IMAGE)){
			noteTraceService.saveNoteTrace(noteid, healthSubmitRequestMessage.getHealth_note_id(), Constant. client_type_HEALTH_DESC, "健康函提交方式:代理人上传客户资料");
		}else if(healthChooseType.equals(Constant.client_note_agent_choose_type_TRANSFER_TO_BRANCH)){
			noteTraceService.saveNoteTrace(noteid, healthSubmitRequestMessage.getHealth_note_id(), Constant. client_type_HEALTH_DESC, "健康函提交方式:代理人转交机构处理");
		}	
	}

}
