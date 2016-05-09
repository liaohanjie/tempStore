package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.event.GameLoggerEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserChapterService;
import com.ks.logic.utils.AwardUtils;
import com.ks.model.Award;
import com.ks.model.achieve.Achieve;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.check.BattleType;
import com.ks.model.dungeon.Box;
import com.ks.model.dungeon.Chapter;
import com.ks.model.dungeon.ChapterChestRecord;
import com.ks.model.dungeon.ChapterJoin;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.CurrFight;
import com.ks.model.dungeon.Drop;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.friend.Friend;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.goods.UserBakProp;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.LoggerType;
import com.ks.model.logger.SweepCountLogger;
import com.ks.model.mission.MissionCondition;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillEffect;
import com.ks.model.user.User;
import com.ks.model.user.UserBuff;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserChapter;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.model.user.UserTeam;
import com.ks.model.vip.VipPrivilege;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.activity.ActivityDefineVO;
import com.ks.protocol.vo.dungeon.BoxVO;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.dungeon.FightResultVO;
import com.ks.protocol.vo.dungeon.FightRoundResultVO;
import com.ks.protocol.vo.dungeon.MonsterAwardVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.items.GoodsVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.user.UserBuffVO;
import com.ks.protocol.vo.user.UserChapterVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.timer.TimerController;

public class UserChapterServiceImpl extends BaseService implements UserChapterService {

	@Override
	public UserChapterVO queryUserChapter(int userId) {
		// 查询最后一个副本的信息
		int dungeonId = userChapterDAO.queryUserChapterLimit(userId, Chapter.CHAPTER_ID_副本_START);
		if (dungeonId == 0) {
			UserChapter ud = new UserChapter();
			ud.setUserId(userId);
			ud.setChapterId(Chapter.CHAPTER_ID_关卡_START);
			ud.setSameDayBuyCount(0);
			ud.setSameDayCount(0);
			userChapterDAO.addUserChapter(ud);
			dungeonId = ud.getChapterId();
			
			// 更新当天zhando
		}
		UserChapter ud = userChapterDAO.queryUserChapter(userId, dungeonId);
		UserChapterVO vo = MessageFactory.getMessage(UserChapterVO.class);
		Chapter chapter = GameCache.getChapter(ud.getChapterId());
		vo.init(ud, chapter.getSiteId());
		return vo;
	}
	
	/**
	 * 重置当天战斗次数，当天购买次数
	 * 
	 * @param userId
	 */
	@Override
	public void reset(int userId){
		userChapterDAO.reset(userId);;
	}

	@Override
	public FightResultVO startFight(int userId, int chapterId, int friendId, byte teamId) {
		User user = userService.getExistUserCache(userId);
		userSoulService.checkSoulFull(user);
		Chapter chapter = GameCache.getChapter(chapterId);
		if (chapter == null) {
			throw new GameException(GameException.CODE_参数错误, "no this chapterId." + chapterId);
		}
		if (user.getLevel() < chapter.getMinLevel()) {
			throw new GameException(GameException.CODE_等级不够, " user level must be over." + chapter.getMinLevel());
		}
		userService.checkStamina(user);
		int stamina = chapter.getStamina();
		boolean isStart = activityService.activityIsStart(ActivityDefine.DEFINE_ID_体力消耗减半);
		// if(user.getGuideStep()==User.GUIDE_STEP21_指引END){//新手走完才能参加活动
		if (isStart) { // 检查体力减半活动开启没有
			DropRateMultiple drm = chapterDAO.getDropRateMultipleCache(ActivityDefine.DEFINE_ID_体力消耗减半, chapter.getSiteId());
			if (drm != null) {
				stamina = chapter.getStamina() / drm.getMultiple();
			}
		}
		// }
		userService.decrementStamina(user, stamina, LoggerType.TYPE_战斗消耗, chapterId + "");
		// 检查前一关
		if (chapter.getPevId() != 0) {
			Chapter prev = GameCache.getChapter(chapter.getPevId());
			if (prev == null) {
				throw new GameException(GameException.CODE_参数错误, "");
			}
			UserChapter prevDungeon = userChapterDAO.queryUserChapter(userId, chapter.getPevId());
			if (prevDungeon == null || prevDungeon.getPassCount() == 0) {
				throw new GameException(GameException.CODE_参数错误, " pve no found." + chapter.getPevId());
			}
		}
		UserChapter ud = userChapterDAO.queryUserChapter(userId, chapterId);
		if (ud == null) {
			ud = new UserChapter();
			ud.setUserId(userId);
			ud.setChapterId(chapterId);
			ud.setSameDayBuyCount(0);
			ud.setSameDayCount(0);
			userChapterDAO.addUserChapter(ud);
		}
		UserTeam team = userTeamService.getExistUserTeamCache(userId, teamId);
		List<UserSoulInfoVO> soulInfos = new ArrayList<UserSoulInfoVO>();
		Backage backage = userGoodsService.getPackage(userId);

		// 战魂
		userGoodsService.checkBackageFull(backage, user);
		for (long userSoulId : team.getPos()) {
			if (userSoulId != 0) {
				UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
				UserSoulInfoVO vo = userSoulService.gainUserSoulInfo(soul, backage.getUseGoodses().values());
				soulInfos.add(vo);
			}
		}
		
		// cost检查
		userTeamService.checkUserTeam(user, soulInfos);
		
		// 检查钥匙BUFF是否过期
		bakChapter(chapter, userId);

		//增加耐久度
		for (UserSoulInfoVO vo : soulInfos) {
			for (UserGoodsVO eq : vo.getEquipments()) {
				UserGoods goods = backage.getUseGoods(eq.getGridId());
				Equipment q = GameCache.getEquipment(goods.getGoodsId());
				if (q.getMaxDurable() > goods.getDurable()) {
					goods.setDurable(goods.getDurable() + 1);
					userGoodsDAO.updateUserGoodsCache(goods);
				}
			}
		}

		if (teamId != user.getCurrTeamId()) {
			user.setCurrTeamId(teamId);
			Map<String, String> hash = new HashMap<>();
			hash.put("currTeamId", String.valueOf(teamId));
			userDAO.updateUserCache(userId, hash);

			UserCap cap = userTeamDAO.getUserCapCache(userId);
			long userSoulId = team.getPos().get(team.getCap() - 1);
			UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
			List<UserGoods> eqs = backage.getSoulEquipments(soul);
			List<Integer> eq = new ArrayList<Integer>();
			for (UserGoods e : eqs) {
				eq.add(e.getGoodsId());
			}
			userTeamService.updateUserCap(cap, soul, eq);
		}

		// 友情点计算------------------------------------------------------
		CurrFight cf = new CurrFight();
		cf.setUserId(userId);
		cf.setChapterId(chapterId);
		boolean flag = activityService.activityIsStart(ActivityDefine.DEFINE_ID_友情点翻倍);
		int multiple = 1;
		// if(user.getGuideStep()==User.GUIDE_STEP21_指引END){
		if (flag) {
			DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_友情点翻倍, chapter.getSiteId());
			if (drm != null) {
				multiple = multiple * drm.getMultiple();
			}

		}
		// }
		if (friendId != -1) {
			Friend friend = friendDAO.queryFriend(userId, friendId);
			if (friend != null) {
				UserStat userStat = userStatDAO.queryUserStat(userId);
				if (userStat.getUseFriend().indexOf(friendId) == -1) {
					userStat.getUseFriend().add(friendId);
					UserStatOpt opt = new UserStatOpt();
					opt.useFriend = UserStatOpt.EQUAL;
					userStatDAO.updateUserStat(opt, userStat);

					cf.setFriendlyPoint(10 * multiple);
					userService.incrementFriendlyPoint(friendId, 10 * multiple, LoggerType.TYPE_FRIEND_FIGHT, friendId + "");
				}
			} else {
				// 冒险者（非好友只给5点）
				cf.setFriendlyPoint(5 * multiple);
				userService.incrementFriendlyPoint(friendId, 5 * multiple, LoggerType.TYPE_ADVENTURER_FIGHT, friendId + "");
			}
			cf.setFriendId(friendId);
		} else {
			// 机器人打过了才有
			cf.setFriendlyPoint(5 * multiple);
		}
		List<ChapterRound> chapterRounds = GameCache.getChapterRounds(chapterId);
		List<FightRoundResultVO> results = getFightResult(chapter, cf, chapterRounds, user);
		List<Goods> mayGoods = new ArrayList<>();
		// 乱入怪物
		getJoinMonsterGoods(chapterId, cf, mayGoods);

		userChapterDAO.updateCurrFightCache(cf);
		// 用户图鉴-------------------------------------------------------
		Set<Integer> idSet = new HashSet<>();
		for (ChapterRound round : chapterRounds) {

			String[] monsters = round.getMonsters().split(ChapterRound.MONSTERS_SPLIT);
			for (String id : monsters) {
				Monster monster = GameCache.getMonster(Integer.valueOf(id));
				if (monster != null) {
					idSet.add(monster.getSoulId());
				}
			}
			if (cf.getJoinMonsters() != null) {
				String[] joinMonster = cf.getJoinMonsters().split(ChapterRound.MONSTERS_SPLIT);
				for (String id : joinMonster) {
					Monster monster = GameCache.getMonster(Integer.valueOf(id));
					if (monster != null) {
						idSet.add(monster.getSoulId());
					}
				}
			}
		}
		userSoulService.seeUserSoulMap(userId, idSet);
		// 乱入奖励
		List<GoodsVO> joinGoods = new ArrayList<>();
		for (Goods goods : mayGoods) {
			GoodsVO vo = MessageFactory.getMessage(GoodsVO.class);
			vo.init(goods);
			joinGoods.add(vo);
		}

		FightResultVO mess = MessageFactory.getMessage(FightResultVO.class);
		mess.setRounds(results);
		mess.setJoinMonsters(cf.getJoinMonsters());
		mess.setExp(cf.getExp());
		mess.setJoinGoods(joinGoods);
		mess.setStamina(user.getStamina());
		mess.setLastRegainStaminaTime(user.getLastRegainStaminaTime().getTime());
		return mess;
	}

	private List<FightRoundResultVO> getFightResult(Chapter chapter, CurrFight cf, List<ChapterRound> rounds, User user) {

		List<FightRoundResultVO> results = new ArrayList<>();
		UserCap cap = userTeamDAO.getUserCapCache(cf.getUserId());
		int capSkillId = GameCache.getSoul(cap.getSoulId()).getCapSkill();
		CapSkill skill = GameCache.getCapSkill(capSkillId);
		List<CapSkillEffect> capSkillEffs = skill == null ? new ArrayList<CapSkillEffect>() : skill.getEffects();
		DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_关卡产出概率翻倍, chapter.getSiteId());
		for (ChapterRound round : rounds) {
			// 每个回合一条记录
			FightRoundResultVO resultVO = MessageFactory.getMessage(FightRoundResultVO.class);

			List<MonsterAwardVO> mgoods = new ArrayList<MonsterAwardVO>();
			String[] monsterIds = round.getMonsters().split(ChapterRound.MONSTERS_SPLIT);
			for (int pos = 0; pos < 6; pos++) {
				int monsterId = Integer.valueOf(monsterIds[pos]);
				if (monsterId == 0) {
					continue;
				}
				Monster mon = GameCache.getMonster(monsterId);
				if (mon == null) {
					throw new GameException(GameException.CODE_参数错误, "monster no found." + monsterId);
				}
				List<Drop> drops = GameCache.getDrops(mon.getDropId());
				initMonsterAward(cf, mgoods, monsterId, pos, round.getRound(), drops, capSkillEffs, drm, user);
				cf.getMonsters().add(monsterId);
			}
			resultVO.setMonsterAwards(mgoods);
			results.add(resultVO);
		}
		// 计算经验
		int multiple = getMultiple(drm, Goods.TYPE_EXP); // 经验翻倍活动
		int exp = chapter.getExp() * multiple;
		for (CapSkillEffect effect : capSkillEffs) {
			if (effect.getEffectType() == SkillEffect.EFFETC_TYPE_经验增长) {
				exp *= ((1f + effect.getAddPercent()));
				exp += effect.getAddPoint();
			}
		}
		cf.setExp(exp);
		return results;
	}

	private void getJoinMonsterGoods(int chapterId, CurrFight cf, List<Goods> mayGoods) {
		cf.setJoinGoods(new ArrayList<Goods>());
		ChapterJoin join = GameCache.getChapterJoinMap().get(chapterId);
		if (join == null) {
			return;
		}
		// 乱入上限为10
		UserStat stat = userStatDAO.queryUserStat(cf.getUserId());
		if (stat.getChpaterJoinCount() >= UserStat.MAX_乱入_次数) {
			return;
		}
		boolean isStart = activityService.activityIsStart(ActivityDefine.DEFINE_ID_乱入概率翻倍);
		int multiple = 1;
		if (isStart) {
			Chapter chapter = GameCache.getChapter(chapterId);
			DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_乱入概率翻倍, chapter.getSiteId());
			if (drm != null) {
				multiple = multiple * drm.getMultiple();
			}
		}
		if (Math.random() < join.getRate() * multiple) {
			cf.setJoinMonsters(join.getMonster());
			stat.setChpaterJoinCount(stat.getChpaterJoinCount() + 1);
			UserStatOpt dbopt = new UserStatOpt();
			dbopt.chapterJoinCount = SQLOpt.EQUAL;
			userStatDAO.updateUserStat(dbopt, stat);
			List<Drop> drops = GameCache.getDrops(join.getDropId());
			for (Drop drop : drops) {
				Goods good = Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1);
				mayGoods.add(good);
			}
			cf.getJoinGoods().addAll(rateDropGoods(drops));
		}
	}

	/**
	 * 根据掉落活动配置的地点掉落的概率翻倍
	 * 
	 * @param siteId
	 * @param goodsType
	 * @return 某个地点掉落的某个物品的概率翻倍
	 */
	// public int getMultiple(int siteId,int goodsType){
	// int multiple=1;
	// DropRateMultiple
	// drm=chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_关卡产出概率翻倍,siteId);
	// if(drm!=null){
	// if(drm.getDefineId()==ActivityDefine.DEFINE_ID_关卡产出概率翻倍){
	// boolean isStart =activityService.activityIsStart(drm.getDefineId());
	// if(isStart && drm.getGoodsType()==goodsType){
	// multiple=drm.getMultiple();
	// }
	// }
	// }
	//
	// return multiple;
	// }
	/**
	 * 根据掉落活动配置的地点掉落的概率翻倍
	 * 
	 * @param drm
	 * @param goodsType
	 * @return 某个地点掉落的某个物品的概率翻倍
	 */
	public int getMultiple(DropRateMultiple drm, int goodsType) {
		int multiple = 1;
		if (drm != null) {
			if (drm.getDefineId() == ActivityDefine.DEFINE_ID_关卡产出概率翻倍) {
				boolean isStart = activityService.activityIsStart(drm.getDefineId());
				if (isStart && drm.getGoodsType() == goodsType) {
					multiple = drm.getMultiple();
				}
			}
		}
		return multiple;
	}

	private void initMonsterAward(CurrFight cf, List<MonsterAwardVO> monsterAwards, int monsterId, int pos, int round, List<Drop> drops, List<CapSkillEffect> capSkillEffs, DropRateMultiple drm,
			User user) {

		Monster monster = GameCache.getMonster(monsterId);
		MonsterAwardVO mongift = MessageFactory.getMessage(MonsterAwardVO.class);
		List<GoodsVO> awardsVO = new ArrayList<GoodsVO>();
		List<Goods> awards = new ArrayList<Goods>();

		double all = 0d;
		if (drops == null) {
			drops = new ArrayList<Drop>();
		}
		for (Drop drop : drops) {
			all += drop.getRate();
		}
		// 概率总合为1则必给一个
		if (all == 1d) {
			double random = Math.random();
			double current = 0d;
			for (Drop drop : drops) {
				int multiple = getMultiple(drm, drop.getType());
				if (drop.getType() != Goods.TYPE_GOLD) {
					current += drop.getRate() * multiple;// 开启概率掉落活动时候奖励物品不为金币的时候掉落概率翻倍
				} else {
					current += drop.getRate();
				}

				// 队长技能加成
				for (CapSkillEffect effect : capSkillEffs) {
					if (drop.getType() == Goods.TYPE_STUFF && effect.getEffectType() == SkillEffect.EFFETC_TYPE_材料掉落) {
						current += effect.getAddPercent();

					} else if (drop.getType() == Goods.TYPE_SOUL && effect.getEffectType() == SkillEffect.EFFETC_TYPE_战魂掉落) {
						current += effect.getAddPercent();

					} else if (drop.getType() == Goods.TYPE_GOLD && effect.getEffectType() == SkillEffect.EFFETC_TYPE_金币掉率) {
						current += effect.getAddPercent();
					}
				}
				if (current > random) {
					if (drop.getType() == Goods.TYPE_BOX) {
						// 服务端记录
						Box box = GameCache.getExistBox(drop.getAssId());
						cf.getBoxes().add(box);
						// 用户奖励
						BoxVO boVo = MessageFactory.getMessage(BoxVO.class);
						boVo.init(box);
						mongift.setBox(boVo);
						break;
					}
					if (drop.getType() != Goods.TYPE_GOLD) {
						awards.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1));
					} else {
						// 开启概率掉落活动时候奖励物品为金币的时候掉落的金币数量翻倍
						awards.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum() * multiple, 1));
					}
					break;
				}
			}
		} else {
			List<Goods> tmpgood = new ArrayList<>();
			for (Drop drop : drops) {
				int multiple = getMultiple(drm, drop.getType());
				// if(user.getGuideStep()==User.GUIDE_STEP21_指引END){
				// multiple=getMultiple(drm,drop.getType());
				// }
				double add = 0d;
				for (CapSkillEffect effect : capSkillEffs) {
					if (drop.getType() == Goods.TYPE_STUFF && effect.getEffectType() == SkillEffect.EFFETC_TYPE_材料掉落) {
						add = effect.getAddPercent();

					} else if (drop.getType() == Goods.TYPE_SOUL && effect.getEffectType() == SkillEffect.EFFETC_TYPE_战魂掉落) {
						add = effect.getAddPercent();

					} else if (drop.getType() == Goods.TYPE_GOLD && effect.getEffectType() == SkillEffect.EFFETC_TYPE_金币掉率) {
						add = effect.getAddPercent();
					}
				}
				double random = Math.random();
				double current = 0d;
				if (drop.getType() != Goods.TYPE_GOLD) {
					current += drop.getRate() * multiple;// 开启概率掉落活动时候奖励物品不为金币的时候掉落概率翻倍
				} else {
					current += drop.getRate();
				}
				if (current + add > random) {
					// 宝箱特别处理
					if (drop.getType() == Goods.TYPE_BOX) {
						// 服务端记录
						Box box = GameCache.getBox(drop.getAssId());
						cf.getBoxes().add(box);
						// 用户奖励
						BoxVO boVo = MessageFactory.getMessage(BoxVO.class);
						boVo.init(box);
						mongift.setBox(boVo);
						tmpgood.clear();
						break;
					}
					if (drop.getType() != Goods.TYPE_GOLD) { // 开启概率掉落活动时候奖励物品不为金币的时候掉落概率翻倍
						tmpgood.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1));
					} else {
						tmpgood.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum() * multiple, 1));// 开启概率掉落活动时候奖励物品不为金币的时候掉落的金币数量翻倍
					}

				}
			}
			awards.addAll(tmpgood);
		}
		cf.getAwards().addAll(awards);

		// result
		for (Goods a : awards) {
			GoodsVO g = MessageFactory.getMessage(GoodsVO.class);
			g.init(a);
			awardsVO.add(g);
		}
		mongift.setMonsterId(monster.getMonsterId());
		mongift.setAwards(awardsVO);
		mongift.setPos(pos);
		mongift.setRound(round);
		monsterAwards.add(mongift);
	}

	@Override
	public FightEndResultVO endFight(int userId, boolean pass, boolean hasJoin) {
		FightEndResultVO fr = null;
		if (pass) {
			if(checkFightService.isPassCheck(userId, BattleType.PVE)){
				checkFightService.clearPassCheck(userId);
			}else{
				throw new GameException(GameException.CODE_参数错误, "fight check no pass");
			}
			
			CurrFight cf = userChapterDAO.getCurrFight(userId);
			if (cf == null) {
				throw new GameException(GameException.CODE_参数错误, "CurrFight is null");
			}
			
			Chapter chapter = GameCache.getChapter(cf.getChapterId());
			
			UserCap cap = userTeamDAO.getUserCapCache(userId);
			if (cf.getChapterId() < Chapter.CHAPTER_ID_副本_START) {
				if (cf.getChapterId() > cap.getCurrChapterId()) {
					cap.setCurrChapterId(cf.getChapterId());
					userTeamDAO.updateUserCapCache(cap);

					// 更新章节排行榜，推图
					userDAO.updateChapterRank(userId, cf.getChapterId());
				}
			}
			User user = userService.getExistUserCache(userId);
			UserChapter ud = userChapterDAO.queryUserChapter(user.getUserId(), cf.getChapterId());
			// 判断副本挑战次数是否满足
			if (chapter.getFightCount() != 0) {
				if (ud.getSameDayCount() >= chapter.getFightCount() + ud.getSameDayBuyCount()) {
					throw new GameException(GameException.CODE_挑战次数不足, "chapter left flight count is 0");
				}
			}
			
			fr = MessageFactory.getMessage(FightEndResultVO.class);
			fr.setUserGoodses(new ArrayList<UserGoodsVO>());
			fr.setUserSouls(new ArrayList<UserSoulVO>());
			fr.setBakProp(new ArrayList<UserBakPropVO>());
			List<Goods> itemGoods = new ArrayList<Goods>();
			// 乱入奖励
			if (hasJoin) {
				cf.getAwards().addAll(cf.getJoinGoods());
			}
			for (Goods goods : cf.getAwards()) {
				switch (goods.getType()) {
				case Goods.TYPE_SOUL:
					for(int i=1; i<=goods.getNum(); i++){
						UserSoul us = userSoulService.addUserSoul(user, goods.getGoodsId(), 1, LoggerType.TYPE_战斗获得);
						UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
						vo.init(us);
						fr.getUserSouls().add(vo);
					}
					break;
				case Goods.TYPE_PROP:
					itemGoods.add(goods);
					break;
				case Goods.TYPE_STUFF:
					itemGoods.add(goods);
					break;
				case Goods.TYPE_EQUIPMENT:
					itemGoods.add(goods);
					break;
				case Goods.TYPE_GOLD:
					userService.incrementGold(user, goods.getNum(), LoggerType.TYPE_战斗获得, cf.getChapterId() + "");
					break;
				case Goods.TYPE_CURRENCY:
					userService.incrementCurrency(user, goods.getNum(), LoggerType.TYPE_战斗获得, cf.getChapterId() + "");
					break;
				case Goods.TYPE_FRIENDLY_POINT:
					userService.incrementFriendlyPoint(user.getUserId(), goods.getNum(), LoggerType.TYPE_战斗获得, cf.getChapterId() + "");
					break;
				case Goods.TYPE_BAK_PROP:
					userGoodsService.addBakProps(user, goods.getGoodsId(), goods.getNum(), LoggerType.TYPE_战斗得到, "");
					UserBakProp prop = UserBakProp.create(user.getUserId(), goods.getGoodsId(), goods.getNum());
					UserBakPropVO propVo = MessageFactory.getMessage(UserBakPropVO.class);
					propVo.init(prop);
					fr.getBakProp().add(propVo);
					break;
				default:
					break;
				}
			}
			if (cf.getFriendlyPoint() > 0) {
				userService.incrementFriendlyPoint(userId, cf.getFriendlyPoint(), LoggerType.TYPE_FIGHT, "");
			}
			userService.incrementExp(user, cf.getExp(), LoggerType.TYPE_战斗获得, cf.getChapterId() + "", cap);

			if (cf.getFriendId() != 0) {
				UserStat stat = userStatDAO.queryUserStat(userId);
				if (stat.getWinFriend() != null) {
					stat.getWinFriend().add(cf.getFriendId());
				}
				UserStatOpt opt = new UserStatOpt();
				opt.winFriend = SQLOpt.EQUAL;
				userStatDAO.updateUserStat(opt, stat);
			}
			fr.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), itemGoods, LoggerType.TYPE_战斗获得, ""));

			fr.setCurrency(user.getCurrency());
			fr.setExp(user.getExp());
			fr.setGold(user.getGold());
			fr.setLevel(user.getLevel());
			fr.setFriendlyPoint(userStatDAO.queryUserStat(userId).getFriendlyPoint());
			// fr.setStamina(user.getStamina());
			// fr.setLastRegainStaminaTime(user.getLastRegainStaminaTime().getTime());
			
			ud.setPassCount(ud.getPassCount() + 1);
			ud.setSameDayCount(ud.getSameDayCount() + 1);
			
			userChapterDAO.updateUserChapter(ud);

			// 关卡成就
			userAchieveService.addUserAchieveProcess(userId, Achieve.TYPE_关卡, cf.getChapterId(), 1);

			int monsterNum = 0; // 做任务击杀任意怪物数量
			// int designationMonsterNum=0;//指定怪物数量
			// GameCache.getMissionConditionList(missionId)
			if (cf.getChapterId() < Chapter.CHAPTER_ID_副本_START) { // 排除副本任务
				userMissionService.finishMissionCondition(user, MissionCondition.TYPE_闯关次数_不限关卡, 0, 1);
				// userMissionService.finishMissionCondition(user,
				// MissionCondition.TYPE_闯关次数_限某一关卡, cf.getChapterId(), 1);
				List<ChapterRound> rounds = GameCache.getChapterRounds(cf.getChapterId());
				// 击杀怪物任务
				for (ChapterRound round : rounds) {
					String[] monsterIds = round.getMonsters().split(ChapterRound.MONSTERS_SPLIT);
					for (int i = 0; i < 6; i++) {
						int monsterId = Integer.valueOf(monsterIds[i]);
						if (monsterId == 0) {
							continue;
						}
						monsterNum += 1;
						// userMissionService.finishMissionCondition(user,
						// MissionCondition.TYPE_怪物击杀数量_限某一怪物, monsterId, 1);
					}

				}
				userMissionService.finishMissionCondition(user, MissionCondition.TYPE_怪物击杀数量_不限怪物, 0, monsterNum);
			}

		}
		userChapterDAO.delCurrFightCache(userId);
		return fr;
	}

	@Override
	public MonsterAwardVO openBox(int userId, int boxId) {
		MonsterAwardVO retAward = MessageFactory.getMessage(MonsterAwardVO.class);
		CurrFight cf = userChapterDAO.getCurrFight(userId);
		if (cf == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		Box box = cf.getBox(boxId);
		List<Drop> drops = GameCache.getDrops(box.getDropId());
		List<Goods> retGoods = new ArrayList<Goods>();
		Monster monster = null;
		double random = Math.random();
		double current = 0d;
		for (Drop drop : drops) {
			current += drop.getRate();
			if (current > random) {
				if (drop.getType() == Goods.TYPE_MONSTER) {
					// 服务端记录
					monster = GameCache.getMonster(drop.getAssId());
					// 用户奖励
					cf.getMonsters().add(monster.getMonsterId());
					break;
				}
				Goods goods = Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1);
				retGoods.add(goods);
				break;
			}
		}
		// 掉怪其他的不掉
		if (monster != null) {
			retGoods.clear();
			retAward.setMonsterId(monster.getMonsterId());
			retAward.setPos(3);
			retAward.setRound(0);
			List<Drop> monsterDrops = GameCache.getDrops(monster.getDropId());
			double monsterCurr = 0d;
			for (Drop drop : monsterDrops) {
				monsterCurr += drop.getRate();
				if (monsterCurr > random) {
					if (drop.getType() == Goods.TYPE_MONSTER) {
						continue;
					}
					Goods goods = Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1);
					retGoods.add(goods);
				}
			}
		}
		cf.getBoxes().remove(box);
		for (Goods ret : retGoods) {
			GoodsVO goodsVO = MessageFactory.getMessage(GoodsVO.class);
			goodsVO.init(ret);
			retAward.addGoods(goodsVO);
			cf.getAwards().add(ret);
		}
		retAward.setBox(null);
		userChapterDAO.updateCurrFightCache(cf);
		return retAward;
	}

	@Override
	public int resurrection(int userId) {
		CurrFight fight = userChapterDAO.getCurrFight(userId);
		User user = userService.getExistUser(userId);
		int resurrectionNum = fight.getResurrectionNum();
		int costCurrency = 0;
		
		// 复活消耗魂钻规则
		if (resurrectionNum == 0) {
			costCurrency = 10;
		} else if (resurrectionNum == 1) {
			costCurrency = 20;
		} else if (resurrectionNum == 2) {
			costCurrency = 40;
		} else if (resurrectionNum == 3) {
			costCurrency = 80;
		} else {
			costCurrency = 160;
		}
		
		fight.setResurrectionNum(resurrectionNum + 1);
		userChapterDAO.updateCurrFightCache(fight);
		userService.decrementCurrency(user, costCurrency, LoggerType.TYPE_复活, "" + resurrectionNum);
		return user.getCurrency();
	}

	/**
	 * 如果该据点是【副本活动】需要检查钥匙BUFF是否过期
	 * @param chapter
	 * @param userId
	 */
	private void bakChapter(Chapter chapter, int userId) {
		// 副本地点
		if (chapter.getSiteId() < 100) {
			ActivityDefine de = null;
			List<ActivityDefine> ads = activityService.getStartingAc();
			for (ActivityDefine ad : ads) {
				if (ad.getChapterIds().contains(chapter.getChapterId() + "")) {
					de = ad;
					break;
				}
			}
			if (de == null) {
				throw new GameException(GameException.CODE_活动未开启, "no activity chapter." + chapter.getSiteId());
			}
			Integer value = Chapter.getChapterKeyProp(chapter.getChapterId());
			if (value != null && userBuffService.buffOffTime(userId, UserBuff.BUFF_ID_副本钥匙, value)) {
				throw new GameException(GameException.CODE_没有副本道具, " user not user prop." + value + " chpater." + chapter.getSiteId());
			}
		}
	}

	@Override
	public List<UserBuffVO> userBakChapterProp(int userId, int propId) {
		userGoodsService.useBakProps(userId, propId, 1, LoggerType.TYPE_进入副本, "");
		long endMins = Calendar.getInstance().getTimeInMillis() + UserBuff.TIME_ONE_HOUR;
		UserBuff addbuff = UserBuff.create(userId, UserBuff.BUFF_ID_副本钥匙, propId, new Date(endMins));
		userBuffService.addUserBuff(userId, addbuff, UserBuff.TIME_ONE_HOUR);

		List<UserBuff> buffs = userBuffDAO.getUserBuffs(userId);
		List<UserBuffVO> vos = new ArrayList<UserBuffVO>();
		Calendar c = Calendar.getInstance();
		for (UserBuff buff : buffs) {
			// 没有过期的发到前端
			if (buff.getEndTime().after(c.getTime())) {
				UserBuffVO vo = MessageFactory.getMessage(UserBuffVO.class);
				vo.init(buff);
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public FightEndResultVO sweep(int userId, int chapterId, int count, byte teamId) {
		User user = userService.getExistUserCache(userId);
		UserStat stat = userStatDAO.queryUserStat(userId);
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		UserBuff vifBuffer = userBuffDAO.getUserBuff(userId, UserBuff.BUFF_ID_赠送vip);
		if (vip == null && vifBuffer == null) {
			throw new GameException(GameException.CODE_不是vip, "not vip privilege");
		}
		if (vip == null && vifBuffer != null) {
			if (vifBuffer.getEndTime().getTime() < System.currentTimeMillis()) {
				throw new GameException(GameException.CODE_不是vip, "not vip privilege");
			}

		}
		if (vifBuffer != null) { // 有免费vip权限
			if (stat.getSweepCount() + count > 10) { // 免费的vip1 有十次免费扫荡机会
				throw new GameException(GameException.CODE_扫荡次数不足, "not sweep count");
			}
		}
		if (vip != null) {
			if (stat.getSweepCount() + count > vip.getEverydaySweepCount() + stat.getBuySweepCount()) { // 判断vip扫荡次数是否足够
				throw new GameException(GameException.CODE_扫荡次数不足, "not sweep count");
			}
			if (stat.getSweepCount() == vip.getEverydaySweepCount() + vip.getBuySweepCount()) {
				throw new GameException(GameException.CODE_今日扫荡次数已经用完, "not sweep");
			}
		}
		userSoulService.checkSoulFull(user);
		Chapter chapter = GameCache.getChapter(chapterId);
		if (chapter == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (count > 10 || count < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		UserChapter ud = userChapterDAO.queryUserChapter(userId, chapterId);
		if (ud == null || ud.getPassCount() == 0) {
			throw new GameException(GameException.CODE_此章节没有通关不能进行扫荡, " not sweep");
		}
		// 判断副本挑战次数是否满足
		if (chapter.getFightCount() != 0) {
			if (ud.getSameDayCount() + count > chapter.getFightCount() + ud.getSameDayBuyCount()) {
				throw new GameException(GameException.CODE_挑战次数不足, "chapter left flight count is 0");
			}
		}
		ud.setPassCount(ud.getPassCount() + count);
		ud.setSameDayCount(ud.getSameDayCount() + count);
		userChapterDAO.updateUserChapter(ud);
		
		userService.checkStamina(user); // 检查体力
		boolean isStart = activityService.activityIsStart(ActivityDefine.DEFINE_ID_体力消耗减半);
		int stamina = chapter.getStamina();
		if (isStart) { // 检查体力减半活动开启没有
			DropRateMultiple drm = chapterDAO.getDropRateMultipleCache(ActivityDefine.DEFINE_ID_体力消耗减半, chapter.getSiteId());
			if (drm != null) {
				stamina = chapter.getStamina() / drm.getMultiple();
			}
		}
		userService.decrementStamina(user, stamina * count, LoggerType.TYPE_扫荡消耗, chapterId + ""); // 扣减体力
		List<UserSoulInfoVO> soulInfos = new ArrayList<UserSoulInfoVO>();
		Backage backage = userGoodsService.getPackage(userId);
		userGoodsService.checkBackageFull(backage, user);
		UserTeam team = userTeamService.getExistUserTeamCache(userId, teamId);
		// 战魂
		for (long userSoulId : team.getPos()) {
			if (userSoulId != 0) {
				UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
				UserSoulInfoVO vo = userSoulService.gainUserSoulInfo(soul, backage.getUseGoodses().values());
				soulInfos.add(vo);
			}
		}
		userTeamService.checkUserTeam(user, soulInfos);
		UserStatOpt opt = new UserStatOpt();
		opt.sweepCount = SQLOpt.EQUAL;
		opt.sweepSoulNum = SQLOpt.EQUAL;
		stat.setSweepCount(stat.getSweepCount() + count);

		List<Goods> goods = new ArrayList<>();
		List<ChapterRound> rounds = GameCache.getChapterRounds(chapterId);
		DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_关卡产出概率翻倍, chapter.getSiteId());
		// 计算经验
		int multiple = getMultiple(drm, Goods.TYPE_EXP); // 经验翻倍活动
		int exp = chapter.getExp() * count * multiple;
		
		Map<Integer, Integer> multipleMap = new HashMap<>();
		
		for (int i = 0; i < count; i++) {
			for (ChapterRound round : rounds) {
				String[] monsterIds = round.getMonsters().split(ChapterRound.MONSTERS_SPLIT);
				for (int pos = 0; pos < 6; pos++) {
					int monsterId = Integer.valueOf(monsterIds[pos]);
					if (monsterId == 0) {
						continue;
					}
					Monster mon = GameCache.getMonster(monsterId);
					List<Drop> drops = GameCache.getDrops(mon.getDropId());
					goods.addAll(rateSweepDropGoods(drops, drm, multipleMap));
				}
			}
		}
		FightEndResultVO fr = null;
		fr = MessageFactory.getMessage(FightEndResultVO.class);

		fr.setUserGoodses(new ArrayList<UserGoodsVO>());
		fr.setUserSouls(new ArrayList<UserSoulVO>());
		List<Goods> itemGoods = new ArrayList<Goods>();
		int gold = 0;
		int currency = 0;
		int num = 0;
		for (Goods good : goods) {
			switch (good.getType()) {
			case Goods.TYPE_SOUL:
				num++;
				if (num <= count * 3) { // 一次扫荡最多三个战魂
					UserSoul us = userSoulService.addUserSoul(user, good.getGoodsId(), 1, LoggerType.TYPE_扫荡获得);
					UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
					vo.init(us);
					fr.getUserSouls().add(vo);
				}
				break;
			case Goods.TYPE_PROP:
				itemGoods.add(good);
				break;
			case Goods.TYPE_STUFF:
				itemGoods.add(good);
				break;
			case Goods.TYPE_EQUIPMENT:
				itemGoods.add(good);
				break;
			case Goods.TYPE_GOLD:
				userService.incrementGold(user, good.getNum(), LoggerType.TYPE_扫荡获得, chapterId + "");
				gold += good.getNum();
				break;
			case Goods.TYPE_CURRENCY:
				userService.incrementCurrency(user, good.getNum(), LoggerType.TYPE_扫荡获得, chapterId + "");
				currency += good.getNum();
				break;
			/*
			 * case Goods.TYPE_EXP: userService.incrementExp(user,
			 * goods.getNum(), ExpLogger.TYPE_战斗获得, cf.getChapterId()+"");
			 * break;
			 */
			// case Goods.TYPE_FRIENDLY_POINT:
			// userService.incrementFriendlyPoint(user.getUserId(),
			// goods.getNum(), FriendlyPointLogger.TYPE_战斗获得,
			// cf.getChapterId()+"");
			// break;
			default:
				break;
			}
		}
		userStatDAO.updateUserStat(opt, stat);
		SweepCountLogger logger = SweepCountLogger.createSweepCountLogger(user.getUserId(), LoggerType.TYPE_消耗扫荡次数, -count, 0, " sweep count");
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
		fr.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), itemGoods, LoggerType.TYPE_扫荡获得, ""));
		userService.incrementExp(user, exp, LoggerType.TYPE_扫荡获得, chapterId + "", null);
		// fr.setExp(exp);
		fr.setCurrency(currency);
		fr.setExp(user.getExp());
		fr.setGold(gold);
		fr.setLevel(user.getLevel());
		fr.setFriendlyPoint(userStatDAO.queryUserStat(userId).getFriendlyPoint());
		fr.setStamina(user.getStamina());
		fr.setLastRegainStaminaTime(user.getLastRegainStaminaTime().getTime());
		return fr;
	}

	private List<Goods> rateDropGoods(List<Drop> drops) {
		List<Goods> goods = new ArrayList<>();
		double all = 0d;
		if (drops == null) {
			return goods;
		}
		for (Drop drop : drops) {
			all += drop.getRate();
		}
		// 概率总合为1则必给一个
		if (all == 1d) {
			double random = Math.random();
			double current = 0d;
			for (Drop drop : drops) {
				current += drop.getRate();
				// 不能出现箱子
				if (current > random && drop.getType() != Goods.TYPE_BOX) {
					goods.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1));
					break;
				}
			}
		} else {
			for (Drop drop : drops) {
				double random = Math.random();
				if (drop.getRate() > random) {
					// 宝箱特别处理
					if (drop.getType() != Goods.TYPE_BOX) {
						goods.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1));

					}
				}
			}
		}
		return goods;
	}

	private List<Goods> rateSweepDropGoods(List<Drop> drops, DropRateMultiple drm, Map<Integer, Integer> multipleMap) {
		List<Goods> goods = new ArrayList<>();
		double all = 0d;
		if (drops == null) {
			return goods;
		}
		for (Drop drop : drops) {
			all += drop.getRate();
		}
		// 概率总合为1则必给一个
		if (all == 1d) {
			double random = Math.random();
			double current = 0d;
			for (Drop drop : drops) {
				Integer multiple = multipleMap.get(drop.getType());
				if(multiple == null){
					multiple = getMultiple(drm, drop.getType());
					multipleMap.put(drop.getType(), multiple);
				}
				
				if (drop.getType() != Goods.TYPE_GOLD) {
					current += drop.getRate() * multiple; // 开启概率掉落活动时候奖励物品不为金币的时候掉落概率翻倍
				} else {
					current += drop.getRate();
				}
				// 不能出现箱子
				if (current > random && drop.getType() != Goods.TYPE_BOX) {
					if (drop.getType() != Goods.TYPE_GOLD) {
						goods.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1));
					} else {
						goods.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum() * multiple, 1)); // 开启概率掉落活动时候奖励物品为金币的时候掉落的金币数量翻倍
					}
					break;
				}
			}
		} else {
			for (Drop drop : drops) {
				double current = 0d;
				double random = Math.random();
				Integer multiple = multipleMap.get(drop.getType());
				if(multiple == null){
					multiple = getMultiple(drm, drop.getType());
					multipleMap.put(drop.getType(), multiple);
				}
				if (drop.getType() != Goods.TYPE_GOLD) {
					current += drop.getRate() * multiple; // 开启概率掉落活动时候奖励物品不为金币的时候掉落概率翻倍
				} else {
					current += drop.getRate();
				}
				if (current > random) {
					// 宝箱特别处理
					if (drop.getType() != Goods.TYPE_BOX) {
						if (drop.getType() != Goods.TYPE_GOLD) {
							goods.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum(), 1));
						} else {
							goods.add(Goods.create(drop.getAssId(), drop.getType(), drop.getNum() * multiple, 1)); // 开启概率掉落活动时候奖励物品为金币的时候掉落的金币数量翻倍
						}
						break;

					}
				}
			}
		}
		return goods;
	}

	@Override
	public List<UserChapterVO> queryUserActivityChapter(int userId) {
		List<ActivityDefineVO> acs = activityService.getStartingAcVo();
		Set<String> set = new TreeSet<String>();
		for (ActivityDefineVO vo : acs) {
			if (vo.getChapterIds() != null && vo.getChapterIds().length() > 0) {
				String[] strs = vo.getChapterIds().split(ActivityDefine.WEEK_SPLIT);
				for (String id : strs) {
					set.add(id);
				}
			}
		}
		List<UserChapter> ucs = userChapterDAO.queryUserChapters(userId, set);
		List<UserChapterVO> ucvos = new ArrayList<>();
		for (UserChapter uc : ucs) {
			UserChapterVO vo = MessageFactory.getMessage(UserChapterVO.class);
			vo.init(uc, 0);
			ucvos.add(vo);
		}
		return ucvos;
	}
	
	@Override
	public List<Integer> getChapterChestRecrds(int userId) {
		List<Integer> list = new ArrayList<>();
		
		List<ChapterChestRecord> _list = chapterChestRecordDAO.queryChapterChestRecord(userId);
		if (_list == null || _list.isEmpty()) {
			return list;
		}
		
		for (int i=0; i<_list.size(); i++) {
			list.add(_list.get(i).getChapterId());
		}
		return list;
	}
	
	@Override
	public UserAwardVO getChapterChestAward(int userId, int chapterId) {
		List<Award> list = GameCache.getChapterChest(chapterId);
		if (list == null || list.isEmpty()) {
			throw new GameException(GameException.CODE_参数错误, String.format("can't find chapter chest award. userId=%s, chapterId=%s", userId, chapterId));
		}
		
		ChapterChestRecord record = chapterChestRecordDAO.findByUserIdChapterId(userId, chapterId);
		if (record != null) {
			throw new GameException(GameException.CODE_不能重复领取, String.format("can't get chest award again. userId=%s, chapterId=%s", userId, chapterId));
		}
		
		User user = userService.getExistUser(userId);
		record = new ChapterChestRecord();
		record.setUserId(userId);
		record.setChapterId(chapterId);
		record.setCreateTime(new Date());
		chapterChestRecordDAO.add(record);
		List<Goods> goodsList = AwardUtils.getGoodsList(list);
	    return goodsService.getUserAwardVO(goodsList, user, LoggerType.TYPE_冒险宝箱, "chest");
    }

	@Override
    public List<UserChapterVO> queryUserChapterList(int userId) {
		List<UserChapter> list = userChapterDAO.queryUserFBChapterList(userId);
		List<UserChapterVO> listVo = new ArrayList<>();
		for (UserChapter uc : list) {
			UserChapterVO vo = MessageFactory.getMessage(UserChapterVO.class);
			Chapter chapter = GameCache.getChapter(uc.getChapterId());
			if (chapter != null) {
				vo.init(uc, chapter.getSiteId());
				listVo.add(vo);
			}
		}
		return listVo;
    }

	@Override
    public UserChapterVO buyChapterFightCount(int userId, int chapterId) {
		User user = userService.getExistUser(userId);
		UserChapter uc = userChapterDAO.queryUserChapter(userId, chapterId);
		if (uc == null) {
			uc = new UserChapter();
			uc.setChapterId(chapterId);
			uc.setCreateTime(new Date());
			uc.setPassCount(0);
			uc.setSameDayBuyCount(1);
			uc.setSameDayCount(0);
			uc.setUserId(userId);
			uc.setUpdateTime(new Date());
			userChapterDAO.addUserChapter(uc);
		} else {
			if (uc.getSameDayBuyCount() >= Chapter.FB_FIGHT_BUY_COUNT) {
				throw new GameException(GameException.CODE_副本购买次数不足, String.format("beyond buy chapter fight count. userId=%s, chapterId=%s", userId, chapterId));
			}
			
			uc.setSameDayBuyCount(uc.getSameDayBuyCount() + 1);
			userChapterDAO.updateUserChapter(uc);
		}
		userService.decrementCurrency(user, Chapter.FB_FIGHT_BUY_COUNT_PRICE, LoggerType.TYPE_购买副本战斗次数, "fight");
		
		UserChapterVO vo = MessageFactory.getMessage(UserChapterVO.class);
		Chapter chapter = GameCache.getChapter(uc.getChapterId());
		vo.init(uc, chapter.getSiteId());
	    return vo;
    }
}
