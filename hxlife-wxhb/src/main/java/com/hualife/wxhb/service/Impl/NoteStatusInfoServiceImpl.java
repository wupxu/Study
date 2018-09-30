package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.NoteInfo;
import com.hualife.wxhb.api.rest.message.request.NoteStatusInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteStatusInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.service.NoteStatusInfoService;

/**
 * @author zhangweiwei
 * @description 查看代理人函件状态impl
 * @date 2017-08-04
 */
@Service
public class  NoteStatusInfoServiceImpl implements NoteStatusInfoService{
	
	private final Logger logger = LoggerFactory.getLogger(NoteStatusInfoServiceImpl.class);
	
	@Autowired
	private AgentDao agentDao;

	/**
	 * 查看代理人函件状态具体逻辑方法，获取返回报文
	 * **/
	public  NoteStatusInfoResponseMessage getNoteStatus(NoteStatusInfoRequestMessage noteStatusInfoRequestMessage){
		
		//检查请求报文
		checkData(noteStatusInfoRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteStatusInfoRequestMessage.getNote_id(), "开始组织返回报文");
		long startTime =0;
		long endTime =0;
		//初始化返回报文
		String noteId=noteStatusInfoRequestMessage.getNote_id();
		NoteStatusInfoResponseMessage noteStatusInfoResponseMessage = new NoteStatusInfoResponseMessage();
		noteStatusInfoResponseMessage.setAppid(Constant.appid);
		
		List<NoteInfo> noteInfoList=new ArrayList<NoteInfo>();
	
		//获取函件主表信息
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "开始获取报文信息");
		startTime=System.currentTimeMillis();
		TNoteMain tNoteMain = agentDao.getNoteMainInfoByNoteid(noteId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"获取函件主表信息耗时"+(endTime-startTime)+"ms");

		if(tNoteMain!=null){
			noteStatusInfoResponseMessage.setNote_id(tNoteMain.getNoteId());
			noteStatusInfoResponseMessage.setClientName(tNoteMain.getClientName());
			noteStatusInfoResponseMessage.setAgentName(tNoteMain.getAgentName());
			noteStatusInfoResponseMessage.setBranch_name(tNoteMain.getBranchName());
			noteStatusInfoResponseMessage.setSendType(tNoteMain.getPushType());
			if(Constant.note_status_UNPUSH.equals(tNoteMain.getNoteStatus())){
				noteStatusInfoResponseMessage.setIs_transpond_to_client(Constant.result_FALSE);
			}else{
				noteStatusInfoResponseMessage.setIs_transpond_to_client(Constant.result_TRUE);
			}
		}else{
			throw new BusinessException("此函件id"+noteStatusInfoRequestMessage.getNote_id()+"不存在!");
		}
		//查询客户层函件状态信息
		HashMap<String,Object> clientNoteMap = new HashMap<>();
		clientNoteMap.put("note_id", noteId);
		startTime=System.currentTimeMillis();
		List<TNoteClientNote> tNoteClientNoteList=agentDao.getClientNoteStatusByNoteId(clientNoteMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询客户层函件状态信息耗时"+(endTime-startTime)+"ms");

		if(tNoteClientNoteList.size()>0){
			for(TNoteClientNote tNoteClientNote:tNoteClientNoteList){
				NoteInfo noteInfo = new NoteInfo();
				noteInfo.setClient_note_id(tNoteClientNote.getClientNoteId());
				noteInfo.setNoteType(tNoteClientNote.getNoteType());
				noteInfo.setNoteTypeDesc(DataConVersion.dataConVersion(Constant.transfer_data_NOTE_TYPE,tNoteClientNote.getNoteType()));
				noteInfo.setNote_agent_status(tNoteClientNote.getNoteAgentStatus());
				noteInfo.setNote_agent_status_desc(DataConVersion.dataConVersion(Constant.transfer_data_NOTE_AGENT_STATUS,tNoteClientNote.getNoteAgentStatus()));
				noteInfo.setNot_qualified_reason(tNoteClientNote.getNotQualifiedReason());
				noteInfoList.add(noteInfo);
			}
			noteStatusInfoResponseMessage.setNoteInfos(noteInfoList);
		}else{
			throw new BusinessException("此函件id"+noteId+"下不存在任何客户层函件！");
		}
		
		//获取该客户层函件高额业务员报告书的信息
		HashMap<String,Object> agentReportInfoMap = new HashMap<>();
		agentReportInfoMap.put("note_type", Constant.client_type_FINA);
		agentReportInfoMap.put("note_id",noteId);
		startTime=System.currentTimeMillis();
		TNoteClientNote tNoteClientNote =agentDao.getAgentReportInfoByNoteId(agentReportInfoMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"获取该客户层函件高额业务员报告书的信息耗时"+(endTime-startTime)+"ms");

		if(tNoteClientNote!=null){
			if(StringUtils.isNotBlank(tNoteClientNote.getIsAgentReport())&&Constant.valid_Y.equals(tNoteClientNote.getIsAgentReport())){
				noteStatusInfoResponseMessage.setIs_agent_report(Constant.result_TRUE);
			}else{
				noteStatusInfoResponseMessage.setIs_agent_report(Constant.result_FALSE);
			}
			if(StringUtils.isNotBlank(tNoteClientNote.getFinishAgentReport())){
				noteStatusInfoResponseMessage.setIs_finish_agent_report(Constant.result_TRUE);
			}else{
				noteStatusInfoResponseMessage.setIs_finish_agent_report(Constant.result_FALSE);
			}
		}else{
			noteStatusInfoResponseMessage.setIs_agent_report(Constant.result_FALSE);
			noteStatusInfoResponseMessage.setIs_finish_agent_report(Constant.result_FALSE);
		}

		return noteStatusInfoResponseMessage;
	}
	
	/**
	 * 校验请求报文
	 * **/
	private void checkData(NoteStatusInfoRequestMessage noteStatusInfoRequestMessage) {
		if(noteStatusInfoRequestMessage==null){
			throw new BusinessException(Constant.error_DESC_BODY_NULL);
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteStatusInfoRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(noteStatusInfoRequestMessage.getNote_id(), "函件id不能为空");
	}
	
}
