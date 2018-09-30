package com.hualife.wxhb.service;

import org.springframework.stereotype.Service;

import com.hualife.wxhb.api.rest.message.request.NotQualifiedImagesInitRequestMessage;
import com.hualife.wxhb.api.rest.message.response.NotQualifiedImagesInitResponseMessage;
/**
 * @descrption : 次品单-客户上传影像资料返service
 * @author : linyongtao
 * @time :2017-08-21  
 */
@Service
public interface NotQualifiedImagesInitService {
	/**
	 * @descrption : 次品单-客户上传影像资料返service
	 * @author : linyongtao
	 * @time :2017-08-21  
	 * **/
	public NotQualifiedImagesInitResponseMessage notQualifiedInit(NotQualifiedImagesInitRequestMessage  notQualifiedImagesInitRequestMessage);
}
