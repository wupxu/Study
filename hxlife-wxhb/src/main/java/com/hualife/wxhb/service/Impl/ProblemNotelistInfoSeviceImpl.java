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
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.request.ProblemInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ProblemInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.dto.ProblemObj;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.ProblemNotelistInfoService;

/**
 * @author 吴培旭
 * @description 问题件列表初始化
 * @time 创建时间：2017年8月8日
 */
@Service
public class ProblemNotelistInfoSeviceImpl implements ProblemNotelistInfoService {
	// log日志
	private final Logger logger = LoggerFactory.getLogger(ProblemNotelistInfoSeviceImpl.class);
	@Autowired
	private ProblemDao problemDao;
	/**
	 * @author 吴培旭
	 * @description 问题件列表初始化
	 * @date 创建时间：2017年8月8日
	 */
	@Override
	public ProblemInfoResponseMessage problemNotelistInfo(ProblemInfoRequestMessage problemInfoRequestMessage) {
		long startTime=0;
		long endTime=0;
		// 非空校验
		checkData(problemInfoRequestMessage);
		// 声明返回报文
		ProblemInfoResponseMessage problemInfoResponseMessage = new ProblemInfoResponseMessage();
		// 声明问题集合
		List<ProblemObj> problemObjList = new ArrayList<>();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(), "查询需要的信息");
		// 投保人是否身份验证
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询投保人是否身份验证");
		startTime = System.currentTimeMillis(); 
		String phoneSuccess = problemDao.selectPhoneSuccess(problemInfoRequestMessage);
		endTime = System.currentTimeMillis(); 
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(),"查询投保人是否身份验证,程序运行时间： " + (endTime - startTime) + "ms");
		
		// 查询代理人姓名，代理电话，投保人姓名，投保人电话，被保人姓名
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询代理人姓名，代理电话，投保人姓名，投保人电话，被保人姓名");
		startTime = System.currentTimeMillis(); 
		problemInfoResponseMessage = problemDao.selectProblemNolMes(problemInfoRequestMessage);
		if (problemInfoResponseMessage==null) {
			throw new BusinessException("根据problemNoteId查询出的结果为空");
		}
		endTime = System.currentTimeMillis(); 
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(),"查询代理人姓名，代理电话，投保人姓名，投保人电话，被保人姓名,程序运行时间： " + (endTime - startTime) + "ms");
		if (phoneSuccess!=null) {
			problemInfoResponseMessage.setPhoneSuccess(phoneSuccess);
		}
		Map<String, String> proMap = new HashMap<>();
		proMap.put("validY", Constant.valid_Y);
		proMap.put("problemNoteId", problemInfoRequestMessage.getProblemNoteId());
		proMap.put("noteTypeProblem", Constant.note_from_core_type_PROBLEM);
		
		// 查询影像集合
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询影像集合");
		List<ImageInfo> probleminfoImagesList = new ArrayList<>();
		startTime = System.currentTimeMillis(); 
		probleminfoImagesList = problemDao.getProbinfoImages(proMap);
		for (ImageInfo imageInfo : probleminfoImagesList) {
				String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
				String imageUrl=OSSUtil.getUrl(ossPath);
				imageInfo.setImage_url(imageUrl);
		}
		endTime = System.currentTimeMillis(); 
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(),"查询影像集合,程序运行时间： " + (endTime - startTime) + "ms");
		// 查询问题件外层集合
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询问题件外层集合");
		startTime = System.currentTimeMillis();
		problemObjList = problemDao.selectProblemObjects(problemInfoRequestMessage.getProblemNoteId());
		endTime = System.currentTimeMillis(); 
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(),"查询影像集合,程序运行时间： " + (endTime - startTime) + "ms");
		for (ProblemObj pro : problemObjList) {
			// 问题详细内层集合查询并set进外层集合
			Map<String, String> delMap = new HashMap<>();
			delMap.put("problemNoteId", problemInfoRequestMessage.getProblemNoteId());
			delMap.put("problemObject", pro.getProblemObject());
			startTime = System.currentTimeMillis(); 
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
					"问题详细内层集合查询并set进外层集合");
			pro.setProblem_descs(problemDao.selectProblemDescs(delMap));
			endTime = System.currentTimeMillis(); 
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(),"问题详细内层集合查询并set进外层集合,程序运行时间： " + (endTime - startTime) + "ms");
		}
		// 组织报文
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(),"组织返回报文");
		problemInfoResponseMessage.setImage(probleminfoImagesList);
		problemInfoResponseMessage.setProblemObjects(problemObjList);
		return problemInfoResponseMessage;
	}
	
	private void checkData(ProblemInfoRequestMessage problemInfoRequestMessage) {
		Assert.notNull(problemInfoRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),problemInfoRequestMessage.getProblemNoteId(), "问题件初始化开始检查报文");
		Assert.notEmpty(problemInfoRequestMessage.getProblemNoteId(), "问题件id不能null");
	}
}
