package com.ks.action.game;

import com.ks.rpc.Async;

/**
 * @author living
 * @date 2014年6月28日
 *	game 通知(world 调用)
 */
public interface GameNotifiAction {
	
	@Async
	void notifiPlayer(long sessionId,int nofitiType);
}
                                                  