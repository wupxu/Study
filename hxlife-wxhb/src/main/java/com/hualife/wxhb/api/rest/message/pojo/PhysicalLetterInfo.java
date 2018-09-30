package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @description: 体检函初始化信息
 * @author : linyongtao
 * @date :2017.08.26
 */
public class PhysicalLetterInfo {
	/**
	 * 代理人姓名
	 * **/
	private String agent_name;
	/**
	 * 代理人电话
	 * **/
	private String agent_phone;
	/**
	 * 是否有免体检资格
	 * **/
	private String is_self_health;
	private String physical_invest_sort;
	private String image_index;
	private String image_url;
	private String image_type;
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
	public String getIs_self_health() {
		return is_self_health;
	}
	public void setIs_self_health(String is_self_health) {
		this.is_self_health = is_self_health;
	}
	public String getPhysical_invest_sort() {
		return physical_invest_sort;
	}
	public void setPhysical_invest_sort(String physical_invest_sort) {
		this.physical_invest_sort = physical_invest_sort;
	}
	public String getImage_index() {
		return image_index;
	}
	public void setImage_index(String image_index) {
		this.image_index = image_index;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}

	
}
