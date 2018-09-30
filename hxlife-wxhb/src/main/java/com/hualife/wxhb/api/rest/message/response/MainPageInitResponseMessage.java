package com.hualife.wxhb.api.rest.message.response;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ClientNoteInfos;
import com.hualife.wxhb.api.rest.message.pojo.ProblemNoteInfos;
import com.hualife.wxhb.api.rest.message.pojo.UndwrtNoteInfo;


/**
 * @description : 业务待处理数据返回的报文
 * @author : linyongtao
 * @time : 2017-08-14
 */
public class MainPageInitResponseMessage {
	/**
	 * appid
	 * **/
	private String appid;
	/**
	 * 客户层函件数组
	 * **/
	private List<ClientNoteInfos> clientNoteInfos;
	/**
	 * 问题件函件数组
	 * **/
	private List<ProblemNoteInfos> problemNoteInfos;
	/**
	 *核保函函件数组 
	 * **/
	private List<UndwrtNoteInfo> undwrtNoteInfo;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public List<ClientNoteInfos> getClientNoteInfos() {
		return clientNoteInfos;
	}
	public void setClientNoteInfos(List<ClientNoteInfos> clientNoteInfos) {
		this.clientNoteInfos = clientNoteInfos;
	}
	public List<ProblemNoteInfos> getProblemNoteInfos() {
		return problemNoteInfos;
	}
	public void setProblemNoteInfos(List<ProblemNoteInfos> problemNoteInfos) {
		this.problemNoteInfos = problemNoteInfos;
	}
	public List<UndwrtNoteInfo> getUndwrtNoteInfo() {
		return undwrtNoteInfo;
	}
	public void setUndwrtNoteInfo(List<UndwrtNoteInfo> undwrtNoteInfo) {
		this.undwrtNoteInfo = undwrtNoteInfo;
	}
	
}
