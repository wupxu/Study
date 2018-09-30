package com.hualife.wxhb.service.Impl;

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
import com.hualife.wxhb.api.soap.message.request.noteFinishDeal.NoteFinishDealBody;
import com.hualife.wxhb.api.soap.message.request.noteFinishDeal.NoteFinishDealRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.NoteFinishDealService;
import com.hualife.wxhb.service.NoteTraceService;
/**
 * @author zhangweiwei
 * @description 核心回销消息impl
 * @date 2017-08-04
 */
@Service
public class NoteFinishDealServiceImpl implements NoteFinishDealService {
	
	private final Logger logger = LoggerFactory.getLogger(NoteFinishDealServiceImpl.class);
	
	@Autowired 
	private HealthDao healthDao;
	@Autowired 
	private PhysicalDao physicalDao;
    @Autowired 
    private ProblemDao problemDao;
    @Autowired
    private NoteTraceService noteTraceService;
    /**
     * 获取核心回销消息的返回报文
     */
	@Override
	
	public void noteFinishDeal(NoteFinishDealRequestMessage noteFinishDealRequestMessage) {
		NoteFinishDealBody noteFinishDealBody=noteFinishDealRequestMessage.getBody();
		//检查请求报文
		checkData(noteFinishDealBody);
		String noteType=noteFinishDealBody.getNote_type();
		String noteSeq=noteFinishDealBody.getNote_seq();
		String taskCode=noteFinishDealBody.getTaskcode();
		dealClientNote(taskCode,noteType,noteSeq);
		dealMainNote(taskCode,noteType,noteSeq);
	}
	/**
	 *  客户层更新函件主表状态
	 * @param taskCode
	 * @param noteType
	 * @param noteSeq
	 */
	@Transactional
	private void dealMainNote(String taskCode, String noteType, String noteSeq) {
		long startTime = 0;
		long endTime   = 0;

		if(Constant.note_from_core_type_FINAOCCU.equals(noteType)||Constant.note_from_core_type_HEALTH.equals(noteType)||Constant.note_from_core_type_PHYSICAL.equals(noteType)||Constant.note_from_core_type_SURVIVAL.equals(noteType)){
			//判断对应的客户层函件下的所有函件的状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),taskCode, "判断客户层函件下的所有函件的状态");
			boolean flag=false;
			HashMap<String,Object> clientNoteInfoMap=new HashMap<>();
			clientNoteInfoMap.put("note_seq",noteSeq);
			clientNoteInfoMap.put("task_code",taskCode);
			startTime=System.currentTimeMillis();
			List<TNoteClientNote> tNoteClientNotes=physicalDao.getClientNoteStatusByTaskCode(clientNoteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),""+taskCode, "查询客户层函件下的所有函件的状态--耗时:"+(endTime-startTime)+"ms");
			for(TNoteClientNote tNoteClientNote:tNoteClientNotes){
				if(Constant.client_type_HEALTH.equals(tNoteClientNote.getNoteType())){
					if(Constant.physical_or_health_note_status_WRITEOFF.equals(tNoteClientNote.getNoteStatus())){
						flag = true;
						continue;
					}else{
						flag = false;
						break;
					}
				}else if(Constant.client_type_PHYSICAL.equals(tNoteClientNote.getNoteType())){
					if(Constant.physical_or_health_note_status_WRITEOFF.equals(tNoteClientNote.getNoteStatus())){
						flag = true;
						continue;
					}else{
						flag = false;
						break;
					}
				}else if(Constant.client_type_FINA.equals(tNoteClientNote.getNoteType())){
					if(Constant.fina_note_status_WRITEOFF.equals(tNoteClientNote.getNoteStatus())){
						flag = true;
						continue;
					}else{
						flag = false;
						break;
					}			
				}else if(Constant.client_type_SURVIVAL.equals(tNoteClientNote.getNoteType())){
					if(Constant.survival_note_status_WRITEOFF.equals(tNoteClientNote.getNoteStatus())){
						flag = true;
						continue;
					}else{
						flag = false;
						break;
					}
				}
			}
			//如果客户层所有函件都已回销，修改客户层函件状态
			HashMap<String,Object> updateMainNoteMap=new HashMap<>();
			if(flag){
				updateMainNoteMap.put("note_status",Constant.note_status_FINISHED);//已结束
				updateMainNoteMap.put("client_note_id",tNoteClientNotes.get(0).getClientNoteId());
				updateMainNoteMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
				startTime=System.currentTimeMillis();
				physicalDao.updateMainNote(updateMainNoteMap);
				endTime=System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id"+tNoteClientNotes.get(0).getClientNoteId(), "更新函件主表信息--耗时:"+(endTime-startTime)+"ms");
			}
		}	
	}
	/**
	 * 更新函件状态
	 * @param taskCode
	 * @param noteType
	 * @param noteSeq
	 */
	@Transactional
	private void dealClientNote(String taskCode,String noteType,String noteSeq) {
		long startTime = 0;
		long endTime   = 0;
		//根据报文信息查询函件信息
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq, "根据报文信息查询函件信息");
		HashMap<String,Object> noteInfoMap=new HashMap<>();
		noteInfoMap.put("note_seq",noteSeq);
		noteInfoMap.put("task_code",taskCode);
		String noteId="";
		//客户层函件id
		String clientNoteId="";
		//问题件id
		String problemNoteId="";
		if(Constant.note_from_core_type_HEALTH.equals(noteType)){
			//查询健康函客户层id
			noteInfoMap.put("note_type",Constant.client_type_HEALTH);
			noteInfoMap.put("noteStatus",Constant.physical_or_health_note_status_DOWN);
			//获取客户层函件信息
			startTime=System.currentTimeMillis();
			TNoteClientNote tNoteClientNote=healthDao.getClientNoteIdByBody(noteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq, "查询健康函客户层id--耗时:"+(endTime-startTime)+"ms");
			if(tNoteClientNote!=null){
				clientNoteId=tNoteClientNote.getClientNoteId();
				noteId=tNoteClientNote.getNoteId();
				if(StringUtils.isNotBlank(clientNoteId)&&StringUtils.isNotBlank(noteId)){
					//更新客户层函件信息
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函的客户层id："+clientNoteId, "更新客户层函件信息");
					noteInfoMap.put("client_note_id", clientNoteId);
					noteInfoMap.put("note_status",Constant.physical_or_health_note_status_WRITEOFF);	
					noteInfoMap.put("note_status_desc",Constant.physical_or_health_note_status_WRITEOFF_DESC);		
					startTime=System.currentTimeMillis();
					healthDao.updateHealthNoteStatus(noteInfoMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),clientNoteId, "更新客户层函件信息--耗时:"+(endTime-startTime)+"ms");
					
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "插入一条健康函轨迹");
					noteTraceService.saveNoteTrace(noteId, clientNoteId, noteType, "健康函已回销");
				}else{
					throw new BusinessException("此函件id"+clientNoteId+"的健康函不存在!");
				}
			}else{
				throw new BusinessException("核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq+"对应的健康函不存在");
			}	
		}else if(Constant.note_from_core_type_PHYSICAL.equals(noteType)){
			//查询体检函客户层函件id
			noteInfoMap.put("note_type",Constant.client_type_PHYSICAL);
			noteInfoMap.put("noteStatus",Constant.physical_or_health_note_status_DOWN);
			startTime=System.currentTimeMillis();
			TNoteClientNote tNoteClientNote=physicalDao.getClientNoteIdByBody(noteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq, "查询客户层--耗时:"+(endTime-startTime)+"ms");
			if(tNoteClientNote!=null){
				clientNoteId=tNoteClientNote.getClientNoteId();
				noteId=tNoteClientNote.getNoteId();
				if(StringUtils.isNotBlank(clientNoteId)&&StringUtils.isNotBlank(noteId)){
					//更新客户层函件信息
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函的客户层id"+clientNoteId, "更新客户层函件信息");
					noteInfoMap.put("client_note_id", clientNoteId);
					noteInfoMap.put("note_status",Constant.physical_or_health_note_status_WRITEOFF);
					noteInfoMap.put("note_status_desc",Constant.physical_or_health_note_status_WRITEOFF_DESC);
					
					startTime=System.currentTimeMillis();
					physicalDao.updatePhysicalNoteStatus(noteInfoMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),""+clientNoteId, "更新客户层函件信息--耗时:"+(endTime-startTime)+"ms");
					
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "插入一条体检函轨迹");
					noteTraceService.saveNoteTrace(noteId, clientNoteId, noteType, "体检函已回销");
				}else{
					throw new BusinessException("此函件id"+clientNoteId+"的体检函不存在!");
				}
			}else{
				throw new BusinessException("核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq+"对应的体检函不存在");
			}				
		}else if(Constant.note_from_core_type_PROBLEM.equals(noteType)){
			//查询问题件函件id
			noteInfoMap.put("note_status",Constant.note_status_FINISHED);
			noteInfoMap.put("noteStatus",Constant.problem_note_status_DOWN);	

			startTime=System.currentTimeMillis();
			TNoteProblemNote tNoteProblemNote=problemDao.getNoteIdByBody(noteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq, "查询财务函客户层id--耗时:"+(endTime-startTime)+"ms");
			if(tNoteProblemNote!=null){
				problemNoteId=tNoteProblemNote.getProblemNoteId();
				noteId=tNoteProblemNote.getNoteId();
				if(StringUtils.isNotBlank(problemNoteId)&&StringUtils.isNotBlank(noteId)){
					//更新问题件信息
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"问题件id"+problemNoteId, "更新问题件件信息");
					noteInfoMap.put("note_id",noteId);
					noteInfoMap.put("problem_note_status",Constant.problem_note_status_WRITEOFF);
					noteInfoMap.put("problem_note_status_desc",Constant.problem_note_status_WRITEOFF_DESC);
					noteInfoMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));

					//更新问题件状态
					startTime=System.currentTimeMillis();
					problemDao.updateProblemNoteStatus(noteInfoMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),""+problemNoteId, "更新问题件件信息--耗时:"+(endTime-startTime)+"ms");
					//更新函件主表状态
					startTime=System.currentTimeMillis();
					problemDao.updateMainNoteStatus(noteInfoMap);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),""+problemNoteId, "更新函件主表信息--耗时:"+(endTime-startTime)+"ms");

					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "插入一条问题件轨迹");
					noteTraceService.saveNoteTrace(noteId, problemNoteId, noteType, "问题件已结束");
				}else{
					throw new BusinessException("此函件id"+problemNoteId+"的问题件不存在!");
				}
			}else{
				throw new BusinessException("核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq+"对应的问题件不存在");
			}
	
		}else{
			throw new BusinessException("核保任务号："+taskCode+" 函件类型："+noteType+" 核保任务序号："+noteSeq+"的函件信息不存在 ");
		}
	}
	/**
	 * 检查报文体信息
	 * @param noteFinishDealBody
	 */
	private void checkData(NoteFinishDealBody noteFinishDealBody) {
		
		Assert.notNull(noteFinishDealBody, Constant.error_DESC_BODY_NULL);
		Assert.notEmpty(noteFinishDealBody.getTaskcode(), "核保任务号不能为空");
		Assert.notEmpty(noteFinishDealBody.getNote_seq(), "核保任务序号不能为空");
		Assert.notEmpty(noteFinishDealBody.getNote_type(), "函件类型不能为空");
	}

}
