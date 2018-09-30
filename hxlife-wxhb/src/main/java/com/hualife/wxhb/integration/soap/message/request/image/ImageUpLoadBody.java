package com.hualife.wxhb.integration.soap.message.request.image;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * @deprecation 影像上载请求报文实体类
 * @author wangt
 * @date  2017-08-20
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageUpLoadBody {
	
	@XmlElement(name = "DOC")
	private List<ImageUpLoadDoc> doc = new ArrayList<ImageUpLoadDoc>();

	public List<ImageUpLoadDoc> getDoc() {
		return doc;
	}

	public void setDoc(List<ImageUpLoadDoc> doc) {
		this.doc = doc;
	}	
	
}
