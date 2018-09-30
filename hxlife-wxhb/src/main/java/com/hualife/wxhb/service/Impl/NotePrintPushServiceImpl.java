package com.hualife.wxhb.service.Impl;

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
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.soap.UWTrans0233;
import com.hualife.wxhb.integration.soap.message.request.notePrintPush.NotePrintPushBody;
import com.hualife.wxhb.integration.soap.message.request.notePrintPush.NotePrintPushBodyNotes;
import com.hualife.wxhb.integration.soap.message.request.notePrintPush.NotePrintPushRequestMessage;
import com.hualife.wxhb.integration.soap.message.response.notePrintPush.NotePrintPushResponseMessage;
import com.hualife.wxhb.service.NotePrintResponseService;
import com.hualife.wxhb.service.CreateCBSWebserviceHead;
import com.hualife.wxhb.service.NotePrintPushService;

/**
 * @author 吴培旭
 * @description 批处理函件打印方式实现类
 * @time 创建时间：2017年8月18日
 */
@Service
public class NotePrintPushServiceImpl implements NotePrintPushService {
	private final Logger logger = LoggerFactory.getLogger(NotePrintPushServiceImpl.class);
	@Autowired
	private AgentDao agentDao;
	@Autowired
	private UWTrans0233 uWTrans0233;
	@Autowired
	private NotePrintResponseService notePrintResponseService;
	@Autowired
	private CreateCBSWebserviceHead createCBSWebserviceHead;

	@Override
	public void dealNotePrintPush() {
		//组织请求报文
		NotePrintPushRequestMessage notePrintPushRequestMessage = createRequestMessage();
		//调用核心接口
		NotePrintPushResponseMessage  notePrintPushResponseMessage = pushRequestMessage(notePrintPushRequestMessage);
		//返回报文处理
		notePrintResponseService.setChooseTypeClientResponse(notePrintPushResponseMessage.getBody());
	}
    //组织请求报文
	private NotePrintPushRequestMessage createRequestMessage() {
		long startTime=0;
		long endTime=0;
		// 声明报文
		NotePrintPushRequestMessage notePrintPushRequestMessage = new NotePrintPushRequestMessage();
		RequestHead requestHead = new RequestHead();
		requestHead = createCBSWebserviceHead.createCBSWebserviceHead(Constant.serviceid_PRINT);
		NotePrintPushBody notePrintPushBody = new NotePrintPushBody();
		String pushStatusUnsend = Constant.push_status_unsend; 
		String pushStatusFailed = Constant.push_status_failed;
		Map<String, String> typeMap = new HashMap<>();
		typeMap.put("pushStatusUnsend", pushStatusUnsend);
		typeMap.put("pushStatusFailed", pushStatusFailed);
		// 通过查询中间表获取核心所需参数报文
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"批处理函件打印,通过查询中间表获取核心所需参数报文");
		startTime = System.currentTimeMillis(); 
		List<NotePrintPushBodyNotes> ChooseTypeClientBodysList = agentDao.findChooseTypeClientBodys(typeMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"批处理函件打印,通过查询中间表获取核心所需参数报文","程序运行时间： " + (endTime - startTime) + "ms");
		Integer chooseSize = ChooseTypeClientBodysList.size();
		// 组织报文
		notePrintPushBody.setCount(chooseSize.toString());
		notePrintPushBody.setNotes(ChooseTypeClientBodysList);
		notePrintPushRequestMessage.setBody(notePrintPushBody);
		notePrintPushRequestMessage.setHead(requestHead);
		return notePrintPushRequestMessage;
	}
    //调用核心接口
	private NotePrintPushResponseMessage pushRequestMessage(NotePrintPushRequestMessage notePrintPushRequestMessage) {
		NotePrintPushResponseMessage notePrintPushResponseMessage = new NotePrintPushResponseMessage();
		String notePrintPushRequestMessageXml = XmlMapper.toXml(notePrintPushRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"函件打印xml报文"+notePrintPushRequestMessageXml);
		String uWTrans02332 = uWTrans0233.uWTrans0233(notePrintPushRequestMessageXml);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"函件打印返回xml报文"+uWTrans02332);
		notePrintPushResponseMessage = XmlMapper.fromXml(uWTrans02332, NotePrintPushResponseMessage.class);
		return notePrintPushResponseMessage;
	}
}
