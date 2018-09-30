package com.hualife.wxhb.integration.dao;

import java.util.List;
import java.util.Map;

import com.hualife.wxhb.domain.entity.TNoteClientRelationApply;
import com.hualife.wxhb.domain.entity.TNoteMain;

/**
 * @author "张龙"
 * @Description 存放main表的数据
 * @time 2017年8月22日 上午10:04:01
 */
public interface NoteMainDao {
	/**
	 * t_note_main 表信息插入
	 * @param TNoteMain
	 *  add by zhanglong
	 */
	public void insertNoteMain(TNoteMain tNoteMain);
	
	/**
	 * t_note_client_relation_apply 表提交
	 * @param TNoteClientRelationApply
	 *  add by zhanglong
	 */
	public void insertRelationApply(List<TNoteClientRelationApply> insertTNoteClientRelationApplyList);
	
	/**
	 * 批量提交 t_note_main 表信息数据
	 * @param insertTNoteMainList
	 * add by zhanglong
	 */
	public void insertNoteMainList(List<TNoteMain> insertTNoteMainList);
	
	/**
	 * 通过taskcode查询是否已经存在客户层函件
	 * @param TNoteMain
	 * @return
	 */
	public TNoteMain selectExistsNoteID(TNoteMain tNoteMain);
	
	/**
	 * 通过noteid更新noteStatus字段
	 * @param tNoteMain
	 */
	public void updateMainNoteStatus(TNoteMain tNoteMain);
	
	/**
	 * 重新下发时 通过note_id更新表状态
	 * @param map
	 */
	public void updateMainSecondStatus(Map<String,Object> map);
}
