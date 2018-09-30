package com.hualife.wxhb.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.service.NotePrintPushService;

/**
 * @author 吴培旭
 * @description 函件打印
 * @time 创建时间：2017年8月17日
 */
public class NotePrintPushTask {
	// @Autowired
	// private ChooseTypeClient chooseTypeClient;
	@Autowired
	private NotePrintPushService notePrintPushService;
	private final Logger logger = LoggerFactory.getLogger(NotePrintPushService.class);

	public void run(){
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件打印","批处理开始" );
		notePrintPushService.dealNotePrintPush();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件打印","批处理结束" );
	}

}
