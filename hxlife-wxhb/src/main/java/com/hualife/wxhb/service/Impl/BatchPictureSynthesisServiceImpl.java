package com.hualife.wxhb.service.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.io.FTPClientUtil;
import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.mesiframework.core.util.security.CryptoUtil;
import com.hualife.mesiframework.restclient.RestClient;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.entity.TNoteClientNote;
import com.hualife.wxhb.domain.entity.TNoteImage;
import com.hualife.wxhb.domain.entity.TTaskPushMerge;
import com.hualife.wxhb.integration.dao.ClientDao;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.integration.rest.message.request.BatchPictureSynthesisRequestMessage;
import com.hualife.wxhb.service.BatchPictureSynthesisService;
import com.hualife.wxhb.service.GetMaxNo;

/**
 * @author yangpeixin
 * @description 批处理图片合成
 * @date 2017-08-04
 */
@Service
public class BatchPictureSynthesisServiceImpl implements BatchPictureSynthesisService {

	private static final Logger logger = LoggerFactory.getLogger(BatchPictureSynthesisServiceImpl.class);

	@Autowired
	RestClient restClient;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private ClientDao clientdao;
	@Autowired
	private GetMaxNo getMaxNo;

	/**
	 * 图片合成
	 **/
	@Override
	public void batchPictureSynthesis() {

		// 查询数据
		BatchPictureSynthesisRequestMessage batchPictureSynthesisRequestMessage = new BatchPictureSynthesisRequestMessage();
		List<TTaskPushMerge> tTaskPushMergeList = new ArrayList<>();
		tTaskPushMergeList = imageDao.getPushMerge(Constant.push_status_success);
		// 查询CLIENT表
		for (TTaskPushMerge tTaskPushMerge : tTaskPushMergeList) {
			String reqXml = "";
			if (tTaskPushMerge.getNoteFtpImageFileName() == null) {
				TNoteClientNote tNoteClientNote = clientdao.getClientPictureInfo(tTaskPushMerge.getClientNoteId());
				// 查询
				String noteid = clientdao.getNoteIdByclietId(tTaskPushMerge.getClientNoteId());
				Map<String, Object> map = new HashMap<>();
				map.put("noteid", noteid);
				map.put("type", Constant.image_type_Physical_UPLOAD_SELF_PICTURES);
				TNoteImage tNoteImage = imageDao.getTNoteImage(map);
				// 体检函件FTP下载与上传
				try {
					FTPClientUtil ftpClientUtil = new FTPClientUtil();
					String phName = getMaxNo.getMaxNo();
					String localPath = "D:\\image\\YuanToFtp\\" + phName + "tijian.pdf";
					ftpClientUtil.download(tNoteClientNote.getNoteFtpImageUrl(), localPath, Constant.FTP_SERVER,
							Constant.FTP_RPORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
					ftpClientUtil.upload(localPath, phName + "physical.pdf", Constant.FTP_YUANMEI_PATH,
							Constant.FTP_SERVER, Constant.FTP_RPORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
					// 从OSS下载图片
					String ziPaiName = getMaxNo.getMaxNo();
					String localoss = "D:\\image\\YuanToFtp\\" + ziPaiName + "zipai.png";
					// 文件保存位置
					File saveDir = new File(localoss);
					if (!saveDir.exists()) {
						saveDir.mkdir();
					}
					byte[] img = null;
					String osspath = Constant.CHANNEL_NO + tNoteImage.getImageFile() + tNoteImage.getImageName();
					OSSUtil.getUploadFile(osspath, localoss);
					File file = new File(saveDir + File.separator + tNoteImage.getImageName());
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(img);
					if (fos != null) {
						fos.close();
					}
					// 将图片上传到Ftp
					ftpClientUtil.upload(localoss, ziPaiName + "zipai.png", Constant.FTP_YUANMEI_PATH,
							Constant.FTP_SERVER, Constant.FTP_RPORT, Constant.FTP_USERNAME, Constant.FTP_PASSWORD);
					String ftpurlphoto = "remoteFoldPath" + "newName";
					// 将数据存到P处理推送表
					Date date = new Date();
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String time = sdf.format(date);
					Map<String, Object> imgMap = new HashMap<>();
					imgMap.put("NoteFtpUrl", tNoteClientNote.getNoteFtpImageUrl());
					imgMap.put("NoteFtpFileName", phName + "physical.pdf");
					imgMap.put("phoneFtpUrl", ftpurlphoto);
					imgMap.put("phoneFtpName", ziPaiName + "zipai.png");
					imgMap.put("id", tNoteClientNote.getClientNoteId());
					imageDao.UpdateTtaskMerge(imgMap);
					// 组织报文
					batchPictureSynthesisRequestMessage.getHead().setManageCom(tTaskPushMerge.getChannelType());
					batchPictureSynthesisRequestMessage.getHead().setSaleChnl(tTaskPushMerge.getChannelType());
					batchPictureSynthesisRequestMessage.getHead().setTranDate(time);
					batchPictureSynthesisRequestMessage.getHead().setTranTime(time);
					batchPictureSynthesisRequestMessage.getHead().setMD5("");
					batchPictureSynthesisRequestMessage.getBody().setPhysical_note_id(tTaskPushMerge.getClientNoteId());
					batchPictureSynthesisRequestMessage.getBody().getPhysical()
							.setFileUrl(tTaskPushMerge.getNoteFtpImageFileName());
					batchPictureSynthesisRequestMessage.getBody().getPhysical()
							.setHttpUrl(tTaskPushMerge.getNoteFtpImageUrl());
					batchPictureSynthesisRequestMessage.getBody().getClient()
							.setFileUrl(tTaskPushMerge.getPhoneFtpFileName());
					batchPictureSynthesisRequestMessage.getBody().getClient()
							.setHttpUrl(tTaskPushMerge.getPhoneFtpUrl());
					// 转换成XML
					reqXml = XmlMapper.toXml(batchPictureSynthesisRequestMessage);

				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			} else {
				Date date = new Date();
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String time = sdf.format(date);
				// 组织报文
				batchPictureSynthesisRequestMessage.getHead().setManageCom(tTaskPushMerge.getChannelType());
				batchPictureSynthesisRequestMessage.getHead().setSaleChnl(tTaskPushMerge.getChannelType());
				batchPictureSynthesisRequestMessage.getHead().setTranDate(time);
				batchPictureSynthesisRequestMessage.getHead().setTranTime(time);
				batchPictureSynthesisRequestMessage.getHead().setMD5("");
				batchPictureSynthesisRequestMessage.getBody().setPhysical_note_id(tTaskPushMerge.getClientNoteId());
				batchPictureSynthesisRequestMessage.getBody().getPhysical()
						.setFileUrl(tTaskPushMerge.getNoteFtpImageFileName());
				batchPictureSynthesisRequestMessage.getBody().getPhysical()
						.setHttpUrl(tTaskPushMerge.getNoteFtpImageUrl());
				batchPictureSynthesisRequestMessage.getBody().getClient()
						.setFileUrl(tTaskPushMerge.getPhoneFtpFileName());
				batchPictureSynthesisRequestMessage.getBody().getClient().setHttpUrl(tTaskPushMerge.getPhoneFtpUrl());
				// 转换成XML
				reqXml = XmlMapper.toXml(batchPictureSynthesisRequestMessage);

			}

			String sign = CryptoUtil.MD5(reqXml + "efghijklmnopqrstuvwxyz1234567890");

			// 请求元镁图片合成接口
			String url = "";
			Map<String, String> headers = new HashMap<>();
			headers.put("sign", sign);
			String result = restClient.postAddHeader(url, reqXml, headers, null);
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(),
					"元镁图片合成接口响应：", result);
		}
	}
}
