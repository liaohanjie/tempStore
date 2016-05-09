package com.ks.model.swaparena;

import java.util.List;

import com.ks.model.goods.Goods;

/**
 * 交换排名竞技赛排名奖励
 * @author hanjie.l
 *
 */
public class SwapArenaRewardSetting {
	
	/**
	 * 排名
	 */
	private int rank;
	
	
	/**
	 * 奖励
	 */
	private List<Goods> rewards;


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public List<Goods> getRewards() {
		return rewards;
	}


	public void setRewards(List<Goods> rewards) {
		this.rewards = rewards;
	}
}
