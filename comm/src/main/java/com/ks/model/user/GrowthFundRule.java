package com.ks.model.user;

import java.io.Serializable;
/**
 * 成长基金奖励规则
 * @author living.li
 * @date 2014年8月5日
 */
public class GrowthFundRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**等级*/
	private int grade;
	/**魂币*/
	private int currency;
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	
	

}
