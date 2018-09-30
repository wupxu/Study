package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.domain.entity.TNoteTrace;
import com.hualife.wxhb.integration.dao.TraceDao;
import com.hualife.wxhb.service.NoteTraceService;
/**
* @author zhangweiwei
* @description 保存函件轨迹impl
* @date 2017-08-04
*/
@Service
public class NoteTraceServiceImpl implements NoteTraceService{
	
	private final Logger logger = LoggerFactory.getLogger(NoteStatusInfoServiceImpl.class);
	
	@Autowired
	private TraceDao traceDao;

	@Override
	public void saveNoteTrace(String noteId, String everyNoteId, String noteType, String trackDesc){
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件主表id"+noteId+" 函件id"+everyNoteId+" 函件类型"+noteType, "开始检查报文");
		Assert.notEmpty(noteId, "函件主表id不能为空");
		Assert.notEmpty(everyNoteId, "每个函件的函件id不能为空");
		Assert.notEmpty(noteType, "函件类型不能为空");
		Assert.notEmpty(trackDesc, "轨迹描述不能为空");
	
		long startTime =0;
		long endTime =0;
		//获取函件轨迹sq
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix()," 函件主表id"+noteId+""+" 函件id"+everyNoteId+" 函件类型"+noteType, "获取函件轨迹sq");

		HashMap<String,Object> traceMap=new HashMap<>();
		traceMap.put("noteId", noteId);
		traceMap.put("everyNoteId", everyNoteId);
		traceMap.put("noteType", noteType);
		startTime=System.currentTimeMillis();
		String trackSeq=traceDao.getTrackSeqByMap(traceMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件主表id"+noteId+" 函件id"+everyNoteId+"函件类型"+noteType,"获取函件轨迹sq"+(endTime-startTime)+"ms");
		if(StringUtils.isNotBlank(trackSeq)){
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件主表id"+noteId+" 函件id"+everyNoteId+" 函件类型"+noteType, "获取函件轨迹sq");
			TNoteTrace tNoteTrace=new TNoteTrace();
			tNoteTrace.setNoteId(noteId);
			tNoteTrace.setEveryNoteId(everyNoteId);
			tNoteTrace.setNoteType(noteType);
			tNoteTrace.setTrackDesc(trackDesc);
			tNoteTrace.setTrackSeq(String.valueOf(Integer.valueOf(trackSeq)+1));
			tNoteTrace.setCreatedDate(new Date());
			startTime=System.currentTimeMillis();
			traceDao.saveNoteTrace(tNoteTrace);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件主表id"+noteId+"函件id"+everyNoteId+"函件类型"+noteType,"插入一条轨迹信息耗时"+(endTime-startTime)+"ms");
		}
		
	}
}
