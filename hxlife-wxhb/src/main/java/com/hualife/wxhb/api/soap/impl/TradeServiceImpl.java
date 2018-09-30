package com.hualife.wxhb.api.soap.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.wxhb.api.soap.TradeService;
import com.hualife.wxhb.api.soap.message.request.NoteSecondPush.NoteSecondPushRequestMessage;
import com.hualife.wxhb.api.soap.message.request.common.RequestHead;
import com.hualife.wxhb.api.soap.message.request.common.RequestMsg;
import com.hualife.wxhb.api.soap.message.request.noteFinishDeal.NoteFinishDealRequestMessage;
import com.hualife.wxhb.api.soap.message.request.noteFromCore.NoteFromCoreRequestMessage;
import com.hualife.wxhb.api.soap.message.request.noteNotQualified.NoteNotQualifiedRequestMessage;
import com.hualife.wxhb.api.soap.message.response.common.CommonResponseMessage;
import com.hualife.wxhb.api.soap.message.response.common.ResponseBody;
import com.hualife.wxhb.api.soap.message.response.common.ResponseHead;
import com.hualife.wxhb.api.soap.message.response.common.ResponseMsg;
import com.hualife.wxhb.api.soap.message.response.common.ResponseStatus;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.service.NoteFinishDealService;
import com.hualife.wxhb.service.NoteFromCoreService;
import com.hualife.wxhb.service.NoteNotQualifiedService;
import com.hualife.wxhb.service.NoteSecondPushService;
/**
 * @author "张龙"
 * @Description 对外曝露交易接口 实现类 改类不能进行逻辑处理，只能作为接口实现，如果需要进行逻辑处理需要建立对应处理对象
 * @time 2017年8月14日 上午9:27:13
 */
@WebService(name = "tradeService", targetNamespace = "http://wxhb.hxlife.com/soap-api", endpointInterface = "com.hualife.wxhb.api.soap.TradeService")
public class TradeServiceImpl implements TradeService {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired 
    private NoteFinishDealService noteFinishDealService;		
	
	@Autowired
	private NoteFromCoreService noteFromCoreService;
	
	@Autowired
	private NoteNotQualifiedService noteNotQualifiedService;
	
	@Autowired
	private NoteSecondPushService noteSecondPushService;
	
	@Override
	public String noteFromCore(String Reqxml) {
		NoteFromCoreRequestMessage noteFromCoreRequestMessage = new NoteFromCoreRequestMessage();
		CommonResponseMessage commonHeadResponseMessage = new CommonResponseMessage();	
		boolean flag = true;
		String message = "";
		noteFromCoreRequestMessage = XmlMapper.fromXml(Reqxml, NoteFromCoreRequestMessage.class);
		try{
			noteFromCoreService.dealDate(noteFromCoreRequestMessage);
		}catch(Exception e){
			flag = false;
			message = e.getMessage();
		}finally{
			commonHeadResponseMessage = clientSend(flag,message,noteFromCoreRequestMessage.getHead());
		}
		return XmlMapper.toXml(commonHeadResponseMessage);
	}
	/**
	 * @author yangpeixin
	 * @Descriptio 次品单
	 * @time 2017年8月25日 	
	 *  */
	@Override
	public String noteNotQualified(String Reqxml) {
		NoteNotQualifiedRequestMessage noteNotQualifiedRequestMessage = XmlMapper.fromXml(Reqxml, NoteNotQualifiedRequestMessage.class);
		
		CommonResponseMessage commonHeadResponseMessage = new CommonResponseMessage();	
		boolean flag = true;
		String message = "";
		try{
			noteNotQualifiedService.saveNoteNotQualified(noteNotQualifiedRequestMessage);
		}catch(Exception e){
			flag = false;
			message = e.getMessage();
		}finally{
			commonHeadResponseMessage = clientSend(flag,message,noteNotQualifiedRequestMessage.getHead());
		}
		return XmlMapper.toXml(commonHeadResponseMessage);
	}
	/**
	 * @author yangpeixin
	 * @Descriptio 重新下发
	 * @time 2017年8月25日 	
	 *  */
	@Override
	public String noteSecondPush(String Reqxml) {
		NoteSecondPushRequestMessage noteSecondPushRequestMessage = XmlMapper.fromXml(Reqxml, NoteSecondPushRequestMessage.class);
		CommonResponseMessage commonHeadResponseMessage = new CommonResponseMessage();
		boolean flag = true;
		String message = "";
		try{
			noteSecondPushService.saveNoteSecondPush(noteSecondPushRequestMessage);
		}catch(Exception e){
			flag = false;
			message = e.getMessage();
		}finally{
			commonHeadResponseMessage = clientSend(flag,message,noteSecondPushRequestMessage.getHead());
		}
		
		return XmlMapper.toXml(commonHeadResponseMessage);
	}
	/**
	 * 回销信息推送入口
	 */
	@Override
	public String noteFinishDeal(String Reqxml) {
		NoteFinishDealRequestMessage noteFinishDealRequestMessage = XmlMapper.fromXml(Reqxml, NoteFinishDealRequestMessage.class);
		CommonResponseMessage commonHeadResponseMessage = new CommonResponseMessage();
		boolean flag = true;
		String message = "";
		try{
			noteFinishDealService.noteFinishDeal(noteFinishDealRequestMessage);
		}catch(Exception e){
			flag = false;
			message = e.getMessage();
		}finally{
			commonHeadResponseMessage = clientSend(flag,message,noteFinishDealRequestMessage.getHead());
		}
		return XmlMapper.toXml(commonHeadResponseMessage);
	}
	
	/**
	 * 组织返回信息
	 * @param flag 成功失败标识
	 * @param tMessage 返回描述
	 * @param requestHead 请求报文头
	 * @return  CommonHeadResponseMessage  返回报文
	 */
	private CommonResponseMessage clientSend(boolean flag,String tMessage,
			 RequestHead requestHead){
		CommonResponseMessage Response = new CommonResponseMessage();
		Date date = new Date();
		ResponseHead head = new ResponseHead();
		head.setVersion(requestHead.getVersion());
		head.setCn2utf8(requestHead.getCn2utf8());
		
		ResponseMsg ResqMsg = new ResponseMsg();
		RequestMsg ReqMsg = requestHead.getMsg();
		
		ResqMsg.setServiceID(ReqMsg.getServiceID());
		ResqMsg.setServiceType(ReqMsg.getServiceType());
		ResqMsg.setCallType(ReqMsg.getCallType());
		ResqMsg.setTransNo(ReqMsg.getTransNo());
		ResqMsg.setClientIP(ReqMsg.getClientIP());
		ResqMsg.setFromSystemkey(ReqMsg.getFromSystemkey());
		ResqMsg.setTransDate(ReqMsg.getTransNo());
		ResqMsg.setTransTime(ReqMsg.getTransTime());
		ResqMsg.setToSystemKey(ReqMsg.getToSystemKey());
		String RefDate = sdf.format(date);
		ResqMsg.setRefDate(RefDate.substring(0,10).replaceAll("-", ""));
		ResqMsg.setRefTime(RefDate.substring(11).replaceAll(":", ""));
		ResqMsg.setEffectiveTime(ReqMsg.getEffectiveTime());
		head.setMsg(ResqMsg);
		
		ResponseStatus ReqStatus = new ResponseStatus();
		ResponseBody responseBody = new ResponseBody();
		if(flag){
			ReqStatus.setReturnCode(Constant.webservice_returnCode_SUCC);
			ReqStatus.setDesc("处理成功！");
			
			responseBody.setReturnCode(Constant.webservice_returnCode_SUCC);
			responseBody.setDesc("处理成功！");
		}else{
			ReqStatus.setReturnCode(Constant.webservice_returnCode_FAILED);
			ReqStatus.setDesc(tMessage);
			
			responseBody.setReturnCode(Constant.webservice_returnCode_FAILED);
			responseBody.setDesc(tMessage);
		}
		head.setStatus(ReqStatus);
		Response.setHead(head);
		Response.setBody(responseBody);
		
		return Response;
	}
}