package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.BossAction;
import com.ks.logic.service.BossService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.boss.CheckBossOpenResult;
import com.ks.protocol.vo.boss.BossFightEndResultVO;
import com.ks.protocol.vo.boss.BossFightStartResultVO;
import com.ks.protocol.vo.boss.MoneyChangeVO;
import com.ks.protocol.vo.boss.WorldBossRecordVO;
import com.ks.protocol.vo.goods.GainAwardVO;

public class BossActionImpl implements BossAction{
	
	private static BossService bossService = ServiceFactory.getService(BossService.class);

	@Override
	public List<CheckBossOpenResult> getCheckBossOpenResult() {
		return bossService.getCheckBossOpenResult();
	}

	@Override
	public void initWorldBoss(int bossId, String version, long endTime) {
		bossService.initWorldBoss(bossId, version, endTime);
	}

	@Override
	public void destroyWorldBoss(int bossId, long nextBeginTime) {
		bossService.destroyWorldBoss(bossId, nextBeginTime);
	}

	@Override
	public List<WorldBossRecordVO> getBossInfo(int userId) {
		return bossService.getBossInfo(userId);
	}

	@Override
	public MoneyChangeVO inspired(int userId, int bossId, byte type) {
		return bossService.inspired(userId, bossId, type);
	}

	@Override
	public BossFightStartResultVO startFight(int userId, byte teamId, int bossId) {
		return bossService.startFight(userId, teamId, bossId);
	}

	@Override
	public BossFightEndResultVO endFight(int userId, int bossId, long hurt) {
		return bossService.endFight(userId, bossId, hurt);
	}

	@Override
	public MoneyChangeVO clearFightCd(int userId, int bossId) {
		return bossService.clearFightCd(userId, bossId);
	}

	@Override
	public GainAwardVO getBossJoinRewards(int userId) {
		return bossService.getBossJoinRewards(userId);
	}

	@Override
	public GainAwardVO getBossRankRewards(int userId) {
		return bossService.getBossRankRewards(userId);
	}

}
