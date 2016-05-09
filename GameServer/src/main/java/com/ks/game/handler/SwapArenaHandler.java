package com.ks.game.handler;
import com.ks.action.logic.SwapArenaAction;
import com.ks.app.Application;
import com.ks.game.model.Player;
import com.ks.handler.GameHandler;
import com.ks.manager.ClientLockManager;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.SwapArenaCMD;
import com.ks.util.LockKeyUtil;
/**
 * 交换竞技场
 * @author hanjie.l
 *
 */
@MainCmd(mainCmd=MainCMD.SWAPARENA)
public class SwapArenaHandler extends ActionAdapter {

	/**
	 * 获取个人信息，及可挑战列表
	 * @param handler
	 * @param int
	 */
	@SubCmd(subCmd=SwapArenaCMD.GET_INFO)
	public void getInfo(GameHandler handler) {
		Player player = handler.getPlayer();
		ClientLockManager.lock(LockKeyUtil.getSwapArenaLockKey(player.getUserId()));
		try{
			SwapArenaAction swapArenaAction = swapArenaAction();
			Application.sendMessage(handler.getChannel(), handler.getHead(), swapArenaAction.getSwapArenaInfo(player.getUserId()));
		}finally{
			ClientLockManager.unlockThreadLock();
		}
		
	}
	
	
	/**
	 * 挑战玩家
	 * @param handler
	 * @param int
	 */
	@SubCmd(subCmd=SwapArenaCMD.CHALLENGE, args={"int"})
	public void challenge(GameHandler handler, int targetId) {

		Player player = handler.getPlayer();
		ClientLockManager.lock(LockKeyUtil.getSwapArenaLockKey(player.getUserId(), targetId));
		try{
			SwapArenaAction swapArenaAction = swapArenaAction();
			Application.sendMessage(handler.getChannel(), handler.getHead(), swapArenaAction.challenge(player.getUserId(), targetId));
		}finally{
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 *  购买挑战次数
	 * @param handler
	 * @param int
	 */
	@SubCmd(subCmd=SwapArenaCMD.BUY_CHALLENGE_TIME, args={"int"})
	public void buyChallengeTimes(GameHandler handler, int count) {

		Player player = handler.getPlayer();
		ClientLockManager.lock(LockKeyUtil.getSwapArenaLockKey(player.getUserId()));
		try{
			SwapArenaAction swapArenaAction = swapArenaAction();
			Application.sendMessage(handler.getChannel(), handler.getHead(), swapArenaAction.buyChallengeTimes(player.getUserId(), count));
		}finally{
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 * 获取战斗日志
	 * @param handler
	 * @param int
	 */
	@SubCmd(subCmd=SwapArenaCMD.GET_FIGHTLOG)
	public void getFightLog(GameHandler handler) {
		SwapArenaAction swapArenaAction = swapArenaAction();
		Application.sendMessage(handler.getChannel(), handler.getHead(), swapArenaAction.getFightLog(handler.getPlayer().getUserId()));
	}
}
