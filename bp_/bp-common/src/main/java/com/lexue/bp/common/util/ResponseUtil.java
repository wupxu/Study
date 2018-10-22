/**
 * 
 */
package com.lexue.bp.common.util;

import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.enums.ResponseStatusEnums;

/**
 * @author bc
 *
 */
public class ResponseUtil {
	
	public  static <T> ResponseDto<T> generateSuccessResponse(T data) {
		return new ResponseDto<>(ResponseStatusEnums.OK.getCode(),data);
	}
	public  static <T> ResponseDto<Void> generateSuccessResponse() {
		return new ResponseDto<Void>(ResponseStatusEnums.OK.getCode());
	}
	
	public  static <T> ResponseDto<Void> generateErrorResponse(String msg) {
		return new ResponseDto<Void>(ResponseStatusEnums.ERROR.getCode(), msg);
	}
	
	public  static <T> ResponseDto<Void> generateErrorResponse(int code,String msg) {
		return new ResponseDto<Void>(code, msg);
	}
}
