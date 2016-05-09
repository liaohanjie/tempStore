package com.ks.protocol.vo.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;
/**
 * 怪物奖励
 * @author ks
 */
public class MonsterAwardVO extends Message {

	private static final long serialVersionUID = 1L;
	/**回合*/
	private int round;
	/**位置*/
	private int pos;
	/**怪物编号*/
	private int monsterId;
	/**奖励*/
	private List<GoodsVO> awards;
	
	private BoxVO box;
	
	
	public void addGoods(GoodsVO vo){
		if(this.awards==null){
			awards=new ArrayList<>();
		}
		awards.add(vo);
	}

	
	public int getMonsterId() {
		return monsterId;
	}
	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}
	public List<GoodsVO> getAwards() {
		return awards;
	}
	public void setAwards(List<GoodsVO> awards) {
		this.awards = awards;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public BoxVO getBox() {
		return box;
	}
	public void setBox(BoxVO box) {
		this.box = box;
	}
	
}
