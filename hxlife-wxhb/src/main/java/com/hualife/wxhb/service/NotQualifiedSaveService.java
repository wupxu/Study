package com.hualife.wxhb.service;

import org.springframework.stereotype.Service; 

import com.hualife.wxhb.api.rest.message.request.NotQualifiedSaveRequestMessage;
/**
 * @descrption : 次品单提交service
 * @author : linyongtao
 * @date : 2017-08-22 
 */
@Service
public interface NotQualifiedSaveService {
	/**
	 * @descrption : 次品单提交方法
	 * @author : linyongtao
	 * @date : 2017-08-22 
	 * **/
	public void notQualifiedSave(NotQualifiedSaveRequestMessage notQualifiedSaveRequestMessage);
}
