package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProductInfo {

	/**
	 * 险种编码
	 */
	@XmlElement(name="Product_code")
	private String product_code = "";

	/**
	 * 险种名称
	 */
	@XmlElement(name="Product_name")
	private String product_name = "";

	/**
	 * 险种结论
	 */
	@XmlElement(name="Undwrt_result")
	private String undwrt_result = "";

	/**
	 * 险种结论原因
	 */
	@XmlElement(name="Undwrt_result_reason")
	private String undwrt_result_reason = "";

	/**
	 * 险种应交保费
	 */
	@XmlElement(name="Total_prem")
	private String total_prem = "0";

	/**
	 * 总计职业加费金额
	 */
	@XmlElement(name="Total_occu_add_prem")
	private String total_occu_add_prem = "0";

	/**
	 * 总计健康加费金额
	 */
	@XmlElement(name="Total_health_add_prem")
	private String total_health_add_prem = "0";

	/**
	 * 加费期限类型(年/月)
	 */
	@XmlElement(name="Add_extra_trem_period_type")
	private String add_extra_trem_period_type = "0";

	/**
	 * 加费期限
	 */
	@XmlElement(name="Add_extra_trem_period")
	private String add_extra_trem_period = "0";

	/**
	 * 除外责任
	 */
	@XmlElement(name="Exclustion")
	private String exclustion = "";

	/**
	 * 削减保额
	 */
	@XmlElement(name="Cut_face_amount")
	private String cut_face_amount = "0";

	/**
	 * 限制保额
	 */
	@XmlElement(name="Limitd_face_amount")
	private String limitd_face_amount = "0";

	/**
	 * 限制份数
	 */
	@XmlElement(name="Limitd_units")
	private String limitd_units = "0";

	/**
	 * 延期期限单位
	 */
	@XmlElement(name="Postponed_period_type")
	private String postponed_period_type = "";

	/**
	 * 延期期限
	 */
	@XmlElement(name="Postponed_period")
	private String postponed_period = "";
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getUndwrt_result() {
		return undwrt_result;
	}
	public void setUndwrt_result(String undwrt_result) {
		this.undwrt_result = undwrt_result;
	}
	public String getUndwrt_result_reason() {
		return undwrt_result_reason;
	}
	public void setUndwrt_result_reason(String undwrt_result_reason) {
		this.undwrt_result_reason = undwrt_result_reason;
	}
	public String getTotal_prem() {
		return total_prem;
	}
	public void setTotal_prem(String total_prem) {
		this.total_prem = total_prem;
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
