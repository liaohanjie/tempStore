package com.ks.protocol.vo.boss;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.dungeon.FightRoundResultVO;

/**
 * boss开始战斗VO
 * @author hanjie.l
 *
 */
public class BossFightStartResultVO extends Message{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9026038133962857165L;
	/**
	 * 战斗VO
	 */
	List<FightRoundResultVO> fightResultVO;
	public List<FightRoundResultVO> getFightResultVO() {
		return fightResultVO;
	}
	public void setFightResultVO(List<FightRoundResultVO> fightResultVO) {
		this.fightResultVO = fightResultVO;
	}     
}