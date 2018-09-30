package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.HashMap;

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
import com.hualife.wxhb.api.rest.message.request.ProblemSaveRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.ProblemSaveService;
import com.hualife.wxhb.service.PushMessageService;
import com.hualife.wxhb.service.TaskImageService;

/**
 * @author 张卫卫
 * @description 问题件提交impl
 * @date 2017-08-15
 * **/
@Service
public class ProblemSaveServiceImpl implements ProblemSaveService{
	
	private final Logger logger = LoggerFactory.getLogger(ProblemSaveServiceImpl.class);
	
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private TaskImageService taskImageService; 
	
	/**
	 * 保存问题件信息
	 * **/
	@Override
	@Transactional
	public void saveProblemSave(ProblemSaveRequestMessage problemSaveRequestMessage) {
		//检查请求报文
		checkData(problemSaveRequestMessage);
		String problemNoteId=problemSaveRequestMessage.getProblem_note_id();
		//判断问题件的信息是否存在
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+problemNoteId, "判断问题件的信息是否存在");

		//获取时间
		long startTime =0;
		long endTime =0;
	
		startTime=System.currentTimeMillis();
		String noteId=problemDao.countProblemNoteByNoteId(problemNoteId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+problemNoteId, "根据函件id查询问题件--耗时:"+(endTime-startTime)+"ms");
		if(StringUtils.isNotBlank(noteId)){
			//更新问题件主表状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemSaveRequestMessage.getProblem_note_id(), "更新问题件主表状态");
			HashMap<String,Object> problemNoteMap = new HashMap<>();	
			problemNoteMap.put("problem_note_id", problemNoteId);
			problemNoteMap.put("note_status",Constant.problem_note_status_DOWN);
			problemNoteMap.put("note_status_desc",Constant.problem_note_status_DOWN_DESC);
			startTime=System.currentTimeMillis();
			problemDao.updateProblemNote(problemNoteMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+problemNoteId, "更新问题件主表状态--耗时:"+(endTime-startTime)+"ms");

			//更新函件主表状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "更新函件主表状态");
			HashMap<String,Object> noteMainMap=new HashMap<>();
			noteMainMap.put("note_status",Constant.note_status_DOWN);
			noteMainMap.put("problem_note_id", problemNoteId);
			noteMainMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));

			startTime=System.currentTimeMillis();
			problemDao.updateMainNote(noteMainMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+problemNoteId, "更新问题件主表状态--耗时:"+(endTime-startTime)+"ms");
			
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "调用接口，向函件轨迹表插入数据");
			noteTraceService.saveNoteTrace(noteId, problemNoteId, Constant.note_from_core_type_PROBLEM,"问题件已完成");
			
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "调用接口，向核心推送函件信息表存入一条数据");
			pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PROBLEM);
			
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "调用接口，向影像系统信息表存入一条数据");
			taskImageService.saveImageUpLoad(noteId,Constant.note_from_core_type_PROBLEM,problemNoteId);
		}else{
			throw new BusinessException("函件id为"+problemNoteId+"的问题件不存在!");
		}
		
	}
	/**
	 * 检查请求报文
	 * @param problemSaveRequestMessage
	 */
	private void checkData(ProblemSaveRequestMessage problemSaveRequestMessage) {
		Assert.notNull(problemSaveRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemSaveRequestMessage.getProblem_note_id(), "开始检查请求报文");
		Assert.notEmpty(problemSaveRequestMessage.getProblem_note_id(), "问题件id不能为空");
	}
}
