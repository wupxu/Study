package com.hualife.wxhb.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropsConfig{
	
	private static Properties props = new Properties();
	
	static{
		load();
	}
	
	private static synchronized void load() {
		try {
			if(props==null || props.isEmpty()){
				InputStream is = PropsConfig.class.getClassLoader().getResourceAsStream("config/sysconfig.properties");
				props.load(is);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
			
	}

	public  static String getPropValue(String key) {
		if(props == null || props.isEmpty()){
			load();
		}
		return (String)props.get(key);
	}
	
	public static int getPropValue(String key,int defaultV) {
		return getPropValue(key)!=null?Integer.parseInt(getPropValue(key)):defaultV;
	}
	
	public static String getPropValue(String key,String defaultV) {
		return getPropValue(key)!=null?getPropValue(key):defaultV;
	}
}
