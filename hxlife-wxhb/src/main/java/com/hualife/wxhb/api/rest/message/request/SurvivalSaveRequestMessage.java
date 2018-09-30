package com.hualife.wxhb.api.rest.message.request;

/**
 * @author 吴培旭
 * @description 契调报告保存请求参数
 * @time 创建时间：2017年8月5日 
 */
public class SurvivalSaveRequestMessage {

	private String survival_note_id;// 契调函id

	private String survival_note_report;// 契调报告

	public String getSurvival_note_id() {
		return survival_note_id;
	}

	public void setSurvival_note_id(String survival_note_id) {
		this.survival_note_id = survival_note_id;
	}

	public String getSurvival_note_report() {
		return survival_note_report;
	}

	public void setSurvival_note_report(String survival_note_report) {
		this.survival_note_report = survival_note_report;
	}


}
