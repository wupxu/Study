package com.hualife.wxhb.service.Impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.service.TaskImageService;
/**
 * 
 * @deprecation 保存影像推送数据--批处理类Impl
 * @author wangt
 * @date  2017-08-20
 *
 */
@Service
public class TaskImageServiceImpl implements TaskImageService{

	Date date = new Date();
	@Autowired
	private ImageDao imageDao;
	private final Logger logger = LoggerFactory.getLogger(TaskImageServiceImpl.class);
	long startTime = 0;
	long endTime   = 0;
	/**
	 * 保存影像上载信息
	 * @throws ParseException 
	 */
	@Transactional
	@Override	
	public void saveImageUpLoad(String noteId,String noteType,String professional_id){
		
		checkData(noteId,noteType);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+"具体函件id："+professional_id, "查询待推送影像表中的数据");
		Map<String, Object> map = new HashMap<>();		
		
		map.put("note_id", noteId);
		map.put("note_type", noteType);
		map.put("professional_id", professional_id);
		map.put("waitUpLoad", Constant.waitUpLoad);
		map.put("failUpLoad", Constant.failUpLoad);
		
		startTime = System.currentTimeMillis();
		Integer count=imageDao.getTaskImageCount(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询影像信息耗时"+(endTime-startTime));
		
		if(count==0){
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+"具体函件id："+professional_id, "插入影像推送信息");
			map.put("push_date", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
			map.put("created_date", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
			map.put("waitUpLoad", Constant.waitUpLoad);

			startTime = System.currentTimeMillis();
			imageDao.saveUpLoad(map);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"保存影像上载推送信息耗时"+(endTime-startTime));
		}
		
	}	
	/**
	 * 保存影像删除信息
	 * @throws ParseException 
	 */
	@Transactional
	@Override
	public void saveImageDelete(String noteId,String noteType,String professional_id){
		
		checkData(noteId,noteType);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("note_id", noteId);
		map.put("note_type", noteType);
		map.put("professional_id", professional_id);		
		map.put("push_date", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		map.put("created_date", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));

		String imageStatus = Constant.waitDelete;
		map.put("image_status", imageStatus);
		
		startTime = System.currentTimeMillis();
		imageDao.saveDelete(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"保存影像删除推送信息耗时"+(endTime-startTime));
	
	}	
	/**
	 * 校验请求报文
	 */
	private void checkData(String noteId,String noteType) {	
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "开始校验");
		Assert.notEmpty(noteId, "函件id不能为空");
		Assert.notEmpty(noteType, "函件类型不能为空");		
	}
}

	

	