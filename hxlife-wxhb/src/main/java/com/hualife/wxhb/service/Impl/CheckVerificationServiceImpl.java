package com.hualife.wxhb.service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.wxhb.api.rest.message.request.CheckVerificationRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.SmsDao;
import com.hualife.wxhb.service.CheckVerificationService;

/**
 * @author yangpeixin
 * @description 验证码校验
 * @date 2017-08-04
 */

@Service
public class CheckVerificationServiceImpl implements CheckVerificationService {

	private final Logger logger = LoggerFactory.getLogger(CheckVerificationServiceImpl.class);

	@Autowired
	private SmsDao smsDao;

	/**
	 * 短信验证
	 **/
	@Transactional
	public void checkVerification(CheckVerificationRequestMessage checkVerificationRequestMessage) {
		// 校验参数是否为空或为null
		this.checkResData(checkVerificationRequestMessage);
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),checkVerificationRequestMessage.getNote_id(), "开始处理数据");
		// 校验验证码
		this.checkResult(checkVerificationRequestMessage);

	}

	/**
	 * 短信验证
	 **/
	public void checkResult(CheckVerificationRequestMessage checkVerificationRequestMessage) {
		long startTime =0;
		long endTime =0;
		// 操作数据库
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),checkVerificationRequestMessage.getNote_id(), "开始短信验证");
		Map<String, Object> phoneCodeMap = new HashMap<>();
		phoneCodeMap.put("phone", checkVerificationRequestMessage.getApplicant_phone());
		phoneCodeMap.put("isused", Constant.sms_N);
		// 查询表里验证码
		Date time = new Date();
		Map<String, Object> timeMap = new HashMap<>();
		timeMap.put("isused", Constant.sms_N);
		timeMap.put("phone", checkVerificationRequestMessage.getApplicant_phone());		
		//耗时
		startTime = System.currentTimeMillis();
		Date validityDate = smsDao.findTime(timeMap);		
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:"+checkVerificationRequestMessage.getNote_id(), "查询数据耗时:"+(endTime-startTime)+"ms");
		if(validityDate==null){
			throw new BusinessException("请先点击发送短信");
		}
		//查看是否在有效期
		if(time.before(validityDate)){
			//查询耗时
			startTime = System.currentTimeMillis();		
			String phonecode = smsDao.phoneCode(phoneCodeMap);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:"+checkVerificationRequestMessage.getNote_id(), "查询数据耗时:"+(endTime-startTime)+"ms");
			if (checkVerificationRequestMessage.getCode().equals(phonecode)) {
				Map<String, Object> map = new HashMap<>();
				map.put("isused", Constant.sms_Y);
				map.put("phone", checkVerificationRequestMessage.getApplicant_phone());
				map.put("code", checkVerificationRequestMessage.getCode());
				 //耗时
				startTime = System.currentTimeMillis();
				smsDao.updateCode(map);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:"+checkVerificationRequestMessage.getNote_id(), "更新数据耗时:"+(endTime-startTime)+"ms");
				
				Map<String, Object> updateMainMap = new HashMap<>();
				updateMainMap.put("id", checkVerificationRequestMessage.getNote_id());
				updateMainMap.put("phoneSuccess", Constant.answer_Y);
			    //耗时
				startTime = System.currentTimeMillis();
				smsDao.updateMain(updateMainMap);
				endTime = System.currentTimeMillis();
				logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:"+checkVerificationRequestMessage.getNote_id(), "更新数据耗时:"+(endTime-startTime)+"ms");
			} else {
				
				throw new BusinessException("验证填写错误");
			}			
		}
		else{
			Map<String, Object> map = new HashMap<>();
			map.put("isused", Constant.sms_Y);
			map.put("phone", checkVerificationRequestMessage.getApplicant_phone());
			map.put("code", checkVerificationRequestMessage.getCode());
			//耗时
			startTime = System.currentTimeMillis();
			smsDao.updateCode(map);
			endTime = System.currentTimeMillis();
			logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"noteid:"+checkVerificationRequestMessage.getNote_id(), "更新数据耗时:"+(endTime-startTime)+"ms");
			throw new BusinessException("验证码过期");
		}

	}

	/**
	 * 入参信息
	 **/
	public void checkResData(CheckVerificationRequestMessage checkVerificationRequestMessage) {
		Assert.notNull(checkVerificationRequestMessage, "入参对象不能为空");
		Assert.notEmpty(checkVerificationRequestMessage.getApplicant_phone(), "手机号不能为空");
		Assert.notEmpty(checkVerificationRequestMessage.getNote_id(), "NOTEID不能为空");
		Assert.notEmpty(checkVerificationRequestMessage.getCode(), "验证码不能为空");

	}
}
