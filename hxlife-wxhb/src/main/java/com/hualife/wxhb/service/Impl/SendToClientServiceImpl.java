package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.rest.message.request.SendToClientRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.SendToClientService;
/**
 * @author zhangweiwei
 * @deprecation 代理人转发函件消息给客户impl
 * @date 2017-08-04
 */
@Service
public class SendToClientServiceImpl implements SendToClientService{
	
	private final Logger logger = LoggerFactory.getLogger(SendToClientServiceImpl.class);

	@Autowired
	private AgentDao agentDao;
	@Autowired
	private NoteTraceService noteTraceService;
	/**
	 * 代理人转发函件消息给客户
	 */
	@Override
	@Transactional
	public void sendToCLient(SendToClientRequestMessage sendToClientRequestMessage) {
		
		//检查请求报文
		checkData(sendToClientRequestMessage);
		long startTime = 0;
		long endTime   = 0;
		String noteId=sendToClientRequestMessage.getNote_id();
		//存储客户层函件的转发消息
		HashMap<String,Object> noteMap=new HashMap<>();
		//存储问题件的转发消息
		HashMap<String,Object> problemNoteMap=new HashMap<>();
		//批处理sql
		List<TNoteClientNote> tNoteClientNoteList = new ArrayList<TNoteClientNote>();
		//批处理sql
		List<TNoteClientNote> tNoteClientNoteResList = new ArrayList<TNoteClientNote>();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "开始检查函件信息");
		noteMap.put("noteId",noteId);
		noteMap.put("noteStatus",Constant.note_status_UNPUSH);
		startTime=System.currentTimeMillis();
		String noteType=agentDao.getNoteTypeByNoteId(noteMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询函件信息耗时"+(endTime-startTime)+"ms");

		if(StringUtils.isNotBlank(noteType)){
			if(noteType.equals(Constant.note_type_CLIENT)){
				//获取客户层函件信息
				tNoteClientNoteList=agentDao.getClientNoteTypesByNoteId(noteId);
				if(tNoteClientNoteList.size()>0){
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "开始更新客户层函件信息");
					for(TNoteClientNote tNoteClientNote:tNoteClientNoteList){
						//体检函、健康函
						
						if(tNoteClientNote.getNoteType().equals(Constant.client_type_HEALTH)){
							tNoteClientNote.setNoteStatus(Constant.physical_or_health_note_status_PUSHING);
							tNoteClientNote.setNoteStatusDesc(Constant.physical_or_health_note_status_PUSHING_DESC);
							noteType=Constant.note_from_core_type_HEALTH;
						}else if(tNoteClientNote.getNoteType().equals(Constant.client_type_PHYSICAL)){
							tNoteClientNote.setNoteStatus(Constant.physical_or_health_note_status_PUSHING);
							tNoteClientNote.setNoteStatusDesc(Constant.physical_or_health_note_status_PUSHING_DESC);
							noteType=Constant.note_from_core_type_PHYSICAL;
						}else if(tNoteClientNote.getNoteType().equals(Constant.client_type_FINA)){
							tNoteClientNote.setNoteStatus(Constant.fina_note_status_PUSHING);
							tNoteClientNote.setNoteStatusDesc(Constant.fina_note_status_PUSHING_DESC);
							noteType=Constant.note_from_core_type_FINAOCCU;
						}else if(tNoteClientNote.getNoteType().equals(Constant.client_type_SURVIVAL)){
							continue;
						}else{
							throw new BusinessException("客户层函件不存在这种"+tNoteClientNote.getNoteType());
							
						}
						tNoteClientNote.setNoteAgentStatus(Constant.note_agent_status_PUSHING);
						tNoteClientNote.setNoteAgentStatusDesc(Constant.note_agent_status_PUSHING_DESC);
						tNoteClientNote.setNoteType(Constant.client_type_SURVIVAL);
						tNoteClientNote.setClientNoteId(tNoteClientNote.getClientNoteId());
						tNoteClientNoteResList.add(tNoteClientNote);
						//插入一条轨迹
						logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),tNoteClientNote.getClientNoteId(), "开始插入一条数据");
						noteTraceService.saveNoteTrace(noteId, tNoteClientNote.getClientNoteId(), noteType,DataConVersion.dataConVersion(Constant.transfer_data_NOTE_TYPE,noteType)+"已转发");
					}
					//更新客户层函件状态
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "更新客户层函件状态");
					
					startTime=System.currentTimeMillis();
					agentDao.batchUpdateClientNote(tNoteClientNoteResList);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"更新客户层函件状态耗时"+(endTime-startTime)+"ms");
					//更新函件主表状态
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "更新函件主表状态");
					HashMap<String,Object> mainNoteMap=new HashMap<>();
					mainNoteMap.put("note_status", Constant.note_status_PUSHING);
					mainNoteMap.put("note_id", noteId);
					mainNoteMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));

					startTime=System.currentTimeMillis();
					agentDao.updateNoteStatus(mainNoteMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"更新函件主表状态耗时"+(endTime-startTime)+"ms");
					
				}else{
					throw new BusinessException("函件id"+noteId+"的客户层函件不存在!");
				}
				
			}else if(noteType.equals(Constant.note_type_PROBLEM)){
				//获取问题件id
				startTime=System.currentTimeMillis();
				String problemNoteId=agentDao.getProblemNoteByNoteId(noteId);
				endTime=System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"获取问题件id耗时"+(endTime-startTime)+"ms");

				if(StringUtils.isNotBlank(problemNoteId)){
					//开始更新问题件对象信息
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "开始更新问题件对象信息");
					problemNoteMap.put("problem_object", Constant.problem_object_AGENT);
					problemNoteMap.put("problem_object_status", Constant.problem_object_status_PUSH);
					problemNoteMap.put("problem_note_id", problemNoteId);
					startTime=System.currentTimeMillis();
					agentDao.updateProblemObjectStatus(problemNoteMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId,"更新函件主表状态耗时"+(endTime-startTime)+"ms");

					//开始更新问题件信息
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "开始更新问题件信息");
					noteMap.put("note_status", Constant.problem_note_status_PUSHING);
					noteMap.put("note_status_desc", Constant.problem_note_status_PUSHING_DESC);
					noteMap.put("note_id", noteId);
					
					startTime=System.currentTimeMillis();
					agentDao.sendToClientProblem(noteMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId,"更新问题件信息耗时"+(endTime-startTime)+"ms");

					//更新函件主表状态
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "更新函件主表状态");
					HashMap<String,Object> mainNoteMap=new HashMap<>();
					mainNoteMap.put("note_status", Constant.note_status_PUSHING);
					mainNoteMap.put("note_id", noteId);
					mainNoteMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));

					startTime=System.currentTimeMillis();
					agentDao.updateNoteStatus(mainNoteMap);	
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId,"更新函件主表状态耗时"+(endTime-startTime)+"ms");
					noteType=Constant.note_from_core_type_PROBLEM;
					noteTraceService.saveNoteTrace(noteId, problemNoteId, noteType,"问题件已转发");

				}else{
					throw new BusinessException("函件id"+noteId+"的问题件不存在!");
				}		
			}else{
				//获取核保函id
				startTime=System.currentTimeMillis();
				String undwrtNoteId=agentDao.getUndwrtNoteByNoteId(noteId);
				endTime=System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"获取核保函id耗时"+(endTime-startTime)+"ms");

				if(StringUtils.isNotBlank(undwrtNoteId)){
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtNoteId, "更新核保表状态");
					noteMap.put("note_status", Constant.undwrt_note_status_PUSHING);
					noteMap.put("note_status_desc", Constant.undwrt_note_status_PUSHING_DESC);
					noteMap.put("undwrt_note_id", undwrtNoteId);
					
					startTime=System.currentTimeMillis();
					agentDao.sendToClientUndwrt(noteMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtNoteId,"更新核保表状态耗时"+(endTime-startTime)+"ms");
					//更新函件主表状态
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),sendToClientRequestMessage.getNote_id(), "更新函件主表状态");
					HashMap<String,Object> mainNoteMap=new HashMap<>();
					mainNoteMap.put("note_status", Constant.note_status_PUSHING);
					mainNoteMap.put("note_id",noteId);
					mainNoteMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
					startTime=System.currentTimeMillis();
					agentDao.updateNoteStatus(mainNoteMap);	
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtNoteId,"更新函件主表状态耗时"+(endTime-startTime)+"ms");
					noteType=Constant.note_from_core_type_UNDWRT;
					noteTraceService.saveNoteTrace(noteId, undwrtNoteId, noteType,"核保函已转发");

				}else{
					throw new BusinessException("函件id"+noteId+"的核保函不存在!");
				}		
			}
		}else{
			return;
		}
		
	}
	/**
	 * 检查请求报文
	 */
	private void checkData(SendToClientRequestMessage sendToClientRequestMessage) {
		
		Assert.notNull(sendToClientRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),sendToClientRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(sendToClientRequestMessage.getNote_id(), "函件id不能为空");
	}
}
