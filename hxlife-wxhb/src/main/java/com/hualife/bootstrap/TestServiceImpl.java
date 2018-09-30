package com.hualife.bootstrap;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hualife.mesiframework.core.util.mapper.XmlMapper;
import com.hualife.wxhb.integration.dao.AgentDao;
import com.hualife.wxhb.integration.soap.UWTrans0233;
import com.hualife.wxhb.integration.soap.message.response.notePrintPush.NotePrintPushResponseMessage;
import com.hualife.wxhb.service.NotePrintResponseService;

/**
 * @author 吴培旭
 * @description
 * @time 创建时间：2017年9月17日
 */
@Service
public class TestServiceImpl implements TestSevice {

	@Autowired
	private AgentDao agentdao;
	@Autowired
	private UWTrans0233 uWTrans0233;
	@Autowired
	private NotePrintResponseService notePrintResponseService;

	@Override
	public void test() {
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");

		try {
			// 读取文件 转换成Document
			// Document document = reader.read(new
			// File("F:/xmltest/test14.xml"));
			// document转换为String字符串
			// String ReqXml = document.asXML();
			// String RespXml = uWTrans0233.uWTrans0233(ReqXml);
			// String yx0191Xml = yx0191.yx0191(ReqXml);
			// System.out.println(yx0191Xml);
			NotePrintPushResponseMessage notePrintPushResponseMessage = new NotePrintPushResponseMessage();
			Document document = reader.read(new File("E:/TEST.xml"));
			String ReqXml = document.asXML();
//			String uWTrans02332 = uWTrans0233.uWTrans0233(ReqXml);
//			System.out.println("核心返回报文"+uWTrans02332);
			notePrintPushResponseMessage = XmlMapper.fromXml(ReqXml, NotePrintPushResponseMessage.class);
			notePrintResponseService.setChooseTypeClientResponse(notePrintPushResponseMessage.getBody());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
