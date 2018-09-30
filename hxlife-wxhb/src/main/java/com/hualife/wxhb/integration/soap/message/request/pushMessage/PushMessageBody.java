package com.hualife.wxhb.integration.soap.message.request.pushMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 吴培旭
 * @description  推送函件信息到核心系统所需输入报文
 * @time 创建时间：2017年8月22日
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PushMessageBody {
	
	@XmlElement(name = "Motions")
	private PushMessageBodyMotions motions = new PushMessageBodyMotions();
	
	@XmlElement(name = "UndwrtInfos")
	private PushMessageBodyUndwetInfos UndwetInfos = new PushMessageBodyUndwetInfos();
	
	@XmlElement(name = "SurivivalInfos")
	private PushMessageBodySurivivalInfos surivivalInfos = new PushMessageBodySurivivalInfos();
	
	@XmlElement(name = "ProblemInfos")
	private PushMessageBodyProblemInfos problemInfos = new PushMessageBodyProblemInfos();

	public PushMessageBodyMotions getMotions() {
		return motions;
	}

	public void setMotions(PushMessageBodyMotions motions) {
		this.motions = motions;
	}


	public PushMessageBodyUndwetInfos getUndwetInfos() {
		return UndwetInfos;
	}

	public void setUndwetInfos(PushMessageBodyUndwetInfos undwetInfos) {
		UndwetInfos = undwetInfos;
	}

	public PushMessageBodySurivivalInfos getSurivivalInfos() {
		return surivivalInfos;
	}

	public void setSurivivalInfos(PushMessageBodySurivivalInfos surivivalInfos) {
		this.surivivalInfos = surivivalInfos;
	}

	public PushMessageBodyProblemInfos getProblemInfos() {
		return problemInfos;
	}

	public void setProblemInfos(PushMessageBodyProblemInfos problemInfos) {
		this.problemInfos = problemInfos;
	}

}
