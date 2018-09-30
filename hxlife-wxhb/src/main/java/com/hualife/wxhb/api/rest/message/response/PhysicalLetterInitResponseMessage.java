package com.hualife.wxhb.api.rest.message.response;

import java.util.List;  

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.PhysicalItem;

/**
 * @description : 体检函初始化返回报文体
 * @author : linyongtao
 * @date : 2017.08.14
 */
public class PhysicalLetterInitResponseMessage {
	/**
	 *代理人姓名
	 * **/
	private String agent_name;
	/**
	 *代理人电话 
	 * **/
	private String agent_phone;
	/**
	 *体检项目
	 * **/
	private List<PhysicalItem> physicalContent;
	/**
	 *体检函客户上传的影像资料	 
	 * **/
	private List<ImageInfo> imageInfo;
	/**
	 *客户是否可以免陪检 
	 * **/
	private String is_self_health;

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_phone() {
		return agent_phone;
	}

	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}

	public List<PhysicalItem> getPhysicalContent() {
		return physicalContent;
	}

	public void setPhysicalContent(List<PhysicalItem> physicalContent) {
		this.physicalContent = physicalContent;
	}

	public List<ImageInfo> getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(List<ImageInfo> imageInfo) {
		this.imageInfo = imageInfo;
	}

	public String getIs_self_health() {
		return is_self_health;
	}

	public void setIs_self_health(String is_self_health) {
		this.is_self_health = is_self_health;
	}
		
}
