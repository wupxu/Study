package com.hualife.wxhb.integration.soap.message.request.image;

import javax.xml.bind.annotation.XmlElement;
/**
 * @deprecation 影像删除请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
public class ImageDeleteBody {
	
	@XmlElement(name = "Docs")
	private ImageDeleteDocs docs = new ImageDeleteDocs();

	public ImageDeleteDocs getDocs() {
		return docs;
	}

	public void setDocs(ImageDeleteDocs docs) {
		this.docs = docs;
	}	
	
}
