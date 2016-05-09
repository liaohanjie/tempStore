package com.ks.model.check;
/**
 * 战斗类型
 * @author hanjie.l
 *
 */
public enum BattleType {

	/**
	 * pve
	 */
	PVE,
	
	/**
	 * 世界boss
	 */
	BOSS,
	
	/**
	 * 爬塔
	 */
	CLIMTOWER,
	
	/**
	 * 交换竞技场
	 */
	SWAPARENA;
	
	
	public static BattleType valueOf(int type){
		for(BattleType battleType : BattleType.values()){
			if(battleType.ordinal() == type){
				return battleType;
			}
		}
		return null;
	}
}
