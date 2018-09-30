package com.hualife.wxhb.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.AppliInfo;
import com.hualife.wxhb.api.rest.message.pojo.Applicant;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContent;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContents;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteAllTask;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteMyTask;
import com.hualife.wxhb.api.rest.message.pojo.SurvivalNoteTaskList;
import com.hualife.wxhb.api.rest.message.request.SurvivalSaveRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalInvestInfoResponseMessage;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.domain.entity.TNoteSurvival;
import com.hualife.wxhb.domain.entity.TNoteSurvivalInvestNote;
import com.hualife.wxhb.domain.entity.TNoteSurvivalNoteItem;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodySurivivalInfo;

@Repository
public interface SurvivalDao {
	/**
	 * @param id
	 * @return 契调函的契报告页面初始化时，得到影像列表  add by zhangweiwei
	 */
	public List<TNoteImage> getImageListByNoteid(Map<String,Object> map);
	/**
	 * 契调报告保存
	 * wupeixu
	 * @param survivalSaveReqMessage
	 */
	void saveSurvivalNoteBySurvivalNoteId(SurvivalSaveRequestMessage survivalSaveReqMessage);
	
	/**
	 * @param map
	 * @return 获取契调人信息 --add by wangt
	 */
	public TNoteSurvival getSurvival(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 获取所有契调任务信息 --add by wangt
	 */
	public List<SurvivalNoteAllTask> getAllTasks(Map<Object, Object> map);
	/**
	 * @param map
	 * @return 获取我的契调任务信息 --add by wangt
	 */
	public List<SurvivalNoteMyTask> getMyTasks(Map<Object, Object> map);
	/**
	 * @param 契调函ID
	 * @return 获取函件ID --add by wangt
	 */
	public String getNoteIDBySurvivalNoteId(String survival_note_id);
	/**
	 * @param 函件ID
	 * @return 获取保单信息 --add by wangt
	 */
	public List<SurvivalNoteTaskList> getTaskLists(String note_id);
	
	/**
	 * @descrption : 契调任务申请
	 * @author : linyongtao
	 *  @date:2017.08.24
	 * **/
	public void survivalStateUpdate(Map<String , Object> map);

	/**
	 * @descrption:契约调查报告书--预览(提交)
	 * @author : linyongtao
	 * @date:2017.08.24
	 * **/
	public void  insertSurvivalProblem(List<ProblemContents> problemContents);

	/**
	 * @param 获取投保人信息
	 * @author : yangpeixin
	 * @date 2017-08-07
	 */
	public List<Applicant> applicant(String id);
	/**
	 * @param 获取契调原因
	 * @author : yangpeixin
	 * @date 2017-08-07
	 */
	public List<String>  getReason(String id);
	/**
	 * @param 获取返回对象
	 * @author : yangpeixin
	 * @date 2017-08-07
	 */
	public SurvivalInvestInfoResponseMessage  survivalInvestInfo(String id);
	/**
	 * t_note_survival_invest_note 表插入
	 * add by zhanglong
	 */
	public void insertNoteSurvival(TNoteSurvivalInvestNote tNoteSurvivalInvestNote);
	
	/**
	 * t_note_survival_note_item 表插入
	 * add by zhanglong
	 */
	public void insertNoteSurvivalItem(List<TNoteSurvivalNoteItem> insertTNoteSurvivalNoteItemList);
	
	/**
	 * 获取契函的个数     add by zhangweiwei
	 */
	public TNoteClientNote countSurvivalNoteByNoteId(String noteId);
	/**
	 * @descrption:更改--契约调查问卷---is_valid  从Y有效--->无效
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public void  survivalIsValidUpdate(Map<String , Object>map);

	/**
	 *@descrption:根据note_id和note_type查询契调函件id--survival_note_id
	 *@author : linyongtao
	 *@date:2017.08.24
	 * **/
	public String  selectSurvivalNoteId(Map<String , Object>map);

	/**
	 * @descrption:契约调查报告完成后将--问卷回答状态(Y-已经完成，N-未完成) --调整为已经完成--Y
	 * @author :linyongtao
	 * @date:2017.08.24
	 * **/
	public void updateNoteItemStatus(Map<String , Object>map);
	
	/**
	 * @descrption:查询保单信息
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<AppliInfo>  selectApplayInfo (String survivalNoteId);
	
	/**
	 * @descrption:查询契约调查报告下的问题内容
	 * @author :linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<ProblemContent> getProblemContents(Map<String , Object>map); 
	/**
	 * @descrption:查询契约调查报告下客户上传的影像资料
	 * @author :linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<ImageInfo> getImageInfo(Map<String, Object> map);
	/**
	 * 批处理推送函件信息查询核保任务序号
	 * wupeixu
	 * @param pushMap
	 * @return
	 */
	public String getSurivivalNoteSeq(Map<Object, Object> pushMap);
	/**
	 * 查询当前契调人工号和契调报告
	 * wupeixu
	 * @param pushMap
	 * @return
	 */
	public PushMessageBodySurivivalInfo getMessageBodySurivivalInfo(Map<Object, Object> pushMap);
	/**
	 * 契调报告保存更新客户层函件状态信息
	 * wupeixu
	 * @param surMap
	 */
	public void updateClientNote(Map<String, String> surMap);
	/**
	 * 契调报告保存接口 查询客户层各个函件的函件状态
	 * wupeixu
	 * @param surMap
	 * @return
	 */
	public TNoteClientNote getClientNoteStatusByNoteid(Map<String, String> surMap);
	/**
	 * 契调报告保存接口 更新主表的状态
	 * wupeixu
	 * @param noteMainMap
	 */
	public void updateMainNote(HashMap<String, Object> noteMainMap); 
	/**
	 * 
	 * 批处理推送函件信息处理核心返回报文的接口 查询契调函对应note_id
	 * wupeixu
	 * @param sMap
	 * @return
	 */
	public String getSurivivalForNoteId(Map<String, String> sMap);
	/**
	 * 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
	 * wupeixu
	 * @param surlist
	 */
	public void updatePushInfoStatus(List<Map<String, String>> surlist);
	/**
	 * 添加轨迹获取note_id
	 * wupeixu
	 * @param survivalNoteId
	 * @return
	 */
	public String getNoteIdForSurvival(String survivalNoteId); 
	
	/**
	 * @descrption : 契调任务放弃
	 * @author : linyongtao
	 *  @date:2017.08.24
	 * **/
	public void survivalStateGiveUp(Map<String , Object> map);
	/**
	 * 批处理推送函件信息处理核心返回报文的接口 查询clientnoteid
	 * wupeixu
	 * @param sMap
	 * @return
	 */
	public String getSurivivalForClientNoteId(Map<String, String> sMap);
	/**
	 * 批处理推送函件信息处理核心返回报文 在client表更新状态
	 * wupeixu
	 * @param csurlist
	 */
	public void updateClientStatus(List<Map<String, String>> csurlist);
	
} 


