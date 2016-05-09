/**
 * 
 */
package com.ks.model.pay;

import java.io.Serializable;

/**
 * 充值送魂钻
 * @author zhoujf
 * @date 2015年6月2日
 */
public class Mall implements Serializable {

    private static final long serialVersionUID = 8398326865370940497L;
    
    /**启用状态*/
    public final static byte STATUS_DISABLE = 0;
    
    /**禁用状态*/
    public final static byte STATUS_ENABLE = 1;
    
    /**充值金额(单位: RMB)*/
    private int money;
    
    /**魂钻*/
    private int currency;
    
    /**赠送魂钻*/
    private int extra;
    
    /**启用状态  0: 禁用, 1: 开启*/
    private byte status;
    
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getExtra() {
		return extra;
	}

	public void setExtra(int extra) {
		this.extra = extra;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
    
}
