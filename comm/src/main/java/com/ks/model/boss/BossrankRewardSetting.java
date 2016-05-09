package com.ks.model.boss;

import java.util.List;

import com.ks.model.goods.Goods;
/**
 * boss排名奖励
 * @author hanjie.l
 *
 */
public class BossrankRewardSetting {
	
	/**
	 * Id
	 */
	private int bossId;
	
	/**
	 * 排名
	 */
	private int rank;
	
	/**
	 * 奖励
	 */
	private List<Goods> rewards;

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

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
