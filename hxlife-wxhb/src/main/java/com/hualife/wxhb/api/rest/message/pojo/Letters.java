package com.hualife.wxhb.api.rest.message.pojo;

/**
 * @author yangpeixin
 * @description 返回参数
 * @date 2017-08-04
 */

public class Letters {
	/**
	 * 财务对象
	 */
	private Fina fina = new Fina();
	/**
	 * 健康对象
	 */
	private Health  health = new Health();
	/**
	 * 体检对象
	 */
	private Physical  physical =new Physical();
	/**
	 * 契调对象
	 */
	private Survival  survival = new Survival();
	public Fina getFina() {
		return fina;
	}
	public void setFina(Fina fina) {
		this.fina = fina;
	}
	public Health getHealth() {
		return health;
	}
	public void setHealth(Health health) {
		this.health = health;
	}
	public Physical getPhysical() {
		return physical;
	}
	public void setPhysical(Physical physical) {
		this.physical = physical;
	}
	public Survival getSurvival() {
		return survival;
	}
	public void setSurvival(Survival survival) {
		this.survival = survival;
	}
	
	
}
