package com.hualife.wxhb.integration.dao;

import java.util.List; 
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.HealthNoteItem;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContents;
import com.hualife.wxhb.api.rest.message.response.HealthNoteInitResponseMessage;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteHealthNote;
import com.hualife.wxhb.domain.entity.TNoteHealthNoteItem;
@Repository
public interface HealthDao {
	/**
	 * @param 
	 * @author : yangpeixin
	 * @date 2017-08-07
	 */
	public String getNoteId(String id);
	/**
	 * @param map
	 * @return 获取函件ID --add by wangt
	 * @date 2017-08-07
	 */
	public String getNoteIDByHealthNoteId(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 客户查看状态
	 * @date 2017-08-07
	 */
	public void update_note_client_status(Map<String,Object> map);
	/**
	 * @param map
	 * @return 代理人状态
	 * @date 2017-08-07
	 */
	public void update_agent(Map<String,Object> map);
	/**
	 * @param map
	 * @return 逻辑处理
	 * @date 2017-08-07
	 */
	public void update_status(Map<String,Object> map);
	/**
	 * @param map
	 * @return 查询函件状态
	 * @date 2017-08-07
	 */
	public String findClinetstatus(Map<String,Object> map);
	/**
	 * @param 
	 * @author : yangpeixin
	 * @return 更新主表
	 */
	public void update_mian(Map<String,Object> map);
	/**
	 * @param map
	 * @return 查询影像信息 --add by wangt
	 */
	public List<ImageInfo> getImages (Map<Object, Object> map);
	/**
	 * @param map
	 * @return 查询健康问题卷信息 --add by wangt
	 */
	public List<HealthNoteItem> getHealthNoteItem (Map<Object, Object> map);
	/**
	 * @param map
	 * @return 查询健康函信息 --add by wangt
	 */
	public HealthNoteInitResponseMessage getHealthNote (Map<Object, Object> map);

	/**
	 *  @author : yangpeixin
	 * 次品单处理
	 */
	public void updatNoteNotQualified(Map<String,Object> map);
	/**
	 * 健康函回销状态处理
	 */
	public void updateHealthNoteStatus(Map<String,Object> map);
	/**
	 * t_note_health_note信息插入
	 * add by zhanglong
	 */
	public void insertNoteHealth(TNoteHealthNote tNoteHealthNote);
	/**
	 * t_note_health_note_item 信息插入
	 * add by zhanglong
	 */
	public void insertNoteHealthItem(List<TNoteHealthNoteItem> insertTNoteHealthNoteItemList);

	/**
	 * 根据note_id和note_type查询健康卷id---note_item_id_
	 * ---linyongtao
	 * **/
	public String  selectHealthNoteId(String noteItemId);

	/**
	 * 保存健康问卷问题内容
	 * linyongtao
	 * **/
	public void healthReportSave(List<ProblemContents> problemContents);
	/**
	 * 健康问卷提交后需要改变t_note_fina_note_item 中的note_item_status--问卷回答状态(Y-已经完成，N-未完成)
	 * linyongtao
	 * **/
	public void updateNoteItemStatus(Map<String , Object>map);
	
	/**
	 *  健康函次品单提交--更改t_note_client_note表
	 *  note_status状态从--次品单处理-->已处理，以及代理人处理状态
	 *  --linyongtao
	 * **/
	public void updateClientStatus(Map<String,Object> map);
	/**
	 *  @author : yangpeixin
	 * 健康函状态改变 
	 */
	public void updatSecondHealth(Map<String,Object> map);	
	/**
	 * @description : 查询健康函问卷下客户提交的影像
	 * @author : linyongtao
	 * @daye : 2017.08.31
	 * **/
	public List<ImageInfo> getHealthReportImageInfo(Map<String , Object>map);	
			
	/**
	 *  @author : yangpeixin
	 * 健康函代理人提交
	 */
	public void healthSubmitAgent(Map<String,Object> map);
	/**
	 * 根据核保任务号、核保序号、函件类型获取客户层id   add  by  zhangweiwei
	 */
	public TNoteClientNote getClientNoteIdByBody(Map<String, Object> map);
	/**
	 *  @author : yangpeixin
	 *  查询TNoteHealthNote
	 */
	public TNoteHealthNote getTNoteHealthNote(String id);
	/**
	 *  @author : yangpeixin
	 *  查询TNoteHealthNoteItem
	 */
	public List<TNoteHealthNoteItem> getTNoteHealthNoteItem(String id);
	/**
	 * 客户是否已完成健康问卷
	 * @author : linyongtao
	 * **/
	public String getIsFinishHealthReport(Map<String, Object> map);
	/**
	 * 查询客户上传的健康问卷
	 * @author : linyongtao
	 */
	public Long getHealthReportContents(String noteItemId);
	
/**
	 * clientid
	 * @author : yangpeixin
	 * **/
	public String findClinetId(Map<String, Object> map);/**
	 * 删除客户提交的健康问卷内容
	 * @author : linyongtao
	 */
	public void deleteHealthReportContents(String noteItemId);
	
	/**
	 * 更改健康问卷的完成状态为未完成
	 * @author : linyongtao
	 */
	public void updateHealthReportStatus(Map<String , Object> updateStatusMap);
	}
