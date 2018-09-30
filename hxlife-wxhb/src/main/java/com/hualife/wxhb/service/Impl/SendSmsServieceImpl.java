package com.hualife.wxhb.service.Impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.mesiframework.core.util.time.DateUtil;
import com.hualife.mesiframework.restclient.RestClient;
import com.hualife.wxhb.api.rest.message.request.SendSmsRequestMessage;
import com.hualife.wxhb.common.Assert;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.integration.dao.SmsDao;
import com.hualife.wxhb.service.GetMaxNo;
import com.hualife.wxhb.service.SendSmsServiece;
import com.primeton.ext.infra.security.BASE64Encoder;

/**
 * @author yangpeixin
 * @description 短信发送
 * @date 2017-08-04
 */
@Service
public class SendSmsServieceImpl implements SendSmsServiece {

	private final Logger logger = LoggerFactory.getLogger(SendSmsServieceImpl.class);

	@Autowired
	private SmsDao smsDao;

	@Autowired
	private RestClient restClient;

	@Autowired
	private GetMaxNo getMaxNo;

	/**
	 * 短信发送
	 **/

	@Override
	public void sendSms(SendSmsRequestMessage sendSmsRequestMessage) {
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),sendSmsRequestMessage.getNote_id(), "开始处理数据");
		// 校验参数是否为空或为null
		this.checkResData(sendSmsRequestMessage);
		// 接收验证码
		this.SaveResult(sendSmsRequestMessage);

	}

	/**
	 * 入参信息
	 **/
	public void checkResData(SendSmsRequestMessage sendSmsRequestMessage) {
		Assert.notNull(sendSmsRequestMessage, "入参对象不能为空");
		Assert.notEmpty(sendSmsRequestMessage.getApplicant_phone(), "手机号不能为空");
		Assert.notEmpty(sendSmsRequestMessage.getNote_id(), "NOTEID不能为空");
		String phone = smsDao.findPhone(sendSmsRequestMessage.getNote_id());
		if (!sendSmsRequestMessage.getApplicant_phone().equals(phone)) {

			throw new BusinessException("手机号与投保人信息不符合");
		}
	}

	/**
	 * MD5加密
	 **/
	public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		// 加密后的字符串
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}

	/**
	 * 存储信息
	 **/
	public void SaveResult(SendSmsRequestMessage sendSmsRequestMessage) {
		// 4位验证码
		String str = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
		Random r = new Random();
		String arr[] = new String[4];
		String phonecode = "";
		for (int i = 0; i < 4; i++) {
			int n = r.nextInt(62);

			arr[i] = str.substring(n, n + 1);
			phonecode += arr[i];

		}
		// 秘钥合成
		StringBuffer from = new StringBuffer("wxtb");
		StringBuffer phone = new StringBuffer(sendSmsRequestMessage.getApplicant_phone());
//		StringBuffer code = new StringBuffer(phonecode);
		StringBuffer code = new StringBuffer("123456");//本地测试
		StringBuffer my = new StringBuffer("faj34j09fadf9j09");
		String msg = from.append(phone).append(code).append(my).toString();
		String sign = "";
		try {
			sign = EncoderByMd5(msg);
		} catch (Exception e) {
			throw new BusinessException("MD5加密异常");
		}

//		// 调用服务器
//		String url = "http:// 114.55.155.251/send/sms";
//		JSONObject json = new JSONObject();
//		json.put("phone", sendSmsRequestMessage.getApplicant_phone());
//		json.put("code", code);
//		json.put("sign", sign);
//		Map<String, Object> map = new HashMap<>();
//		restClient.getForObject(url, JSONObject.class, map, null);
		// 更改状态
		Map<String, Object> updateSmsMap = new HashMap<>();
		updateSmsMap.put("isused", Constant.sms_Y);
		updateSmsMap.put("status", Constant.sms_N);
		updateSmsMap.put("phone", sendSmsRequestMessage.getApplicant_phone());
		smsDao.updateSms(updateSmsMap);
		// 存表
		Date date = new Date();
		String time = DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss", date);
		Map<String, Object> addSmsMap = new HashMap<>();
		
		String id = getMaxNo.getMaxNo();
		addSmsMap.put("id", id);
		addSmsMap.put("code", code.toString());
		addSmsMap.put("isused", Constant.sms_N);
		addSmsMap.put("phone", sendSmsRequestMessage.getApplicant_phone());
		addSmsMap.put("validity_date", DateFormatUtil.formatDate("yyyy-MM-dd HH:mm:ss",DateUtil.addMinutes(date, Constant.SMS_VALID_TIME)));
		addSmsMap.put("create_date", time);
		smsDao.addSms(addSmsMap);

	}
}