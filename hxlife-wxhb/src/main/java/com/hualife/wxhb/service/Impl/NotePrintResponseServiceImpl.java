package com.hualife.wxhb.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.hualife.mesiframework.core.util.io.FTPClientUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.soap.message.response.notePrintPush.NotePrintPushResponseBody;
import com.hualife.wxhb.integration.soap.message.response.notePrintPush.NotePrintPushResponseBodyNote;
import com.hualife.wxhb.service.GetMaxNo;
import com.hualife.wxhb.service.NotePrintResponseService;
import com.hualife.wxhb.service.NoteTraceService;

/**
 * @author 吴培旭
 * @description 批处理函件打印返回报文后的逻辑处理实现类
 * @time 创建时间：2017年8月18日
 */
@Service
public class NotePrintResponseServiceImpl implements NotePrintResponseService {
	private final Logger logger = LoggerFactory.getLogger(NotePrintResponseServiceImpl.class);

	@Autowired
	private AgentDao agentDao;
	@Autowired
	private GetMaxNo getMaxNo;
	@Autowired
	private NoteTraceService noteTraceService;
	@Autowired
	private HealthDao healthDao;

	@Override
	@Transactional
	public void setChooseTypeClientResponse(NotePrintPushResponseBody notePrintPushResponseBody) {
		long startTime = 0;
		long endTime = 0;
		Date date = new Date();
		String time = DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", date);

		List<NotePrintPushResponseBodyNote> notesList = notePrintPushResponseBody.getNotes();

		List<Map<String, String>> printList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> urlList = new ArrayList<Map<String, String>>();
		for (NotePrintPushResponseBodyNote chooseTypeClientRespBodyNote : notesList) {

			String printBackResult = chooseTypeClientRespBodyNote.getPrintResult();
			String taskCode = chooseTypeClientRespBodyNote.getTaskcode();
			String noteSeq = chooseTypeClientRespBodyNote.getNote_seq();
			String noteType = chooseTypeClientRespBodyNote.getNote_type();
			String noteImageUrl = chooseTypeClientRespBodyNote.getNote_image_url();
			//截取核心返回路径‘/’后的路径
			if(!StringUtils.isEmpty(noteImageUrl)){
				noteImageUrl = noteImageUrl.substring(1);
			}
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"核心返回ftp路径为："+noteImageUrl);
			String printResult = "";
			if (StringUtils.isEmpty(printBackResult)) {
				throw new BusinessException("批处理函件打印返回结果为null或者空串");
			}
			if (printBackResult.equals(Constant.printResult_success)) {
				printResult = Constant.push_status_success;
			} else {
				printResult = Constant.push_status_failed;
			}
			Map<String, String> printMap = new HashMap<>();
			printMap.put("taskCode", taskCode);
			printMap.put("noteSeq", noteSeq);
			printMap.put("noteType", noteType);
			printMap.put("time", time);
			printMap.put("printResult", printResult);
			// 判断是否打印成功
			if (Constant.printResult_success.equals(printBackResult)) {
				// ftp下载图片
				FTPClientUtil ftp = new FTPClientUtil();
				String server = Constant.FTP_SERVER;
				Integer port = Constant.FTP_RPORT;
				String userName = Constant.FTP_USERNAME;
				String userPassword = Constant.FTP_PASSWORD;
				String pdfId = getMaxNo.getMaxNo();

				Date da = new Date();
				String dateStr = DateFormatUtil.formatDate("yyyy/MM/dd/", da);
				String localPath = Constant.PDF_LOCAL_PATH + "/HEXIN/"+pdfId+".pdf";
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"ftp本地路径为："+localPath);
				String ossPath = Constant.OSS_PDF_PATH+dateStr+pdfId+".pdf";
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"上传到oss路径为："+ossPath);
				try {
					ftp.connect(server, port, userName, userPassword);
					
					ftp.download(noteImageUrl, localPath, server, port, userName, userPassword);
					File input = new File(localPath);
					InputStream is = null;
					is = new FileInputStream(input);
					// 上传到oss
					OSSUtil.uploadFileByInputStream(is, ossPath);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				// 获取url
				String noteOssUrl = OSSUtil.getUrl(ossPath);
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"获取到的oss路径为："+noteOssUrl);
				Map<String, String> urlMap = new HashMap<>();
				urlMap.put("noteOssUrl", noteOssUrl);
				urlMap.put("noteImageUrl", noteImageUrl);
				urlMap.put("taskCode", taskCode);
				urlMap.put("noteSeq", noteSeq);
				urlMap.put("noteType", noteType);
				urlMap.put("noteAgentStatusLoading", Constant.note_agent_status_LOADING);
				urlMap.put("noteAgentStatusLoadingDesc", Constant.note_agent_status_LOADING_DESC);
				urlList.add(urlMap);
			}
			
			printList.add(printMap);
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"把oss路径和ftp路径,业务员查看状态更新到clent_note表操作开始");
		if (urlList.size()>0) {
			// 把oss路径和ftp路径,业务员查看状态更新到clent_note表
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"批处理函件打印返回报文后的逻辑处理,把oss路径和ftp路径(代理人查看为下载函件)更新到clent_note表");
			startTime = System.currentTimeMillis();
			agentDao.setClientUrlAndStatus(urlList);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					"批处理函件打印返回报文后的逻辑处理,把oss路径和ftp路径(代理人查看为下载函件)更新到clent_note表", "程序运行时间： " + (endTime - startTime) + "ms");
			//添加轨迹
			noteTrace(notesList);
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"批处理函件打印返回报文后的逻辑处理,把打印结果更新到t_task_push_note_print表操作开始");
		if (printList.size()>0) {
			// 把打印结果更新到t_task_push_note_print表
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"批处理函件打印返回报文后的逻辑处理,把打印结果更新到t_task_push_note_print表");
			startTime = System.currentTimeMillis();
			agentDao.setTTaskPushNotePrint(printList);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					"批处理函件打印返回报文后的逻辑处理,把打印结果更新到t_task_push_note_print表", "程序运行时间： " + (endTime - startTime) + "ms");
		}
	}

	// 添加轨迹
	private void noteTrace(List<NotePrintPushResponseBodyNote> nlist) {
		long startTime = 0;
		long endTime = 0;
		for (NotePrintPushResponseBodyNote notePrintPushResponseBodyNote : nlist) {
			String note_seq = notePrintPushResponseBodyNote.getNote_seq();
			String note_type = notePrintPushResponseBodyNote.getNote_type();
			String taskcode = notePrintPushResponseBodyNote.getTaskcode();
			Map<String, Object> nMap = new HashMap<>();
			nMap.put("taskcode", taskcode);
			nMap.put("note_seq", note_seq);
			if (Constant.note_from_core_type_HEALTH.equals(note_type)) {
				// 查询健康函客户层id
				nMap.put("note_type", Constant.client_type_HEALTH);
				nMap.put("noteStatus", Constant.physical_or_health_note_status_DOWN);
				// 获取客户层函件信息
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"获取客户层函件信息clientNoteId和noteId");
				startTime = System.currentTimeMillis();
				TNoteClientNote tNoteClientNote = healthDao.getClientNoteIdByBody(nMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"核保任务号：" + taskcode + " 函件类型：" + note_type + " 核保任务序号：" + note_seq,
						"查询健康函客户层id--耗时:" + (endTime - startTime) + "ms");
				if (tNoteClientNote != null) {
					String clientNoteId = tNoteClientNote.getClientNoteId();
					String noteId = tNoteClientNote.getNoteId();
					if (StringUtils.isNotBlank(clientNoteId) && StringUtils.isNotBlank(noteId)) {
						noteTraceService.saveNoteTrace(noteId, clientNoteId, note_type, "健康函已回销");
					}
					else{
						throw new BusinessException("此函件id"+clientNoteId+"的健康函不存在!");
					}
				}
				else{
					throw new BusinessException("核保任务号："+taskcode+" 函件类型："+note_type+" 核保任务序号："+note_seq+"对应的健康函不存在");
				}
			}
			if (Constant.note_from_core_type_PHYSICAL.equals(note_type)) {
				// 查询健康函客户层id
				nMap.put("note_type", Constant.client_type_PHYSICAL);
				nMap.put("noteStatus", Constant.physical_or_health_note_status_DOWN);
				// 获取客户层函件信息
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"获取客户层函件信息clientNoteId和noteId");
				startTime = System.currentTimeMillis();
				TNoteClientNote tNoteClientNote = healthDao.getClientNoteIdByBody(nMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"核保任务号：" + taskcode + " 函件类型：" + note_type + " 核保任务序号：" + note_seq,
						"查询体检函客户层id--耗时:" + (endTime - startTime) + "ms");
				if (tNoteClientNote != null) {
					String clientNoteId = tNoteClientNote.getClientNoteId();
					String noteId = tNoteClientNote.getNoteId();
					if (StringUtils.isNotBlank(clientNoteId) && StringUtils.isNotBlank(noteId)) {
						noteTraceService.saveNoteTrace(noteId, clientNoteId, note_type, "体检函已回销");
					}
					else{
						throw new BusinessException("此函件id"+clientNoteId+"的体检函不存在!");
					}
				}
				else{
					throw new BusinessException("核保任务号："+taskcode+" 函件类型："+note_type+" 核保任务序号："+note_seq+"对应的体检函不存在");
				}
			}
			
			if (Constant.note_from_core_type_FINAOCCU.equals(note_type)) {
				// 查询健康函客户层id
				nMap.put("note_type", Constant.client_type_FINA);
				nMap.put("noteStatus", Constant.fina_note_status_DOWN);
				// 获取客户层函件信息
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
						"获取客户层函件信息clientNoteId和noteId");
				startTime = System.currentTimeMillis();
				TNoteClientNote tNoteClientNote = healthDao.getClientNoteIdByBody(nMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
						"核保任务号：" + taskcode + " 函件类型：" + note_type + " 核保任务序号：" + note_seq,
						"查询健康函客户层id--耗时:" + (endTime - startTime) + "ms");
				if (tNoteClientNote != null) {
					String clientNoteId = tNoteClientNote.getClientNoteId();
					String noteId = tNoteClientNote.getNoteId();
					if (StringUtils.isNotBlank(clientNoteId) && StringUtils.isNotBlank(noteId)) {
						noteTraceService.saveNoteTrace(noteId, clientNoteId, note_type, "财务函已回销");
					}
					else{
						throw new BusinessException("此函件id"+clientNoteId+"的财务函不存在!");
					}
				}
				else{
					throw new BusinessException("核保任务号："+taskcode+" 函件类型："+note_type+" 核保任务序号："+note_seq+"对应的财务函不存在");
				}
			}
		}
	}

}
