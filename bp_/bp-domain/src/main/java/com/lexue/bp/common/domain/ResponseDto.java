/**
 * 
 */
package com.lexue.bp.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bc
 *
 */
@Data
public class ResponseDto<T> {
	
	/**返回代码**/
	@ApiModelProperty("返回代码")
	private int rc;
	
	/**提示信息**/
	@ApiModelProperty("提示信息")
	private String msg="";
	
	/**响应主体**/
	@ApiModelProperty("响应主体")
	private T rep;
	
	/**签名信息**/
	@ApiModelProperty("签名信息")
	private String sig="";
	
	public ResponseDto(){}
	
	public ResponseDto(int rc) {
		this.rc = rc;
	}
	public ResponseDto(int rc,String msg) {
		this.rc  = rc;
		this.msg = msg;
	}
	
	public ResponseDto(int rc,T data) {
		this.rc = rc;
		this.rep = data;
	}
	
	public ResponseDto(int rc,String msg,T data) {
		this.rc = rc;
		this.msg = msg;
		this.rep = data;
	}
}
