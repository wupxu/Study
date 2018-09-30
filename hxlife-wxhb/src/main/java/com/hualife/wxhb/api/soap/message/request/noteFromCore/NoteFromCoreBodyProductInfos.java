package com.hualife.wxhb.api.soap.message.request.noteFromCore;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class NoteFromCoreBodyProductInfos {

	/**
	 * 条数
	 */
	@XmlElement(name="Count")
	private String Count = "0";
	/**
	 * 产品信息集合
	 */
	@XmlElement(name="ProductInfo")
	private List<NoteFromCoreBodyProductInfo> ProductInfo = new ArrayList<NoteFromCoreBodyProductInfo>();
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public List<NoteFromCoreBodyProductInfo> getProductInfo() {
		return ProductInfo;
	}
	public void setProductInfo(List<NoteFromCoreBodyProductInfo> productInfo) {
		ProductInfo = productInfo;
	}
	
}
