package com.hualife.wxhb.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**  
 * @Description: 日期的转换
 * @author zhanglong 
 * @date 2017年9月5日 下午5:19:34  
 */
public class dateFormat {
	private static SimpleDateFormat sdfdate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date parse(String parseTime){
		try{
			return sdfdate.parse(parseTime);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
			
}
