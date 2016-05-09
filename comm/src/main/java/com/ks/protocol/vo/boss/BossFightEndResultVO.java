package com.ks.protocol.vo.boss;

import com.ks.protocol.Message;
import com.ks.protocol.vo.goods.GainAwardVO;

/**
 * 提交boss站返回VO
 * @author hanjie.l
 *
 */
public class BossFightEndResultVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 594435294789531388L;

	/**
	 * boss最新信息
	 */
	private BossVO bossVO;
	
	/**
	 * 对boss造成总的伤害
	 */
	private long totalHurt;
	
	/**
	 * 伤害奖励
	 */
	private GainAwardVO hurtReward;
	
	/**
	 * 击杀奖励
	 */
	private GainAwardVO killReward;

	public BossVO getBossVO() {
		return bossVO;
	}

	public void setBossVO(BossVO bossVO) {
		this.bossVO = bossVO;
	}

	public long getTotalHurt() {
		return totalHurt;
	}

	public void setTotalHurt(long totalHurt) {
		this.totalHurt = totalHurt;
	}

	public GainAwardVO getHurtReward() {
		return hurtReward;
	}

	public void setHurtReward(GainAwardVO hurtReward) {
		this.hurtReward = hurtReward;
	}

	public GainAwardVO getKillReward() {
		return killReward;
	}

	public void setKillReward(GainAwardVO killReward) {
		this.killReward = killReward;
	}
}