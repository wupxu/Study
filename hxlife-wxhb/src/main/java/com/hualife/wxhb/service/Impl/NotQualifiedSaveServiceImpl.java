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
import com.hualife.wxhb.api.rest.message.request.NotQualifiedSaveRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.NotQualifiedSaveService;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.PushMessageService;
import com.hualife.wxhb.service.TaskImageService;
import com.hualife.wxhb.service.UpdateMainStatusService;
/**
 * @descrption : 次品单上传serviceImpl
 * @author : linyongtao
 * @date : 2017.08.26
 */
@Service
public class NotQualifiedSaveServiceImpl implements NotQualifiedSaveService{
	private final Logger logger = LoggerFactory.getLogger(NotQualifiedSaveServiceImpl.class);
	@Autowired 
	private ProblemDao problemDao;
	@Autowired 
	private HealthDao healthDao;
	@Autowired 
	private PhysicalDao physicalDao;
	@Autowired 
	private ClientDao clientDao;
	@Autowired
	private UpdateMainStatusService updateMainStatusService;
	@Autowired
	private NoteTraceService noteTraceService;	
	@Autowired
	private TaskImageService taskImageService;
	@Autowired
	private PushMessageService pushMessageService;
	long startTime =0;	
	long endTime =0;
	/**
	 * @description :次品单影像上传保存
	 * @author : linyongtao
	 *  @date : 2017.08.26
	 * **/
	@Override
	public void notQualifiedSave(NotQualifiedSaveRequestMessage notQualifiedSaveRequestMessage) {
		checkData(notQualifiedSaveRequestMessage);
		
		//获取请求参数
		String noteId = notQualifiedSaveRequestMessage.getNote_id();
		String noteType = notQualifiedSaveRequestMessage.getNote_type();
        String problemNoteId = problemDao.getProblemNoteIdByNoteId(noteId);
		String clientNoteID = clientDao.getNoteIdByClientNoteId(noteId);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "获取请求参数");
		
		//判断前端传入的note_id是否存在
		startTime = System.currentTimeMillis();
		Long number = clientDao.selectExitByNoteId(noteId);
		endTime = System.currentTimeMillis();		
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "查询前端传入的note_id是否存在--耗时"+(endTime-startTime)+"ms");
		if(number ==0){
			throw new BusinessException("函件类型为"+noteType+"的次品单提交,前端传入的note_id:"+noteId+"-错误!");			
		}
		//函件类型是问题件次品单
		if(noteType.equals(Constant.note_from_core_type_PROBLEM)){
			dealProblemData(noteId,problemNoteId);
			//影像推送
			taskImageService.saveImageUpLoad(noteId, Constant.note_from_core_type_PROBLEM,problemNoteId);		
			//往中间表存数据
			pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PROBLEM);
		}else if(noteType.equals(Constant.note_from_core_type_PHYSICAL)){//体检函--次品单
			dealPhysicalData(noteId,clientNoteID);
			//影像推送
			taskImageService.saveImageUpLoad(noteId, Constant.note_from_core_type_PHYSICAL,clientNoteID);		
			//往中间表存数据
			pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PHYSICAL);
		}else if(noteType.equals(Constant.note_from_core_type_HEALTH)){//健康函--次品单
			dealHealthData(noteId,clientNoteID);
			//影像推送
			taskImageService.saveImageUpLoad(noteId, Constant.note_from_core_type_HEALTH,clientNoteID);		
			//往中间表存数据
			pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_HEALTH);
		}else{
			throw new BusinessException("函件类型为:"+noteType+"的次品单提交,前端传入的note_type错误!");
		}		
		//如果是健康函或体检函次品单提交，需要判定该note_id下其他有效函件的状态
		if(noteType.equals(Constant.note_from_core_type_HEALTH)||
				noteType.equals(Constant.note_from_core_type_PHYSICAL)){
			Map<String,Object> selectClientNoteIdMap =new HashMap<>(); 	
			selectClientNoteIdMap.put("noteId", noteId);
			selectClientNoteIdMap.put("noteType", noteType);
			String clientNoteId = clientDao.getClientNoteIdByNoteId(selectClientNoteIdMap);
			updateMainStatusService.updateMainStatusService(clientNoteId,Constant.note_status_DOWN);
		}
	}
	
	/**
	 * 处理问题件次品单--提交
	 * **/
	@Transactional
	public void dealProblemData(String noteId,String problemNoteId){
		Map<String,Object> updateStatusMap =new HashMap<>(); 
		//主表状态
		updateStatusMap.put("noteStatusMain", Constant.note_status_DOWN);
		//问题件表状态+状态描述
		updateStatusMap.put("noteStatusProblem", Constant.problem_note_status_DOWN);
		updateStatusMap.put("noteStatusDesc", Constant.problem_note_status_DOWN_DESC);
		//函件类型--问题件
		updateStatusMap.put("noteType", Constant.note_type_PROBLEM);
		updateStatusMap.put("noteId", noteId);
		//更新状态操作
		startTime = System.currentTimeMillis();
		problemDao.updateNoteStatus(updateStatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "问题件次品单提交,更改状态--耗时"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "问题件次品单提交");
		noteTraceService.saveNoteTrace(noteId, problemNoteId,Constant.note_from_core_type_PROBLEM, "问题件-已处理");
	}
	
	/**
	 * 处理体检函
	 * **/
	@Transactional
	public void dealPhysicalData(String noteId,String clientNoteID){
		Map<String,Object> updateStatusMap =new HashMap<>(); 
		//体检函表状态+状态描述
		updateStatusMap.put("noteStatus", Constant.physical_or_health_note_status_DOWN);
		updateStatusMap.put("noteStatusDesc", Constant.physical_or_health_note_status_DOWN_DESC);
		updateStatusMap.put("noteAgentStatus", Constant.note_agent_status_FINISH);
		updateStatusMap.put("noteAgentStatusDesc", Constant.note_agent_status_FINISH_DESC);
		//函件类型--财务函
		updateStatusMap.put("noteTypeClient", Constant.client_type_PHYSICAL);
		updateStatusMap.put("noteId",noteId);
		startTime = System.currentTimeMillis();
		physicalDao.updateClientStatus(updateStatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "财务函次品单提交,更改状态--耗时"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "体检函次品单提交");
		noteTraceService.saveNoteTrace(noteId, clientNoteID, Constant.note_from_core_type_PHYSICAL, "体检函-已完成");
	}
	
	/**
	 * 处理健康函次品单--提交
	 * **/
	@Transactional
	public void dealHealthData(String noteId,String clientNoteID){
		Map<String,Object> updateStatusMap =new HashMap<>(); 
		//健康函表状态+状态描述
		updateStatusMap.put("noteStatus", Constant.physical_or_health_note_status_DOWN);
		updateStatusMap.put("noteStatusDesc", Constant.physical_or_health_note_status_DOWN_DESC);
		updateStatusMap.put("noteAgentStatus", Constant.note_agent_status_FINISH);
		updateStatusMap.put("noteAgentStatusDesc", Constant.note_agent_status_FINISH_DESC);
		//函件类型--健康函
		updateStatusMap.put("noteTypeClient", Constant.client_type_HEALTH);
		updateStatusMap.put("noteId",noteId);
		startTime = System.currentTimeMillis();
		healthDao.updateClientStatus(updateStatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "健康函次品单提交,更改状态--耗时"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "健康函次品单提交");
		noteTraceService.saveNoteTrace(noteId, clientNoteID, Constant.note_from_core_type_HEALTH, "健康函-已完成");
	}
	
	/**
	* 校验请求报文
	* **/
	private void checkData(NotQualifiedSaveRequestMessage notQualifiedSaveRequestMessage) {
		Assert.notNull(notQualifiedSaveRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),notQualifiedSaveRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(notQualifiedSaveRequestMessage.getNote_id(), "函件id不能为空");	
		Assert.notEmpty(notQualifiedSaveRequestMessage.getNote_type(), "函件类型不能为空");		
	}
}
