package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.NoteStatusInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteStatusInfoResponseMessage;

/**
 * @author zhangweiwei
 * @description 查询代理人函件状态信息service
 * @date 2017-08-04
 */
public interface NoteStatusInfoService {
	/**
	 * 查询代理人函件状态
	 * **/
	public  NoteStatusInfoResponseMessage getNoteStatus(NoteStatusInfoRequestMessage noteStatusInfoRequestMessage);

}
