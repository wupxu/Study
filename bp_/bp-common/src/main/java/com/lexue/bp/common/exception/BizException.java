package com.lexue.bp.common.exception;


import com.lexue.bp.common.enums.ExceptionEnums;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
	
	private int code;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2418304851635638450L;
	
    
	public BizException( ExceptionEnums exceptionEnums) {
		super(exceptionEnums.getMsg());
		this.code = exceptionEnums.getCode();
	}
	
	public BizException( ExceptionEnums exceptionEnums,String message) {
		super(message);
		this.code = exceptionEnums.getCode();
	}

}
