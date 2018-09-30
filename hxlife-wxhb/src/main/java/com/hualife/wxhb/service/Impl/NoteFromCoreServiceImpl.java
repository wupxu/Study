package com.hualife.wxhb.service.Impl;

import java.math.BigDecimal;
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

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBody;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyFinaItem;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyFinaItems;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyFinaOocuInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyFinaOocuInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyHealthInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyHealthInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyHealthItem;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyHealthItems;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyPhysicalInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyPhysicalInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyPhysicalItem;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyPhysicalItems;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProblem;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProblemInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProblemInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProblemObject;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProblemObjects;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProblems;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProductInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyProductInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyRelevanceInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyRelevanceInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodySurvivalInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodySurvivalInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodySurvivalItem;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodySurvivalItems;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyUndwrtInfo;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreBodyUndwrtInfos;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.common.dateFormat;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteClientRelationApply;
import com.hualife.wxhb.domain.entity.TNoteFinaNoteItem;
import com.hualife.wxhb.domain.entity.TNoteFinaOccuNote;
import com.hualife.wxhb.domain.entity.TNoteHealthNote;
import com.hualife.wxhb.domain.entity.TNoteHealthNoteItem;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.domain.entity.TNotePhysicalExamNote;
import com.hualife.wxhb.domain.entity.TNotePhysicalExamNoteItem;
import com.hualife.wxhb.domain.entity.TNoteProblemDetail;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.domain.entity.TNoteProblemObject;
import com.hualife.wxhb.domain.entity.TNoteSurvivalInvestNote;
import com.hualife.wxhb.domain.entity.TNoteSurvivalNoteItem;
import com.hualife.wxhb.domain.entity.TNoteUndwrtNote;
import com.hualife.wxhb.domain.entity.TNoteUndwrtProductResult;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.FinaDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.NoteMainDao;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.service.GetMaxNo;
import com.hualife.wxhb.service.NoteFromCoreService;
import com.hualife.wxhb.service.NoteTraceService;
import com.hualife.wxhb.service.TaskPushInfoService;

/**
 * @author zhanglong
 * @Description 核心推送数据的处理
 * @date 2017年8月18日 下午3:09:47
 */
@Service
public class NoteFromCoreServiceImpl implements NoteFromCoreService{
	
	private final Logger logger = LoggerFactory.getLogger(NoteFromCoreServiceImpl.class);

	@Autowired
	private GetMaxNo getMaxNo;
	@Autowired
	private NoteMainDao noteMainDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private PhysicalDao physicalDao;
	@Autowired
	private FinaDao finaDao;
	@Autowired
	private HealthDao healthDao;
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private UndwrtDao undwrtDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private TaskPushInfoService taskPushInfoService;
	@Autowired
	private NoteTraceService noteTraceService;
	
	@Override
	@Transactional
	public void dealDate(NoteFromCoreRequestMessage ReqXmlMessage)  {
		NoteFromCoreBody noteFromCoreBody = ReqXmlMessage.getBody();
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),ReqXmlMessage.getHead().getMsg().getTransNo().toString(),"校验报问题所有count是否非空");
		CheckBodyMessage(noteFromCoreBody);
		
		//获取body数组对象
		NoteFromCoreBodyPhysicalInfos noteFromCoreBodyPhysicalInfos = noteFromCoreBody.getPhysicalInfos();//体检函信息
		NoteFromCoreBodyFinaOocuInfos noteFromCoreBodyFinaOocuInfos = noteFromCoreBody.getFinaOocuInfos();//财务函信息
		NoteFromCoreBodyHealthInfos noteFromCoreBodyHealthInfos = noteFromCoreBody.getHealthInfos();//健康函信息
		NoteFromCoreBodySurvivalInfos noteFromCoreBodySurvivalInfos = noteFromCoreBody.getSurvivalInfos();//契调函信息
		NoteFromCoreBodyUndwrtInfos noteFromCoreBodyUndwrtInfos = noteFromCoreBody.getUndwrtInfos();//核保函信息
		NoteFromCoreBodyProblemInfos noteFromCoreBodyProblemInfos = noteFromCoreBody.getProblemInfos();//问题件信息
	 
		//函件信息（包括体检函、健康韩、财务函、契调函）
		if(!"0".equals(noteFromCoreBodyPhysicalInfos.getCount()) || !"0".equals(noteFromCoreBodyFinaOocuInfos.getCount())
				 || !"0".equals(noteFromCoreBodyHealthInfos.getCount()) || !"0".equals(noteFromCoreBodySurvivalInfos.getCount())){
			dealClientInfo(noteFromCoreBody);
		}
		 
		//核保函
		if(!"0".equals(noteFromCoreBodyUndwrtInfos.getCount())){
			clientUndwrtInfos(noteFromCoreBody);
		}
		//问题件
		if(!"0".equals(noteFromCoreBodyProblemInfos.getCount())){
			clientProblemInfos(noteFromCoreBody); 
		}
	}
	
	/**
	 * 对客户层函件进行处理
	 * @param noteFromCoreBody
	 * @ 
	 */
	private void dealClientInfo(NoteFromCoreBody noteFromCoreBody) {
		
		NoteFromCoreBodyRelevanceInfos noteFromCoreBodyRelevanceInfos = noteFromCoreBody.getRelevanceInfos();//投保单信息
		NoteFromCoreBodyPhysicalInfos noteFromCoreBodyPhysicalInfos = noteFromCoreBody.getPhysicalInfos();//体检函信息
		NoteFromCoreBodyFinaOocuInfos noteFromCoreBodyFinaOocuInfos = noteFromCoreBody.getFinaOocuInfos();//财务函信息
		NoteFromCoreBodyHealthInfos noteFromCoreBodyHealthInfos = noteFromCoreBody.getHealthInfos();//健康函信息
		NoteFromCoreBodySurvivalInfos noteFromCoreBodySurvivalInfos = noteFromCoreBody.getSurvivalInfos();//契调函信息
		//创建传输对象 用于生成业务员通知
		HashMap<String,Object> map = new HashMap<String,Object>();
		String clientName = "";//客户姓名
		String orderNo = "";//投保单
		String noteTypes = "";//多个客户层函件
		String agentNo = "";//代理人工号
		
		Date date = new Date(); 
		String note_id = "";
		String taskCode = noteFromCoreBody.getTaskcode(); //核保任务号
		//判断是否已经有 客户层函件
		TNoteMain tNoteMain = new TNoteMain();
		tNoteMain.setTaskCode(taskCode);
		tNoteMain.setNoteType(Constant.note_type_CLIENT);//客户函件
		
		TNoteMain selectTNoteMain = noteMainDao.selectExistsNoteID(tNoteMain);
		if(selectTNoteMain == null ){
			//生成main表信息
			note_id =getMaxNo.getMaxNo() ;
			
			tNoteMain.setNoteId(note_id);//函件id
			tNoteMain.setNoteType(Constant.note_type_CLIENT);//客户函件
			tNoteMain.setClientNo(noteFromCoreBody.getClient_no());//函件关联客户号
			tNoteMain.setClientName(noteFromCoreBody.getClient_name());//函件关联客户姓名
			tNoteMain.setApplicantNo(noteFromCoreBody.getApplicant_no());//投保人客户号(客户需要转发的对象)
			tNoteMain.setApplicantName(noteFromCoreBody.getApplicant_name());//投保人姓名
			tNoteMain.setApplicantPhone(noteFromCoreBody.getApplicant_phone());//投保人手机号(此手机号用于验证)
			tNoteMain.setNoteStatus(Constant.note_status_UNPUSH);//函件处理状态(01-未转发)
			tNoteMain.setAgentNo(noteFromCoreBody.getAgent_no());//处理函件的代理人编码
			tNoteMain.setAgentName(noteFromCoreBody.getAgent_name());//代理人姓名
			tNoteMain.setAgentPhone(noteFromCoreBody.getAgent_phone());//代理人手机号
			tNoteMain.setChannelType(noteFromCoreBody.getChannel_type());  //代理人所属渠道
			tNoteMain.setBranchCode(noteFromCoreBody.getBranch_code());//代理人所属机构代码
			tNoteMain.setBranchName(noteFromCoreBody.getBranch_name());//代理人所属机构名称
			tNoteMain.setCreatedDate(date);//创建日期
			tNoteMain.setUpdatedDate(date);//修改日期
			
			//提交
			noteMainDao.insertNoteMain(tNoteMain);
			
			clientName = noteFromCoreBody.getClient_name();
			agentNo = noteFromCoreBody.getAgent_no();
			
			if(!"0".equals(noteFromCoreBodyRelevanceInfos.getCount())){
				clientRelevanceInfo(note_id,noteFromCoreBodyRelevanceInfos);
				for(NoteFromCoreBodyRelevanceInfo noteFromCoreBodyRelevanceInfo:noteFromCoreBodyRelevanceInfos.getRelevanceInfo()){
					orderNo = "".equals(orderNo)?noteFromCoreBodyRelevanceInfo.getApply_bar_code():(orderNo+"/"+noteFromCoreBodyRelevanceInfo.getApply_bar_code());
				}
			}
		}else{
			note_id = selectTNoteMain.getNoteId();
			//更新mian字段
			tNoteMain.setNoteId(note_id);
			tNoteMain.setNoteStatus(Constant.note_status_UNPUSH);
			
			noteMainDao.updateMainNoteStatus(tNoteMain);
			
			clientName = selectTNoteMain.getAgentName();
			agentNo = selectTNoteMain.getAgentNo();
			for(NoteFromCoreBodyRelevanceInfo noteFromCoreBodyRelevanceInfo:noteFromCoreBodyRelevanceInfos.getRelevanceInfo()){
				orderNo = "".equals(orderNo)?noteFromCoreBodyRelevanceInfo.getApply_bar_code():(orderNo+"/"+noteFromCoreBodyRelevanceInfo.getApply_bar_code());
			}
		}
		
		if(!"0".equals(noteFromCoreBodyPhysicalInfos.getCount())){
			clientPhysicalinfos(note_id,noteFromCoreBodyPhysicalInfos,noteFromCoreBody);
			//三目数据拼接
			noteTypes = "".equals(noteTypes)?
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_PHYSICAL):
						(noteTypes+"/"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_PHYSICAL));
		}
		
		if(!"0".equals(noteFromCoreBodyFinaOocuInfos.getCount())){
			clientFinaOocuInfos(note_id,noteFromCoreBodyFinaOocuInfos,noteFromCoreBody);
			//三目数据拼接
			noteTypes = "".equals(noteTypes)?
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_FINAOCCU):
						(noteTypes+"/"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_FINAOCCU));
		}
		
		if(!"0".equals(noteFromCoreBodyHealthInfos.getCount())){
			clientHealthInfos(note_id,noteFromCoreBodyHealthInfos,noteFromCoreBody);
			//三目数据拼接
			noteTypes = "".equals(noteTypes)?
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_HEALTH):
						(noteTypes+"/"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_HEALTH));
		}
		
		if(!"0".equals(noteFromCoreBodySurvivalInfos.getCount())){
			clientSurvivalInfos(note_id,noteFromCoreBodySurvivalInfos,noteFromCoreBody);
			//三目数据拼接
			noteTypes = "".equals(noteTypes)?
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_SURVIVAL):
						(noteTypes+"/"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_SURVIVAL));
		}
		
		//生成通知业务员的通知书
		map.put("noteId", note_id);
		map.put("noteType", Constant.note_type_CLIENT);
		map.put("clientName", clientName);
		map.put("orderNo", orderNo);
		map.put("noteTypes", noteTypes);
		map.put("agentNo", agentNo);

		taskPushInfoService.saveUnderWriterSendoutNote(map);
	}
	
	/**
	 * 问题件 处理
	 * @param NoteFromCoreBody 报文体实体类对象
	 */
	private void clientProblemInfos(NoteFromCoreBody noteFromCoreBody) {
		Date date = new Date(); 
		NoteFromCoreBodyProblemInfos noteFromCoreBodyProblemInfos = noteFromCoreBody.getProblemInfos();//问题件信息
		
		List<TNoteMain> insertTNoteMainList = new ArrayList<TNoteMain>();
		List<TNoteProblemNote> insertTNoteProblemNote = new ArrayList<TNoteProblemNote>();
		List<TNoteClientRelationApply> insertTNoteClientRelationApplyList = new ArrayList<TNoteClientRelationApply>();
		
		List<NoteFromCoreBodyProblemInfo> problemInfoList =  noteFromCoreBodyProblemInfos.getProblemInfo();
		for(NoteFromCoreBodyProblemInfo noteFromCoreBodyProblemInfo :problemInfoList){
			
			//校验是否有问题件未处理完成
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("task_code", noteFromCoreBody.getTaskcode());
			map.put("note_type", Constant.note_type_PROBLEM);
			map.put("note_stauts_finished", Constant.note_status_FINISHED);
			map.put("apply_bar_code", noteFromCoreBodyProblemInfo.getApply_bar_code());
			
			String selectCount = problemDao.selectnoteProblemExists(map);
			Assert.Compare("0".equals(selectCount), "该保单对应的问题件还未处理完成，不能进行函件下发！");
			
			//创建传输对象 用于生成业务员通知
			map = new HashMap<String,Object>();
			String clientName = "";//客户姓名
			String orderNo = "";//投保单
			String noteTypes = "";//多个客户层函件
			String agentNo = "";//代理人工号
			String specificNoteId = "";
			
			//生成main表信息
			String note_id = getMaxNo.getMaxNo() ;
			TNoteMain tNoteMain = new TNoteMain();
			tNoteMain.setNoteId(note_id);//函件id
			tNoteMain.setNoteType(Constant.note_type_PROBLEM);//客户函件
			tNoteMain.setClientNo(noteFromCoreBodyProblemInfo.getInsured_no());//函件关联客户号
			tNoteMain.setClientName(noteFromCoreBodyProblemInfo.getInsured_name());//函件关联客户姓名
			tNoteMain.setApplicantNo(noteFromCoreBodyProblemInfo.getApplication_no());//投保人客户号(客户需要转发的对象)
			tNoteMain.setApplicantName(noteFromCoreBodyProblemInfo.getApplication_name());//投保人姓名
			tNoteMain.setApplicantPhone(noteFromCoreBodyProblemInfo.getApplication_phone());//投保人手机号(此手机号用于验证)
			tNoteMain.setTaskCode(noteFromCoreBody.getTaskcode());//核保任务号
			tNoteMain.setNoteStatus(Constant.note_status_UNPUSH);//函件处理状态
			tNoteMain.setAgentNo(noteFromCoreBodyProblemInfo.getAgent_no());//处理函件的代理人编码
			tNoteMain.setAgentName(noteFromCoreBodyProblemInfo.getAgent_name());//代理人姓名
			tNoteMain.setAgentPhone(noteFromCoreBodyProblemInfo.getAgent_phone());//代理人手机号
			tNoteMain.setChannelType(noteFromCoreBodyProblemInfo.getChannel_type());  //代理人所属渠道
			tNoteMain.setBranchCode(noteFromCoreBodyProblemInfo.getBranch_code());//代理人所属机构代码
			tNoteMain.setBranchName(noteFromCoreBodyProblemInfo.getBranch_name());//代理人所属机构名称
			tNoteMain.setCreatedDate(date);//创建日期
			tNoteMain.setUpdatedDate(date);//修改日期
			insertTNoteMainList.add(tNoteMain);
			
			clientName = noteFromCoreBodyProblemInfo.getInsured_name();
			agentNo = noteFromCoreBodyProblemInfo.getAgent_no();
			
			TNoteClientRelationApply tNoteClientRelationApply = new TNoteClientRelationApply();
			tNoteClientRelationApply.setNoteId(tNoteMain.getNoteId());
			tNoteClientRelationApply.setApplyBarCode(noteFromCoreBodyProblemInfo.getApply_bar_code());
			tNoteClientRelationApply.setApplicantName(noteFromCoreBodyProblemInfo.getApplication_name());
			tNoteClientRelationApply.setApplicantPhone(noteFromCoreBodyProblemInfo.getApplication_phone());
			tNoteClientRelationApply.setInsuredName(noteFromCoreBodyProblemInfo.getInsured_name());
			insertTNoteClientRelationApplyList.add(tNoteClientRelationApply);
			
			orderNo = tNoteClientRelationApply.getApplyBarCode();
			
			TNoteProblemNote tNoteProblemNote = new TNoteProblemNote();
			tNoteProblemNote.setNoteId(note_id);//函件id
			tNoteProblemNote.setProblemNoteId(getMaxNo.getMaxNo());//问题件函件id
			tNoteProblemNote.setApplyBarCode(noteFromCoreBodyProblemInfo.getApply_bar_code());//投保单号
			tNoteProblemNote.setNoteBarCode(noteFromCoreBodyProblemInfo.getNote_bar_code());//函件条形码
			tNoteProblemNote.setApplicationNo(noteFromCoreBodyProblemInfo.getApplication_no());//投保人客户号
			tNoteProblemNote.setApplicationName(noteFromCoreBodyProblemInfo.getApplication_name());//投保人姓名
			tNoteProblemNote.setInsuredNo(noteFromCoreBodyProblemInfo.getInsured_no());//被保人客户号
			tNoteProblemNote.setInsuredName(noteFromCoreBodyProblemInfo.getInsured_name());//被保人姓名
			tNoteProblemNote.setInsuredAge(Integer.valueOf(noteFromCoreBodyProblemInfo.getInsured_age()) );//被保人年龄
			tNoteProblemNote.setNoteStatus(Constant.problem_note_status_UNPUSH);//函件处理状态
			tNoteProblemNote.setNoteStatusDesc(Constant.problem_note_status_UNPUSH_DESC);//函件处理状态-
			tNoteProblemNote.setNoteReason(noteFromCoreBodyProblemInfo.getNote_reason());//下发问题件原因
			tNoteProblemNote.setAgentNo(noteFromCoreBodyProblemInfo.getAgent_no());//代理人编码
			tNoteProblemNote.setAgentName(noteFromCoreBodyProblemInfo.getAgent_name());//代理人姓名
			tNoteProblemNote.setBranchCode(noteFromCoreBodyProblemInfo.getBranch_code()); //营业机构编码
			tNoteProblemNote.setCreatedDate(date);//创建日期
			tNoteProblemNote.setUpdatedDate(date);//修改日期
			try{
				tNoteProblemNote.setDecidingDate(dateFormat.parse(noteFromCoreBodyProblemInfo.getDeciding_date())); //下发时间
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			tNoteProblemNote.setNoteSeq(noteFromCoreBodyProblemInfo.getNote_seq());//核保任务序号
			tNoteProblemNote.setIsNotQualifiedNote(Constant.answer_N);//是否为次品单
			tNoteProblemNote.setIsSecondNote(Constant.answer_N);//是否为重新下发
			insertTNoteProblemNote.add(tNoteProblemNote);
			
			specificNoteId = tNoteProblemNote.getProblemNoteId();
			
			NoteFromCoreBodyProblemObjects noteFromCoreBodyProblemObjects = noteFromCoreBodyProblemInfo.getProblemObjects();
			if(noteFromCoreBodyProblemObjects.getCount() != "0"){
				clientProblemObjects(tNoteProblemNote.getProblemNoteId(),noteFromCoreBodyProblemObjects);
			}
			
			noteTypes = DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_PROBLEM);;
			map.put("noteId", note_id);
			map.put("noteType", Constant.note_type_PROBLEM);
			map.put("clientName", clientName);
			map.put("orderNo", orderNo);
			map.put("noteTypes", noteTypes);
			map.put("agentNo", agentNo);
			map.put("specificNoteId", specificNoteId);
			
			taskPushInfoService.saveUnderWriterSendoutNote(map);
			
			noteTraceService.saveNoteTrace(note_id, tNoteProblemNote.getProblemNoteId(), Constant.note_from_core_type_PROBLEM, 
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,Constant.note_from_core_type_PROBLEM)+
					DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,Constant.note_status_UNPUSH));
		}
		
		//提交
		noteMainDao.insertNoteMainList(insertTNoteMainList);
		problemDao.insertNoteProblem(insertTNoteProblemNote);
		noteMainDao.insertRelationApply(insertTNoteClientRelationApplyList);
	}
	/**
	 * 问题件对象处理
	 * @param note_id 函件id
	 * @param noteFromCoreBodyProblemObjects 问题件对象列表
	 */
	private void clientProblemObjects(String problemNoteId,NoteFromCoreBodyProblemObjects noteFromCoreBodyProblemObjects){
		
		List<TNoteProblemObject> insertTNoteProblemObjectList = new ArrayList<TNoteProblemObject>();
		List<TNoteProblemDetail> insertTNoteProblemDetailList = new ArrayList<TNoteProblemDetail>();
		
		Date date = new Date(); 
		List<NoteFromCoreBodyProblemObject> problemObjectList = noteFromCoreBodyProblemObjects.getProblemObject();
		for(NoteFromCoreBodyProblemObject noteFromCoreBodyProblemObject:problemObjectList){
			TNoteProblemObject tNoteProblemObject = new TNoteProblemObject();
			tNoteProblemObject.setProblemNoteId(problemNoteId);//问题对象id
			tNoteProblemObject.setProblemObjectId(getMaxNo.getMaxNo());//问题件对象id
			tNoteProblemObject.setProblemObject(noteFromCoreBodyProblemObject.getProblem_object());//问题对象(1-代理人，2-投保人，3-被保人)
			if(Constant.problem_object_AGENT.equals(noteFromCoreBodyProblemObject.getProblem_object())){
				tNoteProblemObject.setProblemObjectStatus(Constant.problem_object_status_UNFINISHED);//问题处理状态
			}else{
				tNoteProblemObject.setProblemObjectStatus(Constant.problem_object_status_UNPUSH);//问题处理状态
			}
			
			insertTNoteProblemObjectList.add(tNoteProblemObject);
			
			NoteFromCoreBodyProblems noteFromCoreBodyProblems  = noteFromCoreBodyProblemObject.getProblems();
			if( !"0".equals(noteFromCoreBodyProblems.getCount()) ){
				List<NoteFromCoreBodyProblem> noteFromCoreBodyProblemList = noteFromCoreBodyProblems.getProblem();
				for(NoteFromCoreBodyProblem noteFromCoreBodyProblem : noteFromCoreBodyProblemList){
					TNoteProblemDetail tNoteProblemDetail = new TNoteProblemDetail();
					tNoteProblemDetail.setProblemObjectId(tNoteProblemObject.getProblemObjectId());
					tNoteProblemDetail.setProblemSeq(noteFromCoreBodyProblem.getProblem_seq());
					tNoteProblemDetail.setProblemDesc(noteFromCoreBodyProblem.getProblem_desc());
					tNoteProblemDetail.setProblemColumn(noteFromCoreBodyProblem.getProblem_Column());
					tNoteProblemDetail.setCreatedDate(date);
					
					insertTNoteProblemDetailList.add(tNoteProblemDetail);
				}
			}
		}
		
		//提交
		problemDao.insertNoteProblemObject(insertTNoteProblemObjectList);
		problemDao.insertTNoteProblemDetail(insertTNoteProblemDetailList);
	}
	
	/**
	 * 核保函 
	 * @param noteFromCoreBody 报问题内容
	 * @ 
	 */
	private void clientUndwrtInfos(NoteFromCoreBody noteFromCoreBody) {
		Date date = new Date(); 
		NoteFromCoreBodyUndwrtInfos noteFromCoreBodyUndwrtInfos = noteFromCoreBody.getUndwrtInfos();//核保函信息
		List<TNoteClientRelationApply> insertTNoteClientRelationApplyList = new ArrayList<TNoteClientRelationApply>();
		List<TNoteMain> insertTNoteMainList = new ArrayList<TNoteMain>();
		List<NoteFromCoreBodyUndwrtInfo> undwrtInfoList = noteFromCoreBodyUndwrtInfos.getUndwrtInfo();
		for(NoteFromCoreBodyUndwrtInfo noteFromCoreBodyUndwrtInfo:undwrtInfoList){
			//创建传输对象 用于生成业务员通知
			Map<String,Object> map = new HashMap<String,Object>();
			String clientName = "";//客户姓名
			String orderNo = "";//投保单
			String noteTypes = "";//多个客户层函件
			String agentNo = "";//代理人工号
			String specificNoteId = "";
			String undwrtType = "";
			
			//生成main表信息
			String note_id =getMaxNo.getMaxNo() ;
			TNoteMain tNoteMain = new TNoteMain();
			tNoteMain.setNoteId(note_id);//函件id
			tNoteMain.setNoteType(Constant.note_type_UNDWRT);//客户函件
			tNoteMain.setClientNo(noteFromCoreBodyUndwrtInfo.getInsured_no());//函件关联客户号
			tNoteMain.setClientName(noteFromCoreBodyUndwrtInfo.getInsured_name());//函件关联客户姓名
			tNoteMain.setApplicantNo(noteFromCoreBodyUndwrtInfo.getApplication_no());//投保人客户号(客户需要转发的对象)
			tNoteMain.setApplicantName(noteFromCoreBodyUndwrtInfo.getApplication_name());//投保人姓名
			tNoteMain.setApplicantPhone(noteFromCoreBodyUndwrtInfo.getApplication_phone());//投保人手机号(此手机号用于验证)
			tNoteMain.setTaskCode(noteFromCoreBody.getTaskcode());//核保任务号
			tNoteMain.setNoteStatus(Constant.note_status_UNPUSH);//函件处理状态
			tNoteMain.setAgentNo(noteFromCoreBodyUndwrtInfo.getAgent_no());//处理函件的代理人编码
			tNoteMain.setAgentName(noteFromCoreBodyUndwrtInfo.getAgent_name());//代理人姓名
			tNoteMain.setAgentPhone(noteFromCoreBodyUndwrtInfo.getAgent_phone());//代理人手机号
			tNoteMain.setChannelType(noteFromCoreBodyUndwrtInfo.getChannel_type()); //代理人所属渠道
			tNoteMain.setBranchCode(noteFromCoreBodyUndwrtInfo.getBranch_code());//代理人所属机构代码
			tNoteMain.setBranchName(noteFromCoreBodyUndwrtInfo.getBranch_name());//代理人所属机构名称
			tNoteMain.setCreatedDate(date);//创建日期
			tNoteMain.setUpdatedDate(date);//修改日期
			insertTNoteMainList.add(tNoteMain);

			clientName = noteFromCoreBodyUndwrtInfo.getInsured_name();
			agentNo = noteFromCoreBodyUndwrtInfo.getAgent_no();
			
			TNoteClientRelationApply tNoteClientRelationApply = new TNoteClientRelationApply();
			tNoteClientRelationApply.setNoteId(tNoteMain.getNoteId());
			tNoteClientRelationApply.setApplyBarCode(noteFromCoreBodyUndwrtInfo.getApply_bar_code());
			tNoteClientRelationApply.setApplicantName(noteFromCoreBodyUndwrtInfo.getApplication_name());
			tNoteClientRelationApply.setApplicantPhone(noteFromCoreBodyUndwrtInfo.getApplication_phone());
			tNoteClientRelationApply.setInsuredName(noteFromCoreBodyUndwrtInfo.getInsured_name());
			insertTNoteClientRelationApplyList.add(tNoteClientRelationApply);
			
			orderNo = tNoteClientRelationApply.getApplyBarCode();
			
			TNoteUndwrtNote tNoteUndwrtNote = new TNoteUndwrtNote();
			tNoteUndwrtNote.setNoteId(note_id);//函件id
			tNoteUndwrtNote.setNoteUndwrtId(getMaxNo.getMaxNo());//核保函id
			tNoteUndwrtNote.setNoteType(noteFromCoreBodyUndwrtInfo.getNote_type());//核保函类型
			tNoteUndwrtNote.setApplyBarCode(noteFromCoreBodyUndwrtInfo.getApply_bar_code());//投保单号
			tNoteUndwrtNote.setNoteBarCode(noteFromCoreBodyUndwrtInfo.getNote_bar_code());//函件条形码
			tNoteUndwrtNote.setAccountNo(noteFromCoreBodyUndwrtInfo.getAccount_no());//客户银行卡号
			tNoteUndwrtNote.setApplicationNo(noteFromCoreBodyUndwrtInfo.getApplication_no());//投保人客户号
			tNoteUndwrtNote.setApplicationName(noteFromCoreBodyUndwrtInfo.getApplication_name());//投保人姓名
			tNoteUndwrtNote.setInsuredNo(noteFromCoreBodyUndwrtInfo.getInsured_no());//被保人客户号
			tNoteUndwrtNote.setInsuredAge(Integer.valueOf(noteFromCoreBodyUndwrtInfo.getInsured_age()) );//被保人年龄
			tNoteUndwrtNote.setInsuredName(noteFromCoreBodyUndwrtInfo.getInsured_name());//被保人姓名
			tNoteUndwrtNote.setNoteStatus(Constant.undwrt_note_status_UNPUSH);//函件处理状态
			tNoteUndwrtNote.setNoteStatusDesc(Constant.undwrt_note_status_UNPUSH_DESC);//函件处理状态描述
			tNoteUndwrtNote.setAgentNo(noteFromCoreBodyUndwrtInfo.getAgent_no());//代理人编码
			tNoteUndwrtNote.setAgentName(noteFromCoreBodyUndwrtInfo.getAgent_name());//代理人姓名
			tNoteUndwrtNote.setBranchName(noteFromCoreBodyUndwrtInfo.getBranch_name());//营业机构
			tNoteUndwrtNote.setCompanyAddress(noteFromCoreBodyUndwrtInfo.getCompany_address());//公司地址
			tNoteUndwrtNote.setCompanyPostalCode(noteFromCoreBodyUndwrtInfo.getCompany_postal_code());//公司邮编
			tNoteUndwrtNote.setCreatedDate(date);//创建时间
			tNoteUndwrtNote.setNoteSeq(noteFromCoreBodyUndwrtInfo.getNote_seq());//核保任务序号
			tNoteUndwrtNote.setDecidingDate(dateFormat.parse(noteFromCoreBodyUndwrtInfo.getDeciding_date()));//函件下发时间
			//提交
			undwrtDao.insertNoteUndwrt(tNoteUndwrtNote);
			
			specificNoteId = tNoteUndwrtNote.getNoteUndwrtId();
			undwrtType = tNoteUndwrtNote.getNoteType();
			
			NoteFromCoreBodyProductInfos noteFromCoreBodyProductInfos = noteFromCoreBodyUndwrtInfo.getProductInfos();
			if(noteFromCoreBodyProductInfos.getCount() != "0"){
				dealProductInfo(noteFromCoreBodyProductInfos,tNoteUndwrtNote.getNoteUndwrtId());
			}
			
			noteTypes = DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME, Constant.note_from_core_type_UNDWRT);
			map.put("noteId", note_id);
			map.put("noteType", Constant.note_type_UNDWRT);
			map.put("clientName", clientName);
			map.put("orderNo", orderNo);
			map.put("noteTypes", noteTypes);
			map.put("agentNo", agentNo);
			map.put("specificNoteId", specificNoteId);
			map.put("undwrtType", undwrtType);

			taskPushInfoService.saveUnderWriterSendoutNote(map);
			noteTraceService.saveNoteTrace(note_id, tNoteUndwrtNote.getNoteUndwrtId(), Constant.note_from_core_type_SURVIVAL, 
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,Constant.note_from_core_type_SURVIVAL)+
					DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,Constant.note_status_UNPUSH));
		}
		//对信息进行提交
		noteMainDao.insertRelationApply(insertTNoteClientRelationApplyList);
		noteMainDao.insertNoteMainList(insertTNoteMainList);
	}
	/**
	 * 核保函险种信息处理
	 * @param noteFromCoreBodyProductInfos 险种信息集合
	 * @param NoteUndwrtId 核保函id
	 * @ 
	 */
	private void dealProductInfo(NoteFromCoreBodyProductInfos noteFromCoreBodyProductInfos,String NoteUndwrtId){
		Date date = new Date(); 
		List<NoteFromCoreBodyProductInfo> productInfoList = noteFromCoreBodyProductInfos.getProductInfo();
		List<TNoteUndwrtProductResult> insertTNoteUndwrtProductResultList = new ArrayList<TNoteUndwrtProductResult>();
		for(NoteFromCoreBodyProductInfo noteFromCoreBodyProductInfo:productInfoList){
			TNoteUndwrtProductResult tNoteUndwrtProductResult = new TNoteUndwrtProductResult();
			tNoteUndwrtProductResult.setNoteUndwrtId(NoteUndwrtId);//核保函id
			tNoteUndwrtProductResult.setProductCode(noteFromCoreBodyProductInfo.getProduct_code());//险种编码
			tNoteUndwrtProductResult.setProductName(noteFromCoreBodyProductInfo.getProduct_name());//险种名称
			tNoteUndwrtProductResult.setUndwrtResult(noteFromCoreBodyProductInfo.getUndwrt_result());//险种结论
			tNoteUndwrtProductResult.setUndwrtResultReason(noteFromCoreBodyProductInfo.getUndwrt_result_reason());//险种结论原因
			tNoteUndwrtProductResult.setTotalPrem( BigDecimal.valueOf(Double.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getTotal_prem())?"0":noteFromCoreBodyProductInfo.getTotal_prem() )) );//险种应交保费
			tNoteUndwrtProductResult.setTotalOccuAddPrem( BigDecimal.valueOf(Double.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getTotal_occu_add_prem())?"0":noteFromCoreBodyProductInfo.getTotal_occu_add_prem() )) );//总计职业加费金额
			tNoteUndwrtProductResult.setTotalHealthAddPrem( BigDecimal.valueOf(Double.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getTotal_health_add_prem())?"0":noteFromCoreBodyProductInfo.getTotal_health_add_prem() )));//总计健康加费金额
			tNoteUndwrtProductResult.setAddExtraTremPeriodType(noteFromCoreBodyProductInfo.getAdd_extra_trem_period_type());//加费期限类型(年/月)
			tNoteUndwrtProductResult.setAddExtraTremPeriod(Integer.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getAdd_extra_trem_period())?"0":noteFromCoreBodyProductInfo.getAdd_extra_trem_period() ));//加费期限
			tNoteUndwrtProductResult.setExclustion(noteFromCoreBodyProductInfo.getExclustion());//除外责任
			tNoteUndwrtProductResult.setCutFaceAmount(
					BigDecimal.valueOf(Double.valueOf(
							"".equals(noteFromCoreBodyProductInfo.getCut_face_amount())?"0":noteFromCoreBodyProductInfo.getCut_face_amount())));//削减保额
			tNoteUndwrtProductResult.setLimitdFaceAmount(BigDecimal.valueOf(Double.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getLimitd_face_amount())?"0":noteFromCoreBodyProductInfo.getLimitd_face_amount() )));//限制保额
			tNoteUndwrtProductResult.setLimitdUnits(Integer.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getLimitd_units())?"0":noteFromCoreBodyProductInfo.getLimitd_units() ));//限制份数
			tNoteUndwrtProductResult.setPostponedPeriodType(noteFromCoreBodyProductInfo.getPostponed_period_type());//延期期限单位
			tNoteUndwrtProductResult.setPostponedPeriod(Integer.valueOf(
					"".equals(noteFromCoreBodyProductInfo.getPostponed_period())?"0":noteFromCoreBodyProductInfo.getPostponed_period() ) );//延期期限
			tNoteUndwrtProductResult.setCreatedDate(date);//创建时间
			
			insertTNoteUndwrtProductResultList.add(tNoteUndwrtProductResult);
		}
		//提交
		undwrtDao.insertNoteUndwrtProduct(insertTNoteUndwrtProductResultList);
	}
	
	/**
	 * 契调函
	 * @param note_id 函件id
	 * @param noteFromCoreBodySurvivalInfos 契调函处理列表
	 * @param noteFromCoreBody 报文体
	 */
	private void clientSurvivalInfos(String note_id,NoteFromCoreBodySurvivalInfos noteFromCoreBodySurvivalInfos  ,NoteFromCoreBody noteFromCoreBody){
		List<NoteFromCoreBodySurvivalInfo> survivalInfoList = noteFromCoreBodySurvivalInfos.getSurvivalInfo();
		for(NoteFromCoreBodySurvivalInfo noteFromCoreBodySurvivalInfo:survivalInfoList){
			
			//判断是否有未处理完成的数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("note_id", note_id);
			map.put("note_type", Constant.client_type_SURVIVAL);
			map.put("note_status_writeoff", Constant.survival_note_status_WRITEOFF);
			
			String tNoteClientNoteCount = clientDao.selectNoteclientExists(map);
			Assert.Compare("0".equals(tNoteClientNoteCount), "存在未处理完成的契调函，不能进行函件下发！");
			
			TNoteClientNote tNoteClientNote = new TNoteClientNote();
			tNoteClientNote.setNoteId(note_id);
			tNoteClientNote.setClientNoteId(getMaxNo.getMaxNo());//客户层函件id			
			tNoteClientNote.setNoteType(Constant.client_type_SURVIVAL);//函件分类
			tNoteClientNote.setNoteBarCode(noteFromCoreBodySurvivalInfo.getNote_bar_code());//函件条形码
			tNoteClientNote.setNoteSeq(noteFromCoreBodySurvivalInfo.getNote_seq());//核保任务序号
			tNoteClientNote.setClientNo(noteFromCoreBody.getClient_no());//函件对应客户客户号
			tNoteClientNote.setNoteStatus(Constant.physical_health_note_status_UNPUSH);//函件处理状态
			tNoteClientNote.setNoteStatusDesc(Constant.physical_health_note_status_UNPUSH_DESC);//函件处理状态描述
			tNoteClientNote.setNoteReason(noteFromCoreBodySurvivalInfo.getNote_reason());//函件下发原因
			tNoteClientNote.setNoteAgentStatus(Constant.note_agent_status_UNPUSH);//函件代理人处理状态
			tNoteClientNote.setNoteAgentStatusDesc(Constant.note_agent_status_UNPUSH_DESC);//函件代理人处理状态描述
			tNoteClientNote.setNoteClientStatus(Constant.note_client_status_UNFINISHED);//函件客户处理状态
			tNoteClientNote.setNoteClientStatusDesc(Constant.note_client_status_UNFINISHED_DESC);//函件客户处理状态描述
			tNoteClientNote.setIsAgentReport(Constant.answer_N);//是否有高额件业务员报告书
			tNoteClientNote.setDecidingDate(dateFormat.parse(noteFromCoreBodySurvivalInfo.getDeciding_date()));// 下发时间
			tNoteClientNote.setIsSecondNote(Constant.answer_N);
			tNoteClientNote.setIsNotQualifiedNote(Constant.answer_N);
			tNoteClientNote.setNoteStatusValid(Constant.answer_Y);
			//提交
			clientDao.insertNoteClient(tNoteClientNote);
			
			TNoteSurvivalInvestNote tNoteSurvivalInvestNote = new TNoteSurvivalInvestNote();
			tNoteSurvivalInvestNote.setSurvivalNoteId(tNoteClientNote.getClientNoteId());//契调函id
			tNoteSurvivalInvestNote.setClientName(noteFromCoreBody.getClient_name());//客户姓名
			tNoteSurvivalInvestNote.setClientAge(Integer.valueOf(noteFromCoreBody.getClient_age()));//客户年龄
			tNoteSurvivalInvestNote.setClientIdno(noteFromCoreBody.getClient_idno());//客户证件号码
			tNoteSurvivalInvestNote.setClientSex(noteFromCoreBody.getClient_sex());//客户性别
			tNoteSurvivalInvestNote.setAgentFile(noteFromCoreBodySurvivalInfo.getAgent_file());	//需要业务员提供的资料		
			tNoteSurvivalInvestNote.setOtherInvest(noteFromCoreBodySurvivalInfo.getOther_invest());//其他调查内容
			tNoteSurvivalInvestNote.setSurvivalReason(noteFromCoreBodySurvivalInfo.getSurvival_reason());//契调原因
			tNoteSurvivalInvestNote.setSurvivalMode(noteFromCoreBodySurvivalInfo.getSurvival_mode());//契调方式
			tNoteSurvivalInvestNote.setSpecialDesc(noteFromCoreBodySurvivalInfo.getSpecial_desc());//核保员特别说明
			tNoteSurvivalInvestNote.setSurvivalBranchProvince(noteFromCoreBodySurvivalInfo.getSurvival_branch_province());//契调函原来所在省
			tNoteSurvivalInvestNote.setSurvivalBranchCode(noteFromCoreBodySurvivalInfo.getSurvival_branch_code());//契调函原来所属机构
			tNoteSurvivalInvestNote.setAgentNo(noteFromCoreBody.getAgent_no());//业务人员工号
			tNoteSurvivalInvestNote.setAgentName(noteFromCoreBody.getAgent_name());//业务人员姓名
			tNoteSurvivalInvestNote.setAgentBranchAddress(noteFromCoreBodySurvivalInfo.getAgent_branch_address());//业务员所在机构地址
			tNoteSurvivalInvestNote.setSurvivalDate(dateFormat.parse(noteFromCoreBodySurvivalInfo.getSurvival_date()));
			tNoteSurvivalInvestNote.setIsFinish(Constant.answer_N);
			//提交
			survivalDao.insertNoteSurvival(tNoteSurvivalInvestNote);
			
			NoteFromCoreBodySurvivalItems noteFromCoreBodySurvivalItems = noteFromCoreBodySurvivalInfo.getSurvivalItems();
			if(noteFromCoreBodySurvivalItems.getCount() != "0"){
				List<NoteFromCoreBodySurvivalItem> survivalItemList = noteFromCoreBodySurvivalItems.getSurvivalItem();
				List<TNoteSurvivalNoteItem> insertTNoteSurvivalNoteItemList = new ArrayList<TNoteSurvivalNoteItem>();
				for(NoteFromCoreBodySurvivalItem noteFromCoreBodySurvivalItem:survivalItemList){
					TNoteSurvivalNoteItem tNoteSurvivalNoteItem = new TNoteSurvivalNoteItem();
					tNoteSurvivalNoteItem.setSurvivalNoteId(tNoteSurvivalInvestNote.getSurvivalNoteId());//契调函件id
					tNoteSurvivalNoteItem.setNoteItemId(getMaxNo.getMaxNo());
					tNoteSurvivalNoteItem.setNoteItemType(noteFromCoreBodySurvivalItem.getNote_item_type());
					insertTNoteSurvivalNoteItemList.add(tNoteSurvivalNoteItem);
				}
				//提交
				survivalDao.insertNoteSurvivalItem(insertTNoteSurvivalNoteItemList);
			}
			noteTraceService.saveNoteTrace(note_id, tNoteClientNote.getClientNoteId(), Constant.note_from_core_type_SURVIVAL, 
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,Constant.note_from_core_type_SURVIVAL)+
					DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,Constant.note_status_UNPUSH));
		}
	}
	
	
	/**
	 * 健康函处理
	 * @param note_id 函件id
	 * @param noteFromCoreBodyHealthInfos 健康函处理列表
	 * @param noteFromCoreBody 报文体
	 */
	private void clientHealthInfos(String note_id,NoteFromCoreBodyHealthInfos noteFromCoreBodyHealthInfos ,NoteFromCoreBody noteFromCoreBody){
		List<NoteFromCoreBodyHealthInfo> healthInfoList = noteFromCoreBodyHealthInfos.getHealthInfo();
		for(NoteFromCoreBodyHealthInfo noteFromCoreBodyHealthInfo:healthInfoList){
			
			//判断是否有未处理完成的数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("note_id", note_id);
			map.put("note_type", Constant.client_type_HEALTH);
			map.put("note_status_writeoff", Constant.physical_or_health_note_status_WRITEOFF);
			map.put("note_status_cancel", Constant.physical_or_health_note_status_CANCEL);
			map.put("note_status_linedown", Constant.physical_or_health_note_status_LINEDOWN);
			
			String tNoteClientNoteCount = clientDao.selectNoteclientExists(map);
			Assert.Compare("0".equals(tNoteClientNoteCount), "存在未处理完成的健康函，不能进行函下发！");
			
			TNoteClientNote tNoteClientNote = new TNoteClientNote();
			tNoteClientNote.setNoteId(note_id); //函件id
			tNoteClientNote.setClientNoteId(getMaxNo.getMaxNo());//客户层函件id			
			tNoteClientNote.setNoteType(Constant.client_type_HEALTH);//函件分类
			tNoteClientNote.setNoteBarCode(noteFromCoreBodyHealthInfo.getNote_bar_code());//函件条形码
			tNoteClientNote.setNoteSeq(noteFromCoreBodyHealthInfo.getNote_seq());//核保任务序号
			tNoteClientNote.setClientNo(noteFromCoreBody.getClient_no());//函件对应客户客户号
			tNoteClientNote.setNoteStatus(Constant.physical_health_note_status_UNPUSH);//函件处理状态
			tNoteClientNote.setNoteStatusDesc(Constant.physical_health_note_status_UNPUSH_DESC);//函件处理状态描述
			tNoteClientNote.setNoteReason(noteFromCoreBodyHealthInfo.getNote_reason());//函件下发原因
			tNoteClientNote.setNoteAgentStatus(Constant.note_agent_status_UNPUSH);//函件代理人处理状态
			tNoteClientNote.setNoteAgentStatusDesc(Constant.note_agent_status_UNPUSH_DESC);//函件代理人处理状态描述
			tNoteClientNote.setNoteClientStatus(Constant.note_client_status_UNFINISHED);//函件客户处理状态
			tNoteClientNote.setNoteClientStatusDesc(Constant.note_client_status_UNFINISHED_DESC);//函件客户处理状态描述
			tNoteClientNote.setIsAgentReport(Constant.answer_N);//是否有高额件业务员报告书
			tNoteClientNote.setDecidingDate(dateFormat.parse(noteFromCoreBodyHealthInfo.getDeciding_date()));// 下发时间
			tNoteClientNote.setIsSecondNote(Constant.answer_N);
			tNoteClientNote.setIsNotQualifiedNote(Constant.answer_N);
			tNoteClientNote.setNoteStatusValid(Constant.answer_Y);
			//提交
			clientDao.insertNoteClient(tNoteClientNote);
			
			
			TNoteHealthNote tNoteHealthNote = new TNoteHealthNote();
			tNoteHealthNote.setHealthNoteId(tNoteClientNote.getClientNoteId());//健康问卷id
			tNoteHealthNote.setClientName(noteFromCoreBody.getClient_name());//函件客户姓名
			tNoteHealthNote.setClientAge(Integer.valueOf(noteFromCoreBody.getClient_age()));//客户年龄
			tNoteHealthNote.setClientSex(noteFromCoreBody.getClient_sex());//客户性别
			tNoteHealthNote.setSpecialDesc(noteFromCoreBodyHealthInfo.getSpecial_desc());//核保员特别说明
			tNoteHealthNote.setOtherInvest(noteFromCoreBodyHealthInfo.getOther_invest());;//其他核保师的说明
			//提交
			healthDao.insertNoteHealth(tNoteHealthNote);
			
			NoteFromCoreBodyHealthItems noteFromCoreBodyHealthItems = noteFromCoreBodyHealthInfo.getHealthItems();
			if(noteFromCoreBodyHealthItems.getCount() != "0"){
				List<NoteFromCoreBodyHealthItem> healthItemList = noteFromCoreBodyHealthItems.getHealthItem();
				List<TNoteHealthNoteItem> insertTNoteHealthNoteItemList = new ArrayList<TNoteHealthNoteItem>();
				for(NoteFromCoreBodyHealthItem noteFromCoreBodyHealthItem:healthItemList){
					TNoteHealthNoteItem tNoteHealthNoteItem = new TNoteHealthNoteItem();
					tNoteHealthNoteItem.setHealthNoteId(tNoteHealthNote.getHealthNoteId());
					tNoteHealthNoteItem.setNoteItemId(getMaxNo.getMaxNo());
					tNoteHealthNoteItem.setNoteItemType(noteFromCoreBodyHealthItem.getNote_item_type());
					tNoteHealthNoteItem.setNoteItemStatus(Constant.answer_N);
					insertTNoteHealthNoteItemList.add(tNoteHealthNoteItem);
				}
				//提交
				healthDao.insertNoteHealthItem(insertTNoteHealthNoteItemList);
			}
			noteTraceService.saveNoteTrace(note_id, tNoteClientNote.getClientNoteId(), Constant.note_from_core_type_HEALTH, 
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,Constant.note_from_core_type_HEALTH)+
					DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,Constant.note_status_UNPUSH));
		}
	}
	
	/**
	 * 处理财务函
	 * @param note_id 函件id
	 * @param Physicalinfos 财务函函件列表
	 * @param noteFromCoreBody 报文体
	 */
	private void clientFinaOocuInfos (String note_id, NoteFromCoreBodyFinaOocuInfos noteFromCoreBodyFinaOocuInfos,NoteFromCoreBody noteFromCoreBody) {
		List<NoteFromCoreBodyFinaOocuInfo> finaOocuInfoList = noteFromCoreBodyFinaOocuInfos.getFinaOocuInfo();
		for(NoteFromCoreBodyFinaOocuInfo noteFromCoreBodyFinaOocuInfo:finaOocuInfoList){
			
			//判断是否有未处理完成的数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("note_id", note_id);
			map.put("note_type", Constant.client_type_FINA);
			map.put("note_status_writeoff", Constant.fina_note_status_WRITEOFF);
			map.put("note_status_linedown", Constant.fina_note_status_LINEDOWN);
			
			String tNoteClientNoteCount = clientDao.selectNoteclientExists(map);
			Assert.Compare("0".equals(tNoteClientNoteCount), "存在未处理完成的财务函，不能进行函件下发！");
			
			TNoteClientNote tNoteClientNote = new TNoteClientNote();
			tNoteClientNote.setNoteId(note_id); //函件id
			tNoteClientNote.setClientNoteId(getMaxNo.getMaxNo());//客户层函件id			
			tNoteClientNote.setNoteType(Constant.client_type_FINA);//函件分类
			tNoteClientNote.setNoteBarCode(noteFromCoreBodyFinaOocuInfo.getNote_bar_code());//函件条形码
			tNoteClientNote.setNoteSeq(noteFromCoreBodyFinaOocuInfo.getNote_seq());//核保任务序号
			tNoteClientNote.setClientNo(noteFromCoreBody.getClient_no());//函件对应客户客户号
			tNoteClientNote.setNoteStatus(Constant.physical_health_note_status_UNPUSH);//函件处理状态
			tNoteClientNote.setNoteStatusDesc(Constant.physical_health_note_status_UNPUSH_DESC);//函件处理状态描述
			tNoteClientNote.setNoteReason(noteFromCoreBodyFinaOocuInfo.getNote_reason());//函件下发原因
			tNoteClientNote.setNoteAgentStatus(Constant.note_agent_status_UNPUSH);//函件代理人处理状态
			tNoteClientNote.setNoteAgentStatusDesc(Constant.note_agent_status_UNPUSH_DESC);//函件代理人处理状态描述
			tNoteClientNote.setNoteClientStatus(Constant.note_client_status_UNFINISHED);//函件客户处理状态
			tNoteClientNote.setNoteClientStatusDesc(Constant.note_client_status_UNFINISHED_DESC);//函件客户处理状态描述
			tNoteClientNote.setIsAgentReport(noteFromCoreBodyFinaOocuInfo.getIs_agent_report());//是否有高额件业务员报告书
			//如果有高额件业务员报告书则生成高额件业务员报告书id
			if(Constant.answer_Y.equals(noteFromCoreBodyFinaOocuInfo.getIs_agent_report())){
				tNoteClientNote.setReportNoteId(getMaxNo.getMaxNo());
			}
			tNoteClientNote.setDecidingDate(dateFormat.parse(noteFromCoreBodyFinaOocuInfo.getDeciding_date()));// 下发时间
			tNoteClientNote.setIsSecondNote(Constant.answer_N);
			tNoteClientNote.setIsNotQualifiedNote(Constant.answer_N);
			tNoteClientNote.setNoteStatusValid(Constant.answer_Y);
			//提交
			clientDao.insertNoteClient(tNoteClientNote);

			TNoteFinaOccuNote tNoteFinaOccuNote = new TNoteFinaOccuNote();
			tNoteFinaOccuNote.setFinaNoteId(tNoteClientNote.getClientNoteId());
			tNoteFinaOccuNote.setSpecialDesc(noteFromCoreBodyFinaOocuInfo.getSpecial_desc());//核保员特别说明
			tNoteFinaOccuNote.setInsuredIdType(noteFromCoreBodyFinaOocuInfo.getInsured_id_type()); //被保人证件类型
			tNoteFinaOccuNote.setInsuredIdNo(noteFromCoreBodyFinaOocuInfo.getInsured_id_no()); //被保人证件号码
			tNoteFinaOccuNote.setInsuredPhone(noteFromCoreBodyFinaOocuInfo.getInsured_phone()); //被保人电话
			//提交
			finaDao.insertNoteFinaOccuNote(tNoteFinaOccuNote);
			
			NoteFromCoreBodyFinaItems noteFromCoreBodyFinaItems = noteFromCoreBodyFinaOocuInfo.getFinaItems();
			if(noteFromCoreBodyFinaItems.getCount() != "0"){
				List<NoteFromCoreBodyFinaItem> finaItemList = noteFromCoreBodyFinaItems.getFinaItem();
				List<TNoteFinaNoteItem> insertTNoteFinaNoteItemList = new ArrayList<TNoteFinaNoteItem>();
				for(NoteFromCoreBodyFinaItem noteFromCoreBodyFinaitem : finaItemList){
					TNoteFinaNoteItem tNoteFinaNoteItem = new TNoteFinaNoteItem();
					tNoteFinaNoteItem.setFinaNoteId(tNoteFinaOccuNote.getFinaNoteId());//财务问卷id
					tNoteFinaNoteItem.setNoteItemId(getMaxNo.getMaxNo());//财务子问卷id
					tNoteFinaNoteItem.setNoteItemType(noteFromCoreBodyFinaitem.getNote_item_type());//财务问卷类型
					tNoteFinaNoteItem.setNoteItemStatus(Constant.answer_N);
					insertTNoteFinaNoteItemList.add(tNoteFinaNoteItem);
				}
				//提交
				finaDao.insertNoteFinaItem(insertTNoteFinaNoteItemList);
			}
			noteTraceService.saveNoteTrace(note_id, tNoteClientNote.getClientNoteId(), Constant.note_from_core_type_FINAOCCU, 
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,Constant.note_from_core_type_FINAOCCU)+
					DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,Constant.note_status_UNPUSH));
		}
	}
		
	/**
	 * 处理体检函
	 * @param note_id 函件id
	 * @param noteFromCoreBodyPhysicalinfos 体检函函件列表
	 * @param noteFromCoreBody 报文体
	 */
	private void clientPhysicalinfos (String note_id,NoteFromCoreBodyPhysicalInfos noteFromCoreBodyPhysicalInfos,NoteFromCoreBody noteFromCoreBody) {
		List<NoteFromCoreBodyPhysicalInfo> physicalInfoList = noteFromCoreBodyPhysicalInfos.getPhysicalInfo();
		for(NoteFromCoreBodyPhysicalInfo noteFromCoreBodyPhysicalInfo : physicalInfoList){
			
			//判断是否有未处理完成的数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("note_id", note_id);
			map.put("note_type", Constant.client_type_PHYSICAL);
			map.put("note_status_writeoff", Constant.physical_or_health_note_status_WRITEOFF);
			map.put("note_status_cancel", Constant.physical_or_health_note_status_CANCEL);
			map.put("note_status_linedown", Constant.physical_or_health_note_status_LINEDOWN);
			
			String tNoteClientNoteCount = clientDao.selectNoteclientExists(map);
			Assert.Compare("0".equals(tNoteClientNoteCount), "存在未处理完成的体检函，不能进行函件下发！");
			
			TNoteClientNote tNoteClientNote = new TNoteClientNote();
			tNoteClientNote.setNoteId(note_id);//函件id
			tNoteClientNote.setNoteType(Constant.client_type_PHYSICAL);//函件分类
			tNoteClientNote.setClientNoteId(getMaxNo.getMaxNo());//客户层函件id
			tNoteClientNote.setNoteBarCode(noteFromCoreBodyPhysicalInfo.getNote_bar_code());//函件条形码
			tNoteClientNote.setNoteSeq(noteFromCoreBodyPhysicalInfo.getNote_seq());//核保任务序号
			tNoteClientNote.setClientNo(noteFromCoreBody.getClient_no());//函件对应客户客户号
			tNoteClientNote.setNoteStatus(Constant.physical_health_note_status_UNPUSH);//函件处理状态
			tNoteClientNote.setNoteStatusDesc(Constant.physical_health_note_status_UNPUSH_DESC);//函件处理状态描述
			tNoteClientNote.setNoteReason(noteFromCoreBodyPhysicalInfo.getNote_reason());//函件下发原因
			tNoteClientNote.setNoteAgentStatus(Constant.note_agent_status_UNPUSH);//函件代理人处理状态
			tNoteClientNote.setNoteAgentStatusDesc(Constant.note_agent_status_UNPUSH_DESC);//函件代理人处理状态描述
			tNoteClientNote.setNoteClientStatus(Constant.note_client_status_UNFINISHED);//函件客户处理状态
			tNoteClientNote.setNoteClientStatusDesc(Constant.note_client_status_UNFINISHED_DESC);//函件客户处理状态描述
			tNoteClientNote.setIsAgentReport(Constant.answer_N);//是否有高额件业务员报告书
			tNoteClientNote.setDecidingDate(dateFormat.parse(noteFromCoreBodyPhysicalInfo.getDeciding_date()));// 下发时间
			tNoteClientNote.setIsSecondNote(Constant.answer_N);
			tNoteClientNote.setIsNotQualifiedNote(Constant.answer_N);
			tNoteClientNote.setNoteStatusValid(Constant.answer_Y);
			//提交
			clientDao.insertNoteClient(tNoteClientNote);
			
			TNotePhysicalExamNote tTNotePhysicalExamNote = new TNotePhysicalExamNote();
			tTNotePhysicalExamNote.setPhysicalNoteId(tNoteClientNote.getClientNoteId());
			tTNotePhysicalExamNote.setIsSelfHealth(noteFromCoreBodyPhysicalInfo.getIs_self_health());
			//提交
			physicalDao.insertNotePhysicalExam(tTNotePhysicalExamNote);
			
			//获取体检项目信息
			NoteFromCoreBodyPhysicalItems noteFromCoreBodyPhysicalItems = noteFromCoreBodyPhysicalInfo.getPhysicalItems(); 
			//判断是否有体检项目
			if(noteFromCoreBodyPhysicalItems.getCount() != "0"){
				
				List<TNotePhysicalExamNoteItem> insertTNotePhysicalExamNoteItemList = new ArrayList<TNotePhysicalExamNoteItem>();
				List<NoteFromCoreBodyPhysicalItem> physicalItemList = noteFromCoreBodyPhysicalItems.getPhysicalItem();
				
				for(NoteFromCoreBodyPhysicalItem noteFromCoreBodyPhysicalItem:physicalItemList){
					TNotePhysicalExamNoteItem tNotePhysicalExamNoteItem = new TNotePhysicalExamNoteItem();
					tNotePhysicalExamNoteItem.setPhysicalNoteId(tTNotePhysicalExamNote.getPhysicalNoteId());
					tNotePhysicalExamNoteItem.setPhysicalItem(noteFromCoreBodyPhysicalItem.getNote_item_type());
					insertTNotePhysicalExamNoteItemList.add(tNotePhysicalExamNoteItem);
				}
				
				//提交
				physicalDao.insertPhysicalItem(insertTNotePhysicalExamNoteItemList);
			}
			noteTraceService.saveNoteTrace(note_id, tNoteClientNote.getClientNoteId(), Constant.note_from_core_type_PHYSICAL, 
					DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,Constant.note_from_core_type_PHYSICAL)+
					DataConVersion.dataConVersion(Constant.transfer_data_NOTE_STATUS,Constant.note_status_UNPUSH));
		}
	}
	/**
	 * 处理函件保单信息
	 * @param note_id 函件id
	 * @param RelevanceInfos 保单信息列表
	 */
	private void clientRelevanceInfo(String note_id,NoteFromCoreBodyRelevanceInfos noteFromCoreBodyRelevanceInfos) {
		
		List<TNoteClientRelationApply> insertTNoteClientRelationApplyList = new ArrayList<TNoteClientRelationApply>();
		
		List<NoteFromCoreBodyRelevanceInfo> relevanceInfoList = noteFromCoreBodyRelevanceInfos.getRelevanceInfo();
		for(NoteFromCoreBodyRelevanceInfo noteFromCoreBodyRelevanceinfo:relevanceInfoList ){
			TNoteClientRelationApply noteClientRelationApply = new TNoteClientRelationApply();
			noteClientRelationApply.setNoteId(note_id);
			noteClientRelationApply.setApplyBarCode(noteFromCoreBodyRelevanceinfo.getApply_bar_code());
			noteClientRelationApply.setApplicantName(noteFromCoreBodyRelevanceinfo.getApplicant_name());
			noteClientRelationApply.setApplicantPhone(noteFromCoreBodyRelevanceinfo.getApplicant_phone());
			noteClientRelationApply.setInsuredName(noteFromCoreBodyRelevanceinfo.getInsured_name());
			insertTNoteClientRelationApplyList.add(noteClientRelationApply);
		}
		
		//对信息进行提交
		noteMainDao.insertRelationApply(insertTNoteClientRelationApplyList);
	}
	
	/**
	 * 对报文体进行校验
	 * @param noteFromCoreBody
	 */
	private void CheckBodyMessage(NoteFromCoreBody noteFromCoreBody){
		//获取报问题数据
		NoteFromCoreBodyRelevanceInfos noteFromCoreBodyRelevanceInfos = noteFromCoreBody.getRelevanceInfos();//投保单信息
		NoteFromCoreBodyPhysicalInfos noteFromCoreBodyPhysicalInfos = noteFromCoreBody.getPhysicalInfos();//体检函信息
		NoteFromCoreBodyFinaOocuInfos noteFromCoreBodyFinaOocuInfos = noteFromCoreBody.getFinaOocuInfos();//财务函信息
		NoteFromCoreBodyHealthInfos noteFromCoreBodyHealthInfos = noteFromCoreBody.getHealthInfos();//健康函信息
		NoteFromCoreBodySurvivalInfos noteFromCoreBodySurvivalInfos = noteFromCoreBody.getSurvivalInfos();//契调函信息
		NoteFromCoreBodyUndwrtInfos noteFromCoreBodyUndwrtInfos = noteFromCoreBody.getUndwrtInfos();//核保函信息
		NoteFromCoreBodyProblemInfos noteFromCoreBodyProblemInfos = noteFromCoreBody.getProblemInfos();//问题件信息

		//先进行count的校验
		Assert.notEmpty(noteFromCoreBodyRelevanceInfos.getCount(), "报文体RelevanceInfos中的Count不能为空");
		Assert.notEmpty(noteFromCoreBodyPhysicalInfos.getCount(), "报文体Physicalnfos中的Count不能为空");
		Assert.notEmpty(noteFromCoreBodyFinaOocuInfos.getCount(), "报文体FinaOocuInfos中的Count不能为空");
		Assert.notEmpty(noteFromCoreBodyHealthInfos.getCount(), "报文体HealthInfos中的Count不能为空");
		Assert.notEmpty(noteFromCoreBodySurvivalInfos.getCount(), "报文体SurvivalInfos中的Count不能为空");
		Assert.notEmpty(noteFromCoreBodyUndwrtInfos.getCount(), "报文体UndwrtInfos中的Count不能为空");
		Assert.notEmpty(noteFromCoreBodyProblemInfos.getCount(), "报文体ProblemInfos中的Count不能为空");
		
		//校验公共部分必录字段
		Assert.notEmpty(noteFromCoreBody.getTaskcode(), "报文体Taskcode不能为空");

		//根据逻辑进行计算  先校验函件信息，如果存在函件信息，则对报问题非集合部分进行校验
		if(!"0".equals(noteFromCoreBodyPhysicalInfos.getCount()) || !"0".equals(noteFromCoreBodyFinaOocuInfos.getCount())
				 || !"0".equals(noteFromCoreBodyHealthInfos.getCount()) || !"0".equals(noteFromCoreBodySurvivalInfos.getCount())){
			//dody 校验
			Assert.notEmpty(noteFromCoreBody.getClient_no(), "报文体Client_no不能为空");
			Assert.notEmpty(noteFromCoreBody.getClient_name(), "报文体Client_name不能为空");
			Assert.notEmpty(noteFromCoreBody.getClient_age(), "报文体Client_age不能为空");
			Assert.notEmpty(noteFromCoreBody.getClient_idno(), "报文体Client_idno不能为空");
			Assert.notEmpty(noteFromCoreBody.getClient_sex(), "报文体Client_sex不能为空");
			Assert.notEmpty(noteFromCoreBody.getApplicant_no(), "报文体Applicant_no不能为空");
			Assert.notEmpty(noteFromCoreBody.getApplicant_name(), "报文体Applicant_name不能为空");
			Assert.notEmpty(noteFromCoreBody.getApplicant_phone(), "报文体Applicant_phone不能为空");
			Assert.notEmpty(noteFromCoreBody.getAgent_no(), "报文体Agent_no不能为空");
			Assert.notEmpty(noteFromCoreBody.getAgent_name(), "报文体Agent_name不能为空");
			Assert.notEmpty(noteFromCoreBody.getAgent_phone(), "报文体Agent_phone不能为空");
			Assert.notEmpty(noteFromCoreBody.getChannel_type(), "报文体Channel_type不能为空");
			Assert.notEmpty(noteFromCoreBody.getBranch_code(), "报文体Branch_code不能为空");
			Assert.notEmpty(noteFromCoreBody.getBranch_name(), "报文体Branch_name不能为空");
			
			Assert.isPhone(noteFromCoreBody.getApplicant_phone(), "报文体Applicant_phone不是手机号码");

			
			//团单信息校验
			Assert.Compare(!"0".equals(noteFromCoreBodyRelevanceInfos.getCount()), "RelevanceInfos中count不能为0");
			Assert.Compare(noteFromCoreBodyRelevanceInfos.getCount().equals(
					noteFromCoreBodyRelevanceInfos.getRelevanceInfo().size()+""+""), "RelevanceInfos中count和RelevanceInfo数据不匹配");
			Assert.Compare(noteFromCoreBodyPhysicalInfos.getCount().equals(
					noteFromCoreBodyPhysicalInfos.getPhysicalInfo().size()+""), "PhysicalInfos中count和Physicalinfo数据不匹配");
			Assert.Compare(noteFromCoreBodyFinaOocuInfos.getCount().equals(
					noteFromCoreBodyFinaOocuInfos.getFinaOocuInfo().size()+""), "FinaOocuInfos中count和FinaOocuInfo数据不匹配");
			Assert.Compare(noteFromCoreBodyHealthInfos.getCount().equals(
					noteFromCoreBodyHealthInfos.getHealthInfo().size()+""), "HealthInfos中count和HealthInfo数据不匹配");
			Assert.Compare(noteFromCoreBodySurvivalInfos.getCount().equals(
					noteFromCoreBodySurvivalInfos.getSurvivalInfo().size()+""), "SurvivalInfos中count和SurvivalInfo数据不匹配");
			
			
			//校验传入保单情况是否为空
			List<NoteFromCoreBodyRelevanceInfo> noteFromCoreBodyRelevanceInfoList = noteFromCoreBodyRelevanceInfos.getRelevanceInfo();
			for(NoteFromCoreBodyRelevanceInfo noteFromCoreBodyRelevanceInfo : noteFromCoreBodyRelevanceInfoList){
				Assert.notEmpty(noteFromCoreBodyRelevanceInfo.getApplicant_name(), "RelevanceInfo中Applicant_name不能为空");
				Assert.notEmpty(noteFromCoreBodyRelevanceInfo.getApplicant_phone(), "RelevanceInfo中Applicant_phone不能为空");
				Assert.notEmpty(noteFromCoreBodyRelevanceInfo.getApply_bar_code(), "RelevanceInfo中Apply_bar_code不能为空");
				Assert.notEmpty(noteFromCoreBodyRelevanceInfo.getInsured_name(), "RelevanceInfo中Insured_name不能为空");
			
				Assert.isPhone(noteFromCoreBodyRelevanceInfo.getApplicant_phone(), "RelevanceInfo中Applicant_phone不是电话号码");
			}
			
			if(!"0".equals(noteFromCoreBodyPhysicalInfos.getCount()) ){
				checkPhysicalInfos(noteFromCoreBodyPhysicalInfos);
			}
			if(!"0".equals(noteFromCoreBodyFinaOocuInfos.getCount())){
				checkFinaOocuInfos(noteFromCoreBodyFinaOocuInfos);
			}
			if(!"0".equals(noteFromCoreBodyHealthInfos.getCount())){
				checkHealthInfos(noteFromCoreBodyHealthInfos);
			}
			if(!"0".equals(noteFromCoreBodySurvivalInfos.getCount())){
				checkSurvivalInfos(noteFromCoreBodySurvivalInfos);
			}
		}
		
		if(!"0".equals(noteFromCoreBodyUndwrtInfos.getCount())){
			Assert.Compare(noteFromCoreBodyUndwrtInfos.getCount().equals(
					noteFromCoreBodyUndwrtInfos.getUndwrtInfo().size()+""+""), "UndwrtInfos中count和UndwrtInfo数据不匹配");
			checkUndwrtInfos(noteFromCoreBodyUndwrtInfos);
		}
		if(!"0".equals(noteFromCoreBodyProblemInfos.getCount())){
			Assert.Compare(noteFromCoreBodyProblemInfos.getCount().equals(
					noteFromCoreBodyProblemInfos.getProblemInfo().size()+""+""), "ProblemInfos中count和ProblemInfo数据不匹配");
			checkProblemInfos(noteFromCoreBodyProblemInfos);
		}
	}
	
	/**
	 * 问题件信息校验
	 * @param noteFromCoreBodyProblemInfos
	 */
	private void checkProblemInfos(NoteFromCoreBodyProblemInfos noteFromCoreBodyProblemInfos){
		List<NoteFromCoreBodyProblemInfo> noteFromCoreBodyProblemInfoList = noteFromCoreBodyProblemInfos.getProblemInfo();
		for(NoteFromCoreBodyProblemInfo noteFromCoreBodyProblemInfo : noteFromCoreBodyProblemInfoList){
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getNote_seq(), "ProblemInfo中的Note_seq不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getNote_bar_code(), "ProblemInfo中的Note_bar_code不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getApply_bar_code(), "ProblemInfo中的Apply_bar_code不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getApplication_no(), "ProblemInfo中的Application_no不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getApplication_name(), "ProblemInfo中的Application_name不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getApplication_phone(), "ProblemInfo中的Application_phone不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getInsured_no(), "ProblemInfo中的Insured_no不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getInsured_name(), "ProblemInfo中的Insured_name不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getInsured_age(), "ProblemInfo中的Insured_age不能为空");
//			Assert.notEmpty(noteFromCoreBodyProblemInfo.getNote_reason(), "ProblemInfo中的Note_reason不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getAgent_no(), "ProblemInfo中的Agent_no不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getAgent_name(), "ProblemInfo中的Agent_name不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getAgent_phone(), "ProblemInfo中的Agent_phone不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getChannel_type(), "ProblemInfo中的Channel_type不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getBranch_code(), "ProblemInfo中的Branch_code不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getBranch_name(), "ProblemInfo中的Branch_name不能为空");
			Assert.notEmpty(noteFromCoreBodyProblemInfo.getDeciding_date(), "ProblemInfo中的Deciding_date不能为空");

			Assert.isPhone(noteFromCoreBodyProblemInfo.getApplication_phone(), "ProblemInfo中的Application_phone不是手机号码");
			Assert.isPhone(noteFromCoreBodyProblemInfo.getAgent_phone(), "ProblemInfo中的Agent_phone不是手机号码");
			
			NoteFromCoreBodyProblemObjects noteFromCoreBodyProblemObjects =  noteFromCoreBodyProblemInfo.getProblemObjects();
			
			Assert.Compare(noteFromCoreBodyProblemObjects.getCount().equals(
					noteFromCoreBodyProblemObjects.getProblemObject().size()+""), "ProblemObjects中count和ProblemObject对象不匹配");
			
			List<NoteFromCoreBodyProblemObject> noteFromCoreBodyProblemObjectList = noteFromCoreBodyProblemObjects.getProblemObject();
			for(NoteFromCoreBodyProblemObject noteFromCoreBodyProblemObject : noteFromCoreBodyProblemObjectList){
				Assert.notEmpty(noteFromCoreBodyProblemObject.getProblem_object(), "ProblemObject中的Problem_object不能为空");
				
				NoteFromCoreBodyProblems noteFromCoreBodyProblems = noteFromCoreBodyProblemObject.getProblems();
				Assert.Compare(noteFromCoreBodyProblems.getCount().equals(
						noteFromCoreBodyProblems.getProblem().size()+""), "Problems中count和Problem的数量不匹配");
				
				List<NoteFromCoreBodyProblem> noteFromCoreBodyProblemList = noteFromCoreBodyProblems.getProblem();
				for(NoteFromCoreBodyProblem NoteFromCoreBodyProblem : noteFromCoreBodyProblemList){
					Assert.notEmpty(NoteFromCoreBodyProblem.getProblem_seq(), "Problem中的Problem_seq不能为空");
					Assert.notEmpty(NoteFromCoreBodyProblem.getProblem_Column(), "Problem中的Problem_Column不能为空");
					Assert.notEmpty(NoteFromCoreBodyProblem.getProblem_desc(), "Problem中的Problem_desc不能为空");
				}
			}
		}
	}
	
	/**
	 * 核保数据校验
	 * @param noteFromCoreBodyUndwrtInfos
	 */
	private void checkUndwrtInfos(NoteFromCoreBodyUndwrtInfos noteFromCoreBodyUndwrtInfos){
		List<NoteFromCoreBodyUndwrtInfo> noteFromCoreBodyUndwrtInfoList = noteFromCoreBodyUndwrtInfos.getUndwrtInfo();
		for(NoteFromCoreBodyUndwrtInfo noteFromCoreBodyUndwrtInfo : noteFromCoreBodyUndwrtInfoList){
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getNote_seq(), "UndwrtInfo中 Note_seq的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getNote_type(), "UndwrtInfo中Note_type 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getApply_bar_code(), "UndwrtInfo中Apply_bar_code 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getNote_bar_code(), "UndwrtInfo中Note_bar_code 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getAccount_no(), "UndwrtInfo中Account_no 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getApplication_no(), "UndwrtInfo中Application_no 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getApplication_name(), "UndwrtInfo中 Application_name的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getApplication_phone(), "UndwrtInfo中Application_phone 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getInsured_no(), "UndwrtInfo中Insured_no 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getInsured_name(), "UndwrtInfo中Insured_name 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getInsured_age(), "UndwrtInfo中Insured_age 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getAgent_no(), "UndwrtInfo中Agent_no 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getAgent_name(), "UndwrtInfo中Agent_name 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getAgent_phone(), "UndwrtInfo中Agent_phone 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getChannel_type(), "UndwrtInfo中 Channel_type的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getBranch_code(), "UndwrtInfo中Branch_code 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getBranch_name(), "UndwrtInfo中Branch_name 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getCompany_address(), "UndwrtInfo中Company_address 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getCompany_postal_code(), "UndwrtInfo中Company_postal_code 的不能为空");
			Assert.notEmpty(noteFromCoreBodyUndwrtInfo.getDeciding_date(), "UndwrtInfo中Deciding_date 的不能为空");
			
			Assert.isPhone(noteFromCoreBodyUndwrtInfo.getApplication_phone(), "UndwrtInfo中Application_phone不是电话号码");
			Assert.isPhone(noteFromCoreBodyUndwrtInfo.getAgent_phone(), "UndwrtInfo中Agent_phone不是电话号码");

			NoteFromCoreBodyProductInfos noteFromCoreBodyProductInfos = noteFromCoreBodyUndwrtInfo.getProductInfos();
			
			Assert.Compare(noteFromCoreBodyProductInfos.getCount().equals(
					noteFromCoreBodyProductInfos.getProductInfo().size()+""), "ProductInfos中count与ProductInfo数量不匹配");
			
			List<NoteFromCoreBodyProductInfo> noteFromCoreBodyProductInfoList = noteFromCoreBodyProductInfos.getProductInfo();
			for(NoteFromCoreBodyProductInfo noteFromCoreBodyProductInfo : noteFromCoreBodyProductInfoList){
				Assert.notEmpty(noteFromCoreBodyProductInfo.getProduct_code(), "ProductInfo中的Product_code不能为空");
				Assert.notEmpty(noteFromCoreBodyProductInfo.getProduct_name(), "ProductInfo中的Product_name不能为空");
				Assert.notEmpty(noteFromCoreBodyProductInfo.getUndwrt_result(), "ProductInfo中的Undwrt_result不能为空");
				Assert.notEmpty(noteFromCoreBodyProductInfo.getUndwrt_result_reason(), "ProductInfo中的Undwrt_result_reason不能为空");
			}
		}
	}
	
	/**
	 * 核保函数据校验
	 * @param noteFromCoreBodySurvivalInfos
	 */
	private void checkSurvivalInfos(NoteFromCoreBodySurvivalInfos noteFromCoreBodySurvivalInfos){
		List<NoteFromCoreBodySurvivalInfo> noteFromCoreBodySurvivalInfoList = noteFromCoreBodySurvivalInfos.getSurvivalInfo();
		for(NoteFromCoreBodySurvivalInfo noteFromCoreBodySurvivalInfo : noteFromCoreBodySurvivalInfoList){
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getNote_seq(), "SurvivalInfo中Note_seq不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getNote_bar_code(), "SurvivalInfo中Note_bar_code不能为空");
//			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getNote_reason(), "SurvivalInfo中Note_reason不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getClient_idno(), "SurvivalInfo中Client_idno不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getAgent_file(), "SurvivalInfo中Agent_file不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getOther_invest(), "SurvivalInfo中Other_invest不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getSurvival_reason(), "SurvivalInfo中Survival_reason不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getSurvival_mode(), "SurvivalInfo中Survival_mode不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getSpecial_desc(), "SurvivalInfo中Special_desc不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getSurvival_branch_province(), "SurvivalInfo中Survival_branch_province不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getSurvival_branch_code(), "SurvivalInfo中Survival_branch_code不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getAgent_branch_address(), "SurvivalInfo中Agent_branch_address不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getSurvival_date(), "SurvivalInfo中Survival_Date不能为空");
			Assert.notEmpty(noteFromCoreBodySurvivalInfo.getDeciding_date(), "SurvivalInfo中Deciding_date不能为空");
			
			NoteFromCoreBodySurvivalItems noteFromCoreBodySurvivalItems = noteFromCoreBodySurvivalInfo.getSurvivalItems();
			Assert.Compare(noteFromCoreBodySurvivalItems.getCount().equals(
					noteFromCoreBodySurvivalItems.getSurvivalItem().size()+""), "SurvivalItems中count值和SurvivalItem数量不匹配");
			for(NoteFromCoreBodySurvivalItem noteFromCoreBodySurvivalItem : noteFromCoreBodySurvivalItems.getSurvivalItem()){
				Assert.notEmpty(noteFromCoreBodySurvivalItem.getNote_item_type(), "SurvivalItem中Note_item_type不能为空");
			}
			
		}
	}
	
	/**
	 * 健康函信息校验
	 * @param noteFromCoreBodyHealthInfos
	 */
	private void checkHealthInfos(NoteFromCoreBodyHealthInfos noteFromCoreBodyHealthInfos){
		List<NoteFromCoreBodyHealthInfo> noteFromCoreBodyHealthInfoList = noteFromCoreBodyHealthInfos.getHealthInfo();
		for(NoteFromCoreBodyHealthInfo noteFromCoreBodyHealthInfo : noteFromCoreBodyHealthInfoList){
			Assert.notEmpty(noteFromCoreBodyHealthInfo.getNote_seq(), "HealthInfo中Note_seq 不能为空");
			Assert.notEmpty(noteFromCoreBodyHealthInfo.getNote_bar_code(), "HealthInfo中Note_bar_code 不能为空");
//			Assert.notEmpty(noteFromCoreBodyHealthInfo.getNote_reason(), "HealthInfo中 Note_reason不能为空");
			Assert.notEmpty(noteFromCoreBodyHealthInfo.getSpecial_desc(), "HealthInfo中Special_desc 不能为空");
			Assert.notEmpty(noteFromCoreBodyHealthInfo.getOther_invest(), "HealthInfo中 Other_invest不能为空");
			Assert.notEmpty(noteFromCoreBodyHealthInfo.getDeciding_date(), "HealthInfo中 Deciding_date不能为空");
			
			NoteFromCoreBodyHealthItems noteFromCoreBodyHealthItems = noteFromCoreBodyHealthInfo.getHealthItems();
			Assert.Compare(noteFromCoreBodyHealthItems.getCount().equals(
					noteFromCoreBodyHealthItems.getHealthItem().size()+""), "HealthItems中count值和HealthItem数量不匹配");
			for(NoteFromCoreBodyHealthItem noteFromCoreBodyHealthItem : noteFromCoreBodyHealthItems.getHealthItem()){
				Assert.notEmpty(noteFromCoreBodyHealthItem.getNote_item_type(), "HealthItem中Note_item_type不能为空");
			}
		}
	}
	/**
	 * 财务函数据校验
	 * @param noteFromCoreBodyFinaOocuInfos
	 */
	private void checkFinaOocuInfos(NoteFromCoreBodyFinaOocuInfos noteFromCoreBodyFinaOocuInfos){
		List<NoteFromCoreBodyFinaOocuInfo> noteFromCoreBodyFinaOocuInfoList = noteFromCoreBodyFinaOocuInfos.getFinaOocuInfo();
		for(NoteFromCoreBodyFinaOocuInfo noteFromCoreBodyFinaOocuInfo : noteFromCoreBodyFinaOocuInfoList){
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getNote_seq(), "FinaOocuInfo中 Note_seq不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getNote_bar_code(), "FinaOocuInfo中 Note_bar_code不能为空");
//			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getNote_reason(), "FinaOocuInfo中Note_reason 不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getSpecial_desc(), "FinaOocuInfo中Special_desc 不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getIs_agent_report(), "FinaOocuInfo中 Is_agent_report不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getDeciding_date(), "FinaOocuInfo中Deciding_date 不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getInsured_id_no(), "FinaOocuInfo中Insured_id_no 不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getInsured_id_type(), "FinaOocuInfo中 Insured_id_type不能为空");
			Assert.notEmpty(noteFromCoreBodyFinaOocuInfo.getInsured_phone(), "FinaOocuInfo中 Insured_phone不能为空");
			Assert.isPhone(noteFromCoreBodyFinaOocuInfo.getInsured_phone(), "FinaOocuInfo中 Insured_phone不是电话号码");

			NoteFromCoreBodyFinaItems noteFromCoreBodyFinaItems  = noteFromCoreBodyFinaOocuInfo.getFinaItems();
			
			Assert.Compare(noteFromCoreBodyFinaItems.getCount().equals(
					noteFromCoreBodyFinaItems.getFinaItem().size()+""), "Finaitems中count和Finaitem数量不匹配");
			for(NoteFromCoreBodyFinaItem noteFromCoreBodyFinaitem : noteFromCoreBodyFinaItems.getFinaItem()){
				Assert.notEmpty(noteFromCoreBodyFinaitem.getNote_item_type(), "Finaitem中 Note_item_type不能为空");
			}
		}
	}
	
	/**
	 * 对体检信息进行校验
	 * @param noteFromCoreBodyPhysicalinfos
	 */
	private void checkPhysicalInfos(NoteFromCoreBodyPhysicalInfos noteFromCoreBodyPhysicalInfos){
		List<NoteFromCoreBodyPhysicalInfo> noteFromCoreBodyPhysicalInfoList = noteFromCoreBodyPhysicalInfos.getPhysicalInfo();
		for(NoteFromCoreBodyPhysicalInfo noteFromCoreBodyPhysicalInfo : noteFromCoreBodyPhysicalInfoList){
			Assert.notEmpty(noteFromCoreBodyPhysicalInfo.getNote_seq(), "Physicalinfo中Note_seq 不能为空");
			Assert.notEmpty(noteFromCoreBodyPhysicalInfo.getNote_bar_code(), "Physicalinfo中Note_bar_code 不能为空");
//			Assert.notEmpty(noteFromCoreBodyPhysicalInfo.getNote_reason(), "Physicalinfo中Note_reason 不能为空");
			Assert.notEmpty(noteFromCoreBodyPhysicalInfo.getIs_self_health(), "Physicalinfo中Is_self_health 不能为空");
			Assert.notEmpty(noteFromCoreBodyPhysicalInfo.getDeciding_date(), "Physicalinfo中Deciding_date不能为空");
			
			NoteFromCoreBodyPhysicalItems noteFromCoreBodyPhysicalItems = noteFromCoreBodyPhysicalInfo.getPhysicalItems();
			List<NoteFromCoreBodyPhysicalItem> noteFromCoreBodyPhysicalItemList = noteFromCoreBodyPhysicalItems.getPhysicalItem();
			Assert.Compare(noteFromCoreBodyPhysicalItems.getCount().equals(
					noteFromCoreBodyPhysicalItemList.size()+""), "PhysicalItems中的count和PhysicalItem数量不匹配");
			for(NoteFromCoreBodyPhysicalItem noteFromCoreBodyPhysicalItem : noteFromCoreBodyPhysicalItemList){
				Assert.notEmpty(noteFromCoreBodyPhysicalItem.getNote_item_type(), "PhysicalItem中有Note_item_type为空");
			}
		}
	}
}
