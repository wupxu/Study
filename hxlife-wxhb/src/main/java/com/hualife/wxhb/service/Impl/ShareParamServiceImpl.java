package com.hualife.wxhb.service.Impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hualife.mesiframework.core.exception.BusinessException;
import com.hualife.mesiframework.core.log.DiagnosisBusinessLogger;
import com.hualife.mesiframework.core.log.LogPrefixUtil;
import com.hualife.mesiframework.restclient.RestClient;
import com.hualife.wxhb.api.rest.message.response.ShareParamInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ShareParamInfoResponseMessage;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.common.Md5;
import com.hualife.wxhb.service.ShareParamService;

/**
 * @deprecation : 分享配置参数接口实现接口实现类serviceImpl
 * @author : zhanyilong
 * @date : 2017-08-07  
 */
@Service
public class ShareParamServiceImpl implements ShareParamService {
	
	private final Logger logger = LoggerFactory.getLogger(ShareParamServiceImpl.class); 
	
	@Autowired
	private RestClient restClient;
	
	/**
	 * 分享配置参数接口实现方法
	 * **/
	@Override
	public ShareParamInfoResponseMessage shareparam(ShareParamInfoRequestMessage shareParamInfoRequestMessage){
		ShareParamInfoResponseMessage ShareParamInfoResponseMessage = new ShareParamInfoResponseMessage();
		
		Map<String,Object> baseParamMap = new HashMap<String,Object>();
		String appid = Constant.appid;
		
		//获取企业号基本参数
		baseParamMap = this.queryBaseParam();
		
		//获取报文参数
		String jsapi_ticket = baseParamMap.get("jsapi_ticket").toString(); /* 获取ticket */
		Long timestamp = System.currentTimeMillis() / 1000; /* 生成签名的时间戳 */
		String nonceStr = UUID.randomUUID().toString(); /* 生成签名的随机串 */
		String jsonParam = "";
		try {
			jsonParam = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + 
					 "&timestamp=" + timestamp + "&url=" + URLDecoder.decode(shareParamInfoRequestMessage.getUrl(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),"jsonParam："+jsonParam, "获取企业号加密前参数");
		//通过Md5转码
		String signature = Md5.SHA1(jsonParam);
		
		//组装报文
		ShareParamInfoResponseMessage.setAppId(appid);
		ShareParamInfoResponseMessage.setTimestamp(timestamp);
		ShareParamInfoResponseMessage.setSignature(signature);
		ShareParamInfoResponseMessage.setNonceStr(nonceStr);
		
		return ShareParamInfoResponseMessage;
	}
	
	
	/**
	 * 获取企业号基本参数
	 */
	private  Map<String, Object> queryBaseParam() {
		long startTime = 0;
		long endTime = 0;
		
		Long timestamp = System.currentTimeMillis();
		String nonce = UUID.randomUUID().toString();
		String trade_source = Constant.enterprise_QUERY_USER_INFOR_TRADE_SOURCE;
		String key = Constant.enterprise_QUERY_USER_INFOR_KEY;
		String signature = "";
		try {
			signature = Md5.Md5_32(key + timestamp + nonce + trade_source).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BusinessException("Md5转码异常");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BusinessException("Md5转码异常");
		}
		
		//调用企业号 获取企业号成本参数 接口
		//组装报文
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("timestamp", String.valueOf(timestamp));
		paramMap.put("nonce", String.valueOf(nonce));
		paramMap.put("trade_source", String.valueOf(trade_source));
		paramMap.put("signature", String.valueOf(signature));
		
		//调用企业号
		startTime = System.currentTimeMillis();
		JSONObject jsapi_ticket = restClient.getForSimple(Constant.enterprise_QUERY_USER_PRD_INFOR_URL, paramMap,null);
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),String.valueOf(jsapi_ticket),"timestamp="+timestamp+";nonce="+nonce+";trade_source="+trade_source+";signature="+signature);
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),String.valueOf(jsapi_ticket),"调用企业号接口获取ticket="+jsapi_ticket);
		endTime = System.currentTimeMillis();
		logger.info(DiagnosisBusinessLogger.generateLogTemplate(),LogPrefixUtil.getBusinessPrefix(),String.valueOf(trade_source),"调用企业号接口耗时"+(endTime-startTime)+"ms");
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(jsapi_ticket.get("result_code").toString().equals(Constant.push_status)){
			map.put("jsapi_ticket", jsapi_ticket.get("ticket").toString());
		}else{
			throw new BusinessException("获取企业号参数ticket失败");
		}
		return map;
	}

	public static void main(String[] args){
		//获取报文参数
		/*String jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VOXmwwAVZgsTWQ2apIJrHLgpYQRjkRNasJVpA_1--E-UVjdUWsFJgAQpJivX4zMm7w";  获取ticket 
		String url = "http%3A%2F%2Fwxtb2017-dev.ihxlife.com%2Fwxhb%2Fhtml%2FweChart%2Fhealth.html";
		Long timestamp = System.currentTimeMillis() / 1000;  生成签名的时间戳 
		String nonceStr = UUID.randomUUID().toString();  生成签名的随机串 
		String jsonParam = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + 
				 "&timestamp=" + timestamp + "&url=" + url;
		//通过Md5转码
		System.out.println("jsapi_ticket="+jsapi_ticket);
		System.out.println("url="+url);
		System.out.println("timestamp="+timestamp);
		System.out.println("nonceStr="+nonceStr);
		String signature = Md5.SHA1(jsonParam);
		System.out.println(signature);*/
		//1741857dcb2e4fa0163896dec8515bf4292adc27
		String url = "http%3A%2F%2Fwxtb2017-dev.ihxlife.com%2Fwxhb%2Fhtml%2FweChart%2Fhealth.html";
		try {
			String url_new = URLDecoder.decode(url, "UTF-8");
			System.out.println(url_new);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//转码
	}
	
	
	/**
	 * sha1加密
	 * @param 传入字符串
	 * @return  返回加密后字符串
	 */
	public static String SHA1(String decript) {
		String signature = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(decript.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (BusinessException b){
		} catch (SystemException e) {
			
		} catch (Throwable t) {
			
		}
		return signature;
	}
    
	
	/**
	 * 转码
	 * **/
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
