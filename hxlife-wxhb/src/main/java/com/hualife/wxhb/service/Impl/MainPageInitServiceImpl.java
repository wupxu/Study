package com.hualife.wxhb.service.Impl; 

import java.util.ArrayList;
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
import com.hualife.wxhb.api.rest.message.pojo.AppliInfo;
import com.hualife.wxhb.api.rest.message.pojo.ClientNoteInfos;
import com.hualife.wxhb.api.rest.message.pojo.ClientNoteType;
import com.hualife.wxhb.api.rest.message.pojo.ProblemNoteInfos;
import com.hualife.wxhb.api.rest.message.pojo.UndwrtNoteInfo;
import com.hualife.wxhb.api.rest.message.request.MainPageInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.MainPageInitResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.domain.dto.SelectId;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.service.MainPageInitService;
/**
 * @deprecation : 业务待处理页面的实现类----主页面初始化的实现类serviceImpl
 * @author : linyongtao
 * @date : 2017-08-07  
 */
@Service
public class MainPageInitServiceImpl implements MainPageInitService{
	private final Logger logger = LoggerFactory.getLogger(MainPageInitServiceImpl.class);
	
	@Autowired
	private AgentDao agentDao;
	
	/**
	 * @deprecation :主页面初始化获取参数返回报文
	 * @author : linyongtao
	 * @date : 2017-08-07
	 * **/
	@Override
	public MainPageInitResponseMessage mainPageInfo(MainPageInitRequestMessage mainPageInitRequestMessage) {
		checkData(mainPageInitRequestMessage);
		long startTime =0;
		long endTime =0;
		//声明返回对象--MainPageInitResMessBody
		MainPageInitResponseMessage mainPageInitResponseMessage = new MainPageInitResponseMessage();
		
		//客户层函件部分
		List<ClientNoteInfos> clientNoteInfosList = new ArrayList<ClientNoteInfos>();
		//问题件函件部分
		List<ProblemNoteInfos> problemNoteInfosList = new ArrayList<ProblemNoteInfos>(); 
		//核保函函件
		List<UndwrtNoteInfo> undwrtNoteInfoList = new ArrayList<UndwrtNoteInfo>(); 
		
		//***处理客户层函件部分
		//获取请求参数代理人编号agent_no
		String agentNo = mainPageInitRequestMessage.getAgent_no();
		startTime = System.currentTimeMillis();
		Long number =agentDao.selectExitByAgentNo(agentNo);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "查询代理人工号是否存在--耗时:"+(endTime-startTime)+"ms");
		if(number==0){
			throw new BusinessException("主页面初始化接口,前端传入的参数代理人编号--agentNo:"+agentNo+"错误!");
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "获取请求参数的数据");		
		//查询该代理人下有多少客户层函件----一个note_id代表一个函件
		List<SelectId> clientSelectIdList = new ArrayList<SelectId>();
		
		Map<String , Object> clientNoteStatusMap = new HashMap<>();
		clientNoteStatusMap.put("agentNo", agentNo);
		clientNoteStatusMap.put("noteType",Constant.note_type_CLIENT);
		clientNoteStatusMap.put("noteStatusUNPUSH",Constant.note_status_UNPUSH);
		clientNoteStatusMap.put("noteStatusPUSHING",Constant.note_status_PUSHING);
		clientNoteStatusMap.put("noteStatusWAITINGPRINT",Constant.note_status_WAITINGPRINT);
		startTime = System.currentTimeMillis();
		clientSelectIdList = agentDao.selectNoteId(clientNoteStatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "业务待处理数据页面查询业务员下的note_id--耗时:"+(endTime-startTime)+"ms");

		//遍历客户层noteid，将noteid查询的结果集放入报文
		for (int i=0 ; i<clientSelectIdList.size(); i++) {
			//查询客户层函件每个note_id下对应的投保单信息
			ClientNoteInfos clientNoteInfos = new ClientNoteInfos();
			String noteId = clientSelectIdList.get(i).getNote_id();
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "查询该noteId下面的客户层函件的信息");		
			//通过noteId查询投保单信息
			List<AppliInfo> appliInfoList = new ArrayList<AppliInfo>();
			startTime = System.currentTimeMillis();
			appliInfoList = agentDao.mainAppliInfo(noteId);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "客户层函件:根据函件id查询该客户的保单信息--耗时:"+(endTime-startTime)+"ms");
			
			//将查询到的客户层其他信息，客户姓名，状态,函件下发方式等放到客户层对象中
			startTime = System.currentTimeMillis();
			clientNoteInfos = agentDao.mainPageClientInfo(noteId);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "客户层函件:根据函件id查询该客户姓名,状态,下发方式--耗时：:"+(endTime-startTime)+"ms");
			
			if(clientNoteInfos.getPush_type()==null){
				clientNoteInfos.setPush_type(Constant.note_push_type_NOSELECT_PRINT);
			}
			clientNoteInfos.setNote_status_desc(DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,clientNoteInfos.getNote_status()));

			
			//配置客户层函件--的状体reason和type两参数
			startTime = System.currentTimeMillis();
			List<ClientNoteType> clientNoteTypeList = agentDao.mainClientNoteType(noteId);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "客户层函件:根据函件id查询该函件类型及下发原因--耗时:"+(endTime-startTime)+"ms");
			for (ClientNoteType clientNoteType : clientNoteTypeList) {
				clientNoteType.setNote_type_desc(DataConVersion.dataConVersion(Constant.transfer_data_NOTE_TYPE,clientNoteType.getNote_type()));
			}
		
			//将NoteId查询的结果集放到List中
			clientNoteInfos.setAppliInfo(appliInfoList);
			clientNoteInfos.setClientNoteType(clientNoteTypeList);
			clientNoteInfos.setNote_id(noteId);
			clientNoteInfosList.add(clientNoteInfos);
		}					
		
		
		//****处理问题件部分
		//创建问题件对象将集合中的数据放到对象中，然后将对象add到返回的报文体rProblemNoteInfos中
		Map<Object , Object> problemNoteStatusMap = new HashMap<>();
		problemNoteStatusMap.put("agentNo",agentNo);
		problemNoteStatusMap.put("noteType",Constant.note_type_PROBLEM);
		problemNoteStatusMap.put("noteStatusUNPUSH",Constant.note_status_UNPUSH);
		problemNoteStatusMap.put("noteStatusPUSHING",Constant.note_status_PUSHING);
		problemNoteStatusMap.put("noteStatusNOTQUALIFIED",Constant.note_status_NOTQUALIFIED);	
		startTime = System.currentTimeMillis();
		problemNoteInfosList = agentDao.mainPageProblemInfo(problemNoteStatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "问题件层函件:根据函件代理人编号agent_no查询问题件信息--耗时:"+(endTime-startTime)+"ms");
		for (ProblemNoteInfos problemNoteInfos : problemNoteInfosList) {
			problemNoteInfos.setNote_status_desc(DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS, problemNoteInfos.getNote_status()));
		}
		
		//****查询核保函部分
		//声明集合，接受参数用
		//查询所有核保函件下的信息
		Map<Object , Object> undwrtInfoMap = new HashMap<>();
		undwrtInfoMap.put("agentNo",agentNo);
		undwrtInfoMap.put("noteType",Constant.note_type_UNDWRT);
		undwrtInfoMap.put("noteStatusUNPUSH",Constant.note_status_UNPUSH);
		undwrtInfoMap.put("noteStatusPUSHING",Constant.note_status_PUSHING);
		startTime = System.currentTimeMillis();
		undwrtNoteInfoList = agentDao.mainPageUndwrtInfo(undwrtInfoMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "核保函层函件:根据函件代理人编号agent_no查询核保函信息--耗时:"+(endTime-startTime)+"ms");
		for (UndwrtNoteInfo undwrtNoteInfo : undwrtNoteInfoList) {
			undwrtNoteInfo.setNote_status_desc(DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS, undwrtNoteInfo.getNote_status()));
		}
		
		
		//****将核保函，问题件，客户层函件数据存储到返回的报文体中			
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),agentNo, "组装返回报文");
		mainPageInitResponseMessage.setAppid(Constant.appid);
		mainPageInitResponseMessage.setClientNoteInfos(clientNoteInfosList);
		mainPageInitResponseMessage.setProblemNoteInfos(problemNoteInfosList);
		mainPageInitResponseMessage.setUndwrtNoteInfo(undwrtNoteInfoList);
		
		return mainPageInitResponseMessage;
	}	
	
	/**
	 * 校验请求报文
	 * **/
	private void checkData(MainPageInitRequestMessage mainPageInitRequestMessage) {
		Assert.notNull(mainPageInitRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),mainPageInitRequestMessage.getAgent_no(), "开始检查请求报文");
		Assert.notEmpty(mainPageInitRequestMessage.getAgent_no(), "代理人编号不能为空");		
	}	
}