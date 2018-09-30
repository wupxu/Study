package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.HealthNoteInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.HealthNoteInitResponseMessage;
/**
 * @author wangt
 * @description 健康函初始化service
 * @date 2017-08-08
 */
public interface HealthNoteInfoService {
	/**
	 * 健康函初始化
	 * @param HealthNoteInitRequestMessage
	 * @return HealthNoteInitResponseMessage
	 */
	public	HealthNoteInitResponseMessage healthNoteInfo(HealthNoteInitRequestMessage healthNoteInitResponseMessage);

}
