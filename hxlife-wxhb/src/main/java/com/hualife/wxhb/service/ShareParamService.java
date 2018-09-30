package com.hualife.wxhb.service;

import com.hualife.wxhb.api.rest.message.response.ShareParamInfoRequestMessage;
import com.hualife.wxhb.api.rest.message.response.ShareParamInfoResponseMessage;

/**
 * @descrption :　分享配置参数接口实现接口类
 * @author : zhanyilong
 * @date : 2017-08-31
 */
public interface ShareParamService {
	
   /**
    * 分享配置参数接口实现方法
    * **/	
   public ShareParamInfoResponseMessage shareparam(ShareParamInfoRequestMessage shareParamInfoRequestMessage) ;
   
}
