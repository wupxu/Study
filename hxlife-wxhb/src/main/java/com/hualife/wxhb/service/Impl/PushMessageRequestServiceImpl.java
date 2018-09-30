package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.dto.PushMessage;
import com.hualife.wxhb.domain.dto.PushMotion;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.integration.soap.UWTrans0237;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBody;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyMotion;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyMotions;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyProblemInfo;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyProblemInfos;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyProblemObject;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyProblemObjects;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodySurivivalInfo;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodySurivivalInfos;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyUndwetInfoInfo;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageBodyUndwetInfos;
import com.hualife.wxhb.integration.soap.message.request.pushMessage.PushMessageRequestMessage;
import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseMessage;
import com.hualife.wxhb.service.CreateCBSWebserviceHead;
import com.hualife.wxhb.service.PushMessageRequestService;
import com.hualife.wxhb.service.PushMessageResponseService;

/**
 * @author 吴培旭
 * @description 批处理推送函件信息的接口
 * @time 创建时间：2017年8月22日
 */
@Service
public class PushMessageRequestServiceImpl implements PushMessageRequestService {
	private final Logger logger = LoggerFactory.getLogger(PushMessageRequestServiceImpl.class);
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private UndwrtDao undwrtDao;
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private CreateCBSWebserviceHead createCBSWebserviceHead;
	@Autowired
	private UWTrans0237 uwTrans0237;
	@Autowired
	private PushMessageResponseService pushMessageResponseService;

	@Override
	public void PushMessageBodyAndHead() {
		// 组织报文
		PushMessageRequestMessage createPushMessage = createPushMessage();
		// 调用核心接口
		PushMessageResponseMessage pushMessageResponseMessage = pushRequestMessage(createPushMessage);
		// 返回报文处理
		pushMessageResponseService.handlMessage(pushMessageResponseMessage.getBody());
	}

	// 批处理推送函件查询PushMessageBodyProblemObjects
	private PushMessageBodyProblemObjects findPushMessageBodyProblemObjects(Map<Object, Object> pushMap) {
		PushMessageBodyProblemObjects pushMessageBodyProblemObjects = new PushMessageBodyProblemObjects();
		long startTime = 0;
		long endTime = 0;
		List<PushMessageBodyProblemObject> selProblemObjectDetailsList = new ArrayList<>();
		// 批处理推送函件查询对应问题和问题回答集合
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"批处理推送函件查询对应问题和问题回答集合");
		startTime = System.currentTimeMillis();
		selProblemObjectDetailsList = problemDao.getproblemObjectDetails(pushMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				"批处理推送函件查询对应问题和问题回答和对象集合", "程序运行时间： " + (endTime - startTime) + "ms");
		Integer detailsSize = selProblemObjectDetailsList.size();
		pushMessageBodyProblemObjects.setCount(detailsSize.toString());

		pushMessageBodyProblemObjects.setProblemObjectList(selProblemObjectDetailsList);
		return pushMessageBodyProblemObjects;
	}

	// 调用核心接口
	private PushMessageResponseMessage pushRequestMessage(PushMessageRequestMessage pushMessageRequestMessage) {
		String pushMessageRequestMessageXml = XmlMapper.toXml(pushMessageRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"批处理推送函件信息推送xml报文"+pushMessageRequestMessageXml);
		String uWTrans02372 = uwTrans0237.uWTrans0237(pushMessageRequestMessageXml);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"批处理推送函件信息返回xml报文"+uWTrans02372);
		PushMessageResponseMessage pushMessageResponseMessage = XmlMapper.fromXml(uWTrans02372,
				PushMessageResponseMessage.class);
		return pushMessageResponseMessage;
	}

	// 组织报文
	private PushMessageRequestMessage createPushMessage() {
		// 声明输入报文
		PushMessageRequestMessage pushMessageRequestMessage = new PushMessageRequestMessage();
		PushMessageBody pushMessageBody = new PushMessageBody();
		// 声明问题件
		PushMessageBodyProblemInfos pushMessageBodyProblemInfos = new PushMessageBodyProblemInfos();
		List<PushMessageBodyProblemInfo> problemInfoList = new ArrayList<>();

		// 声明核保函
		PushMessageBodyUndwetInfos pushMessageBodyUndwetInfos = new PushMessageBodyUndwetInfos();
		List<PushMessageBodyUndwetInfoInfo> undList = new ArrayList<>();
		// 声明契调函
		PushMessageBodySurivivalInfos pushMessageBodySurivivalInfos = new PushMessageBodySurivivalInfos();
		List<PushMessageBodySurivivalInfo> surList = new ArrayList<>();
		// 其他函件
		PushMessageBodyMotions pushMessageBodyMotions = new PushMessageBodyMotions();
		List<PushMessageBodyMotion> motionList = new ArrayList<>();

		long startTime = 0;
		long endTime = 0;
		RequestHead requestHead = new RequestHead();
		requestHead = createCBSWebserviceHead.createCBSWebserviceHead(Constant.serviceid_NOTEMESSAGEPUSH);
		String pushStatusUnsend = Constant.push_status_unsend;
		String pushStatusFailed = Constant.push_status_failed;
		Map<Object, Object> mMap = new HashMap<>();
		mMap.put("pushStatusUnsend", pushStatusUnsend);
		mMap.put("pushStatusFailed", pushStatusFailed);
		// 批处理推送函件查询中间表所有符合条件的note_id和note_type
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"批处理推送函件查询中间表所有符合条件的note_id和note_type");
		startTime = System.currentTimeMillis();
		List<PushMessage> pushMessagesList = clientDao.getPushNoteId(mMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
				"批处理推送函件查询中间表所有符合条件的note_id和note_type", "程序运行时间： " + (endTime - startTime) + "ms");
		for (PushMessage pushMessage : pushMessagesList) {
			String noteId = pushMessage.getNoteId();
			String noteType = pushMessage.getNoteType();
			Map<Object, Object> MpushMap = new HashMap<Object, Object>();
			MpushMap.put("noteId", noteId);
			// 数码转换
			if (Constant.note_from_core_type_FINAOCCU.equals(noteType)
					|| Constant.note_from_core_type_PHYSICAL.equals(noteType)
					|| Constant.note_from_core_type_HEALTH.equals(noteType)
					|| Constant.note_from_core_type_SURVIVAL.equals(noteType)) {
				MpushMap.put("MnoteType", Constant.note_type_CLIENT);
			}
			if (Constant.note_from_core_type_PROBLEM.equals(noteType)) {
				MpushMap.put("MnoteType", Constant.note_type_PROBLEM);
			}
			if (Constant.note_from_core_type_UNDWRT.equals(noteType)) {
				MpushMap.put("MnoteType", Constant.note_type_UNDWRT);
			}
			// 批处理推送函件查询核保任务号task_code
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"批处理推送函件查询核保任务号task_code");
			startTime = System.currentTimeMillis();
			String taskcode = clientDao.getTaskcode(MpushMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					"批处理推送函件查询核保任务号task_code", "程序运行时间： " + (endTime - startTime) + "ms");
			Map<Object, Object> pushMap = new HashMap<Object, Object>();
			pushMap.put("noteId", noteId);
			pushMap.put("noteType", noteType);
			// 如果函件类型为问题件
			if (Constant.note_from_core_type_PROBLEM.equals(noteType)) {
				PushMessageBodyProblemInfo pushMessageBodyProblemInfo = new PushMessageBodyProblemInfo();
				PushMessage proPushMessage = new PushMessage();
				// 批处理推送函件查询核保任务序号 投保单号 是否为次品单
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(问题件)批处理推送函件查询核保任务序号 投保单号 是否为次品单");
				startTime = System.currentTimeMillis();
				proPushMessage = problemDao.getNoteSeqAndCode(pushMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(函件类型为问题件)批处理推送函件查询核保任务序号 投保单号 是否为次品单", "程序运行时间： " + (endTime - startTime) + "ms");
				String applyBarCode = proPushMessage.getApplyBarCode();
				String noteSeq = proPushMessage.getNoteSeq();
				String isNotQualifiedNote = proPushMessage.getIsNotQualifiedNote();
				String isSecondNote = proPushMessage.getIsSecondNote();
				// 组织报文PushMessageBodyProblemInfo
				PushMessageBodyProblemObjects pushMessageBodyProblemObjects = findPushMessageBodyProblemObjects(
						pushMap);
				if (Constant.is_not_second_note_Y.equals(isSecondNote)) {
					pushMessageBodyProblemInfo.setBuybackType(Constant.buyback_type_again);
				} else if (Constant.is_not_qualified_note_Y.equals(isNotQualifiedNote)) {
					pushMessageBodyProblemInfo.setBuybackType(Constant.buyback_type_defective_list);
				} else {
					pushMessageBodyProblemInfo.setBuybackType(Constant.buyback_type_ordinary);
				}
				pushMessageBodyProblemInfo.setTaskCode(taskcode);
				pushMessageBodyProblemInfo.setNoteSeq(noteSeq);
				pushMessageBodyProblemInfo.setApplyNo(applyBarCode);
				pushMessageBodyProblemInfo.setPushMessageBodyProblemObjects(pushMessageBodyProblemObjects);
				problemInfoList.add(pushMessageBodyProblemInfo);
			}
			// 如果函件类型为核保函
			else if (Constant.note_from_core_type_UNDWRT.equals(noteType)) {
				PushMessageBodyUndwetInfoInfo pushMessageBodyUndwetInfoInfo = new PushMessageBodyUndwetInfoInfo();
				// 查询核保任务序号和客户回复信息和投保单号
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(核保函)查询核保任务序号和客户回复信息和投保单号");
				startTime = System.currentTimeMillis();
				pushMessageBodyUndwetInfoInfo = undwrtDao.getUndwetInfoInfo(pushMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(函件类型为核保函)批处理推送函件查询核保任务序号和客户回复信息", "程序运行时间： " + (endTime - startTime) + "ms");
				pushMessageBodyUndwetInfoInfo.setTaskCode(taskcode);
				undList.add(pushMessageBodyUndwetInfoInfo);

			}
			// 如果为契调函
			else if (Constant.note_from_core_type_SURVIVAL.equals(noteType)) {
				pushMap.put("clientNoteType", Constant.client_type_SURVIVAL);
				PushMessageBodySurivivalInfo pushMessageBodySurivivalInfo = new PushMessageBodySurivivalInfo();
				// 查询核保任务序号
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(契调函)查询核保任务序号");
				startTime = System.currentTimeMillis();
				String surivivalNoteSeq = survivalDao.getSurivivalNoteSeq(pushMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(函件类型为契调函)批处理推送函件查询核保任务序号", "程序运行时间： " + (endTime - startTime) + "ms");
				// 查询当前契调人工号和契调报告
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(契调函)查询当前契调人工号和契调报告");
				startTime = System.currentTimeMillis();
				pushMessageBodySurivivalInfo = survivalDao.getMessageBodySurivivalInfo(pushMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(契调函)查询当前契调人工号和契调报告", "程序运行时间： " + (endTime - startTime) + "ms");
				pushMessageBodySurivivalInfo.setNoteSeq(surivivalNoteSeq);
				pushMessageBodySurivivalInfo.setTaskCode(taskcode);
				surList.add(pushMessageBodySurivivalInfo);
			}
			// 如果为其他函件
			else {
				PushMessageBodyMotion pushMessageBodyMotion = new PushMessageBodyMotion();
				PushMotion pushMotion = new PushMotion();
				// 批处理推送函件-查询其他函件的核保序号和函件类型 回销类型
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(其他函件)批处理推送函件-查询其他函件的核保序号和函件类型 回销类型");
				startTime = System.currentTimeMillis();
				pushMotion = clientDao.getPushMessageBodyMotion(pushMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(函件类型为其他函件)批处理推送函件查询其他函件的核保序号和函件类型 回销类型", "程序运行时间： " + (endTime - startTime) + "ms");
				String isNotQualifiedNote = pushMotion.getIsNotQualifiedNote();
				String isSecondNote = pushMotion.getIsSecondNote();
				String noteSeq = pushMotion.getNoteSeq();
				String noteMotionType = pushMotion.getNoteType();
				if (Constant.is_not_second_note_Y.equals(isSecondNote)) {
					pushMessageBodyMotion.setBuybackType(Constant.buyback_type_again);
				} else if (Constant.is_not_qualified_note_Y.equals(isNotQualifiedNote)) {
					pushMessageBodyMotion.setBuybackType(Constant.buyback_type_defective_list);
				} else {
					pushMessageBodyMotion.setBuybackType(Constant.buyback_type_ordinary);
				}
				pushMessageBodyMotion.setNote_seq(noteSeq);
				pushMessageBodyMotion.setNote_type(noteMotionType);
				pushMessageBodyMotion.setTaskCode(taskcode);
				motionList.add(pushMessageBodyMotion);
			}
		}
		// 问题件
		Integer InfosSize = problemInfoList.size();
		pushMessageBodyProblemInfos.setCount(InfosSize.toString());
		pushMessageBodyProblemInfos.setProblemInfoList(problemInfoList);
		// 核保函
		Integer UndwetInfoInfosSize = undList.size();
		pushMessageBodyUndwetInfos.setCount(UndwetInfoInfosSize.toString());
		pushMessageBodyUndwetInfos.setUndwetInfoInfoList(undList);

		// 契调函
		Integer SurivivalSize = surList.size();
		pushMessageBodySurivivalInfos.setCount(SurivivalSize.toString());
		pushMessageBodySurivivalInfos.setSurivivalInfoList(surList);
		// 其他类函件
		Integer motionsSize = motionList.size();
		pushMessageBodyMotions.setCount(motionsSize.toString());
		pushMessageBodyMotions.setMotionList(motionList);

		pushMessageBody.setProblemInfos(pushMessageBodyProblemInfos);
		pushMessageBody.setUndwetInfos(pushMessageBodyUndwetInfos);
		pushMessageBody.setSurivivalInfos(pushMessageBodySurivivalInfos);
		pushMessageBody.setMotions(pushMessageBodyMotions);
		pushMessageRequestMessage.setBody(pushMessageBody);
		pushMessageRequestMessage.setHead(requestHead);
		return pushMessageRequestMessage;
	}

}
