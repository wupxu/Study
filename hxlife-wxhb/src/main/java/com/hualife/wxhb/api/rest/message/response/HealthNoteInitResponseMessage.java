package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.HealthNoteItem;
import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;

/**
 * @description 健康函初始化返回报文实体类
 * @author wangt
 * @date 2017-08-08
 */
public class HealthNoteInitResponseMessage {
	
	private String agent_name;		//服务专员姓名
	private String agent_phone;		//服务专员电话
	private String client_name;		//客户姓名
	private String other_invest;	//其他核保师的说明
	private String special_desc;	//核保员特别说明
	private String client_age;      //客户性别
	private String client_sex;      //客户年龄
	private List<HealthNoteItem> note_items;		//健康问卷
	private List<ImageInfo> images;				//影像图片
	
	public String getClient_age() {
		return client_age;
	}
	public void setClient_age(String client_age) {
		this.client_age = client_age;
	}
	public String getClient_sex() {
		return client_sex;
	}
	public void setClient_sex(String client_sex) {
		this.client_sex = client_sex;
	}
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
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getOther_invest() {
		return other_invest;
	}
	public void setOther_invest(String other_invest) {
		this.other_invest = other_invest;
	}
	public String getSpecial_desc() {
		return special_desc;
	}
	public void setSpecial_desc(String special_desc) {
		this.special_desc = special_desc;
	}
	public List<HealthNoteItem> getNote_items() {
		return note_items;
	}
	public void setNote_items(List<HealthNoteItem> note_items) {
		this.note_items = note_items;
	}
	public List<ImageInfo> getImages() {
		return images;
	}
	public void setImages(List<ImageInfo> images) {
		this.images = images;
	}
	
	
}
