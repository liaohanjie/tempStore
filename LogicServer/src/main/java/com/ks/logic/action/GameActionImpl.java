package com.ks.logic.action;

import com.ks.action.logic.GameAction;
import com.ks.logic.cache.GameCache;
/**
 * 重读游戏缓存
 * @author hanjie.l
 *
 */
public class GameActionImpl implements GameAction {

	@Override
	public void reloadGameCache() {
		GameCache.init();
	}

}
