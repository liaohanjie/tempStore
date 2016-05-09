/**
 * 
 */
package com.ks.action.world;

import java.util.List;

import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.filter.MonsterFilter;
import com.ks.model.filter.SoulFilter;
import com.ks.model.goods.BakProp;
import com.ks.model.goods.Prop;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;

/**
 * @author living.li
 * @date 2014年10月23日 下午2:53:56
 * 
 * 
 */
public interface WorldGoodsAction {
	/**
	 * 获得所有道具
	 * 
	 * @return 所有道具
	 */
	public List<Prop> getAllProp();

	/**
	 * 获得所有装备
	 * 
	 * @return 所有装备
	 */
	public List<Equipment> getEquipments();

	/**
	 * 获得所有材料
	 * 
	 * @return 所有材料
	 */
	public List<Stuff> getStuffs();

	/**
	 * 副本道具定义
	 * 
	 * @return
	 */

	public List<BakProp> queryBakProp();


	/**
	 * 查询战魂
	 * 
	 * @return 所有战魂
	 */
	public List<Soul> getSouls(SoulFilter soul);
	
	/**
	 * 得到战魂总数量
	 * @param soul
	 * @return
	 */
	public Integer getSoulsCount(SoulFilter soul);
	
	/**
	 * 查询怪物
	 * @param name
	 * @return
	 */
	public List<Monster> getMonster(MonsterFilter name);

	/**
	 * 得到怪物总数量
	 * @param name
	 * @return
	 */
	public Integer getMonsterCount(MonsterFilter name);
}
