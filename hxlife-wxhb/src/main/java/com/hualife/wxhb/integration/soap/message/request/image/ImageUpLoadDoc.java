package com.hualife.wxhb.integration.soap.message.request.image;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * @deprecation 影像上载请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageUpLoadDoc {
	/**
	 * 回调地址
	 */
	@XmlElement(name = "RETURNURL")
	private String returnurl = "";
	/**
	 * 单证号码
	 */
	@XmlElement(name = "DOCCODE")
	private String doccode = "";		
	/**
	 * 组号
	 */
	@XmlElement(name = "GROUPNO")
	private String groupno = "";		//组号
	/**
	 * 渠道
	 */
	@XmlElement(name = "CHANNEL")
	private String channel = "";		//渠道
	/**
	 * 业务类型
	 */
	@XmlElement(name = "BUSSTYPE")
	private String busstype = "";		
	/**
	 * 单证类型
	 */
	@XmlElement(name = "SUBTYPE")
	private String subtype = "";
	/**
	 * 总页数
	 */
	@XmlElement(name = "NUMPAGES")
	private String numpages = "";		
	/**
	 * 扫描机构
	 */
	@XmlElement(name = "MANAGECOM")
	private String managecom = "";		
	/**
	 * 扫描号
	 */
	@XmlElement(name = "SCANNO")
	private String scanno = "";			
	/**
	 * 扫描人员
	 */
	@XmlElement(name = "SCANOPERATOR")
	private String scanoperator = "";	
	/**
	 * 扫描时间
	 */
	@XmlElement(name = "SCANDATE")
	private String scandate = "";	
	/**
	 * 单证ID唯一标识
	 */
	@XmlElement(name = "DOCID")
	private String docid = "";		
	
	@XmlElement(name = "PAGE")
	private List<ImageUpLoadPage> page = new ArrayList<ImageUpLoadPage>();	

	public String getDoccode() {
		return doccode;
	}

	public void setDoccode(String doccode) {
		this.doccode = doccode;
	}

	public String getGroupno() {
		return groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getReturnurl() {
		return returnurl;
	}

	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

	public String getBusstype() {
		return busstype;
	}

	public void setBusstype(String busstype) {
		this.busstype = busstype;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getNumpages() {
		return numpages;
	}

	public void setNumpages(String numpages) {
		this.numpages = numpages;
	}

	public String getManagecom() {
		return managecom;
	}

	public void setManagecom(String managecom) {
		this.managecom = managecom;
	}

	public String getScanno() {
		return scanno;
	}

	public void setScanno(String scanno) {
		this.scanno = scanno;
	}

	public String getScanoperator() {
		return scanoperator;
	}

	public void setScanoperator(String scanoperator) {
		this.scanoperator = scanoperator;
	}

	public String getScandate() {
		return scandate;
	}

	public void setScandate(String scandate) {
		this.scandate = scandate;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public List<ImageUpLoadPage> getPage() {
		return page;
	}

	public void setPage(List<ImageUpLoadPage> page) {
		this.page = page;
	} 
	
	
}
