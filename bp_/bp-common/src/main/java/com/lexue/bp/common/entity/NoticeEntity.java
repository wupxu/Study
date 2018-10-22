package com.lexue.bp.common.entity;


import javax.persistence.*;

import com.lexue.bp.common.entity.pk.NoticePK;
import lombok.Data;

/**
 * 公告类
 * 
 * @author wpx
 *
 */
@Entity
@Table(name = "bp_notice")
@IdClass(NoticePK.class)
@Data
public class NoticeEntity {

	/** 系统主键 */
	@Id
	private long id;

	/* 模版id */
	@Id
	@Column(name = "mdid")
	private int moduleId;

	/**
	 * 0/1 开启/关闭 状态
	 */
	private int status;

	/** 公告名称 
	private String noticeName;*/

	/** 公告内容 */
	@Column(length = 1500)
	private String content;

	/** 操作员姓名 */
	private String operatorName;
	
	/** 操作员编号 */
	private long operatorId;

	/** 更新时间 */
	@Column(name = "update_time")
	private long updateTime;


}
