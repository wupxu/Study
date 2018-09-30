package com.hualife.wxhb.service;

/**
 * @author zhanglong
 * @description 修改客户层函件主表状态
 * @date 2017-08-31
 */
public interface UpdateMainStatusService {
	/**
	 * 更新主表状态
	 * @param client_note_id 客户函表id
	 * @param noteStatus 主表状态代码
	 */
	void updateMainStatusService(String client_note_id,String noteStatus);
}
