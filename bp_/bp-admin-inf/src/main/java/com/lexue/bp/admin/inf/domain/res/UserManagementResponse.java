package com.lexue.bp.admin.inf.domain.res;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理接受类
 * @author wpx
 *
 */
@Data
public class UserManagementResponse {
	 /**
     * 乐学id
     */
	@ApiModelProperty("乐学id")
    private String lid ;

    /**
     * uid
     */
    @ApiModelProperty("uid")
    private long uid ;

    /**
     * 用户昵称
     */
	@ApiModelProperty("用户昵称")
    private String nick;
    
    /**
     * 手机号
     */
	@ApiModelProperty("手机号")
    private long mobile;
    
   /**
    * 总剩余积分
    */
	@ApiModelProperty("总剩余积分")
	private int totalRemain;
}
