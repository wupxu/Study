package com.hualife.wxhb.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.soap.message.request.noteNotQualified.NoteNotQualifiedRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.NoteNotQualifiedService;
import com.hualife.wxhb.service.TaskPushInfoService;

/**
 * @author yangpeixin
 * @deprecation 次品单Impl
 * @date 2017-08-24
 */
@Service
public class NoteNotQualifiedServiceImpl implements NoteNotQualifiedService {

	private final Logger logger = LoggerFactory.getLogger(NoteSecondPushServiceImpl.class);

	@Autowired
	private AgentDao agentDao;
	@Autowired
	private PhysicalDao physicalDao;
	@Autowired
	private HealthDao healthDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private TaskPushInfoService taskPushInfoService;
	@Autowired
	private ImageDao imageDao;
	/**
	 * 实现次品单逻辑处理
	 **/
	@Override
	@Transactional
	public void saveNoteNotQualified(NoteNotQualifiedRequestMessage noteNotQualifiedRequestMessage) {
		// 检查入参
		checkResquestData(noteNotQualifiedRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteNotQualifiedRequestMessage.getBody().getTaskcode(), "开始检查报文");
		// 存储数据
		saveResult(noteNotQualifiedRequestMessage);
	}

	/**
	 * 检查参数
	 **/
	private void checkResquestData(NoteNotQualifiedRequestMessage noteNotQualifiedRequestMessage) {
		Assert.notNull(noteNotQualifiedRequestMessage, "入参对象为空");
		Assert.notEmpty(noteNotQualifiedRequestMessage.getBody().getImage_failed_reason(), "次品单原因不能为空");
		Assert.notEmpty(noteNotQualifiedRequestMessage.getBody().getNote_seq(), "核保任务序号不能为空");
		Assert.notEmpty(noteNotQualifiedRequestMessage.getBody().getNote_type(), "函件类型不能为空");
		Assert.notEmpty(noteNotQualifiedRequestMessage.getBody().getTaskcode(), "核保任务号不能为空");
	}

	/**
	 * 存储数据
	 **/
	private void saveResult(NoteNotQualifiedRequestMessage noteNotQualifiedRequestMessage) {
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteNotQualifiedRequestMessage.getBody().getTaskcode(), "开始处理数据");
		String task_code = noteNotQualifiedRequestMessage.getBody().getTaskcode();
		String note_seq = noteNotQualifiedRequestMessage.getBody().getNote_seq();
		String note_type = noteNotQualifiedRequestMessage.getBody().getNote_type();
		// 查询NOTEID
		String noteid = "";
		Map<String, Object> notemain = new HashMap<>();
		Map<String, Object> updateNoteNotQualifiedMainMap = new HashMap<>();
		if (note_type.equals(Constant.note_from_core_type_PROBLEM)) {
			String type = Constant.note_type_PROBLEM;
			notemain.put("noteType", type);
			notemain.put("taskcode", task_code);
			notemain.put("status", Constant.note_status_FINISHED);
			noteid = agentDao.findNoteidByProblem(notemain);
			// 更新主表状态 不考虑状态直接处理 次品单处理
			updateNoteNotQualifiedMainMap.put("status", Constant.problem_note_status_IMAGE_FAILED);
			updateNoteNotQualifiedMainMap.put("noteid", noteid);
		} else {
			String type = Constant.note_type_CLIENT;
			notemain.put("noteType", type);
			notemain.put("taskcode", task_code);
			noteid = agentDao.findNoteidByClient(notemain);

			// 更新主表状态 不考虑状态直接处理 次品单处理
			updateNoteNotQualifiedMainMap.put("status", Constant.note_status_PUSHING);
			updateNoteNotQualifiedMainMap.put("noteid", noteid);
		}
		agentDao.updateNoteNotQualifiedMain(updateNoteNotQualifiedMainMap);
		TNoteMain	tNoteMain = imageDao.getMainInfoById(noteid);
		if(tNoteMain==null){
			throw new BusinessException("客户查询信息有误");
		}
		// 更新问题件表
		if (note_type.equals(Constant.note_from_core_type_PROBLEM)) {
			Map<String, Object> updatNoteNotQualifiedReasonMap = new HashMap<>();
			updatNoteNotQualifiedReasonMap.put("reason",
					noteNotQualifiedRequestMessage.getBody().getImage_failed_reason());
			updatNoteNotQualifiedReasonMap.put("noteSeq", note_seq);
			updatNoteNotQualifiedReasonMap.put("noteid", noteid);
			updatNoteNotQualifiedReasonMap.put("value", Constant.is_not_qualified_note_Y);
			updatNoteNotQualifiedReasonMap.put("noteStatus", Constant.problem_note_status_IMAGE_FAILED);
			updatNoteNotQualifiedReasonMap.put("noteStatusDesc", Constant.problem_note_status_IMAGE_FAILED_DESC);
			problemDao.updatNoteNotQualifiedReason(updatNoteNotQualifiedReasonMap);
			String problemId = problemDao.getProblemNoteIdByNoteId(noteid);
			//调用TASK接口
			Map<String, Object> failmap = new HashMap<>();
			failmap.put("noteId", noteid);
			failmap.put("specificNoteId", problemId);
			failmap.put("noteType", Constant.note_from_core_type_PROBLEM);
			failmap.put("agentNo", tNoteMain.getAgentNo());
			failmap.put("clientName", tNoteMain.getClientName());
			taskPushInfoService.saveSendFailNote(failmap);
		}
		// 更新体检函件状态
		if (note_type.equals(Constant.note_from_core_type_PHYSICAL)) {
			Map<String, Object> map = new HashMap<>();
			map.put("reason", noteNotQualifiedRequestMessage.getBody().getImage_failed_reason());
			map.put("noteid", noteid);
			map.put("noteSeq", note_seq);
			map.put("noteType", Constant.client_type_PHYSICAL);
			map.put("value", Constant.is_not_qualified_note_Y);
			map.put("noteStatus", Constant.physical_or_health_note_status_IMAGE_FAILED);
			map.put("noteStatusDesc", Constant.physical_or_health_note_status_IMAGE_FAILED_DESC);
			map.put("agentNoteStatus", Constant.note_agent_status_IMAGE_FAILED);
			map.put("agentNoteStatusDesc", Constant.note_agent_status_IMAGE_FAILED_DESC);
			physicalDao.updatNoteNotQualified(map);
			Map<String, Object> clientMap = new HashMap<>();
			clientMap.put("noteId", noteid);
			clientMap.put("type", Constant.note_from_core_type_PHYSICAL);
			String clientId = healthDao.findClinetId(clientMap);		
			//调用TASK接口
			Map<String, Object> failmap = new HashMap<>();
			failmap.put("noteId", noteid);
			failmap.put("specificNoteId", clientId);
			failmap.put("noteType", Constant.note_from_core_type_PHYSICAL);
			failmap.put("agentNo", tNoteMain.getAgentNo());
			failmap.put("clientName", tNoteMain.getClientName());
			taskPushInfoService.saveSendFailNote(failmap);
			
		}
		// 跟新健康函状态
		if (note_type.equals(Constant.note_from_core_type_HEALTH)) {
			Map<String, Object> map = new HashMap<>();
			map.put("reason", noteNotQualifiedRequestMessage.getBody().getImage_failed_reason());
			map.put("noteid", noteid);
			map.put("noteSeq", note_seq);
			map.put("noteType", Constant.client_type_HEALTH);
			map.put("value", Constant.is_not_qualified_note_Y);
			map.put("noteStatus", Constant.physical_or_health_note_status_IMAGE_FAILED);
			map.put("noteStatusDesc", Constant.physical_or_health_note_status_IMAGE_FAILED_DESC);
			map.put("agentNoteStatus", Constant.note_agent_status_IMAGE_FAILED);
			map.put("agentNoteStatusDesc", Constant.note_agent_status_IMAGE_FAILED_DESC);
			healthDao.updatNoteNotQualified(map);
			Map<String, Object> clientMap = new HashMap<>();
			clientMap.put("noteId", noteid);
			clientMap.put("type", Constant.note_from_core_type_HEALTH);
			String clientId = healthDao.findClinetId(clientMap);	
			//调用TASK接口
			Map<String, Object> failmap = new HashMap<>();
			failmap.put("noteId", noteid);
			failmap.put("specificNoteId", clientId);
			failmap.put("noteType", Constant.note_from_core_type_HEALTH);
			failmap.put("agentNo", tNoteMain.getAgentNo());
			failmap.put("clientName", tNoteMain.getClientName());
			taskPushInfoService.saveSendFailNote(failmap);
		}
		
	}
}
