package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.PushMessageService;

/**
 * @author 吴培旭
 * @description 推送函件信息到表t_task_push_note_inf接口
 * @time 创建时间：2017年8月22日
 */
@Service
public class PushMessageServiceImpl implements PushMessageService {
	private final Logger logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);
	@Autowired
	private ProblemDao problemDao;

	@Override
	@Transactional
	public void pushMessageToTable(String noteId, String noteType) {
		long startTime = 0;
		long endTime = 0;
		Date date = new Date();
		String time = DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", date);

		Map<Object, Object> toTableMap = new HashMap<Object, Object>();
		String pushStatusUnsend = Constant.push_status_unsend;
		toTableMap.put("noteType", noteType);
		toTableMap.put("noteId", noteId);
		toTableMap.put("createTime", time);
		toTableMap.put("updateTime", time);
		toTableMap.put("pushStatusUnsend", pushStatusUnsend);
		// 查看t_task_push_note_info是否有重复数据表中状态为未发送和发送失败
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查看t_task_push_note_info是否有重复数据表中状态为未发送和发送失败");
		Map<String, String> chMap = new HashMap<>();
		chMap.put("noteId", noteId);
		chMap.put("noteType", noteType);
		chMap.put("pushStatusFailed", Constant.push_status_failed);
		chMap.put("pushStatusUnsend", pushStatusUnsend);
		startTime = System.currentTimeMillis();
		String back = problemDao.checkNoteMessage(chMap);		
		endTime = System.currentTimeMillis();logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				"查看t_task_push_note_info是否有重复数据表中状态为未发送和发送失败", "程序运行时间： " + (endTime - startTime) + "ms");
		if (Integer.valueOf(back)==0) {
			// 推送函件信息，调用此接口，在info中间表里存储数据，暂时写在problemDao里
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"推送函件信息到表接口,在t_task_push_note_inf中间表里存储数据");
			startTime = System.currentTimeMillis();
			problemDao.pushProblemMessageToTable(toTableMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					"推送函件信息到表t_task_push_note_inf接口,在info中间表里存储数据", "程序运行时间： " + (endTime - startTime) + "ms");
		}

	}

}
