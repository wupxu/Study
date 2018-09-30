package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.NoteInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteInfoResponseMessage;
/**
 * @description:函件初始化
 * @author yangpeixin
 * @date 2017-08-04
 */
public interface NoteInfoService {
	/**
	 * 函件初始化
	 */
	public NoteInfoResponseMessage noteClientInfo(NoteInfoRequestMessage noteInfoRequestMessage);
}
