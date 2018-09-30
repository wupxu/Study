package com.hualife.wxhb.api.rest.message.request;

import java.util.List;

import com.hualife.wxhb.api.rest.message.pojo.ProblemContent; 


/**
 * 
 * @description : 公共问卷,报告请求参数
 * @author : linyongtao
 * @date : 2017.08.10
 */
public class QuestionNaireSaveResquestMessage {
	/**
	 * 函件id
	 * **/
	private String client_note_id;
	/**
	 * 函件类型
	 * **/
	private String note_type;
	/**
	 * 问卷子id
	 * **/
	private String note_item_id;
	/**
	 * 回答结果
	 * **/
	private List<ProblemContent> problemContent;

	public String getClient_note_id() {
		return client_note_id;
	}
	public void setClient_note_id(String client_note_id) {
		this.client_note_id = client_note_id;
	}
	public String getNote_type() {
		return note_type;
	}
	public void setNote_type(String note_type) {
		this.note_type = note_type;
	}
	public String getNote_item_id() {
		return note_item_id;
	}
	public void setNote_item_id(String note_item_id) {
		this.note_item_id = note_item_id;
	}
	public List<ProblemContent> getProblemContent() {
		return problemContent;
	}
	public void setProblemContent(List<ProblemContent> problemContent) {
		this.problemContent = problemContent;
	}


			
}
	
	
