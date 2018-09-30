package com.hualife.wxhb.common;

/**
 * @author "张龙"
 * @Description 校验字段
 * @date 2017年8月21日 下午4:13:04
 */
public class Assert {
	
	/**
	 * 校验传入的字段不能为空
	 * @param obj 校验参数
	 * @param description 错误提示语
	 */
	public static void notEmpty(String obj,String description){
		if(obj == null || "".equals(obj.trim()) || "null".equals(obj)){
			throw new RuntimeException(description);
		}
	}
	
	/**
	 * 判断对象是否为空
	 * @param obj 校验对象
	 * @param description 错误提示语
	 */
	public static void notNull(Object obj,String description){
		if(obj == null){
			throw new RuntimeException(description);
		}
	}
	/**
	 * 数据比较
	 * @param flag 比较结果 true 则不提示，false则提示
	 * @param description 错误提示语
	 */
	public static void Compare(boolean flag,String description){
		if(!flag){
			throw new RuntimeException(description);
		}
	}
	
	/**
	 * 手机号码位数校验
	 * @param phone 手机号码
	 * @param description 错误描述
	 */
	public static void isPhone(String phone,String description){
		Compare(phone.length() == 11, description);
	}
}
