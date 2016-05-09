package com.ks.protocol.vo.goods;

import com.ks.model.goods.UserBakProp;
import com.ks.protocol.Message;

public abstract class UserBakPropVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**物品id*/
	private int bakPropId;
	/**数量*/
	private int num;

	
	public void init(UserBakProp o){
		this.bakPropId = o.getBakPropId();
		this.num = o.getNum();
	}
	public int getBakPropId() {
		return bakPropId;
	}

	public void setBakPropId(int bakPropId) {
		this.bakPropId = bakPropId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
