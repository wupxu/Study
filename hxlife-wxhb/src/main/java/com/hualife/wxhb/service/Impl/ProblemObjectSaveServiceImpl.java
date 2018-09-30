package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.ProblemInfo;
import com.hualife.wxhb.api.rest.message.request.ProblemObjectSaveRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.domain.dto.ProblemNoteAnswerInfo;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.ProblemObjectSaveService;

/**
 * @author zhangweiwei
 * @description 保存问题件的问卷信息
 * @date 2017-08-03
 */
@Service
public class ProblemObjectSaveServiceImpl implements ProblemObjectSaveService{
	
	private final Logger logger = LoggerFactory.getLogger(ProblemObjectSaveServiceImpl.class);
	
	@Autowired
	private ProblemDao problemDao;
	
	/**
	 * 保存问题件的问卷信息
	 * **/
	@Override
	@Transactional
	public void problemObjectSave(ProblemObjectSaveRequestMessage problemObjectSaveRequestMessage) {
		//检查请求报文
		checkData(problemObjectSaveRequestMessage);
		//检查问题件对象信息是否存在
		String problemNoteId=problemObjectSaveRequestMessage.getProblem_note_id();
		String problemObject=problemObjectSaveRequestMessage.getProblem_object();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "批量更新问题内容");;
		//获取时间
		long startTime =0;
		long endTime =0;
		HashMap<String,Object> problemObjectIdMap=new HashMap<>();
		problemObjectIdMap.put("problem_note_id", problemNoteId);
		problemObjectIdMap.put("problem_object", problemObject);

		startTime=System.currentTimeMillis();
		String problemObjectId=problemDao.getProblemObjectIdByNoteId(problemObjectIdMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"问题件id："+problemNoteId, "根据函件id查询问题件--耗时:"+(endTime-startTime)+"ms");;

		if(StringUtils.isNotBlank(problemObjectId)){
			//批量更新问题内容
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemNoteId, "批量更新问题内容");;	
			List<ProblemNoteAnswerInfo> problemNoteAnswerInfoList = new ArrayList<ProblemNoteAnswerInfo>();
			List<ProblemInfo> problemInfoList = problemObjectSaveRequestMessage.getProblemInfos();
			for(ProblemInfo problemInfo:problemInfoList){
				ProblemNoteAnswerInfo problemNoteAnswerInfo = new ProblemNoteAnswerInfo();
				problemNoteAnswerInfo.setProblem_object_id(problemObjectId);
				problemNoteAnswerInfo.setProblem_seq(problemInfo.getProblem_seq());
				problemNoteAnswerInfo.setProblem_answer(problemInfo.getProblem_answer());
				problemNoteAnswerInfoList.add(problemNoteAnswerInfo);
			}
			startTime=System.currentTimeMillis();
			//批量更新问题内容
			problemDao.batchUpdateProblemInfoList(problemNoteAnswerInfoList);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"问题件id："+problemNoteId, "//批量更新问题内容--耗时:"+(endTime-startTime)+"ms");;

			//更新问题件对象处理状态
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemObjectId, "更新问题件对象处理状态");;
			HashMap<String,Object> problemObjectMap = new HashMap<>();
			problemObjectMap.put("problem_object_id", problemObjectId);
			problemObjectMap.put("problem_object_status",Constant.problem_object_status_FINISHED);
			startTime=System.currentTimeMillis();
			problemDao.updateProblemObject(problemObjectMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"问题件id："+problemNoteId, "//批量更新问题内容--耗时:"+(endTime-startTime)+"ms");;
		}else{
			throw new BusinessException("此函件id"+problemNoteId+"的问题件不存在!");
		}	
		
	}
	
	/**
	 * 检查请求报文
	 * **/
	private void checkData(ProblemObjectSaveRequestMessage problemObjectSaveRequestMessage) {
		
		Assert.notNull(problemObjectSaveRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemObjectSaveRequestMessage.getProblem_note_id(), "开始检查请求报文");;
		Assert.notEmpty(problemObjectSaveRequestMessage.getProblem_note_id(),"问题件id不能为空");
		Assert.notEmpty(problemObjectSaveRequestMessage.getProblem_object(), "问题件对象不能为空");
		List<ProblemInfo> problemInfos=problemObjectSaveRequestMessage.getProblemInfos();
		if(problemInfos.size()>0){
			for(ProblemInfo problemInfo:problemInfos){
				Assert.notEmpty(problemInfo.getProblem_seq(),"问题编号不能为空");
				Assert.notEmpty(problemInfo.getProblem_answer(),"问题答案不能为空");
			}
		}else{
			throw new BusinessException("此函件id"+problemObjectSaveRequestMessage.getProblem_note_id()+"没有填写问卷!");
		}
	}
}
