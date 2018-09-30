package com.hualife.wxhb.integration.soap.message.response.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/** 
 * @author 吴培旭 
 * @description 推送函件信息到核心，核心返回报文
 * @time 创建时间：2017年8月30日   
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageResponseBody {

	@XmlElement(name = "Motions")
	private  PushMessageResponseBodyMotions motions = new PushMessageResponseBodyMotions();
	
	@XmlElement(name = "UndwetInfos")
	private PushMessageResponseBodyUndwetInfos UndwetInfos = new PushMessageResponseBodyUndwetInfos();
	
	@XmlElement(name = "SurivivalInfos")
	private PushMessageResponseBodySurivivalInfos surivivalInfos = new PushMessageResponseBodySurivivalInfos();
	
	@XmlElement(name = "ProblemInfos")
	private PushMessageResponseBodyProblemInfos problemInfos = new PushMessageResponseBodyProblemInfos();

	public PushMessageResponseBodyMotions getMotions() {
		return motions;
	}

	public void setMotions(PushMessageResponseBodyMotions motions) {
		this.motions = motions;
	}

	public PushMessageResponseBodyUndwetInfos getUndwetInfos() {
		return UndwetInfos;
	}

	public void setUndwetInfos(PushMessageResponseBodyUndwetInfos undwetInfos) {
		UndwetInfos = undwetInfos;
	}

	public PushMessageResponseBodySurivivalInfos getSurivivalInfos() {
		return surivivalInfos;
	}

	public void setSurivivalInfos(PushMessageResponseBodySurivivalInfos surivivalInfos) {
		this.surivivalInfos = surivivalInfos;
	}

	public PushMessageResponseBodyProblemInfos getProblemInfos() {
		return problemInfos;
	}

	public void setProblemInfos(PushMessageResponseBodyProblemInfos problemInfos) {
		this.problemInfos = problemInfos;
	}

	
}
