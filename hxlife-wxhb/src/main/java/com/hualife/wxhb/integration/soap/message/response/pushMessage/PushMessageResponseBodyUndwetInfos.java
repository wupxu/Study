package com.hualife.wxhb.integration.soap.message.response.pushMessage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description 批处理核心返回 核保函
 * @time 创建时间：2017年8月30日
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBodyUndwetInfos {

	@XmlElement(name = "Count")
	private String count = "0";

	@XmlElement(name = "UndwetInfoInfo")
	List<PushMessageResponseBodyUndwetInfoInfo> UndwetInfoInfoList = new ArrayList<>();

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<PushMessageResponseBodyUndwetInfoInfo> getUndwetInfoInfoList() {
		return UndwetInfoInfoList;
	}

	public void setUndwetInfoInfoList(List<PushMessageResponseBodyUndwetInfoInfo> undwetInfoInfoList) {
		UndwetInfoInfoList = undwetInfoInfoList;
	}

}
