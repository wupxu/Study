package com.hualife.wxhb.domain.dto;

/**
 * @deprecation t_task_push_image表实体类
 * @author wangt
 * @date  2017-08-20
 */
public class ImageTaskTable {
	
	
	private String note_id;			//函件id
	private String note_type;		//影像类型
	private String image_status;	//影像状态
	private String push_date;		//推送日期
	private String created_date;	//创建日期
	
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}	
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	public String getImage_status() {
		return image_status;
	}
	public void setImage_status(String image_status) {
		this.image_status = image_status;
	}
	public String getPush_date() {
		return push_date;
	}
	public void setPush_date(String push_date) {
		this.push_date = push_date;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
}
