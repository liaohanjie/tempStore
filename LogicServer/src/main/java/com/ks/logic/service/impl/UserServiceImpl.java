package com.ks.logic.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.ks.app.Application;
import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.event.DataLevelUpEvent;
import com.ks.logic.event.DataLoginEvent;
import com.ks.logic.event.DataLoginOutEevent;
import com.ks.logic.event.DataOrderEvent;
import com.ks.logic.event.DataUserRegEvent;
import com.ks.logic.event.GameLoggerEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserService;
import com.ks.logic.utils.DateUtils;
import com.ks.logic.utils.PropertyUtils;
import com.ks.model.ZoneConfig;
import com.ks.model.achieve.Achieve;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityGift;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.activity.OnTimeLoginGift;
import com.ks.model.activity.TotalLoginGift;
import com.ks.model.affiche.Affiche;
import com.ks.model.coin.CoinHand;
import com.ks.model.coin.CoinHandRule;
import com.ks.model.filter.UserFilter;
import com.ks.model.friend.Friend;
import com.ks.model.game.Stat;
import com.ks.model.goods.Backage;
import com.ks.model.goods.FightProp;
import com.ks.model.goods.Goods;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.AthleticsInfoLog;
import com.ks.model.logger.CurrencyLogger;
import com.ks.model.logger.ExpLogger;
import com.ks.model.logger.FriendlyPointLogger;
import com.ks.model.logger.GameLogger;
import com.ks.model.logger.GoldLogger;
import com.ks.model.logger.LoggerType;
import com.ks.model.logger.StaminaLogger;
import com.ks.model.logger.SweepCountLogger;
import com.ks.model.mission.MissionCondition;
import com.ks.model.pay.Mall;
import com.ks.model.pay.PayOrder;
import com.ks.model.pvp.AthleticsInfo;
import com.ks.model.stamina.Stamina;
import com.ks.model.user.GrowthFundRule;
import com.ks.model.user.User;
import com.ks.model.user.UserBuff;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserRule;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.model.user.UserTeam;
import com.ks.model.vip.VipPrivilege;
import com.ks.model.vip.VipWeekAward;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.GuideRetVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;
import com.ks.protocol.vo.shop.ProductBuyCountVO;
import com.ks.protocol.vo.user.CoinHandVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserInfoVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.protocol.vo.user.UserStatVO;
import com.ks.protocol.vo.user.UserTeamVO;
import com.ks.timer.TimerController;
import com.ks.util.DateUtil;
import com.ks.util.JSONUtil;
import com.ks.util.KeyWordsUtil;
import com.ks.util.RandomUtil;

public final class UserServiceImpl extends BaseService implements UserService {

	@Override
	public LoginResultVO userLogin(LoginVO login) {
		User user = userDAO.findUserByUsername(login.getUsername(), login.getPartner());
		LoginResultVO vo = MessageFactory.getMessage(LoginResultVO.class);
		if (user != null) {
			if (user.getBanAccountTime() != null && System.currentTimeMillis() < user.getBanAccountTime().getTime()) {
				throw new GameException(GameException.CODE_玩家已封号, "forbiden user login. userId=" + vo.getUserId() + ", time=" + user.getBanAccountTime());
			}
			
			vo.setUserId(user.getUserId());
			// 数据统计代码
			DataLoginEvent event = new DataLoginEvent(user.getUserId(), new Date(), login.getIp(), login.getPixel(), login.getModel(), login.getNet(),
			        login.getOperator(), login.getSystem());
			TimerController.submitGameEvent(event);

			// 每天登录送礼包
			loginActivity(user);

			// 登录统计
			// CountUtils.countLogin(user);
			
			// 重置当天数据
			resetSameDayData(user);
			
			// 发送后台补偿邮件或邮件
			mailService.send(user.getUserId(), false);
		}
		return vo;
	}

	@Override
	public void logout(int userId) {
		User user = userDAO.getUserFromCache(userId);
		if (user != null) {
			userDAO.updateUser(user);
			userDAO.delUserCache(userId);
			userSoulService.clearUserSoul(userId);
			userTeamService.clearUserTeamCache(userId);
			userGoodsService.clearPackage(userId);
			userGoodsService.clearFightProps(userId);
			userGoodsService.clearUserBakProps(userId);
			userMissionDAO.clearUserMissionCache(userId);
			// 数据统计
			DataLoginOutEevent outEv = new DataLoginOutEevent(user.getUserId(), user.getPartner(), user.getLevel(), user.getLastLoginTime(), new Date());
			TimerController.submitGameEvent(outEv);
		}

	}

	@Override
	public void userRegister(RegisterVO register) {
		// if(register!=null){
		// throw new GameException(GameException.CODE_参数错误, "user not found");
		// }
		User user = new User();
		user.setUsername(register.getUsername());
		user.setPlayerName(register.getPlayerName());
		user.setPartner(register.getPartner());
		user.setGold(0);
		user.setCurrency(0);
		user.setGuideStep(User.GUIDE_STEP1_播放GC);

		// 是否正在苹果上架审核中，在审核中注册账号均为高级账号
		Boolean review = PropertyUtils.SYS_CONFIG.getBool("apple.review", false);
		Integer level = PropertyUtils.SYS_CONFIG.getInt("apple.review.user.level", 31);
		if (review) {
			user.setLevel(level);
			user.setGuideStep(User.GUIDE_STEP4_角色选择);
		}

		UserRule rule = GameCache.getUserRule(user.getLevel());
		user.setStamina(rule.getStamina());

		user = userDAO.addUser(user);
		userDAO.updateLevelRank(user.getUserId(), user.getLevel());

		Friend f = new Friend();
		f.setFriendId(User.USER_ID_好友机器人);
		f.setUserId(user.getUserId());
		friendDAO.addFriend(f);

		// 苹果上架审核选择战魂
		if (review) {
			gainUserInfo(user.getUserId());
			nextSetp(User.GUIDE_STEP4_角色选择, user.getUserId());
			newbieSoul(User.GUIDE_SOULS[0], user.getUserId());
			nextSetp(User.GUIDE_STEP22_指引END, user.getUserId());
		}

		// 数据中心data
		DataUserRegEvent even = new DataUserRegEvent(user.getUserId(), user.getUsername(), register.getIp(), new Date(), register.getPixel(),
		        register.getModel(), register.getNet(), register.getOperator(), register.getSystem());
		TimerController.submitGameEvent(even);

		// 内侧删档使用, 活动发放礼品
		// kfTestRegisterActivity(user);

		// [开服有礼活动]
		sendRegisterRewardMail(user);

		// 注册统计
		// CountUtils.countRegister(register, user);
	}

	private static int[] calUserId(int val) {
		int id = val;
		int[] x = new int[] { id / 100000000, (id / 10000000) % 10, (id / 1000000) % 10, (id / 100000) % 10, (id / 10000) % 10, (id / 1000) % 10,
		        (id / 100) % 10, (id / 10) % 10, (id / 1) % 10 };
		for (int i = 0; i < x.length; i++) {
			switch (x[i]) {
			case 0:
				x[i] = 8;
				break;
			case 1:
				x[i] = 2;
				break;
			case 2:
				x[i] = 5;
				break;
			case 3:
				x[i] = 3;
				break;
			case 4:
				x[i] = 9;
				break;
			case 5:
				x[i] = 4;
				break;
			case 6:
				x[i] = 0;
				break;
			case 7:
				x[i] = 7;
				break;
			case 8:
				x[i] = 1;
				break;
			case 9:
				x[i] = 6;
				break;
			}
		}
		return x;
	}

	@Override
	public UserInfoVO gainUserInfo(int userId) {
		User user = userDAO.getUserFromCache(userId);
		if (user == null) {
			user = userDAO.findUserByUserId(userId);

			if (user == null) {
				throw new GameException(GameException.CODE_参数错误, "user not found");
			}
			//检查封号
			if (user.getBanAccountTime() != null && System.currentTimeMillis() < user.getBanAccountTime().getTime()) {
				throw new GameException(GameException.CODE_玩家已封号, "forbiden user login. userId=" + userId + ", time=" + user.getBanAccountTime());
			}
			userDAO.addUserCache(user);
		}

		UserStat stat = userStatDAO.queryUserStat(userId);
		if (stat == null) {
			stat = new UserStat();
			stat.setUserId(userId);
			stat.setHandselFriend(new ArrayList<Integer>());
			stat.setUseFriend(new ArrayList<Integer>());
			stat.setWinFriend(new ArrayList<Integer>());
			stat.setVipAwardTime(new Date());
			userStatDAO.addUserStat(stat);
		}
		List<UserSoul> uses = userSoulService.initUserSoul(userId);

		List<UserTeam> uts = userTeamService.initUserTeam(user, uses);
		List<UserGoods> ugs = userGoodsService.initUserGoods(userId);
		List<FightProp> fps = userGoodsService.initFightProp(userId);
		List<TotalLoginGift> totalGifts = activityService.queryTotalLoginGift();
		List<OnTimeLoginGift> onGifts = activityService.queryOnTimeGift();

		userMissionService.initUserMission(userId);
		userGoodsService.initUseBakProp(userId);
		
		UserInfoVO userInfo = MessageFactory.getMessage(UserInfoVO.class);
		boolean reset = DateUtil.isBeforeToDay(user.getLastLoginTime());
		if (reset) {
			userInfo.setTodayfisrtLogin(true);
		}

		// 登陆奖励
		activityService.getLoginGift(user, stat);

		// 重置当天数据
		resetSameDayData(user);

		// 计算连续登陆次数
		uninterruptedLoginCount(user);

		// 内侧删档使用, 活动发放礼品
		// kfTestActivity(user);

		Map<String, String> hash = new HashMap<>();
		user.setLastLoginTime(new Date());
		hash.put("lastLoginTime", String.valueOf(user.getLastLoginTime().getTime()));
		hash.put("uninterruptedLoginCount", String.valueOf(user.getUninterruptedLoginCount()));
		hash.put("totalCurrency", String.valueOf(user.getTotalCurrency()));
		userDAO.updateUserCache(userId, hash);

		if (uts.size() != 0) {
			userTeamService.initUserCap(user, getSoulCap(user, uses, uts));
		}
		UserCap cap = userTeamDAO.getUserCapCache(userId);
		List<Integer> want;
		if (cap != null) {
			want = cap.getWant();
		} else {
			want = new ArrayList<Integer>();
		}
		userDAO.updateLevelRank(userId, user.getLevel());
		//
		checkStamina(user);
		AthleticsInfo info = null;
		long lastBackTime = 0; // 竞技场回点是时间
		if (user.getLevel() >= 3) { // pvp玩家等级大于3
			info = athleticsInfoDAO.getAthleticsInfo(userId);
			if (info == null) {
				info = new AthleticsInfo();
				info.setUserId(userId);
				info.setAwardTitle(new ArrayList<Integer>());
				info.setAthleticsPoint(AthleticsInfo.ATHLETICS_POINT_初始的竞技点数);
				info.setLastBackTime(new Date());
				AthleticsInfoLog logger = AthleticsInfoLog.createBakAthleticsInfoLog(GameLogger.LOGGER_TYPE_PVP_POINT, userId, LoggerType.竞技点_type_第一次送竞技点,
				        AthleticsInfo.ATHLETICS_POINT_初始的竞技点数, AthleticsInfoLog.category_竞技点, "send");
				TimerController.submitGameEvent(new GameLoggerEvent(logger));
				athleticsInfoDAO.addAthleticsInfo(info);
				info = athleticsInfoDAO.getAthleticsInfo(userId);
			} else {
				athleticsInfoService.checkAthleticsInfoPoint(info);
			}
			lastBackTime = info.getLastBackTime().getTime();
		} else {
			info = new AthleticsInfo();
		}
		
		if (!onGifts.isEmpty()) {
			totalGifts.clear();
		}
		userInfo.init(user, uses, uts, ugs, fps, totalGifts, onGifts, want, info.getAthleticsPoint(), lastBackTime, info.getTotalIntegral());


		return userInfo;
	}

	/**
	 * 计算连续登陆
	 * 
	 * @param user
	 */
	private void uninterruptedLoginCount(User user) {
		// 计算日差, 最后登陆日期和现在登陆日期差
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(new Date());
		c2.setTime(user.getLastLoginTime());
		int days = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

		// 连续登陆次数(一天只累加一次)
		if (days == 1) {
			user.setUninterruptedLoginCount(user.getUninterruptedLoginCount() + 1);
		} else if (days > 1) {
			user.setUninterruptedLoginCount(1);
		}
	}

	/*
	 * 内侧删档使用 ，内侧过后需要删除
	 */
	private void kfTestActivity(User user) {
		// 计算日差, 最后登陆日期和现在登陆日期差
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(new Date());
		c2.setTime(user.getLastLoginTime());
		int days = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

		// Calendar c3 = Calendar.getInstance();
		// c3.set(2015, 5, 27, 0, 0, 0);
		/*
		 * if(user.getCreateTime().getTime() > user.getLastLoginTime().getTime()
		 * || (days == 1 && c3.getTime().getTime() >
		 * System.currentTimeMillis())) {
		 */
		if (days == 1 || user.getCreateTime().getTime() > user.getLastLoginTime().getTime()) {
			// 内侧删档使用 ,内侧时间过删除 [活动五:金币，经验钥匙送不停， 每天登录的玩家都必送金币和经验钥匙各2吧]
			List<Goods> activity5GoodsList = Arrays.asList(Goods.create(3050002, Goods.TYPE_PROP, 2, 0), Goods.create(3050003, Goods.TYPE_PROP, 2, 0));
			Affiche affiche4 = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_指定时间段登录, "【活动五】金币，经验钥匙送不停", "每天登录惊喜不断连绵，连绵！", activity5GoodsList,
			        Affiche.STATE_未读, "0");
			afficheService.addAffiche(affiche4);

			// 内侧删档使用 ,内侧时间过删除 [活动六:每天登录的玩家都必送经验宝2个]
			List<Integer> sExpIds = Arrays.asList(1010209, 1010212, 1010215, 1010218, 1010221, 1010224);
			Collections.shuffle(sExpIds);
			List<Goods> activity6GoodsList = Arrays.asList(Goods.create(sExpIds.get(0), Goods.TYPE_SOUL, 1, 1),
			        Goods.create(sExpIds.get(1), Goods.TYPE_SOUL, 1, 1));
			Affiche affiche6 = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_指定时间段登录, "【活动六】经验宝宝助成长", "来吧，快奔向你的战神之路！", activity6GoodsList,
			        Affiche.STATE_未读, "0");
			afficheService.addAffiche(affiche6);
		}

		// 连续登陆次数(一天只累加一次)
		if (days == 1) {
			user.setUninterruptedLoginCount(user.getUninterruptedLoginCount() + 1);
		} else if (days > 1) {
			user.setUninterruptedLoginCount(1);
		}

		// 内侧删档使用 ,内侧时间过删除
		// [活动三:连续3天登录游戏并参与测试的玩家将可获得尊贵vip会员体验（需要奖励对应的钻石和圣骑士莱恩（4星1级）]
		// VIP是否为5级
		if (user.getUninterruptedLoginCount() == 3 && GameCache.getVipGrade(user.getTotalCurrency()) < 5) {
			int vip5 = 5;
			VipPrivilege vipPrivilege = GameCache.getVipPrivilegeMap().get(vip5);
			if (vipPrivilege != null) {
				user.setTotalCurrency(vipPrivilege.getTotalCurrency());
			}
			List<Goods> activity6GoodsList = Arrays.asList(Goods.create(1010156, Goods.TYPE_SOUL, 1, 1));
			Affiche affiche6 = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_连续登录, "【活动三】莱恩带来尊贵v5", "祝福你在时界旅途之路享受更多的惊喜，奋斗吧朋友！", activity6GoodsList,
			        Affiche.STATE_未读, "0");
			afficheService.addAffiche(affiche6);
		}
	}

	/**
	 * 向玩家发送[开服有礼]邮件
	 * 
	 * @param user
	 */
	private void sendRegisterRewardMail(User user) {
		// 获取活动
		ActivityDefine activity = activityService.getCurrentActivityByDefineId(ActivityDefine.DEFINE_ID_开服有礼);
		// 获取活动礼包
		List<ActivityGift> activityGiftList = activityGiftDAO.queryActivityGiftByDefineId(ActivityDefine.DEFINE_ID_开服有礼);

		if (activity != null && activityGiftList != null && !activityGiftList.isEmpty()) {
			ActivityGift activityGift = activityGiftList.get(0);
			List<Goods> goods = JSONUtil.toObject(activityGift.getGift(), new TypeReference<List<Goods>>() {
			});
			// 发邮件
			Affiche affiche = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_开服有礼, activity.getTitle(), activity.getContext(), goods, Affiche.STATE_未读,
			        "0");
			afficheService.addAffiche(affiche);
		}
	}

	/*
	 * 内侧删档使用 ，内侧过后需要删除
	 */
	private void kfTestRegisterActivity(User user) {
		// 内侧删档使用 ,内侧时间过删除 [活动一:首登就送2880钻+友情点2000]
		Goods goodsCurrency = Goods.create(0, Goods.TYPE_CURRENCY, 2880, 0);
		Goods goodsFriendPoint = Goods.create(0, Goods.TYPE_FRIENDLY_POINT, 2000, 0);
		List<Goods> activity1GoodsList = Arrays.asList(goodsCurrency, goodsFriendPoint);
		Affiche affiche1 = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_指定时间段登录, "【活动一】旅途者们开启时界战士荣耀之路吧", "欢迎参与时界游戏之路，荣耀之路属于你，开始战斗吧！",
		        activity1GoodsList, Affiche.STATE_未读, "0");
		afficheService.addAffiche(affiche1);

		// 内侧删档使用 ,内侧时间过删除 [活动四:光明顶boss， 建号登录起，登录游戏并参与测试的玩家将可获得黑骑士刹士（4星1级）]
		Goods goodsHqsss = Goods.create(1010154, Goods.TYPE_SOUL, 1, 1);
		List<Goods> activity4GoodsList = Arrays.asList(goodsHqsss);
		Affiche affiche4 = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_指定时间段登录, "【活动四】刹士带你飞", "首登福利不间断，刹士带你飞！", activity4GoodsList, Affiche.STATE_未读,
		        "0");
		afficheService.addAffiche(affiche4);
	}

	/**
	 * 登陆活动，活动期内，每天第一次登陆送礼包
	 * 
	 * @param user
	 */
	public void loginActivity(User user) {
		int defineId = ActivityDefine.DEFINE_ID_每天登录送礼包;
		ActivityDefine activity = activityService.getCurrentActivityByDefineId(defineId);

		if (activityService.activityIsStart(activity)) {
			if (DateUtils.dateDiff(new Date(), user.getLastLoginTime()) >= 1) {
				List<ActivityGift> activityGiftList = activityGiftDAO.queryActivityGiftByDefineId(defineId);
				ActivityGift activityGift = activityGiftList.get(0);

				List<Goods> goods = JSONUtil.toObject(activityGift.getGift(), new TypeReference<List<Goods>>() {
				});
				// 发邮件
				Affiche affiche = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_开服有礼, activity.getTitle(), activity.getContext(), goods,
				        Affiche.STATE_未读, "0");
				afficheService.addAffiche(affiche);
			}
		}
	}

	/**
	 * 重置用户信息
	 * 
	 * @param userId
	 *            用户编号
	 */
	@Override
	public void resetUserStat(int userId) {
		userStatDAO.reset(userId);
	}

	private UserSoul getSoulCap(User user, List<UserSoul> uses, List<UserTeam> uts) {
		UserTeam team = null;
		for (UserTeam ut : uts) {
			if (ut.getTeamId() == user.getCurrTeamId()) {
				team = ut;
				break;
			}
		}
		long capId = team.getPos().get(team.getCap() - 1);
		UserSoul us = null;
		for (UserSoul u : uses) {
			if (u.getId() == capId) {
				us = u;
				break;
			}
		}
		return us;
	}

	@Override
	public User getExistUserCache(int userId) {
		User user = userDAO.getUserFromCache(userId);
		if (user == null) {
			throw new GameException(GameException.CODE_已经掉线, " user has disconnect");
		}
		return checkUser(user, userId);
	}

	@Override
	public User getExistUser(int userId) {
		User user = userDAO.getUserFromCache(userId);
		if (user == null) {
			user = userDAO.findUserByUserId(userId);
			if (user == null) {
				throw new GameException(GameException.CODE_用户不存在, " user no found");
			}
			return user;
		}

		return checkUser(user, userId);
	}

	private User checkUser(User user, int userId) {
		if (user.getUserId() == 0) {
			user = userDAO.findUserByUserId(userId);
			userDAO.addUserCache(user);
		}
		return user;
	}

	@Override
	public void incrementStamina(User user, int num, int type, String description) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		updateStamina(user, num, type, description);
	}

	@Override
	public void decrementStamina(User user, int num, int type, String description) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (user.getStamina() < num) {
			throw new GameException(GameException.CODE_体力不足, "");
		}
		updateStamina(user, -num, type, description);
	}

	private void updateStamina(User user, int num, int type, String description) {
		if (num == 0) {
			return;
		}
		user.setStamina(user.getStamina() + num);
		Map<String, String> hash = new HashMap<>();
		hash.put("stamina", String.valueOf(user.getStamina()));
		userDAO.updateUserCache(user.getUserId(), hash);

		StaminaLogger logger = StaminaLogger.createStaminaLogger(user.getUserId(), type, num, 0, description);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
	}

	@Override
	public List<UserRule> getUserRules() {
		return userCfgDAO.queryUserRules();
	}

	@Override
	public void incrementGold(User user, int num, int type, String description) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		updateGold(user, num, type, description);
		// 游戏币数量 成就
		userAchieveService.addUserAchieveProcess(user.getUserId(), Achieve.TYPE_游戏币数量, 0, num);
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_金币数量, 0, num);
	}

	@Override
	public void decrementGold(User user, int num, int type, String description) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (user.getGold() < num) {
			throw new GameException(GameException.CODE_金币不足, "");
		}
		updateGold(user, -num, type, description);
	}

	@Override
	public void increPoint(User user, int num, int type, String description) {
		if (num <= 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		user.setPoint(user.getPoint() + num);
		Map<String, String> hash = new HashMap<>();
		hash.put("point", String.valueOf(user.getPoint()));
		userDAO.updateUserCache(user.getUserId(), hash);
	}

	@Override
	public void decrementPoint(User user, int num, int type, String description) {
		if (num <= 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (user.getPoint() < num) {
			throw new GameException(GameException.CODE_积分不足, "");
		}

		user.setPoint(user.getPoint() - num);
		Map<String, String> hash = new HashMap<>();
		hash.put("point", String.valueOf(user.getPoint()));
		userDAO.updateUserCache(user.getUserId(), hash);
	}

	/**
	 * 增加荣誉值
	 * 
	 * @param user
	 * @param num
	 * @param type
	 * @param description
	 */
	@Override
	public void increHonor(User user, int num, int type, String description) {
		if (num <= 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		user.setHonor(user.getHonor() + num);
		Map<String, String> hash = new HashMap<>();
		hash.put("honor", String.valueOf(user.getHonor()));
		userDAO.updateUserCache(user.getUserId(), hash);
	}

	/**
	 * 扣减荣誉值
	 * 
	 * @param user
	 * @param num
	 * @param type
	 * @param description
	 */
	@Override
	public void decrementHonor(User user, int num, int type, String description) {
		if (num <= 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (user.getHonor() < num) {
			throw new GameException(GameException.CODE_交换竞技场荣誉值不足, "");
		}

		user.setHonor(user.getHonor() - num);
		Map<String, String> hash = new HashMap<>();
		hash.put("honor", String.valueOf(user.getHonor()));
		userDAO.updateUserCache(user.getUserId(), hash);
	}

	private void updateGold(User user, int num, int type, String description) {
		if (num == 0) {
			return;
		}
		user.setGold(user.getGold() + num);
		Map<String, String> hash = new HashMap<>();
		hash.put("gold", String.valueOf(user.getGold()));
		userDAO.updateUserCache(user.getUserId(), hash);

		GoldLogger logger = GoldLogger.createGoldLogger(user.getUserId(), type, num, 0, description);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
	}

	@Override
	public void incrementExp(User user, int num, int type, String description, UserCap userCap) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "num moust over 0");
		}
		int beLeveL = user.getLevel();
		int addStamina = 0;
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		if (vip != null) {
			addStamina = vip.getAddStamina();
		}
		if (user.getLevel() < User.MAX_LEVEL) {
			user.setExp(user.getExp() + num);
			UserRule rule = GameCache.getUserRule(user.getLevel());
			Map<String, String> hash = new HashMap<>();
			while (user.getExp() >= rule.getNextExp() && user.getLevel() < User.MAX_LEVEL) {
				user.setLevel(user.getLevel() + 1);
				user.setExp(user.getExp() - rule.getNextExp());
				if (user.getLevel() == User.MAX_LEVEL) {
					user.setExp(0);
				}
				rule = GameCache.getUserRule(user.getLevel());
				user.setStamina(rule.getStamina() + addStamina);
				hash.put("stamina", String.valueOf(user.getStamina()));
			}

			if (userCap == null) {
				userCap = userTeamDAO.getUserCapCache(user.getUserId());
			}
			userCap.setUserLevel(user.getLevel());
			userTeamDAO.updateUserCapCache(userCap);

			hash.put("exp", String.valueOf(user.getExp()));
			hash.put("level", String.valueOf(user.getLevel()));
			userDAO.updateUserCache(user.getUserId(), hash);

			// 用户等级 成就
			if (user.getLevel() - beLeveL > 0) {
				userAchieveService.addUserAchieveProcess(user.getUserId(), Achieve.TYPE_用户等级, 0, user.getLevel() - beLeveL);
				//
				DataLevelUpEvent event = new DataLevelUpEvent(user.getPartner(), user.getUserId(), user.getLevel(), new Date(), user.getLastLoginTime());
				TimerController.submitGameEvent(event);
			}

			ExpLogger logger = ExpLogger.createExpLogger(user.getUserId(), type, num, 0, description);
			TimerController.submitGameEvent(new GameLoggerEvent(logger));
		}
	}

	@Override
	public void incrementCurrency(User user, int num, int type, String description) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (type == LoggerType.TYPE_充值获得) {
			userAchieveService.addUserAchieveProcess(user.getUserId(), Achieve.TYPE_购买宝石, 0, num);
			rechargeVipAward(user, num); // vip等级奖励
			user.setTotalCurrency(user.getTotalCurrency() + num);// 记录充值总数

		}
		updateCurrency(user, num, type, description);

		// 数据统计
		/*
		 * DataGivePropEvent gve=new DataGivePropEvent(user.getPartner(),
		 * user.getUserId(), user.getUsername(), 0, Goods.TYPE_CURRENCY, num,
		 * type,new Date()); TimerController.submitGameEvent(gve);
		 */
	}

	@Override
	public void decrementCurrency(User user, int num, int type, String description) {
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (user.getCurrency() < num) {
			throw new GameException(GameException.CODE_魂钻不够, "");
		}
		updateCurrency(user, -num, type, description);
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_消费钻数量, 0, num);

		// 累计消费魂钻送礼活动 , 新手指引召唤战魂不计入消费
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_消费送礼) && user.getGuideStep() != User.GUIDE_STEP6_召唤战魂) {
			UserStat stat = userStatDAO.queryUserStat(user.getUserId());
			stat.setActivityTotalCostCurrency(stat.getActivityTotalCostCurrency() + num);

			UserStatOpt opt = new UserStatOpt();
			opt.activityTotalCostCurrency = UserStatOpt.EQUAL;
			userStatDAO.updateUserStat(opt, stat);
		}

		// 数据统计
		/*
		 * DataGivePropEvent gve=new DataGivePropEvent(user.getPartner(),
		 * user.getUserId(), user.getUsername(), 0, Goods.TYPE_CURRENCY, num,
		 * type,new Date()); TimerController.submitGameEvent(gve);
		 */
	}

	private void updateCurrency(User user, int num, int type, String description) {
		if (num == 0) {
			return;
		}
		user.setCurrency(user.getCurrency() + num);
		Map<String, String> hash = new HashMap<>();
		hash.put("currency", String.valueOf(user.getCurrency()));
		if (type == LoggerType.TYPE_充值获得) {
			hash.put("totalCurrency", String.valueOf(user.getTotalCurrency())); // 记录充值的总数
		}

		// if(userDAO.getUserFromCache(user.getUserId())!=null){
		userDAO.updateUserCache(user.getUserId(), hash);
		// }else{
		// userDAO.updateUser(user);
		// }
		CurrencyLogger logger = CurrencyLogger.createCurrencyLogger(user.getUserId(), type, num, 0, description);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
	}

	@Override
	public UserStat incrementFriendlyPoint(int userId, int num, int type, String description) {
		
		UserStat stat = userStatDAO.queryUserStat(userId);
		if(stat == null){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		if (num == 0) {
			return stat;
		}
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		if (stat.getFriendlyPoint() >= UserStat.MAX_FRIENDLY_POINT) {
			return stat;
		}
		
		int tagetValue = stat.getFriendlyPoint() + num;
		int result = tagetValue >= UserStat.MAX_FRIENDLY_POINT? UserStat.MAX_FRIENDLY_POINT : tagetValue;
		stat.setFriendlyPoint(result);
		
		updateFriendlyPoint(stat, num, type, description);
		
		return stat;
	}

	private void updateFriendlyPoint(UserStat stat, int num, int type, String description) {
		
		UserStatOpt opt = new UserStatOpt();
		opt.friendlyPoint = UserStatOpt.EQUAL;
		userStatDAO.updateUserStat(opt, stat);

		FriendlyPointLogger logger = FriendlyPointLogger.createFriendlyPointLogger(stat.getUserId(), type, num, 0, description);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
	}

	@Override
	public UserStat decrementFriendlyPoint(int userId, int num, int type, String description) {
		UserStat stat = userStatDAO.queryUserStat(userId);
		if (num == 0) {
			return stat;
		}
		if (num < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (stat.getFriendlyPoint() < num) {
			throw new GameException(GameException.CODE_友情点不足, "");
		}
		
		int tagetValue = stat.getFriendlyPoint() - num;
		stat.setFriendlyPoint(tagetValue);
		
		updateFriendlyPoint(stat, -num, type, description);
		
		return stat;
	}

	@Override
	public UserStatVO gainUserStat(int userId) {
		UserStat stat = userStatDAO.queryUserStat(userId);
		UserStatVO vo = MessageFactory.getMessage(UserStatVO.class);
		vo.init(stat);

		// 解析每天有限制商品购买次数记录，并转换成VO对象
		List<ProductBuyCountVO> pbcList = productService.createProductBuyCountVOList(stat.getProductDayMark());
		vo.setProductBuyCountVoList(pbcList);
		return vo;
	}

	@Override
	public void checkStamina(User user) {
		UserRule rule = GameCache.getUserRule(user.getLevel());
		int totalStamina = rule.getStamina();
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		if (vip != null) {
			totalStamina += vip.getAddStamina();
		}
		if (user.getStamina() < totalStamina) {
			long time = System.currentTimeMillis() - user.getLastRegainStaminaTime().getTime();
			long count = time / User.REGAIN_STAMINA_TIME;
			int stamina = 0;
			if (count > 0) {
				if (count > 1000) {
					stamina = totalStamina - user.getStamina();
					user.setLastRegainStaminaTime(new Date());
				} else {
					if (user.getStamina() + (int) count > totalStamina) {
						stamina = totalStamina - user.getStamina();
						user.setLastRegainStaminaTime(new Date());
					} else {
						stamina = (int) count;
						user.setLastRegainStaminaTime(new Date(System.currentTimeMillis() - (time % User.REGAIN_STAMINA_TIME)));
					}
				}
				userService.incrementStamina(user, stamina, LoggerType.TYPE_自动回体, "");
			}
		} else {
			user.setLastRegainStaminaTime(new Date());
		}
		Map<String, String> hash = new HashMap<>();
		hash.put("lastRegainStaminaTime", String.valueOf(user.getLastRegainStaminaTime().getTime()));
		userDAO.updateUserCache(user.getUserId(), hash);
	}

	@Override
	public int regainStamina(int userId) {
		// 计算最大购买次数
		// 加满体力
		// 计算消耗魂钻

		User user = getExistUserCache(userId);
		UserRule rule = GameCache.getUserRule(user.getLevel());
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());

		int maxCount = vip.getAddBuyStaminaCount();
		int maxStamina = rule.getStamina() + vip.getAddStamina();

		if (user.getStamina() >= maxStamina) {
			throw new GameException(GameException.CODE_体力已满, "stamina:" + user.getStamina());
		}

		UserStat stat = userStatDAO.queryUserStat(userId);
		stat.setBuyStaminaCount(stat.getBuyStaminaCount() + 1);

		if (stat.getBuyStaminaCount() > maxCount) {
			throw new GameException(GameException.CODE_体力购买次数已用完, "overflow stamina max count");
		}

		// 计算消耗魂钻
		int currency = 0;
		Stamina staminaConfig = GameCache.getStaminaMap().get(stat.getBuyStaminaCount());
		if (staminaConfig == null) {
			currency = 40;
		} else {
			currency = staminaConfig.getPrice();
		}

		// 活动体力打折活动
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_体力打折)) {
			ZoneConfig config = activityDAO.queryZoneConfig(ZoneConfig.ID_USER_ID_SEED);
			currency = currency * config.getAcStaminaPrice() / 10;
		}

		int stamina = maxStamina - user.getStamina();
		if (stamina > 0) {
			incrementStamina(user, stamina, LoggerType.TYPE_购买体力, "num:" + stat.getBuyStaminaCount());

			UserStatOpt opt = new UserStatOpt();
			opt.buyStaminaCount = SQLOpt.EQUAL;
			userStatDAO.updateUserStat(opt, stat);

			decrementCurrency(user, currency, LoggerType.TYPE_购买体力, "");
		}
		return user.getCurrency();
	}

	@Override
	public void zeroResetUserStat(int userId) {
		User user = getExistUserCache(userId);
		UserStat stat = userStatDAO.queryUserStat(userId);
		// 在线状态的每日奖励
		activityService.getLoginGift(user, stat);
		// kfTestActivity(user);
		user.setLastLoginTime(new Date());
		// 增加连续登录天数
		user.setUninterruptedLoginCount(user.getUninterruptedLoginCount() + 1);
		
		// 重置当天数据
		resetSameDayData(user);
		
		Map<String, String> hash = new HashMap<>();
		hash.put("lastLoginTime", String.valueOf(user.getLastLoginTime().getTime()));
		hash.put("uninterruptedLoginCount", String.valueOf(user.getUninterruptedLoginCount()));
		userDAO.updateUserCache(user.getUserId(), hash);

		// 连续充值送豪礼中断重置
		userStatDAO.zeroResetActivityContinuousRechargeCount();
	}

	@Override
	public FightPropVO givePlayerName(String playerName, int userId) {
		User user = getExistUserCache(userId);
		if (user.getGuideStep() != User.GUIDE_STEP3_玩家起名) {
			throw new GameException(GameException.CODE_错误的步骤, " pev step must be 200");
		}

		if (playerName == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		// 校验
		playerName = playerName.trim();
		if (playerName.equals("") || user.getPlayerName().equals(playerName)) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		User tempUser = userDAO.findUserByPlayername(playerName);
		if (tempUser != null) {
			throw new GameException(GameException.CODE_已存在该玩家名, "");
		}

		if (playerName == null || playerName.length() > 7) {
			throw new GameException(GameException.CODE_验证不通过, "");
		}
		if (KeyWordsUtil.hasBadWords(playerName)) {
			throw new GameException(GameException.CODE_验证不通过, "");
		}
		user.setGuideStep(User.GUIDE_STEP4_角色选择);
		// update
		user.setPlayerName(playerName);
		Map<String, String> hash = new HashMap<>();
		hash.put("guideStep", User.GUIDE_STEP4_角色选择 + "");
		hash.put("playerName", playerName);
		userDAO.updateUserCache(userId, hash);

		userDAO.updatePlayerName(userId, playerName);

		FightProp prop = fightPropDAO.geFightPropFromCache(userId, 1);
		prop.setPropId(3030001);
		prop.setNum(1);
		fightPropDAO.updateFightPropCache(prop);
		FightPropVO fpVo = MessageFactory.getMessage(FightPropVO.class);
		fpVo.init(prop);
		return fpVo;
	}

	@Override
	public GuideRetVO newbieSoul(int soulId, int userId) {
		boolean has = false;
		for (Integer id : User.GUIDE_SOULS) {
			if (id == soulId) {
				has = true;
				break;
			}
		}
		if (!has) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		User user = getExistUser(userId);
		if (user.getGuideStep() != User.GUIDE_STEP4_角色选择) {
			throw new GameException(GameException.CODE_错误的步骤, " user step must be 400");
		}
		user.setGuideStep(User.GUIDE_STEP5_二次战斗);
		// update
		Map<String, String> hash = new HashMap<>();
		hash.put("guideStep", User.GUIDE_STEP5_二次战斗 + "");
		userDAO.updateUserCache(userId, hash);
		// 添加战魂
		// userSoulService.checkSoulFull(user);
		// this.incrementCurrency(user, User.USER_新手赠送, LoggerType.TYPE_新手赠送,
		// "");
		UserSoul userSoul = userSoulService.addUserSoul(user, soulId, 1, LoggerType.TYPE_新手赠送);
		UserSoul userParnerSoul = userSoulService.addUserSoul(user, addParnterSoul(soulId), 1, LoggerType.TYPE_新手赠送);

		List<UserTeam> uts = userTeamDAO.getUserTeamCache(userId);
		if (uts.size() == 0) {
			for (byte i = 1; i <= UserTeam.TEAM_SIZE; i++) {
				UserTeam ut = new UserTeam();
				ut.setTeamId(i);
				ut.setUserId(user.getUserId());
				ut.setCap((byte) 1);
				ut.setPos(new ArrayList<Long>());
				for (int j = 0; j < 5; j++) {
					if (j < 1) {
						UserSoul soul = userSoul;
						soul.setSoulSafe(soul.getSoulSafe() | UserSoul.getInTeamSafe(ut.getTeamId()));
						ut.getPos().add(soul.getId());

						// 添加战魂伙伴
						if (i == 1) {
							ut.getPos().add(userParnerSoul.getId());
						}
					} else {
						ut.getPos().add(0L);
					}
				}
				ut.setCreateTime(new Date());
				ut.setUpdateTime(new Date());
				uts.add(ut);
			}
			userTeamDAO.addUserTeams(uts);
		}
		userTeamDAO.addUserTeamCache(uts);
		userSoulDAO.updateUserSoulCache(userSoul);

		List<UserSoul> uses = new ArrayList<UserSoul>();
		uses.add(userSoul);
		userTeamService.initUserCap(user, getSoulCap(user, uses, uts));

		List<UserTeamVO> userTeam = new ArrayList<UserTeamVO>();
		for (UserTeam ut : uts) {
			UserTeamVO vo = MessageFactory.getMessage(UserTeamVO.class);
			vo.init(ut);
			userTeam.add(vo);
		}
		// ret
		UserSoulVO soulVO = MessageFactory.getMessage(UserSoulVO.class);
		soulVO.init(userSoul);
		UserSoulVO userParnerVO = MessageFactory.getMessage(UserSoulVO.class);
		userParnerVO.init(userParnerSoul);

		GuideRetVO retVo = MessageFactory.getMessage(GuideRetVO.class);
		retVo.init(Arrays.asList(soulVO, userParnerVO), user.getCurrency(), userTeam);
		return retVo;
	}

	// 新手伙伴战魂
	private int addParnterSoul(int chooseSoulId) {
		int soulId = 0;

		if (chooseSoulId == 1010001) {
			soulId = 1010120;
		} else if (chooseSoulId == 1010006) {
			soulId = 1010099;
		} else if (chooseSoulId == 1010011) {
			soulId = 1010136;
		} else if (chooseSoulId == 1010016) {
			soulId = 1010132;
		} else {
			throw new GameException(GameException.CODE_参数错误, "no found right soulId for." + chooseSoulId);
		}
		return soulId;
	}

	@Override
	public GainAwardVO nextSetp(int nextStep, int userId) {
		GainAwardVO award = MessageFactory.getMessage(GainAwardVO.class);
		award.setFriendlyPoint(-1);
		award.setCurrency(-1);
		award.setGold(-1);
		award.setGoodses(new ArrayList<UserGoodsVO>());
		award.setSouls(new ArrayList<UserSoulVO>());

		User user = getExistUser(userId);
		if (nextStep < user.getGuideStep()) {
			throw new GameException(GameException.CODE_参数错误, "next step must be greater than the guide step");
		}

		if (nextStep == User.GUIDE_STEP11_强化) {
			userService.incrementGold(user, 10100, LoggerType.TYPE_新手得到, "");
			UserSoul soul = userSoulService.addUserSoul(user, 1010226, 1, LoggerType.TYPE_新手奖励);
			UserSoulVO soulVo = MessageFactory.getMessage(UserSoulVO.class);
			soulVo.init(soul);
			award.getSouls().add(soulVo);
			award.setGold(user.getGold());
		} else if (nextStep == User.GUIDE_STEP13_进化) {
			userService.incrementGold(user, 1000, LoggerType.TYPE_新手得到, "");
			UserSoul soul = userSoulService.addUserSoul(user, 1010179, 1, LoggerType.TYPE_新手奖励);
			UserSoulVO soulVo = MessageFactory.getMessage(UserSoulVO.class);
			soulVo.init(soul);
			award.getSouls().add(soulVo);
			award.setGold(user.getGold());
		} else if (nextStep == User.GUIDE_STEP20_道具屋) {
			Backage backage = userGoodsService.getPackage(userId);
			userGoodsService.checkBackageFull(backage, user);
			List<Goods> goodses = new ArrayList<Goods>();
			//goodses.add(Goods.create(3040014, Goods.TYPE_STUFF, 1, 0));
			//goodses.add(Goods.create(3040016, Goods.TYPE_STUFF, 1, 0));
			goodses.add(Goods.create(3030001, Goods.TYPE_PROP, 1, 0));
			goodses.add(Goods.create(3030002, Goods.TYPE_PROP, 1, 0));
			userService.incrementGold(user, 500, LoggerType.TYPE_新手得到, "");
			award.getGoodses().addAll(userGoodsService.addGoods(user, backage, goodses, LoggerType.TYPE_新手指引得到, ""));
			award.setGold(user.getGold());
		} else if (nextStep == User.GUIDE_STEP21_锻造屋送材料) {
			// 送材料：火之原液*5（ID：3040020），火之矿石*5（ID：3040001），麻痹卷轴*1（ID：3061001），金币：21000只送一次
			int gold = 21000;
			Backage backage = userGoodsService.getPackage(userId);
			userGoodsService.checkBackageFull(backage, user);
			List<Goods> goodses = new ArrayList<Goods>();
//			goodses.add(Goods.create(3040020, Goods.TYPE_STUFF, 5, 0));
//			goodses.add(Goods.create(3040001, Goods.TYPE_STUFF, 5, 0));
			goodses.add(Goods.create(3061001, Goods.TYPE_PROP, 1, 0));
			goodses.add(Goods.create(3010001, Goods.TYPE_EQUIPMENT, 1, 0));
			goodses.add(Goods.create(3010002, Goods.TYPE_EQUIPMENT, 1, 0));

			award.getGoodses().addAll(userGoodsService.addGoods(user, backage, goodses, LoggerType.TYPE_新手指引得到, ""));
			award.setGold(user.getGold() + gold);
			userService.incrementGold(user, gold, LoggerType.TYPE_新手得到, "");
		}
		user.setGuideStep(nextStep);
		Map<String, String> hash = new HashMap<>();
		hash.put("guideStep", nextStep + "");
		userDAO.updateUserCache(userId, hash);
		return award;
	}
	
	@Override
	public void nextInfoStep(int step, int userId) {
		User user = getExistUserCache(userId);
		user.setInfoStep(user.getInfoStep() | step);
		Map<String, String> hash = new HashMap<>();
		hash.put("infoStep", user.getInfoStep() + "");
		userDAO.updateUserCache(userId, hash);
	}
	
	@Override
	public void nextStep(User user, int nextStep) {
		if (nextStep < user.getGuideStep()) {
			throw new GameException(GameException.CODE_参数错误, "next step must be greater than the guide step");
		}
		user.setGuideStep(User.GUIDE_STEP7_编队指引);
		Map<String, String> hash = new HashMap<>();
		hash.put("guideStep", String.valueOf(nextStep));
		userDAO.updateUserCache(user.getUserId(), hash);
	}

	@Override
	public User bossFindUserByUsername(String username, int partner) {
		return userDAO.findUserByUsername(username, partner);
	}

	@Override
	public List<VipPrivilege> queryListVipPrivilege() {
		return userCfgDAO.queryListVipPrivilege();
	}

	public List<VipWeekAward> queryListVipWeekAward() {
		return userCfgDAO.queryListVipWeekAward();
	}

	@Override
	public int buySweepCount(int userId, int count) {
		User user = getExistUserCache(userId);
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency()); // 查询是否拥有vip对应的权限
		UserBuff buff = userBuffDAO.getUserBuff(userId, UserBuff.BUFF_ID_赠送vip);// 查询是否有临时vip权限
		if (vip == null && buff == null) {
			throw new GameException(GameException.CODE_不是vip, userId + "not vip");
		}
		if (count > 10 || count <= 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if (vip == null && buff != null && System.currentTimeMillis() > buff.getEndTime().getTime()) {
			throw new GameException(GameException.CODE_不是vip, userId + "not vip");
		}
		UserStat stat = userStatDAO.queryUserStat(userId);
		if (vip == null) {
			if (stat.getBuySweepCount() + count > 10) { // 赠送vip权限
				throw new GameException(GameException.CODE_购买扫荡次数不足, "not buy sweep count");
			}
		}
		if (vip != null) {
			if (stat.getBuySweepCount() + count > vip.getBuySweepCount()) {
				throw new GameException(GameException.CODE_购买扫荡次数不足, "not buy sweep count");
			}
		}

		// 购买扫荡每次扣除 2 魂钻
		decrementCurrency(user, count * 2, LoggerType.TYPE_购买扫荡次数, "");
		stat.setBuySweepCount(stat.getBuySweepCount() + count);
		// stat.setSweepCount(stat.getSweepCount()+count);
		UserStatOpt opt = new UserStatOpt();
		opt.buySweepCount = SQLOpt.EQUAL;
		userStatDAO.updateUserStat(opt, stat);
		SweepCountLogger logger = SweepCountLogger.createSweepCountLogger(user.getUserId(), LoggerType.TYPE_购买扫荡次数, count, 0, "buy sweep count");
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
		return user.getCurrency();
	}

	@Override
	public void rechargeVipAward(User user, int num) {
		int beforeVipGrade = GameCache.getVipGrade(user.getTotalCurrency());
		int afterVipGrade = GameCache.getVipGrade(user.getTotalCurrency() + num);
		if (afterVipGrade > 0) {
			UserStat stat = userStatDAO.queryUserStat(user.getUserId());
			// UserStatOpt opt = new UserStatOpt();
			for (int i = 1; i <= afterVipGrade - beforeVipGrade; i++) {
				/*
				 * List<Goods> goodsList = new ArrayList<Goods>();//
				 * vip等级6、7、8、9每周第一次登录要发奖 List<VipWeekAward> awards =
				 * GameCache.getVipWeekAwards(beforeVipGrade + i); for
				 * (VipWeekAward vw : awards) { Goods goods =
				 * Goods.create(vw.getAssId(), vw.getType(), vw.getNum(),
				 * vw.getLevel()); goodsList.add(goods); } Affiche affiche =
				 * Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_充值送奖励,
				 * "vip特权奖励", "只有vip才能有的每周特权奖励，快点击领取吧！", goodsList,
				 * Affiche.STATE_未读, "0"); afficheService.addAffiche(affiche);
				 * opt.vipAwardTime = SQLOpt.EQUAL;
				 */

				issueVipWeekAward(stat, beforeVipGrade + i);
			}
			/*
			 * stat.setVipAwardTime(new Date()); userStatDAO.updateUserStat(opt,
			 * stat);
			 */
		}
	}

	@Override
	public int buyGrowthfund(int userId) {
		User user = getExistUserCache(userId);
		if ((user.getProperty() & User.PROPERTY_成长基金) != 0) {
			throw new GameException(GameException.CODE_已购买成长基金, "has buy growth found");
		}
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());
		if (vip.getVipGrade() < 2) { // vip 等级小于2不能购买
			throw new GameException(GameException.CODE_充值总额, "total charge not enough");
		}
		decrementCurrency(user, 1000, LoggerType.TYPE_购买成长基金, "");

		user.setProperty(user.getProperty() | User.PROPERTY_成长基金);
		Map<String, String> hash = new HashMap<>();
		hash.put("property", user.getProperty() + "");
		userDAO.updateUserCache(userId, hash);

		// 记录购买基金人数
		int extraCount = (int) (Math.random() * 4 + 4);
		Stat stat = statDAO.findById(Stat.ID_BUY_GROW_FUND_COUNT);
		stat.setValue(stat.getValue() + extraCount + 1);
		statDAO.update(stat);
		return user.getCurrency();
	}

	@Override
	public int getGrowthCurrency(int userId, int grade) {
		User user = getExistUserCache(userId);
		UserStat stat = userStatDAO.queryUserStat(userId);
		if ((user.getProperty() & User.PROPERTY_成长基金) == 0) {
			throw new GameException(GameException.CODE_没有购买基金, "have not  buy growth found");
		}
		GrowthFundRule rule = GameCache.getGrowthFundRule(grade);
		if (rule == null) {
			throw new GameException(GameException.CODE_参数错误, "there no this grade fund." + grade);
		}
		if (user.getLevel() < grade) {
			throw new GameException(GameException.CODE_等级不够, "grade must be over." + grade);
		}
		String[] has = stat.getGrowthGift().split(UserStat.SPLIT);
		for (String str : has) {
			if (str.equals(grade + "")) {
				throw new GameException(GameException.CODE_不能重复领取, "has get this grade fund." + grade);
			}
		}
		incrementCurrency(user, rule.getCurrency(), LoggerType.CODE_成长基金领取, "grade." + grade);
		stat.setGrowthGift(grade + UserStat.SPLIT + stat.getGrowthGift());
		UserStatOpt opt = new UserStatOpt();
		opt.growthGift = SQLOpt.EQUAL;
		userStatDAO.updateUserStat(opt, stat);
		return user.getCurrency();
	}

	@Override
	public List<GrowthFundRule> queryGrowthFundRule() {
		return userCfgDAO.queryGrowthFundRule();
	}

	@Override
	public void nextStoryMission(int storyMission, int userId) {
		if (storyMission > User.STORY_MISSION_END) {
			throw new GameException(GameException.CODE_错误的步骤, " can't auto this story mission." + storyMission);
		}
		User user = getExistUserCache(userId);
		user.setStoryMission(storyMission);
		Map<String, String> hash = new HashMap<>();
		hash.put("storyMission", storyMission + "");
		userDAO.updateUserCache(userId, hash);
	}

	@Override
	public void pay(String orderNo, int amount, int gameCoin, String userName, int partner, int goodsId) {
		User user = userDAO.findUserByUsername(userName, partner);
		if (user == null) {
			throw new GameException(GameException.CODE_用户不存在, " userName." + userName + " partner." + partner);
		}
		User userCa = getExistUser(user.getUserId());

		// 首次充值记录(首充活动送三倍魂钻)
		// 月卡购买也是首冲记录
		saveFirstPay(userCa, amount * 10, amount, goodsId);

		switch (goodsId) {
		case PayOrder.GOODS_ID_CURRENCY:
			processCurrency(userCa, amount, gameCoin, goodsId, orderNo);
			break;
		case PayOrder.GOODS_ID_GOLD_MONTH_CARD:
			processGoldMonthCard(userCa, amount, gameCoin, goodsId);
			break;
		case PayOrder.GOODS_ID_DIAMOND_MONTH_CARD:
			processDianmondMonthCard(userCa, amount, gameCoin, goodsId);
			break;
		default:
			throw new GameException(GameException.CODE_充值商品编号不存在, goodsId + "");
		}

		// 支付统计
		// CountUtils.countPay(userCa, orderNo, amount, gameCoin, goodsId);
		
		// 数据中心data
		DataOrderEvent event = new DataOrderEvent(user.getUserId(), 1, null, orderNo, amount, gameCoin, userName);
		TimerController.submitGameEvent(event);
	}

	/**
	 * 购买魂钻处理
	 * 
	 * @param amount
	 * @param gameCoin
	 * @param userName
	 * @param partner
	 * @param goodsId
	 */
	private void processCurrency(User user, int amount, int gameCoin, int goodsId, String orderNo) {
		// 充值送魂钻
		Mall mall = GameCache.getAllMall().get(amount);
		if (mall != null && mall.getStatus() == Mall.STATUS_ENABLE && mall.getExtra() > 0) {
			userService.incrementCurrency(user, mall.getExtra(), LoggerType.TYPE_充值额外, orderNo);
		}
		userService.incrementCurrency(user, gameCoin, LoggerType.TYPE_充值获得, orderNo);

		UserStat userStat = userStatDAO.queryUserStat(user.getUserId());
		UserStatOpt opt = new UserStatOpt();

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_限时购买魂币送礼)) {
			BuyCoinGift gifts = activityDAO.getBuyCoingift();
			int coin = gifts.getGameCoin();
			if (userStat.getAcChageTotal() < coin && userStat.getAcChageTotal() + gameCoin >= coin) {
				afficheService.addAffiche(Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_充值送奖励, gifts.getMailTital(), gifts.getMailContext(),
				        gifts.getGoods(), Affiche.STATE_未读, ""));
			}
			opt.acChargeTotal = UserStatOpt.PULS;
			userStat.setAcChageTotal(gameCoin);
		}

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_限量购买魂币送礼)) {
			FlashGiftBag bag = activityDAO.getFlashGiftForLock(1);
			if (bag.getBuyNum() <= bag.getMaxNum() && amount >= bag.getPrice()) {
				Affiche af = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYP_限时礼包物品, bag.getMailTital(), bag.getMailContext(), bag.getGoods(),
				        Affiche.STATE_未读, "");
				activityDAO.incrementFlashBag(bag.getId());
				afficheService.addAffiche(af);
			}
		}

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_限时礼包)) {
			// 限时活动累计充值记录
			opt.activityRechargeCurrency = UserStatOpt.EQUAL;
			userStat.setActivityRechargeCurrency(userStat.getActivityRechargeCurrency() + gameCoin);
		}

		// 连续充值
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_每日充值送豪礼)) {
			// 上次和这次充值是否日期差一天
			int diffDays = 0;
			if (userStat.getLastRechargeTime() != null) {
				diffDays = DateUtils.dateDiff(new Date(), userStat.getLastRechargeTime());
			}
			if (userStat.getLastRechargeTime() == null || diffDays == 1) {
				opt.activityContinuousRechargeCount = UserStatOpt.EQUAL;
				userStat.setActivityContinuousRechargeCount(userStat.getActivityContinuousRechargeCount() + 1);
			} else if (diffDays == 0) {
				// 什么也不处理
			} else {
				opt.activityContinuousRechargeCount = UserStatOpt.EQUAL;
				userStat.setActivityContinuousRechargeCount(1);
				opt.activityContinuousRechargeGetMark = UserStatOpt.EQUAL;
				userStat.setActivityContinuousRechargeGetMark(0);
				userStat.setDayTotalCurrency(0);
			}
			// 记录每天充值累计
			opt.dayTotalCurrency = UserStatOpt.EQUAL;
			userStat.setDayTotalCurrency(userStat.getDayTotalCurrency() + gameCoin);
		}
		
		opt.lastRechargeTime = UserStatOpt.EQUAL;
		userStat.setLastRechargeTime(new Date());
		
		userStatDAO.updateUserStat(opt, userStat);
		
	}

	/**
	 * 购买黄金月卡处理
	 * 
	 * @param user
	 * @param amount
	 * @param gameCoin
	 * @param userName
	 * @param partner
	 * @param goodsId
	 */
	private void processGoldMonthCard(User user, int amount, int gameCoin, int goodsId) {
		UserBuff buff = new UserBuff();
		buff.setUserId(user.getUserId());
		buff.setBuffId(UserBuff.BUFF_ID_黄金月卡);
		buff.setCreateTime(new Date());
		buff.setEndTime(new Date());
		userBuffService.addUserBuff(user.getUserId(), buff, 3600L * 1000 * 24 * 30);

		Mall mall = GameCache.getAllMall().get(amount);
		int extraCurrency = 0;
		if (mall != null && mall.getStatus() == Mall.STATUS_ENABLE && mall.getExtra() > 0) {
			extraCurrency = mall.getExtra();
			// 立即送 300 魂钻
			userService.incrementCurrency(user, extraCurrency, LoggerType.TYPE_黄金月卡, "gold_month_card");
		}
	}

	/**
	 * 购买钻石卡处理
	 * 
	 * @param user
	 * @param amount
	 * @param gameCoin
	 * @param userName
	 * @param partner
	 * @param goodsId
	 */
	private void processDianmondMonthCard(User user, int amount, int gameCoin, int goodsId) {
		UserBuff buff = new UserBuff();
		buff.setUserId(user.getUserId());
		buff.setBuffId(UserBuff.BUFF_ID_钻石月卡);
		buff.setCreateTime(new Date());
		buff.setEndTime(new Date());
		userBuffService.addUserBuff(user.getUserId(), buff, 3600L * 1000 * 24 * 30);

		Mall mall = GameCache.getAllMall().get(amount);
		int extraCurrency = 0;
		if (mall != null && mall.getStatus() == Mall.STATUS_ENABLE && mall.getExtra() > 0) {
			extraCurrency = mall.getExtra();
			// 立即送 980 魂钻
			userService.incrementCurrency(user, extraCurrency, LoggerType.TYPE_钻石月卡, "diamond_month_card");
		}
	}

	@Override
	public void sendRegainStamina(int userId) {

		User user = getExistUserCache(userId);
		UserRule rule = GameCache.getUserRule(user.getLevel());
		int addStamina = 0;
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency()); // 查询是否拥有vip对应的权限
		UserBuff buff = userBuffDAO.getUserBuff(userId, UserBuff.BUFF_ID_赠送vip);// 查询是否有临时vip权限
		if (vip == null && buff != null && System.currentTimeMillis() < buff.getEndTime().getTime()) {
			addStamina = 3;
		}
		if (vip != null) {
			addStamina = vip.getAddStamina();
		}
		UserStat stat = userStatDAO.queryUserStat(userId);

		boolean isStart1 = activityService.activityIsStart(ActivityDefine.DEFINE_ID_免费领体力);
		boolean isStart2 = activityService.activityIsStart(ActivityDefine.DEFINE_ID_送体力);
		if (isStart1 == false && isStart2 == false) {
			throw new GameException(GameException.CODE_活动未开启, "activity is not ");
		}
		if (isStart1 && (stat.getSendStamainCount() & UserStat.PROPERTY_第一次领取体力) != 0) {
			throw new GameException(GameException.CODE_免费领取体力次数已经用完, "send stamian not count");
		}
		if (isStart1 && (stat.getSendStamainCount() & UserStat.PROPERTY_第一次领取体力) == 0) {
			stat.setSendStamainCount(stat.getSendStamainCount() | UserStat.PROPERTY_第一次领取体力);
		}
		if (isStart2 && (stat.getSendStamainCount() & UserStat.PROPERTY_第二次领取体力) != 0) {
			throw new GameException(GameException.CODE_免费领取体力次数已经用完, "send stamian not count");
		}
		if (isStart2 && (stat.getSendStamainCount() & UserStat.PROPERTY_第二次领取体力) == 0) {
			stat.setSendStamainCount(stat.getSendStamainCount() | UserStat.PROPERTY_第二次领取体力);
		}

		UserStatOpt opt = new UserStatOpt();
		opt.sendStamainCount = SQLOpt.EQUAL;
		userStatDAO.updateUserStat(opt, stat);
		int stamina = rule.getStamina() + addStamina - user.getStamina();
		if (stamina > 0) {
			incrementStamina(user, stamina, LoggerType.TYPE_免费领取体力, "");
		}
	}

	@Override
	public List<User> getUsers(UserFilter filter) {
		return userDAO.getUsers(filter);
	}

	/**
	 * 首次充值,保存充值金额
	 * 
	 * @param user
	 * @param gameCoin
	 */
	private void saveFirstPay(User user, int gameCoin, int amount, int goodsId) {

		if (user.getFirstCurrency() == 0) {

			// 月卡保存额外赠送的作为首冲
			Mall mall = GameCache.getAllMall().get(amount);
			if (mall != null && mall.getStatus() == Mall.STATUS_ENABLE && mall.getExtra() > 0) {
				if (goodsId == PayOrder.GOODS_ID_DIAMOND_MONTH_CARD || goodsId == PayOrder.GOODS_ID_GOLD_MONTH_CARD) {
					user.setFirstCurrency(mall.getExtra());
				}
			}

			// 其他类型保存首次充值的魂钻
			if (user.getFirstCurrency() == 0) {
				user.setFirstCurrency(gameCoin);
			}

			Map<String, String> hash = new HashMap<>();
			hash.put("firstCurrency", user.getFirstCurrency() + "");
			userDAO.updateUserCache(user.getUserId(), hash);

			// 保证离线用户也能拿到首冲
			userDAO.updateUser(user);
		}
	}

	@Override
	public List<UserCapVO> userRank(int rankTypeId) {
		switch (rankTypeId) {
		case 1:
			List<Integer> userIds = userDAO.gainUserLevel();
			if (userIds != null && !userIds.isEmpty()) {
				return createUserCapVOList(userTeamDAO.getUserCapsCache(userIds));
			}
			break;
		case 2:
			List<Integer> userIdsChapter = userDAO.gainChapterRankingTop10();
			if (userIdsChapter != null && !userIdsChapter.isEmpty()) {
				return createUserCapVOList(userTeamDAO.getUserCapsCache(userIdsChapter));
			}
			break;
		default:
			throw new GameException(GameException.CODE_未知排行类型, "rank");
		}
		return new ArrayList<UserCapVO>();
	}

	private List<UserCapVO> createUserCapVOList(List<UserCap> list) {
		List<UserCapVO> voList = new ArrayList<UserCapVO>();
		for (UserCap uc : list) {
			UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
			vo.init(uc);
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public List<Map<String, Object>> statisticsUserLevel() {
		List<Map<String, Object>> list = null;
		try {
			list = userDAO.statisticsUserLevel();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public CoinHandVO coinHand(int userId) {
		CoinHandVO vo = MessageFactory.getMessage(CoinHandVO.class);
		User user = getExistUserCache(userId);
		UserStat userStat = userStatDAO.queryUserStat(userId);
		VipPrivilege vip = GameCache.getVipPrivilege(user.getTotalCurrency());

		if (vip != null) {
			int maxCoinNum = vip.getCoinHandNum();
			int useCoinNum = userStat.getCoinHandNum();

			if (useCoinNum >= maxCoinNum) {
				throw new GameException(GameException.CODE_超出使用次数, "over flow max coin hand num");
			}

			CoinHand hand = GameCache.getCoinHandMap().get(useCoinNum + 1);

			List<CoinHandRule> list = GameCache.getCoinHandRuleMap().get(hand.getNum());
			CoinHandRule rule = (CoinHandRule) RandomUtil.getRandom(list);

			// 扣取魂钻
			decrementCurrency(user, hand.getPrice(), LoggerType.TYPE_点金手, "coin_hand");

			// 发放金币
			incrementGold(user, rule.getTimes() * hand.getBaseGold(), LoggerType.TYPE_点金手, "coin_hand");
			vo.setBaseGold(hand.getBaseGold());
			vo.setTimes(rule.getTimes());

			// 修改使用次数
			UserStatOpt opt = new UserStatOpt();
			opt.coinHandNum = UserStatOpt.EQUAL;
			userStat.setCoinHandNum(userStat.getCoinHandNum() + 1);
			userStatDAO.updateUserStat(opt, userStat);

			// 点金手任务
			// userMissionService.finishMissionCondition(user,
			// MissionCondition.TYPE_点金手, 0, 1);
		}
		return vo;
	}

	@Override
	public void issueVipWeekAward(UserStat userStat, int vipGrade) {
		List<Goods> goodsList = new ArrayList<Goods>();
		List<VipWeekAward> awards = GameCache.getVipWeekAwards(vipGrade);
		for (VipWeekAward vw : awards) {
			Goods goods = Goods.create(vw.getAssId(), vw.getType(), vw.getNum(), vw.getLevel());
			goodsList.add(goods);
		}
		Affiche affiche = Affiche.create(userStat.getUserId(), Affiche.AFFICHE_TYPE_充值送奖励, "vip特权奖励", "只有vip才能有的每周特权奖励，快点击领取吧！", goodsList, Affiche.STATE_未读,
		        "0");
		afficheService.addAffiche(affiche);

		UserStatOpt opt = new UserStatOpt();
		opt.vipAwardTime = SQLOpt.EQUAL;
		// 记录当前发奖的最后时间
		userStat.setVipAwardTime(DateUtils.getNextMonday());
		userStatDAO.updateUserStat(opt, userStat);
	}
	
	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public UserCap getUserCap(int userId) {
		// 队长
		UserCap result = userTeamDAO.getUserCapCache(userId);
		if (result == null) {
			User user = getExistUser(userId);
			if (user != null) {
				UserTeam userTeam = userTeamService.getExistUserTeam(userId, user.getCurrTeamId());
				// 队长战魂
				long capSoulId = userTeam.getPos().get(userTeam.getCap() - 1);

				UserSoul capSoul = userSoulDAO.queryUserSoul(userId, capSoulId);

				UserCap cap = new UserCap();
				cap.setUserId(user.getUserId());
				cap.setUserLevel(user.getLevel());
				cap.setPlayerName(user.getPlayerName());
				cap.setWant(new ArrayList<Integer>());
				cap.setCreateTime(new Date());

				cap.setUserSoulId(capSoul.getId());
				cap.setSoulId(capSoul.getSoulId());
				cap.setUg(0);
				cap.setExSkillCount(0);
				cap.setLevel(capSoul.getLevel());
				cap.setUpdateTime(new Date());
				cap.setEquipments(new ArrayList<Integer>());

				result = cap;
			}
		}
		return result;
	}

	@Override
    public List<Map<String, Object>> statisticsUserGuide() {
		List<Map<String, Object>> list = null;
		try {
			list = userDAO.statisticsUserGuide();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
    }
	
	/**
	 * 重置当日数据
	 * @param user
	 */
	private void resetSameDayData(User user){
		boolean reset = DateUtil.isBeforeToDay(user.getLastLoginTime());
		if (reset) {
			resetUserStat(user.getUserId());
			userChapterService.reset(user.getUserId());
			userStatDAO.reset(user.getUserId());
		}
	}

	@Override
    public void orderReturn(int partner, String userName, String orderNo, int currency, int extraCurrency) {
		User user = userDAO.findUserByUsername(userName, partner);
		if (user == null) {
			throw new GameException(GameException.CODE_用户不存在, " userName." + userName + " partner." + partner);
		}
		User userCa = getExistUser(user.getUserId());
		
		if (currency < 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		userAchieveService.addUserAchieveProcess(user.getUserId(), Achieve.TYPE_购买宝石, 0, currency);
		rechargeVipAward(user, currency); // vip等级奖励
		user.setTotalCurrency(user.getTotalCurrency() + currency);// 记录充值总数
		
		user.setCurrency(user.getCurrency() + extraCurrency + currency);
		Map<String, String> hash = new HashMap<>();
		hash.put("currency", String.valueOf(user.getCurrency()));
		hash.put("totalCurrency", String.valueOf(user.getTotalCurrency())); // 记录充值的总数

		userDAO.updateUserCache(user.getUserId(), hash);
		CurrencyLogger logger = CurrencyLogger.createCurrencyLogger(user.getUserId(), LoggerType.TYPE_订单返还, (currency + extraCurrency), 0, orderNo);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
	    
    }
	
}