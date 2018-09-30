package com.hualife.wxhb.api.rest.message.response;
/** 
 * @author 吴培旭 
 * @description 财务函初始化返回报文
 * @time 创建时间：2017年8月5日   
 */

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.domain.dto.FinaItem;

public class FinaOccuInfoResponseMessage {

	private List<FinaItem> Fina_questionnaire;// 问卷集合

	private List<ImageInfo> images;// 影像集合

	private String agentName;// 服务专员姓名

	private String agentPhone;// 服务专员电话

	private String clientName;// 客户姓名

	private String specialDesc;// 核保员特别说明

	private String insuredIdType;// 被保人证件类型

	private String insuredIdNo;// 被保人证件号码

	private String insuredPhone;// 被保人电话

	private String agentNo;// 处理函件的代理人编码

	public String getInsuredIdType() {
		return insuredIdType;
	}

	public void setInsuredIdType(String insuredIdType) {
		this.insuredIdType = insuredIdType;
	}

	public String getInsuredIdNo() {
		return insuredIdNo;
	}

	public void setInsuredIdNo(String insuredIdNo) {
		this.insuredIdNo = insuredIdNo;
	}

	public String getInsuredPhone() {
		return insuredPhone;
	}

	public void setInsuredPhone(String insuredPhone) {
		this.insuredPhone = insuredPhone;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public List<FinaItem> getFina_questionnaire() {
		return Fina_questionnaire;
	}

	public void setFina_questionnaire(List<FinaItem> fina_questionnaire) {
		Fina_questionnaire = fina_questionnaire;
	}

	

	public List<ImageInfo> getImages() {
		return images;
	}

	public void setImages(List<ImageInfo> images) {
		this.images = images;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSpecialDesc() {
		return specialDesc;
	}

	public void setSpecialDesc(String specialDesc) {
		this.specialDesc = specialDesc;
	}

}
