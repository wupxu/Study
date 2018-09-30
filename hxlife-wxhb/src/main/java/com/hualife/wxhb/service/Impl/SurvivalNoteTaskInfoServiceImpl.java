package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteAllTask;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteMyTask;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteTaskList;
import com.hualife.wxhb.api.rest.message.request.SurvivalNoteTaskInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalNoteTaskInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteSurvival;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.SurvivalNoteTaskInfoService;
/**
 * @author wangt
 * @description 契调任务初始化impl
 * @date 2017-08-07
 */
@Service
public class SurvivalNoteTaskInfoServiceImpl implements SurvivalNoteTaskInfoService {

	private final Logger logger = LoggerFactory.getLogger(SurvivalNoteTaskInfoServiceImpl.class);
	
	@Autowired
	private SurvivalDao survivalDao; 
	/**
	 * 组织契调任务初始化报文
	 */
	@Override
	public SurvivalNoteTaskInfoResponseMessage survivalNoteTaskInfo(SurvivalNoteTaskInfoRequestMessage survivalNoteTaskInfoRequestMessage ) {
		
		checkData(survivalNoteTaskInfoRequestMessage);
		
		SurvivalNoteTaskInfoResponseMessage survivalNoteTaskInfoResponseMessage = new SurvivalNoteTaskInfoResponseMessage();		
		Map<Object, Object> map = new HashMap<>();	
		long startTime = 0;
		long endTime   = 0;
		
		//契调人工号
		String survivalNo = survivalNoteTaskInfoRequestMessage.getSurvival_no();		
		map.put("survival_no",survivalNo);
		
		map.put("note_type", Constant.client_type_SURVIVAL);
		map.put("down", Constant.note_status_DOWN);
		map.put("finished", Constant.note_status_FINISHED);
		//根据契调人编号查询契调人信息
		TNoteSurvival tNoteSurvival = new TNoteSurvival();
		
		startTime = System.currentTimeMillis();
		tNoteSurvival = survivalDao.getSurvival(map);
		if(tNoteSurvival == null){
			throw new BusinessException("契调人id查询为空");
		}
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNo,"查询契调人信息耗时"+(endTime-startTime));
		
		//契调人所属机构
		if(tNoteSurvival.getSurvivalBranchCode() == null){
			throw new BusinessException("契调人所属机构查询为空");
		}		
		map.put("survival_branch_code",tNoteSurvival.getSurvivalBranchCode());
		
		//契调人所属机构名称
		String survivalBranchName = tNoteSurvival.getSurvivalBranchName();
		survivalNoteTaskInfoResponseMessage.setSurvival_branch_name(survivalBranchName);
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNo, "开始查询所有任务");
		//查询所有任务
		List<SurvivalNoteAllTask> survivalNoteAllTaskList = new ArrayList<SurvivalNoteAllTask>();
		startTime = System.currentTimeMillis();		
		survivalNoteAllTaskList = survivalDao.getAllTasks(map);		
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNo,"查询所有任务耗时"+(endTime-startTime));
		if(survivalNoteAllTaskList != null){	
			startTime = System.currentTimeMillis();
			for(SurvivalNoteAllTask survivalNoteAllTask : survivalNoteAllTaskList){
				//查询NoteID
				String noteId = survivalDao.getNoteIDBySurvivalNoteId(survivalNoteAllTask.getSurvival_note_id());			
				if(noteId == null){
					throw new BusinessException("函件ID查询为空");
				}
				//查询任务保单信息
				List<SurvivalNoteTaskList> allSurvivalNoteTaskList = new ArrayList<SurvivalNoteTaskList>();
				allSurvivalNoteTaskList = survivalDao.getTaskLists(noteId);			
				survivalNoteAllTask.setAllTaskLists(allSurvivalNoteTaskList);
			}
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNo,"查询任务保单信息耗时"+(endTime-startTime));
		}
		
		
		
		survivalNoteTaskInfoResponseMessage.setAllTasks(survivalNoteAllTaskList);
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNo, "开始查询我的任务");
		//查询我的任务
		List<SurvivalNoteMyTask> survivalNoteMyTaskList = new ArrayList<SurvivalNoteMyTask>();
		
		startTime = System.currentTimeMillis();
		survivalNoteMyTaskList = survivalDao.getMyTasks(map);			
		endTime = System.currentTimeMillis();
		logger.info("查询我的任务耗时"+(endTime-startTime));
		
		if(survivalNoteMyTaskList != null){
			startTime = System.currentTimeMillis();
			for(SurvivalNoteMyTask survivalNoteMyTask : survivalNoteMyTaskList){
				//查询NoteID
				String noteId = survivalDao.getNoteIDBySurvivalNoteId(survivalNoteMyTask.getSurvival_note_id());
				if(noteId == null){
					throw new BusinessException("函件ID查询为空");
				}
				survivalNoteMyTask.setSurvival_note_status_desc(Constant.survival_note_status_desc);
				//查询任务保单信息
				List<SurvivalNoteTaskList> mySurvivalNoteTaskList =  new ArrayList<SurvivalNoteTaskList>();
				mySurvivalNoteTaskList = survivalDao.getTaskLists(noteId);
				survivalNoteMyTask.setMyTaskLists(mySurvivalNoteTaskList);
			}
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNo,"查询任务保单信息耗时"+(endTime-startTime));
		}
		
		survivalNoteTaskInfoResponseMessage.setMyTasks(survivalNoteMyTaskList);			
		
		return survivalNoteTaskInfoResponseMessage;
	
	}
	/**
	 * 校验请求报文
	 */
	@Transactional
	private void checkData(SurvivalNoteTaskInfoRequestMessage survivalNoteTaskInfoRequestMessage) {
		Assert.notNull(survivalNoteTaskInfoRequestMessage, "报文不能为空");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNoteTaskInfoRequestMessage.getSurvival_no(), "开始契调函初始化校验");
		Assert.notEmpty(survivalNoteTaskInfoRequestMessage.getSurvival_no(), "契调人工号不能为空");		
	}
}
