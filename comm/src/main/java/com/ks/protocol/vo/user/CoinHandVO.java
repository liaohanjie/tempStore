package com.ks.protocol.vo.user;

import com.ks.protocol.Message;

/**
 * 点金手
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月16日
 */
public class CoinHandVO extends Message {
	
	private static final long serialVersionUID = 1L;
	
	/**基本金币数量*/
	private int baseGold;
	/**倍数*/
	private int times;
	
	public void init(int baseGold, int times){
		this.baseGold = baseGold;
		this.times = times;
	}
	
	public int getBaseGold() {
		return baseGold;
	}
	public void setBaseGold(int baseGold) {
		this.baseGold = baseGold;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
}
