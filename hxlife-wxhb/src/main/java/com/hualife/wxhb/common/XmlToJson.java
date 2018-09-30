package com.hualife.wxhb.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;  
import java.util.HashMap;  
import java.util.LinkedList;  
import java.util.List;  
import java.util.Map;


import net.sf.json.JSONObject;

import org.jdom.Document;  
import org.jdom.Element;  
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;  
  
public class XmlToJson {  
    /** 
     * 转换一个xml格式的字符串到json格式 
     * @param xml 
     * xml格式的字符串 
     * @return 成功返回json 格式的字符串;失败反回null 
     */  
    public static  String xml2JSON(String xml) {  
        JSONObject obj = new JSONObject();  
        try {  
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(is);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 转换一个xml格式的字符串到json格式 
     * @param file java.io.File实例是一个有效的xml文件 
     * @return 成功反回json 格式的字符串;失败反回null 
     */  
    public static String xml2JSON(File file) {  
        JSONObject obj = new JSONObject();  
        try {  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(file);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 一个迭代方法 
     * @param element : org.jdom.Element 
     * @return java.util.Map 实例 
     */  
    @SuppressWarnings("unchecked")  
    private static Map<String,Object>  iterateElement(Element element) {  
        List<Object> jiedian = element.getChildren();  
        Element et = null;  
        Map<String,Object> obj = new HashMap<String,Object>();  
        List<Object> list = null;  
        for (int i = 0; i < jiedian.size(); i++) {  
            list = new LinkedList<Object>();  
            et = (Element) jiedian.get(i);  
            if (et.getTextTrim().equals("")) {  
                if (et.getChildren().size() == 0)  
                    continue;  
                if (obj.containsKey(et.getName())) {  
                    list = (List<Object>) obj.get(et.getName());  
                }  
                list.add(iterateElement(et));  
                obj.put(et.getName(), list);  
            } else {  
                if (obj.containsKey(et.getName())) {  
                    list = (List<Object>) obj.get(et.getName());  
                }  
                list.add(et.getTextTrim());  
                obj.put(et.getName(), list);  
            }  
        }  
        return obj;  
    }  
  
    /**
     * 将XML转换成String输出
     * **/
    public static String xml2Str(String file) throws Exception {

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new FileInputStream(new File(file)));

        Format format = Format.getCompactFormat();
        format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
        XMLOutputter xmlout = new XMLOutputter();
        
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        xmlout.output(document, bo);
        return bo.toString().trim();
    }
    
    
    
    
    // 测试  
    public static void main(String[] args) { 
        try {
			System.out.println(XmlToJson.xml2JSON(XmlToJson.xml2Str("E:\\test\\test2.xml")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
}