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
import com.hualife.wxhb.api.rest.message.request.FinaOccuInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.FinaOccuInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.domain.dto.FinaItem;
import com.hualife.wxhb.integration.dao.FinaDao;
import com.hualife.wxhb.service.FinaOccuInfoService;

/**
 * @author 吴培旭
 * @description 财务函初始化实现类
 * @time 创建时间：2017年8月5日
 */
@Service
public class FinaOccuInfooServiceImpl implements FinaOccuInfoService {
	private final Logger logger = LoggerFactory.getLogger(FinaOccuInfooServiceImpl.class);
	@Autowired
	private FinaDao finaDao;
	/**
	 * @author 吴培旭
	 * @description 财务函初始化
	 * @time 创建时间：2017年8月5日
	 */
	@Override
	public FinaOccuInfoResponseMessage finaOccuInfo(FinaOccuInfoRequestMessage finaOccuInfoRequestMessage) {
		long startTime=0;
		long endTime=0;
		// 非空校验
		checkData(finaOccuInfoRequestMessage);
		String finaNoteId = finaOccuInfoRequestMessage.getFinaNoteId();
		FinaOccuInfoResponseMessage finaOccuInfoResponseMessage = new FinaOccuInfoResponseMessage();
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(), "查询需要的信息");
		// 查询客户层中的noteId
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询客户层中的noteId");
		startTime = System.currentTimeMillis(); 
		String noteId = finaDao.selectFinaOccuInfoNoteId(finaNoteId);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(),"查询客户层中的noteId,程序运行时间： " + (endTime - startTime) + "ms");
		
		// 查询核保员特别说明，被保人证件类型，被保人证件号码，被保人电话
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询核保员特别说明，被保人证件类型，被保人证件号码，被保人电话");
		startTime = System.currentTimeMillis(); 
		finaOccuInfoResponseMessage = finaDao.selectFinaOccuInfodesc(finaOccuInfoRequestMessage);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(),"查询核保员特别说明，被保人证件类型，被保人证件号码，被保人电话,程序运行时间： " + (endTime - startTime) + "ms");
		String specialDesc = finaOccuInfoResponseMessage.getSpecialDesc();
		String insuredIdType = finaOccuInfoResponseMessage.getInsuredIdType();
		String insuredIdNo = finaOccuInfoResponseMessage.getInsuredIdNo();
		String insuredPhone = finaOccuInfoResponseMessage.getInsuredPhone();
		
		// 查询客户 姓名 ，代理人姓名，代理人电话，代理人编号
		Map<Object, Object> finaMap = new HashMap<>();
		finaMap.put("finaNoteId", finaNoteId);
		finaMap.put("noteId", noteId);
		finaMap.put("validY", Constant.valid_Y);
		finaMap.put("clientTypeFina", Constant.note_from_core_type_FINAOCCU);
		finaMap.put("imageTypeFina", Constant.image_type_Fina_CLIENT_TAKE_PICTURES);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询客户 姓名 ，代理人姓名，代理人电话，代理人编号");
		startTime = System.currentTimeMillis(); 
		finaOccuInfoResponseMessage = finaDao.selectFinaOccuInfoRespMes(finaMap);
		if (finaOccuInfoResponseMessage==null) {
			throw new BusinessException("财务函初始化用finaNoteId查询结果为空");
		}
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(),"查询客户 姓名 ，代理人姓名，代理人电话，代理人编号,程序运行时间： " + (endTime - startTime) + "ms");
		finaOccuInfoResponseMessage.setAgentName(finaOccuInfoResponseMessage.getAgentName());
		
		// 查询具体财务问卷   
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询具体财务问卷");
		List<FinaItem> finaItemList = new ArrayList<FinaItem>();
		startTime = System.currentTimeMillis(); 
		finaItemList = finaDao.selectFinaQuestionnaire(finaNoteId);
		for (FinaItem finaItem : finaItemList) {
			String noteItemType = finaItem.getNoteItemType();
			finaItem.setNoteItemTypeName(DataConVersion.dataConVersion(Constant.transfer_data_FINA_TYPE_NAME,noteItemType));
		}
		endTime = System.currentTimeMillis(); 
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(),"查询具体财务问卷,程序运行时间： " + (endTime - startTime) + "ms");
		// 查询影像集合
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(), LogPrefixUtil.getBusinessPrefix(), "",
				"查询影像集合");
		List<ImageInfo> finaimagesList = new ArrayList<>();
		startTime = System.currentTimeMillis(); 
		finaimagesList = finaDao.selectImages(finaMap);
		for (ImageInfo imageInfo : finaimagesList) {
			String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
			String imageUrl=OSSUtil.getUrl(ossPath);
			imageInfo.setImage_url(imageUrl);
		}
		endTime = System.currentTimeMillis(); 
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(),"查询具体财务问卷,程序运行时间： " + (endTime - startTime) + "ms");
		
		// 组织返回报文
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(),"组织返回报文");
		finaOccuInfoResponseMessage.setFina_questionnaire(finaItemList);
		finaOccuInfoResponseMessage.setImages(finaimagesList);
		finaOccuInfoResponseMessage.setSpecialDesc(specialDesc);
		finaOccuInfoResponseMessage.setInsuredIdNo(insuredIdNo);
		finaOccuInfoResponseMessage.setInsuredIdType(DataConVersion.dataConVersion(Constant.transfer_core_data_ID_TYPE_NAME, insuredIdType));
		finaOccuInfoResponseMessage.setInsuredPhone(insuredPhone);
		return finaOccuInfoResponseMessage;
	}
	private void checkData(FinaOccuInfoRequestMessage finaOccuInfoRequestMessage) {
		Assert.notNull(finaOccuInfoRequestMessage, Constant.error_DESC_BODY_NULL);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),finaOccuInfoRequestMessage.getFinaNoteId(), "财务问卷初始化开始检查报文");
		Assert.notEmpty(finaOccuInfoRequestMessage.getFinaNoteId(), "财务问卷id不能null");
	}

}
