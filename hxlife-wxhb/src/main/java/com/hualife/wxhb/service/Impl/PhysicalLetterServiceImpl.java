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
import com.hualife.wxhb.api.rest.message.pojo.PhysicalItem;
import com.hualife.wxhb.api.rest.message.request.PhysicalLetterInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.PhysicalLetterInitResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.PhysicalDao;
import com.hualife.wxhb.service.PhysicalLetterService;

/**
 * @description : 体检函初始化实现类serviceImpl
 * @author : linyongtao
 * @date : 2017-08-04 
 */
@Service
public class PhysicalLetterServiceImpl implements PhysicalLetterService{
	private final Logger logger = LoggerFactory.getLogger(PhysicalLetterServiceImpl.class);
	@Autowired
	private PhysicalDao physicalDao;
	@Autowired
	private ClientDao clientDao;
	/**
	 * @description : 体检函初始化
	 * @author : linyongtao
	 * @date : 2017-08-04 
	 * **/
	@Override
	public  PhysicalLetterInitResponseMessage selectPhysicalLetter(PhysicalLetterInitRequestMessage physicalLetterInitRequestMessage) {		
		checkData(physicalLetterInitRequestMessage);
		long startTime = 0;
		long endTime = 0;
	    PhysicalLetterInitResponseMessage physicalLetterInitResponseMessage = new PhysicalLetterInitResponseMessage();	    	 	
	    List<PhysicalItem> physicalItemList = new ArrayList<PhysicalItem>();
	    List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();

		//获取请求参数的关键字段
	    String physicalNoteId = physicalLetterInitRequestMessage.getPhysical_note_id();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "获取请求参数");
	
		//校验前端传入的physicalNoteId是否存在
		Long number = clientDao.selectExitByClientNoteId(physicalNoteId);
		if(number==0){
			throw new BusinessException("体检函初始化接口,前端传入的参数physicalNoteId:"+physicalNoteId+"错误!");
		}
		// 获取到返回的结果(代理人姓名+代理人电话+是否有免陪检资格)
		startTime = System.currentTimeMillis();
		physicalLetterInitResponseMessage = physicalDao.selectPhysicalLetter(physicalNoteId);    
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "查询代理人信息和客户是否有免陪检资格--耗时"+(endTime-startTime)+"ms");

    	//查询客户上传的图片并返回
		Map<String , Object> imageInfoMap = new HashMap<>();
		imageInfoMap.put("physicalNoteId",physicalNoteId);
		imageInfoMap.put("noteType", Constant.client_type_PHYSICAL);
		imageInfoMap.put("imageTypeSelf",Constant.image_type_Physical_UPLOAD_SELF_PICTURES);
		imageInfoMap.put("imageTypePicture", Constant.image_type_Physical_UPLOAD_PICTURES);
		imageInfoMap.put("imageStatus", Constant.valid_Y);
		startTime = System.currentTimeMillis();
		imageInfoList = physicalDao.selectImageInfo(imageInfoMap);
		endTime = System.currentTimeMillis();
		for (ImageInfo imageInfo : imageInfoList) {
			String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
			String imageUrl=OSSUtil.getUrl(ossPath);
			imageInfo.setImage_url(imageUrl);
		}
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "查询客户上传的图片--耗时"+(endTime-startTime)+"ms");
    	
		//将查询到的体检项目放到返回list中
		startTime = System.currentTimeMillis();
    	physicalItemList = physicalDao.selectPhysicalItem(physicalNoteId);  
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"体检函id:"+physicalNoteId, "查询客户体检项目--耗时"+(endTime-startTime)+"ms");
	    //组装返回报文部分
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalNoteId, "组装返回报文");
	    physicalLetterInitResponseMessage.setPhysicalContent(physicalItemList);
	    physicalLetterInitResponseMessage.setImageInfo(imageInfoList);
	    return physicalLetterInitResponseMessage;
	}		
	
	/**  
	 * 校验请求报文
	 * **/
	private void checkData(PhysicalLetterInitRequestMessage physicalLetterInitRequestMessage) {
		Assert.notNull(physicalLetterInitRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),physicalLetterInitRequestMessage.getPhysical_note_id(), "开始检查请求报文");
		Assert.notEmpty(physicalLetterInitRequestMessage.getPhysical_note_id(), "体检函id不能为空");		
	}
}