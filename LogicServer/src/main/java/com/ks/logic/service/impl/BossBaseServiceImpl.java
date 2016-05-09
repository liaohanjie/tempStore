package com.ks.logic.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.model.boss.BeginEndTime;
import com.ks.model.boss.BossOpenSetting;
import com.ks.model.boss.BossSetting;
import com.ks.model.boss.CheckBossOpenResult;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.Monster;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.dungeon.FightRoundResultVO;
import com.ks.protocol.vo.dungeon.MonsterAwardVO;

/**
 * boss基础服务
 * 
 * @author admin
 * 
 */
public abstract class BossBaseServiceImpl extends BaseService {
	
	
	/**
	 * 获取boss配置
	 * @param bossId
	 * @param level
	 * @return
	 */
	protected BossSetting getBossSetting(int bossId, int level){
		BossSetting bossSetting = GameCache.getBossByIdAndLevel(bossId, level);
		if(bossSetting == null){
			return GameCache.getMaxLevelBossSetting(bossId);
		}
		return bossSetting;
	}

	/**
	 * 获取战斗VO
	 * 
	 * @param bossSetting
	 * @return
	 */
	protected List<FightRoundResultVO> getFightVO(BossSetting bossSetting) {

		List<FightRoundResultVO> fightRoundResultVOs = new ArrayList<>();

		// 每个回合一条记录
		FightRoundResultVO resultVO = MessageFactory.getMessage(FightRoundResultVO.class);

		List<MonsterAwardVO> mgoods = new ArrayList<MonsterAwardVO>();
		String[] monsterIds = bossSetting.getMonsters().split(ChapterRound.MONSTERS_SPLIT);
		for (int pos = 0; pos < 6; pos++) {
			int monsterId = Integer.valueOf(monsterIds[pos]);
			if (monsterId == 0) {
				continue;
			}
			Monster mon = GameCache.getMonster(monsterId);
			if (mon == null) {
				throw new GameException(GameException.CODE_参数错误, "monster no found." + monsterId);
			}
			MonsterAwardVO monsterAwardVO = getMonsterAwardVO(monsterId, pos, 1);
			mgoods.add(monsterAwardVO);
		}
		resultVO.setMonsterAwards(mgoods);
		fightRoundResultVOs.add(resultVO);
		return fightRoundResultVOs;
	}

	/**
	 * 不需要计算掉落和箱子
	 * 
	 * @param monsterId
	 * @param pos
	 * @param round
	 * @return
	 */
	private MonsterAwardVO getMonsterAwardVO(int monsterId, int pos, int round) {
		MonsterAwardVO mongift = MessageFactory.getMessage(MonsterAwardVO.class);
		mongift.setMonsterId(monsterId);
		mongift.setPos(pos);
		mongift.setRound(round);
		return mongift;
	}

	/**
	 * 检查活动是否开启
	 * 
	 * @param bossId
	 * @return
	 */
	protected CheckBossOpenResult checkBossOpen(int bossId) {
		long nowTime = System.currentTimeMillis();
		CheckBossOpenResult bossOpenResult = new CheckBossOpenResult();
		bossOpenResult.setBossId(bossId);

		BossOpenSetting bossOpenSetting = GameCache.getBossOpenSetting(bossId);
		List<BeginEndTime> beginEndTimes = bossOpenSetting.getBeginEndTimes();
		for (int i = 1; i <= beginEndTimes.size(); i++) {
			BeginEndTime beginEndTime = beginEndTimes.get(i - 1);
			Calendar begin = Calendar.getInstance();
			begin.set(Calendar.HOUR_OF_DAY, beginEndTime.getBegin().get(Calendar.HOUR_OF_DAY));
			begin.set(Calendar.MINUTE, beginEndTime.getBegin().get(Calendar.MINUTE));
			begin.set(Calendar.SECOND, beginEndTime.getBegin().get(Calendar.SECOND));

			Calendar end = Calendar.getInstance();
			end.set(Calendar.HOUR_OF_DAY, beginEndTime.getEnd().get(Calendar.HOUR_OF_DAY));
			end.set(Calendar.MINUTE, beginEndTime.getEnd().get(Calendar.MINUTE));
			end.set(Calendar.SECOND, beginEndTime.getEnd().get(Calendar.SECOND));

			// 在开启时间内
			if (nowTime >= begin.getTimeInMillis() && nowTime < end.getTimeInMillis()) {
				bossOpenResult.setOpen(true);
				bossOpenResult.setEndTime(end.getTimeInMillis());

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
				String version = dateFormat.format(new Date()) + "_" + i;
				bossOpenResult.setVersion(version);
				break;
				// 下次开启时间是当前场
			} else if (nowTime < begin.getTimeInMillis()) {
				bossOpenResult.setNextOpenTime(begin.getTimeInMillis());
				break;
				// 下次开始时间是明天
			} else if (nowTime >= end.getTimeInMillis() && i >= beginEndTimes.size()) {
				Calendar todayFirstBegin = Calendar.getInstance();
				todayFirstBegin.set(Calendar.HOUR_OF_DAY, beginEndTimes.get(0).getBegin().get(Calendar.HOUR_OF_DAY));
				todayFirstBegin.set(Calendar.MINUTE, beginEndTimes.get(0).getBegin().get(Calendar.MINUTE));
				todayFirstBegin.set(Calendar.SECOND, beginEndTimes.get(0).getBegin().get(Calendar.SECOND));

				bossOpenResult.setNextOpenTime(todayFirstBegin.getTimeInMillis() + 24 * 60 * 60 * 1000);
				break;
			}
		}

		return bossOpenResult;
	}
}
