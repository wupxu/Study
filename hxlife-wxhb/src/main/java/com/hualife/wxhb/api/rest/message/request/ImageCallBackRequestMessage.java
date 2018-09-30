package com.hualife.wxhb.api.rest.message.request;

import java.util.ArrayList;
import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageCallBack;

/**  
 * @Description: 影像回调请求参数 message
 * @author zhanglong 
 * @date 2017年9月19日 下午6:02:34  
 */
public class ImageCallBackRequestMessage {
	List<ImageCallBack> imageCallBackList = new ArrayList<ImageCallBack>();

	public List<ImageCallBack> getImageCallBackList() {
		return imageCallBackList;
	}

	public void setImageCallBackList(List<ImageCallBack> imageCallBackList) {
		this.imageCallBackList = imageCallBackList;
	}
	
}
