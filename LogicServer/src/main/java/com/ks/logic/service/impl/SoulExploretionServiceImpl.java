package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.SoulExploretionService;
import com.ks.model.explora.ExplorationAward;
import com.ks.model.explora.ExplorationAwardExp;
import com.ks.model.explora.SoulExploretion;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.MissionCondition;
import com.ks.model.soul.Soul;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.model.vip.VipPrivilege;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.explora.SoulExploretionVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.util.RandomUtil;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * 
 * @version 创建时间：2014年8月8日 下午6:01:20 类说明
 */
public class SoulExploretionServiceImpl extends BaseService implements SoulExploretionService {

	@Override
	public SoulExploretionVO addSoulExploretion(int userId, long userSoulId, int index, int teamId) {
		User user = userService.getExistUserCache(userId);
		Backage backage = userGoodsService.getPackage(userId);
		userGoodsService.checkBackageFull(backage, user);
		if (index < 0 || index > 5) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (teamId < 1 || teamId > 3) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (user.getLevel() < 10) {
			throw new GameException(GameException.CODE_等级不够, " not leve");
		}
		List<SoulExploretion> list = soulExplorationDAO.querSoulExploretionList(userId);
		if (user.getLevel() < 30) {
			if (list.size() >= 1) {
				throw new GameException(GameException.CODE_该等级已达探索上线, " not exploretion");
			}

		} else if (user.getLevel() < 60) {
			if (list.size() >= 2) {
				throw new GameException(GameException.CODE_该等级已达探索上线, " not exploretion");
			}
		} else {
			if (list.size() >= 3) {
				throw new GameException(GameException.CODE_该等级已达探索上线, " not exploretion");
			}
		}

		int hour = ExplorationAward.HOUE_TIME[index - 1];
		UserSoul userSoul = userSoulService.getExistUserSoulCache(userId, userSoulId);
		if (getNotByTeam(userSoul.getSoulSafe())) {
			throw new GameException(GameException.CODE_该战魂在队伍中不可以探索, " usersoul is team not  exploretion");
		}
		if ((userSoul.getSoulSafe() & UserSoul.SOUL_SAFE_探索状态中) != 0) {
			throw new GameException(GameException.CODE_该战魂在队伍中不可以探索, " usersoul is team not  exploretion");
		}
		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		// userService.decrementGold(user, ExplorationAward.getHourGold(hour),
		// GoldLogger.TYPE_探索消耗, "exploration");
		SoulExploretion se = new SoulExploretion();
		se.setUserId(userId);
		se.setSoulRare(soul.getSoulRare());
		se.setHourTime(hour);
		se.setSoulId(userSoulId);
		se.setStartTime(new Date());
		se.setEndTime(new Date());
		se.setTeamId(teamId);

		// 探索任务
		// userMissionService.finishMissionCondition(user,
		// MissionCondition.TYPE_探索次数, 0, 1);
		soulExplorationDAO.addSoulExploretion(se);
		userSoul.setSoulSafe(userSoul.getSoulSafe() | UserSoul.SOUL_SAFE_探索状态中);
		userSoulDAO.updateUserSoulCache(userSoul);
		SoulExploretionVO vo = MessageFactory.getMessage(SoulExploretionVO.class);
		vo.init(se);
		vo.setGold(user.getGold());
		return vo;
	}

	public boolean getNotByTeam(int soulSafe) {
		boolean flag = false;
		for (int i = 0; i < UserSoul.SOUL_SAFE_IN_TEAM.length; i++) {
			if ((UserSoul.SOUL_SAFE_IN_TEAM[i] & soulSafe) != 0) {
				flag = true;
				break;
			}

		}
		return flag;
	}

	@Override
	public List<ExplorationAward> querAwardList() {
		return soulExplorationCfgDAO.queryExplorationAward();
	}

	@Override
	public List<SoulExploretionVO> getSoulExploretionList(int userId) {
		List<SoulExploretionVO> listVO = new ArrayList<SoulExploretionVO>();
		List<SoulExploretion> list = soulExplorationDAO.querSoulExploretionList(userId);
		if (!list.isEmpty()) {
			for (SoulExploretion se : list) {
				SoulExploretionVO vo = MessageFactory.getMessage(SoulExploretionVO.class);
				vo.init(se);
				listVO.add(vo);
			}
		}
		return listVO;
	}

	@Override
	public FightEndResultVO exploretionAward(int userId, long soulId) {
		User user = userService.getExistUserCache(userId);
		// Backage backage = userGoodsService.getPackage(userId);
		// userGoodsService.checkBackageFull(backage, user);

		SoulExploretion se = soulExplorationDAO.querSoulExploretion(userId, soulId);
		if (se == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		ExplorationAwardExp awardExp = GameCache.getExplorationAwadExp(se.getHourTime(), se.getSoulRare());
		// ExplorationAwardNum awardNum=
		// GameCache.getExplorationAwadNum(se.getHourTime(), se.getSoulRare());
		if (awardExp == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		long now = System.currentTimeMillis();
		long oneHour = 60 * 60 * 1000L;
		long finishTime = se.getStartTime().getTime() + se.getHourTime() * oneHour;

		// vip 减少探索时间
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		long vipMS = 0;
		if (vip != null) {
			vipMS = (long) (se.getHourTime() * (1 - vip.getReduceExploreTime()) * 60 * 60 * 1000L);
		}

		if (now < finishTime - vipMS) {
			throw new GameException(GameException.CODE_探索时间没有到不可以领奖, "not time");
		}

		UserSoul userSoul = userSoulService.getExistUserSoulCache(userId, soulId);
		List<ExplorationAward> awardList = GameCache.getExplorationAwards(se.getHourTime(), se.getSoulRare());
		List<Goods> goodsList = new ArrayList<Goods>();
		FightEndResultVO fr = MessageFactory.getMessage(FightEndResultVO.class);
		int exp = 0; // 奖励战魂经验
		int gold = 0; // 奖励金币
		int point = 0;
		int awardNumGoods = 0;// 奖励物品次数
		long doubleAwardTime = se.getStartTime().getTime() + oneHour * se.getHourTime() + 60 * 1000L * 20;// 探索时间到了之后在等20分钟领取双倍经验
		long nowTime = System.currentTimeMillis();
		
		// 随机奖励物品的数量
		double randomNum = Math.random();
		if (randomNum <= awardExp.getRate1()) {
			awardNumGoods = awardExp.getAwardNum1();
		} else {
			awardNumGoods = awardExp.getAwardNum2();
		}
		
//		for (int i = 0; i < awardGoodsNum; i++) {
//			double random = Math.random();
//			double totalRate = 0;
//			for (ExplorationAward ex : awardList) {
//				totalRate += ex.getRate();
//				if (random <= totalRate) {
//					if (ex.getType() == Goods.TYPE_SOUL) {
//						for (int num = 1; num <= ex.getNum(); num++) {
//							userSoulService.checkSoulFull(user);
//							UserSoul us = userSoulService.addUserSoul(user, ex.getAssId(), ex.getLevel(), SoulLogger.TYPE_探索获得);
//							UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
//							vo.init(us);
//							fr.getUserSouls().add(vo);
//						}
//					} else if (ex.getType() == Goods.TYPE_GOLD) {
//						gold += ex.getNum();
//					} else if (ex.getType() == Goods.TYPE_CURRENCY) {
//						userService.incrementCurrency(user, ex.getNum(), CurrencyLogger.TYPE_探索获得, "");
//					} else if (ex.getType() == Goods.TYPE_FRIENDLY_POINT) {
//						point += ex.getNum();
//						userService.incrementFriendlyPoint(user.getUserId(), ex.getNum(), FriendlyPointLogger.TYPE_探索获得, "");
//					} else {
//						Goods goods = new Goods();
//						goods.setType(ex.getType());
//						goods.setGoodsId(ex.getAssId());
//						goods.setNum(ex.getNum());
//						goods.setLevel(ex.getLevel());
//						goodsList.add(goods);
//					}
//					break;
//				}
//
//			}
//		}
		
		for (int i = 0; i < awardNumGoods; i++) {
			ExplorationAward explorationAward = (ExplorationAward)RandomUtil.getRandom(awardList);
			if (explorationAward.getType() == Goods.TYPE_SOUL) {
				for (int num = 1; num <= explorationAward.getNum(); num++) {
					userSoulService.checkSoulFull(user);
					UserSoul us = userSoulService.addUserSoul(user, explorationAward.getAssId(), explorationAward.getLevel(), LoggerType.TYPE_探索获得);
					UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
					vo.init(us);
					fr.getUserSouls().add(vo);
				}
			} else if (explorationAward.getType() == Goods.TYPE_GOLD) {
				gold += explorationAward.getNum();
			} else if (explorationAward.getType() == Goods.TYPE_CURRENCY) {
				userService.incrementCurrency(user, explorationAward.getNum(), LoggerType.TYPE_探索获得, "");
			} else if (explorationAward.getType() == Goods.TYPE_FRIENDLY_POINT) {
				point += explorationAward.getNum();
				userService.incrementFriendlyPoint(user.getUserId(), explorationAward.getNum(), LoggerType.TYPE_探索获得, "");
			} else {
				Goods goods = new Goods();
				goods.setType(explorationAward.getType());
				goods.setGoodsId(explorationAward.getAssId());
				goods.setNum(explorationAward.getNum());
				goods.setLevel(explorationAward.getLevel());
				goodsList.add(goods);
			}
		}
		
		if (nowTime > doubleAwardTime) {
			exp = awardExp.getExp() * 2;

		} else {
			exp = awardExp.getExp();

		}
		gold += awardExp.getGold();
		userService.incrementGold(user, gold, LoggerType.TYPE_探索获得, "");

		fr.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), goodsList, LoggerType.TYPE_探索获得, ""));
		fr.setExp(exp);
		fr.setCurrency(user.getCurrency());
		fr.setGold(user.getGold());
		fr.setFriendlyPoint(point);
		userSoulService.addSoulExp(user, userSoul, exp, LoggerType.TYPE_探索加经验, "");
		userSoul.setSoulSafe(userSoul.getSoulSafe() & ~UserSoul.SOUL_SAFE_探索状态中);
		userSoulDAO.updateUserSoulCache(userSoul);
		soulExplorationDAO.updateSoulExploretion(userId, soulId);
		// 探索任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_探索次数, 0, 1);
		return fr;
	}

	@Override
	public List<ExplorationAwardExp> querAwardExpList() {
		return soulExplorationCfgDAO.queryExplorationAwardExp();
	}

	@Override
	public FightEndResultVO quicklyExploretionAward(int userId, long soulId) {
		User user = userService.getExistUserCache(userId);

		SoulExploretion se = soulExplorationDAO.querSoulExploretion(userId, soulId);
		if (se == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		ExplorationAwardExp awardExp = GameCache.getExplorationAwadExp(se.getHourTime(), se.getSoulRare());
		if (awardExp == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		// long oneTime = 60 * 60 * 1000L;
		// long time =se.getStartTime().getTime()+se.getHourTime()*oneTime -
		// System.currentTimeMillis();

		// int hour =(int) Math.ceil((time / (oneTime/2.0)));
		UserStat stat = userStatDAO.queryUserStat(userId);
		stat.setBuyExploretionCount(stat.getBuyExploretionCount() + 1);
		int money = (int) (Math.pow(2, (stat.getBuyExploretionCount() - 1))) * 20;
		userService.decrementCurrency(user, money, LoggerType.TYPE_立即结束探索消耗, "");
		UserSoul userSoul = userSoulService.getExistUserSoulCache(userId, soulId);
		List<ExplorationAward> awardList = GameCache.getExplorationAwards(se.getHourTime(), se.getSoulRare());
		List<Goods> goodsList = new ArrayList<Goods>();
		FightEndResultVO fr = MessageFactory.getMessage(FightEndResultVO.class);
		int exp = 0; // 奖励战魂经验
		int gold = 0; // 奖励金币
		int point = 0;
		int awardNumGoods = 0;// 奖励物品次数
		double randomNum = Math.random();
		if (randomNum <= awardExp.getRate1()) {
			awardNumGoods = awardExp.getAwardNum1();
		} else {
			awardNumGoods = awardExp.getAwardNum2();
		}
//		for (int i = 0; i < awardNumGoods; i++) {
//			double random = Math.random();
//			double totalRate = 0;
//			for (ExplorationAward ex : awardList) {
//				totalRate += ex.getRate();
//				if (random <= totalRate) {
//					if (ex.getType() == Goods.TYPE_SOUL) {
//						userSoulService.checkSoulFull(user);
//						for (int num = 1; num <= ex.getNum(); num++) {
//							UserSoul us = userSoulService.addUserSoul(user, ex.getAssId(), ex.getLevel(), SoulLogger.TYPE_探索获得);
//							UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
//							vo.init(us);
//							fr.getUserSouls().add(vo);
//						}
//
//					} else if (ex.getType() == Goods.TYPE_GOLD) {
//						gold += ex.getNum();
//					} else if (ex.getType() == Goods.TYPE_CURRENCY) {
//						userService.incrementCurrency(user, ex.getNum(), CurrencyLogger.TYPE_探索获得, "");
//					} else if (ex.getType() == Goods.TYPE_FRIENDLY_POINT) {
//						point += ex.getNum();
//						userService.incrementFriendlyPoint(user.getUserId(), ex.getNum(), FriendlyPointLogger.TYPE_探索获得, "");
//					} else {
//						Goods goods = new Goods();
//						goods.setType(ex.getType());
//						goods.setGoodsId(ex.getAssId());
//						goods.setNum(ex.getNum());
//						goods.setLevel(ex.getLevel());
//						goodsList.add(goods);
//					}
//					break;
//				}
//			}
//		}
		
		for (int i = 0; i < awardNumGoods; i++) {
			ExplorationAward explorationAward = (ExplorationAward)RandomUtil.getRandom(awardList);
			if (explorationAward.getType() == Goods.TYPE_SOUL) {
				for (int num = 1; num <= explorationAward.getNum(); num++) {
					userSoulService.checkSoulFull(user);
					UserSoul us = userSoulService.addUserSoul(user, explorationAward.getAssId(), explorationAward.getLevel(), LoggerType.TYPE_探索获得);
					UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
					vo.init(us);
					fr.getUserSouls().add(vo);
				}
			} else if (explorationAward.getType() == Goods.TYPE_GOLD) {
				gold += explorationAward.getNum();
			} else if (explorationAward.getType() == Goods.TYPE_CURRENCY) {
				userService.incrementCurrency(user, explorationAward.getNum(), LoggerType.TYPE_探索获得, "");
			} else if (explorationAward.getType() == Goods.TYPE_FRIENDLY_POINT) {
				point += explorationAward.getNum();
				userService.incrementFriendlyPoint(user.getUserId(), explorationAward.getNum(), LoggerType.TYPE_探索获得, "");
			} else {
				Goods goods = new Goods();
				goods.setType(explorationAward.getType());
				goods.setGoodsId(explorationAward.getAssId());
				goods.setNum(explorationAward.getNum());
				goods.setLevel(explorationAward.getLevel());
				goodsList.add(goods);
			}
		}

		exp = awardExp.getExp();
		gold += awardExp.getGold();
		userService.incrementGold(user, gold, LoggerType.TYPE_探索获得, "");
		fr.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), goodsList, LoggerType.TYPE_探索获得, ""));
		fr.setExp(exp);
		fr.setCurrency(user.getCurrency());
		fr.setGold(user.getGold());
		fr.setFriendlyPoint(point);
		userSoulService.addSoulExp(user, userSoul, exp, LoggerType.TYPE_探索加经验, "");
		userSoul.setSoulSafe(userSoul.getSoulSafe() & ~UserSoul.SOUL_SAFE_探索状态中);
		userSoulDAO.updateUserSoulCache(userSoul);
		soulExplorationDAO.updateSoulExploretion(userId, soulId);
		// 探索任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_探索次数, 0, 1);
		UserStatOpt opt = new UserStatOpt();
		opt.buyExploretionCount = SQLOpt.EQUAL;
		
		userStatDAO.updateUserStat(opt, stat);
		return fr;
	}

}
