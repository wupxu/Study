package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.rest.message.request.PhysicalLetterInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.PhysicalLetterInitResponseMessage;
/**
 * @descrption : 体检函初始化方法
 * @author : linyongtao
 * @time : 2017-08-04 
 */
@Service
public interface PhysicalLetterService {
	/**
	 * @descrption : 体检函初始化方法
	 * @author : linyongtao
	 * @date: 2017-08-04
	 * **/
	public PhysicalLetterInitResponseMessage selectPhysicalLetter(PhysicalLetterInitRequestMessage physicalLetterInitRequestMessage);
}
