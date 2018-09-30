package com.hualife.wxhb.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.security.CryptoUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.mesiframework.restclient.RestClient;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.request.NoteImgDelRequestMessage;
import com.hualife.wxhb.api.rest.message.request.NoteImgRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NoteImgResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.domain.entity.TNoteProblemNote;
import com.hualife.wxhb.domain.entity.TNoteUndwrtNote;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.service.GetMaxNo;
import com.hualife.wxhb.service.NoteImgService;
import com.primeton.ext.infra.security.BASE64Decoder;

/**
 * @author 张卫卫
 * @description 微信端进行影像上传、删除操作impl
 * @date 2017-08-08
 */
@Service
public class NoteImgServiceImpl implements NoteImgService{
	
	private final Logger logger = LoggerFactory.getLogger(NoteImgServiceImpl.class);
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private GetMaxNo getMaxNo;
	@Autowired
	private RestClient restClient;
	/**
	 *  影像上传到oss
	 */
	@Override
	@Transactional
	public NoteImgResponseMessage ossUpload(NoteImgRequestMessage noteImgRequestMessage){
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteImgRequestMessage, "进入接口");
		//检查请求报文
		checkData(noteImgRequestMessage);
		String noteId=noteImgRequestMessage.getNote_id();
		String noteType=noteImgRequestMessage.getNote_type();
		String imageType=noteImgRequestMessage.getImage_type();
		String imageId=getMaxNo.getMaxNo();
		String mediaId=noteImgRequestMessage.getMedia_id();
		String medias=noteImgRequestMessage.getMedias();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像类型："+imageType, "调用影像接口");
//		//获取时间
		long startTime =0;
		long endTime =0;
		//获取文件
		File input = null;
		InputStream is = null;	
		String accessToken = "";
		boolean flag = false;
	
		Date date = new Date(); 
	    String dateNowStr = DateFormatUtil.formatDate("yyyyMMdd", date);
		String path=Constant.FTP_LOCAL_PATH+"/"+dateNowStr+"/"+noteId+"/"+noteType;
		logger.info("在linux服务器的路径"+path);
		//创建文件夹
		mkDirectory(path);		
		try{
			if( !medias.isEmpty()){
				//签名先删除，再上传
				ossDelete(noteId,noteType,imageType);
				input = getInputStreamByBase64(medias,path);
			}else{
				//体检函自拍照和契约报告书生成图片可以多次提交，但只能保存一次
				if(Constant.image_type_Physical_UPLOAD_SELF_PICTURES.equals(imageType)||Constant.image_type_Survival_Investigation_Report_CREATE_PICTURES.equals(imageType)){
					ossDelete(noteId,noteType,imageType);
				}
				JSONObject accessTokenJson =restClient.getForSimple(Constant.enterprise_QUERY_PRD_TOKEN_URL, getPushMapMsg(), null);
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),accessTokenJson, "查询企业accessToken");
				if(accessTokenJson.get("result_code").toString().equals(Constant.push_status)){
					accessToken = accessTokenJson.get("access_token").toString();
				}else{
					throw new BusinessException("获取企业号access_token失败");
				}
				flag = getImagePhotoFromWeChat(mediaId,accessToken);
				if(flag){
					flag=true;
					logger.info("腾讯公司拒绝上传图片");
				}
				input =PhotoFromWeChat(mediaId,accessToken,path);
			}
			is=new FileInputStream(input);
		}catch (Exception e){
			throw new BusinessException(e.getMessage());
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+"影像类型："+imageType, "查询函件信息");
		Map<String,Object> noteInfoMap=new HashMap<>();	
		noteInfoMap=getNoteSeq(noteId,imageId,noteType);
		String noteSeq=(String)noteInfoMap .get("noteSeq");
		String professionalId=(String)noteInfoMap.get("professionalId");
		logger.info("创建oss文件夹");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "开始调用oss上传图片");
	    String imageFile=dateNowStr+"/"+noteId+"/"+noteType+"/"+noteSeq+"/";
	    String imageName=imageId+".png";
		String ossPath=Constant.CHANNEL_NO+imageFile+imageName;
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId+" 影像地址："+ossPath, "获取影像地址");
		//创建文件夹
		mkDirectory(ossPath);
		logger.info("上传影像到oss服务器");
		
		startTime=System.currentTimeMillis();
		flag=OSSUtil.uploadFileByInputStream(is,ossPath);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "上传影像到oss服务器耗时:"+(endTime-startTime)+"ms");
		if(flag){		
			TNoteImage tNoteImage=new TNoteImage();
			tNoteImage.setNoteId(noteId);
			tNoteImage.setNoteType(noteType);
			tNoteImage.setProfessionalId(professionalId);
			tNoteImage.setImageId(imageId);
			tNoteImage.setImageType(imageType);
			tNoteImage.setImageFile(imageFile);
			tNoteImage.setImageName(imageName);
			tNoteImage.setImageStatus(Constant.valid_Y);
			tNoteImage.setCreatedDate(new Date());
			
			startTime=System.currentTimeMillis();
			imageDao.saveImageInfo(tNoteImage);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "插入影像信息--耗时:"+(endTime-startTime)+"ms");
		}else{
			throw new BusinessException("上传影像失败");
		}
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "组装返回报文");
		NoteImgResponseMessage  noteImgResponseMessage=new NoteImgResponseMessage();
		ImageInfo imageInfo=new ImageInfo();
		imageInfo.setImage_id(imageId);
		imageInfo.setImage_type(imageType);
		String imageUrl=OSSUtil.getUrl(ossPath);
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId+" 影像地址："+imageUrl, "获取oss影像地址");

		imageInfo.setImage_url(imageUrl);
		noteImgResponseMessage.setImageInfo(imageInfo);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "返回响应报文");

		return noteImgResponseMessage;	
	
	}
	private Map<String,Object> getNoteSeq(String noteId,String imageId,String noteType) {
		long startTime=0;
		long endTime=0;
		Map<String,Object>  noteInfoMap=new HashMap<String, Object>();
		//String noteSeq="";
		TNoteClientNote tNoteClientNote=null;
		TNoteProblemNote tNoteProblemNote=null;
		TNoteUndwrtNote tNoteUndwrtNote=null;
		if(noteType.equals(Constant.note_from_core_type_FINAOCCU)||noteType.equals(Constant.note_from_core_type_HEALTH)||noteType.equals(Constant.note_from_core_type_PHYSICAL)||noteType.equals(Constant.note_from_core_type_SURVIVAL)){
			//查询核保任务序号
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "查询核保任务序号");
			noteInfoMap.put("noteType", noteType);
			noteInfoMap.put("noteId",noteId);
			
			startTime=System.currentTimeMillis();
			tNoteClientNote=imageDao.getClentNoteSeqByMap(noteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "根据函件id查询核保任务序号--耗时:"+(endTime-startTime)+"ms");
			if(tNoteClientNote==null){
				throw new BusinessException("此函件id"+noteId+"的"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType)+"的核保任务序号不存在!");
			}
			Assert.notEmpty(tNoteClientNote.getNoteSeq(), "核保任务序号不能为空");
			Assert.notEmpty(tNoteClientNote.getClientNoteId(), "客户层函件id不能为空");
			noteInfoMap.put("noteSeq", tNoteClientNote.getNoteSeq());
			noteInfoMap.put("professionalId", tNoteClientNote.getClientNoteId());
		}else if(noteType.equals(Constant.note_from_core_type_PROBLEM)){
			//查询核保任务序号
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "查询核保任务序号");
			noteInfoMap.put("noteId",noteId);
			noteInfoMap.put("noteStatus", Constant.note_status_FINISHED);
			
			startTime=System.currentTimeMillis();
			tNoteProblemNote=imageDao.getProblemNoteSeqyMap(noteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "根据函件id查询核保任务序号--耗时:"+(endTime-startTime)+"ms");
			if(tNoteProblemNote==null){
				throw new BusinessException("此函件id"+noteId+"的"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType)+"的核保任务序号不存在!");
			}
			Assert.notEmpty(tNoteProblemNote.getNoteSeq(), "核保任务序号不能为空");
			Assert.notEmpty(tNoteProblemNote.getProblemNoteId(), "问题件id不能为空");
			noteInfoMap.put("noteSeq", tNoteProblemNote.getNoteSeq());
			noteInfoMap.put("professionalId", tNoteProblemNote.getProblemNoteId());
		}else if(noteType.equals(Constant.note_from_core_type_UNDWRT)){
			//查询核保任务序号
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "查询核保任务序号");
			noteInfoMap.put("noteId",noteId);
			noteInfoMap.put("noteStatus", Constant.note_status_FINISHED);
			
			startTime=System.currentTimeMillis();
			tNoteUndwrtNote=imageDao.getUndwrtNoteSeqByMap(noteInfoMap);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"函件id："+noteId+" 函件类型："+noteType+" 影像编号："+imageId, "根据函件id查询核保任务序号--耗时:"+(endTime-startTime)+"ms");
			if(tNoteUndwrtNote==null){
				throw new BusinessException("此函件id"+noteId+"的"+DataConVersion.dataConVersion(Constant.transfer_core_data_Note_TYPE_NAME,noteType)+"的核保任务序号不存在!");
			}
			noteInfoMap.put("noteSeq", tNoteUndwrtNote.getNoteSeq());
			noteInfoMap.put("professionalId", tNoteUndwrtNote.getNoteUndwrtId());
			Assert.notEmpty(tNoteUndwrtNote.getNoteSeq(), "核保任务序号不能为空");
			Assert.notEmpty(tNoteUndwrtNote.getNoteUndwrtId(), "核保函id不能为空");
		}else{
			throw new BusinessException("此函件id"+noteId+"这种函件类型!");
		}
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteInfoMap, "获取核保任务序号");
		return noteInfoMap;
	}
	/**
	 * 创建文件夹
	 * @param path
	 */
	private void mkDirectory(String path) {
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	/**
	 * 删除影像
	 */
	@Override
	@Transactional
	public void ossDelete(NoteImgDelRequestMessage noteImgDelRequestMessage) {
		//检查请求报文
		checkDelData(noteImgDelRequestMessage);
		String imageId=noteImgDelRequestMessage.getImage_id();		
		//获取时间
		long startTime =0;
		long endTime =0;
		//查询影像信息是否存在
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix()," 影像编号："+imageId, "查询影像信息");
	
		startTime=System.currentTimeMillis();
		TNoteImage tNoteImage=imageDao.getImageInfoById(imageId);
		endTime=System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"影像编号："+imageId, "查询影像信息--耗时:"+(endTime-startTime)+"ms");
		if(tNoteImage!=null){
			String imageFile=tNoteImage.getImageFile();
			String imageName=tNoteImage.getImageName();
			String ossPath=imageFile+imageName;
			OSSUtil.deleteFile(Constant.CHANNEL_NO+"/"+ossPath);
			if(Constant.answer_Y.equals(tNoteImage.getIsSendSuss())){
				startTime=System.currentTimeMillis();
				imageDao.updateImageInfoById(imageId);
				endTime=System.currentTimeMillis();
			}else{
				imageDao.deleteImageInfoById(imageId);
			}	
		}
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
			imageDao.batchUpdateImageInfo(tNoteImages);
			endTime=System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"影像类型："+imageType, "影像信息--耗时:"+(endTime-startTime)+"ms");
		}
	}
	/**
	 * 检查删除的请求报文
	 */
	private void checkDelData(NoteImgDelRequestMessage noteImgDelRequestMessage) {
		Assert.notNull(noteImgDelRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteImgDelRequestMessage.getImage_id(), "开始检查请求报文");	
		Assert.notEmpty(noteImgDelRequestMessage.getImage_id(), "影像编号不能为空");
	}
	/**
	 * 检查请求报文
	 */
	private void checkData(NoteImgRequestMessage noteImgRequestMessage) {
		Assert.notNull(noteImgRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteImgRequestMessage.getNote_id(), "开始检查请求报文");
		Assert.notEmpty(noteImgRequestMessage.getNote_id(), "函件id不能为空");
		Assert.notEmpty(noteImgRequestMessage.getNote_type(), "函件类型不能为空");
		Assert.notEmpty(noteImgRequestMessage.getImage_type(), "影像类型不能为空");		
	}
	 /**
     * 从微信获取文件流
     * @param mediaId
     * @param accessToken	
     * @param path
     * @return
     */
	public boolean  getImagePhotoFromWeChat(String mediaId, String accessToken) throws Exception{
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="
				+ accessToken	 + "&media_id=" + mediaId + "";
		logger.info("微信图片url \t"+requestUrl);
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;
		
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			is = url.openStream();
			byte[] byteWx= input2byte(is);
			byte[] newByte = byteWx;
			String str = new String(newByte);
			
			if(str.contains(Constant.errCode)){
				logger.info("腾讯公司拒绝上传图片:错误码是:--- "+str+"---");
				return Constant.result_TRUE;
			}else{ 
				return Constant.result_FALSE;
			}
			
		} catch (Exception ex) {
			logger.error("获取微信图片信息出错" +ex.getMessage());
			throw ex;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 从微信端获取图片
	 * @param mediaId
	 * @param accessToken
	 * @param path
	 * @return 
	 * @throws Exception
	 */
	public File PhotoFromWeChat(String mediaId, String accessToken,String path) throws Exception{	
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="
				+ accessToken + "&media_id=" + mediaId + "";
		logger.info("微信图片url \t"+requestUrl);
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;	
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			logger.info("图片本地路径："+path);
			File outFile = new File(path + "/" + UUID.randomUUID() + ".png");
			os = new FileOutputStream(outFile);
			is = url.openStream();
			byte[] buff = new byte[1024];
			while (true) {
				int readed = is.read(buff);
				if (readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				os.write(temp);
			}
			os.flush();
			logger.info("返回图片流："+outFile);
			return outFile;
		} catch (Exception ex) {
			logger.error("获取微信图片信息出错" + ex.getMessage());
			throw ex;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 从微信端获取签名
	 * @param imgStr
	 * @param path
	 * @return
	 */
	public File getInputStreamByBase64(String imgStr,String path)
	{
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		 File file = new File(path + "/" + UUID.randomUUID() + ".png");
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			out = new FileOutputStream(file);
			out.write(bytes);
			out.flush();	
		}
		catch(Exception ex)
		{
			throw new BusinessException("获取签名失败");
		}
		finally{
			if(out!=null)
			{
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
	/**
	 * 根据输入流得到字节
	 * @param inStream
	 * @return
	 */
	public byte[] input2byte(InputStream inStream)
	{  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        try {
			while ((rc = inStream.read(buff, 0, 100)) > 0) {  
			    swapStream.write(buff, 0, rc);  
			}
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		}  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
	 }
	/**
	 * 拼装企业号报文参数
	 * 
	 * @param data 推送人员信息
	 * @return 企业号请求报文参数map
	 */
	public Map<String,String> getPushMapMsg() {
		Map<String, String> paramMap = new HashMap<String, String>();
		String timestamp = "" + System.currentTimeMillis();
		String trade_source = Constant.enterprise_QUERY_USER_INFOR_TRADE_SOURCE;
		String nonce = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		String KEY = Constant.enterprise_QUERY_USER_INFOR_KEY;
		
		String secryptStr = KEY+ timestamp + nonce + trade_source;
		String signature = CryptoUtil.MD5(secryptStr);
		paramMap.put("timestamp", timestamp);
		paramMap.put("trade_source", trade_source);
		paramMap.put("nonce", nonce);
		paramMap.put("signature", signature);
		
		return paramMap;
	}
}
