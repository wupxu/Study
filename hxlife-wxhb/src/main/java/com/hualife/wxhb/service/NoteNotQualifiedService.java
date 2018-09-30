package com.hualife.wxhb.service;


import com.hualife.wxhb.api.soap.message.request.noteNotQualified.NoteNotQualifiedRequestMessage;
/**
 * @author yangpeixin
 * @deprecation 次品单service
 * @date 2017-08-24
 */
public interface NoteNotQualifiedService {
	/**
	 * 存储次品单信息
	 * **/
	public void saveNoteNotQualified(NoteNotQualifiedRequestMessage noteNotQualifiedRequestMessage );
}
