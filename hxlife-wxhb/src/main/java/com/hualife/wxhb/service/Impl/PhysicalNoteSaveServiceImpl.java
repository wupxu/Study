package com.hualife.wxhb.service.Impl;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.request.PhysicalNoteSaveRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.PhysicalNoteSaveService;
import com.hualife.wxhb.service.PictureSynthesisServiceByIdService;
import com.hualife.wxhb.service.PushMessageService;
import com.hualife.wxhb.service.PushNotePrintService;
import com.hualife.wxhb.service.TaskImageService;
import com.hualife.wxhb.service.TaskPushInfoService;
import com.hualife.wxhb.service.UpdateMainStatusService;
/**
 * @author zhangweiwei
 * @deprecation 客户、代理人提交体检函impl
 * @date 2017-08-04
 */
@Service
public class  PhysicalNoteSaveServiceImpl implements PhysicalNoteSaveService{
	
	private final Logger logger = LoggerFactory.getLogger(PhysicalNoteSaveServiceImpl.class);

	@Autowired
	private PhysicalDao physicalDao;
	@Autowired
	private TaskPushInfoService taskPushInfoService;
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private PushMessageService pushMessageService;
	@Autowired 
	private UpdateMainStatusService updateMainStatusService;
	@Autowired
	private TaskImageService taskImageService; 
	@Autowired
	private PictureSynthesisServiceByIdService pictureSynthesisServiceByIdService;
	@Autowired
	private PushNotePrintService pushNotePrintService;
	
	/**
	 * 代理人、客户提交代理人
	 */
	@Override
	@Transactional
	public void updatePhysicalNote(PhysicalNoteSaveRequestMessage physicalNoteSaveRequestMessage) {
		
		//检查请求报文
		checkData(physicalNoteSaveRequestMessage);
		String physicalNoteId=physicalNoteSaveRequestMessage.getPhysical_note_id();
		String clientChooseType=physicalNoteSaveRequestMessage.getClient_choose_type();
		String agentChooseType=physicalNoteSaveRequestMessage.getAgent_choose_type();
		String agentRemarkDesc=physicalNoteSaveRequestMessage.getAgent_remark_desc();
		//体检函信息
		HashMap<String,Object> clientNoteMap=new HashMap<>();
		long startTime = 0;
		long endTime = 0;
		clientNoteMap.put("physicalNoteId", physicalNoteId);
		startTime=System.currentTimeMillis();
		TNoteClientNote tNoteClientNote=physicalDao.getClientNoteInfoByNoteId(clientNoteMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "根据体检函id获取函件信息--耗时"+(endTime-startTime)+"ms");
		if(tNoteClientNote!=null){
			if(!Constant.physical_or_health_note_status_DOWN.equals(tNoteClientNote.getNoteStatus())){
				updateNoteInfo(physicalNoteId,clientChooseType,agentChooseType,agentRemarkDesc);
			}
		}else{
			throw new BusinessException("体检函id为"+physicalNoteId+"的体检函不存在!");
		}
	}
	private void updateNoteInfo(String physicalNoteId,String clientChooseType,String agentChooseType,String agentRemarkDesc) {
		long startTime = 0;
		long endTime = 0;
		HashMap<String,Object> clientNoteMap=new HashMap<>();
		HashMap<String,Object> physicalNoteMap=new HashMap<>();
		//根据体检函id获取函件主表id
		startTime = System.currentTimeMillis();
		String noteId=physicalDao.getNoteId(physicalNoteId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "根据体检函id获取函件主表id--耗时"+(endTime-startTime)+"ms");
		
		//更新体检函信息
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "更新体检函函件信息");
		//推送消息用的入参
		HashMap<String,Object> taskPushInfoMap=new HashMap<>();
		if(StringUtils.isNotBlank(clientChooseType)&&StringUtils.isNotBlank(agentChooseType)){
			throw new BusinessException("此函件id"+physicalNoteId+"的体检函不能选择两种处理方式!");
		}else if(StringUtils.isNotBlank(clientChooseType)){
			physicalNoteMap.put("client_choose_type",clientChooseType);
			//自行体检
			if((Constant.physical_client_choose_type_WITHOUT_INSPECTION).equals(clientChooseType)){
				clientNoteMap.put("note_status", Constant.physical_or_health_note_status_DOWN);
				clientNoteMap.put("note_status_desc",Constant.physical_or_health_note_status_DOWN_DESC);
				clientNoteMap.put("note_client_status",Constant.note_agent_status_PRODUCTING);
				clientNoteMap.put("note_client_status_desc",Constant.note_agent_status_PRODUCTING_DESC);
				clientNoteMap.put("note_agent_status",Constant.note_agent_status_FINISH);
				clientNoteMap.put("note_agent_status_desc",Constant.note_agent_status_FINISH_DESC);
				//调用接口，插入一条轨迹
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，插入一条轨迹");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL,"已选择自行到指定机构进行体检体检方式");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL,"体检函已完成");
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向打印中间表推一条数据");
				pushNotePrintService.addTaskPushnotePrint(noteId, Constant.note_from_core_type_PHYSICAL);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，合成图片");
				pictureSynthesisServiceByIdService.pushPictureSynthesisServicePush(physicalNoteId);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向核心推送函件信息表存入一条数据");
				pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PHYSICAL);
			}
			//代理人陪同体检
			if((Constant.physical_client_choose_type_INSPECTION).equals(clientChooseType)){
				clientNoteMap.put("note_status", Constant.physical_or_health_note_status_DOWN);
				clientNoteMap.put("note_status_desc", Constant.physical_or_health_note_status_DOWN_DESC);
				clientNoteMap.put("note_client_status",Constant.note_client_status_FININSHED);
				clientNoteMap.put("note_client_status_desc",Constant.physical_or_health_note_status_DOWN_DESC);
				clientNoteMap.put("note_agent_status",Constant.note_agent_status_PRODUCTING);
				clientNoteMap.put("note_agent_status_desc",Constant.note_agent_status_PRODUCTING_DESC);
				//调用接口，往消息推送表存入数据
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向往消息推送表存入一条数据");
				taskPushInfoService.saveAuthorizeAgentHandleMedicalNotice(noteId,physicalNoteId);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向函件轨迹表插入数据");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId,  Constant.note_from_core_type_PHYSICAL,"已选择由服务专员协调体检事项体检方式");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL,"体检函系统函件生成中");
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向打印中间表推一条数据");
				pushNotePrintService.addTaskPushnotePrint(noteId, Constant.note_from_core_type_PHYSICAL);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，合成图片");
				pictureSynthesisServiceByIdService.pushPictureSynthesisServicePush(physicalNoteId);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向核心推送函件信息表存入一条数据");
				pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PHYSICAL);
				
			}
			//上传近一年的体检资料
			if((Constant.physical_client_choose_type_SELF_UPLOAD_IMAGE).equals(clientChooseType)){
				clientNoteMap.put("note_status",Constant.physical_or_health_note_status_DOWN);
				clientNoteMap.put("note_status_desc",Constant.physical_or_health_note_status_DOWN_DESC);
				clientNoteMap.put("note_client_status",Constant.note_client_status_FININSHED);
				clientNoteMap.put("note_client_status_desc",Constant.note_client_status_FININSHED_DESC);
				clientNoteMap.put("note_agent_status",Constant.note_agent_status_FINISH);
				clientNoteMap.put("note_agent_status_desc",Constant.note_agent_status_FINISH_DESC);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向函件轨迹表插入数据");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId,  Constant.note_from_core_type_PHYSICAL,"已选择上传最近一年内体检资料体检方式");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL,"体检函已完成");
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向核心推送函件信息表存入一条数据");
				pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PHYSICAL);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向影像系统信息表存入一条数据");
				taskImageService.saveImageUpLoad(noteId,Constant.note_from_core_type_PHYSICAL,physicalNoteId);
			}
			//授权给代理人处理
			if((Constant.physical_client_choose_type_TRANSFER_TO_AGENT).equals(clientChooseType)){
				clientNoteMap.put("note_status", Constant.physical_or_health_note_status_AUTHORIZATION);
				clientNoteMap.put("note_status_desc", Constant.physical_or_health_note_status_AUTHORIZATION_DESC);
				clientNoteMap.put("note_client_status",Constant.note_client_status_FININSHED);
				clientNoteMap.put("note_client_status_desc",Constant.note_client_status_FININSHED_DESC);
				clientNoteMap.put("note_agent_status",Constant.note_agent_status_DEALING);
				clientNoteMap.put("note_agent_status_desc",Constant.note_agent_status_DEALING_DESC);
				taskPushInfoMap.put("client_note_id",physicalNoteId);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向往消息推送表存入一条数据");
				taskPushInfoService.saveAuthorizeAgentHandleNote(noteId,physicalNoteId,Constant.client_type_PHYSICAL);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向函件轨迹表插入数据");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL, "已选择将纸质资料转交给服务专员处理体检方式");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL, "体检函处理函件");

			}
		}else if(StringUtils.isNotBlank(agentChooseType)){
			physicalNoteMap.put("agent_choose_type", agentChooseType);
			physicalNoteMap.put("agent_remark_desc", agentRemarkDesc);
			if((Constant.client_note_agent_choose_type_TRANSFER_TO_BRANCH).equals(agentChooseType)){
				clientNoteMap.put("note_status", Constant.physical_or_health_note_status_LINEDOWN);
				clientNoteMap.put("note_status_desc", Constant.physical_or_health_note_status_LINEDOWN_DESC);
				clientNoteMap.put("note_agent_status",Constant.note_agent_status_FINISH);
				clientNoteMap.put("note_agent_status_desc",Constant.note_agent_status_FINISH_DESC);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向函件轨迹表插入数据");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL, "体检函已完成");
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向核心推送函件信息表存入一条数据");
				pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PHYSICAL);
			}
			//代理人自己打印
			if((Constant.client_note_agent_choose_type_SELF_UPLOAD_IMAGE).equals(agentChooseType)){
				clientNoteMap.put("note_status", Constant.physical_or_health_note_status_DOWN);
				clientNoteMap.put("note_status_desc", Constant.physical_or_health_note_status_DOWN_DESC);
				clientNoteMap.put("note_agent_status",Constant.note_agent_status_FINISH);
				clientNoteMap.put("note_agent_status_desc",Constant.note_agent_status_FINISH_DESC);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向函件轨迹表插入数据");
				noteTraceService.saveNoteTrace(noteId, physicalNoteId, Constant.note_from_core_type_PHYSICAL, "体检函已完成");
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向影像系统信息表存入一条数据");
				taskImageService.saveImageUpLoad(noteId,Constant.note_from_core_type_PHYSICAL,physicalNoteId);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，向核心推送函件信息表存入一条数据");
				pushMessageService.pushMessageToTable(noteId, Constant.note_from_core_type_PHYSICAL);
			}
		}else{
			throw new BusinessException("此函件id"+physicalNoteId+"没有选择处理方式!");
		}
		physicalNoteMap.put("physical_note_id", physicalNoteId);
		clientNoteMap.put("client_note_id", physicalNoteId);
		//更新体检函信息
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，更新体检函信息");
		startTime = System.currentTimeMillis();
		physicalDao.updatePhysicalNote(physicalNoteMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "更新体检函信息--耗时"+(endTime-startTime)+"ms");
		//更新客户层函件状态信息
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，更新客户层函件状态");
		startTime = System.currentTimeMillis();
		physicalDao.updateClientNote(clientNoteMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "更新客户层函件状态信息--耗时"+(endTime-startTime)+"ms");
		//调用接口，更新函件主表状态
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "调用接口，更新函件主表状态");
		updateMainStatusService.updateMainStatusService(physicalNoteId,Constant.note_status_DOWN);		
	}
	/**
	 *检查请求报文
	 */
	private void checkData(PhysicalNoteSaveRequestMessage physicalNoteSaveRequestMessage) {
		
		Assert.notNull(physicalNoteSaveRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteSaveRequestMessage.getPhysical_note_id(), "开始检查请求报文");
		Assert.notEmpty(physicalNoteSaveRequestMessage.getPhysical_note_id(), "体检函id不能为空");
	}
}
