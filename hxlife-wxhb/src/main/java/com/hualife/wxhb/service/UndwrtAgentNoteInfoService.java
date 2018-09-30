package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.UndwrtAgentNoteInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.UndwrtAgentNoteInfoResponseMessage;
/**
 * @author wangt
 * @description 核保涵初始化service
 * @date 2017-08-07
 */
public interface UndwrtAgentNoteInfoService {
	/**
	 * 核保涵初始化
	 * @param UndwrtAgentNoteInfoRequestMessage
	 * @return UndwrtAgentNoteInfoResponseMessage
	 */
	public UndwrtAgentNoteInfoResponseMessage undwrtAgentNoteInfo(UndwrtAgentNoteInfoRequestMessage undwrtAgentNoteInfoRequestMessage);
}
