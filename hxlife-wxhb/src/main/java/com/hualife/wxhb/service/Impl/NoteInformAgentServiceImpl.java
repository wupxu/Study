package com.hualife.wxhb.service.Impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.security.CryptoUtil;
import com.hualife.mesiframework.restclient.RestClient;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.entity.TTaskPushAgentInfo;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.service.NoteInformAgentService;
/**
 * @author zhangweiwei
 * @description 查看要推送的消息信息impl
 * @date 2017-08-04
 */
@Service
public class NoteInformAgentServiceImpl implements NoteInformAgentService{
	
	private final Logger logger = LoggerFactory.getLogger(NoteInformAgentServiceImpl.class);

	@Autowired
	private AgentDao agentDao;
	@Autowired
	private RestClient  restClient;
	/**
	 * 查看要推送的消息信息
	 */
	@Override
	public void saveNoteInformAgent() {
		logger.debug("查看要推送的消息信息");
		String pushStatus=Constant.push_status_success;
		List<TTaskPushAgentInfo> taskPushInfoList=agentDao.getTaskPushInfo(pushStatus);
			List<TTaskPushAgentInfo> pushResultList = new ArrayList<>();
			for(TTaskPushAgentInfo tTaskPushAgentInfo:taskPushInfoList){
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+tTaskPushAgentInfo.getNoteId()+"具体函件id"+tTaskPushAgentInfo.getClientNoteId(), "开始调用消息接口");
					String url = Constant.enterprise_SEND_MESSAGE_URL;
					String data = getData(tTaskPushAgentInfo.getPushObjectNo(),tTaskPushAgentInfo.getPushDesc());
					
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id"+tTaskPushAgentInfo.getNoteId()+"具体函件id"+tTaskPushAgentInfo.getClientNoteId(), "获取调用结果");
					String result = restClient.postForSimple(url, getPushMapMsg(data), null);
					// 校验推送成功与否
					if (result != null && result.contains(Constant.push_status)) {
						// 设定更新推送状态字段
						tTaskPushAgentInfo.setPushStatus(Constant.push_status_success);
					}else {
						tTaskPushAgentInfo.setPushStatus(Constant.push_status_failed);
					}
					tTaskPushAgentInfo.setPushStatus(Constant.push_status_success);
					pushResultList.add(tTaskPushAgentInfo);
				}
			if (pushResultList.size()> 0){
				agentDao.batchUpdateTTaskPushAgentInfo(pushResultList);
			}
		}
	
	/**
	 * 获取消息内容
	 */
	public String getData(String touserId, String desc) {
		JSONObject data = new JSONObject();
		data.put("touser",touserId);
		data.put("toparty", "");
		data.put("totag", "");
		data.put("agentid", "0");
		data.put("msgtype", "text");
		JSONObject textJson= new JSONObject();
		textJson.put("content", desc);
		data.put("text", textJson);
		return data.toJSONString();
	}
	
	/**
	 * 拼装企业号报文参数
	 * 
	 * @param data 推送人员信息
	 * @return 企业号请求报文参数map
	 */
	public Map<String,String> getPushMapMsg(String data) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String timestamp = "" + System.currentTimeMillis();
		String trade_source = Constant.enterprise_QUERY_USER_INFOR_TRADE_SOURCE;
		String nonce = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		String KEY = Constant.enterprise_QUERY_USER_INFOR_KEY;
		
		String secryptStr = KEY+ timestamp + nonce + trade_source + data;
		String signature = CryptoUtil.MD5(secryptStr);
		paramMap.put("timestamp", timestamp);
		paramMap.put("trade_source", trade_source);
		paramMap.put("nonce", nonce);
		paramMap.put("signature", signature);
		paramMap.put("data", data);
		
		return paramMap;
	}
}
