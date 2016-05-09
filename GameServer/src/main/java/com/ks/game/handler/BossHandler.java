package com.ks.game.handler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ks.app.Application;
import com.ks.exceptions.GameException;
import com.ks.game.kernel.SerialThreadExecutor;
import com.ks.handler.GameHandler;
import com.ks.logger.LoggerFactory;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.BossCMD;
import com.ks.protocol.sub.ErrorCodeCMD;
import com.ks.protocol.vo.Head;
import com.ks.protocol.vo.boss.BossFightEndResultVO;
import com.ks.protocol.vo.boss.BossFightStartResultVO;
import com.ks.protocol.vo.boss.MoneyChangeVO;
import com.ks.protocol.vo.boss.WorldBossRecordVO;
import com.ks.protocol.vo.goods.GainAwardVO;

/**
 * boss
 * 
 * @author hanjie.l
 * 
 */
@MainCmd(mainCmd = MainCMD.WORLDBOSS)
public class BossHandler extends ActionAdapter {

	private final Logger LOGGER = LoggerFactory.get(BossHandler.class);

	@SubCmd(subCmd = BossCMD.BOSS_获取活动信息)
	public void getBossInfo(GameHandler handler) {
		List<WorldBossRecordVO> bossInfo = bossAction().getBossInfo(handler.getPlayer().getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead(), bossInfo);
	}

	@SubCmd(subCmd = BossCMD.BOSS_鼓舞, args = { "int", "byte" })
	public void inspired(GameHandler handler, int bossId, byte type) {
		MoneyChangeVO inspired = bossAction().inspired(handler.getPlayer().getUserId(), bossId, type);
		Application.sendMessage(handler.getChannel(), handler.getHead(), inspired);
	}

	@SubCmd(subCmd = BossCMD.BOSS_开始战斗, args = { "byte", "int" })
	public void startFight(GameHandler handler, byte teamId, int bossId) {
		BossFightStartResultVO startFight = bossAction().startFight(handler.getPlayer().getUserId(), teamId, bossId);
		Application.sendMessage(handler.getChannel(), handler.getHead(), startFight);
	}

	@SubCmd(subCmd = BossCMD.BOSS_提交战斗, args = { "int", "long" })
	public void endFight(final GameHandler handler, final int bossId, final long hurt) throws Exception {

		Callable<Boolean> callable = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				try {
					BossFightEndResultVO endFight = bossAction().endFight(handler.getPlayer().getUserId(), bossId, hurt);
					Application.sendMessage(handler.getChannel(), handler.getHead(), endFight);
				} catch (Exception e) {
					LOGGER.error(e);
					Head head = handler.getHead();
					returnErrorCode(head, handler, e);
				}
				return true;
			}
		};

		FutureTask<Boolean> submit = new FutureTask<Boolean>(callable);
		SerialThreadExecutor.getInstance().executeSerially(bossId, submit);
		submit.get(10, TimeUnit.SECONDS);
	}

	@SubCmd(subCmd = BossCMD.BOSS_清除战斗cd, args = { "int" })
	public void clearFightCd(GameHandler handler, int bossId) {
		MoneyChangeVO clearFightCd = bossAction().clearFightCd(handler.getPlayer().getUserId(), bossId);
		Application.sendMessage(handler.getChannel(), handler.getHead(), clearFightCd);
	}

	/**
	 * 领取奖励
	 * @param handler
	 * @param type
	 *            1参与奖励 2排名奖励
	 */
	@SubCmd(subCmd = BossCMD.BOSS_领取奖励, args = { "int" })
	public void getBossRewards(GameHandler handler, int type) {
		if (type == 1) {
			GainAwardVO bossJoinRewards = bossAction().getBossJoinRewards(handler.getPlayer().getUserId());
			Application.sendMessage(handler.getChannel(), handler.getHead(), bossJoinRewards);
		} else if (type == 2) {
			GainAwardVO bossRankRewards = bossAction().getBossRankRewards(handler.getPlayer().getUserId());
			Application.sendMessage(handler.getChannel(), handler.getHead(), bossRankRewards);
		} else {
			throw new GameException(GameException.CODE_参数错误, "");
		}
	}
	
	/**
	 * 返回错误码
	 * @param head
	 * @param handler
	 * @param e
	 */
	private void returnErrorCode(Head head, GameHandler handler, Exception e){
		if (e instanceof GameException) {
			GameException gameException = (GameException) e;
			head.init(MainCMD.ERROR_CODE, ErrorCodeCMD.GAME_ERROR);
			Application.sendMessage(handler.getChannel(), head, gameException.getCode(), gameException.getMessage());
		} else {
			head.init(MainCMD.ERROR_CODE, ErrorCodeCMD.APP_ERROR);
			Application.sendMessage(handler.getChannel(), head, e.getMessage() + "");
		}
	}
}
