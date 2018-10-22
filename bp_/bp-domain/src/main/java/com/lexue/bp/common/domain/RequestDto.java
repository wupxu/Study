/**
 * 
 */
package com.lexue.bp.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto<T> {
	
	
	/**SessionId**/
//	private String sid;
	
	/**设备ID**/
//	private String did;
	
	/**请求主体**/
	private T requestBody;
	
	/**签名信息**/
	private String sign;
	
	public RequestDto(T requestBody){
		this.requestBody = requestBody;
	}
	
}
