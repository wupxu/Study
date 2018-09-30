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
import com.hualife.wxhb.api.rest.message.request.RemoteSignatureRequestMessage;
import com.hualife.wxhb.api.rest.message.response.RemoteSignatureResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.service.RemoteSignatureService;

/**
 * @author zhangweiwei
 * @deprecation 投保人查看被保人的异地签名的返回报文
 * @date 2017-09-11
 */
@Service
public class RemoteSignatureServiceImpl implements RemoteSignatureService{
	
	private final Logger logger = LoggerFactory.getLogger(RemoteSignatureServiceImpl.class);
	@Autowired
	private UndwrtDao undwrtDao;
	@Autowired
	private ImageDao imageDao;
	@Override
	public RemoteSignatureResponseMessage getRemoteSignature(RemoteSignatureRequestMessage remoteSignatureRequestMessage) {
		//检查请求报文
		checkData(remoteSignatureRequestMessage);
		long startTime = 0;
		long endTime   = 0;
		String noteId=remoteSignatureRequestMessage.getNote_id();
		String noteType=remoteSignatureRequestMessage.getNote_type();
		String imageType=remoteSignatureRequestMessage.getImage_type();
		Integer count=remoteSignatureRequestMessage.getCount();
		
		if(!Constant.image_type_Undwrt_INSURED_SIGNATURE.equals(imageType)){
			throw new BusinessException("影像类型"+imageType+"不正确!");
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "查询异地签名");
		Map<String,Object> imageMap=new HashMap<String,Object>();
		imageMap.put("noteId", noteId);
		imageMap.put("noteType", noteType);
		imageMap.put("imageType", imageType);
		startTime=System.currentTimeMillis();
		TNoteImage tNoteImage=undwrtDao.getRemoteSignature(imageMap);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询影像信息耗时"+(endTime-startTime)+"ms");
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId, "开始组装返回报文");
		RemoteSignatureResponseMessage remoteSignatureResponseMessage=new RemoteSignatureResponseMessage();
		ImageInfo imageInfo=null;
		if(count==1){
			ossDelete(noteId,noteType,imageType);
			remoteSignatureResponseMessage.setImageInfo(imageInfo);
		}else{
			if(tNoteImage!=null){
				imageInfo=new ImageInfo();
				imageInfo.setImage_id(tNoteImage.getImageId());
				String imageUrl=OSSUtil.getUrl(Constant.CHANNEL_NO+tNoteImage.getImageFile()+tNoteImage.getImageName());
				imageInfo.setImage_url(imageUrl);
				remoteSignatureResponseMessage.setImageInfo(imageInfo);
			}
		}	
		return remoteSignatureResponseMessage;

	}
	/**
	 * 检查入参报文
	 * @param remoteSignatureRequestMessage
	 */
	private void checkData(RemoteSignatureRequestMessage remoteSignatureRequestMessage) {
		Assert.notNull(remoteSignatureRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),remoteSignatureRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(remoteSignatureRequestMessage.getNote_id(), "函件id不能为空");
		Assert.notEmpty(remoteSignatureRequestMessage.getNote_type(), "函件类型不能为空");
		Assert.notEmpty(remoteSignatureRequestMessage.getImage_type(), "影像类型不能为空");
	}
	/**
	 *  删除签名影像
	 * @param noteId
	 * @param noteType
	 * @param imageType
	 */
	public void ossDelete(String noteId,String noteType,String imageType) {
		//获取时间
		long startTime =0;
		long endTime =0;
		//查询影像信息是否存在
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix()," 影像类型："+imageType, "查询影像信息");
	
		Map<String,Object> tNoteImageMap=new HashMap<String, Object>();
		tNoteImageMap.put("noteId", noteId);
		tNoteImageMap.put("noteType", noteType);
		tNoteImageMap.put("imageType", imageType);
		
		startTime=System.currentTimeMillis();
		List<TNoteImage> tNoteImageList=imageDao.getImageInfo(tNoteImageMap);
		endTime=System.currentTimeMillis();
		List<TNoteImage> tNoteImages = new ArrayList<TNoteImage>();

		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"影像类型："+imageType, "查询影像信息--耗时:"+(endTime-startTime)+"ms");
		if(tNoteImageList.size()>0){
			for(TNoteImage tNoteImage: tNoteImageList){
				String imageFile=tNoteImage.getImageFile();
				String imageName=tNoteImage.getImageName();
				String ossPath=imageFile+imageName;
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"影像类型："+imageType, "删除影像");
				OSSUtil.deleteFile(ossPath);
				TNoteImage tNoteImage2=new TNoteImage();
				tNoteImage2.setNoteId(tNoteImage.getNoteId());
				tNoteImage2.setNoteType(tNoteImage.getNoteType());
				tNoteImage2.setImageType(tNoteImage.getImageType());
				tNoteImages.add(tNoteImage2);
			}
			startTime=System.currentTimeMillis();
			imageDao.deleteImageInfo(tNoteImageMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"影像类型："+imageType, "影像信息--耗时:"+(endTime-startTime)+"ms");
		}
	}
}
