package com.hualife.wxhb.api.rest.message.pojo;

import java.util.ArrayList;
import java.util.List;

/**  
 * @Description: 影像回调请求参数
 * @author zhanglong 
 * @date 2017年9月19日 下午7:27:47  
 */
public class ImageCallBack {
	private String docid;
	private String print_no;
	private String result_code;
	private String result_message;
	private String retDetailCode;
	private String retDetailMessage;
	private List<Pages> pages = new ArrayList<Pages>();
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public String getPrint_no() {
		return print_no;
	}
	public void setPrint_no(String print_no) {
		this.print_no = print_no;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_message() {
		return result_message;
	}
	public void setResult_message(String result_message) {
		this.result_message = result_message;
	}
	public String getRetDetailCode() {
		return retDetailCode;
	}
	public void setRetDetailCode(String retDetailCode) {
		this.retDetailCode = retDetailCode;
	}
	public String getRetDetailMessage() {
		return retDetailMessage;
	}
	public void setRetDetailMessage(String retDetailMessage) {
		this.retDetailMessage = retDetailMessage;
	}
	public List<Pages> getPages() {
		return pages;
	}
	public void setPages(List<Pages> pages) {
		this.pages = pages;
	}
}
