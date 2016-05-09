package com.ks.model.goods;

import java.io.Serializable;
/**
 * 副本道具
 * @author living.li
 * @date   2014年6月23日
 */
public class BakProp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int PROP_铜钥匙=3050001;
	public static final int PROP_银钥匙=3050002;
	public static final int PROP_金钥匙=3050003;
	
	private int id;
	/**最大持有数亮*/
	private int maxNum;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
	
	
}

