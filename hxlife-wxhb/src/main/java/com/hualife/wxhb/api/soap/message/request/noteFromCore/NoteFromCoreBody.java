package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBody {
	/**
	 * 核保任务号
	 */
	@XmlElement(name="Taskcode")
	private String Taskcode = "";
	/**
	 * 函件关联号
	 */
	@XmlElement(name="Client_no")
	private String client_no = "";
	/**
	 * 函件关联客户姓名
	 */
	@XmlElement(name="Client_name")
	private String client_name = "";
	/**
	 * 客户年龄
	 */
	@XmlElement(name="Client_age")
	private String client_age = "";
	/**
	 * 客户证件号码
	 */
	@XmlElement(name="Client_idno")
	private String client_idno = "";
	/**
	 * 客户性别
	 */
	@XmlElement(name="Client_sex")
	private String client_sex = "";
	/**
	 * 投保人客户号
	 */
	@XmlElement(name="Applicant_no")
	private String applicant_no = "";
	/**
	 * 投保人姓名
	 */
	@XmlElement(name="Applicant_name")
	private String applicant_name = "";
	/**
	 * 投保人手机号
	 */
	@XmlElement(name="Applicant_phone")
	private String applicant_phone = "";
	/**
	 * 业务员
	 */
	@XmlElement(name="Agent_no")
	private String agent_no = ""; 
	/**
	 * 代理人姓名
	 */
	@XmlElement(name="Agent_name")
	private String agent_name = "";
	/**
	 * 代理人手机号
	 */
	@XmlElement(name="Agent_phone")
	private String agent_phone = "";
	
	/**
	 * 代理人(代理机构)所属渠道
	 */
	@XmlElement(name="Channel_type")
	private String channel_type = "";
	
	/**
	 * 代理人所属机构代码
	 */
	@XmlElement(name="Branch_code")
	private String Branch_code = "";

	/**
	 * 代理人所属机构名称
	 */
	@XmlElement(name="Branch_name")
	private String Branch_name = "";
	
	/**
	 * 保单信息 
	 */
	@XmlElement(name="RelevanceInfos")
	private NoteFromCoreBodyRelevanceInfos RelevanceInfos = new NoteFromCoreBodyRelevanceInfos();
	
	/**
	 * 体检函对象
	 */
	@XmlElement(name="PhysicalInfos")
	private NoteFromCoreBodyPhysicalInfos PhysicalInfos = new NoteFromCoreBodyPhysicalInfos();

	/**
	 * 财务函对象
	 */
	@XmlElement(name="FinaOocuInfos")
	private NoteFromCoreBodyFinaOocuInfos FinaOocuInfos = new NoteFromCoreBodyFinaOocuInfos();

	/**
	 * 健康函对象
	 */
	@XmlElement(name="HealthInfos")
	private NoteFromCoreBodyHealthInfos HealthInfos = new NoteFromCoreBodyHealthInfos();

	/**
	 * 契调函对象
	 */
	@XmlElement(name="SurvivalInfos")
	private NoteFromCoreBodySurvivalInfos SurvivalInfos = new NoteFromCoreBodySurvivalInfos();

	/**
	 * 核保函对象
	 */
	@XmlElement(name="UndwrtInfos")
	private NoteFromCoreBodyUndwrtInfos UndwrtInfos = new NoteFromCoreBodyUndwrtInfos();

	/**
	 * 问题件对象
	 */
	@XmlElement(name="ProblemInfos")
	private NoteFromCoreBodyProblemInfos ProblemInfos = new NoteFromCoreBodyProblemInfos();
	
	public String getTaskcode() {
		return Taskcode;
	}
	public void setTaskcode(String taskcode) {
		Taskcode = taskcode;
	}
	public String getClient_no() {
		return client_no;
	}
	public void setClient_no(String client_no) {
		this.client_no = client_no;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getClient_age() {
		return client_age;
	}
	public void setClient_age(String client_age) {
		this.client_age = client_age;
	}
	public String getClient_idno() {
		return client_idno;
	}
	public void setClient_idno(String client_idno) {
		this.client_idno = client_idno;
	}
	public String getClient_sex() {
		return client_sex;
	}
	public void setClient_sex(String client_sex) {
		this.client_sex = client_sex;
	}
	public String getApplicant_no() {
		return applicant_no;
	}
	public void setApplicant_no(String applicant_no) {
		this.applicant_no = applicant_no;
	}
	public String getApplicant_name() {
		return applicant_name;
	}
	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}
	public String getApplicant_phone() {
		return applicant_phone;
	}
	public void setApplicant_phone(String applicant_phone) {
		this.applicant_phone = applicant_phone;
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
	public String getAgent_phone() {
		return agent_phone;
	}
	public void setAgent_phone(String agent_phone) {
		this.agent_phone = agent_phone;
	}
	public NoteFromCoreBodyRelevanceInfos getRelevanceInfos() {
		return RelevanceInfos;
	}
	public void setRelevanceInfos(NoteFromCoreBodyRelevanceInfos relevanceInfos) {
		RelevanceInfos = relevanceInfos;
	}
	
	public NoteFromCoreBodyPhysicalInfos getPhysicalInfos() {
		return PhysicalInfos;
	}
	public void setPhysicalInfos(NoteFromCoreBodyPhysicalInfos physicalInfos) {
		PhysicalInfos = physicalInfos;
	}
	public NoteFromCoreBodyFinaOocuInfos getFinaOocuInfos() {
		return FinaOocuInfos;
	}
	public void setFinaOocuInfos(NoteFromCoreBodyFinaOocuInfos finaOocuInfos) {
		FinaOocuInfos = finaOocuInfos;
	}
	public NoteFromCoreBodyHealthInfos getHealthInfos() {
		return HealthInfos;
	}
	public void setHealthInfos(NoteFromCoreBodyHealthInfos healthInfos) {
		HealthInfos = healthInfos;
	}
	
	public NoteFromCoreBodySurvivalInfos getSurvivalInfos() {
		return SurvivalInfos;
	}
	public void setSurvivalInfos(NoteFromCoreBodySurvivalInfos survivalInfos) {
		SurvivalInfos = survivalInfos;
	}
	public NoteFromCoreBodyUndwrtInfos getUndwrtInfos() {
		return UndwrtInfos;
	}
	public void setUndwrtInfos(NoteFromCoreBodyUndwrtInfos undwrtInfos) {
		UndwrtInfos = undwrtInfos;
	}
	public NoteFromCoreBodyProblemInfos getProblemInfos() {
		return ProblemInfos;
	}
	public void setProblemInfos(NoteFromCoreBodyProblemInfos problemInfos) {
		ProblemInfos = problemInfos;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getBranch_code() {
		return Branch_code;
	}
	public void setBranch_code(String branch_code) {
		Branch_code = branch_code;
	}
	public String getBranch_name() {
		return Branch_name;
	}
	public void setBranch_name(String branch_name) {
		Branch_name = branch_name;
	}
	
}
