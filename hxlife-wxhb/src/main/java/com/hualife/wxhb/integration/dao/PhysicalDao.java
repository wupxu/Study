package com.hualife.wxhb.integration.dao;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.PhysicalItem;
import com.hualife.wxhb.api.rest.message.response.PhysicalLetterInitResponseMessage;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.domain.entity.TNotePhysicalExamNote;
import com.hualife.wxhb.domain.entity.TNotePhysicalExamNoteItem;

@Repository
public interface PhysicalDao {
	/**
	 * 
	 * @param map
	 * @return 获取客户层所有函件的状态  add by zhangweiwei
	 */
	public  List<TNoteClientNote> getClientNoteStatusByNoteid(Map<String,Object> map);
	/**
	 * 
	 * @param 体检函id
	 * @return 根据id获取体检函信息   add by zhangweiwei
	 */
	public  TNotePhysicalExamNote getPhysicalNoteByNoteid(String noteId);

	/**
	 * 更新体检函的函件状态   add by zhangweiwei
	 * @param param
	 */

	public void updatePhysicalNote(HashMap<String,Object> param);
	/**
	 * 更新客户层函件的函件状态   add by zhangweiwei
	 * @param param
	 */
	public void updateClientNote(HashMap<String,Object> param);
	/**
	 * 更新函件主表的函件状态   add by zhangweiwei
	 * @param param
	 */
	public void updateMainNote(HashMap<String,Object> param);
	/**
	 * @descrption : 体检函初始化方法
	 * @author : linyongtao
	 * @date : 2017.08.24 
	 * **/
	public  PhysicalLetterInitResponseMessage selectPhysicalLetter(String physicalNoteId);
	
	/**
	 * @descrption : 查询客户体检项目
	 * @author : linyongtao
	 * @date : 2017.08.24 
	 * **/
	public  List<PhysicalItem> selectPhysicalItem(String physicalNoteId);
	
	/**
	 * @descrption : 查询体检函下客户上传的影像信息
	 * @author : linyongtao
	 * @date : 2017.08.24 
	 * **/
	public List<ImageInfo> selectImageInfo(Map<String,Object> map);
	/**
	 * @descrption : 体检函次品单提交--更改客户层表状态次品单处理-->已处理，以及代理人处理状态
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public void updateClientStatus(Map<String,Object> map);
	
    /**
	 * 次品单处理
	 * @param param
	 */
	public void updatNoteNotQualified(Map<String, Object> map);
	/**
	 * 体检函回销状态
	 * @param map   add by zhangweiwei
	 */
	public void updatePhysicalNoteStatus(HashMap<String, Object> map);
	
	/**
	 * 体检函表插入数据
	 * @param tTNotePhysicalExamNote 
	 * add by zhanglong
	 */
	public void insertNotePhysicalExam(TNotePhysicalExamNote tTNotePhysicalExamNote);
	
	/**
	 * 体检项目提交
	 * @param insertTNotePhysicalExamNoteItemList
	 * add by zhanglong
	 */
	public void insertPhysicalItem(List<TNotePhysicalExamNoteItem> insertTNotePhysicalExamNoteItemList);
	 /**
		 * 重新下发体检函
		 * @param param
		 */
	public void updatNotSecondPhysical(Map<String, Object> map);
	/**
	 * 根据核保任务号、核保序号、函件类型获取客户层信息   add  by  zhangweiwei
	 */
	public TNoteClientNote getClientNoteIdByBody(Map<String, Object> map);
	/**
	 * 根据客户层函件id获取函件id    add by zhangweiwei
	 */
	public String getNoteId(String clientNoteId);
	/**
	 * 获取体检对象    add by yangpeixin
	 */
	public TNotePhysicalExamNote getTNotePhysicalExamNote(String id);
	/**
	 * 插入体检对象    add by yangpeixin
	 */
	public void  addTNotePhysicalExamNote(TNotePhysicalExamNote tNotePhysicalExamNote);
	/**
	 * 获取体检对象问卷    add by yangpeixin
	 */
	public List<TNotePhysicalExamNoteItem> getTNotePhysicalExamNoteItem(String id);
	/**
	 * 插入体检对象问卷    add by yangpeixin
	 */
	public TNotePhysicalExamNoteItem addTNotePhysicalExamNoteItem(TNotePhysicalExamNoteItem tNotePhysicalExamNoteItem);
	/**
	 * 查询主表数据    add by yangpeixin
	 */
	public TNoteMain findTNoteMain(Map<String, Object> map);
	/**
	 * 
	 * @param map
	 * @return 获取客户层所有函件的状态  add by zhangweiwei
	 */
	public  List<TNoteClientNote> getClientNoteStatusByTaskCode(Map<String,Object> map);
	/**
	 * 根据体检函id获取客户层信息   add  by  zhangweiwei
	 */
	public TNoteClientNote getClientNoteInfoByNoteId(Map<String, Object> map);
}
