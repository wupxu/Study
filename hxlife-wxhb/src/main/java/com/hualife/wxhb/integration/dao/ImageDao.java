package com.hualife.wxhb.integration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.domain.dto.ImageTable;
import com.hualife.wxhb.domain.dto.ImageTaskTable;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.domain.entity.TNoteUndwrtNote;
import com.hualife.wxhb.domain.entity.TTaskPushMerge;
@Repository

public interface  ImageDao {
	/**
	 * @param map
	 * @return 保存上载推送信息 --add by wangt
	 */
	public void saveUpLoad(Map<String, Object> map);
	/**
	 * @param map
	 * @return 保存删除推送信息 --add by wangt
	 */
	public void saveDelete(Map<String, Object> map);
	/**
	 * @param map
	 * @return 查询上载推送信息 --add by wangt
	 */
	public List<ImageTaskTable> getUpLoad(Map<String, Object> map);
	/**
	 * @param map
	 * @return 查询条形码 --add by wangt
	 */
	public String getBarCode(Map<String, Object> map);
	/**
	 * @param map
	 * @return 查询影像信息 --add by wangt
	 */
	public List<ImageTable> getImage(Map<String, Object> map);
	/**
	 * @param map
	 * @return 查询删除推送信息 --add by wangt
	 */
	public List<ImageTaskTable> getDelete();		
	/**
	 * @param map
	 * @return 更改已上载状态 --add by wangt
	 */
	public void updateUpLoad(Map<String, Object> map);
	/**
	 * @param map
	 * @return 更改已删除状态 --add by wangt
	 */
	public void updateDelete(Map<String, Object> map);
	/**
	 * @param map
	 * @return 更改主表推送状态 --add by wangt
	 */
	public void updateImageNote(Map<String, Object> map);
	/**
	 * 将影像信息保存到影像表   add by zhangweiwei
	 */
	public void saveImageInfo(TNoteImage tNoteImage);
	/**
	 * 将影像信息删除  add by zhangweiwei
	 */
	public void updateImageInfoById(String  imageId);
	/**
	 * 将影像信息删除  add by zhangweiwei
	 */
	public void batchUpdateImageInfo(List<TNoteImage>tNoteImages);
	/**
	 *  查出客户层核保任务序号
	 */
	public TNoteClientNote getClentNoteSeqByMap(Map<String, Object> noteSeqMap);
	/**
	 *  查出客户层核保任务序号
	 */
	public TNoteProblemNote getProblemNoteSeqyMap(Map<String, Object> noteSeqMap);
	/**
	 *  查出客户层核保任务序号
	 */
	public TNoteUndwrtNote getUndwrtNoteSeqByMap(Map<String, Object> noteSeqMap);
	/**
	 * 获取影像地址   add  by zhangweiwei
	 */
	public List<TNoteImage> getImageInfo(Map<String,Object> map);
	/**
	 * 获取影像地址   add  by zhangweiwei
	 */
	public TNoteImage getImageInfoById(String imageId);
	/**
	 * 存储影像地址   add  by YANGPEIXIN
	 */
	public void updateUrl(Map<String, Object> map);
	
	/**
	 * 更改推送表状态  add  by YANGPEIXIN
	 */
	public void updatePushImage(Map<String, Object> map);
	/**
	 * 获取表数据  add  by YANGPEIXIN
	 */
	public List<TTaskPushMerge> getPushMerge(String status);
	/**
	 * 获取影像表数据  add  by YANGPEIXIN
	 * 
	 */
	public TNoteImage getTNoteImage(Map<String, Object> map);
	/**
	 * 查询主表信息  add  by YANGPEIXIN
	 * 
	 */
	public TNoteMain getMainInfoById(String id);
	/**
	 * 查询CLINET表信息  add  by YANGPEIXIN
	 * 
	 */
	public TNoteClientNote getClientInfo(String id);
	/**
	 * 插入推送表  add  by YANGPEIXIN
	 * 
	 */
	public void addTtaskMerge(Map<String, Object> map);
	/**
	 * 修改状态  add  by YANGPEIXIN
	 * 
	 */
	public void UpdateTtaskMerge(Map<String, Object> map);
	/**
	 *  删除影像
	 * @param tNoteImages
	 */
	public void deleteImageInfo(Map<String,Object> map);
	/**
	 *  删除影像
	 * @param tNoteImages
	 */
	public void deleteImageInfoById(String imageId);
	/**
	 * 查询待推送影像表中的数据
	 * @param map
	 */
	public Integer getTaskImageCount(Map<String, Object> map);
	
}
