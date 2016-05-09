/**
 * 
 */
package com.ks.wrold.action;

import java.util.List;

import com.ks.action.logic.GoodsAction;
import com.ks.action.world.WorldGoodsAction;
import com.ks.app.Application;
import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.filter.MonsterFilter;
import com.ks.model.filter.SoulFilter;
import com.ks.model.goods.BakProp;
import com.ks.model.goods.Prop;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.rpc.RPCKernel;

/**
 * @author living.li
 * @date 2014年10月23日 下午2:57:06
 * 
 * 
 */
public class WorldGoodsActionImpl implements WorldGoodsAction {

	public static GoodsAction goodsAction() {
		GoodsAction goodsActoon = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, GoodsAction.class);
		return goodsActoon;
	}

	@Override
	public List<Prop> getAllProp() {
		return goodsAction().getAllProp();
	}

	@Override
	public List<Equipment> getEquipments() {
		return goodsAction().getEquipments();
	}

	@Override
	public List<Stuff> getStuffs() {
		return goodsAction().getStuffs();
	}

	@Override
	public List<BakProp> queryBakProp() {
		return goodsAction().queryBakProp();
	}

	@Override
	public List<Soul> getSouls(SoulFilter soul) {
		return goodsAction().getSouls(soul);
	}

	@Override
	public Integer getSoulsCount(SoulFilter soul) {
		return goodsAction().getSoulsCount(soul);
	}

	@Override
	public List<Monster> getMonster(MonsterFilter filter) {
		return goodsAction().getMonster(filter);
	}

	@Override
	public Integer getMonsterCount(MonsterFilter filter) {
		return goodsAction().getMonsterCount(filter);
	}

}
