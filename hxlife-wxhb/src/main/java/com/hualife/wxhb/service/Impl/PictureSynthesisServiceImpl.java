package com.hualife.wxhb.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.io.FTPClientUtil;
import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.wxhb.api.rest.message.pojo.FormXml;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.ImageDao;
import com.hualife.wxhb.service.PictureSynthesisService;

/**
 * @author yangpeixin
 * @description 图片合成
 * @date 2017-08-28
 */

@Service
public class PictureSynthesisServiceImpl implements PictureSynthesisService {

	private final Logger logger = LoggerFactory.getLogger(PictureSynthesisServiceImpl.class);

	@Autowired
	private ImageDao imageDao;

	/**
	 * 图片合成
	 **/
	@Override
	public void pictureSynthesis(String form) {

		// 校验参数是否为空或为null
		FormXml formXml = XmlMapper.fromXml(form, FormXml.class);
		this.checkResData(formXml);
		// 存储数据
		this.savePictureSynthesis(formXml);
	}

	/**
	 * 存储数据
	 **/
	private void savePictureSynthesis(FormXml formXml) {
		if (formXml.getHead().getStatus().equals("Success")) {
			try {
				long startTime = 0;
				long endTime = 0;
				FTPClientUtil ftpClientUtil = new FTPClientUtil();
				String server = "";
				Integer port = 0;
				String userName = "";
				String userPassword = "";
				String localPath = "";
				ftpClientUtil.download(null, localPath, server, port, userName, userPassword);
				// oss
				File input = new File(localPath);
				InputStream is = null;
				is = new FileInputStream(input);
				// 上传到oos
				OSSUtil.uploadFileByInputStream(is, "");
				// 获取
				String noteOssUrl = OSSUtil.getUrl("");
				Map<String, Object> map = new HashMap<>();
				map.put("physicalId", formXml.getBody().getPhysical_note_id());
				map.put("httpUrl", formXml.getBody().getPhysical_tif().getHttpUrl());
				map.put("ossUrl", noteOssUrl);
				startTime = System.currentTimeMillis();
				imageDao.updateUrl(map);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:" + formXml.getBody().getPhysical_note_id(),
						"更新数据耗时:" + (endTime - startTime) + "ms");
				// 更改推送状态
				Map<String, Object> updatePushImageMap = new HashMap<>();
				updatePushImageMap.put("noteId", formXml.getBody().getPhysical_note_id());
				updatePushImageMap.put("status", Constant.push_status_success);
				imageDao.updatePushImage(updatePushImageMap);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// 更改推送状态
			Map<String, Object> updatePushImageMap = new HashMap<>();
			updatePushImageMap.put("noteId", formXml.getBody().getPhysical_note_id());
			updatePushImageMap.put("status", Constant.push_status_failed);
			imageDao.updatePushImage(updatePushImageMap);
		}

	}

	/**
	 * 检测入参
	 **/
	public void checkResData(FormXml formXml) {
		Assert.notNull(formXml, "入参对象不能为空");
		Assert.notNull(formXml.getBody(), "BODY不能为空");
		Assert.notNull(formXml.getHead(), "HEAD不能为空");

	}

}
