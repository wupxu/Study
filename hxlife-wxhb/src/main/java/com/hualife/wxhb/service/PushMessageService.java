package com.hualife.wxhb.service;
/** 
 * @author 吴培旭 
 * @description 推送函件信息到表t_task_push_note_inf接口
 * @time 创建时间：2017年8月22日   
 */
public interface PushMessageService {
	/** 
	 * @author 吴培旭 
	 * @description 推送函件信息到表t_task_push_note_inf接口
	 * @time 创建时间：2017年8月22日   
	 */
	public void pushMessageToTable(String noteId, String noteType);
}
