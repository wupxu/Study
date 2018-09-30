package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreRequestMessage;

/**
 * @author "张龙"
 * @Description 核心推送数据的处理
 * @time 2017年8月18日 下午3:03:43
 */
@Service
public interface NoteFromCoreService {
	/**
	 * 对数据进行处理
	 * @param NoteFromCoreRequestMessage
	 * @return CommonHeadResponseMessage
	 * @throws Exception 
	 */
	void dealDate(NoteFromCoreRequestMessage ReqXmlMessage) throws Exception;
}
