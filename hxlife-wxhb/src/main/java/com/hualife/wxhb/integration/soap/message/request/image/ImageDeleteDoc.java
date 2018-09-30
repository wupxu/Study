package com.hualife.wxhb.integration.soap.message.request.image;

import javax.xml.bind.annotation.XmlElement;
/**
 * @deprecation 影像删除请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
public class ImageDeleteDoc {
	
	@XmlElement(name = "DocCode")
	private String docCode = "";		//单证号码
	
	@XmlElement(name = "DocId")
	private String docId = "";			//单证ID

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}	
	
}
