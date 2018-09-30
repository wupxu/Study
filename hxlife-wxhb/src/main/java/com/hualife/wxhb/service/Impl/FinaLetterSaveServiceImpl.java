package com.hualife.wxhb.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.request.FinaLetterSaveRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.FinaDao;
import com.hualife.wxhb.service.FinaLetterSaveService;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.PushMessageService;
import com.hualife.wxhb.service.TaskImageService;
import com.hualife.wxhb.service.TaskPushInfoService;
import com.hualife.wxhb.service.UpdateMainStatusService;
/**
 * @description : 客户/代理人提交财务函函信息（两个接口合二为一）
 * @author : linyongtao
 * @date : 2017-08-04 
 */
@Service
public class FinaLetterSaveServiceImpl implements FinaLetterSaveService{
	private final Logger logger = LoggerFactory.getLogger(FinaLetterSaveServiceImpl.class);
	@Autowired
	private FinaDao finaDao;	
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private UpdateMainStatusService updateMainStatusService;
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private TaskPushInfoService  taskPushInfoService;
	@Autowired
	private TaskImageService taskImageService;
	@Autowired
	private PushMessageService pushMessageService;
	long startTime = 0;
	long endTime =0;	
	/**
	 * @description :财务函保存
	 * @author : linyongtao
	 * @date : 2017-08-04
	 * **/
	@Override
	@Transactional
	public void finaLetterSave(FinaLetterSaveRequestMessage finaLetterSaveRequestMessage) {
		checkData(finaLetterSaveRequestMessage);
		//获取请求参数 上传人员--是客户上传还是代理人上传,finaNoteId,finaChooseType--资料上传方式（自己上传/转交代理人）
		String finaSubmitType = finaLetterSaveRequestMessage.getFina_submit_type();
		String finaNoteId = finaLetterSaveRequestMessage.getFina_note_id();
		String finaChooseType = finaLetterSaveRequestMessage.getFina_choose_type();
		String clientNoteID =finaNoteId;
		String noteId = clientDao.getNoteIdByClientNoteId(clientNoteID);
		
		//查询前端传入的参数是否存在
		Long number = clientDao.selectExitByClientNoteId(finaNoteId);
		if(number==0){
			throw new BusinessException("财务函提交接口,前端传入的参数Fina_note_id:"+finaNoteId+"错误!");
		}		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaNoteId, "获取请求参数");
		//客户在触发提交操作
		if(finaSubmitType.equals(Constant.client_note_CLIENT_SUBMID)){
			//根据资料的上传方式，自己上传-01-则将财务函状态置为已处理，如果是转交代理人-02-则将财务函状态置为授权代理
			clientSaveNote(finaChooseType,finaNoteId,noteId);
			//影像推送
			taskImageService.saveImageUpLoad(noteId, Constant.note_from_core_type_FINAOCCU,finaNoteId);
		}

		//代理人触发提交操作
		if(finaSubmitType.equals(Constant.client_note_AGENT_SUBMID)){
			//获取业务员说明备注---agent_remark_desc
			String agentRemarkDesc = finaLetterSaveRequestMessage.getAgent_remark_desc();
			agentSaveNote(noteId,finaChooseType,agentRemarkDesc,finaNoteId);
		}		
		//调用接口
		updateMainStatusService.updateMainStatusService(finaNoteId,Constant.note_status_DOWN);
	
		//往中间表存数据
		pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_FINAOCCU);
	}		
	/**
	 * 客户触发操作，提交财务函
	 * **/
	public void clientSaveNote(String finaChooseType,String finaNoteId,String noteId){
		Map<String , Object> finaMap = new HashMap<>();
		finaMap.put("clientNoteId", finaNoteId);
		if (finaChooseType.equals(Constant.client_note_client_choose_type_SELF_UPLOAD_IMAGE)) {
			//函件状态+客户看到的状态+代理人的状态，
			finaMap.put("noteStatus", Constant.fina_note_status_DOWN);				
			finaMap.put("noteStatusDesc", Constant.fina_note_status_DOWN_DESC);
			finaMap.put("noteClientStatus",Constant.note_client_status_FININSHED);
			finaMap.put("noteClientStatusDesc", Constant.note_client_status_FININSHED_DESC);//状态描述
			finaMap.put("noteAgentStatus", Constant.fina_note_status_DOWN);
			finaMap.put("noteAgentStatusDesc",Constant.fina_note_status_DOWN_DESC);
		}else if(finaChooseType.equals(Constant.client_note_client_choose_type_TRANSFER_TO_BRANCH)){
			//函件状态+客户看到的状态+代理人的状态，
			finaMap.put("noteStatus", Constant.fina_note_status_AUTHORIZATION);
			finaMap.put("noteStatusDesc",Constant.fina_note_status_AUTHORIZATION_DESC);
			finaMap.put("noteClientStatus",Constant.note_client_status_FININSHED);
			finaMap.put("noteClientStatusDesc", Constant.note_client_status_FININSHED_DESC);//状态描述
			finaMap.put("noteAgentStatus", Constant.note_agent_status_DEALING);
			finaMap.put("noteAgentStatusDesc",Constant.note_agent_status_DEALING_DESC);
			taskPushInfoService.saveAuthorizeAgentHandleNote(noteId, finaNoteId, Constant.client_type_FINA);
		}
			startTime =System.currentTimeMillis();
			finaDao.finaLetterSubmitClient(finaMap);	
			endTime =System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+finaNoteId,"客户选择上传财务函资料耗时"+(endTime-startTime)+"ms");
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+finaNoteId, "客户选择上传财务函资料");			
			
			//添加轨迹
			if(finaChooseType.equals(Constant.client_note_client_choose_type_SELF_UPLOAD_IMAGE)){
				noteTraceService.saveNoteTrace(noteId, finaNoteId, Constant.note_from_core_type_FINAOCCU, "财务函-已完成");
			}else if(finaChooseType.equals(Constant.client_note_client_choose_type_TRANSFER_TO_BRANCH)){
				noteTraceService.saveNoteTrace(noteId, finaNoteId, Constant.note_from_core_type_FINAOCCU, "财务函-处理函件");
			}					
	}
	/**
	 * 代理人触发操作，提交财务函
	 * **/
	public void agentSaveNote(String noteId,String finaChooseType,String agentRemarkDesc ,String finaNoteId){
		Map<String , Object> finaMap = new HashMap<>();
		finaMap.put("clientNoteId", finaNoteId);
		//代理人自己上传客户的财务函资料
		if(finaChooseType.equals(Constant.client_note_agent_choose_type_SELF_UPLOAD_IMAGE)){
			//函件的状态+代理人看到的状态+描述
			finaMap.put("noteStatus", Constant.fina_note_status_DOWN);
			finaMap.put("noteStatusDesc", Constant.fina_note_status_DOWN_DESC);
			finaMap.put("noteAgentStatus", Constant.note_agent_status_FINISH);
			finaMap.put("noteAgentStatusDesc", Constant.note_agent_status_FINISH_DESC);
			finaMap.put("agentRemarkDesc", agentRemarkDesc);
			//影像推送
			taskImageService.saveImageUpLoad(noteId, Constant.note_from_core_type_FINAOCCU,finaNoteId);
		//代理人选择将客户的财务函资料交给机构
		}else if(finaChooseType.equals(Constant.client_note_agent_choose_type_TRANSFER_TO_BRANCH)){
			//函件的状态+状态描述+代理人看到的状态+代理人描述备注
			finaMap.put("noteStatus", Constant.fina_note_status_LINEDOWN);
			finaMap.put("noteStatusDesc", Constant.fina_note_status_LINEDOWN_DESC);
			finaMap.put("noteAgentStatus", Constant.note_agent_status_FINISH);
			finaMap.put("noteAgentStatusDesc", Constant.note_agent_status_FINISH_DESC);
			finaMap.put("agentRemarkDesc", agentRemarkDesc);
		}
			startTime =System.currentTimeMillis();
			finaDao.finaLetterSubmitAgent(finaMap);
			endTime =System.currentTimeMillis();
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+finaNoteId, "代理人选择资料上传方式耗时:"+(endTime-startTime)+"ms");
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+finaNoteId, "代理人选择资料上传方式");
			
			//添加轨迹
			noteTraceService.saveNoteTrace(noteId, finaNoteId, Constant.note_from_core_type_FINAOCCU, "财务函-已完成");				
	}
	/**
	 * 校验请求报文
	 * **/
	private void checkData(FinaLetterSaveRequestMessage finaLetterSaveRequestMessage) {
		Assert.notNull(finaLetterSaveRequestMessage,Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaLetterSaveRequestMessage.getFina_note_id(), "开始检查请求报文");
		Assert.notEmpty(finaLetterSaveRequestMessage.getFina_note_id(), "函件id不能为空");
		Assert.notEmpty(finaLetterSaveRequestMessage.getFina_submit_type(), "函件提交类型--客户/代理人不能为空");
		Assert.notEmpty(finaLetterSaveRequestMessage.getFina_choose_type(), "资料上传方式不能为空");			
	}
}