package com.hualife.wxhb.api.rest.message.pojo;
/**
 * @description 核保函(代理人/客户)初始化返回报文-险种列表
 * @author wangt
 * @date 2017-08-09
 */
public class UndwrtNoteProduct {
	
	private String product_name;			//险种名称
	private String undwrt_result_reason;	//险种结论原因	
	private String undwrt_result;				//险种结论代码
	private String total_prem;					//险种应交保费
	private String total_occu_add_prem;			//总计职业加费金额	
	private String total_health_add_prem;		//总计健康加费金额
	private String add_extra_trem_period_type;	//加费期限类型(年/月)
	private String add_extra_trem_period;		//加费期限
	private String exclustion;					//除外责任
	private String cut_face_amount;				//削减保额
	private String limitd_face_amount;			//限制保额
	private String limitd_units;				//限制份数
	private String postponed_period_type;		//延期期限单位
	private String postponed_period;			//延期期限
	
	
	public String getTotal_prem() {
		return total_prem;
	}
	public void setTotal_prem(String total_prem) {
		this.total_prem = total_prem;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getUndwrt_result_reason() {
		return undwrt_result_reason;
	}
	public void setUndwrt_result_reason(String undwrt_result_reason) {
		this.undwrt_result_reason = undwrt_result_reason;
	}
	public String getUndwrt_result() {
		return undwrt_result;
	}
	public void setUndwrt_result(String undwrt_result) {
		this.undwrt_result = undwrt_result;
	}
	public String getTotal_occu_add_prem() {
		return total_occu_add_prem;
	}
	public void setTotal_occu_add_prem(String total_occu_add_prem) {
		this.total_occu_add_prem = total_occu_add_prem;
	}
	public String getTotal_health_add_prem() {
		return total_health_add_prem;
	}
	public void setTotal_health_add_prem(String total_health_add_prem) {
		this.total_health_add_prem = total_health_add_prem;
	}
	public String getAdd_extra_trem_period_type() {
		return add_extra_trem_period_type;
	}
	public void setAdd_extra_trem_period_type(String add_extra_trem_period_type) {
		this.add_extra_trem_period_type = add_extra_trem_period_type;
	}
	public String getAdd_extra_trem_period() {
		return add_extra_trem_period;
	}
	public void setAdd_extra_trem_period(String add_extra_trem_period) {
		this.add_extra_trem_period = add_extra_trem_period;
	}
	public String getExclustion() {
		return exclustion;
	}
	public void setExclustion(String exclustion) {
		this.exclustion = exclustion;
	}
	public String getCut_face_amount() {
		return cut_face_amount;
	}
	public void setCut_face_amount(String cut_face_amount) {
		this.cut_face_amount = cut_face_amount;
	}
	public String getLimitd_face_amount() {
		return limitd_face_amount;
	}
	public void setLimitd_face_amount(String limitd_face_amount) {
		this.limitd_face_amount = limitd_face_amount;
	}
	public String getLimitd_units() {
		return limitd_units;
	}
	public void setLimitd_units(String limitd_units) {
		this.limitd_units = limitd_units;
	}
	public String getPostponed_period_type() {
		return postponed_period_type;
	}
	public void setPostponed_period_type(String postponed_period_type) {
		this.postponed_period_type = postponed_period_type;
	}
	public String getPostponed_period() {
		return postponed_period;
	}
	public void setPostponed_period(String postponed_period) {
		this.postponed_period = postponed_period;
	}	
	 
}	
