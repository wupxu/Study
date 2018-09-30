package com.hualife.wxhb.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.pojo.Applicant;
import com.hualife.wxhb.api.rest.message.request.SurvivalInvestInfoRequestMeassage;
import com.hualife.wxhb.api.rest.message.response.SurvivalInvestInfoResponseMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.HealthDao;
import com.hualife.wxhb.integration.dao.SurvivalDao;
import com.hualife.wxhb.service.SurvivalInvestInfoService;
/**
 * @description:初始化契调通知书信息实现类serviceImpl
 * @author yangpeixin
 * @date :2017.08.11
 */
@Service
public class SurvivalInvestInfoServiceImpl implements SurvivalInvestInfoService {
	private final Logger logger = LoggerFactory.getLogger(SurvivalInvestInfoServiceImpl.class);
	@Autowired
	private HealthDao healthDao;
	@Autowired
	private SurvivalDao survivalDao;
	
	 /**
     * 契调方法
     */	
	public SurvivalInvestInfoResponseMessage survivalInvestInfo(SurvivalInvestInfoRequestMeassage survivalInvestInfoRequestMessage) {
		//检测参数
		checkResData(survivalInvestInfoRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+survivalInvestInfoRequestMessage.getSurvival_note_id(),"开始检查请求报文");
		//查询数据
		return this.findData(survivalInvestInfoRequestMessage);
	}
	 /**
     * 查询数据
     * 
     */	
	public SurvivalInvestInfoResponseMessage findData(SurvivalInvestInfoRequestMeassage suervivalInvestInfoRequestMessag) {
		SurvivalInvestInfoResponseMessage survivalInvestInfoResponseMessage = new SurvivalInvestInfoResponseMessage();
		long startTime =0;
		long endTime =0;
		//查询NOTEID
		startTime =System.currentTimeMillis();
		String noteid = healthDao.getNoteId(suervivalInvestInfoRequestMessag.getSurvival_note_id());
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+suervivalInvestInfoRequestMessag.getSurvival_note_id(), "根据契调函id查询函件id--耗时:"+(endTime-startTime)+"ms");
		//查询契调原因
		List<String> list = new ArrayList<String>();
		startTime = System.currentTimeMillis();
		list = survivalDao.getReason(suervivalInvestInfoRequestMessag.getSurvival_note_id());
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+suervivalInvestInfoRequestMessag.getSurvival_note_id(), "查询数据耗时:"+(endTime-startTime)+"ms");
		//契调信息
		startTime = System.currentTimeMillis();
		survivalInvestInfoResponseMessage = survivalDao.survivalInvestInfo(suervivalInvestInfoRequestMessag.getSurvival_note_id());		
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+suervivalInvestInfoRequestMessag.getSurvival_note_id(), "查询数据耗时:"+(endTime-startTime)+"ms");
		//投保人信息
		startTime = System.currentTimeMillis();
		List<Applicant> applicant = survivalDao.applicant(noteid);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"契调函id:"+suervivalInvestInfoRequestMessag.getSurvival_note_id(), "查询数据耗时:"+(endTime-startTime)+"ms");

		//组织报文
		survivalInvestInfoResponseMessage.setNote_item_type(list);
		survivalInvestInfoResponseMessage.setApplicant(applicant);	
		if(survivalInvestInfoResponseMessage.getSurvival_mode().equals(Constant.survival_MODEDERICT)){
			survivalInvestInfoResponseMessage.setSurvival_mode(Constant.survival_MODEDERICTDESC);
		}else{
			survivalInvestInfoResponseMessage.setSurvival_mode(Constant.survival_MODESIDEDESC);
		}
		return survivalInvestInfoResponseMessage;
	}
	/**
	 * 检查请求报文
	 * **/
	public void checkResData(SurvivalInvestInfoRequestMeassage survivalInvestInfoRequestMessage) {
		Assert.notNull(survivalInvestInfoRequestMessage, Constant.error_DESC_BODY_NULL);
		Assert.notEmpty(survivalInvestInfoRequestMessage.getSurvival_note_id(), "契调函id不能为空");		
	}
}
