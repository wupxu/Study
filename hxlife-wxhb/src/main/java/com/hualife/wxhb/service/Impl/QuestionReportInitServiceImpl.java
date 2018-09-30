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
import com.hualife.wxhb.api.rest.message.request.QuestionRopertInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.QuestionReportInitResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.FinaDao;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.service.QuestionReportInitService;
@Service
public class QuestionReportInitServiceImpl implements QuestionReportInitService{
	@Autowired
	private FinaDao finaDao;
	@Autowired
	private HealthDao healthDao;
	@Autowired
	private ClientDao clientDao;
	private final Logger logger = LoggerFactory.getLogger(PhysicalLetterServiceImpl.class);
	List<ImageInfo> tNoteImageList = new ArrayList<ImageInfo>();
	long startTime =0;
	long endTime =0;
	/**
	 * 问卷和报告初始化
	 * **/
	@Override
	public QuestionReportInitResponseMessage questionReportInit(QuestionRopertInitRequestMessage questionRopertInitRequestMessage) {
		checkData(questionRopertInitRequestMessage);
		String isFinish ="";
		QuestionReportInitResponseMessage questionReportInitResponseMessage =new QuestionReportInitResponseMessage();		
		//获取请求参数
		String noteId =questionRopertInitRequestMessage.getNote_id();
		String noteType =questionRopertInitRequestMessage.getNote_type();

		//判断前段传过来的note_id是否存在
		startTime =System.currentTimeMillis();
		Long c = clientDao.selectExitByNoteId(noteId);
		endTime =System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "查询前端传入的note_id是否存在--耗时:"+(endTime-startTime)+"ms");
		if( c > 0 ){
			Map<String,Object> isFinishMap =new HashMap<>();
			isFinishMap.put("noteId", noteId);
			//高额业务员报告书
			if(noteType.equals(Constant.note_agent_report_CLIENT)){
				dealAgentReport(noteId);
				isFinishMap.put("noteType", Constant.client_type_FINA);
				isFinish = finaDao.getIsFinishAgentReport(isFinishMap);						
			}
			//处理财务函问卷
			if(noteType.equals(Constant.client_type_FINA)){
				String noteItemType =questionRopertInitRequestMessage.getNote_item_type();
				dealFinaReport(noteId,noteItemType);
				isFinishMap.put("noteType", noteType);
				isFinishMap.put("noteItemType", noteItemType);
				isFinish = finaDao.getIsFinishFinaReport(isFinishMap);
			}
			//处理健康函问卷
			if(noteType.equals(Constant.client_type_HEALTH)){
				String noteItemType =questionRopertInitRequestMessage.getNote_item_type();
				dealHealthReport(noteId,noteItemType);
				isFinishMap.put("noteType", noteType);
				isFinishMap.put("noteItemType", noteItemType);
				isFinish = healthDao.getIsFinishHealthReport(isFinishMap);	
			}		
		}else{
			throw new BusinessException("初始化问卷类型为:"+noteType+",前端传入的note_id不正确!");
		}
		if(isFinish==null||isFinish.equals("")||isFinish.equals("N")){
			isFinish = Constant.item_status_N;
		}
		
		//拼接影像的url路径
		for (ImageInfo imageInfo : tNoteImageList) {
			String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
			String imageUrl=OSSUtil.getUrl(ossPath);
			imageInfo.setImage_url(imageUrl);
		}
		
		//组织报文
		questionReportInitResponseMessage.setIsFinish(isFinish);
		questionReportInitResponseMessage.setImageInfo(tNoteImageList);
		return questionReportInitResponseMessage;
		
	}		
	/**
	 * 处理高额业务员报告书问卷部分
	 * **/
	public List<ImageInfo> dealAgentReport(String noteId){
		Map<String,Object> getImageInfosMap =new HashMap<>();
		getImageInfosMap.put("noteId", noteId);
		getImageInfosMap.put("notetype", Constant.client_type_FINA);
		getImageInfosMap.put("imageType", Constant.image_type_Fina_AGENT_REPORT_SIGNATURE);
		getImageInfosMap.put("imageStatus", Constant.valid_Y);	
		startTime =System.currentTimeMillis();
		tNoteImageList=finaDao.getAgentReportImageInfo(getImageInfosMap);		
		endTime =System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "查询高额业务员报告书客户上传影像资料--耗时:"+(endTime-startTime)+"ms");
		return tNoteImageList;		
	}
	/**
	 * 处理财务函问卷部分
	 * **/
	public List<ImageInfo> dealFinaReport(String noteId,String noteItemType){
		Map<String,Object> getImageInfosMap =new HashMap<>();
		getImageInfosMap.put("noteId", noteId);
		getImageInfosMap.put("notetype",Constant.client_type_FINA);
		getImageInfosMap.put("imageStatus", Constant.valid_Y);
		if(noteItemType.equals(Constant.finl_note_item_type_FINL)){		
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Fina_FINL_TAKE_SIGNATURE);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Fina_FINL_UPLOAD_PICTURES);
		}else{
			throw new BusinessException("未匹配到该类型:"+noteItemType+"的财务问卷");
		}
		startTime =System.currentTimeMillis();
		tNoteImageList=finaDao.getFinaReportImageInfo(getImageInfosMap);
		endTime =System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "查询财务函问卷客户上传影像资料--耗时:"+(endTime-startTime)+"ms");
		return tNoteImageList;		
	}
	/**
	 * 处理健康函问卷部分
	 * **/
	public List<ImageInfo> dealHealthReport(String noteId,String noteItemType){
		Map<String,Object> getImageInfosMap =new HashMap<>();
		getImageInfosMap.put("noteId", noteId);
		getImageInfosMap.put("notetype", Constant.client_type_HEALTH);
		getImageInfosMap.put("imageStatus", Constant.valid_Y);

		if(noteItemType.equals(Constant.health_note_item_type_LIVER)){//肝脏疾病调查问卷--4			
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Health_LIVER_TAKE_PICTURES);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Health_LIVER_TAKE_SIGNATURE);
		}else		
		if(noteItemType.equals(Constant.health_note_item_type_HYPERTENSION)){//高血压调查问卷--2
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Health_HYPERTENSION_TAKE_PICTURES);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Health_HYPERTENSION_TAKE_SIGNATURE);
		}else	
		if(noteItemType.equals(Constant.health_note_item_type_PAST_ILLNESSPAST)){//既往疾病调查问卷--1
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Health_PAST_ILLNESSPAST_TAKE_PICTURES);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Health_PAST_ILLNESSPAST_TAKE_SIGNATURE);
		}else		
		if(noteItemType.equals(Constant.health_note_item_type_THYROID_DISEASE)){//甲状腺疾病调查问卷--11
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Health_THYROID_DISEASE_TAKE_PICTURES);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Health_THYROID_DISEASE_TAKE_SIGNATURE);
		}else		
		if(noteItemType.equals(Constant.health_note_item_type_FEMALE_DISEASE)){//女性疾病调查问卷--5
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Health_FEMALE_DISEASE_TAKE_PICTURES);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Health_FEMALE_DISEASE_TAKE_SIGNATURE);
		}else		
		if(noteItemType.equals(Constant.health_note_item_type_DIGESTIVE_SYSTEM_DISEASE)){//消化系统疾病调查问卷--9
			getImageInfosMap.put("imageTypePictures", Constant.image_type_Health_DIGESTIVE_SYSTEM_DISEASE_TAKE_PICTURES);
			getImageInfosMap.put("imageTypeSignature", Constant.image_type_Health_DIGESTIVE_SYSTEM_DISEASE_TAKE_SIGNATURE);
		}else{
			throw new BusinessException("未匹配到该类型:"+noteItemType+"的健康问卷");
		}
		startTime =System.currentTimeMillis();
		tNoteImageList=healthDao.getHealthReportImageInfo(getImageInfosMap);	
		endTime =System.currentTimeMillis();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "查询健康函问卷客户上传影像资料--耗时:"+(endTime-startTime)+"ms");
		return tNoteImageList;		
	}
	
	/**
	 * 检查请求参数报文
	 * **/
	public boolean checkData(QuestionRopertInitRequestMessage questionRopertInitRequestMessage){
		Assert.notNull(questionRopertInitRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),questionRopertInitRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(questionRopertInitRequestMessage.getNote_id(), "函件id不能为空");
		Assert.notEmpty(questionRopertInitRequestMessage.getNote_type(), "不能为空");	
		if(questionRopertInitRequestMessage.getNote_type().equals(Constant.client_type_HEALTH)||questionRopertInitRequestMessage.getNote_type().equals(Constant.client_type_PHYSICAL)){
			Assert.notEmpty(questionRopertInitRequestMessage.getNote_item_type(), questionRopertInitRequestMessage.getNote_type()+"--问卷类型不能为空");								
		}				
		return true;		
	}
}
