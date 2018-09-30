package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.UndwrtClientSaveRequestMessage;
import com.hualife.wxhb.api.rest.message.response.UndwrtClientSaveResponseMessage;
/**
 * @author wangt
 * @description 客户提交核保涵service
 * @date 2017-08-08
 */
public interface UndwrtClientSaveService {
	/**
	 * 客户提交核保涵
	 * @param HealthNoteInitRequestMessage
	 * @return UndwrtClientSaveResponseMessage
	 */
	public UndwrtClientSaveResponseMessage undwrtClientSave(UndwrtClientSaveRequestMessage undwrtClientSaveRequestMessage);
}
