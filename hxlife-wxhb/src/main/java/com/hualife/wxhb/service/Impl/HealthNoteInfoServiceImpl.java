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
import com.hualife.wxhb.api.rest.message.pojo.HealthNoteItem;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.request.HealthNoteInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.HealthNoteInitResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.service.HealthNoteInfoService;
/**
 * @author wangt
 * @description 健康函初始化impl
 * @date 2017-08-08
 */
@Service
public class HealthNoteInfoServiceImpl implements HealthNoteInfoService {

	private final Logger logger = LoggerFactory.getLogger(HealthNoteInfoServiceImpl.class);
	
	@Autowired
	private HealthDao healthDao;
	/**
	 * 组织健康函初始化报文
	 */
	@Override
	
	public HealthNoteInitResponseMessage healthNoteInfo(HealthNoteInitRequestMessage healthNoteInitRequestMessage) {
		
		checkData(healthNoteInitRequestMessage);
		
		//返回对象
		HealthNoteInitResponseMessage healthNoteInitResponseMessage = new HealthNoteInitResponseMessage();		
		Map<Object, Object> map = new HashMap<>();	
		long startTime = 0;
		long endTime   = 0;

		//获取健康函ID
		String healthNoteId = healthNoteInitRequestMessage.getHealth_note_id();
		map.put("health_note_id",healthNoteId);
		
		// 查询客户层中的noteId
		startTime = System.currentTimeMillis();
		String noteId = healthDao.getNoteIDByHealthNoteId(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId,"查询客户层中的noteId耗时"+(endTime-startTime));		
		if(noteId == null){
			throw new BusinessException("函件ID查询为空");
		}
		map.put("note_id",noteId);		
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId, "开始查询健康函信息");
		// 查询健康函信息
		startTime = System.currentTimeMillis();
		healthNoteInitResponseMessage = healthDao.getHealthNote(map);		
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId,"查询健康函信息耗时"+(endTime-startTime));
		
		if(healthNoteInitResponseMessage != null){
		
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId, "开始查询健康问卷信息");
			// 查询健康问卷
			List<HealthNoteItem> healthNoteItemList = new ArrayList<HealthNoteItem>();
			
			startTime = System.currentTimeMillis();
			healthNoteItemList = healthDao.getHealthNoteItem(map);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId,"查询健康问卷耗时"+(endTime-startTime));
			
			
			for(HealthNoteItem healthNoteItem : healthNoteItemList){
				healthNoteItem.setNote_item_type_name(DataConVersion.dataConVersion(Constant.transfer_data_HEALTH_TYPE_NAME,healthNoteItem.getNote_item_type()));
			}
			
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId, "开始查询影像信息");
			// 查询影像集合
			List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();
			
			map.put("image_type", Constant.image_type_Health_UPLOAD_CASE_DATA_PICTURES);
			map.put("note_type", Constant.note_from_core_type_HEALTH);
			
			startTime = System.currentTimeMillis();
			imageInfoList = healthDao.getImages(map);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteId,"查询影像集合耗时"+(endTime-startTime));
			
			if(imageInfoList != null){
				//设置影像路径
				for(ImageInfo imageInfo:imageInfoList){
					String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
					String imageUrl=OSSUtil.getUrl(ossPath);
					imageInfo.setImage_url(imageUrl);
				}
			}			
			
			healthNoteInitResponseMessage.setClient_sex(DataConVersion.dataConVersion(Constant.transfer_data_SEX,healthNoteInitResponseMessage.getClient_sex()));
			
			healthNoteInitResponseMessage.setNote_items(healthNoteItemList);
			healthNoteInitResponseMessage.setImages(imageInfoList);
			
		}
		
		return healthNoteInitResponseMessage;
	}
	/**
	 * 校验请求报文
	 */
	private void checkData(HealthNoteInitRequestMessage healthNoteInitRequestMessage) {	
		Assert.notNull(healthNoteInitRequestMessage, "报文不能为空");	
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),healthNoteInitRequestMessage.getHealth_note_id(), "开始健康函初始化报文校验");
		Assert.notEmpty(healthNoteInitRequestMessage.getHealth_note_id(), "函件id不能为空");
	}
	public static void main(String[] args) {
		String a = DataConVersion.dataConVersion(Constant.transfer_data_HEALTH_TYPE_NAME,"9");
		System.out.println(a);
	}
}
