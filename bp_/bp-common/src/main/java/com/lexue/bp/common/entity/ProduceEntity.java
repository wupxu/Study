package com.lexue.bp.common.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * 积分累积流水
 * @author bc
 *
 */
@Entity
@Table(name = "bp_produce",indexes={@Index(name="idx_u_m_c_s",columnList="uid,module_id,produce_channel,status")})
@Data
public class ProduceEntity {

	/** 系统主键 */
	@Id
	private Long id;
	
	/** 用户编号 */
	@Column(name="uid")
	private long uid;
	
	/** 模块编号 */
	@Column(name="module_id")
	private int moduleId;
	
	/** 累积渠道编号 */
	@Column(name="produce_channel")
	private int produceChannel;
	
	/** 累积业务编号 */
	@Column(name="produce_bizid")
	private String produceBizid;
	
	/** 累积积分数 */
	@Column(name="produce_score")
	private int produceScore;
	
	/** 剩余积分数 */
	@Column(name="remain_score")
	private int remainScore;
	
	/** 生效日期 毫秒 */
	@Column(name="effective_date")
	private long effectiveDate;
	
	/** 失效日期 毫秒 */
	@Column(name="invalid_date")
	private long invalidDate;

	/**
	 * 状态
	 * 1、2、3、4、5 待领取、未使用、已使用、已失效、已过期。
	 */
	@Column(name="status")
	private byte status;
	
	/**
	 * 后台增加积分的原因
	 * 
	 * 备注
	 */
	private String remark;
	
	/** 创建时间 */
	private long cTime;
	
	/**
	 * 版本
	 */
	@Version
	private Long version;

	@Transient
	private String showDate;



	public ProduceEntity(long id ,String showDate, int type, byte status, int remainScore,long effectiveDate,long invalidDate,long uid) {
		this.id = id;
		this.showDate = showDate;
		this.produceChannel = type;
		this.status = status;
		this.remainScore = remainScore;
		this.effectiveDate = effectiveDate;
		this.invalidDate = invalidDate;
		this.uid = uid;
	}

	public ProduceEntity() {
	}

	public ProduceEntity(Long id, long uid, int moduleId, int produceChannel, String produceBizid, int produceScore, int remainScore, long effectiveDate, long invalidDate, byte status, String remark, long cTime, Long version, String showDate) {
		this.id = id;
		this.uid = uid;
		this.moduleId = moduleId;
		this.produceChannel = produceChannel;
		this.produceBizid = produceBizid;
		this.produceScore = produceScore;
		this.remainScore = remainScore;
		this.effectiveDate = effectiveDate;
		this.invalidDate = invalidDate;
		this.status = status;
		this.remark = remark;
		this.cTime = cTime;
		this.version = version;
		this.showDate = showDate;
	}

	public ProduceEntity(Long id) {
		this.id = id;
	}

	public ProduceEntity(long id,long effectiveDate, long cTime,long uid,int produceChannel) {
		this.id = id;
		this.effectiveDate = effectiveDate;
		this.cTime = cTime;
		this.uid = uid;
		this.produceChannel = produceChannel;
	}
}
