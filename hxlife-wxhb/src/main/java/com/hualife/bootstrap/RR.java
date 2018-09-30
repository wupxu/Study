package com.hualife.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hualife.mesiframework.core.util.time.DateFormatUtil;
import com.hualife.wxhb.common.Constant;
import com.hualife.wxhb.service.GetMaxNo;

/** 
 * @author 吴培旭 
 * @description
 * @time 创建时间：2017年9月19日   
 */
public class RR {

	
	@Autowired
	private static GetMaxNo getMaxNo;
	public static void main(String[] args) throws IOException {
//		String pdfId = getMaxNo.getMaxNo();
		Date da = new Date();
//		Constant.OSS_PDF_PATH +
		String dateStr = DateFormatUtil.formatDate("yyyy/MM/dd/", da);
//		String ossPath =  "/" + "jand" + "/" + "1" + "/"+dateStr+".pdf";
		String ossPath = dateStr+"111"+".pdf";
		System.out.println(ossPath);
	}
	private static  void copyFile(String src,String dest) throws IOException{
	    FileInputStream in=new FileInputStream(src);
	    File file=new File(dest);
	    if(!file.exists())
	        file.createNewFile();
	    FileOutputStream out=new FileOutputStream(file);
	    int c;
	    byte buffer[]=new byte[1024];
	    while((c=in.read(buffer))!=-1){
	        for(int i=0;i<c;i++)
	            out.write(buffer[i]);        
	    }
	    in.close();
	    out.close();
	}
	public static void StringBufferDemo(String src) throws IOException{
        File file=new File(src);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        for(int i=0;i<10000;i++){
            StringBuffer sb=new StringBuffer();
            sb.append("这是第"+i+"行:前面介绍的各种方法都不关用,为什么总是奇怪的问题 ");
            out.write(sb.toString().getBytes("utf-8"));
        }        
        out.close();
        System.out.println("开始写入");
    }
}
