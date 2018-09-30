package com.hualife.wxhb.domain.dto;

/**
 * @descrption : 用于查询每一个代理人下的函件id
 * @author : linyongtao
 * @time : 2017-08-14
 *
 */
public class SelectId {
	/**
	 *函件id 
	 * **/
	private String note_id;
	/**
	 * 问题件函件id
	 * **/
	private String problem_note_id;
	/**
	 * 核保函函件id
	 * **/
	private String note_undwrt_id;
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getProblem_note_id() {
		return problem_note_id;
	}
	public void setProblem_note_id(String problem_note_id) {
		this.problem_note_id = problem_note_id;
	}
	public String getNote_undwrt_id() {
		return note_undwrt_id;
	}
	public void setNote_undwrt_id(String note_undwrt_id) {
		this.note_undwrt_id = note_undwrt_id;
	}
	
	

}
