package com.hualife.wxhb.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.request.ProblemInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ProblemInfoResponseMessage;
import com.hualife.wxhb.domain.dto.ProblemDetail;
import com.hualife.wxhb.domain.dto.ProblemNoteAnswerInfo;
import com.hualife.wxhb.domain.dto.ProblemObj;
import com.hualife.wxhb.domain.dto.PushMessage;
import com.hualife.wxhb.domain.entity.TNoteProblemDetail;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.domain.entity.TNoteProblemObject;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyProblemObject;

/**
 * @description
 * @author zhangweiwei
 * @date 2017-08-03
 */
@Repository
public interface ProblemDao {

	/**
	 * 代理人、客户提交各自的相关问卷，更新问题件对象处理状态  add by zhangweiwei
	 * @param map
	 */
	public void updateProblemObject(Map<String,Object> map);
	/**
	 * 问题件列表初始化--查询代理人姓名，代理电话，投保人姓名，投保人电话，被保人姓名，投保人是否身份验证
	 * wupeixu
	 * @param problemInfoReqMess
	 */
	public ProblemInfoResponseMessage selectProblemNolMes(ProblemInfoRequestMessage problemInfoReqMess);
	/**
	 * 问题件初始化--查询影像集合--add by wupeixu
	 * @param proMap
	 */
	public List<ImageInfo> getProbinfoImages(Map<String, String> proMap);
	/**
	 * 问题件初始化--查询外层集合--add by wupeixu
	 * @param string
	 * @return
	 */
	public List<ProblemObj> selectProblemObjects(String string);
	/**
	 * 问题件初始化--查询内层集合--add by wupeixu
	 * @param delMap
	 */
	public List<ProblemDetail> selectProblemDescs(Map<String, String> delMap);
	/**
	 * 客户问题件提交时，修改问题件函件信息表的函件状态  add by zhangweiwei
	 * @param map
	 */
	public void updateProblemNote(Map<String,Object> map);
	/**
	 * 客户进行问题件提交时，修改主表的函件状态    add by zhangweiwei
	 * @param map
	 */
	public void updateMainNote(Map<String,Object> map);
	

	/** 
	 * 问题件回销状态
	 * @param map
	 */
	public void updateProblemNoteStatus(HashMap<String, Object> map);
	/**
	 * 更新主表状态
	 * @param map
	 */
	public void updateMainNoteStatus(HashMap<String, Object> map);
	/**
	 * t_note_problem_note 表更新
	 * add by zhanglong
	 */
	public void insertNoteProblem(List<TNoteProblemNote> insertTNoteProblemNoteList);
	/**
	 * 存储次品单原因
	 * @param map
	 */
	public void updatNoteNotQualifiedReason(Map<String, Object> map);
	
	/**
	 * 存重新下发原因
	 * @param map
	 */
	public void updatNoteSecondReason(Map<String, Object> map);
	
	/**
	 * 更新重新下发状态
	 * @param String
	 */
     public void updateNoteSecond(Map<String, String> map);
	 
     /**
 	 * 查询问题件信息
 	 * @param String
 	 */
	 public TNoteProblemNote findNoteSecond(String id);
	 
	 /**
	 * 插入数据
	 * @param tNoteProblemNote
	 */
	 public void addNoteSecond(TNoteProblemNote tNoteProblemNote);
	
	/**
	  * 批量更新问题件内容
	  * add by zhanyl
	  * **/
	public void batchUpdateProblemInfoList(List<ProblemNoteAnswerInfo> problemNoteAnswerInfoList);
	
	/**
	  * t_note_problem_object 表更新
	  * @param tNoteProblemObject
	  * add by zhanglong
	  */
	 public void insertNoteProblemObject(List<TNoteProblemObject> insertTNoteProblemObjectList);
	 /**
	  *  根据问题件id查询问题件是否存在  add by zhangweiwei
	  */
	 public String countProblemNoteByNoteId(String problemNoteId);
	 /**
	  * 投保人是否身份验证 
	  * wupeixu
	  * @param problemInfoReqMess
	  * @return
	  */
	 public String selectPhoneSuccess(ProblemInfoRequestMessage problemInfoReqMess);
/**
	  * 批处理推送函件查询核保任务序号 投保单号 是否为次品单 是否重新下发
	  * wupeixu
	  * @param pushMap
	  * @return
	  */
	 public PushMessage getNoteSeqAndCode(Map<Object, Object> pushMap);
	/**
	 * 批处理推送函件查询对应问题和问题回答集合 
	 * wupeixu
	 * @param pushMap
	 * @return
	 */
	 public List<PushMessageBodyProblemObject> getproblemObjectDetails(Map<Object, Object> pushMap);
	/**
	 * 批处理推送函件查询对象集合
	 * wupeixu
	 * @param pushMap
	 * @return
	 */
	 public List<PushMessageBodyProblemObject> getProblemObject(Map<Object, Object> pushMap);
	/**
	 * 批处理推送函件-问题件信息存储到t_task_push_note_info表
	 * wupeixu
	 * @param toTableMap
	 */
	 public void pushProblemMessageToTable(Map<Object, Object> toTableMap);/**
	 * @descrption : 问题件次品单提交--更改t_note_mian表和t_note_problem_note表note_status状态从--次品单处理-->已处理
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public void updateNoteStatus(Map<String,Object> map);		
	/**
	 * @descrption : 查询问题件--下发次品单原因
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public String selectProblemNotQualifiedReason(Map<String , Object>map);	
	/**
	 * @descrption : 查询问题件--客户上传的影像资料
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<ImageInfo> selectProblemNotQualifiedImage(Map<String , Object>map);
	
	/**
	 * t_note_problem_detail 表数据插入
	 * @param insertTNoteProblemDetailList
	 */
	public void insertTNoteProblemDetail(List<TNoteProblemDetail> insertTNoteProblemDetailList);
	/**
	 * 根据核保任务号、核保序号、函件类型获取问题件id   add  by  zhangweiwei
	 */
	public TNoteProblemNote getNoteIdByBody(HashMap<String, Object> map);
	/**
	 * 
	 * @param problemNoteId
	 * @return 获取问题件对象id  add by zhangweiwei
	 */
	public String getProblemObjectIdByNoteId(Map<String,Object> map);
	/**
	 * 批处理推送函件信息处理核心返回报文的接口 查询问题件对应note_id
	 * wupeixu
	 * @param pMap
	 * @return
	 */
	public String getProblemForNoteId(Map<String, String> pMap);
	/**
	 * 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
	 * wupeixu
	 * @param prolist
	 */
	public void updatePushInfoStatus(List<Map<String, String>> prolist);
	
	/**
	 * 查询是否问题件已经处理完成
	 * @param map
	 * @return
	 */
	public String selectnoteProblemExists(Map<String, Object> map);

	/**
	 * 查询问题件对象信息表
	 * @param map
	 * @return
	 */
	public List<TNoteProblemObject> getTNoteProblemObject(String id);
	/**
	 * 插入问题件对象信息表
	 * @param map
	 * @return
	 */
	public void addTNoteProblemObject(TNoteProblemObject tNoteProblemObject);

	/**
	 * 查询问题件详细信息表
	 * @param map
	 * @return
	 */
	public List<TNoteProblemDetail> getTNoteProblemDetail(String id);
	/**
	 * 插入问题件详细信息表
	 * @param map
	 * @return
	 */
	public void addTNoteProblemDetail(TNoteProblemDetail tNoteProblemDetail);/**
	 * 根据noteId查询问题件id
	 * @author : linyongtao
	 * **/
	public String getProblemNoteIdByNoteId(String noteId);
	/**
	 * 查询info表里是否有重复数据
	 * wupeixu
	 * @param chMap
	 */
	public String checkNoteMessage(Map<String, String> chMap);
}
