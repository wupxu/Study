package com.hualife.wxhb.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.wxhb.service.NoteInformAgentService;
import com.hualife.wxhb.service.Impl.NoteStatusInfoServiceImpl;

/**
 * @author zhangweiwei
 * @deprecation 给代理人推送函件消息--批处理类
 * @date  2017-08-16
 */
public class NoteInformAgentTask {
	
	private final Logger logger = LoggerFactory.getLogger(NoteStatusInfoServiceImpl.class);

	@Autowired
	private NoteInformAgentService noteInformAgentService;
    public void run(){
    	logger.info("任务线程开始启动");
    	this.noteInformAgentService.saveNoteInformAgent();
    	logger.info("任务线程结束");
    } 
}
