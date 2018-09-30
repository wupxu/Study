package com.hualife.wxhb.integration.soap.message.request.image;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
/**
 * @deprecation 影像删除请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
public class ImageDeleteDocs {

	@XmlElement(name = "Count")
	private String count = "";		//Doc标签个数
	
	@XmlElement(name = "Doc")
	private List<ImageDeleteDoc> doc = new ArrayList<ImageDeleteDoc>();
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<ImageDeleteDoc> getDoc() {
		return doc;
	}
	public void setDoc(List<ImageDeleteDoc> doc) {
		this.doc = doc;
	}	
	
}
