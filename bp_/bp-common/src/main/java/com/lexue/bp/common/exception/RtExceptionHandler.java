package com.lexue.bp.common.exception;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lexue.bp.common.domain.ResponseDto;
import com.lexue.bp.common.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;



/**
 * @author bc
 *
 */
@ControllerAdvice
@Slf4j
public class RtExceptionHandler  {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ DataAccessException.class })
	@ResponseBody
	public ResponseDto<?> databaseError(Exception e) {
		log.error("Database error:{}",e);
		return ResponseUtil.generateErrorResponse(e.getMessage());
	}
	
	@ExceptionHandler(BizException.class)
	@ResponseBody
	public ResponseDto<?> handleBBSErrorHandler(BizException e) {
		log.error("BizException:{}",e);
		return ResponseUtil.generateErrorResponse(e.getCode(),e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseDto<?> defaultErrorHandler(Exception e) {
		log.error("Exception:{}",e);
		return ResponseUtil.generateErrorResponse(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseDto<?> handleMethodArgumentNotValid(MethodArgumentNotValidException manve) {
		String msg = "";
		//
		List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
		for(FieldError fe : fieldErrors) {
			//只写第一项错误
			msg = fe.getField()+messageSource.getMessage(fe, null);
			break;
		}
		return ResponseUtil.generateErrorResponse(msg);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseDto<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		return ResponseUtil.generateErrorResponse(ex.getMessage());
	}
	
}
