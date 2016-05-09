package com.ks.protocol.vo.swaparena;

import com.ks.protocol.Message;
import com.ks.protocol.vo.fight.FightVO;

/**
 * 交换排名竞技赛挑战结果信息VO
 * @author hanjie.l
 *
 */
public class ChallengeResultVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8252350032151583830L;
	
	/**
	 * 获得荣誉值
	 */
	private int addHonor;

	/**
	 * 原来排名
	 */
	private int oldRank;
	
	/**
	 * 最新排名
	 */
	private int newRank;
	
	/**
	 * 剩余可挑战次数
	 */
	private int times;
	
	/**
	 * 下次可挑战时间
	 */
	private long nextFightTime;
	
	/**
	 * 战斗结果VO
	 */
	private FightVO fightVO;

	public int getOldRank() {
		return oldRank;
	}

	public void setOldRank(int oldRank) {
		this.oldRank = oldRank;
	}

	public int getNewRank() {
		return newRank;
	}

	public void setNewRank(int newRank) {
		this.newRank = newRank;
	}

	public FightVO getFightVO() {
		return fightVO;
	}

	public void setFightVO(FightVO fightVO) {
		this.fightVO = fightVO;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public long getNextFightTime() {
		return nextFightTime;
	}

	public void setNextFightTime(long nextFightTime) {
		this.nextFightTime = nextFightTime;
	}

	public int getAddHonor() {
		return addHonor;
	}

	public void setAddHonor(int addHonor) {
		this.addHonor = addHonor;
	}
}
