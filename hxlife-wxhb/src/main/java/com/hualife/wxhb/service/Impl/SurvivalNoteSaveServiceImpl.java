package com.hualife.wxhb.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.request.SurvivalSaveRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.PushMessageService;
import com.hualife.wxhb.service.SurvivalNoteSaveService;
import com.hualife.wxhb.service.TaskImageService;
import com.hualife.wxhb.service.UpdateMainStatusService;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年8月4日
 */
@Service
public class SurvivalNoteSaveServiceImpl implements SurvivalNoteSaveService {
	private final Logger logger = LoggerFactory.getLogger(SurvivalNoteSaveServiceImpl.class);
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private UpdateMainStatusService updateMainStatusService;
	@Autowired
	private TaskImageService taskImageService;

	/**
	 * @author wupeixu
	 * @description 契调报告保存
	 * @param survivalSaveRequestMessage
	 */
	@Override
	@Transactional
	public void survivalNoteSave(SurvivalSaveRequestMessage survivalSaveRequestMessage) {
		long startTime = 0;
		long endTime = 0;
		// 非空校验
		checkData(survivalSaveRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				survivalSaveRequestMessage.getSurvival_note_id(), "把契调报告保存到数据库中");
		// 把契调报告保存到数据库中
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				" 把契调报告保存到数据库中");
		startTime = System.currentTimeMillis();
		survivalDao.saveSurvivalNoteBySurvivalNoteId(survivalSaveRequestMessage);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				survivalSaveRequestMessage.getSurvival_note_id(),
				"把契调报告保存到数据库中,程序运行时间： " + (endTime - startTime) + "ms");
		// 更新客户层函件状态信息
		String survivalNoteId = survivalSaveRequestMessage.getSurvival_note_id();
		String survivalNoteStatusDown = Constant.survival_note_status_DOWN;
		String survivalNoteStatusDownDesc = Constant.survival_note_status_DOWN_DESC;
		String noteAgentStatusFinish = Constant.note_agent_status_FINISH;
		String noteAgentStatusFinishDesc = Constant.note_agent_status_FINISH_DESC;
		Map<String, String> surMap = new HashMap<>();
		surMap.put("survivalNoteId", survivalNoteId);
		surMap.put("survivalNoteStatusDown", survivalNoteStatusDown);
		surMap.put("survivalNoteStatusDownDesc", survivalNoteStatusDownDesc);
		surMap.put("noteAgentStatusFinish", noteAgentStatusFinish);
		surMap.put("noteAgentStatusFinishDesc", noteAgentStatusFinishDesc);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				" 更新客户层函件业务状态为已处理,代理人查看状态为已完成");
		startTime = System.currentTimeMillis();
		survivalDao.updateClientNote(surMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				survivalSaveRequestMessage.getSurvival_note_id(),
				"更新客户层函件业务状态为已处理,代理人查看状态为已完成,程序运行时间： " + (endTime - startTime) + "ms");
		// 查询note_id
		String noteIDBySurvivalNoteId = survivalDao.getNoteIDBySurvivalNoteId(survivalNoteId);
		// 添加轨迹
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询契调对应的note_id");
		startTime = System.currentTimeMillis();
		String survivalForNoteId = survivalDao.getNoteIdForSurvival(survivalNoteId);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				survivalSaveRequestMessage.getSurvival_note_id(),
				"查询契调对应的note_id,程序运行时间： " + (endTime - startTime) + "ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"添加轨迹");
		noteTraceService.saveNoteTrace(survivalForNoteId, survivalNoteId, Constant.note_from_core_type_SURVIVAL,
				"契调函已完成");
		// 调用接口更改主表状态
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"调用接口更改主表状态");
		updateMainStatusService.updateMainStatusService(survivalNoteId, Constant.note_status_DOWN);
		// 调用接口，往给核心推送消息的info表插入数据
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"调用接口，往给核心推送消息的info表插入数据");
		pushMessageService.pushMessageToTable(survivalForNoteId, Constant.note_from_core_type_SURVIVAL);
		// 调用接口 上传图像
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"调用接口 上传图像");
		taskImageService.saveImageUpLoad(noteIDBySurvivalNoteId, Constant.note_from_core_type_SURVIVAL,
				Constant.client_type_SURVIVAL);

	}

	private void checkData(SurvivalSaveRequestMessage survivalSaveReqMessage) {
		Assert.notNull(survivalSaveReqMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				survivalSaveReqMessage.getSurvival_note_id(), "契调函保存接口开始检查报文");
		Assert.notEmpty(survivalSaveReqMessage.getSurvival_note_id(), "契调函id不能为null");
		Assert.notEmpty(survivalSaveReqMessage.getSurvival_note_report(), "契调报告不能为null");
	}

}
