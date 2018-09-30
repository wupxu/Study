package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseBody;
import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseBodyMotion;
import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseBodyProblemInfo;
import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseBodySurivivalInfo;
import com.hualife.wxhb.integration.soap.message.response.pushMessage.PushMessageResponseBodyUndwetInfoInfo;
import com.hualife.wxhb.service.PushMessageResponseService;
import com.hualife.wxhb.service.UpdateMainStatusService;

/**
 * @author 吴培旭
 * @description 批处理推送函件信息处理核心返回报文的接口
 * @time 创建时间：2017年8月31日
 */
@Service
public class PushMessageResponseServiceImpl implements PushMessageResponseService {
	private final Logger logger = LoggerFactory.getLogger(PushMessageResponseServiceImpl.class);
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private UndwrtDao undwrtDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private UpdateMainStatusService updateMainStatusService;

	@Override
	@Transactional
	public void handlMessage(PushMessageResponseBody pushResponseMessageBody) {
		long startTime = 0;
		long endTime = 0;
		// 问题件
		if (pushResponseMessageBody.getProblemInfos().getProblemInfoList().size() > 0) {
			List<Map<String, String>> prolist = new ArrayList<>();
			List<PushMessageResponseBodyProblemInfo> problemInfoList = pushResponseMessageBody.getProblemInfos()
					.getProblemInfoList();
			for (PushMessageResponseBodyProblemInfo pushResponseMessageBodyProblemInfo : problemInfoList) {
				String proTime = getDate();
				String noteFromCoreTypeProblem = Constant.note_from_core_type_PROBLEM;
				String noteSeq = pushResponseMessageBodyProblemInfo.getNoteSeq();
				String proBackResultType = pushResponseMessageBodyProblemInfo.getResultType();
				String pTaskCode = pushResponseMessageBodyProblemInfo.getTaskCode();
				String applyNo = pushResponseMessageBodyProblemInfo.getApplyNo();
				String resultType = "";
				if (StringUtils.isEmpty(proBackResultType)) {
					throw new BusinessException("批处理返回结果为null或者空串");
				}
				if (Constant.webservice_returnCode_SUCC.equals(proBackResultType)) {
					resultType = Constant.push_status_success;
				} else {
					resultType = Constant.push_status_failed;
				}

				Map<String, String> pMap = new HashMap<>();
				pMap.put("noteSeq", noteSeq);
				pMap.put("pTaskCode", pTaskCode);
				pMap.put("status", Constant.note_status_FINISHED);
				pMap.put("applyNo", applyNo);
				pMap.put("problemNoteStatusDown", Constant.problem_note_status_DOWN);
				// 批处理推送函件信息处理核心返回报文的接口 查询问题件对应note_id
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(问题件)批处理推送函件信息处理核心返回报文的接口 查询问题件对应note_id");
				startTime = System.currentTimeMillis();
				String proNoteId = problemDao.getProblemForNoteId(pMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(问题件)批处理推送函件信息处理核心返回报文 查询问题件对应note_id", "程序运行时间： " + (endTime - startTime) + "ms");
				Map<String, String> proMap = new HashMap<>();
				proMap.put("time", proTime);
				proMap.put("noteFromCoreTypeProblem", noteFromCoreTypeProblem);
				proMap.put("proNoteId", proNoteId);
				proMap.put("resultType", resultType);
				prolist.add(proMap);
			}
			if (prolist.size() > 0) {
				// 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(问题件)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态");
				startTime = System.currentTimeMillis();
				problemDao.updatePushInfoStatus(prolist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(问题件)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态",
						"程序运行时间： " + (endTime - startTime) + "ms");
			}

		}

		// 契调函
		if (pushResponseMessageBody.getSurivivalInfos().getSurivivalInfoList().size() > 0) {
			List<Map<String, String>> surlist = new ArrayList<>();
			List<Map<String, String>> csurlist = new ArrayList<>();

			List<PushMessageResponseBodySurivivalInfo> surivivalInfoList = pushResponseMessageBody.getSurivivalInfos()
					.getSurivivalInfoList();
			for (PushMessageResponseBodySurivivalInfo pushResponseMessageBodySurivivalInfo : surivivalInfoList) {
				String surTime = getDate();
				String noteFromCoreTypeSurvival = Constant.note_from_core_type_SURVIVAL;
				String surBackResultType = pushResponseMessageBodySurivivalInfo.getResultType();
				String taskCode = pushResponseMessageBodySurivivalInfo.getTaskCode();
				String surNoteSeq = pushResponseMessageBodySurivivalInfo.getNoteSeq();
				String surResultType = "";
				if (StringUtils.isEmpty(surBackResultType)) {
					throw new BusinessException("批处理返回结果为null或者空串");
				}
				if (Constant.webservice_returnCode_SUCC.equals(surBackResultType)) {
					surResultType = Constant.push_status_success;
				} else {
					surResultType = Constant.push_status_failed;
				}
				Map<String, String> sMap = new HashMap<>();
				sMap.put("taskCode", taskCode);
				sMap.put("surNoteSeq", surNoteSeq);
				sMap.put("validY", Constant.valid_Y);
				sMap.put("clientTypeSurvival", Constant.client_type_SURVIVAL);
				// 批处理推送函件信息处理核心返回报文的接口 查询契调函对应note_id
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(契调函)批处理推送函件信息处理核心返回报文 查询契调函对应note_id");
				startTime = System.currentTimeMillis();
				String surivivalForNoteId = survivalDao.getSurivivalForNoteId(sMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(契调函)批处理推送函件信息处理核心返回报文 查询契调函对应note_id", "程序运行时间： " + (endTime - startTime) + "ms");
				// 批处理推送函件信息处理核心返回报文的接口 查询client_note_id
				Map<String, String> cMap = new HashMap<>();
				cMap.put("surivivalForNoteId", surivivalForNoteId);
				cMap.put("clientTypeSurvival", Constant.client_type_SURVIVAL);
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(契调函)批处理推送函件信息处理核心返回报文  查询clientnoteid");
				startTime = System.currentTimeMillis();
				String surivivalForClientNoteId = survivalDao.getSurivivalForClientNoteId(sMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(契调函)批处理推送函件信息处理核心返回报文  查询clientnoteid", "程序运行时间： " + (endTime - startTime) + "ms");

				Map<String, String> surMap = new HashMap<>();
				surMap.put("surTime", surTime);
				surMap.put("noteFromCoreTypeSurvival", noteFromCoreTypeSurvival);
				surMap.put("surResultType", surResultType);
				surMap.put("surivivalForNoteId", surivivalForNoteId);
				surlist.add(surMap);
				Map<String, String> csurMap = new HashMap<>();
				csurMap.put("surivivalForClientNoteId", surivivalForClientNoteId);
				csurMap.put("survivalNoteStatusWriteoff", Constant.survival_note_status_WRITEOFF);
				csurMap.put("survivalNoteStatusWriteoffDesc", Constant.survival_note_status_WRITEOFF_DESC);
				if (Constant.webservice_returnCode_SUCC.equals(surBackResultType)) {
					csurlist.add(csurMap);
					// 判断其他函件状态 调用接口修改主表状态
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
							"(契调函)判断其他函件状态调用接口修改主表状态");
					updateMainStatusService.updateMainStatusService(surivivalForClientNoteId,
							Constant.note_status_FINISHED);
				}

			}
			if (csurlist.size() > 0) {
				// 批处理推送函件信息处理核心返回报文 在client表更新状态
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(契调函)批处理推送函件信息处理核心返回报文 更改client表状态为已回销");
				startTime = System.currentTimeMillis();
				survivalDao.updateClientStatus(csurlist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(契调函)批处理推送函件信息处理核心返回报文 更改client表状态为已回销", "程序运行时间： " + (endTime - startTime) + "ms");
			}
			if (surlist.size() > 0) {
				// 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(契调函)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态");
				startTime = System.currentTimeMillis();
				survivalDao.updatePushInfoStatus(surlist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(契调函)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态",
						"程序运行时间： " + (endTime - startTime) + "ms");
			}
		}
		// 核保函
		if (pushResponseMessageBody.getUndwetInfos().getUndwetInfoInfoList().size() > 0) {
			List<Map<String, String>> undlist = new ArrayList<>();
			List<Map<String, String>> cundlist = new ArrayList<>();
			List<PushMessageResponseBodyUndwetInfoInfo> undwetInfoInfoList = pushResponseMessageBody.getUndwetInfos()
					.getUndwetInfoInfoList();
			for (PushMessageResponseBodyUndwetInfoInfo pushResponseMessageBodyUndwetInfoInfo : undwetInfoInfoList) {
				String undTime = getDate();
				String noteFromCoreTypeUndwrt = Constant.note_from_core_type_UNDWRT;
				String undBackResultType = pushResponseMessageBodyUndwetInfoInfo.getResultType();
				String taskCode = pushResponseMessageBodyUndwetInfoInfo.getTaskCode();
				String uNoteSeq = pushResponseMessageBodyUndwetInfoInfo.getNoteSeq();
				String uApplyNo = pushResponseMessageBodyUndwetInfoInfo.getApplyNo();
				String undResultType = "";
				if (StringUtils.isEmpty(undBackResultType)) {
					throw new BusinessException("批处理返回结果为null或者空村串");
				}
				if (Constant.webservice_returnCode_SUCC.equals(undBackResultType)) {
					undResultType = Constant.push_status_success;
				} else {
					undResultType = Constant.push_status_failed;
				}
				Map<String, String> UMap = new HashMap<>();
				UMap.put("taskCode", taskCode);
				UMap.put("noteType", Constant.note_type_UNDWRT);
				UMap.put("uNoteSeq", uNoteSeq);
				UMap.put("uApplyNo", uApplyNo);
				// 批处理推送函件信息处理核心返回报文的接口 查询核保函对应note_id
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(核保函)批处理推送函件信息处理核心返回报文的接口 查询核保函对应note_id");
				startTime = System.currentTimeMillis();
				String undwetForNoteId = undwrtDao.getUndwetForNoteId(UMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(核保函)批处理推送函件信息处理核心返回报文的接口 查询核保函对应note_id", "程序运行时间： " + (endTime - startTime) + "ms");
				Map<String, String> undMap = new HashMap<>();
				undMap.put("undTime", undTime);
				undMap.put("noteFromCoreTypeUndwrt", noteFromCoreTypeUndwrt);
				undMap.put("undResultType", undResultType);
				undMap.put("undwetForNoteId", undwetForNoteId);
				undlist.add(undMap);
				if (Constant.webservice_returnCode_SUCC.equals(undBackResultType)) {
					Map<String, String> zMap = new HashMap<>();
					zMap.put("undwetForNoteId", undwetForNoteId);
					zMap.put("noteStatusFinished", Constant.note_status_FINISHED);
					cundlist.add(zMap);
				}
			}
			if (cundlist.size() > 0) {
				// 批处理推送函件信息处理核心返回报文的接口 成功则更改主表状态为已结束
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(核保函)批处理推送函件信息处理核心返回报文的接口 成功则更改主表状态为已结束");
				startTime = System.currentTimeMillis();
				undwrtDao.uMainNoteStatus(cundlist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(核保函)批处理推送函件信息处理核心返回报文的接口 成功则更改主表状态为已结束", "程序运行时间： " + (endTime - startTime) + "ms");
			}
			if (undlist.size() > 0) {
				// 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(核保函)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态");
				startTime = System.currentTimeMillis();
				undwrtDao.updatePushInfoStatus(undlist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(核保函)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态",
						"程序运行时间： " + (endTime - startTime) + "ms");
			}
		}
		// 其他类函件
		if (pushResponseMessageBody.getMotions().getMotionList().size() > 0) {
			List<Map<String, String>> motlist = new ArrayList<>();
			List<Map<String, String>> cmotlist = new ArrayList<>();
			List<PushMessageResponseBodyMotion> motionList = pushResponseMessageBody.getMotions().getMotionList();
			for (PushMessageResponseBodyMotion pushResponseMessageBodyMotion : motionList) {
				String motTime = getDate();
				String taskCode = pushResponseMessageBodyMotion.getTaskCode();
				String motionNoteType = pushResponseMessageBodyMotion.getNoteType();
				String motionNoteSeq = pushResponseMessageBodyMotion.getNoteSeq();
				String motionBackResultType = pushResponseMessageBodyMotion.getResultType();
				String motionResultType = "";
				if (StringUtils.isEmpty(motionBackResultType)) {
					throw new BusinessException("批处理返回结果为null或者空村串");
				}
				if (Constant.webservice_returnCode_SUCC.equals(motionBackResultType)) {
					motionResultType = Constant.push_status_success;
				} else {
					motionResultType = Constant.push_status_failed;
				}
				// 批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应note_id
				Map<String, String> mMap = new HashMap<>();
				mMap.put("taskCode", taskCode);
				mMap.put("motionNoteType", motionNoteType);
				mMap.put("motionNoteSeq", motionNoteSeq);
				mMap.put("validY", Constant.valid_Y);
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(其他类函件)批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应note_id");
				startTime = System.currentTimeMillis();
				String motionForNoteId = clientDao.getMotionForNoteId(mMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(其他类函件)批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应note_id", "程序运行时间： " + (endTime - startTime) + "ms");
				// 批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应client_note_id
				Map<String, String> mcMap = new HashMap<>();
				mcMap.put("motionForNoteId", motionForNoteId);
				mcMap.put("clientTypeFina", Constant.client_type_FINA);
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(其他类函件)批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应client_note_id");
				startTime = System.currentTimeMillis();
				String motionForClientNoteId = clientDao.getMotionForClientNoteId(mcMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(其他类函件)批处理推送函件信息处理核心返回报文的接口 查询其他类函件对应client_note_id",
						"程序运行时间： " + (endTime - startTime) + "ms");
				// 如果成功更改主表
				if (Constant.webservice_returnCode_SUCC.equals(motionBackResultType)
						&& Constant.note_from_core_type_FINAOCCU.equals(motionNoteType)) {
					Map<String, String> ucMap = new HashMap<>();
					ucMap.put("motionForClientNoteId", motionForClientNoteId);
					ucMap.put("finaNoteStatusWriteoff", Constant.fina_note_status_WRITEOFF);
					ucMap.put("finaNoteStatusWriteoffDesc", Constant.fina_note_status_WRITEOFF_DESC);
					cmotlist.add(ucMap);
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
							"如果成功更改主表");
					updateMainStatusService.updateMainStatusService(motionForClientNoteId,
							Constant.note_status_FINISHED);
				}
				Map<String, String> motMap = new HashMap<>();
				motMap.put("motTime", motTime);
				motMap.put("motionNoteType", motionNoteType);
				motMap.put("motionResultType", motionResultType);
				motMap.put("motionForNoteId", motionForNoteId);
				motlist.add(motMap);
			}
			if (cmotlist.size() > 0) {
				// 如果成功更改client表状态
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(其他类函件)如果成功client表状态");
				startTime = System.currentTimeMillis();
				clientDao.updateMationClientStatus(cmotlist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(其他类函件)如果成功client表状态", "程序运行时间： " + (endTime - startTime) + "ms");
			}
			if (motlist.size() > 0) {
				// 批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"(其他类函件)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态");
				startTime = System.currentTimeMillis();
				clientDao.updatePushInfoStatus(motlist);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"(其他类函件)批处理推送函件信息处理核心返回报文 在t_task_push_note_info变更推送状态",
						"程序运行时间： " + (endTime - startTime) + "ms");
			}
		}

	}

	// 获取时间
	private String getDate() {
		Date date = new Date();
		String time = DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", date);
		return time;
	}

}
