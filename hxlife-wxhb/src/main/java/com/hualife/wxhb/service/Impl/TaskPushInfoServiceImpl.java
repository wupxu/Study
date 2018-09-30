package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.domain.entity.TTaskCode;
import com.hualife.wxhb.domain.entity.TTaskPushAgentInfo;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.service.TaskPushInfoService;
/**
 * @author zhangweiwei
 * @deprecation 组织推送消息到表impl
 * @date 2017-08-04
 */
@Service
public class TaskPushInfoServiceImpl implements TaskPushInfoService{
	
	private final Logger logger = LoggerFactory.getLogger(TaskPushInfoServiceImpl.class);

	@Autowired
	private AgentDao agentDao;
	/**
	 * 核保师下发函件，代理人收到函件消息
	 */
	@Override
	@Transactional
	public void saveUnderWriterSendoutNote(Map<String,Object> map){
		String noteId=(String) map.get("noteId");
		String noteType=(String) map.get("noteType");
		String clientName=(String) map.get("clientName");
		String agentNo=(String) map.get("agentNo");
		Assert.notEmpty(noteId, "函件noteid不能为空");
		Assert.notEmpty(noteType, "函件类型不能为空");
		Assert.notEmpty(clientName, "客户姓名不能为空");
		Assert.notEmpty(agentNo, "代理人工号不能为空");

		long startTime = 0;
		long endTime   = 0;
		String modeId=null;
		String pushDesc=null;
		String modeUrl=null;
		TTaskCode tTaskCode=null;
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"检查函件id是否为空");
		if(noteType.equals(Constant.note_type_CLIENT)){
			String orderNo=(String) map.get("orderNo");
			String noteTypes=(String) map.get("noteTypes");
			Assert.notEmpty(orderNo, "函件noteid不能为空");
			Assert.notEmpty(noteTypes, "函件noteid不能为空");
			//根据消息模板类型获取消息模板id
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),Constant.client_mode_type,"根据消息模板类型获取消息模板id");
			startTime=System.currentTimeMillis();
			tTaskCode=agentDao.getTaskCodeInfo(Constant.client_mode_type);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"核保师下发函件，代理人收到函件消息耗时"+(endTime-startTime)+"ms");
			if(tTaskCode!=null){
				modeId= tTaskCode.getModeId();
				modeUrl=tTaskCode.getModeUrl();
				modeUrl=Constant.WXWEB_URL+modeUrl;
			}else{
				throw new BusinessException("对应的模板不存在"); 
			}	
			//拼接消息内容
			pushDesc=tTaskCode.getModeDesc().
					replaceAll("name",clientName).
					replaceAll("orderno",orderNo).
					replaceAll("notetype",noteTypes);
			pushDesc="<a href='"+modeUrl+"?note_id="+noteId+"'>"+pushDesc+"</a>";
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),pushDesc,"获取链接");

			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"核保师下发客户层函件，代理人收到函件消息");
			insertTTaskPushInfo(noteId,noteId,modeId,agentNo,pushDesc);
		}
		if(noteType.equals(Constant.note_type_PROBLEM)){
			String specificNoteId=(String) map.get("specificNoteId");
			String orderNo=(String) map.get("orderNo");
			Assert.notEmpty(specificNoteId, "具体 函件id不能为空");
			Assert.notEmpty(orderNo, "投保单号不能为空");
			//根据消息模板类型获取消息模板id
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),Constant.problem_mode_type,"根据消息模板类型获取消息模板id");
			
			startTime=System.currentTimeMillis();
			tTaskCode=agentDao.getTaskCodeInfo(Constant.problem_mode_type);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"核保师下发问题件，代理人收到函件消息耗时"+(endTime-startTime)+"ms");		
			if(tTaskCode!=null){
				modeId= tTaskCode.getModeId();
				modeUrl=tTaskCode.getModeUrl();
				modeUrl=Constant.WXWEB_URL+modeUrl;
			}else{
				throw new BusinessException("对应的模板不存在"); 
			}
			pushDesc=tTaskCode.getModeDesc().
				replaceAll("name",clientName).
				replaceAll("orderno",orderNo);
			pushDesc="<a href='"+modeUrl+"?problem_note_id="+specificNoteId+"'>"+pushDesc+"</a>";
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),pushDesc,"获取链接");

			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"插入一条机构回销函件，选择下发次品单消息");
			insertTTaskPushInfo(noteId,specificNoteId,modeId,agentNo,pushDesc);
		}
		if(noteType.equals(Constant.note_type_UNDWRT)){
			String undwrtType=(String) map.get("undwrtType");
			String specificNoteId=(String) map.get("specificNoteId");
			Assert.notEmpty(undwrtType, "核保函类型不能为空");
			if(undwrtType.equals(Constant.undwrt_note_type_UNSTANDARD)){
				//根据消息模板类型获取消息模板id
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtType,"根据消息模板类型获取消息模板id");
				startTime=System.currentTimeMillis();
				tTaskCode=agentDao.getTaskCodeInfo(Constant.undwrt_addlist_mode_type);
				endTime=System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"核保师下发核保函，代理人收到函件消息耗时"+(endTime-startTime)+"ms");
				if(tTaskCode!=null){
					modeId= tTaskCode.getModeId();
					modeUrl=tTaskCode.getModeUrl();
					modeUrl=Constant.WXWEB_URL+modeUrl;
				}else{
					throw new BusinessException("对应的模板不存在"); 
				}
			}else if(undwrtType.equals(Constant.undwrt_note_type_POSTPONED_CANCEL)){
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtType,"根据消息模板类型获取消息模板id");
				startTime=System.currentTimeMillis();
				tTaskCode=agentDao.getTaskCodeInfo(Constant.undwrt_refuse_mode_type);
				endTime=System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"核保师下发核保函，代理人收到函件消息耗时"+(endTime-startTime)+"ms");
				if(tTaskCode!=null){
					modeId= tTaskCode.getModeId();
					modeUrl=tTaskCode.getModeUrl();
					modeUrl=Constant.WXWEB_URL+modeUrl;
				}else{
					throw new BusinessException("对应的模板不存在"); 
				}	
			}else{
				throw new BusinessException("不存在类型"+undwrtType+"的核保函"); 
			}
			pushDesc=tTaskCode.getModeDesc().
					replaceAll("name",clientName);
			pushDesc="<a href='"+modeUrl+"?note_undwrt_id="+specificNoteId+"'>"+pushDesc+"</a>";
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),pushDesc,"获取链接");

			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"插入一条机构回销函件，选择下发次品单消息");
			insertTTaskPushInfo(noteId,specificNoteId,modeId,agentNo,pushDesc);
		}
	}
	/**
	 * 机构回销函件，选择重新发送函件
	 */
	@Override
	@Transactional
	public void saveReSendNoteToAgent(Map<String,Object> map){
		
		String noteId=(String) map.get("noteId");
		String specificNoteId=(String) map.get("specificNoteId");
		String noteType=(String) map.get("noteType");
		String agentNo=(String) map.get("agentNo");
		String reason=(String) map.get("reason");
		String clientName=(String) map.get("clientName");
		Assert.notEmpty(noteId, "函件noteid不能为空");
		Assert.notEmpty(specificNoteId, "具体 函件id不能为空");
		Assert.notEmpty(noteType, "函件类型不能为空");
		Assert.notEmpty(clientName, "客户姓名不能为空");
		Assert.notEmpty(agentNo, "代理人工号不能为空");
		Assert.notEmpty(reason, "重新下发原因不能为空");
		long startTime = 0;
		long endTime   = 0;
		//获取模板信息
		TTaskCode tTaskCode=null;
		String modeId="";
		String pushDesc="";
		String modeUrl="";
		if(noteType.equals(Constant.note_from_core_type_HEALTH)||noteType.equals(Constant.note_from_core_type_PHYSICAL)){
			startTime=System.currentTimeMillis();
			tTaskCode=agentDao.getTaskCodeInfo(Constant.resend_client_mode_type);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取模板信息耗时"+(endTime-startTime)+"ms");
			if(tTaskCode!=null){
				modeId= tTaskCode.getModeId();
				modeUrl=tTaskCode.getModeUrl();
				modeUrl=Constant.WXWEB_URL+modeUrl;
			}else{
				throw new BusinessException("对应的模板不存在");
			}
			String noteTypeDesc=DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType);
			pushDesc=tTaskCode.getModeDesc().
						replaceAll("name",clientName).
						replaceAll("notetype", noteTypeDesc).
						replaceAll("reason", reason);
			pushDesc="<a href='"+modeUrl+"?note_id="+noteId+"'>"+pushDesc+"</a>";
		}else if(noteType.equals(Constant.note_from_core_type_PROBLEM)){	
			startTime=System.currentTimeMillis();
			tTaskCode=agentDao.getTaskCodeInfo(Constant.resend_problem_mode_type);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取模板信息耗时"+(endTime-startTime)+"ms");
			if(tTaskCode!=null){
				modeId= tTaskCode.getModeId();
				modeUrl=tTaskCode.getModeUrl();
				modeUrl=Constant.WXWEB_URL+modeUrl;
			}else{
				throw new BusinessException("对应的模板不存在");
			}
			String noteTypeDesc=DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType);
			pushDesc=tTaskCode.getModeDesc().
						replaceAll("name",clientName).
						replaceAll("notetype", noteTypeDesc).
						replaceAll("reason", reason);
			pushDesc="<a href='"+modeUrl+"?problem_note_id="+specificNoteId+"'>"+pushDesc+"</a>";
		}else{
			throw new BusinessException("函件类型"+noteType+"的函件不存在次品单");
		}
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"插入一条机构回销函件，选择下发次品单消息");
		insertTTaskPushInfo(noteId,specificNoteId,modeId,agentNo,pushDesc);
	}
	/**
	 * 机构回销函件，选择下发次品单
	 */	
	@Override
	@Transactional
	public void saveSendFailNote(Map<String,Object> map){
		String noteId=(String) map.get("noteId");
		String specificNoteId=(String) map.get("specificNoteId");
		String noteType=(String) map.get("noteType");
		String agentNo=(String) map.get("agentNo");
		String clientName=(String) map.get("clientName");
		Assert.notEmpty(noteId, "函件noteid不能为空");
		Assert.notEmpty(specificNoteId, "具体 函件id不能为空");
		Assert.notEmpty(noteType, "函件类型不能为空");
		Assert.notEmpty(clientName, "客户姓名不能为空");
		Assert.notEmpty(agentNo, "代理人工号不能为空");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"检查函件id是否为空");
		long startTime = 0;
		long endTime   = 0;
		//获取模板信息
		TTaskCode tTaskCode=null;
		String modeId="";
		String pushDesc="";
		String modeUrl="";
		if(noteType.equals(Constant.note_from_core_type_HEALTH)||noteType.equals(Constant.note_from_core_type_PHYSICAL)){
			startTime=System.currentTimeMillis();
			tTaskCode=agentDao.getTaskCodeInfo(Constant.image_client_fail_mode_type);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取模板信息耗时"+(endTime-startTime)+"ms");
			if(tTaskCode!=null){
				modeId= tTaskCode.getModeId();
				modeUrl=tTaskCode.getModeUrl();
				modeUrl=Constant.WXWEB_URL+modeUrl;
			}else{
				throw new BusinessException("对应的模板不存在");
			}
			String noteTypeDesc=DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType);
			pushDesc=tTaskCode.getModeDesc().
						replaceAll("name",clientName).
						replaceAll("notetype", noteTypeDesc);
			pushDesc="<a href='"+modeUrl+"?note_id="+noteId+"'>"+pushDesc+"</a>";
		}else if(noteType.equals(Constant.note_from_core_type_PROBLEM)){	
			startTime=System.currentTimeMillis();
			tTaskCode=agentDao.getTaskCodeInfo(Constant.image_problem_fail_mode_type);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取模板信息耗时"+(endTime-startTime)+"ms");
			if(tTaskCode!=null){
				modeId= tTaskCode.getModeId();
				modeUrl=tTaskCode.getModeUrl();
				modeUrl=Constant.WXWEB_URL+modeUrl;
			}else{
				throw new BusinessException("对应的模板不存在");
			}
			String noteTypeDesc=DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType);
			pushDesc=tTaskCode.getModeDesc().
						replaceAll("name",clientName).
						replaceAll("notetype", noteTypeDesc);
			pushDesc="<a href='"+modeUrl+"?note_id="+noteId+"&note_type="+Constant.note_from_core_type_PROBLEM+"'>"+pushDesc+"</a>";
		}else{
			throw new BusinessException("函件类型"+noteType+"的函件不存在次品单");
		}

		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"插入一条机构回销函件，选择下发次品单消息");
		insertTTaskPushInfo(noteId,specificNoteId,modeId,agentNo,pushDesc);
	}
	/**
	 * 客户授权代理人处理函件 
	 */
	@Transactional
	public void saveAuthorizeAgentHandleNote(String noteId,String clientNoteId,String noteType) {
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),clientNoteId,"检查客户层id是否为空");
		if(StringUtils.isBlank(clientNoteId)){
			throw new BusinessException("客户层函件id不能为空");
		}
		if(StringUtils.isBlank(noteType)){
			throw new BusinessException("客户层函件类型不能为空");
		}
		if(StringUtils.isBlank(noteId)){
			throw new BusinessException("函件主表id不能为空");
		}
		long startTime = 0;
		long endTime   = 0;
		//获取模板信息
		startTime=System.currentTimeMillis();
		TTaskCode tTaskCode=agentDao.getTaskCodeInfo(Constant.dealmess_mode_type);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取模板信息耗时"+(endTime-startTime)+"ms");
	
		String modeId="";
		String pushDesc="";
		String modeUrl="";
		if(tTaskCode!=null){
			modeId= tTaskCode.getModeId();
			modeUrl=tTaskCode.getModeUrl();
			modeUrl=Constant.WXWEB_URL+modeUrl;
		}else{
			throw new BusinessException("对应的模板不存在");
		}
		//获取函件主表信息
		startTime=System.currentTimeMillis();
		TNoteMain tNoteMain=agentDao.getNoteMainInfoByNoteid(noteId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取函件主表信息耗时"+(endTime-startTime)+"ms");
		if(tNoteMain!=null){
			String clientType=DataConVersion.dataConVersion(Constant.transfer_data_NOTE_TYPE,noteType);
			pushDesc=tTaskCode.getModeDesc().
					replaceAll("name",tNoteMain.getClientName()).
					replaceAll("notetype", clientType);
			pushDesc="<a href='"+modeUrl+"?note_id="+noteId+"'>"+pushDesc+"</a>";
		}else{	
			throw new BusinessException("对应函件主表信息不存在");
		}
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"插入一条客户授权代理人处理函件消息");
		insertTTaskPushInfo(noteId,clientNoteId,modeId,tNoteMain.getAgentNo(),pushDesc);
		
	}
	/**
	 * 客户选择代理人处理体检通知书
	 */
	@Override
	@Transactional
	public void saveAuthorizeAgentHandleMedicalNotice(String noteId,String clientNoteId){

		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),clientNoteId,"检查体检函id是否为空");
		if(StringUtils.isBlank(clientNoteId)){
			throw new BusinessException("体检函id不能为空");
		}
		if(StringUtils.isBlank(noteId)){
			throw new BusinessException("函件id不能为空");
		}
		long startTime = 0;
		long endTime   = 0;
		//获取模板信息
		startTime=System.currentTimeMillis();
		TTaskCode tTaskCode=agentDao.getTaskCodeInfo(Constant.dealnotice_mode_type);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取模板信息耗时"+(endTime-startTime)+"ms");

		String modeId="";
		String pushDesc="";
		String modeUrl="";
		if(tTaskCode!=null){
			modeId= tTaskCode.getModeId();
			modeUrl=tTaskCode.getModeUrl();
			modeUrl=Constant.WXWEB_URL+modeUrl;
		}else{
			throw new BusinessException("对应的模板不存在");
		}
		//获取函件主表信息
		startTime=System.currentTimeMillis();
		TNoteMain tNoteMain=agentDao.getNoteMainInfoByNoteid(noteId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"获取函件主表信息耗时"+(endTime-startTime)+"ms");
		if(tNoteMain!=null){
			pushDesc=tTaskCode.getModeDesc().
					replaceAll("name",tNoteMain.getClientName()).
					replaceAll("agentName", tNoteMain.getAgentName());
			pushDesc="<a href='"+modeUrl+"?note_id="+noteId+"'>"+pushDesc+"</a>";
		}else{	
			throw new BusinessException("对应函件主表信息不存在");
		}
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"插入一条客户选择代理人处理体检通知书消息");
		insertTTaskPushInfo(noteId,clientNoteId,modeId,tNoteMain.getAgentNo(),pushDesc);
	}
	/**
	 *   插入一条消息
	 * @param noteId
	 * @param modeId
	 * @param clientNoteId
	 * @param pushObjectNo
	 * @param pushDesc
	 * @param startTime
	 * @param endTime
	 */
	public void insertTTaskPushInfo(String noteId, String clientNoteId,String modeId,String agentNo,String pushDesc){
		long startTime=0;
		long endTime=0;
	    TTaskPushAgentInfo tTaskPushInfo=new TTaskPushAgentInfo();
			//拼接消息内容
			tTaskPushInfo.setNoteId(noteId);
			tTaskPushInfo.setClientNoteId(clientNoteId);
			tTaskPushInfo.setModeId(modeId);
			tTaskPushInfo.setPushObjectNo(agentNo);
			tTaskPushInfo.setPushStatus(Constant.push_status_unsend);
			tTaskPushInfo.setPushDesc(pushDesc);
			tTaskPushInfo.setCreatedDate(new Date());
			
			startTime=System.currentTimeMillis();
			agentDao.insertTTaskPushInfo(tTaskPushInfo);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+noteId,"插入消息耗时"+(endTime-startTime)+"ms");
					
	 }
}
