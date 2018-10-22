package com.lexue.bp.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="bp_user",indexes={@Index(name="idx_uid_module",columnList="uid,module_id")})
@Data
public class UserEntity {

	/** 系统主键 */
	@Id
	private Long id;
	
	/** 用户编号  */
	@Column(name="uid")
	private long uid;
	
	/** 模块编号 */
	@Column(name="module_id")
	private int moduleId;
	
	/** 总累积积分*/
	@Column(name="total_produce")
	private int totalProduce;
	
	/** 总消耗积分*/
	@Column(name="total_consume")
	private int totalConsume;
	
	/** 总剩余积分 */
	@Column(name="total_remain")
	private int totalRemain;

}
