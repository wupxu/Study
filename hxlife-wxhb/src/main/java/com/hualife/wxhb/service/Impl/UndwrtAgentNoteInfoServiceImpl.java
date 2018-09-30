package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.UndwrtNoteProduct;
import com.hualife.wxhb.api.rest.message.request.UndwrtAgentNoteInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.UndwrtAgentNoteInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.DataConVersion;
import com.hualife.wxhb.common.OSSUtil;
import com.hualife.wxhb.integration.dao.UndwrtDao;
import com.hualife.wxhb.service.UndwrtAgentNoteInfoService;
/**
 * @author wangt
 * @description 核保函初始化impl
 * @time 2017-08-08
 */
@Service
public class UndwrtAgentNoteInfoServiceImpl implements UndwrtAgentNoteInfoService {

	private final Logger logger = LoggerFactory.getLogger(UndwrtAgentNoteInfoServiceImpl.class);
	
	@Autowired
	private UndwrtDao undwrtDao; 
	/**
	 * 组织核保涵初始化报文
	 */
	@Override
	public UndwrtAgentNoteInfoResponseMessage undwrtAgentNoteInfo(UndwrtAgentNoteInfoRequestMessage undwrtAgentNoteInfoRequestMessage) {	
		
		checkData(undwrtAgentNoteInfoRequestMessage);
		
		UndwrtAgentNoteInfoResponseMessage undwrtAgentNoteInfoResponseMessage = new UndwrtAgentNoteInfoResponseMessage();				
		Map<Object, Object> map = new HashMap<>();	
		long startTime = 0;
		long endTime   = 0;
		
		String noteUndwrtId = undwrtAgentNoteInfoRequestMessage.getNote_undwrt_id();
		
		map.put("note_undwrt_id",noteUndwrtId);	
		
		//获取noteid
		startTime = System.currentTimeMillis();
		String noteId = undwrtDao.getNoteIDByNoteUndwrtId(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"获取noteid耗时"+(endTime-startTime));
		if(noteId == null){
			throw new BusinessException("函件ID查询为空");
		}
		map.put("note_id",noteId);	
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId, "开始查询核保函信息");
		//查询核保函信息
		startTime = System.currentTimeMillis();
		undwrtAgentNoteInfoResponseMessage = undwrtDao.getUndwrtNote(map);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"查询核保函信息耗时"+(endTime-startTime));
		
		if(undwrtAgentNoteInfoResponseMessage != null){

			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId, "开始查询影像信息");
			//查询影像信息	
			List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();		
			
			String noteType = Constant.note_from_core_type_UNDWRT;
			map.put("note_type", noteType);
			
			startTime = System.currentTimeMillis(); 
			imageInfoList = undwrtDao.getImages(map);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"查询影像信息耗时"+(endTime-startTime));
			
			if(imageInfoList != null){
				//设置影像路径
				for(ImageInfo imageInfo:imageInfoList){
					String ossPath=Constant.CHANNEL_NO+imageInfo.getImage_url();
					String imageUrl=OSSUtil.getUrl(ossPath);
					imageInfo.setImage_url(imageUrl);
				}
			}
			
			undwrtAgentNoteInfoResponseMessage.setImages(imageInfoList);
			
			logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId, "开始查询险种信息");
			//查询险种信息
			List<UndwrtNoteProduct> undwrtNoteProductList = new ArrayList<UndwrtNoteProduct>();
			
			startTime = System.currentTimeMillis();
			undwrtNoteProductList = undwrtDao.getProducts(map);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId,"查询险种信息耗时"+(endTime-startTime));
			
			if(undwrtNoteProductList != null){
				for(UndwrtNoteProduct undwrtNoteProduct:undwrtNoteProductList){
					undwrtNoteProduct.setUndwrt_result(DataConVersion.dataConVersion(Constant.transfer_core_Undwrt_Result,undwrtNoteProduct.getUndwrt_result()));
				}
				
				undwrtAgentNoteInfoResponseMessage.setProducts(undwrtNoteProductList);
				
				logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),noteUndwrtId, "开始计算保费");
				//计算保费
				double totalPrem = 0;	  //险种应交保费
				double healthAddPrem = 0; //总计健康加费金额
				double occuAddPrem = 0;   //总计职业加费金额
				for(int i=0;i<undwrtNoteProductList.size();i++){
					   if(StringUtils.isNotBlank(undwrtNoteProductList.get(i).getTotal_prem())){
						   totalPrem += Double.valueOf(undwrtNoteProductList.get(i).getTotal_prem());
					   }			   
					   if(StringUtils.isNotBlank(undwrtNoteProductList.get(i).getTotal_health_add_prem())){
						   healthAddPrem += Double.valueOf(undwrtNoteProductList.get(i).getTotal_health_add_prem());
					   }
					   if(StringUtils.isNotBlank(undwrtNoteProductList.get(i).getTotal_occu_add_prem())){
						   occuAddPrem += Double.valueOf(undwrtNoteProductList.get(i).getTotal_occu_add_prem());
					   }			   
			       } 		
				double plusPrem = healthAddPrem+occuAddPrem;	//加费合计
				double sumPrem  = plusPrem+totalPrem;			//应交保费
				
				undwrtAgentNoteInfoResponseMessage.setPlus_prem(String.valueOf(plusPrem));
				undwrtAgentNoteInfoResponseMessage.setPay_prem(String.valueOf(totalPrem));
				undwrtAgentNoteInfoResponseMessage.setSum_prem(String.valueOf(sumPrem));
				
			}
			}
		
		return undwrtAgentNoteInfoResponseMessage;
	
	}
	/**
	 * 校验请求报文
	 */
	private void checkData(UndwrtAgentNoteInfoRequestMessage undwrtAgentNoteInfoRequestMessage) {
		Assert.notNull(undwrtAgentNoteInfoRequestMessage, "报文不能为空");
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),undwrtAgentNoteInfoRequestMessage.getNote_undwrt_id(), "开始核保涵初始化报文校验");
		Assert.notEmpty(undwrtAgentNoteInfoRequestMessage.getNote_undwrt_id(), "函件id不能为空");
	}
}
