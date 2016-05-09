package com.ks.model.totem;

import java.io.Serializable;

/**
 * 神木图腾战魂重塑规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class TotemRule implements Serializable {

	private int id;
	/**战魂稀有度/战魂星级*/
	private int soulRare;
	/**花费金币*/
	private int costCoin;
	/**升级下一星级概率*/
    private double rateNextRare;
    /**变为素材卡的概率*/
    private double rateSelfRare;
    /**同星级其他战魂概率*/
    private double rateEqualRare;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSoulRare() {
		return soulRare;
	}
	public void setSoulRare(int soulRare) {
		this.soulRare = soulRare;
	}
	public int getCostCoin() {
		return costCoin;
	}
	public void setCostCoin(int costCoin) {
		this.costCoin = costCoin;
	}
	public double getRateNextRare() {
		return rateNextRare;
	}
	public void setRateNextRare(double rateNextRare) {
		this.rateNextRare = rateNextRare;
	}
	public double getRateSelfRare() {
		return rateSelfRare;
	}
	public void setRateSelfRare(double rateSelfRare) {
		this.rateSelfRare = rateSelfRare;
	}
	public double getRateEqualRare() {
		return rateEqualRare;
	}
	public void setRateEqualRare(double rateEqualRare) {
		this.rateEqualRare = rateEqualRare;
	}
}
