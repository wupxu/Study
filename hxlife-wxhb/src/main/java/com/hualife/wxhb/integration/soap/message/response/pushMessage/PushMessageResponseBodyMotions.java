package com.hualife.wxhb.integration.soap.message.response.pushMessage;
/** 
 * @author 吴培旭 
 * @description 其他类函件
 * @time 创建时间：2017年8月30日   
 */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBodyMotions {

	@XmlElement(name = "Count")
	private String count = "0";
	
	@XmlElement(name = "Motion")
	private List<PushMessageResponseBodyMotion> motionList = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<PushMessageResponseBodyMotion> getMotionList() {
		return motionList;
	}

	public void setMotionList(List<PushMessageResponseBodyMotion> motionList) {
		this.motionList = motionList;
	}
	
	
	
}
