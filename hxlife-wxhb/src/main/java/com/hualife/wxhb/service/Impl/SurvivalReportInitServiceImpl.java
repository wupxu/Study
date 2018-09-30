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
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.ProblemContent;
import com.hualife.wxhb.api.rest.message.request.SurvivalReportInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalReportInitResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.SurvivalReportInitService;

/**
 * @description : 契调报告初始化serviceImpl
 * @author : linyongtao
 * @date : 2017-08-17
 */
@Service
public class SurvivalReportInitServiceImpl   implements SurvivalReportInitService{
	private final Logger logger = LoggerFactory.getLogger(SurvivalReportInitServiceImpl.class);
	@Autowired
	private SurvivalDao survivalDao;
	@Autowired
	private ClientDao clientDao;
	/**
	 * @description : 契调报告初始化
	 * @author : linyongtao
	 * @date : 2017-08-17
	 * **/
	@Override
	public SurvivalReportInitResponseMessage selectSurvivalReport(SurvivalReportInitRequestMessage survivalReportInitRequestMessage) {
		checkData(survivalReportInitRequestMessage);
		long startTime = 0;
		long endTime =0;
		//获取请求参数中的survival_noe_id 契调函id
		String survivalNoteId = survivalReportInitRequestMessage.getSurvival_note_id();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "获取请求参数");
		SurvivalReportInitResponseMessage  survivalReportInitResponseMessage = new SurvivalReportInitResponseMessage();
		//声明返回的问卷问题内容+影像资料+保单信息
		List<ProblemContent> problemContentList = new ArrayList<ProblemContent>();	
		List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();		
		List<AppliInfo> appliInfoList = new ArrayList<AppliInfo>();	
		//判断前端传入的survivalNoteId是否存在
		Long number =clientDao.selectExitByClientNoteId(survivalNoteId);
		if(number==0){
			throw new BusinessException("契调任务报告书初始化接口,前端传入的参数契调函id--survivalNoteId:"+survivalNoteId+"错误!");
		}
		//查询该契调函的所属的note_id
		String noteId = survivalDao.getNoteIDBySurvivalNoteId(survivalNoteId);
		
		//查询契调函报告的投保人姓名+被保人姓名+投保单号+查询契约调查报告内容
		startTime =System.currentTimeMillis();
		appliInfoList = survivalDao.selectApplayInfo(survivalNoteId);
		endTime =System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "根据契调函id查询契调报告下的保单信息--耗时:"+(endTime-startTime)+"ms");
		Map<String,Object> getImageMap = new HashMap<>();
		getImageMap.put("survivalNoteId", survivalNoteId);
		getImageMap.put("noteType", Constant.client_type_SURVIVAL);	
		getImageMap.put("imageTypeSIGNATURE", Constant.image_type_Survival_Investigation_Report_SIGNATURE);	
		getImageMap.put("imageTypeUPLOAD", Constant.image_type_Survival_Investigation_Report_UPLOAD_PICTURES);	
		getImageMap.put("noteType", Constant.client_type_SURVIVAL);			
		getImageMap.put("imageStatus", Constant.valid_Y);		
		//查询契调函报告的客户上传的影像资料
		startTime =System.currentTimeMillis();
		imageInfoList = survivalDao.getImageInfo(getImageMap);
		endTime =System.currentTimeMillis();
		for (ImageInfo imageInfo2 : imageInfoList) {
			String ossPath=Constant.CHANNEL_NO+imageInfo2.getImage_url();
			String imageUrl=OSSUtil.getUrl(ossPath);
			imageInfo2.setImage_url(imageUrl);
		}
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "根据契调函id查询契调报告下客户上传的影像信息--耗时:"+(endTime-startTime)+"ms");

		//查询契调函报告内容
		Map<String,Object> getProblemMap = new HashMap<>();
		getProblemMap.put("survivalNoteId", survivalNoteId);
		getProblemMap.put("noteItemStatus", Constant.item_status_Y);//问卷已完成
		getProblemMap.put("isValid",Constant.valid_Y);//当前这个问题有效
		startTime =System.currentTimeMillis();
		problemContentList = survivalDao.getProblemContents(getProblemMap);
		endTime =System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "根据契调函id查询契调报告客户填写的问题内容答案--耗时:"+(endTime-startTime)+"ms");
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalNoteId, "组织返回报文");
		survivalReportInitResponseMessage.setNote_id(noteId);
		survivalReportInitResponseMessage.setImageInfo(imageInfoList);
		survivalReportInitResponseMessage.setAppliInfo(appliInfoList);
		survivalReportInitResponseMessage.setProblemContent(problemContentList);		
		return survivalReportInitResponseMessage;
	}	
	/**
	 * 校验请求报文
	 * **/
	private void checkData(SurvivalReportInitRequestMessage survivalReportInitRequestMessage) {
		Assert.notNull(survivalReportInitRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalReportInitRequestMessage.getSurvival_note_id(), "开始检查请求报文");
		Assert.notEmpty(survivalReportInitRequestMessage.getSurvival_note_id(), "契调函id不能为空");		
	}
}
