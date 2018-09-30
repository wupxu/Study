package com.hualife.wxhb.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.service.PushMessageRequestService;
/**
 * @author 吴培旭
 * @description 批处理推送信息 调用前其他通过pushMessageService接口已经将数据存储到info中间表里
 * @time 创建时间：2017年8月17日
 */
public class PushMessageTask {
	@Autowired
	private PushMessageRequestService pushMessageRequestService;
	// log日志
	private final Logger logger = LoggerFactory.getLogger(PushMessageTask.class);

	public void run() throws Exception {
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件系统推送信息到核心系统","批处理开始" );
		pushMessageRequestService.PushMessageBodyAndHead();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件系统推送信息到核心系统","批处理结束" );
	}
}
