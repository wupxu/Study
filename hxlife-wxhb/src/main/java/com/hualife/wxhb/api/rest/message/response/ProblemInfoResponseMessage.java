package com.hualife.wxhb.api.rest.message.response;
/** 
 * @author 吴培旭 
 * @description 问题件列表初始化返回报文
 * @time 创建时间：2017年8月8日
 * agentName:代理人姓名
 * agentPhone:代理人电话
 * applicantPhone:投保人手机号(此手机号用于验证)
 * applicationName:投保人姓名
 * isIdentityProve:投保人是否进行了身份验证
 * image:影像集合
 * problemObjects:问题集合(外层集合加内层集合)
 */

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ImageInfo;
import com.hualife.wxhb.domain.dto.ProblemObj;

public class ProblemInfoResponseMessage {

	private String agentName;// 代理人姓名

	private String agentPhone;// 代理人电话

	private String applicantPhone;// 投保人手机号(此手机号用于验证)

	private String applicationName;// 投保人姓名

	private String insuredName;// 被保人姓名

	private String phoneSuccess;// 投保人是否进行了身份验证

	private List<ImageInfo> image;// 影像集合

	private List<ProblemObj> problemObjects;// 问题相对应集合

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

	public String getApplicantPhone() {
		return applicantPhone;
	}

	public void setApplicantPhone(String applicantPhone) {
		this.applicantPhone = applicantPhone;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getPhoneSuccess() {
		return phoneSuccess;
	}

	public void setPhoneSuccess(String phoneSuccess) {
		this.phoneSuccess = phoneSuccess;
	}


	public List<ImageInfo> getImage() {
		return image;
	}

	public void setImage(List<ImageInfo> image) {
		this.image = image;
	}

	public List<ProblemObj> getProblemObjects() {
		return problemObjects;
	}

	public void setProblemObjects(List<ProblemObj> problemObjects) {
		this.problemObjects = problemObjects;
	}

}
