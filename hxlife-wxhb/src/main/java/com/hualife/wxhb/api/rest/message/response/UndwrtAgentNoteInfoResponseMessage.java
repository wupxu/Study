package com.hualife.wxhb.api.rest.message.response;	

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.api.rest.message.pojo.UndwrtNoteProduct;
/**
 * @description 核保函(代理人/客户)初始化返回报文实体类
 * @author wangt
 * @date 2017-08-09
 */
public class UndwrtAgentNoteInfoResponseMessage {
	
	private String agent_phone;			//代理人电话
	private String application_name;	//投保人姓名
	private String insured_name;		//被保人姓名
	private String applicant_phone;		//投保人手机号
	private String phone_success;		//短信是否验证成功标识
	private List<UndwrtNoteProduct> products;	//险种信息列表
	private List<ImageInfo> images;				//签名图片
	private String apply_bar_code;		//投保单号
	private String pay_prem;			//标准保费：险种应交（total_prem）的和
	private String sum_prem;			//应交保费合计:标准保费+加费
	private String plus_prem;			//加费：险种结论费用的和
	private String agent_no;			//代理人编码
	private String agent_name;			//代理人姓名
	private String branch_name;			//营业机构
	private String company_address;		//公司地址
	private String company_postal_code;	//邮编
	private String insured_age;			//被保人年龄
	private String created_date;		//创建时间
	
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public List<ImageInfo> getImages() {
		return images;
	}
	public void setImages(List<ImageInfo> images) {
		this.images = images;
	}
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
	public String getApplicant_phone() {
		return applicant_phone;
	}
	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
	}
	public String getPhone_success() {
		return phone_success;
	}
	public void setPhone_success(String phone_success) {
		this.phone_success = phone_success;
	}
	public List<UndwrtNoteProduct> getProducts() {
		return products;
	}
	public void setProducts(List<UndwrtNoteProduct> products) {
		this.products = products;
	}
	public String getApply_bar_code() {
		return apply_bar_code;
	}
	public void setApply_bar_code(String apply_bar_code) {
		this.apply_bar_code = apply_bar_code;
	}
	
	public String getPay_prem() {
		return pay_prem;
	}
	public void setPay_prem(String pay_prem) {
		this.pay_prem = pay_prem;
	}
	public String getSum_prem() {
		return sum_prem;
	}
	public void setSum_prem(String sum_prem) {
		this.sum_prem = sum_prem;
	}
	public String getPlus_prem() {
		return plus_prem;
	}
	public void setPlus_prem(String plus_prem) {
		this.plus_prem = plus_prem;
	}
	public String getAgent_no() {
		return agent_no;
	}
	public void setAgent_no(String agent_no) {
		this.agent_no = agent_no;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getCompany_postal_code() {
		return company_postal_code;
	}
	public void setCompany_postal_code(String company_postal_code) {
		this.company_postal_code = company_postal_code;
	}
	public String getInsured_age() {
		return insured_age;
	}
	public void setInsured_age(String insured_age) {
		this.insured_age = insured_age;
	}
	public String getAgent_phone() {
		return agent_phone;
	}
	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}
	
	
}
