package com.lexue.bp.admin.inf.domain;

/**
 * 统计需求类-用户信息统计：按用户统计
 * 
 * @author wpx
 *
 */
public class UserMessageStatistic {

	/** 时间 */
	private long date;
	/** 用户编号 */
	private long uid;
	/** 渠道及消耗类型 */
	private int type;
	/** 已使用分数 */
	private int countScore;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getCountScore() {
		return countScore;
	}

	public void setCountScore(int countScore) {
		this.countScore = countScore;
	}
}
