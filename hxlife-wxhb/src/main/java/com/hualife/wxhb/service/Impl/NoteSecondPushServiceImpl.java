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

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.soap.message.request.NoteSecondPush.NoteSecondPushRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteHealthNote;
import com.hualife.wxhb.domain.entity.TNoteHealthNoteItem;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.domain.entity.TNotePhysicalExamNote;
import com.hualife.wxhb.domain.entity.TNotePhysicalExamNoteItem;
import com.hualife.wxhb.domain.entity.TNoteProblemDetail;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.domain.entity.TNoteProblemObject;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.NoteMainDao;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.GetMaxNo;
import com.hualife.wxhb.service.NoteSecondPushService;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.TaskPushInfoService;

/**
 * @author yangpeixin
 * @deprecation 重新下发impl
 * @date 2017-08-24
 */

@Service
public class NoteSecondPushServiceImpl implements NoteSecondPushService {

	private final Logger logger = LoggerFactory.getLogger(NoteSecondPushServiceImpl.class);

	@Autowired
	private AgentDao agentDao;
	@Autowired
	private PhysicalDao physicalDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private GetMaxNo getMaxNo;
	@Autowired
	private NoteMainDao noteMainDao;
	@Autowired
	private HealthDao healthDao;
	@Autowired
	private TaskPushInfoService taskPushInfoService;
	@Autowired 
	private NoteTraceService noteTraceService;
	/**
	 * 实现重新下发逻辑处理
	 **/
	@Override
	@Transactional
	public void saveNoteSecondPush(NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		// 检查入参
		checkResquestData(noteSecondPushRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteSecondPushRequestMessage.getBody().getTaskcode(), "开始检查报文");
		// 处理问题件
		if (noteSecondPushRequestMessage.getBody().getNote_type().equals(Constant.note_from_core_type_PROBLEM)) {
			handleProblem(noteSecondPushRequestMessage);
		}
		// 处理体检
		else if (noteSecondPushRequestMessage.getBody().getNote_type().equals(Constant.note_from_core_type_PHYSICAL)) {
			handlePhysical(noteSecondPushRequestMessage);
		}
		// 处理健康
		else if (noteSecondPushRequestMessage.getBody().getNote_type().equals(Constant.note_from_core_type_HEALTH)) {
			handleHealth(noteSecondPushRequestMessage);
		}
	}

	/**
	 * 健康逻辑
	 **/
	public void handleHealth(NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteSecondPushRequestMessage.getBody().getTaskcode(), "开始处理健康函件");
		String task_code = noteSecondPushRequestMessage.getBody().getTaskcode();
		// 查询NOTEID
		Map<String, Object> notemain = new HashMap<>();
		String type = Constant.note_type_CLIENT;
		notemain.put("noteType", type);
		notemain.put("taskcode", task_code);
		notemain.put("status", Constant.note_status_FINISHED);
		TNoteMain tNoteMain = new TNoteMain();
		tNoteMain = physicalDao.findTNoteMain(notemain);
		// 更改主表状态
		updateClientMain(tNoteMain);
		// 查询老t_note_client_note
		Map<String, Object> findAllMap = new HashMap<>();
		findAllMap.put("noteid", tNoteMain.getNoteId());
		findAllMap.put("noteSeq", noteSecondPushRequestMessage.getBody().getNote_seq());
		findAllMap.put("noteType", Constant.client_type_HEALTH);
		TNoteClientNote tNoteClientNote = clientDao.findAll(findAllMap);
		// 更新老表状态
		String oldClientNoteId = tNoteClientNote.getClientNoteId();
		updatetNoteClientNote(oldClientNoteId);
		// 插入新的数据
		String newClientNoteId = getMaxNo.getMaxNo();
		tNoteClientNote.setClientNoteId(newClientNoteId);
		insertClientNote(tNoteClientNote, noteSecondPushRequestMessage);
		//查询老t_note_health_note
		TNoteHealthNote tNoteHealthNote = healthDao.getTNoteHealthNote(oldClientNoteId);
		//插入新t_note_health_note
		tNoteHealthNote.setHealthNoteId(newClientNoteId);
		insertTNoteHealthNote(tNoteHealthNote);
		//查询老t_note_health_note_item
		List<TNoteHealthNoteItem> tNoteHealthNoteItemList = healthDao.getTNoteHealthNoteItem(oldClientNoteId);
		for(TNoteHealthNoteItem tNoteHealthNoteItem:tNoteHealthNoteItemList){
			tNoteHealthNoteItem.setHealthNoteId(newClientNoteId);
			tNoteHealthNoteItem.setNoteItemStatus("N");
			tNoteHealthNoteItem.setNoteItemId(getMaxNo.getMaxNo());
		}
		healthDao.insertNoteHealthItem(tNoteHealthNoteItemList);
		//雕永归集
		noteTraceService.saveNoteTrace(tNoteMain.getNoteId(), oldClientNoteId, Constant.note_from_core_type_HEALTH,  Constant.physical_or_health_note_status_CANCEL_DESC);
		//雕永新贵继
		noteTraceService.saveNoteTrace(tNoteMain.getNoteId(), newClientNoteId, Constant.note_from_core_type_HEALTH, Constant.physical_health_note_status_UNPUSH_DESC);
		//调用TASK接口
		Map<String, Object> taskMap = new HashMap<>();
		taskMap.put("noteId", tNoteMain.getNoteId());
		taskMap.put("specificNoteId", newClientNoteId);
		taskMap.put("clientName", tNoteMain.getClientName());
		taskMap.put("agentNo",tNoteMain.getAgentNo());
		taskMap.put("noteType", Constant.note_from_core_type_HEALTH);
		taskMap.put("reason", noteSecondPushRequestMessage.getBody().getNote_second_reason());
		taskPushInfoService.saveReSendNoteToAgent(taskMap);
	}
	
	/**
	 * 插入TNoteHealthNote
	 **/
	public void insertTNoteHealthNote(TNoteHealthNote tNoteHealthNote) {
		tNoteHealthNote.setAgentRemarkDesc("");
		healthDao.insertNoteHealth(tNoteHealthNote);
		
	}

	/**
	 * 体检逻辑
	 **/
	public void handlePhysical(NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteSecondPushRequestMessage.getBody().getTaskcode(), "开始处理体检函件");
		String task_code = noteSecondPushRequestMessage.getBody().getTaskcode();
		// 查询NOTEID
		Map<String, Object> notemain = new HashMap<>();
		String type = Constant.note_type_CLIENT;
		notemain.put("noteType", type);
		notemain.put("taskcode", task_code);
		notemain.put("status", Constant.note_status_FINISHED);
		TNoteMain tNoteMain = new TNoteMain();
		tNoteMain = physicalDao.findTNoteMain(notemain);
		// 更改主表状态
		updateClientMain(tNoteMain);
		// 查询老t_note_client_note
		Map<String, Object> findAllMap = new HashMap<>();
		findAllMap.put("noteid", tNoteMain.getNoteId());
		findAllMap.put("noteSeq", noteSecondPushRequestMessage.getBody().getNote_seq());
		findAllMap.put("noteType", Constant.client_type_PHYSICAL);
		TNoteClientNote tNoteClientNote = clientDao.findAll(findAllMap);
		// 更新老表状态
		String oldClientNoteId = tNoteClientNote.getClientNoteId();
		updatetNoteClientNote(oldClientNoteId);
		// 插入新的数据
		String newClientNoteId = getMaxNo.getMaxNo();
		tNoteClientNote.setClientNoteId(newClientNoteId);
		insertClientNote(tNoteClientNote, noteSecondPushRequestMessage);
		//查询老t_note_physical_exam_note
		TNotePhysicalExamNote tNotePhysicalExamNote = physicalDao.getTNotePhysicalExamNote(oldClientNoteId);
		//插入新数据
		tNotePhysicalExamNote.setPhysicalNoteId(newClientNoteId);
		insertTNotePhysicalExamNote(tNotePhysicalExamNote);
		//查询老t_note_physical_exam_note_item
		List<TNotePhysicalExamNoteItem> tNotePhysicalExamNoteItemList = physicalDao.getTNotePhysicalExamNoteItem(oldClientNoteId);
		for(TNotePhysicalExamNoteItem tNotePhysicalExamNoteItem:tNotePhysicalExamNoteItemList){
			tNotePhysicalExamNoteItem.setPhysicalNoteId(newClientNoteId);
		}
		physicalDao.insertPhysicalItem(tNotePhysicalExamNoteItemList);
		//雕永归集
		noteTraceService.saveNoteTrace(tNoteMain.getNoteId(), oldClientNoteId, Constant.note_from_core_type_PHYSICAL,  Constant.physical_or_health_note_status_CANCEL_DESC);
		//雕永新贵继
		noteTraceService.saveNoteTrace(tNoteMain.getNoteId(), newClientNoteId, Constant.note_from_core_type_PHYSICAL, Constant.physical_health_note_status_UNPUSH_DESC);
		//调用TASK接口
		Map<String, Object> taskMap = new HashMap<>();
		taskMap.put("noteId", tNoteMain.getNoteId());
		taskMap.put("specificNoteId", newClientNoteId);
		taskMap.put("clientName", tNoteMain.getClientName());
		taskMap.put("agentNo",tNoteMain.getAgentNo());
		taskMap.put("noteType", Constant.note_from_core_type_PHYSICAL);
		taskMap.put("reason", noteSecondPushRequestMessage.getBody().getNote_second_reason());
		taskPushInfoService.saveReSendNoteToAgent(taskMap);
	}
	/**
	 * 插入新TNotePhysicalExamNote
	 **/
	public void insertTNotePhysicalExamNote(TNotePhysicalExamNote tNotePhysicalExamNote) {
		//is_self_health
		tNotePhysicalExamNote.setAgentChooseType("");
		tNotePhysicalExamNote.setAgentRemarkDesc("");	
		tNotePhysicalExamNote.setIsUploadSelfImage("");	
		tNotePhysicalExamNote.setRemarkDesc("");
		physicalDao.insertNotePhysicalExam(tNotePhysicalExamNote);
		
	}

	/**
	 * 插入新clientnote
	 **/
	public void insertClientNote(TNoteClientNote tNoteClientNote,
			NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		tNoteClientNote.setNoteStatus(Constant.physical_health_note_status_UNPUSH);
		tNoteClientNote.setNoteStatusDesc(Constant.physical_health_note_status_UNPUSH_DESC);
		tNoteClientNote.setNoteAgentStatus(Constant.note_agent_status_UNPUSH);
		tNoteClientNote.setNoteAgentStatusDesc(Constant.note_agent_status_UNPUSH_DESC);
		tNoteClientNote.setNoteClientStatus(Constant.note_client_status_UNFINISHED);
		tNoteClientNote.setNoteClientStatusDesc(Constant.note_client_status_UNFINISHED_DESC);
		tNoteClientNote.setIsSecondNote(Constant.is_not_second_note_Y);
		tNoteClientNote.setSecondReason(noteSecondPushRequestMessage.getBody().getNote_second_reason());
		tNoteClientNote.setReportNoteId("");
		tNoteClientNote.setFinishAgentReport("");
		tNoteClientNote.setNoteImageUrl("");
		tNoteClientNote.setNoteFtpImageUrl("");
		tNoteClientNote.setNotQualifiedReason("");
		tNoteClientNote.setIsNotQualifiedNote(Constant.answer_N);
		tNoteClientNote.setReportNoteId(null);
		clientDao.insertNoteClient(tNoteClientNote);
	}

	/**
	 * 更新CLIENTNOTE状态
	 **/
	private void updatetNoteClientNote(String oldClientNoteId) {
		Map<String, Object> map = new HashMap<>();
		map.put("clientId", oldClientNoteId);
		map.put("note_status", Constant.physical_or_health_note_status_CANCEL);
		map.put("note_status_desc", Constant.physical_or_health_note_status_CANCEL_DESC);
		physicalDao.updatNotSecondPhysical(map);

	}

	/**
	 * 客户层函件主表
	 **/
	public void updateClientMain(TNoteMain tNoteMain) {
		Date time = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("noteid", tNoteMain.getNoteId());
		map.put("NoteStatus", Constant.problem_note_status_UNPUSH);
		map.put("PhoneSuccess", Constant.answer_N);
		map.put("time", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", time));
		noteMainDao.updateMainSecondStatus(map);
		
	}

	/**
	 * 问题件处理
	 **/
	public void handleProblem(NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteSecondPushRequestMessage.getBody().getTaskcode(), "开始处理问题件");
		String task_code = noteSecondPushRequestMessage.getBody().getTaskcode();
		String task_seq = noteSecondPushRequestMessage.getBody().getNote_seq();
		// 查询NOTEID
		Map<String, Object> notemain = new HashMap<>();
		String type = Constant.note_type_PROBLEM;
		notemain.put("noteType", type);
		notemain.put("taskcode", task_code);
		notemain.put("taskseq", task_seq);
		notemain.put("status", Constant.note_status_FINISHED);
		TNoteMain tNoteMain = new TNoteMain();
		tNoteMain = agentDao.findTNoteMain(notemain);
		String oldnoteid = tNoteMain.getNoteId();
		// 更改老主表状态
		updateMain(tNoteMain);
		// 插入新的主表状态
		String newNoteid = getMaxNo.getMaxNo();
		insertMain(tNoteMain, newNoteid);
		// 查询老t_note_problem_note数据
		TNoteProblemNote tNoteProblemNote = problemDao.findNoteSecond(oldnoteid);
		String oldproblemNoteId = tNoteProblemNote.getProblemNoteId();
		// 更改老表状态
		updateOldTNoteProblemNote(oldnoteid);
		// 插入新表状态
		String newproblemNoteId = getMaxNo.getMaxNo();
		tNoteProblemNote.setProblemNoteId(newproblemNoteId);
		tNoteProblemNote.setNoteId(newNoteid);
		insertTNoteProblemNote(tNoteProblemNote, noteSecondPushRequestMessage);
		// 查询老t_note_problem_object
		dealProblemObject(oldproblemNoteId, newproblemNoteId);
		//雕永归集
		noteTraceService.saveNoteTrace(oldnoteid, oldproblemNoteId, Constant.note_from_core_type_PROBLEM,  Constant.note_status_FINISHED_DESC);
		//雕永新贵继
		noteTraceService.saveNoteTrace(newNoteid, newproblemNoteId, Constant.note_from_core_type_PROBLEM, Constant.problem_note_status_UNPUSH_DESC);
		//调用TASK接口
		Map<String, Object> taskMap = new HashMap<>();
		taskMap.put("noteId", newNoteid);
		taskMap.put("specificNoteId", newproblemNoteId);
		taskMap.put("clientName", tNoteMain.getClientName());
		taskMap.put("agentNo",tNoteMain.getAgentNo());
		taskMap.put("noteType", Constant.note_from_core_type_PROBLEM);
		taskMap.put("reason", noteSecondPushRequestMessage.getBody().getNote_second_reason());
		taskPushInfoService.saveReSendNoteToAgent(taskMap);	

	}

	/**
	 * 处理逻辑
	 **/
	private void dealProblemObject(String oldproblemNoteId, String newproblemNoteId) {
		Date time = new Date();

		List<TNoteProblemObject> tNoteProblemObjectList = problemDao.getTNoteProblemObject(oldproblemNoteId);
		for (TNoteProblemObject tNoteProblemObject : tNoteProblemObjectList) {
			String oldObjectId = tNoteProblemObject.getProblemObjectId();
			List<TNoteProblemDetail> tNoteProblemDetailList = problemDao.getTNoteProblemDetail(oldObjectId);
			String newProblemObjectId = getMaxNo.getMaxNo();
			for (TNoteProblemDetail tNoteProblemDetail : tNoteProblemDetailList) {
				tNoteProblemDetail.setProblemObjectId(newProblemObjectId);
				tNoteProblemDetail.setProblemAnswer("");
				tNoteProblemDetail.setCreatedDate(time);
			}
			problemDao.insertTNoteProblemDetail(tNoteProblemDetailList);
			tNoteProblemObject.setProblemNoteId(newproblemNoteId);
			tNoteProblemObject.setProblemObjectId(newProblemObjectId);
			if(tNoteProblemObject.getProblemObject().equals(Constant.problem_object_AGENT)){			
				tNoteProblemObject.setProblemObjectStatus(Constant.problem_object_status_UNPUSH);
			}else{
				
				tNoteProblemObject.setProblemObjectStatus(Constant.problem_object_status_UNFINISHED);
			}
		}
		problemDao.insertNoteProblemObject(tNoteProblemObjectList);
	}

	/**
	 * 插入新TNoteProblemNote
	 **/
	private void insertTNoteProblemNote(TNoteProblemNote tNoteProblemNote,
			NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		tNoteProblemNote.setNoteStatus(Constant.problem_note_status_UNPUSH);
		tNoteProblemNote.setNoteStatusDesc(Constant.problem_note_status_UNPUSH_DESC);
		tNoteProblemNote.setIsSecondNote(Constant.is_not_second_note_Y);
		tNoteProblemNote.setSecondReason(noteSecondPushRequestMessage.getBody().getNote_second_reason());
		Date time = new Date();
		tNoteProblemNote.setUpdatedDate(time);
		tNoteProblemNote.setCreatedDate(time);
		List<TNoteProblemNote> tNoteProblemNoteList = new ArrayList<>();
		tNoteProblemNoteList.add(tNoteProblemNote);
		problemDao.insertNoteProblem(tNoteProblemNoteList);
	}

	/**
	 * 更新老TNoteProblemNote
	 **/
	private void updateOldTNoteProblemNote(String oldnoteid) {
		Map<String, String> updateNoteSecondProblem = new HashMap<>();
		updateNoteSecondProblem.put("status", Constant.problem_note_status_CANCEL);
		updateNoteSecondProblem.put("noteid", oldnoteid);
		updateNoteSecondProblem.put("statusDesc", Constant.problem_note_status_CANCEL_DESC);
		updateNoteSecondProblem.put("time", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		problemDao.updateNoteSecond(updateNoteSecondProblem);
	}

	/**
	 * 插入新的主表数据
	 **/
	public void insertMain(TNoteMain tNoteMain, String newNoteid) {
		tNoteMain.setNoteId(newNoteid);
		tNoteMain.setNoteStatus(Constant.problem_note_status_UNPUSH);
		tNoteMain.setPhoneSuccess(Constant.answer_N);
		Date time = new Date();
		tNoteMain.setCreatedDate(time);
		tNoteMain.setUpdatedDate(time);
		tNoteMain.setPushType("");
		noteMainDao.insertNoteMain(tNoteMain);
	}

	/**
	 * 更新老主表状态
	 **/
	public void updateMain(TNoteMain tNoteMain) {
		String noteid = tNoteMain.getNoteId();
		Date time = new Date();
		Map<String, Object> updateNoteSecondCancel = new HashMap<>();
		updateNoteSecondCancel.put("status", Constant.note_status_FINISHED);
		updateNoteSecondCancel.put("noteid", noteid);
		updateNoteSecondCancel.put("time", time);
		agentDao.updateNoteSecondMain(updateNoteSecondCancel);

	}

	
		

	/**
	 * 检查参数
	 **/
	public void checkResquestData(NoteSecondPushRequestMessage noteSecondPushRequestMessage) {
		Assert.notNull(noteSecondPushRequestMessage, "入参对象为空");
		Assert.notEmpty(noteSecondPushRequestMessage.getBody().getNote_second_reason(), "重新下发原因不能为空");
		Assert.notEmpty(noteSecondPushRequestMessage.getBody().getNote_seq(), "核保任务序号不能为空");
		Assert.notEmpty(noteSecondPushRequestMessage.getBody().getNote_type(), "函件类型不能为空");
		Assert.notEmpty(noteSecondPushRequestMessage.getBody().getTaskcode(), "核保任务号不能为空");
	}

	
}
