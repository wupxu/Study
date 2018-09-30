package com.hualife.wxhb.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.service.GetMaxNo;

/**
 * @author "张龙"
 * @Description 获取对应id
 * @time 2017年8月19日 下午2:48:52
 */
@Service
public class GetMaxNoImpl implements GetMaxNo{
	
	private String MaxNo = "";
	
	@Override
	public String getMaxNo() {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date(); 
		String time =dateFormat1.format(date); 
		if("".equals(MaxNo)){
			MaxNo = time+"00001";
		}else{
			String checkTime =dateFormat2.format(date);
			if(MaxNo.startsWith(checkTime)){
				Long maxNo = Long.valueOf(MaxNo)+1;
				MaxNo = Long.toString(maxNo);
			}else{
				MaxNo = time+"00001";
			}
		}
		return MaxNo;
	}
}
