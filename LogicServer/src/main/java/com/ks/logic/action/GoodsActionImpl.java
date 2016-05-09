/**
 * 
 */
package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.GoodsAction;
import com.ks.logic.service.GoodsService;
import com.ks.logic.service.ServiceFactory;
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
 * @date 2014年10月23日 下午2:55:45
 * 
 * 
 */
public class GoodsActionImpl implements GoodsAction {
	private GoodsService goodsService = ServiceFactory.getService(GoodsService.class);

	@Override
	public List<Prop> getAllProp() {
		return goodsService.getAllProp();
	}

	@Override
	public List<Equipment> getEquipments() {
		return goodsService.getEquipments();
	}

	@Override
	public List<Stuff> getStuffs() {
		return goodsService.getStuffs();
	}

	@Override
	public List<BakProp> queryBakProp() {
		return goodsService.queryBakProp();
	}

	@Override
	public List<Soul> getSouls(SoulFilter sould) {
		return goodsService.getSouls(sould);
	}

	@Override
	public Integer getSoulsCount(SoulFilter soul) {
		return goodsService.getSoulsCount(soul);
	}

	@Override
	public List<Monster> getMonster(MonsterFilter filter) {
		return goodsService.getMonster(filter);
	}

	@Override
	public Integer getMonsterCount(MonsterFilter filter) {
		return goodsService.getMonsterCount(filter);
	}

}
