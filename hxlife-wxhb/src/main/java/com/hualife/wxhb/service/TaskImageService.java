package com.hualife.wxhb.service;

/**
 * 
 * @deprecation 保存影像推送数据--批处理类Service
 * @author wangt
 * @date  2017-08-20
 *
 */
public interface TaskImageService {
	/**
	 * 保存影像上载信息
	 */
	public void saveImageUpLoad(String noteId,String noteType,String professional_id);
	/**
	 * 保存影像删除信息
	 */
	public void saveImageDelete(String noteId,String noteType,String professional_id);

}
