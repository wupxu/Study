package com.hualife.wxhb.service;

import java.util.Map;

/**
 * 
 * @author zhangweiwei
 *  @deprecation 存储推送代理人函件消息
 * @date  2017-08-16
 *
 */
public interface TaskPushInfoService {
	/**
	 * 核保师下发函件，代理人收到函件消息
	 */
	public void saveUnderWriterSendoutNote(Map<String,Object> map);
	/**
	 * 机构回销函件，选择重新发送函件
	 */
	public void saveReSendNoteToAgent(Map<String,Object> map);
	/**
	 * 机构回销函件，选择下发次品单
	 */	
	public void saveSendFailNote(Map<String,Object> map);
	/**
	 * 客户授权代理人处理函件 
	 */
	public void saveAuthorizeAgentHandleNote(String noteId,String clientNoteId,String noteType);
	/**
	 * 客户选择代理人处理体检通知书
	 */
	public void saveAuthorizeAgentHandleMedicalNotice(String noteId,String clientNoteId);

}
