package com.hualife.wxhb.service.Impl;

import java.util.Date;
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
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.rest.message.request.UndwrtClientSaveRequestMessage;
import com.hualife.wxhb.api.rest.message.response.UndwrtClientSaveResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.PushMessageService;
import com.hualife.wxhb.service.TaskImageService;
import com.hualife.wxhb.service.UndwrtClientSaveService;
/**
 * @author wangt
 * @description 核保涵提交impl
 * @date 2017-08-05
 */
@Service
public class UndwrtClientSaveServiceImpl implements UndwrtClientSaveService {

	private final Logger logger = LoggerFactory.getLogger(UndwrtClientSaveServiceImpl.class);
	
	@Autowired
	private UndwrtDao undwrtDao; 
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private TaskImageService taskImageService;
	@Autowired
	private PushMessageService pushMessageService;
	/**
	 * 组织核保涵提交报文
	 */
	@Override
	@Transactional
	public UndwrtClientSaveResponseMessage undwrtClientSave(UndwrtClientSaveRequestMessage undwrtClientSaveRequestMessage) {
		
		checkData(undwrtClientSaveRequestMessage);
		
		UndwrtClientSaveResponseMessage undwrtClientSaveResponseMessage = new UndwrtClientSaveResponseMessage();
		Map<Object, Object> map = new HashMap<>();	
		long startTime = 0;
		long endTime   = 0;
		
		//客户回答结果
		String result = undwrtClientSaveRequestMessage.getClient_answer_result();	
		//核保涵ID
		String noteUndwrtId = undwrtClientSaveRequestMessage.getNote_undwrt_id();			
			
		map.put("note_undwrt_id",noteUndwrtId);
		map.put("client_answer_result",result);
		
		//获取note_id 
		startTime = System.currentTimeMillis();
		String noteId = undwrtDao.getNoteIDByNoteUndwrtId(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"获取noteid耗时"+(endTime-startTime));
		
		if(noteId == null){
			throw new BusinessException("函件ID查询为空");
		}
		map.put("note_id",noteId);
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId, "开始执行核保涵状态更改");
		
		String dateStr = DateFormatUtil.formatDate(Constant.time_DATE, new Date());
		String noteMainStatus = Constant.note_status_DOWN;
		String noteUndwrtStatus = Constant.undwrt_note_status_DOWN;
		String noteUndwrtStatusDesc = Constant.undwrt_note_status_DOWN_DESC;
		
		map.put("noteMainStatus",noteMainStatus);
		map.put("noteUndwrtStatus",noteUndwrtStatus);
		map.put("noteUndwrtStatusDesc",noteUndwrtStatusDesc);
		map.put("updated_date",dateStr);
		
		//保存客户回答结果
		startTime = System.currentTimeMillis();
		undwrtDao.updateUndwrtStatus(map);	
		endTime   = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"保存客户回答结果耗时"+(endTime-startTime));
		noteTraceService.saveNoteTrace(noteId, noteUndwrtId, Constant.note_from_core_type_SURVIVAL, "核保函已完成");
				
		//根据客户回答结果判定
		switch(result)
		{
		case Constant.client_answer_result_Yes:	
			noteTraceService.saveNoteTrace(noteId, noteUndwrtId, Constant.note_from_core_type_SURVIVAL, "已同意核保结论");
			//返回银行卡号
			startTime = System.currentTimeMillis();
			String accountNo = undwrtDao.getAccount_no(map);
			endTime   = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"返回银行卡号耗时"+(endTime-startTime));
			
			if(accountNo == null){
				throw new BusinessException("银行卡号查询为空");
			}
			
			undwrtClientSaveResponseMessage.setAccount_no(accountNo);
			break;
			
		case Constant.client_answer_result_No:
			noteTraceService.saveNoteTrace(noteId, noteUndwrtId, Constant.note_from_core_type_SURVIVAL, "未同意核保结论");
			break;
		}		
		
		//影像推送
		taskImageService.saveImageUpLoad(noteId, Constant.note_from_core_type_UNDWRT,noteUndwrtId);		
		//往中间表存数据
		pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_UNDWRT);
		
		return undwrtClientSaveResponseMessage;
	}
	/**
	 * 校验请求报文
	 */
	private void checkData(UndwrtClientSaveRequestMessage undwrtClientSaveRequestMessage) {	
		Assert.notNull(undwrtClientSaveRequestMessage, "报文不能为空");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtClientSaveRequestMessage.getNote_undwrt_id(), "开始核保涵提交报文校验");
		Assert.notEmpty(undwrtClientSaveRequestMessage.getNote_undwrt_id(), "函件ID不能为空");
		Assert.notEmpty(undwrtClientSaveRequestMessage.getClient_answer_result(), "客户回答结果不能为空");		
	}
}
