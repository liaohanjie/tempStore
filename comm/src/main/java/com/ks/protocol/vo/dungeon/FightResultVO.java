package com.ks.protocol.vo.dungeon;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;
/**
 * 开始战斗的返回
 * @author living.li
 * @date 2014年7月9日
 */
public class FightResultVO extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**回合详情*/
	private List<FightRoundResultVO> rounds;
	
	/**乱入怪物*/
	private String joinMonsters;
	/**经验*/
	private int exp;
	/**乱入可能得到的物品**/
	private List<GoodsVO> joinGoods;
	/**体力*/
	private int stamina;
	/**最后恢复体力时间*/
	private long lastRegainStaminaTime;
	
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public long getLastRegainStaminaTime() {
		return lastRegainStaminaTime;
	}
	public void setLastRegainStaminaTime(long lastRegainStaminaTime) {
		this.lastRegainStaminaTime = lastRegainStaminaTime;
	}
	
	public List<GoodsVO> getJoinGoods() {
		return joinGoods;
	}
	public void setJoinGoods(List<GoodsVO> joinGoods) {
		this.joinGoods = joinGoods;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public String getJoinMonsters() {
		return joinMonsters;
	}
	public void setJoinMonsters(String joinMonsters) {
		this.joinMonsters = joinMonsters;
	}
	
	public List<FightRoundResultVO> getRounds() {
		return rounds;
	}
	public void setRounds(List<FightRoundResultVO> rounds) {
		this.rounds = rounds;
	}
	
	
}
