package com.hualife.wxhb.service;
/**
 * @author zhangweiwei
 * @description 保存函件轨迹service
 * @date 2017-08-04
 */
public interface NoteTraceService {
	/**
	 * 保存函件状态轨迹
	 */
	public void saveNoteTrace(String noteId,String everyNoteId,String noteType,String trackDesc);
}
