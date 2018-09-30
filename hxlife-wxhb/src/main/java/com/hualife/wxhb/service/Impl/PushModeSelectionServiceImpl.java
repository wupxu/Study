package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.rest.message.request.PushModeSelectionRequestMessage;
import com.hualife.wxhb.api.rest.message.response.PushModeSelectionResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.dto.ClientMessage;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.PushModeSelectionService;
import com.hualife.wxhb.service.PushNotePrintService;

/**
 * @author 吴培旭
 * @description 函件下发方式选择实现类
 * @time 2017-08-03
 */
@Service
public class PushModeSelectionServiceImpl implements PushModeSelectionService {
	// log日志
	private final Logger logger = LoggerFactory.getLogger(PushModeSelectionServiceImpl.class);
	@Autowired
	private AgentDao agentDao;
	@Autowired
	private PushNotePrintService pushNotePrintService;
	@Autowired
	private NoteTraceService noteTraceService;

	/**
	 * @author 吴培旭
	 * @description 函件下发方式选择
	 * @time 2017-08-03
	 */
	@Override
	@Transactional
	public PushModeSelectionResponseMessage pushModeSelection(PushModeSelectionRequestMessage pushModeSelectionRequestMessage) {
		long startTime = 0;// 开始时间
		long endTime = 0;// 结束时间
		// 非空校验
		checkData(pushModeSelectionRequestMessage);
		
		// 中间表的生成时间
		Date date = new Date();
		String time = DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", date);
		String pushType = pushModeSelectionRequestMessage.getPush_type();
		String nodeId = pushModeSelectionRequestMessage.getNote_id();
		//查询数据库里是否有函件打印方式
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询数据库里是否有函件打印方式");
		startTime = System.currentTimeMillis();
		String pushToType = agentDao.findPushTypeByNoteId(nodeId);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				nodeId, "查询函件下发方式,程序运行时间： " + (endTime - startTime) + "ms");
		if (!StringUtils.isEmpty(pushToType)) {
			throw new BusinessException("下发方式已经选择");
		}
		
		// 更改函件处理状态
		Map<Object, Object> pushMap = new HashMap<>();
		pushMap.put("nodeId", nodeId);
		pushMap.put("pushType", pushType);
		pushMap.put("time", time);
		// 声明函件下发返回报文
		PushModeSelectionResponseMessage pushModeSelectionResponseMessage = new PushModeSelectionResponseMessage();
		// 下发方式为电子函件时，更改主表函件状态为未转发
		if (pushType.equals(Constant.note_push_type_LINED)) {
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					nodeId, "下发方式为电子函件时，更改主表函件状态为未转发");
			pushMap.put("noteStatus", Constant.note_status_UNPUSH);
			// 函件下发方式为自己打印，更改主表函件状态为待打印, 客户层函件类型批量插入中间表
		} else if (pushType.equals(Constant.note_push_type_SELF_PRINT)) {
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					nodeId, "函件下发方式选择为自己打印");
			pushMap.put("noteStatus", Constant.note_status_WAITINGPRINT);
			// 更改client_note表状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"自己打印更改client_note表状态");
			updateClientStutas(nodeId, pushType);
			// 获取集合插入中间表
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"自己打印获取集合插入中间表");
			pushNotePrintService.addTaskPushnotePrint(nodeId, "");
			// 函件下发方式为机构打印，更改主表函件状态为已结束
		} else if (pushType.equals(Constant.note_push_type_BRANCH_PRINT)) {
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					nodeId, "函件下发方式选贼为机构打印");
			pushMap.put("noteStatus", Constant.note_status_FINISHED);
			// 更改client_note表状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"机构打印更改client_note表状态");
			updateClientStutas(nodeId,pushType);
			// 返回代理人所属渠道(前端新增字段)
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"查询机构打印返回代理人所属渠道");
			startTime = System.currentTimeMillis();
			pushModeSelectionResponseMessage = agentDao.returnPushModeSelectionResponseMessage(pushMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					pushModeSelectionRequestMessage.getNote_id(), "查询机构打印返回代理人所属渠道,程序运行时间： " + (endTime - startTime) + "ms");
			// 调用核心接口实时向核心推送 需要组织报文 判断排除契调函
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					nodeId, "函件下发方式为机构打印调用核心接口实时向核心推送 ");
			pushNotePrintService.setChooseTypeRestClientBody(nodeId);
		}
		// 更改主表函件状态
		startTime = System.currentTimeMillis();
		agentDao.updatePushTypeByNoteId(pushMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				pushModeSelectionRequestMessage.getNote_id(), "更改主表函件下发方式和函件状态,程序运行时间： " + (endTime - startTime) + "ms");
		// 返回报文
		return pushModeSelectionResponseMessage;
	}

	// 非空校验
	private void checkData(PushModeSelectionRequestMessage pushModeSelectionRequestMessage) {
		Assert.notNull(pushModeSelectionRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				pushModeSelectionRequestMessage.getNote_id(), "函件下发方式开始检查报文");
		Assert.notEmpty(pushModeSelectionRequestMessage.getPush_type(), "函件下发方式入参pushType不能null");
		Assert.notEmpty(pushModeSelectionRequestMessage.getNote_id(), "函件下发方式入参note_id不能null");
	}

	// 自己打印更改client_note表的函件状态
	private void updateClientStutas(String nodeId, String pushType) {
		// 更改client_note表的状态
		long startTime = 0;// 开始时间
		long endTime = 0;// 结束时间
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), nodeId,
				"更改client_note表函件状态");
		Map<Object, Object> updateMap = new HashMap<>();
		updateMap.put("nodeId", nodeId);
		if (pushType.equals(Constant.note_push_type_SELF_PRINT)) {
			updateMap.put("noteAgentStatusProducting", Constant.note_agent_status_PRODUCTING);
			updateMap.put("noteAgentStatusProductingDesc", Constant.note_agent_status_PRODUCTING_DESC);
			//添加轨迹
			noteTrace(nodeId);
		}
		// 体检函和健康函修改状态所用字段
		updateMap.put("clientTypePhysical", Constant.client_type_PHYSICAL);
		updateMap.put("clientTypeHealth", Constant.client_type_HEALTH);
		updateMap.put("physicalOrHealthNoteStatusLinedown", Constant.physical_or_health_note_status_LINEDOWN);
		updateMap.put("physicalOrHealthNoteStatusLinedownDesc", Constant.physical_or_health_note_status_LINEDOWN_DESC);
		// 财务函修改状态所用字段
		updateMap.put("clientTypeFina", Constant.client_type_FINA);
		updateMap.put("finaNoteStatusLinedown", Constant.fina_note_status_LINEDOWN);
		updateMap.put("finaNoteStatusLinedownDesc", Constant.fina_note_status_LINEDOWN_DESC);

		startTime = System.currentTimeMillis(); // 获取开始时间
		agentDao.updateClientNoteStutas(updateMap);
		endTime = System.currentTimeMillis(); // 获取结束时间
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), nodeId,
				"(体检函和健康函时修改状态)函件下发方式为自己打印,更改client_note表函件状态,程序运行时间： " + (endTime - startTime) + "ms");
		startTime = System.currentTimeMillis(); // 获取开始时间
		agentDao.updateClientNoteStutasForFina(updateMap);
		endTime = System.currentTimeMillis(); // 获取结束时间
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), nodeId,
				"(财务函时)函件下发方式为自己打印,更改client_note表函件状态,程序运行时间： " + (endTime - startTime) + "ms");
	}
	//更改轨迹
	private void noteTrace(String nodeId){
		List<ClientMessage> clilist = new ArrayList<>();
		long startTime = 0;// 开始时间
		long endTime = 0;// 结束时间
		startTime = System.currentTimeMillis(); // 获取开始时间
		clilist = agentDao.findNoteType(nodeId);
		endTime = System.currentTimeMillis(); // 获取结束时间
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), nodeId,
				"获取client表的client_note_id，note_type： " + (endTime - startTime) + "ms");
		for (ClientMessage clientMessage : clilist) {
			String client_note_id = clientMessage.getClient_note_id();
			String note_type = clientMessage.getNote_type();
			if (Constant.client_type_FINA.equals(note_type)) {
				noteTraceService.saveNoteTrace(nodeId, client_note_id, note_type, "财务函变更client表轨迹添加");
			}
			if (Constant.client_type_PHYSICAL.equals(note_type)) {
				noteTraceService.saveNoteTrace(nodeId, client_note_id, note_type, "体检函变更client表轨迹添加");
			}
			if (Constant.client_type_HEALTH.equals(note_type)) {
				noteTraceService.saveNoteTrace(nodeId, client_note_id, note_type, "健康函变更client表轨迹添加");
			}
		}
	}

}
