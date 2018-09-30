package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.SurvivalNoteTaskInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalNoteTaskInfoResponseMessage;
/**
 * @author wangt
 * @description 契调任务初始化service
 * @date 2017-08-07
 */
public interface SurvivalNoteTaskInfoService {
	/**
	 * 契调任务初始化
	 * @param SurvivalNoteTaskInfoRequestMessage
	 * @return SurvivalNoteTaskInfoResponseMessage
	 */
	public SurvivalNoteTaskInfoResponseMessage survivalNoteTaskInfo(SurvivalNoteTaskInfoRequestMessage survivalNoteTaskInfoRequestMessage);
}
