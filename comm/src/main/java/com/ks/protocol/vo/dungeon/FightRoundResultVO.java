package com.ks.protocol.vo.dungeon;

import java.util.List;

import com.ks.protocol.Message;
/**
 * 战斗回合结果
 * @author ks 
 */
public class FightRoundResultVO extends Message {
	
	private static final long serialVersionUID = 1L;
	
	/**怪物奖励*/
	private List<MonsterAwardVO> monsterAwards;
	
	
	public List<MonsterAwardVO> getMonsterAwards() {
		return monsterAwards;
	}
	public void setMonsterAwards(List<MonsterAwardVO> monsterAwards) {
		this.monsterAwards = monsterAwards;
	}
	
}
