package com.hualife.wxhb.api.rest.message.response;
import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.NoteInfo;

/**
 * @author zhangweiwei
 * @description 函件状态查询信息返回报文类
 * @date 2017-08-04
 */
public class NoteStatusInfoResponseMessage {
	/**
	 * 函件id
	 */
	private String note_id;
	/**
	 * /客户姓名
	 */
	private String clientName;
	/**
	 * /代理人姓名
	 */
	private String agentName;
	/**
	 * 是否有高额件业务员报告书
	 */
	private Boolean is_agent_report;
	/**
	 * 是否完成高额件业务员报告书
	 */
	private Boolean is_finish_agent_report;
	/**
	 * 函件下发方式
	 */
	private String  sendType;
	/**
	 * 是否已转发给客户
	 */
	private Boolean is_transpond_to_client;
	/**
	 * 函件信息
	 */
	private List<NoteInfo> noteInfos;
	/**
	 * 企业号
	 */
	private String appid;
	/**
	 * branch_name
	 */
	private String branch_name;
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Boolean getIs_agent_report() {
		return is_agent_report;
	}
	public void setIs_agent_report(Boolean is_agent_report) {
		this.is_agent_report = is_agent_report;
	}
	public Boolean getIs_finish_agent_report() {
		return is_finish_agent_report;
	}
	public void setIs_finish_agent_report(Boolean is_finish_agent_report) {
		this.is_finish_agent_report = is_finish_agent_report;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public Boolean getIs_transpond_to_client() {
		return is_transpond_to_client;
	}
	public void setIs_transpond_to_client(Boolean is_transpond_to_client) {
		this.is_transpond_to_client = is_transpond_to_client;
	}
	public List<NoteInfo> getNoteInfos() {
		return noteInfos;
	}
	public void setNoteInfos(List<NoteInfo> noteInfos) {
		this.noteInfos = noteInfos;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	
	
}
