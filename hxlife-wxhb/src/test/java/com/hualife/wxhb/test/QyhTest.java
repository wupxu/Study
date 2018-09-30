package com.hualife.wxhb.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hualife.mesiframework.core.util.security.CryptoUtil;
import com.hualife.mesiframework.restclient.util.HttpUtils;

public class QyhTest {
	public static final Logger logger = LoggerFactory.getLogger(QyhTest.class);
	public static void main(String[] args) {
		
		get11();
//		post15();
	}
	
	public static void get11(){
		String url = "http://10.139.109.137:8081/api/v1/ticket/get";
		String params = getPushStrMsg("");
		String result = HttpUtils.get(url+"?" +params);
		System.out.println(result);
	}
	
	public static void post15() {
		String url = "http://10.139.109.137:8081/api/v1/message/text/send";
		// 创建默认的httpClient实例.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost  
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列  
		List<BasicNameValuePair> formparams = new ArrayList<>();
		
		for(Map.Entry<String, String> entry : getPushMapMsg(getData()).entrySet()){
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return
	 */
	public static String getData() {
		JSONObject data = new JSONObject();
		data.put("touser","110001797");
		data.put("toparty", "");
		data.put("totag", "");
		data.put("agentid", "0");
		data.put("msgtype", "text");
		JSONObject textJson= new JSONObject();
		textJson.put("content", "ssssa");
		data.put("text", textJson);
		return data.toJSONString();
	}
	
	/**
	 * 拼装企业号报文参数
	 * 
	 * @param data 推送人员信息
	 * @return 企业号请求报文参数map
	 */
	public static Map<String,String> getPushMapMsg(String data) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String timestamp = "" + System.currentTimeMillis();
		String trade_source = "WXHB";
		String nonce = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		String KEY = "DKR47132HRS4R7162ZQXM72961R22K4O";
		
		String secryptStr = KEY+ timestamp + nonce + trade_source + data;
		String signature = CryptoUtil.MD5(secryptStr);
		paramMap.put("timestamp", timestamp);
		paramMap.put("trade_source", trade_source);
		paramMap.put("nonce", nonce);
		paramMap.put("signature", signature);
		paramMap.put("data", data);
		
		return paramMap;
	}
	
	/**
	 * 拼装企业号报文参数
	 * 
	 * @param data 推送人员信息
	 * @return 企业号请求报文参数map
	 */
	public static String getPushStrMsg(String data) {
		String timestamp = "" + System.currentTimeMillis();
		String trade_source = "WXHB";
		String nonce = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		String KEY = "DKR47132HRS4R7162ZQXM72961R22K4O";
		
		String secryptStr = KEY+ timestamp + nonce + trade_source + data;
		String signature = CryptoUtil.MD5(secryptStr);
		StringBuilder sb = new StringBuilder();
		sb.append("timestamp=").append(timestamp);
		sb.append("&trade_source=").append(trade_source);
		sb.append("&nonce=").append(nonce);
		if (!"".equals(data)) {
			sb.append("&data=").append(data);
		}
		sb.append("&signature=").append(signature);
		return sb.toString();
	}
}
