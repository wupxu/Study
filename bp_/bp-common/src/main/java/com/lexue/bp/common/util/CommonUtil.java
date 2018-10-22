package com.lexue.bp.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class CommonUtil {
	
	/**
	 * 向后增加一年
	 * 
	 * @param source
	 * @param days
	 * @return
	 */
	public static long addYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		cal.add(Calendar.YEAR, 1);
		return cal.getTimeInMillis();
	}
	
	
	/**
	 * 向后增加指定的天数
	 * 
	 * @param source
	 * @param days
	 * @return
	 */
	public static Date addDay(Date source, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.add(Calendar.DATE, days);
		return cal.getTime();

	}
	
	/**
	 * 年月日时分秒毫秒+4位随机数
	 * @return
	 */
	public static long generateId() {
		Calendar calendar = new GregorianCalendar();
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf(calendar.get(Calendar.YEAR)))
				.append(valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2))
				.append(valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2))
				.append(valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2))
				.append(valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)), 2))
				.append(valueOfString(String.valueOf(calendar.get(Calendar.SECOND)), 2))
				.append(valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)), 3))
				.append(valueOfString(Integer.toString(new Random().nextInt(99)),2));
		return Long.parseLong(sb.toString());
	}
	
	private static String valueOfString(String str, int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len - str.length(); i++) {
			sb.append("0");
		}
		return (sb.length() == 0) ? (str) : (sb.toString() + str);
	}
}
