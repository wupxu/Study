package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContent;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContents;
import com.hualife.wxhb.api.rest.message.request.QuestionNaireSaveResquestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.FinaDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.GetMaxNo;
import com.hualife.wxhb.service.QuestionNaireSaveService;
/**
 * @description : 契调报告书和问卷实现类serviceImpl
 * @author linyongtao
 * @date : 2017.08.11
 */
@Service
public class QuestionNaireSaveServiceImpl  implements QuestionNaireSaveService{
	private final Logger logger = LoggerFactory.getLogger(QuestionNaireSaveServiceImpl.class);
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private FinaDao finaDao;
	@Autowired
	private AgentDao agentDao;
	@Autowired
	private HealthDao healthDao;
	@Autowired
	private GetMaxNo getMaxNo;
	long startTime = 0;
	long endTime =0;
	/**
	 * @description : 契调报告书和问卷提交保存
	 * @author linyongtao
	 * @date : 2017.08.11
	 * **/
	@Override
	@Transactional
	public void survivalQuesAns(QuestionNaireSaveResquestMessage questionNaireSaveResquestMessage) {
		checkData(questionNaireSaveResquestMessage);

		//声明参数接收前台的参数--note_type----根据此条件判断是哪个函件--体检函，健康函，财务函，契调函，高额业务员报告书
		String noteType = questionNaireSaveResquestMessage.getNote_type();
		String clientNoteId = questionNaireSaveResquestMessage.getClient_note_id();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId+"--函件类型:"+noteType, "获取请求参数");

		//声明集合接收前台传过来的参数---问题id+结果+结果描述
		List<ProblemContent> problemContentsList = new ArrayList<ProblemContent>();
		problemContentsList =questionNaireSaveResquestMessage.getProblemContent();
		
		//校验前端传入的客户层函件id是否存在
		Long number = clientDao.selectExitByClientNoteId(clientNoteId);
		if(number==0){
			throw new BusinessException("问卷和报告提交接口,前端传入的函件类型为:"+noteType+"的客户层函件id--clientNoteId:"+clientNoteId+"错误!");
		}
		//更改问卷完成状态的传参map
		Map<String, Object> updateStatusMap = new HashMap<>();
		
		//如果函件类型是--契调函--04	
		if(noteType.equals(Constant.client_type_SURVIVAL)){			
			dealSurvivalReport(clientNoteId,problemContentsList);
		}else 
		if(noteType.equals(Constant.note_agent_report_CLIENT)){//高额业务员报告书部分--05
			startTime = System.currentTimeMillis();
			Long contentsNum =agentDao.getAgentReportContents(clientNoteId);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId, "查询代理人是否已经填写的高额业务员报告--耗时:"+(endTime-startTime)+"ms");
			//判断代理人是否已提交过报告书，提交了就先删除,改状态在插入
			if(contentsNum>0){
				//删除代理人上传的业务员报告书
				startTime = System.currentTimeMillis();
				agentDao.deleteAgentReport(clientNoteId);
				endTime = System.currentTimeMillis();
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId+"--函件类型:"+noteType, "删除代理人填写的高额业务员报告");
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId, "删除代理人填写的高额业务员报告--耗时:"+(endTime-startTime)+"ms");
				//更改高额业务员报告书的完成状态
				startTime = System.currentTimeMillis();
				updateStatusMap.put("clientNoteId", clientNoteId);
				updateStatusMap.put("finishAgentReport",Constant.item_status_N);
				agentDao.updateAgentReportStatus(updateStatusMap);
				endTime = System.currentTimeMillis();
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId+"--函件类型:"+noteType, "更改高额业务员报告完成状态为未完成");
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId, "更改高额业务员报告完成状态为未完成--耗时:"+(endTime-startTime)+"ms");		
			}
			dealAgentReport(clientNoteId,problemContentsList);
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId+"--函件类型:"+noteType, "开始处理代理人填写的高额业务员报告");					
		}else		
			if(noteType.equals(Constant.client_type_FINA)){	//财务函问卷部分--01
			//获取财务问卷的子问卷id
			String noteItemId = questionNaireSaveResquestMessage.getNote_item_id();
			startTime = System.currentTimeMillis();
			Long contentsNum =finaDao.getFinaReportContents(noteItemId);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId, "查询客户是否已提交财务问卷--耗时:"+(endTime-startTime)+"ms");
			if(contentsNum>0){
				startTime = System.currentTimeMillis();
				finaDao.deleteFinaReportContents(noteItemId);
				endTime = System.currentTimeMillis();
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId+"--函件类型:"+noteType, "删除客户已提交财务问卷");
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId, "删除客户已提交财务问卷--耗时:"+(endTime-startTime)+"ms");
				//更改财务问卷的完成状态
				startTime = System.currentTimeMillis();
				updateStatusMap.put("clientNoteId", clientNoteId);
				updateStatusMap.put("noteItemId",noteItemId);
				updateStatusMap.put("noteItemStatus",Constant.item_status_N);
				finaDao.updateFinaReportStatus(updateStatusMap);
				endTime = System.currentTimeMillis();
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId+"--函件类型:"+noteType, "更改财务问卷的完成状态");
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId, "更改财务问卷的完成状态--耗时:"+(endTime-startTime)+"ms");		
			}
			dealFinaReport(clientNoteId,problemContentsList,noteItemId);
		}else		
			if(noteType.equals(Constant.client_type_HEALTH)){//健康问卷部分--03
			//获取健康问卷的子问卷id
			String noteItemId = questionNaireSaveResquestMessage.getNote_item_id();	
			startTime = System.currentTimeMillis();
			Long contentsNum =healthDao.getHealthReportContents(noteItemId);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId, "查询客户是否已提交健康问卷--耗时:"+(endTime-startTime)+"ms");
			if(contentsNum>0){
				startTime = System.currentTimeMillis();
				healthDao.deleteHealthReportContents(noteItemId);
				endTime = System.currentTimeMillis();
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId+"--函件类型:"+noteType, "删除客户已提交健康问卷");
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId, "删除客户已提交健康问卷--耗时:"+(endTime-startTime)+"ms");
				//更改高额业务员报告书的完成状态
				startTime = System.currentTimeMillis();
				updateStatusMap.put("clientNoteId", clientNoteId);
				updateStatusMap.put("noteItemId",noteItemId);
				updateStatusMap.put("noteItemStatus",Constant.item_status_N);
				healthDao.updateHealthReportStatus(updateStatusMap);
				endTime = System.currentTimeMillis();
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId+"--函件类型:"+noteType, "更改健康问卷的完成状态");
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId, "更改健康问卷的完成状态--耗时:"+(endTime-startTime)+"ms");		
			}
			dealHealthReport(noteItemId,clientNoteId,problemContentsList);
		}else{
			throw new BusinessException("问卷和报告提交接口,前端传入的函件类型为:"+noteType+"错误!");	
		}
	}	
	
	/**
	 * 处理契约调查报告书部分
	 * **/
	@Transactional
	public void dealSurvivalReport(String clientNoteId,List<ProblemContent> problemContentsList){
		List<ProblemContents> problemList=new ArrayList<ProblemContents>();
		Map<String , Object> updateSatusMap = new HashMap<>();
		String isValidN = Constant.valid_N;//无效
		String isValidY = Constant.valid_Y;//有效
		String maxNo =getMaxNo.getMaxNo();
		updateSatusMap.put("survivalNoteId", clientNoteId);
		updateSatusMap.put("isValidN", isValidN);
		startTime = System.currentTimeMillis();
		survivalDao.survivalIsValidUpdate(updateSatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+clientNoteId, "根据survivalNoteId，将客户之前的问题答案置为无效--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+clientNoteId, "更改契调问卷，将原问卷答案置为无效");
		for (ProblemContent problemContent : problemContentsList) {
			ProblemContents problemContents = new ProblemContents();
			problemContents.setSurvival_answer_id(maxNo);
			problemContents.setItem_question_id(problemContent.getId());
			problemContents.setItem_question_name(problemContent.getItem_question_id());
			problemContents.setItem_question_answer(problemContent.getItem_question_answer());
			problemContents.setItem_question_desc(problemContent.getItem_question_desc());
			problemContents.setSurvival_note_id(clientNoteId);
			problemContents.setIs_valid(isValidY);
			problemList.add(problemContents);
		}
		startTime = System.currentTimeMillis();
		survivalDao.insertSurvivalProblem(problemList);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+clientNoteId, "保存契调报告答案--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+clientNoteId, "向契调问卷插入数据");
		//将契约调查报告状态调为已完成
		Map<String , Object> updateSurvicalMap = new HashMap<>();			
		updateSurvicalMap.put("survivalNoteId", clientNoteId);
		updateSurvicalMap.put("noteItemStatus", Constant.item_status_Y);
		startTime = System.currentTimeMillis();
		survivalDao.updateNoteItemStatus(updateSurvicalMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+clientNoteId, "契调报告问题提交成功,改变报告状态为已完成--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+clientNoteId, "将问卷状态从未完成--->已完成");
	}
	
	/**
	 * 处理高额业务员报告书部分
	 * **/
	@Transactional
	public void dealAgentReport(String clientNoteId,List<ProblemContent> problemContentsList){
		List<ProblemContents> problemList=new ArrayList<ProblemContents>();
		Map<String , Object> updateSatusMap = new HashMap<>();
		startTime = System.currentTimeMillis();
		String noteReportId = agentDao.selectAgentReportId(clientNoteId);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"客户层函件id:"+clientNoteId, "根据函件id查询高额报告书id--耗时:"+(endTime-startTime)+"ms");
		for (ProblemContent problemContent : problemContentsList) {
			ProblemContents problemContents = new ProblemContents();
			problemContents.setItem_question_id(problemContent.getId());
			problemContents.setItem_question_name(problemContent.getItem_question_id());
			problemContents.setItem_question_answer(problemContent.getItem_question_answer());
			problemContents.setItem_question_desc(problemContent.getItem_question_desc());
			problemContents.setNote_report_id(noteReportId);
			problemList.add(problemContents);
		}
		startTime = System.currentTimeMillis();
		agentDao.agentReportSave(problemList);		
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"高额业务员报告书id:"+noteReportId, "保存高额报告书答案--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"高额业务员报告书id"+noteReportId, "向高额业务员报告书表插入客户填写的数据");
		//将高额业务员报告书的状态调为是Y--是否完成高额件业务员报告书
		updateSatusMap.put("noteReportId", noteReportId);
		updateSatusMap.put("clientNoteId", clientNoteId);
		updateSatusMap.put("finishAgentReport", Constant.item_status_Y);
		startTime = System.currentTimeMillis();
		agentDao.noteItemStatusUpdate(updateSatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"高额业务员报告书id:"+noteReportId, "将高额业务员报告书状态调为已完成--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"高额业务员报告书id"+noteReportId, "将高额业务员报告书状态调整为--->已完成");		
	}
	
	/**
	 * 处理财务问卷部分
	 * **/
	@Transactional
	public void dealFinaReport(String clientNoteId,List<ProblemContent> problemContentsList, String noteItemId){
		List<ProblemContents> problemList=new ArrayList<ProblemContents>();
		Map<String , Object> updateSatusMap = new HashMap<>();
		for (ProblemContent problemContent : problemContentsList) {
			ProblemContents problemContents = new ProblemContents();
			problemContents.setItem_question_id(problemContent.getId());
			problemContents.setItem_question_name(problemContent.getItem_question_id());
			problemContents.setItem_question_answer(problemContent.getItem_question_answer());
			problemContents.setItem_question_desc(problemContent.getItem_question_desc());
			problemContents.setNote_item_id(noteItemId);
			problemList.add(problemContents);
		}
		startTime = System.currentTimeMillis();
		finaDao.finaReportSave(problemList);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId+",财务函子问卷id为"+noteItemId, "插入财务函问卷答案--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId+",财务函子问卷id为"+noteItemId, "向财务问卷表插入客户 填写的数据");
		updateSatusMap.put("noteItemId", noteItemId);
		updateSatusMap.put("noteItemStatus", Constant.item_status_Y);
		startTime = System.currentTimeMillis();
		finaDao.updateNoteItemStatus(updateSatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id:"+clientNoteId+",财务函子问卷id为"+noteItemId, "更改财务问卷完成状态--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"财务函id为:"+clientNoteId+",财务函子问卷id为"+noteItemId, "将财务问卷状态调整为--->已完成");							
	}
	
	/**
	 * 处理健康问卷部分
	 * **/
	@Transactional
	public void dealHealthReport(String noteItemId,String clientNoteId,List<ProblemContent> problemContentsList){
		List<ProblemContents> problemList=new ArrayList<ProblemContents>();
		Map<String , Object> updateSatusMap = new HashMap<>();
		for (ProblemContent problemContent : problemContentsList){
			ProblemContents problemContents = new ProblemContents();
			problemContents.setItem_question_id(problemContent.getId());
			problemContents.setItem_question_name(problemContent.getItem_question_id());
			problemContents.setItem_question_answer(problemContent.getItem_question_answer());
			problemContents.setItem_question_desc(problemContent.getItem_question_desc());
			problemContents.setNote_item_id(noteItemId);
			problemList.add(problemContents);
		}
		startTime = System.currentTimeMillis();
		healthDao.healthReportSave(problemList);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId+"健康问卷id:"+noteItemId, "保存健康问卷答案--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId+"健康问卷id:"+noteItemId, "向健康问卷表插入客户填写数据");
		updateSatusMap.put("noteItemId", noteItemId);
		updateSatusMap.put("noteItemStatus", Constant.item_status_Y);
		startTime = System.currentTimeMillis();
		healthDao.updateNoteItemStatus(updateSatusMap);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康函id:"+clientNoteId+"健康问卷id:"+noteItemId, "更改健康问卷完成状态--耗时:"+(endTime-startTime)+"ms");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"健康问卷id:"+noteItemId, "将健康问卷状态调整为--->已完成");		
	}
		
	/**
	 * 校验请求报文
	 * **/
	private void checkData(QuestionNaireSaveResquestMessage questionNaireSaveResquestMessage) {
		Assert.notNull(questionNaireSaveResquestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+questionNaireSaveResquestMessage.getClient_note_id(), "开始检查请求报文");
		Assert.notEmpty(questionNaireSaveResquestMessage.getClient_note_id(), "客户层函件id不能为空");	
		Assert.notEmpty(questionNaireSaveResquestMessage.getNote_type(), "函件类型不能为空");		
		if(questionNaireSaveResquestMessage.getNote_type().equals(Constant.client_type_HEALTH)){
			Assert.notEmpty(questionNaireSaveResquestMessage.getNote_item_id(), "健康问子问卷id不能为空");		
		}
		if(questionNaireSaveResquestMessage.getNote_type().equals(Constant.client_type_FINA)){
			Assert.notEmpty(questionNaireSaveResquestMessage.getNote_item_id(), "财务函子问卷id不能为空");		
		}		
		List<ProblemContent> problemContent =questionNaireSaveResquestMessage.getProblemContent();
		for (ProblemContent problemContent2 : problemContent) {
			Assert.notEmpty(problemContent2.getId(), "item_question_id不能为空");
			Assert.notEmpty(problemContent2.getItem_question_id(), "item_question_name不能为空");
		}		
	}			
}