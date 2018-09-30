package com.hualife.wxhb.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import com.hualife.mesiframework.core.util.io.FTPClientUtil;
import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
import com.hualife.wxhb.api.soap.message.response.common.CommonResponseMessage;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.dto.ImageTable;
import com.hualife.wxhb.domain.dto.ImageTaskTable;
import com.hualife.wxhb.domain.entity.TNoteMain;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.integration.soap.Yx0191;
import com.hualife.wxhb.integration.soap.message.request.image.ImageUpLoadBody;
import com.hualife.wxhb.integration.soap.message.request.image.ImageUpLoadDoc;
import com.hualife.wxhb.integration.soap.message.request.image.ImageUpLoadPage;
import com.hualife.wxhb.integration.soap.message.request.image.ImageUpLoadRequestMessage;
import com.hualife.wxhb.service.CreateCBSWebserviceHead;
import com.hualife.wxhb.service.ImageService;
/**
 * 
 * @deprecation 推送影像消息--批处理类impl
 * @author wangt
 * @date  2017-08-20
 *
 */
@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private Yx0191 yx0191;
	@Autowired
	private CreateCBSWebserviceHead createCBSWebserviceHead;
	
	private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);	
	long startTime = 0;
	long endTime   = 0;
	/**
	 * 影像上载推送
	 */
	@Override
	public void imageUpLoad(){		
		List<ImageTaskTable> imageTaskTableList = new ArrayList<ImageTaskTable>();			
		Map<String, Object> map = new HashMap<>();
		//从 t_task_push_image 查询待上载信息	
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "从 t_task_push_image查询待上载信息");
		String waitUpLoad = Constant.waitUpLoad;
		String failUpLoad = Constant.failUpLoad;
		map.put("awaitUpLoad", waitUpLoad);
		map.put("failUpLoad", failUpLoad);
		startTime = System.currentTimeMillis();
		imageTaskTableList = imageDao.getUpLoad(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),imageTaskTableList,"从 t_task_push_image 查询待上载信息"+(endTime-startTime));
		
		if(imageTaskTableList.size()>0){
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "开始组织推送影像上载报文");
			ImageUpLoadRequestMessage imageUpLoadRequestMessage = new ImageUpLoadRequestMessage();
			RequestHead requestHead = new RequestHead();
			requestHead = createCBSWebserviceHead.createCBSWebserviceHead(Constant.serviceid_IMAGE);
			imageUpLoadRequestMessage.setHead(requestHead);
			ImageUpLoadBody imageUpLoadBody = new ImageUpLoadBody();
			List<ImageUpLoadDoc> imageUpLoadDocList = new ArrayList<ImageUpLoadDoc>();
			
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"从  t_note_image 查询待上载信息");
			//从  t_note_image 查询待上载信息	
			for(ImageTaskTable imageTaskTable : imageTaskTableList){		
				String noteId = imageTaskTable.getNote_id();
				String noteType = imageTaskTable.getNote_type();	
				map.put("note_id", noteId);
				map.put("note_type", noteType);
				//查询条形码
				startTime = System.currentTimeMillis();
				String noteBarCode =  imageDao.getBarCode(map);	
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询条形码耗时"+(endTime-startTime)+"ms");

				//查询所属渠道
				startTime = System.currentTimeMillis();
				TNoteMain tNoteMain = imageDao.getMainInfoById(noteId);			
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询客户号、单证类型，所属渠道耗时"+(endTime-startTime)+"ms");
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "组装请求报文");
				ImageUpLoadDoc 	imageUpLoadDoc = new ImageUpLoadDoc();
				imageUpLoadDoc.setReturnurl(Constant.IMAGECALLBACK_URL);
				imageUpLoadDoc.setDoccode(noteBarCode);			//单证号码
				imageUpLoadDoc.setBusstype("TB");					//业务类型
				imageUpLoadDoc.setSubtype(noteBarCode.substring(0,4));		//单证类型      单证号码前四位
				imageUpLoadDoc.setScandate(DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date())); 	//扫描时间
				imageUpLoadDoc.setManagecom(tNoteMain.getBranchCode());			//扫描机构
				imageUpLoadDoc.setScanoperator(tNoteMain.getAgentNo());			//扫描人员			
				//以下字段可为空
				imageUpLoadDoc.setChannel(tNoteMain.getChannelType()); 			//渠道			
				imageUpLoadDoc.setDocid("");						//单证ID唯一标识
				imageUpLoadDoc.setGroupno("");						//组号			
				imageUpLoadDoc.setScanno("");
				//查询影像信息
				List<ImageTable> imageTableList = new ArrayList<ImageTable>();
				startTime = System.currentTimeMillis();
				imageTableList = imageDao.getImage(map);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteId,"查询影像信息耗时"+(endTime-startTime)+"ms");
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),imageTableList,"影像信息");
				if(imageTableList.size()>0){
					imageUpLoadDoc.setNumpages(String.valueOf(imageTableList.size()));//总页码			
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId, "开始从OSS下载图片并上传FTP");
					String dateStr = DateFormatUtil.formatDate("yyyyMMdd", new Date());		
					//文件下载保存位置  
					String path = Constant.ImageSystem_LOCAL_PATH+"/"+dateStr+"/";
					//创建文件夹
					File saveDir = new File(path); 
			        if(!saveDir.exists()){  
			            saveDir.mkdirs();  
			        }
					List<ImageUpLoadPage> imageUpLoadPageList = new ArrayList<ImageUpLoadPage>();	
					for(ImageTable imageTable:imageTableList){
						ImageUpLoadPage imageUpLoadPage = new ImageUpLoadPage();
						imageUpLoadPage.setPageno(imageTable.getImage_id());	//页码	
						imageUpLoadPage.setPagename(imageTable.getImage_name());//文件名		
						try {					
							//图片在OSS上的地址
							String ossPath = Constant.CHANNEL_NO+imageTable.getImage_file()+imageTable.getImage_name();	
							logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),ossPath,"OSS路径");
			        
					        //将String转换成byte并保存成文件	
							logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"从OSS下载图片");
							startTime = System.currentTimeMillis();
							OSSUtil.getUploadFile(ossPath,path+imageTable.getImage_name());	
							endTime=System.currentTimeMillis();
							logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),ossPath,"下载影像信息耗时"+(endTime-startTime)+"ms");

							//将图片上传到Ftp
					        logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"将图片上传到Ftp");
							FTPClientUtil ftpClientUtil = new FTPClientUtil();
							String localFile = path+imageTable.getImage_name();
							logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"linux 本地路径"+localFile,"下载到本地的路径");
							String ftpUrl = Constant.FTP_IMAGESYSTEM_PATH+"/"+noteBarCode+"_"+dateStr;
							logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"ftp 上传 影像路径"+ftpUrl,"的路径");
							
							startTime = System.currentTimeMillis();
							ftpClientUtil.upload(localFile, imageTable.getImage_name(),ftpUrl,Constant.FTP_SERVER,Constant.FTP_RPORT,Constant.FTP_USERNAME,Constant.FTP_PASSWORD);
							endTime=System.currentTimeMillis();
							logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),ossPath,"影像上传到ftp耗时"+(endTime-startTime)+"ms");
							imageUpLoadPage.setPagepath(ftpUrl);	//文件存放Ftp地址
							imageUpLoadPageList.add(imageUpLoadPage);		

						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
					imageUpLoadDoc.setPage(imageUpLoadPageList);
					imageUpLoadDocList.add(imageUpLoadDoc);	
				}			
			}	
			imageUpLoadBody.setDoc(imageUpLoadDocList);
			imageUpLoadRequestMessage.setBody(imageUpLoadBody);
			//发送报文			
			String reqXml = XmlMapper.toXml(imageUpLoadRequestMessage);
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),reqXml,"发送影像上载报文");
			String resXml = yx0191.yx0191(reqXml);
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),resXml,"返回响应报文");

			//获得返回报文
			CommonResponseMessage commonResponseMessage = XmlMapper.fromXml(resXml,CommonResponseMessage.class);			
			 if(Constant.sendImageSuss.equals(commonResponseMessage.getHead().getStatus().getReturnCode())){
				 	for(ImageTaskTable imageTaskTable : imageTaskTableList){
				 		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "发送成功更改状态");
				 		map.put("note_id", imageTaskTable.getNote_id());
			 			map.put("note_type", imageTaskTable.getNote_type());
						String imageStatus=Constant.sendSuss;
						String isSendSuss=Constant.valid_Y;
						map.put("image_status", imageStatus);
						map.put("push_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
						startTime = System.currentTimeMillis();
						imageDao.updateUpLoad(map);
						endTime=System.currentTimeMillis();
						logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"更新推送表影像成功状态耗时"+(endTime-startTime)+"ms");
						//更影像表推送状态
						logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "更影像表推送状态");
						map.put("is_send_suss", isSendSuss);
						startTime = System.currentTimeMillis();
						imageDao.updateImageNote(map);
						endTime=System.currentTimeMillis();
						logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"更新影像信息状态耗时"+(endTime-startTime)+"ms");
					}
			}else{
				for(ImageTaskTable imageTaskTable : imageTaskTableList){
		 			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "发送成功更改状态");
		 			map.put("note_id", imageTaskTable.getNote_id());
		 			map.put("note_type", imageTaskTable.getNote_type());
					String imageStatus=Constant.failUpLoad;
					String isSendSuss=Constant.valid_N;		
					map.put("image_status", imageStatus);
					map.put("push_date",DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
					startTime = System.currentTimeMillis();
					imageDao.updateUpLoad(map);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"更新推送表影像成功状态耗时"+(endTime-startTime)+"ms");
					//更影像表推送状态
					logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(), "更影像表推送状态");
					map.put("is_send_suss", isSendSuss);
					startTime = System.currentTimeMillis();
					imageDao.updateImageNote(map);
					endTime=System.currentTimeMillis();
					logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"更新影像信息状态耗时"+(endTime-startTime)+"ms");
					throw new BusinessException(commonResponseMessage.getHead().getStatus().getReturnCode());
				}
			}
		}
	}

}
