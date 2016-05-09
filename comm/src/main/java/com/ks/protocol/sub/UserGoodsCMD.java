package com.ks.protocol.sub;
/**
 * 用户物品
 * @author ks
 */
public interface UserGoodsCMD {
	
	/**使用装备*/
	short USE_EQUIPMENT = 1;
	/**取消使用装备*/
	short UN_USE_EQUIPMENT = 2;
	/**使用道具*/
	short USE_PROP = 3;
	/**修改战斗道具*/
	short UPDATE_FIGHT_PROP = 4;
	/**卖出物品*/
	short SELL_GOODS = 5;
	/**增加道具仓库容量*/
	short ADD_ITEM_CAPACITY = 6;
	/**合成物品*/
	short SYNTHESIS_GOODS = 7;	
	/**修理装备*/
	short USER_修理装备 = 8;
	/**副本道具*/
	short USER_副本道具= 9;
	/**精炼装备*/
	short USER_精炼装备= 10;
}
