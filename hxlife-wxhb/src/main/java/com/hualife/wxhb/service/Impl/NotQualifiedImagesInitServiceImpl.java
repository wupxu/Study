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
import com.hualife.wxhb.api.rest.message.request.NotQualifiedImagesInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NotQualifiedImagesInitResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.ProblemDao;
import com.hualife.wxhb.service.NotQualifiedImagesInitService;
/** 
 * @description : 次品单-客户上传影像资料实现类serviceImpl
 * @author : linyongtao
 * @date :2017-08-21  
 */
@Service
public class NotQualifiedImagesInitServiceImpl implements NotQualifiedImagesInitService{
	private final Logger logger = LoggerFactory.getLogger(NotQualifiedImagesInitServiceImpl.class);
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private ProblemDao problemDao;
	/**
	 * @description :次品单影像初始化
	 * @author : linyongtao
	 * @date :2017-08-21 
	 * **/
	@Override
	public NotQualifiedImagesInitResponseMessage notQualifiedInit(NotQualifiedImagesInitRequestMessage notQualifiedImagesInitRequestMessage) {
		checkData(notQualifiedImagesInitRequestMessage);
		long startTime =0;
		long endTime =0;
		//声明返回的报文体
		NotQualifiedImagesInitResponseMessage notQualifiedImagesInitResponseMessage = new NotQualifiedImagesInitResponseMessage();
		//获取请求参数
		String noteId = notQualifiedImagesInitRequestMessage.getNote_id();
		String noteType = notQualifiedImagesInitRequestMessage.getNote_type();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "获取请求参数");
		
		Long number =clientDao.selectExitByNoteId(noteId);
		if(number==0){
			throw new BusinessException("函件类型为"+noteType+"的次品单初始化,前端传入的note_id:"+noteId+"-错误!");
		}
		//常见变量和集合
		Map<String,Object> reasonImageMap = new HashMap<>();
		String notQualifiedReason ="";
		List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();
		
		reasonImageMap.put("noteId", noteId);
		reasonImageMap.put("isNotQualifiedNote",Constant.is_not_qualified_note_Y);
		//如果次品单类型是---问题件
		if(noteType.equals(Constant.note_from_core_type_PROBLEM)){
			reasonImageMap.put("noteType", Constant.note_from_core_type_PROBLEM);
			reasonImageMap.put("imageStatus", Constant.valid_Y);
			//查询问题件次品单下发原因+	查询客户上传的影像资料
			startTime = System.currentTimeMillis();
			notQualifiedReason=problemDao.selectProblemNotQualifiedReason(reasonImageMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "查询问题件次品单下发原因--耗时"+(endTime-startTime)+"ms");
			startTime = System.currentTimeMillis();
			imageInfoList = problemDao.selectProblemNotQualifiedImage(reasonImageMap);
			endTime = System.currentTimeMillis();			
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "查询问题件次品单客户上传图片--耗时"+(endTime-startTime)+"ms");			
		}else{
			if(noteType.equals(Constant.note_from_core_type_HEALTH)){
				reasonImageMap.put("noteType", Constant.client_type_HEALTH);
			}else if(noteType.equals(Constant.note_from_core_type_PHYSICAL)){
				reasonImageMap.put("noteType", Constant.client_type_PHYSICAL);
			}else if(noteType.equals(Constant.note_from_core_type_FINAOCCU)){
				reasonImageMap.put("noteType", Constant.client_type_FINA);
			}else if(noteType.equals(Constant.note_from_core_type_SURVIVAL)){
				reasonImageMap.put("noteType", Constant.client_type_SURVIVAL);
			}else{
				throw new BusinessException("函件类型为:"+noteType+"的次品单初始化,前端传入的note_type错误!");
			}
			reasonImageMap.put("imageStatus", Constant.valid_Y);
			startTime = System.currentTimeMillis();
			notQualifiedReason = clientDao.selectClientNotQualifiedReason(reasonImageMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "查询客户称函件次品单客户上传影像--耗时:"+(endTime-startTime)+"ms");
			startTime = System.currentTimeMillis();
			imageInfoList = clientDao.selectClientNotQualifiedImage(reasonImageMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "查询客户称函件次品单客户上传影像--耗时:"+(endTime-startTime)+"ms");
		}
		//拼接影像的url路径
		for (ImageInfo imageInfo : imageInfoList) {
			String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
			String imageUrl=OSSUtil.getUrl(ossPath);
			imageInfo.setImage_url(imageUrl);
		}	
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id:"+noteId, "组装返回报文");
		notQualifiedImagesInitResponseMessage.setIs_not_qualified_note(notQualifiedReason);
		notQualifiedImagesInitResponseMessage.setImageInfo(imageInfoList);		
		return notQualifiedImagesInitResponseMessage;
	}	
	/**
	* 校验请求报文
	* **/
	private void checkData(NotQualifiedImagesInitRequestMessage notQualifiedImagesInitRequestMessage) {
		Assert.notNull(notQualifiedImagesInitRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),notQualifiedImagesInitRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(notQualifiedImagesInitRequestMessage.getNote_id(), "函件id不能为空");	
		Assert.notEmpty(notQualifiedImagesInitRequestMessage.getNote_type(), "函件类型不能为空");		
	}
}