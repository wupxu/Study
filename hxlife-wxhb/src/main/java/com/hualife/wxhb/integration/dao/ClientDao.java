package com.hualife.wxhb.integration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hualife.wxhb.api.rest.message.pojo.Fina;
import com.hualife.wxhb.api.rest.message.pojo.Health;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.NoteTypeAndStatus;
import com.hualife.wxhb.api.rest.message.pojo.Physical;
import com.hualife.wxhb.api.rest.message.pojo.Survival;
import com.hualife.wxhb.api.rest.message.response.NoteInfoResponseMessage;
import com.hualife.wxhb.domain.dto.PushMessage;
import com.hualife.wxhb.domain.dto.PushMotion;
import com.hualife.wxhb.domain.entity.TNoteClientNote;

@Repository
public interface ClientDao {

	/**
	* 查询客户信息
	* @author : yangpei
	*/
    public NoteInfoResponseMessage findNoteInfo(String id);
    /**
	* 查询契调数据
	* @author : yangpei
	*/
    public Survival  findSurvival(Map<String , Object>map);
    /**
	* 查询健康函
	* @author : yangpei
	*/
    public Health  findHealth(Map<String , Object>map);
    /**
	* 查询体检函
	* @author : yangpei
	*/
    public Physical  findPhysical(Map<String , Object>map);
    /**
	* 查询财务函
	* @author : yangpei
	*/
    public Fina   findFina(Map<String , Object>map);
	/**
	 * @descrption:次品单上传后需要判断其他函件的状态
	 * @author : linyongtao
	 * @date:2017.08.24
	 * **/
	public List<NoteTypeAndStatus> selectClientNoteStatus (String fina_note_id);
	/**
	 * @descrption:根据上面selectOtherClientNoteStatus 的查询结果来判定主表中函件的状态，如果查询到的
	 * 各个函件的状态都是已处理，则将主表的状态调整为已处理    --linyongtao
	 * @author : linyongtao
	 * @date:2017.08.24
	 * **/
	public void updateMainSatus(Map<Object , Object>map);
	/**
	 * @descrption:查询下发次品单原因
	 * @author : linyongtao
	 * @date:2017.08.24
	 * **/
	public String selectClientNotQualifiedReason(Map<String , Object>map);
	/**
	 * @descrption:查询客户上传的影像资料---次品单
	 * @author : linyongtao
	 * @date:2017.08.24
	 * **/
	public List<ImageInfo> selectClientNotQualifiedImage(Map<String , Object>map);
	

    
    /**
     * t_note_client_note 表数据提交
     * @param TNoteClientNote 
     * add by zhanglong
     */
    public void insertNoteClient(TNoteClientNote tNoteClientNote);
    

    /**
	 * 次品单原因
	 * @param param
	 */
    public void updatNoteNotQualifiedReason(Map<String, Object> map);
    
    /**
	 * 重新下发原因
	 * @param param
	 */
    public void updatNoteSecondReason(Map<String, Object> map);
    /**
	 * 更新主表数据
	 * @param param
	 */
    public void updateMainSecond(String id);
    
    /**
	 * 更新重下下发状态
	 * 
	 * @param param
	 */
    public void updateNoteSecond(String id);
    
    /**
	 * 查询表里数据
	 * @author :yangpeixin
	 * @param param
	 */
    public TNoteClientNote findAll(Map<String, Object> map);
    
    /**
	 * 插入数据
	 * @param param
	 */
	public void addNoteSecond(TNoteClientNote tNoteClientNote);

	public String findClinetId(Map<String, Object> map);

	/**
	 * 批处理推送函件从t_task_push_note_inf上获取noteid和noteType 
	 * wupeixu
	 * @param mMap
	 * @return
	 */
	public List<PushMessage> getPushNoteId(Map<Object, Object> mMap);
    /**
     * 批处理推送函件查询核保任务号
     * wupeixu
     * @param pushMap
     * @return
     */
	public String getTaskcode(Map<Object, Object> pushMap);
    /**
     * 批处理推送函件-查询其他函件的核保序号和函件类型 回销类型
     *  wupeixu
     * @param pushMap
     * @return
     */
	public PushMotion getPushMessageBodyMotion(Map<Object, Object> pushMap);
	
	/**
	 * @description :查询该客户层函件下其他函件的状态
	 * @author :linyongtao
	 * @date : 2017-08-24
	 * **/
	public List<NoteTypeAndStatus> selectOtherClientNoteStatus (String clientNoteId);
/**
	 * @description :查询该客户层函件下其他函件的状态
	 * @author :linyongtao
	 * @date : 2017-08-24
	 * **/
	public String getClientNoteIdByNoteId(Map<String, Object> map);/**
	 * 批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应note_id
	 * wupeixu
	 * @param mMap
	 * @return
	 */
	public String getMotionForNoteId(Map<String, String> mMap);
	/**
	 * 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
	 * wupeixu
	 * @param motlist
	 */
	public void updatePushInfoStatus(List<Map<String, String>> motlist);
	/**
	 * @descrption :体检函免陪
	 * @author :yangpeixin
	 * @date : 2017-08-24
	 * **/
	 public String selectChooseType(String id);
	 /**
	 * @descrption :体检函
	 * @author :yangpeixin
	 * @date : 2017-08-24
	 * **/
	 public List<TNoteClientNote> getnoteClientInfo(String id);
 
	 /**
	  * 查询业务表是否存在未处理完的数据
	  * @param map
	  * @return
	  */
	 public String selectNoteclientExists(Map<String, String> map); /**
		 * @descrption :批处理数据查询
		 * @author :yangpeixin
		 * @date : 2017-08-24
		 * **/
	 public TNoteClientNote getClientPictureInfo(String id);
	 /**
		 * @descrption :批处获取noteid
		 * @author :yangpeixin
		 * @date : 2017-09-95
		 * **/
	 public String getNoteIdByclietId(String id);

	 /**
	  * 根据客户层函件id查询note_id
	  * **/
	 public String getNoteIdByClientNoteId(String client_note_id);
	 /**
	  * 批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应client_note_id
	  * wupeixu
	  * @param mMap
	  * @return
	  */
	public String getMotionForClientNoteId(Map<String, String> mMap);
	/**
	 * 批处理推送函件信息处理核心返回报文的接口 如果成功更改主表和client表状态
	 * wupeixu
	 * @param cmotlist
	 */
	public void updateMationClientStatus(List<Map<String, String>> cmotlist);

	/**
	 * 查询前端传过来的note_id是否存在
	 * @author :linyongtao
	 */
	public Long selectExitByNoteId(String noteId);
	
	/**
	 * 查询前端传过来的client_note_id是否存在
	 * @author :linyongtao
	 */
	public Long selectExitByClientNoteId(String clientNoteId);
	
}
