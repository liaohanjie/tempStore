package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.exceptions.GameException;
import com.ks.logger.LoggerFactory;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.event.GameLoggerEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserSoulService;
import com.ks.logic.utils.DateUtils;
import com.ks.logic.utils.MarqueeUtils;
import com.ks.model.ZoneConfig;
import com.ks.model.achieve.Achieve;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.LoggerType;
import com.ks.model.logger.SoulLogger;
import com.ks.model.mission.MissionCondition;
import com.ks.model.skill.ActiveSkill;
import com.ks.model.soul.CallRule;
import com.ks.model.soul.CallRuleSoul;
import com.ks.model.soul.Soul;
import com.ks.model.soul.SoulEvolution;
import com.ks.model.totem.TotemRule;
import com.ks.model.totem.TotemSoul;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserSoulMap;
import com.ks.model.user.UserStat;
import com.ks.model.vip.VipPrivilege;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.soul.CallSoulResultVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.soul.UserSoulMapVO;
import com.ks.protocol.vo.soul.UserSoulOptResultVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.timer.TimerController;
import com.ks.util.Calculate;
import com.ks.util.CheckUtil;
import com.ks.util.RandomUtil;

public class UserSoulServiceImpl extends BaseService implements UserSoulService {

	private Logger logger = LoggerFactory.get(UserSoulServiceImpl.class);

	private static final double[] STRENG_TYPE = new double[] { 0.7, 0.2, 0.1 };
	private static final double[] STRENG_TYPE_MAX = new double[] { 0.5, 0.4, 0.1 };
	private static final double[] STRENG_TYPE_SUPPER = new double[] { 0.6, 0.2, 0.2 };

	private static final double[] STRENG_TYPE_MULTIPLE = new double[] { 1, 1.5, 2 };

	@Override
	public UserSoul addUserSoul(User user, int soulId, int level, int type) {
		Soul soul = GameCache.getSoul(soulId);
		if (soul == null) {
			throw new GameException(GameException.CODE_战魂不存在, "soulId:" + soulId);
		}
		UserSoul userSoul = new UserSoul();
		userSoul.setUserId(user.getUserId());
		userSoul.setSoulId(soulId);
		userSoul.setLevel(level);
		userSoul.setSkillLv(1);
		userSoul.setSoulType(Calculate.randomContains(UserSoul.SOUL_TYPE_COOL, UserSoul.SOUL_TYPE_STUBBORN));
		userSoul.setUpdateTime(new Date());
		userSoul.setCreateTime(new Date());
		userSoulDAO.addUserSoul(userSoul);
		userSoulDAO.addUserSoulCache(userSoul);
		lightUserSoulMap(user.getUserId(), soulId);
		// 添加日志
		SoulLogger logger = SoulLogger.createSoulLogger(user.getUserId(), type, 1, soulId, userSoul.getId() + ":");
		GameEvent event = new GameLoggerEvent(logger);
		TimerController.submitGameEvent(event);
		
		// 添加跑马灯
		if (soul != null && soul.getMarquee() == 1 && type != LoggerType.TYPE_机器初始化 && type != LoggerType.TYPE_高级账号) {
			marqueeService.add(MarqueeUtils.createSoulMarquee(user.getPlayerName(), soul.getName(), soul.getSoulRare()));
		}
		return userSoul;
	}

	@Override
	public List<UserSoul> initUserSoul(int userId) {
		List<UserSoul> uses = userSoulDAO.getUserSoulListFromCache(userId);
		if (uses.size() != 0) {
			userSoulDAO.clearUserSoulCache(userId);
		}
		uses = userSoulDAO.queryUserSoul(userId);
		userSoulDAO.addUserSoulCache(uses);
		return uses;
	}

	@Override
	public void clearUserSoul(int userId) {
		userSoulDAO.clearUserSoulCache(userId);
	}

	@Override
	public UserSoulOptResultVO strengUserSoul(int userId, long userSoulId, List<Long> soulIds) {
		User user = userService.getExistUserCache(userId);
		UserSoul us = getExistUserSoulCache(userId, userSoulId);
		Soul soul = GameCache.getSoul(us.getSoulId());
		if (CheckUtil.isRepeatlist(soulIds)) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		if (soulIds.size() > 5) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		List<UserSoul> souls = new ArrayList<>();
		for (long id : soulIds) {
			UserSoul s = getExistUserSoulCache(userId, id);
			if (s.getSoulSafe() > 0) {
				throw new GameException(GameException.CODE_参数错误, "soul is safe." + s.getSoulSafe());
			}
			souls.add(s);
		}

		Map<String, String> hash = new HashMap<>();

		int gold = soul.getStrongGold(us.getLevel(), souls.size());

		ActiveSkill skill = GameCache.getActiveSkill(soul.getSkill());

		// 最终获得的总经验
		int finalExp = 0;
		for (UserSoul s : souls) {
			Soul ms = GameCache.getSoul(s.getSoulId());
			// 该战魂可以获得的经验
			int exp = ms.getStrongEx(s.getLevel());
			// 消耗战魂与升级战魂属性一直，可以增加50%经验
			if (ms.getSoulEle() == soul.getSoulEle()) {
				exp *= 1.5;
			}
			finalExp += exp;
			// 还有一定几率可以提升主动技能
			if (skill != null) {
				if (us.getSkillLv() < skill.getMaxLevel()) {
					double random = Math.random();
					double rate = 0.01;
					if (ms.getSeries() == 888) {// 特点系列战魂技能升级概率
						rate = 1;
					} else if (ms.getSoulEle() == soul.getSoulEle()) {// 相同属性技能升级概率
						rate = 0.1;
					} else if (ms.getSkill() == soul.getSkill() || ms.getSeries() == soul.getSeries()) {// 相同技能或者相同系列技能升级概率
						rate = 0.8;
					} else {
						rate = 0.01;
					}

					// 检测提高强化技能升级概率活动是否开启
					boolean skillRate = activityService.activityIsStart(ActivityDefine.DEFINE_ID_强化技能概率); // 开启技能强化活动
					int multiple = 1;
					// if(user.getGuideStep()==User.GUIDE_STEP21_指引END)
					if (skillRate) {
						DropRateMultiple drm = chapterDAO.getDropRateMultipleCache(ActivityDefine.DEFINE_ID_强化技能概率, 0);
						if (drm != null) {
							multiple = drm.getMultiple();
						}
					}

					if (rate * multiple > random) {
						us.setSkillLv(us.getSkillLv() + 1);
						hash.put("skillLv", String.valueOf(us.getSkillLv()));
					}
				}
			}
		}

		// 扣金币
		userService.decrementGold(user, gold, LoggerType.TYPE_战魂强化消耗, "");
		int strengType = 0;
		double random = Math.random();
		double rate = 0;
		double[] CURRENT_STRENG_TYPE = STRENG_TYPE; // 当前的战魂强化概率
		boolean maxSuccess = activityService.activityIsStart(ActivityDefine.DEFINE_ID_强化大成功概率);
		boolean supperSuccess = activityService.activityIsStart(ActivityDefine.DEFINE_ID_强化超成功概率);
		if (user.getGuideStep() == User.GUIDE_STEP22_指引END) {
			if (maxSuccess) { // 大成功概率活动开启就取对应的概率
				CURRENT_STRENG_TYPE = STRENG_TYPE_MAX;
			}
			if (supperSuccess) { // 超成功概率活动开启就取超成功的概率
				CURRENT_STRENG_TYPE = STRENG_TYPE_SUPPER;
			}
		}

		for (int i = 0; i < CURRENT_STRENG_TYPE.length; i++) {
			rate += CURRENT_STRENG_TYPE[i];
			if (rate > random) {
				strengType = i;
				break;
			}
		}
		finalExp *= STRENG_TYPE_MULTIPLE[strengType];

		addSoulExp(user, us, finalExp, LoggerType.TYPE_合成增加经验, "");

		// VIP增加战魂强化经验
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		int vipExp = 0;
		if (vip != null) {
			vipExp = vip.getExtraStrengthenExp();
		}
		addSoulExp(user, us, vipExp, LoggerType.TYPE_VIP强化增加经验, "");

		for (UserSoul s : souls) {
			deleteUserSoul(user, s, LoggerType.TYPE_合成消耗, "");
		}
		userSoulDAO.updateUserSoulCache(user.getUserId(), us.getId(), hash);
		// 强化战魂任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_战魂强化, 0, 1);

		UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
		vo.init(us);

		UserSoulOptResultVO result = MessageFactory.getMessage(UserSoulOptResultVO.class);
		result.setGold(user.getGold());
		result.setType(strengType);
		result.setSoul(vo);

		if (user.getGuideStep() == User.GUIDE_STEP11_强化) {
			user.setGuideStep(User.GUIDE_STEP12_送进化精灵);
			Map<String, String> userHash = new HashMap<>();
			userHash.put("guideStep", User.GUIDE_STEP12_送进化精灵 + "");
			userDAO.updateUserCache(userId, userHash);
		}
		return result;
	}

	@Override
	public UserSoul getExistUserSoulCache(int userId, long userSoulId) {
		UserSoul soul = userSoulDAO.getUserSoulFromCache(userId, userSoulId);
		if (soul == null) {
			throw new GameException(GameException.CODE_战魂不存在, "soul." + userSoulId);
		}
		return soul;
	}

	@Override
	public UserSoul getExistUserSoul(int userId, long userSoulId) {
		UserSoul soul = userSoulDAO.getUserSoulFromCache(userId, userSoulId);
		if (soul == null) {
			soul = userSoulDAO.queryUserSoul(userId, userSoulId);
			if (soul == null) {
				throw new GameException(GameException.CODE_战魂不存在, "");
			}
		}
		return soul;
	}

	@Override
	public void deleteUserSoul(User user, UserSoul userSoul, int type, String description) {
		if (userSoul.getSoulSafe() > 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		userSoulDAO.delUserSoulCache(user.getUserId(), userSoul.getId());
		userSoulDAO.deleteUserSoul(user.getUserId(), userSoul.getId());
		GameLoggerEvent event = new GameLoggerEvent(SoulLogger.createSoulLogger(user.getUserId(), type, 1, userSoul.getSoulId(), userSoul.getId() + ":"
		        + description));
		TimerController.submitGameEvent(event);
	}

	@Override
	public void addSoulExp(User user, UserSoul userSoul, int num, int type, String description) {
		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		if (userSoul.getLevel() >= soul.getLvMax()) {
			return;
		}
		userSoul.setExp(userSoul.getExp() + num);
		calLevelUp(userSoul, soul);

		Map<String, String> hash = new HashMap<>();
		hash.put("exp", String.valueOf(userSoul.getExp()));
		hash.put("level", String.valueOf(userSoul.getLevel()));
		userSoulDAO.updateUserSoulCache(user.getUserId(), userSoul.getId(), hash);

		UserCap userCap = userTeamDAO.getUserCapCache(user.getUserId());

		if (userSoul.getId() == userCap.getUserSoulId()) {
			userCap.setLevel(userSoul.getLevel());
			userTeamDAO.updateUserCapCache(userCap);
		}
		GameLoggerEvent event = new GameLoggerEvent(SoulLogger.createSoulLogger(user.getUserId(), type, num, userSoul.getSoulId(), userSoul.getId() + ":" + description));
		TimerController.submitGameEvent(event);
	}

	private void calLevelUp(UserSoul userSoul, Soul soul) {
		int levelUpExp = Soul.getLevelUpExp(soul.getLvMode(), userSoul.getLevel());
		while (userSoul.getExp() >= levelUpExp) {
			userSoul.setLevel(userSoul.getLevel() + 1);
			userSoul.setExp(userSoul.getExp() - levelUpExp);
			if (userSoul.getLevel() >= soul.getLvMax()) {
				userSoul.setExp(0);
				break;
			}
			levelUpExp = Soul.getLevelUpExp(soul.getLvMode(), userSoul.getLevel());
		}
	}

	@Override
	public UserSoulOptResultVO soulEvolution(int userId, long userSoulId, List<Long> soulIds) {
		//
		//
		// args[0] : 483672393
		// args[1] : 3174
		// args[2] : [3178]
		if (CheckUtil.isRepeatlist(soulIds)) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		User user = userService.getExistUserCache(userId);
		UserSoul userSoul = getExistUserSoulCache(userId, userSoulId);

		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		if (userSoul.getLevel() < soul.getLvMax()) {
			throw new GameException(GameException.CODE_参数错误, " level must be max ,current" + userSoul.getLevel());
		}
		SoulEvolution se = GameCache.getEvolution(userSoul.getSoulId());
		if (se == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		List<Integer> evoMaterials = new LinkedList<>();
		for (int i : se.getSouls()) {
			evoMaterials.add(i);
		}

		if (soulIds.size() != evoMaterials.size()) {
			throw new GameException(GameException.CODE_材料不足, "");
		}

		for (long materialSoulId : soulIds) {
			UserSoul us = getExistUserSoulCache(userId, materialSoulId);
			if (us.getSoulSafe() > 0) {
				if (us.getSoulSafe() == UserSoul.SOUL_SAFE_PROTECTION) {

					throw new GameException(GameException.CODE_战魂处于保护状态, "");
				} else if (us.getSoulSafe() == UserSoul.SOUL_SAFE_探索状态中) {

					throw new GameException(GameException.CODE_战魂正在探索中, "");
				} else {
					throw new GameException(GameException.CODE_战魂正在队伍中, "");
				}
			}
			int index = evoMaterials.indexOf(us.getSoulId());
			if (index == -1) {
				throw new GameException(GameException.CODE_参数错误, "");
			}
			evoMaterials.remove(index);

			deleteUserSoul(user, us, LoggerType.TYPE_合成增加经验, String.valueOf(userSoulId));
		}
		// 不需要 券轴则可以不用
		List<UserGoods> goodsList = new ArrayList<>();
		if (se.getScrollPropId() != 0) {
			Backage backage = userGoodsService.getPackage(userId);
			goodsList = userGoodsService
			        .deleteGoods(backage, Goods.TYPE_PROP, se.getScrollPropId(), 1, LoggerType.TYPE_战魂进化消耗, "soul:" + userSoul.getSoulId());
		}
		userService.decrementGold(user, se.getGold(), LoggerType.TYPE_战魂进化消耗, userSoulId + ":" + soul.getSoulId() + "->" + se.getTargetSoul());

		Soul evoSoul = GameCache.getSoul(se.getTargetSoul());

		Map<String, String> hash = new HashMap<>();

		userSoul.setSoulId(se.getTargetSoul());
		hash.put("soulId", String.valueOf(userSoul.getSoulId()));

		userSoul.setLevel(1);
		hash.put("level", String.valueOf(userSoul.getLevel()));

		// 技能等级向上取
		if (evoSoul.getSkill() != soul.getSkill()) {
			userSoul.setSkillLv(1);
			hash.put("skillLv", String.valueOf(userSoul.getSkillLv()));
		}

		userSoulDAO.updateUserSoulCache(userId, userSoulId, hash);

		UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
		vo.init(userSoul);

		UserSoulOptResultVO result = MessageFactory.getMessage(UserSoulOptResultVO.class);
		result.setGold(user.getGold());
		result.setType(UserSoul.OPT_EVOLUTION);
		result.setSoul(vo);
		result.setUserGoods(new ArrayList<UserGoodsVO>());
		for (UserGoods goods : goodsList) {
			UserGoodsVO goodsVo = MessageFactory.getMessage(UserGoodsVO.class);
			goodsVo.init(goods);
			result.getUserGoods().add(goodsVo);
		}
		// 进化战魂任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_战魂进化, 0, 1);
		// 点亮图鉴
		userSoulService.lightUserSoulMap(userSoul.getUserId(), userSoul.getSoulId());
		if (user.getGuideStep() == User.GUIDE_STEP13_进化) {
			user.setGuideStep(User.GUIDE_STEP14_进入关卡);
			Map<String, String> userHash = new HashMap<>();
			userHash.put("guideStep", User.GUIDE_STEP14_进入关卡 + "");
			userDAO.updateUserCache(userId, userHash);
		}
		UserCap userCap = userTeamDAO.getUserCapCache(user.getUserId());
		if (userSoul.getId() == userCap.getUserSoulId()) {
			userCap.setLevel(userSoul.getLevel());
			userTeamDAO.updateUserCapCache(userCap);
		}
		return result;
	}

	@Override
	public int sellSoul(int userId, List<Long> userSoulIds) {
		if (CheckUtil.isRepeatlist(userSoulIds)) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (userSoulIds.size() > 10) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		User user = userService.getExistUserCache(userId);
		int gold = 0;
		for (long userSoulId : userSoulIds) {
			UserSoul userSoul = getExistUserSoulCache(userId, userSoulId);
			if (userSoul.getSoulSafe() > 0) {
				throw new GameException(GameException.CODE_参数错误, "");
			}
			Soul soul = GameCache.getSoul(userSoul.getSoulId());
			gold += soul.getSellPrice(userSoul.getLevel());
			deleteUserSoul(user, userSoul, LoggerType.TYPE_出售战魂, "");
		}
		userService.incrementGold(user, gold, LoggerType.TYPE_卖出战魂获得, "");
		return user.getGold();
	}

	/*
	 * @Override public CallSoulResultVO callSoul(int userId, int type, int num)
	 * { if (num <= 0 || num >= 99) { throw new
	 * GameException(GameException.CODE_参数错误, "num must 1 and 99"); } User user
	 * = userService.getExistUserCache(userId);
	 * userSoulService.checkSoulFull(user);
	 * 
	 * switch (type) { case CallSoulRule.TYPE_FRIENDLY_POINT:
	 * userService.decrementFriendlyPoint(userId,
	 * UserSoul.CALL_SOUL_FRIEND_POINT * num, FriendlyPointLogger.TYPE_召唤消耗,
	 * " num." + num); break; case CallSoulRule.TYPE_CURRENCY: if (num != 1 &&
	 * num != 10) { throw new GameException(GameException.CODE_参数错误,
	 * "num must 1 or 10,type." + type); } int dec = User.USER_CALL_召唤战魂一次; if
	 * (num == 10) { dec = User.USER_CALL_召唤战魂十次; }
	 * userService.decrementCurrency(user, dec, CurrencyLogger.TYPE_召唤消耗, "");
	 * break; default: throw new GameException(GameException.CODE_参数错误, " num."
	 * + num); } List<CallSoulRule> rules = soulDAO.querySoulCallRule(type); //
	 * 不定时活动 List<CallSoulRule> activityRule =
	 * soulDAO.queryActivitySoulCallRule(type); rules.addAll(activityRule);
	 * 
	 * // 新手指引不要添加指定战魂 if (user.getGuideStep() > User.GUIDE_STEP14_进入关卡) { //
	 * 指定召唤战魂 if
	 * (activityService.activityIsStart(ActivityDefine.DEFINE_ID_友情召唤出指定战魂) &&
	 * type == CallSoulRule.TYPE_FRIENDLY_POINT) { // List<CallSoulRule>
	 * activitySoulRule = // soulDAO.queryActivitySoulRule(); //
	 * rules.addAll(activitySoulRule); DropRateMultiple drm =
	 * chapterDAO.queryDropRateMultiple(ActivityDefine.DEFINE_ID_友情召唤出指定战魂); if
	 * (drm != null) { CallSoulRule cr = new CallSoulRule(); cr.setLevel(1);
	 * cr.setSoulId(drm.getSiteId()); cr.setRate(drm.getMultiple());
	 * cr.setType(type); rules.add(cr); } } if
	 * (activityService.activityIsStart(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂) &&
	 * type == CallSoulRule.TYPE_CURRENCY) { DropRateMultiple drm =
	 * chapterDAO.queryDropRateMultiple(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂); if
	 * (drm != null) { CallSoulRule cr = new CallSoulRule(); cr.setLevel(1);
	 * cr.setSoulId(drm.getSiteId()); cr.setRate(drm.getMultiple());
	 * cr.setType(type); rules.add(cr); } }
	 * 
	 * } int maxRate = 0; for (CallSoulRule rule : rules) { maxRate +=
	 * rule.getRate(); } List<UserSoulVO> userSouls = new ArrayList<>();
	 * List<CallSoulRule> givenRule = new ArrayList<>(); boolean isStart =
	 * activityService.activityIsStart(ActivityDefine.DEFINE_ID_召唤战魂概率); //
	 * 战魂概率活动开启 for (int i = 0; i < num; i++) { CallSoulRule csr = null; int
	 * random = (int) (Math.random() * maxRate); int rate = 0; for (CallSoulRule
	 * rule : rules) { int multiple = 1; // if(user.getGuideStep()
	 * ==User.GUIDE_STEP21_指引END){ if (isStart) { DropRateMultiple drm =
	 * chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_召唤战魂概率,
	 * rule.getSoulId()); if (drm != null) { multiple = drm.getMultiple(); } }
	 * // } rate += rule.getRate() * multiple; if (rate > random) { csr = rule;
	 * break; } } givenRule.add(csr); } if (user.getGuideStep() ==
	 * User.GUIDE_STEP6_召唤战魂) { if (num != 1) { throw new
	 * GameException(GameException.CODE_参数错误, " the call num must be 1"); } int
	 * soulId = 0; UserTeam userTeam =
	 * userTeamService.getExistUserTeam(user.getUserId(), user.getCurrTeamId());
	 * UserSoul chooseSoul =
	 * userSoulService.getExistUserSoulCache(user.getUserId(),
	 * userTeam.getPos().get(userTeam.getCap() - 1)); long chooseId =
	 * userTeam.getPos().get(userTeam.getCap() - 1); if (chooseSoul.getSoulId()
	 * == 1010001) { soulId = 1010120; } else if (chooseSoul.getSoulId() ==
	 * 1010006) { soulId = 1010099; } else if (chooseSoul.getSoulId() ==
	 * 1010011) { soulId = 1010136; } else if (chooseSoul.getSoulId() ==
	 * 1010016) { soulId = 1010132; } else { throw new
	 * GameException(GameException.CODE_参数错误, "no found right soulId for." +
	 * chooseId); } // 如果是新手所有的都clear，只给新手 CallSoulRule rule = new
	 * CallSoulRule(); rule.setLevel(1); rule.setSoulId(soulId);
	 * givenRule.clear(); givenRule.add(rule); } ;
	 * 
	 * if (user.getGuideStep() == User.GUIDE_STEP6_召唤战魂) {
	 * user.setGuideStep(User.GUIDE_STEP7_编队指引); Map<String, String> hash = new
	 * HashMap<>(); hash.put("guideStep", User.GUIDE_STEP7_编队指引 + "");
	 * userDAO.updateUserCache(userId, hash); }
	 * 
	 * // process ret CallSoulResultVO result =
	 * MessageFactory.getMessage(CallSoulResultVO.class);
	 * result.setSouls(userSouls); result.setCurrency(user.getCurrency());
	 * UserStat stat = userStatDAO.queryUserStat(userId);
	 * result.setFriendlyPoint(stat.getFriendlyPoint()); for (CallSoulRule rule
	 * : givenRule) { Soul soul = GameCache.getSoul(rule.getSoulId());
	 * UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
	 * vo.init(addUserSoul(user, soul.getSoulId(), rule.getLevel(),
	 * SoulLogger.TYPE_召唤获得)); userSouls.add(vo); } return result; }
	 */

	@Override
	public CallSoulResultVO callSoul(int userId, int typeId, int num) {
		// 判断召唤类型
		// 扣去魂钻或者友情点，记录召唤次数
		// 是否满足保底机制
		// 计算双倍概率
		// 计算随机战魂
		// 发放战魂

		if (num <= 0 || num >= 99) {
			throw new GameException(GameException.CODE_参数错误, "num must 1 and 99");
		}
		User user = userService.getExistUserCache(userId);
		userSoulService.checkSoulFull(user);

		UserStatOpt opt = new UserStatOpt();
		UserStat userStat = userStatDAO.queryUserStat(userId);

		switch (typeId) {
		case CallRule.TYPE_ID_FRIEND_POINT:
			userStat = userService.decrementFriendlyPoint(userId, UserSoul.CALL_SOUL_FRIEND_POINT * num, LoggerType.TYPE_召唤消耗, " num." + num);
			break;
		case CallRule.TYPE_ID_CURRENCY:
			//userStat.getCallSoulCurrencyNum();
			if (num != 1 && num != 10) {
				throw new GameException(GameException.CODE_参数错误, "num must 1 or 10,type." + typeId);
			}
			int dec = User.USER_CALL_召唤战魂一次;
			if (num == 10) {
				dec = User.USER_CALL_召唤战魂十次;
			}
			
			// 05:00 以后每天首次召唤免费召唤
			boolean isFree = false;
			Calendar nowCalendar = Calendar.getInstance();
            Calendar nextFreshCalendar = DateUtils.getCurrentDate(5, 0, 0);
            
            if (nowCalendar.get(Calendar.HOUR_OF_DAY) >= 5) {
		        nextFreshCalendar.add(Calendar.DATE, 1);
		    }
            
			if(num == 1) {
				if (userStat.getFreeCallSoulTime() == null) {
				    isFree = true;
				} else {
				    if (nextFreshCalendar.getTime().getTime() > userStat.getFreeCallSoulTime().getTime()) {
				        isFree = true;
				    }
				}
			}
			
			if (isFree) {
			    // 记录免费时间
                opt.freeCallSoulTime = SQLOpt.EQUAL;
                userStat.setFreeCallSoulTime(nextFreshCalendar.getTime());
                userStatDAO.updateUserStat(opt, userStat);
			} else {
                userService.decrementCurrency(user, dec, LoggerType.TYPE_召唤消耗, "");
			}
			break;
		default:
			throw new GameException(GameException.CODE_参数错误, " num." + num);
		}

//		List<CallSoulRule> rules = soulCfgDAO.querySoulCallRule(typeId);
//		// 不定时活动
//		List<CallSoulRule> activityRule = soulCfgDAO.queryActivitySoulCallRule(typeId);
//		rules.addAll(activityRule);
//
//		// 新手指引不要添加指定战魂
//		if (user.getGuideStep() > User.GUIDE_STEP14_进入关卡) {
//			// 指定召唤战魂
//			if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_友情召唤出指定战魂) && typeId == CallSoulRule.TYPE_FRIENDLY_POINT) {
//				DropRateMultiple drm = chapterDAO.queryDropRateMultiple(ActivityDefine.DEFINE_ID_友情召唤出指定战魂);
//				if (drm != null) {
//					CallSoulRule cr = new CallSoulRule();
//					cr.setLevel(1);
//					cr.setSoulId(drm.getSiteId());
//					cr.setRate(drm.getMultiple());
//					cr.setType(typeId);
//					rules.add(cr);
//				}
//			}
//			if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂) && typeId == CallSoulRule.TYPE_CURRENCY) {
//				DropRateMultiple drm = chapterDAO.queryDropRateMultiple(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂);
//				if (drm != null) {
//					CallSoulRule cr = new CallSoulRule();
//					cr.setLevel(1);
//					cr.setSoulId(drm.getSiteId());
//					cr.setRate(drm.getMultiple());
//					cr.setType(typeId);
//					rules.add(cr);
//				}
//			}
//
//		}

		List<Integer> soulIdList = new ArrayList<>();

		// 随机抽取指定战魂
		for (int i = 0; i < num; i++) {
			int soulId = getRandomCallSoulId(typeId, userStat);
			soulIdList.add(soulId);

			if (typeId == CallRule.TYPE_ID_FRIEND_POINT) {
				userStat.setCallSoulFriendPointNum(userStat.getCallSoulFriendPointNum() + 1);
				opt.callSoulFriendPointNum = SQLOpt.EQUAL;
				userStatDAO.updateUserStat(opt, userStat);
			}
			if (typeId == CallRule.TYPE_ID_CURRENCY) {
				userStat.setCallSoulCurrencyNum(userStat.getCallSoulCurrencyNum() + 1);
				opt.callSoulCurrencyNum = SQLOpt.EQUAL;
				userStatDAO.updateUserStat(opt, userStat);
			}
		}

		if (user.getGuideStep() == User.GUIDE_STEP6_召唤战魂) {
			if (num != 1) {
				throw new GameException(GameException.CODE_参数错误, " the call num must be 1");
			}
//			int soulId = 0;
//			UserTeam userTeam = userTeamService.getExistUserTeam(user.getUserId(), user.getCurrTeamId());
//			UserSoul chooseSoul = userSoulService.getExistUserSoulCache(user.getUserId(), userTeam.getPos().get(userTeam.getCap() - 1));
//			long chooseId = userTeam.getPos().get(userTeam.getCap() - 1);
//			if (chooseSoul.getSoulId() == 1010001) {
//				soulId = 1010120;
//			} else if (chooseSoul.getSoulId() == 1010006) {
//				soulId = 1010099;
//			} else if (chooseSoul.getSoulId() == 1010011) {
//				soulId = 1010136;
//			} else if (chooseSoul.getSoulId() == 1010016) {
//				soulId = 1010132;
//			} else {
//				throw new GameException(GameException.CODE_参数错误, "no found right soulId for." + chooseId);
//			}
//			// 如果是新手所有的都clear，只给新手
//			soulIdList.clear();
//			soulIdList.add(soulId);
			
			// 指定战魂召唤
//			int[] soulIds = new int[]{1010091,1010095,1010103,1010107,1010111,1010116,1010124,1010128,1010140,1010150};
			int[] soulIds = new int[]{1010092,1010096,1010100,1010104,1010108,1010112,1010117,1010121,1010125};
//			int[] soulIds = new int[]{1010073,1010076,1010080,1010084,1010088,1010092,1010096,1010100,1010104,1010108,1010112,1010117,1010121,1010125,1010129,1010133,1010137,1010141,1010144,1010151,1010159,1010163,1010167,1010171,1010145,1010164,1010089,1010074};
			soulIdList.clear();
			soulIdList.add(soulIds[(int) (Math.random() * soulIds.length)]);

			user.setGuideStep(User.GUIDE_STEP7_编队指引);
			Map<String, String> hash = new HashMap<>();
			hash.put("guideStep", User.GUIDE_STEP7_编队指引 + "");
			userDAO.updateUserCache(userId, hash);
		}

		// process ret
		ArrayList<UserSoulVO> userSouls = new ArrayList<>();
		for (int soulId : soulIdList) {
			UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
			vo.init(addUserSoul(user, soulId, 1, LoggerType.TYPE_召唤获得));
			userSouls.add(vo);
		}
		CallSoulResultVO result = MessageFactory.getMessage(CallSoulResultVO.class);
		result.setSouls(userSouls);
		result.setCurrency(user.getCurrency());
		result.setFriendlyPoint(userStat.getFriendlyPoint());
		return result;
	}

	/**
	 * 获取随机战魂编号
	 * 
	 * @param typeId
	 * @param userStat
	 * @return
	 */
	private int getRandomCallSoulId(int typeId, UserStat userStat) {
		List<CallRule> list = GameCache.getCallRuleMap().get(typeId);
		int callRuleId = 0;

		switch (typeId) {
		case CallRule.TYPE_ID_FRIEND_POINT:
			if (userStat.getCallSoulFriendPointNum() > 0 && userStat.getCallSoulFriendPointNum() % CallRule.FIRST_FACTOR == 0) {
				for (CallRule entity : list) {
					// 满足保底机制
					if (entity.getFirst() == CallRule.FIRST_YES) {
						callRuleId = entity.getId();
						System.out.println("友情保底");
						break;
					}
				}
			} else {
				CallRule rule = (CallRule) RandomUtil.getRandom(list);
				callRuleId = rule.getId();
			}

			break;
		case CallRule.TYPE_ID_CURRENCY:
			if (userStat.getCallSoulCurrencyNum() > 0 && userStat.getCallSoulCurrencyNum() % CallRule.FIRST_FACTOR == 0) {
				for (CallRule entity : list) {
					// 满足保底机制
					if (entity.getFirst() == CallRule.FIRST_YES) {
						callRuleId = entity.getId();
						System.out.println("魂钻保底: ");
						break;
					}
				}
			} else {
				CallRule rule = (CallRule) RandomUtil.getRandom(list);
				callRuleId = rule.getId();
			}
			break;
		}

		List<CallRuleSoul> callRuleSoulList = GameCache.getNewCallRuleListByCallRuleId(callRuleId);

		// 战魂概率活动开启
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_召唤战魂概率)) {
			// 指定战魂权重翻倍
			for (CallRuleSoul crs : callRuleSoulList) {
				DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_召唤战魂概率, crs.getSoulId());
				if (drm != null) {
					crs.setWeight(crs.getWeight() * drm.getMultiple());
				}
			}
		}
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_友情召唤出指定战魂) && typeId == CallRule.TYPE_ID_FRIEND_POINT) {
			// 指定战魂权重翻倍
			for (CallRuleSoul crs : callRuleSoulList) {
				DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_友情召唤出指定战魂, crs.getSoulId());
				if (drm != null) {
					crs.setWeight(crs.getWeight() * drm.getMultiple());
				}
			}
		}
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂) && typeId == CallRule.TYPE_ID_CURRENCY) {
			// 指定战魂权重翻倍
			for (CallRuleSoul crs : callRuleSoulList) {
				DropRateMultiple drm = chapterDAO.queryDropRateMultipleDefineId(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂, crs.getSoulId());
				if (drm != null) {
					crs.setWeight(crs.getWeight() * drm.getMultiple());
				}
			}
		}

		// 重新设置权重和
		int totalWeight = 0;
		for (CallRuleSoul crs : callRuleSoulList) {
			totalWeight = totalWeight + crs.getWeight();
		}
		for (CallRuleSoul crs : callRuleSoulList) {
			crs.setTotalWeight(totalWeight);
			//System.out.println(String.format("id=%s, callRuleId=%s, soulId=%s,weight=%s,totalWeight=%s", crs.getId(), crs.getCallRuleId(), crs.getSoulId(), crs.getWeight(), crs.getTotalWeight()));
		}

		CallRuleSoul crs = (CallRuleSoul) RandomUtil.getRandom(callRuleSoulList);
		return crs.getSoulId();
	}

	@Override
	public UserSoulInfoVO gainUserSoulCapInfo(int userId) {
		UserCap cap = userTeamDAO.getUserCapCache(userId);
		Collection<UserGoods> goodses;
		Backage backage = userGoodsService.getPackage(userId);
		if (backage != null) {
			goodses = backage.getUseGoodses().values();
		} else {
			goodses = userGoodsDAO.queryUserGoods(userId);
		}
		return gainUserSoulInfo(getExistUserSoul(userId, cap.getUserSoulId()), goodses);
	}

	@Override
	public UserSoulInfoVO gainUserSoulInfo(UserSoul soul, Collection<UserGoods> userGoods) {
		List<UserGoods> equments = new ArrayList<UserGoods>();
		for (UserGoods ug : userGoods) {
			if (ug.getGoodsType() == UserGoods.GOODS_TYPE_EQUIPMENT && ug.getUserSoulId() == soul.getId()) {
				equments.add(ug);
			}
		}
		UserSoulInfoVO userSoulInfoVO = MessageFactory.getMessage(UserSoulInfoVO.class);
		userSoulInfoVO.init(soul, equments);
		return userSoulInfoVO;
	}

	@Override
	public void checkSoulFull(User user) {
		int soulCount = userSoulDAO.getUserSoulCount(user.getUserId());
		int box = GameCache.getUserRule(user.getLevel()).getSoulBox();
		int vipExtra = 0;
		// VIP额外增加战魂仓库上限
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		if (vip != null) {
			vipExtra = vip.getExtraSoulCapacity();
		}

		if (user.getSoulCapacity() + box + vipExtra <= soulCount) {
			throw new GameException(GameException.CODE_战魂个数已达上限, "");
		}
	}

	@Override
	public int addSoulCapacity(int userId) {
		User user = userService.getExistUserCache(userId);
		if (user.getSoulCapacity() > User.MAX_SOUL_CAPACITY) {
			throw new GameException(GameException.CODE_战魂仓库已到最大容量, "");
		}

		int currency = User.USER_扩展战魂仓库价格;
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_战魂仓库格子打折)) {
			ZoneConfig config = activityDAO.queryZoneConfig(ZoneConfig.ID_USER_ID_SEED);
			currency = config.getAcSoulCapacityPrice();
		}
		userService.decrementCurrency(user, currency, LoggerType.TYPE_增加战魂仓库容量, "");
		user.setSoulCapacity(user.getSoulCapacity() + 5);

		Map<String, String> hash = new HashMap<>();
		hash.put("soulCapacity", String.valueOf(user.getSoulCapacity()));
		userDAO.updateUserCache(userId, hash);
		return user.getCurrency();
	}

	@Override
	public UserSoulMap lightUserSoulMap(int userId, int soulId) {
		if (UserSoulMap.NOT_IN_MAP.contains(soulId + "")) {
			return null;
		}
		UserSoulMap userMap = userSoulMapDAO.getUserMapSoul(userId, soulId);
		if (userMap != null && userMap.getState() == UserSoulMap.STATE_拥有过) {
			return userMap;
		}
		if (userMap == null) {
			userMap = UserSoulMap.create(userId, soulId, UserSoulMap.STATE_拥有过);
			userSoulMapDAO.addUserMapSoul(userMap);
		} else if (userMap.getState() != UserSoulMap.STATE_拥有过) {
			userSoulMapDAO.updateUserMapSoul(userId, soulId, UserSoulMap.STATE_拥有过);
		}
		userAchieveService.addUserAchieveProcess(userId, Achieve.TYPE_收集战魂, 0, 1);
		return userMap;
	}

	@Override
	public UserSoulMap seeUserSoulMap(int userId, int soulId) {
		UserSoulMap userMap = userSoulMapDAO.getUserMapSoul(userId, soulId);
		if (userMap == null) {
			userMap = UserSoulMap.create(userId, soulId, UserSoulMap.STATE_见过);
			userSoulMapDAO.addUserMapSoul(userMap);
		}
		return userMap;
	}

	@Override
	public List<UserSoulMap> seeUserSoulMap(int userId, Collection<Integer> soulIds) {
		List<UserSoulMap> list = userSoulMapDAO.getUserMapSouls(userId, soulIds);
		Iterator<Integer> iterator = soulIds.iterator();
		while (iterator.hasNext()) {
			Integer value = iterator.next();
			for (UserSoulMap user : list) {
				if (user.getSoulId() == value) {
					iterator.remove();
				}
			}
		}
		List<UserSoulMap> userSee = new ArrayList<UserSoulMap>();
		for (Integer value : soulIds) {
			UserSoulMap map = UserSoulMap.create(userId, value, UserSoulMap.STATE_见过);
			userSee.add(map);
		}
		list.addAll(userSee);
		userSoulMapDAO.addUserMapSoulBatch(userId, userSee);
		return list;
	}

	@Override
	public List<UserSoulMapVO> queryUserSoulMap(int userId) {
		List<UserSoulMapVO> retList = new ArrayList<>();
		List<UserSoulMap> list = userSoulMapDAO.getUserMapSouls(userId);
		for (UserSoulMap domain : list) {
			UserSoulMapVO vo = MessageFactory.getMessage(UserSoulMapVO.class);
			vo.init(domain);
			retList.add(vo);
		}
		return retList;
	}

	@Override
	public List<UserSoulMap> queryUserSoulMapByeState(int userId, int state) {
		return userSoulMapDAO.getUserMapSoulsState(userId, state);
	}

	@Override
	public void updateSoulSafe(int userId, long userSoulId, boolean safe) {
		UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
		if (safe) {
			soul.setSoulSafe(soul.getSoulSafe() | UserSoul.SOUL_SAFE_PROTECTION);
		} else {
			soul.setSoulSafe(soul.getSoulSafe() & ~UserSoul.SOUL_SAFE_PROTECTION);
		}
		userSoulDAO.updateUserSoulCache(soul);
	}

	/*
	 * @Override public UserSoulOptResultVO reShapeSoul(int userId, List<Long>
	 * soulIds) { User user = userService.getExistUser(userId); List<UserSoul>
	 * souls = new ArrayList<>(); Set<Integer> soulRare = new HashSet<>();
	 * List<Soul> rateList = new ArrayList<>(); for (long id : soulIds) {
	 * UserSoul us = getExistUserSoulCache(userId, id); Soul soul =
	 * GameCache.getSoul(new Long(us.getSoulId()).intValue()); if
	 * (us.getSoulSafe() > 0) { throw new GameException(GameException.CODE_参数错误,
	 * " soul safe not 0"); } souls.add(us); soulRare.add(soul.getSoulRare());
	 * rateList.add(soul); deleteUserSoul(user, us, SoulLogger.TYPE_重塑战魂消耗,
	 * String.valueOf(id)); } if (soulIds.size() != 5 || soulRare.size() > 1) {
	 * throw new GameException(GameException.CODE_参数错误, ""); } int rareValue =
	 * soulRare.iterator().next().intValue(); double random = Math.random();
	 * 
	 * if (0.1 < random && random < 1.0) { rateList.clear(); for (Soul entry :
	 * GameCache.getSoulMap().values()) { if (rareValue == entry.getSoulRare())
	 * { rateList.add(entry); } } } else if (random <= 0.02) { rateList.clear();
	 * for (Soul entry : GameCache.getSoulMap().values()) { if (rareValue + 1 ==
	 * entry.getSoulRare()) { rateList.add(entry); } } } if (rateList.isEmpty())
	 * { for (long id : soulIds) { UserSoul us = getExistUserSoulCache(userId,
	 * id); Soul soul = GameCache.getSoul(new Long(us.getSoulId()).intValue());
	 * if (us.getSoulSafe() > 0) { throw new
	 * GameException(GameException.CODE_参数错误, " soul safe not 0"); }
	 * rateList.add(soul); } } userService.decrementGold(user, rareValue * 1000
	 * * 5, GoldLogger.TYPE_重塑战魂消耗, soulIds + ":" + rareValue); // 添加新的
	 * UserSoulVO giveSoulVO = MessageFactory.getMessage(UserSoulVO.class); Soul
	 * current = rateList.get((int) (Math.random() * rateList.size()));
	 * giveSoulVO.init(addUserSoul(user, current.getSoulId(), 1,
	 * SoulLogger.TYPE_重塑战魂得到)); // return UserSoulOptResultVO retSoul =
	 * MessageFactory.getMessage(UserSoulOptResultVO.class);
	 * retSoul.setSoul(giveSoulVO); retSoul.setGold(user.getGold());
	 * retSoul.setType(UserSoul.OPT_重塑); return retSoul; }
	 */

	@Override
	public UserSoulOptResultVO reShapeSoul(int userId, List<Long> soulIds) {
		// 1. 判断战魂是否在探索
		// 2. 删掉素材战魂
		// 3. 判断战魂稀有度是否相同
		// 4. 概率计算
		// 5. 添加合成战魂

		int needSoulNum = 5;
		if (soulIds.size() != needSoulNum) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		User user = userService.getExistUser(userId);
		List<Soul> soulList = new ArrayList<>();

		for (int i = 0; i < soulIds.size(); i++) {
			Long id = soulIds.get(i);
			UserSoul userSoul = getExistUserSoulCache(userId, id);

			if (userSoul == null || userSoul.getSoulSafe() > 0) {
				throw new GameException(GameException.CODE_参数错误, " soul safe not 0");
			}
			soulList.add(GameCache.getSoul(new Long(userSoul.getSoulId()).intValue()));

			deleteUserSoul(user, userSoul, LoggerType.TYPE_重塑战魂消耗, String.valueOf(id));
		}

		int tmpSoulRare = soulList.get(0).getSoulRare();
		for (Soul soul : soulList) {
			if (soul.getSoulRare() != tmpSoulRare)
				throw new GameException(GameException.CODE_参数错误, " soul rare not equal");
		}

		// rate1 + rate2 + rate3 = 1
		// 以 1 来确定概率范围
		TotemRule rule = GameCache.getAllTotemRule().get(tmpSoulRare);
		double rate1 = rule.getRateNextRare();
		double rate2 = rate1 + rule.getRateSelfRare();
		double rate3 = rate2 + rule.getRateEqualRare();

		double rand1 = Math.random();

		int resultSoulId = soulList.get(0).getSoulId();

		if (rand1 < rate1) {
			// 下一星级指定战魂随机
			List<TotemSoul> list = GameCache.getAllTotemSoul(tmpSoulRare + 1);
			TotemSoul entity = (TotemSoul) RandomUtil.getRandom(list);
			resultSoulId = entity.getSoulId();
		} else if (rand1 < rate2) {
			// 素材战魂
			int index = (int) (Math.random() * needSoulNum);
			resultSoulId = soulList.get(index).getSoulId();
		} else {
			// 同星级指定战魂随机
			List<TotemSoul> list = GameCache.getAllTotemSoul(tmpSoulRare);
			TotemSoul entity = (TotemSoul) RandomUtil.getRandom(list);
			resultSoulId = entity.getSoulId();
		}

		userService.decrementGold(user, rule.getCostCoin(), LoggerType.TYPE_重塑战魂消耗, soulIds + ":" + rule.getSoulRare());

		UserSoulVO giveSoulVO = MessageFactory.getMessage(UserSoulVO.class);
		giveSoulVO.init(addUserSoul(user, resultSoulId, 1, LoggerType.TYPE_重塑战魂得到));

		UserSoulOptResultVO retSoul = MessageFactory.getMessage(UserSoulOptResultVO.class);
		retSoul.setSoul(giveSoulVO);
		retSoul.setGold(user.getGold());
		retSoul.setType(UserSoul.OPT_重塑);
		return retSoul;
	}

	@Override
	public UserSoulVO getGuideStrengSoul(int userId, int choose) {
		User user = userService.getExistUser(userId);
		// if(user.getGuideStep()!=User.GUIDE_STEP16_领取强化素材){
		// throw new GameException(GameException.CODE_错误的步骤,
		// "must be "+User.GUIDE_STEP16_领取强化素材);
		// }
		addUserSoul(user, choose, 1, LoggerType.TYPE_新手强化赠送);
		UserSoul soul = addUserSoul(user, choose, 1, LoggerType.TYPE_新手强化赠送);
		UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
		vo.init(soul);
		// Map<String, String> hash = new HashMap<>();
		// hash.put("guide_step",User.GUIDE_STEP17_强化前对话+"");
		// userDAO.updateUserCache(userId, hash);
		return vo;
	}

	@Override
	public UserSoulOptResultVO guideSoulEvolution(int userId, long userSoulId) {
		User user = userService.getExistUserCache(userId);
		// if(user.getGuideStep()!=User.GUIDE_STEP20_进化){
		// throw new GameException(GameException.CODE_错误的步骤,
		// "must be "+User.GUIDE_STEP20_进化);
		// }
		UserSoul userSoul = getExistUserSoulCache(userId, userSoulId);

		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		if (userSoul.getLevel() < soul.getLvMax()) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		SoulEvolution se = GameCache.getEvolution(userSoul.getSoulId());
		if (se == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		userService.decrementGold(user, se.getGold(), LoggerType.TYPE_战魂进化消耗, userSoulId + ":" + soul.getSoulId() + "->" + se.getTargetSoul());

		Soul evoSoul = GameCache.getSoul(se.getTargetSoul());

		Map<String, String> hash = new HashMap<>();
		userSoul.setSoulId(se.getTargetSoul());
		hash.put("soulId", String.valueOf(userSoul.getSoulId()));
		userSoul.setLevel(1);
		hash.put("level", String.valueOf(userSoul.getLevel()));

		// 技能等级向上取
		if (evoSoul.getSkill() != soul.getSkill()) {
			userSoul.setSkillLv(1);
			hash.put("skillLv", String.valueOf(userSoul.getSkillLv()));
		}

		userSoulDAO.updateUserSoulCache(userId, userSoulId, hash);

		UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
		vo.init(userSoul);

		UserSoulOptResultVO result = MessageFactory.getMessage(UserSoulOptResultVO.class);
		result.setGold(user.getGold());
		result.setType(UserSoul.OPT_EVOLUTION);
		result.setSoul(vo);
		// 点亮图鉴
		userSoulService.lightUserSoulMap(userSoul.getUserId(), userSoul.getSoulId());
		Map<String, String> hashMap = new HashMap<>();
		userDAO.updateUserCache(userId, hashMap);

		return result;
	}

	@Override
	public List<UserSoul> getUserSoulListFromCache(int userId) {
		return userSoulDAO.getUserSoulListFromCache(userId);
	}
}