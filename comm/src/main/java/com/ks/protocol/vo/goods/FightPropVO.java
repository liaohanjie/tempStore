package com.ks.protocol.vo.goods;

import com.ks.model.goods.FightProp;
import com.ks.protocol.Message;

public class FightPropVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**格子编号*/
	private int gridId;
	/**道具编号*/
	private int propId;
	/**数量*/
	private int num;
	
	public void init(FightProp o){
		this.gridId = o.getGridId();
		this.propId = o.getPropId();
		this.num = o.getNum();
	}
	
	public int getGridId() {
		return gridId;
	}
	public void setGridId(int gridId) {
		this.gridId = gridId;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
