package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.rest.message.request.UndwrtEndDealRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteUndwrtNote;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.UndwrtEndDealService;

/**
 * @author zhangweiwei
 * @deprecation 代理人删除延期、拒保的核保函impl
 * @date 2017-08-04
 * **/
@Service
public class UndwrtEndDealServiceImpl implements UndwrtEndDealService {
	
	private final Logger logger = LoggerFactory.getLogger(UndwrtEndDealServiceImpl.class);
	
	@Autowired
	private UndwrtDao undwrtDao;
	@Autowired
	private NoteTraceService noteTraceService;
	/**
	 * 代理人删除延期、拒保的核保函
	 */
	@Override
	@Transactional
	public void undwrtEndDeal(UndwrtEndDealRequestMessage undwrtEndDealRequestMessage) {
		
		//检查请求报文
		checkData(undwrtEndDealRequestMessage);
		long startTime = 0;
		long endTime   = 0;
		String noteUndwrtId=undwrtEndDealRequestMessage.getNote_undwrt_id();	
		//获取核保函的信息
		startTime = System.currentTimeMillis();
		TNoteUndwrtNote tNoteUndwrtNote=undwrtDao.countUndwrtNoteByNoteId(noteUndwrtId);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保函id"+noteUndwrtId,"查询核保函信息耗时"+(endTime-startTime)+"ms");

		if(tNoteUndwrtNote!=null){
			//更新核保函的信息
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保函id"+noteUndwrtId, "更新核保函主表状态");
			HashMap<String,Object> noteUndwrtMap=new HashMap<>();
			noteUndwrtMap.put("note_status",Constant.undwrt_note_status_WRITEOFF);
			noteUndwrtMap.put("note_status_desc", Constant.undwrt_note_status_WRITEOFF_DESC);
			noteUndwrtMap.put("note_undwrt_id", noteUndwrtId);
			startTime = System.currentTimeMillis();
			undwrtDao.updateUndwrtEndDeal(noteUndwrtMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保函id"+noteUndwrtId,"更新核保函信息耗时"+(endTime-startTime)+"ms");
			//更新函件主表状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保函id"+noteUndwrtId, "更新函件主表状态");
			HashMap<String,Object> noteMainMap=new HashMap<>();
			noteMainMap.put("note_status", Constant.note_status_FINISHED);
			noteMainMap.put("note_undwrt_id", noteUndwrtId);
			noteMainMap.put("updated_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));

			startTime = System.currentTimeMillis();
			undwrtDao.updateMainStatus(noteMainMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"核保函id"+noteUndwrtId,"更新函件主表信息耗时"+(endTime-startTime)+"ms");	
			noteTraceService.saveNoteTrace(tNoteUndwrtNote.getNoteId(), noteUndwrtId, Constant.note_from_core_type_UNDWRT, "核保函已回销");
		}else{
			throw new BusinessException("函件id"+noteUndwrtId+"的核保函不存在!");
		}	
	}
	
	/**
	 * 校验请求报文
	 * 
	 */
	private void checkData(UndwrtEndDealRequestMessage undwrtEndDealRequestMessage) {
		
		Assert.notNull(undwrtEndDealRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtEndDealRequestMessage.getNote_undwrt_id(), "开始检查请求报文");
		Assert.notEmpty(undwrtEndDealRequestMessage.getNote_undwrt_id(), "核保函id不能为空");
	}
}
