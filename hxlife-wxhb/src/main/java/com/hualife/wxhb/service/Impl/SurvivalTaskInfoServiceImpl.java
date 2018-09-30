package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.request.SurvivalTaskRequestMessage;
import com.hualife.wxhb.api.rest.message.response.SurvivalTaskResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.SurvivalTaskInfoService;
/**
 * @author zhangweiwei
 * @description 查看影像信息impl
 * @date 2017-08-04
 */
@Service
public class SurvivalTaskInfoServiceImpl implements SurvivalTaskInfoService{
	
	private final Logger logger = LoggerFactory.getLogger(SurvivalTaskInfoServiceImpl.class);

	@Autowired
	private SurvivalDao survivalDao;
	/**
	 * 查看影像信息
	 */
	@Override
	public SurvivalTaskResponseMessage getImageList(SurvivalTaskRequestMessage survivalTaskRequestMessage) {
		
		//检查请求报文
		checkData(survivalTaskRequestMessage);
		long startTime = 0;
		long endTime   = 0;
		String survivalNoteId=survivalTaskRequestMessage.getSurvival_note_id();
		
		//查询契调函id是否存在
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNoteId, "查询契调函id是否存在");
		startTime = System.currentTimeMillis();
		TNoteClientNote tNoteClientNote=survivalDao.countSurvivalNoteByNoteId(survivalNoteId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNoteId,"查询契调函信息耗时"+(endTime-startTime)+"ms");
		if(tNoteClientNote==null){
			throw new BusinessException("函件id为"+survivalNoteId+"的契调函不存在!");
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNoteId, "开始组织返回报文");
		//初始化返回报文
		SurvivalTaskResponseMessage survivalTaskResponseMessagenseMessage=new SurvivalTaskResponseMessage();
		survivalTaskResponseMessagenseMessage.setNote_id(tNoteClientNote.getNoteId());
		List<ImageInfo>imageInfos=new ArrayList<>();
		
		//获取影像信息
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNoteId, "开始获取影像信息");
		HashMap<String,Object> tNoteImageMap=new HashMap<>();
		tNoteImageMap.put("client_note_id",survivalNoteId);
		tNoteImageMap.put("note_type", Constant.note_from_core_type_SURVIVAL);
		tNoteImageMap.put("image_type", Constant.image_type_Survival_CLIENT_TAKE_PICTURES);

		startTime = System.currentTimeMillis();
		List<TNoteImage> tNoteImageList=survivalDao.getImageListByNoteid(tNoteImageMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalNoteId,"查询契调函影像信息耗时"+(endTime-startTime)+"ms");
		if(tNoteImageList.size()>0){
			for(TNoteImage tNoteImage:tNoteImageList){
				ImageInfo imageInfo=new ImageInfo();
				imageInfo.setImage_id(tNoteImage.getImageId());
				String imageUrl=OSSUtil.getUrl(Constant.CHANNEL_NO+tNoteImage.getImageFile()+tNoteImage.getImageName());
				imageInfo.setImage_url(imageUrl);
				imageInfos.add(imageInfo);
			}
		}
		survivalTaskResponseMessagenseMessage.setImageInfos(imageInfos);
		return survivalTaskResponseMessagenseMessage;
	}
	/**
	 * 校验请求报文
	 * **/
	private void checkData(SurvivalTaskRequestMessage survivalTaskRequestMessage) {
		
		Assert.notNull(survivalTaskRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),survivalTaskRequestMessage.getSurvival_note_id(), "开始检查请求报文");
		Assert.notEmpty(survivalTaskRequestMessage.getSurvival_note_id(), "契调函id不能为空");

	}
}
