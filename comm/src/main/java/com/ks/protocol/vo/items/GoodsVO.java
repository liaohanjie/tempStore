package com.ks.protocol.vo.items;

import com.ks.model.goods.Goods;
import com.ks.protocol.Message;
/**
 * 物品
 * @author ks
 */
public class GoodsVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**物品编号*/
	private int goodsId;
	/**物品类型*/
	private int type;
	/**物品数量*/
	private int num;
	/**等级*/
	private int level;
	
	public void init(Goods o){
		this.goodsId = o.getGoodsId();
		this.type = o.getType();
		this.num = o.getNum();
		this.level = o.getLevel();
	}
	
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
