package com.hualife.wxhb.domain.dto;

/**
 * @deprecation t_note_image表实体类
 * @author wangt
 * @date  2017-08-20
 */
public class ImageTable {
	
	
	private String image_id;		//影像序号
	private String image_type;		//影像类型
	private String image_name;		//图像名称
	private String image_file;		//影像文件夹
	private String created_date;	//更新时间
	//private String localhost_url;	//本地存放路径
	//private String image_status;
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public String getImage_file() {
		return image_file;
	}
	public void setImage_file(String image_file) {
		this.image_file = image_file;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
}
