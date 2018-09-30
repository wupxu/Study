package com.hualife.wxhb.service;

import com.hualife.wxhb.api.soap.message.request.noteFinishDeal.NoteFinishDealRequestMessage;

/**
 * @author zhangweiwei
 * @description 核心回销消息Service
 * @date 2017-08-04
 */
public interface NoteFinishDealService {
	/**
	 * 核心调用平台回销消息
	 * @param ReqXml
	 * @return
	 */
	public void noteFinishDeal(NoteFinishDealRequestMessage noteFinishDealRequestMessage);
}
