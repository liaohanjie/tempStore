package com.ks.protocol.vo.goods;

import com.ks.protocol.Message;
/**
 * 卖出道具VO
 * @author ks
 */
public class SellGoodsVO extends Message {

	private static final long serialVersionUID = 1L;
	/**卖出道具格子编号*/
	private int gridId;
	/**卖出的数量*/
	private int num;
	public int getGridId() {
		return gridId;
	}
	public void setGridId(int gridId) {
		this.gridId = gridId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SellGoodsVO){
			SellGoodsVO vo = (SellGoodsVO)obj;
			return vo.getGridId()==gridId;
		}
		return super.equals(obj);
	}
	
}
