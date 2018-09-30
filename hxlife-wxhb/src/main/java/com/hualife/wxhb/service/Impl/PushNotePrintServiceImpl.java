package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.dto.PushnotePrint;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.soap.UWTrans0238;
import com.hualife.wxhb.integration.soap.message.request.chooseTypePush.ChooseTypePushRequestBody;
import com.hualife.wxhb.integration.soap.message.request.chooseTypePush.ChooseTypePushRequestBodyNoteClient;
import com.hualife.wxhb.integration.soap.message.request.chooseTypePush.ChooseTypePushRequestBodyNoteClients;
import com.hualife.wxhb.integration.soap.message.request.chooseTypePush.ChooseTypePushRequestMessage;
import com.hualife.wxhb.integration.soap.message.response.chooseTypePush.ChooseTypePushReponseBodyStatus;
import com.hualife.wxhb.integration.soap.message.response.chooseTypePush.ChooseTypePushReponseMessage;
import com.hualife.wxhb.service.CreateCBSWebserviceHead;
import com.hualife.wxhb.service.PushNotePrintService;

/**
 * @author 吴培旭
 * @description 推送接口
 * @time 创建时间：2017年8月29日
 */
@Service
public class PushNotePrintServiceImpl implements PushNotePrintService {
	private final Logger logger = LoggerFactory.getLogger(PushModeSelectionServiceImpl.class);
	@Autowired
	private AgentDao agentDao;
	@Autowired
	private CreateCBSWebserviceHead createCBSWebserviceHead;
	@Autowired
	private UWTrans0238 uWTrans0238;

	/**
	 * @author 吴培旭
	 * @description 其他类函件推送到t_task_push_note_print调用此接口
	 * @time 创建时间：2017年8月29日
	 */
	@Override
	public void addTaskPushnotePrint(String nodeId, String noteType) {
		long startTime = 0;// 开始时间
		long endTime = 0;// 结束时间
		Date date = new Date();
		String time = DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", date);
		Map<String, String> addMap = new HashMap<>();
		addMap.put("nodeId", nodeId);
		addMap.put("clientTypeSurvival", Constant.client_type_SURVIVAL);
		addMap.put("noteType", noteType);

		// 查询向函件推送状态表的的数据中的task_code note_type,note_seq集合
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询向函件推送状态表的的数据中的task_code note_type,note_seq集合");
		startTime = System.currentTimeMillis();
		List<PushnotePrint> pushnotePrintsList = agentDao.findNotePrintTableMessageByNoteId(addMap);// note_id,契调类型
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "noteid" + nodeId,
				"查询向函件推送状态表的的数据中的note_type,note_seq集合--耗时:" + (endTime - startTime) + "ms");
		for (PushnotePrint pushnotePrint : pushnotePrintsList) {
			pushnotePrint.setNotePushStatus(Constant.push_status_unsend);
			pushnotePrint.setCreateDate(time);
			pushnotePrint.setUpdateDate(time);
		}
		if (pushnotePrintsList.size() > 0) {
			// 批量插入到中间表t_task_push_note_print
			startTime = System.currentTimeMillis();
			agentDao.addTaskPushnotePrint(pushnotePrintsList);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					"noteid" + nodeId, "批量插入到中间表t_task_push_note_print--耗时:" + (endTime - startTime) + "ms");
		}

	}

	/**
	 * @author 吴培旭
	 * @description 函件下发方式为机构打印时实时向核心推送信息
	 * @time 创建时间：2017年8月29日
	 */
	@Override
	public void setChooseTypeRestClientBody(String nodeId) {
		// 组织报文
		ChooseTypePushRequestMessage chooseTypePushRequestMessage = createChooseTypePushRequestBody(nodeId);
		// 调用核心接口
		ChooseTypePushReponseMessage chooseTypePushReponseMessage = pushTypeRequestMessage(chooseTypePushRequestMessage);
		List<ChooseTypePushReponseBodyStatus> result = chooseTypePushReponseMessage.getBody().getResult();
		for(ChooseTypePushReponseBodyStatus chooseTypePushReponseBodyStatus : result){
			if (!Constant.webservice_returnCode_SUCC.equals(chooseTypePushReponseBodyStatus.getReturnCode())) {
				throw new BusinessException("推送信息失败，请重新选择");
			}
		}
	}

	// 组织报文
	private ChooseTypePushRequestMessage createChooseTypePushRequestBody(String nodeId) {
		long startTime = 0;// 开始时间
		long endTime = 0;// 结束时间
		// 声明报文
		ChooseTypePushRequestBody chooseTypePushRequestBody = new ChooseTypePushRequestBody();
		RequestHead requestHead = new RequestHead();
		requestHead = createCBSWebserviceHead.createCBSWebserviceHead(Constant.serviceid_CHOOSETYPEPUSH);
		ChooseTypePushRequestMessage chooseTypePushRequestMessage = new ChooseTypePushRequestMessage();

		Map<String, String> setMap = new HashMap<>();
		setMap.put("nodeId", nodeId);
		setMap.put("clientTypeSurvival", Constant.client_type_SURVIVAL);
		// 查询函件task_code
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询函件task_code");
		startTime = System.currentTimeMillis();
		String taskCode = agentDao.findNotePrintTableTaskCodeByNoteId(setMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "noteid" + nodeId,
				"查询函件task_code:" + (endTime - startTime) + "ms");
		// 查询向函件推送状态表的的数据中的note_type,note_seq集合
		List<ChooseTypePushRequestBodyNoteClient> chooseTypePushRequestBodyNoteClientList = agentDao
				.findNoteRestMessageByNoteId(setMap);
		// 组织报文
		chooseTypePushRequestBody.setTaskcode(taskCode);

		ChooseTypePushRequestBodyNoteClients Note_Clients = new ChooseTypePushRequestBodyNoteClients();
		Integer size = chooseTypePushRequestBodyNoteClientList.size();
		Note_Clients.setCount(size.toString());
		Note_Clients.setNote_client(chooseTypePushRequestBodyNoteClientList);
		chooseTypePushRequestBody.setNote_Clients(Note_Clients);

		chooseTypePushRequestMessage.setBody(chooseTypePushRequestBody);
		chooseTypePushRequestMessage.setHead(requestHead);
		return chooseTypePushRequestMessage;
	}

	// 调用核心接口
	private ChooseTypePushReponseMessage pushTypeRequestMessage(ChooseTypePushRequestMessage chooseTypePushRequestMessage) {
		String chooseTypePushRequestMessageXml = XmlMapper.toXml(chooseTypePushRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"函件下发方式为机构打印时实时向核心推送信息推送报文:"+chooseTypePushRequestMessageXml);
		String uWTrans02382 = uWTrans0238.uWTrans0238(chooseTypePushRequestMessageXml);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"函件下发方式为机构打印时实时向核心推送信息核心返回报文:"+uWTrans02382);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "返回报文",
				uWTrans02382);
		ChooseTypePushReponseMessage chooseTypePushReponseMessage = XmlMapper.fromXml(uWTrans02382, ChooseTypePushReponseMessage.class);
		return chooseTypePushReponseMessage;
	}

}
