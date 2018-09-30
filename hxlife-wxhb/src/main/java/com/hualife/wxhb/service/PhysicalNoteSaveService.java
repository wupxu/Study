package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.request.PhysicalNoteSaveRequestMessage;
/**
 * @author zhangweiwei
 * @description 体检函提交service
 * @date 2017-08-05
 */
public interface PhysicalNoteSaveService {
	/**
	 * * 体检函提交接口
	 */
	public  void updatePhysicalNote(PhysicalNoteSaveRequestMessage physicalNoteSaveRequestMessage)  ;
}
