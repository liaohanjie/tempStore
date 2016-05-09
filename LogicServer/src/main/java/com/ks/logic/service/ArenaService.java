package com.ks.logic.service;

import com.ks.access.Transaction;
import com.ks.model.user.UserSoul;
import com.ks.protocol.vo.fight.FightVO;

public interface ArenaService {
	/**
	 * 战斗
	 * @param attackerId 攻击方编号
	 * @param defenderId 防守方编号
	 * @return 战斗结果
	 */
	@Transaction
	FightVO fighting(int attackerId,int defenderId);
	
	/**
	 * 计算各属性值
	 * @param userSoul
	 * @param valueType
	 * 0 生命
	 * 1 攻击
	 * 2 防御
	 * 3 恢复
	 * @return
	 */
	public int calAttributesValue(UserSoul userSoul, int valueType);
	
}
