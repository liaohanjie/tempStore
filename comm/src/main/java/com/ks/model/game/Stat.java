package com.ks.model.game;

import java.io.Serializable;

/**
 * 游戏记录状态
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月11日
 */
public class Stat implements Serializable {

    private static final long serialVersionUID = 4132866819380729783L;
    
    /**购买成长基金人数*/
	public final static int ID_BUY_GROW_FUND_COUNT = 1;
	/**神秘商店最后更新时间 */
	public final static int ID_MYSTERY_SHOP_UPDATE_TIME = 2;
	
	private int id;
	private long value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
}
