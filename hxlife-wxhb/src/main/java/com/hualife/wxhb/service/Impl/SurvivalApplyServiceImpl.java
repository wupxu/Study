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
import com.hualife.wxhb.api.rest.message.request.SurvivalApplyRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.SurvivalApplyService;
/**
 * @description : 契调任务申请：业务员对任务进行申请serviceImpl
 * @author : linyongtao
 * @date : 2017-08-04 
 */
@Service
public class SurvivalApplyServiceImpl implements SurvivalApplyService{
	private final Logger logger = LoggerFactory.getLogger(SurvivalApplyServiceImpl.class);
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private NoteTraceService noteTraceService;
	long startTime = 0;
	long endTime =0;
	/**
	 * @description : 契调任务申请：业务员对任务进行申请
	 * @author : linyongtao
	 * @date : 2017-08-04 
	 * **/
	@Override
	public void survivalStateUpdate(SurvivalApplyRequestMessage survivalApplyRequestMessage) {
		checkData(survivalApplyRequestMessage);

		//获取请求参数
		String	survivalNoteId = survivalApplyRequestMessage.getSurvival_note_id();		
		String survivalNo = survivalApplyRequestMessage.getSurvival_no();
		String survivalType =survivalApplyRequestMessage.getSurvival_type();
		String noteId =survivalDao.getNoteIdForSurvival(survivalNoteId);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "获取请求参数");
		
		//判断前端传入的契调函是否存在
		Long number =clientDao.selectExitByClientNoteId(survivalNoteId);
		if(number ==0){
			throw new BusinessException("契调任务申请/放弃接口,前端传入的参数契调函id--survivalNoteId:"+survivalNoteId+"错误!");
		}
		//契调任务申请
		if(survivalType.equals(Constant.survival_note_APPLY)){
			survivalApply(survivalNoteId,survivalNo);
			noteTraceService.saveNoteTrace(noteId, survivalNoteId, Constant.note_from_core_type_SURVIVAL, "契调人员领取契调任务");
		}else if(survivalType.equals(Constant.survival_note_GIVEUP)){//契调任务放弃
			survivalGiveUp(survivalNoteId);
			noteTraceService.saveNoteTrace(noteId, survivalNoteId, Constant.note_from_core_type_SURVIVAL, "契调人员放弃契调任务");
		}else{
			throw new BusinessException("契调任务申请/放弃接口,前端传入的参数契调任务类型--survivalType:"+survivalType+"错误!");
		}		
	}	
	/**
	 * 契调任务申请
	 * **/
	@Transactional
	public void survivalApply(String survivalNoteId,String survivalNo){
		//进行契调任务申请
		Map<String , Object> survivalMap = new HashMap<>();
		survivalMap.put("survivalNoteId", survivalNoteId);
		survivalMap.put("survivalNo", survivalNo);
		survivalMap.put("noteStatus",Constant.survival_note_status_RECEIVEING);
		survivalMap.put("noteStatusDesc",Constant.survival_note_status_RECEIVEING_DESC);
		startTime =System.currentTimeMillis();
		survivalDao.survivalStateUpdate(survivalMap);
		endTime =System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId+"--契调人员工号:"+survivalNo, "契调员进行任务申请--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "契调员进行任务申请成功");
	}	
	/**
	 * 契调任务放弃
	 * **/
	@Transactional
	public void survivalGiveUp(String survivalNoteId){
		//进行契调任务放弃
		Map<String , Object> survivalMap = new HashMap<>();
		survivalMap.put("survivalNoteId", survivalNoteId);
		survivalMap.put("survivalNo", Constant.survival_note_NULL);
		survivalMap.put("noteStatus",Constant.survival_note_status_UNRECEIVE);
		survivalMap.put("noteStatusDesc",Constant.survival_note_status_UNRECEIVE_DESC);
		startTime =System.currentTimeMillis();
		survivalDao.survivalStateGiveUp(survivalMap);
		endTime =System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "契调员放弃契调任务任务--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "契调员放弃契调任务任务成功");
	}
	/**
	* 校验请求报文
	* **/
	private void checkData(SurvivalApplyRequestMessage survivalApplyRequestMessage) {
		Assert.notNull(survivalApplyRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalApplyRequestMessage.getSurvival_note_id(), "开始检查请求报文");
		Assert.notEmpty(survivalApplyRequestMessage.getSurvival_note_id(), "契调函id不能为空");	
		Assert.notEmpty(survivalApplyRequestMessage.getSurvival_no(), "当前契调员编号不能为空");		
	}
}