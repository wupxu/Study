package com.hualife.wxhb.integration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.AppliInfo;
import com.hualife.wxhb.api.rest.message.pojo.ClientNoteInfos;
import com.hualife.wxhb.api.rest.message.pojo.ClientNoteType;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContents;
import com.hualife.wxhb.api.rest.message.pojo.ProblemNoteInfos;
import com.hualife.wxhb.api.rest.message.pojo.UndwrtNoteInfo;
import com.hualife.wxhb.api.rest.message.response.PushModeSelectionResponseMessage;
import com.hualife.wxhb.domain.dto.ClientMessage;
import com.hualife.wxhb.domain.dto.PushnotePrint;
import com.hualife.wxhb.domain.dto.SelectId;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteClientRelationApply;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.domain.entity.TTaskCode;
import com.hualife.wxhb.domain.entity.TTaskPushAgentInfo;
import com.hualife.wxhb.integration.soap.message.request.chooseTypePush.ChooseTypePushRequestBodyNoteClient;
import com.hualife.wxhb.integration.soap.message.request.notePrintPush.NotePrintPushBodyNotes;

@Repository
public interface AgentDao {

	/**
	 * 函件下发方式选择时，对下发方式和noteStatus状态的修改 
	 * wupeixu
	 * @param map
	 */
	public void updatePushTypeByNoteId(Map<Object, Object> map);
	/**
	 * 
	 * @param map
	 * 获取每个函件的状态   add by zhangweiwei
	 */
	public List<TNoteClientNote> getClientNoteStatusByNoteId(Map<String,Object> map);
	/**
	 * 
	 * @param noteid
	 * 获取该客户层函件高额业务员报告书的信息  add by zhangweiwei
	 */
	public TNoteClientNote getAgentReportInfoByNoteId(Map<String,Object> map);
	/**
	 * 
	 * @param note_id
	 * @return 获取函件类型 --modify by zhangweiwei
	 */
	public String getNoteTypeByNoteId(Map<String,Object> map);
	/**
	 * 
	 * @param note_id
	 * @return 获取客户层函件类型列表  --modify by zhangweiwei
	 */
	public List<TNoteClientNote> getClientNoteTypesByNoteId(String noteId);
	/**
	 * 
	 * @param note_id
	 * @return 获取函件类型列表  --modify by zhangweiwei
	 */
	public String getClientNoteTypeByNoteId(String clientNoteId);
	/**
	 * @param note_id
	 * @return 获取到问题件对象的类型   --modify by zhangweiwei
	 */
	public String getProblemNoteByNoteId(String noteId);
	/**
	 * 客户层---转发给客户   add by zhangweiwei
	 * @param map 
	 */
	public void sendToClientClient(Map<String,Object> map);
	/**
	 * 问题件---转发给客户   add by  zhangweiwei
	 * @param map
	 */
	public void sendToClientProblem(Map<String,Object> map);
	
	/**
	 * 更新问题件对象状态    add by  zhangweiwei
	 */
	public void updateProblemObjectStatus(Map<String,Object> map); 
	
	/**
	 * 核保函---转发给客户   add by  zhangweiwei
	 * @param map
	 */
	public void sendToClientUndwrt(Map<String,Object> map);
	/**
	 * 保存函件主表的状态   add by zhangweiwei
	 * @param map
	 */
	public void updateNoteStatus(Map<String,Object> map);
	
	/**
	 * @descrption: 查询该代理人对对应的函件下---问题件函件信息
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<ProblemNoteInfos> mainPageProblemInfo(Map<Object, Object> map);
	/**
	 * @descrption:查询该代理人对对应的函件下---核保函函件信息
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<UndwrtNoteInfo> mainPageUndwrtInfo (Map<Object, Object> map);
	/**
     * @descrption:查询该代理人对对应的函件下---客户层函件信息
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public ClientNoteInfos mainPageClientInfo (String noteId);
	/**
	 * @descrption:查询查询客户层下的函件类型及状态
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<ClientNoteType> mainClientNoteType(String noteId);
	/**
	 * @descrption:查询保单信息--投保人+被保人+保单号 
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<AppliInfo> mainAppliInfo(String noteId);	
	/**
	 * @descrption:查询客户层函件id
	 * @author : linyongtao
	 * @date : 2017.08.24
	 * **/
	public List<SelectId> selectNoteId(Map<String, Object> map);

	/**
	 * 批处理函件下发核心返回后添加通知书路径
	 * wupeixu
	 * @param chooseRespMap
	 */
	public void setChooseTypeClientNoteUrl(Map<Object, Object> chooseRespMap);
	/**
	 * 
	 * @param note_id
	 * @return 返回函件主表的信息    --modify by zhangweiwei
	 */
	public TNoteMain getNoteMainInfoByNoteid(String noteId);
	/**
	 * 
	 * @param note_id
	 * @return 返回函件对应的投保单信息 --modify by zhangweiwei
	 */
	public List<TNoteClientRelationApply> getOrderNoInfosByNoteId(String noteId);
	/**
	 * 
	 */
	public TTaskCode getTaskCodeInfo(String modeType);
	/**
	 * 根据客户层函件类型代码查询客户层函件类型
	 */
	public String getValue(String key);
	/** 
	 * 将消息存到数据库中   add by zhangweiwei
	 */
	public void insertTTaskPushInfo(TTaskPushAgentInfo tTaskPushAgentInfo);
	/**
	 * 查询消息是否存在于消息列表中    add by zhangweiwei
	 */
	public Integer getTaskPushInfoCount(TTaskPushAgentInfo tTaskPushAgentInfo);
	/**
	 * 
	 * @param map
	 * @return 获取是否存在需要重新下发函件的客户层函件    add by zhangweiwei
	 */
	public TNoteClientNote countReSendClient(HashMap<String, Object> map);
	/**
	 * 
	 * @param map
	 * @return 取是否存在需要重新下发函件的问题件    add by zhangweiwei
	 */
	public TNoteProblemNote countReSendProblem(HashMap<String, Object> map);
	/**
	 *  获取客户选择代理人处理体检通知书    add by zhangweiwei
	 */
	public List<TNoteClientNote> countNoSendNotice(HashMap<String, Object> map );
	/**
	 * 根据客户层函件id获取函件id    add by zhangweiwei
	 */
	public String getNoteId(String clientNoteId);
	/**
	 * 查询需要发送的消息    add by zhangweiwei
	 */
	public List<TTaskPushAgentInfo> getTaskPushInfo(String flag);

	/**
	 * 查询问题件NOTEID
	 * @param param
	 */
	public String findNoteidByProblem(Map<String, Object> map);
	/**
	 * 查询问题件NOTEID 重新下发
	 * @param param
	 */
	public TNoteMain findTNoteMain(Map<String, Object> map);
	/**
	 * 查询函件NOTEID
	 * @param param
	 */
	public String findNoteidByClient(Map<String, Object> map);
	/**
	 * 函件下发方式选择为机构打印时，返回代理人所属渠道
	 * wupeixu
	 * @param map
	 * @return 
	 */
	public PushModeSelectionResponseMessage returnPushModeSelectionResponseMessage(Map<Object, Object> map);

	/**
	 * 函件下发方式为自己打印时，在状态中间表里存储需要的值 wupeixu
	 * 
	 * @param pushnotePrints
	 */
	public void addTaskPushnotePrint(List<PushnotePrint> pushnotePrints);

	/**
	 * 次品单主表状态更改
	 * @param 
	 * @return 
	 */
	public void updateNoteNotQualifiedMain(Map<String, Object> map);
	
	 /**
	  *  @descrption:根据note_id和note_type查询高额业务员报告书的id---note_report_id
	  *  @author : linyongtao
	  *  @date : 2017.08.24
	  * **/
	public String  selectAgentReportId(String clientNoteId);
	/**
	 * @descrption:保存高额业务员报告书
	 * @author :linyongtao
	 * @date : 2017.08.24
	 * **/
	public void  agentReportSave(List<ProblemContents> problemContents);
	/**
	 * @descrption:业务员高额报告书状态改变  --未完成N--->已经完成Y
	 * @author :linyongtao
	 * @date : 2017.08.24
	 * **/
	public void noteItemStatusUpdate(Map<String , Object>map);
	
	/**
	 * 查询向函件推送状态表的的数据中的task_code
	 * wupeixu
	 * @param map
	 * @return
	 */
	public String findNotePrintTableTaskCodeByNoteId(Map<String, String> map);
	/**
	 * 查询向函件推送状态表的的数据中的note_type,note_seq集合
	 * wupeixu
	 * @param map
	 * @return
	 */
	public List<PushnotePrint> findNotePrintTableMessageByNoteId(Map<String, String> map);
	/**
	 * 批处理rest接口中为机构打印时组织报文 查询note_type,note_seq集合
	 * wupeixu
	 * @param map
	 * @return
	 */
	public List<ChooseTypePushRequestBodyNoteClient> findNoteRestMessageByNoteId(Map<String,String> map);
	/**
	 * 重新下发主表状态更改
	 * @param 
	 * @return
	 * add by yangpeixin 
	 */
	public void updateNoteSecondMain(Map<String , Object>map);

	/**
	 * 查询主表所有数据
	 * @param 
	 * @return
	 * add by yangpeixin  
	 */
	 public TNoteMain  finaAllSecondMessage(String id);
	
	/**
	 * 插入数据
	 * @param 
	 * @return
	 * add by yangpeixin  
	 */
	 public void AddSecondMessage(TNoteMain tNoteMain);
		/**
		 * 批量更新客户层函件状态   add by zhangweiwei
		 * @param tNoteClientNoteList
		 */
		public void batchUpdateClientNote(List<TNoteClientNote> tNoteClientNoteList);
		/**  
		 * 根据id 获取核保函id  add by zhangweiwei
		 * @param noteId
		 * @return
		 */
		public String getUndwrtNoteByNoteId(String noteId);

	/**
	 * 批处理函件下发 ,通过查询中间表获取核心所需参数报文
	 *  wupeixu
	 * @param typeMap
	 * @return
	 */
	public List<NotePrintPushBodyNotes> findChooseTypeClientBodys(Map<String, String> typeMap);
    /**
     * 批处理函件打印 把打印结果存储到t_task_push_note_print表
     * wupeixu
     * @param printList
     */
	public void setTTaskPushNotePrint(List<Map<String, String>> printList);
    /**
     * 批处理函件打印时核心返回报文后 把oss路径和ftp路径插入到clentnote表
     * wupeixu
     * @param printList
     */
	public void setClientUrlAndStatus(List<Map<String, String>> printList);
	/**
	 * 更改client_note表的状态(健康函，体检函)
	 * wupeixu
	 * @param pushMap
	 */
	public void updateClientNoteStutas(Map<Object, Object> pushMap);
	/**
	 * 更改client_note表的状态(财务函)
	 * wupeixu
	 * @param pushMap
	 */
	public void updateClientNoteStutasForFina(Map<Object, Object> pushMap);
	/**
	 *  批量更新推送状态
	 * @param pushResultList
	 */
	public void batchUpdateTTaskPushAgentInfo(List<TTaskPushAgentInfo> pushResultList);
	/**
	 * 查询数据库里是否有函件打印方式
	 * wupeixu
	 * @return
	 */
	public String findPushTypeByNoteId(String note_id);
	
	/**
	 * 查询客户是否已经提交过高额业务员报告书
	 * author :linyongtao
	 * **/
	public long getAgentReportContents(String clientNoteId);
	
	/**
	 * 删除客户已经上传的高额业务员报告书
	 * author :linyongtao
	 */
	public void deleteAgentReport(String clientNoteId);
	
	/**
	 * 更改业务员报告书的完成状态
	 * author :linyongtao
	 */
	public void updateAgentReportStatus(Map<String, Object> updateStatusMap);
	
	/**
	 * 查询代理人编号agent_no是否存在
	 * author :linyongtao
	 * **/
	public Long selectExitByAgentNo(String agent_no);
	/**
	 * 获取client表的client_note_id，note_type
	 * wupeixu
	 * @param noteId
	 * @return
	 */
	public List<ClientMessage> findNoteType(String noteId);
}
