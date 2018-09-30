package com.hualife.wxhb.service;


import com.hualife.wxhb.api.soap.message.request.NoteSecondPush.NoteSecondPushRequestMessage;

/**
 * @author yangpeixin
 * @deprecation 重新下发service
 * @date 2017-08-24
 */
public interface NoteSecondPushService {
	
	/**
	 *存储重新下发
	 * **/
	public void saveNoteSecondPush(NoteSecondPushRequestMessage noteSecondPushRequestMessage);}
