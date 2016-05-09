package com.ks.logic.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.ks.exceptions.GameException;
import com.ks.logger.LoggerFactory;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.service.ActivityService;
import com.ks.logic.service.BaseService;
import com.ks.logic.utils.DateUtils;
import com.ks.model.ZoneConfig;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityGift;
import com.ks.model.activity.ActivityGiftRecord;
import com.ks.model.activity.ActivityPrice;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.CallSoulNotice;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.activity.OnTimeLoginGift;
import com.ks.model.activity.TotalLoginGift;
import com.ks.model.affiche.Affiche;
import com.ks.model.dungeon.DropRateMultiple;
import com.ks.model.game.Stat;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.goods.UserBakProp;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.MissionCondition;
import com.ks.model.mission.UserMission;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserSoulMap;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.activity.ActivityDefineVO;
import com.ks.protocol.vo.activity.ActivityGiftVO;
import com.ks.protocol.vo.activity.ActivityVO;
import com.ks.protocol.vo.activity.CallSoulNoticeVO;
import com.ks.protocol.vo.activity.ChargeGiftVO;
import com.ks.protocol.vo.activity.FlashGiftBagVO;
import com.ks.protocol.vo.activity.PointPriceVO;
import com.ks.protocol.vo.dungeon.DropRateMultipleVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.items.GoodsVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.util.DateUtil;
import com.ks.util.JSONUtil;

public class ActivityServiceImpl extends BaseService implements ActivityService {

	private static final Logger logger = LoggerFactory.get(ActivityServiceImpl.class);

	@Override
	public void getLoginGift(User user, UserStat stat) {
		if (stat.getOnTimeGiftTime() == null) {
			Date date = new Date(283968000000l); // 设置默认时间为 1979-01-01 00:00:00
			stat.setOnTimeGiftTime(date);
		}
		List<Affiche> affiches = new ArrayList<Affiche>();
		boolean first = DateUtil.isBeforeToDay(user.getLastLoginTime());
		if (!first) {
			return;
		}
		List<OnTimeLoginGift> onTimeGifts = activityCfgDAO.queryOnTimeLoginGifts();
		OnTimeLoginGift currGift = null;
		OnTimeLoginGift firstDay = null;
		if (!onTimeGifts.isEmpty()) {
			firstDay = onTimeGifts.get(0);
			Calendar startItme = Calendar.getInstance();
			startItme.setTime(firstDay.getStartTime());
			// 计算活动开始了多少天
			int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - startItme.get(Calendar.DAY_OF_YEAR) + 1;
			for (OnTimeLoginGift gift : onTimeGifts) {
				if (gift.getDay() == day) {
					currGift = gift;
					break;
				}
			}
			if (stat.getOnTimeGiftTime().before(firstDay.getStartTime())) {
				stat.setOnTimeLoginAward("");
			}
		}
		if (currGift != null && firstDay != null) {
			stat.setOnTimeLoginAward(currGift.getDay() + UserStat.SPLIT + stat.getOnTimeLoginAward());
			stat.setOnTimeGiftTime(new Date());
			Goods goods = Goods.create(currGift.getAssId(), currGift.getGoodsType(), currGift.getNum(), currGift.getGoodsLevel());
			List<Goods> goodsList = new ArrayList<Goods>();
			goodsList.add(goods);
			Affiche a = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_指定时间段登录, currGift.getTitle(), currGift.getContext(), goodsList, Affiche.STATE_未读, currGift.getLogo());
			affiches.add(a);
		} else {
			// 本月第一次重置数据
			Calendar month = Calendar.getInstance();
			month.set(Calendar.DAY_OF_MONTH, 1);
			month.set(Calendar.HOUR_OF_DAY, 0);
			month.set(Calendar.MINUTE, 0);
			month.set(Calendar.MILLISECOND, 0);
			if (user.getLastLoginTime().before(month.getTime())) {
				stat.setTotalLogin(0);
				stat.setTotalLoginAward("");
			}
			stat.setTotalLogin(stat.getTotalLogin() + 1);
			List<TotalLoginGift> gifts = activityCfgDAO.queryTotalLoginGifts();
			for (TotalLoginGift gift : gifts) {
				if (gift.getDay() == stat.getTotalLogin()) {
					Goods goods = Goods.create(gift.getAssId(), gift.getGoodsType(), gift.getNum(), gift.getGoodsLevel());
					List<Goods> goodsList = new ArrayList<Goods>();
					goodsList.add(goods);
					Affiche a = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_累计登录, gift.getTitle(), gift.getContext(), goodsList, Affiche.STATE_未读, gift.getLogo());
					affiches.add(a);
					stat.setTotalLoginAward(gift.getId() + UserStat.SPLIT + stat.getTotalLoginAward());
					break;
				}
			}
		}
		UserStatOpt opt = new UserStatOpt();
		int vipGrade = GameCache.getVipGrade(user.getTotalCurrency()); // 根据充值总数获得vip等级
		if (vipGrade > 0 && vipGrade <= 9) {
			long vipAwartiame = stat.getVipAwardTime().getTime();
			// 下一次发奖时间
			long nextMondayTime = DateUtils.getNextMonday().getTime();
			if (vipAwartiame < nextMondayTime) {
				/*List<Goods> goodsList = new ArrayList<Goods>();// vip等级6、7、8,9每周第一次登录要发奖
				List<VipWeekAward> awards = GameCache.getVipWeekAwards(vipGrade);
				for (VipWeekAward vw : awards) {
					Goods goods = Goods.create(vw.getAssId(), vw.getType(), vw.getNum(), vw.getLevel());
					goodsList.add(goods);
				}
				Affiche affiche = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_充值送奖励, "vip特权奖励", "只有vip才能有的每周特权奖励，快点击领取吧", goodsList, Affiche.STATE_未读, "0");
				afficheService.addAffiche(affiche);
				opt.vipAwardTime = SQLOpt.EQUAL;
				stat.setVipAwardTime(new Date(nextMondayTime));*/
				
				userService.issueVipWeekAward(stat, vipGrade);
			}

		}
		opt.onTimeGiftTime = SQLOpt.EQUAL;
		opt.onTimeLoginAward = SQLOpt.EQUAL;
		opt.totalLoginAward = SQLOpt.EQUAL;
		opt.totalLogin = SQLOpt.EQUAL;
		userStatDAO.updateUserStat(opt, stat);
		afficheService.addAffiches(affiches);
	}
	

	/**
	 * 获取 本周周日时间
	 * 
	 * @return
	 */
	public static long getSunday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_YEAR, 1); // 增加一个星期，才是我们中国人理解的本周日的日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 格式化日期
		String sunDayOfWeek = sdf.format(cal.getTime()) + " 23:59:59";
		long sunDayTime = 0;
		try {
			sunDayTime = sdf.parse(sunDayOfWeek).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sunDayTime;
	}

	@Override
	public List<OnTimeLoginGift> queryOnTimeGift() {
		return activityCfgDAO.queryOnTimeLoginGifts();
	}

	@Override
	public List<TotalLoginGift> queryTotalLoginGift() {
		return activityCfgDAO.queryTotalLoginGifts();
	}

	@Override
	public boolean activityIsStart(int defineId) {
		List<ActivityDefine> list = getActivityByDefineId(defineId);
		if(list == null || list.isEmpty()) {
			return false;
		}
		
		for (ActivityDefine entity : list) {
			if(activityIsStart(entity))
				return true;
		}
		return false;
	}

	@Override
	public void checkActivity(int defineId) {
		List<ActivityDefine> list = getActivityByDefineId(defineId);
		if (list == null || list.isEmpty()) {
			throw new GameException(GameException.CODE_活动不存在, " not define activity." + defineId);
		}
		
		boolean flag = false;
		for (ActivityDefine entity : list) {
			if(activityIsStart(entity.getDefineId())){
				flag = true;
			}
		}
		
		if (!flag) {
			throw new GameException(GameException.CODE_活动未开启, "activity not starting." + defineId);
		}
	}

	@Override
	public List<ActivityDefine> getStartingAc() {
		List<ActivityDefine> startings = new ArrayList<>();
		List<ActivityDefine> defines = activityDAO.queryAllActivityCache();
		/*for (ActivityDefine define : defines) {
			if (define.getDefineId() == ActivityDefine.DEFINE_ID_免费领体力 || define.getDefineId() == ActivityDefine.DEFINE_ID_送体力) {
				startings.add(define);
				continue;
			}
			
			
			if (activityIsStart(define)) {
				startings.add(define);
			}
		}*/
		
		Map<Integer, ActivityDefine> map = new TreeMap<>();
		
		for (ActivityDefine item : defines){
			ActivityDefine activity = map.get(item.getDefineId());
			if (activity == null) {
				map.put(item.getDefineId(), item);
			} else {
				if(activity.getEndTime().getTime() > item.getEndTime().getTime() && System.currentTimeMillis() > item.getEndTime().getTime()) {
					map.put(item.getDefineId(), item);
				}
			}
		}
		
		
		for (Map.Entry<Integer, ActivityDefine> entry : map.entrySet()){
			startings.add(entry.getValue());
		}
		return startings;
	}

	@Override
	public ActivityDefine getActivityById(int id) {
		return activityDAO.queryActivityCacheById(id);
	}

	@Override
	public List<ActivityDefineVO> getStartingAcVo() {
		List<ActivityDefine> defines = getStartingAc();
		List<ActivityDefineVO> dvos = new ArrayList<>();
		
		for (ActivityDefine define : defines) {
			ActivityDefineVO dvo = MessageFactory.getMessage(ActivityDefineVO.class);
			dvo.init(define);
			
			if (System.currentTimeMillis() <= define.getEndTime().getTime()) {
				//如果不是一天24小时开放
				if (!(define.getStartHour() == 0 && define.getEndHour() == 24)) {
					Calendar cStart = generateTodayCalendar(define.getStartHour(), 0, 0);
					dvo.setStartTime(cStart.getTimeInMillis());

					Calendar cEnd =  generateTodayCalendar(define.getEndHour(), 0, 0);
					dvo.setEndTime(cEnd.getTimeInMillis());
				} else {
					//如果不是一周7天不间断
					if (define.getWeekTime().split("_").length < 7 && ActivityDefine.DEFINE_ID_免费领体力 != define.getDefineId() && ActivityDefine.DEFINE_ID_送体力 != define.getDefineId()) {
						Calendar cEnd = generateTodayCalendar(0, 0, 0);
						while (true) {
							// 增加一天时间(至次日0时)
							cEnd.add(Calendar.DAY_OF_YEAR, 1);
							//这个时间是否已结束
							if (!activityIsStart(cEnd, define)) {
								dvo.setEndTime(cEnd.getTimeInMillis());
								break;
							}
						}
					}

				}
			}
			dvos.add(dvo);
		}
		return dvos;
	}
	
	/**
	 * 根据所给时分秒获取一个当日Calendar
	 * @param hour
	 * @param minute
	 * @param secend
	 * @return
	 */
	private Calendar generateTodayCalendar(int hour, int minute, int secend){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, secend);
		return calendar;
	}

	@Override
	public List<ActivityDefine> bossGetAllAc() {
		return activityDAO.queryAllActivityCache();
	}

	@Override
	public void updateActivity(ActivityDefine d) {
		activityDAO.updateActivityCache(d);
	}

	@Override
	public void updateAcCache(ActivityDefine ad) {
		activityDAO.updateActivityCache(ad);
	}

	@Override
	public void initActivity() {
		activityDAO.reloadActivityCache();
	}

	@Override
	public ChargeGiftVO getChargeAcInfo(int userId) {
		int myCoin = userStatDAO.queryUserStat(userId).getAcChageTotal();
		List<GoodsVO> goodVos = new ArrayList<GoodsVO>();
		BuyCoinGift gift = activityDAO.getBuyCoingift();
		ChargeGiftVO vo = MessageFactory.getMessage(ChargeGiftVO.class);
		if (gift != null) {
			vo.setGameCoin(gift.getGameCoin());
			vo.setTotalCurrency(myCoin);
			for (Goods g : gift.getGoods()) {
				GoodsVO gv = MessageFactory.getMessage(GoodsVO.class);
				gv.init(g);
				goodVos.add(gv);
			}
			vo.setGoods(goodVos);
		}
		return vo;
	}

	@Override
	public FlashGiftBagVO getFlashGiftBag() {
		FlashGiftBag bag = activityDAO.getFlashGift();
		FlashGiftBagVO bvo = MessageFactory.getMessage(FlashGiftBagVO.class);
		List<GoodsVO> list = new ArrayList<>();
		for (Goods good : bag.getGoods()) {
			GoodsVO gvo = MessageFactory.getMessage(GoodsVO.class);
			gvo.init(good);
			list.add(gvo);
		}
		bvo.init(bag, list);
		return bvo;
	}

	@Override
	public List<DropRateMultiple> queryDropRateMultipleList() {
		return chapterDAO.queryAllDropRateMultipleCache();
	}

	@Override
	public void updateDropRateMultiple(DropRateMultiple drm) {
		chapterDAO.updateDropRateMultipleCache(drm);
	}

	@Override
	public void addDropRateMultiple(DropRateMultiple drm) {
		// chapterDAO.addDropRateMultiple(drm);
		chapterDAO.addDropRateMultipleCache(drm);
	}

	@Override
	public void deleteDropRateMultiple(int id) {
		chapterDAO.deleteDropRateMultiple(id);
	}

	@Override
	public CallSoulNoticeVO getActivitySoulId() {
		CallSoulNoticeVO vo = MessageFactory.getMessage(CallSoulNoticeVO.class);
		int soulId = 0;
		int defineId = 0;
		if (activityIsStart(ActivityDefine.DEFINE_ID_友情召唤出指定战魂)) {
			defineId = ActivityDefine.DEFINE_ID_友情召唤出指定战魂;
			DropRateMultiple drm = chapterDAO.queryDropRateMultiple(ActivityDefine.DEFINE_ID_友情召唤出指定战魂);
			if (drm != null) {
				soulId = drm.getSiteId();
			}
		}
		
		if (activityIsStart(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂)) {
			DropRateMultiple drm = chapterDAO.queryDropRateMultiple(ActivityDefine.DEFINE_ID_魂币召唤出指定战魂);
			defineId = ActivityDefine.DEFINE_ID_魂币召唤出指定战魂;
			if (drm != null) {
				soulId = drm.getSiteId();
			}
		}
		
		
		
		List<ActivityDefine> list = getActivityByDefineId(defineId);
		long now = System.currentTimeMillis();
		for (ActivityDefine entity : list) {
			if (now > entity.getStartTime().getTime() && entity.getEndTime().getTime() > now) {
				CallSoulNotice csn = new CallSoulNotice();
				csn.setEndTime(entity.getEndTime());
				csn.setStartTime(entity.getStartTime());
				csn.setTitle(entity.getTitle());
				csn.setContext(entity.getContext());
				csn.setDefineId(defineId);
				vo.init(csn, soulId);
				break;
			}
		}
		return vo;
	}

	@Override
	public PointPriceVO getPointPrice() {
		PointPriceVO vo = MessageFactory.getMessage(PointPriceVO.class);
		ZoneConfig zonfig = activityDAO.queryZoneConfig(ZoneConfig.ID_USER_ID_SEED);

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_战魂仓库格子打折)) {
			vo.setSoulCapacityPrice(zonfig.getAcSoulCapacityPrice());
		} else {
			vo.setSoulCapacityPrice(User.USER_扩展战魂仓库价格);
		}

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_道具仓库格子打折)) {
			vo.setItemCapacityPrice(zonfig.getAcItemCapacityPrice());
		} else {
			vo.setItemCapacityPrice(User.USER_扩展家园上线价格);
		}

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_好友上限数量打折)) {
			vo.setFriendCapacityPrice(zonfig.getAcFriendCapacityPrice());
		} else {
			vo.setFriendCapacityPrice(User.USER_提升好友上线价格);
		}

		// 此处计算是百分比
		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_体力打折)) {
			vo.setStaminaPrice(zonfig.getAcStaminaPrice());
		} else {
			vo.setStaminaPrice(1);
		}

		if (activityService.activityIsStart(ActivityDefine.DEFINE_ID_竞技点打折)) {
			vo.setAthleticsPoint(zonfig.getAcAthleticsPoint());
		} else {
			vo.setAthleticsPoint(User.USER_恢复竞技点价格);
		}
		return vo;
	}

	@Override
	public BuyCoinGift getBuyCoinGift() {
		return activityDAO.getBuyCoingift();
	}

	@Override
	public ActivityPrice getActivityPrices() {
		return activityDAO.getActivityPrice();
	}

	@Override
	public void updateActivityPrice(ActivityPrice price) {
		activityDAO.updateActivityPrice(price);
	}

	@Override
	public void updateBuyConGift(ActivityDefine ad, BuyCoinGift gift, boolean clearData) {
		activityService.updateActivity(ad);
		activityDAO.updateBuyCoinGift(gift);
		if (clearData) {
			userStatDAO.resetAcChargeCoin();
		}
	}

	@Override
	public FlashGiftBag getFlashGiftBagInfo() {
		return activityDAO.getFlashGift();
	}

	@Override
	public void updateFlashGiftBag(ActivityDefine ad, FlashGiftBag bag) {
		activityService.updateActivity(ad);
		activityDAO.updateFlashGiftBag(bag);
	}

	@Override
	public List<DropRateMultipleVO> queryDropRateMultipleListBySite() {
		List<DropRateMultipleVO> voList = new ArrayList<DropRateMultipleVO>();
		List<DropRateMultiple> drmList = chapterDAO.queryAllDropRateMultipleCache();
		List<ActivityDefine> define = activityDAO.queryAllActivityCache();
		for (DropRateMultiple drm : drmList) {
			switch (drm.getDefineId()) {
			case ActivityDefine.DEFINE_ID_关卡产出概率翻倍:
			case ActivityDefine.DEFINE_ID_体力消耗减半:
			case ActivityDefine.DEFINE_ID_乱入概率翻倍:
			case ActivityDefine.DEFINE_ID_友情点翻倍:
			case ActivityDefine.DEFINE_ID_强化技能概率:
			case ActivityDefine.DEFINE_ID_召唤战魂概率:
				if (activityIsStart(getDefine(define, drm.getDefineId()))) {
					DropRateMultipleVO drmVO = MessageFactory.getMessage(DropRateMultipleVO.class);
					drmVO.init(drm);
					voList.add(drmVO);
				}
				break;

			default:
				break;
			}
		}
		return voList;
	}

	private ActivityDefine getDefine(List<ActivityDefine> define, int defineId) {
		for (ActivityDefine def : define) {
			if (def.getDefineId() == defineId) {
				return def;
			}
		}
		return null;
	}

	@Override
	public void initDropRateMultiple() {
		chapterDAO.initDropRateMultipleCache();
	}

	@Override
	public boolean activityIsStart(ActivityDefine define) {
		if (define == null) {
			return false;
		}
		// 时间
		Calendar c = Calendar.getInstance();
		return activityIsStart(c, define);
	}

	private boolean activityIsStart(Calendar c, ActivityDefine define) {
		boolean isStart = false;
		if (c.getTime().after(define.getStartTime()) && c.getTime().before(define.getEndTime())) {
			isStart = true;
		} else {
			isStart = false;
		}
		// 小时
		if (isStart) {
			int hour = c.get(Calendar.HOUR_OF_DAY);
			if (define.getStartHour() <= hour && hour < define.getEndHour()) {
				isStart = true;
			} else {
				isStart = false;
			}
		}
		// 周
		if (isStart) {
			int week = c.get(Calendar.DAY_OF_WEEK);
			// 转换成中国星期习惯
			week = week == 1 ? 7 : week - 1;
			if (define.isWeekTime(week)) {
				isStart = true;
			} else {
				isStart = false;
			}
		}
		return isStart;
	}

	@Override
	public ActivityVO queryActivityGift(int userId, int defineId) {
		ActivityDefine ad = getCurrentActivityByDefineId(defineId);
		User user = userService.getExistUserCache(userId);
		UserStat userStat = userStatDAO.queryUserStat(userId);

		if (ad == null || user == null || userStat == null) {
			throw new GameException(GameException.CODE_参数错误, "definedId=" + defineId + ", userId=" + userId);
		}

		// 查询指定活动礼包
		List<ActivityGiftVO> voList = new ArrayList<ActivityGiftVO>();
		for (ActivityGift gift : activityGiftDAO.queryActivityGiftByDefineId(defineId)) {
			if (gift.getGift() != null && !gift.getGift().trim().equals("")) {
				ActivityGiftVO vo = MessageFactory.getMessage(ActivityGiftVO.class);
				vo.initActivityGiftVO(gift, createGoodsVOList(gift.getGift()));
				voList.add(vo);
			}
		}

		byte status = ActivityGiftVO.STATUS_UNSATISFY;
		// 用户礼包领取状态
		for (ActivityGiftVO vo : voList) {
			status = ActivityGiftVO.STATUS_UNSATISFY;
			switch (vo.getActivityDefineId()) {
			case ActivityDefine.DEFINE_ID_连续登陆活动:
				// 连续登陆活动未开始或结束，直接把用户领取记录和连续登陆数置为初始值
				String loginMark = userStat.getContinuousLoginGiftMark();
				long now = System.currentTimeMillis();
				if( now < ad.getStartTime().getTime() || now > ad.getEndTime().getTime()){
					loginMark = "0";
					userStat.setContinuousLoginGiftMark(loginMark);
					user.setUninterruptedLoginCount(1);
					
					UserStatOpt opt = new UserStatOpt();
					opt.continuousLoginGiftMark = SQLOpt.EQUAL;
					userStatDAO.updateUserStat(opt, userStat);
					
					Map<String, String> hash = new HashMap<>();
					hash.put("uninterruptedLoginCount", String.valueOf(user.getUninterruptedLoginCount()));
					userDAO.updateUserCache(userId, hash);
				}
				
				// 连续登陆一个周期循环领取问题
				// 连续登陆奖励，超过一个周期，把所有领取记录和连续登陆次数置为初始值
				int continueLoginCycle = voList.size() + 1;
				int continueLoginCountKey = Integer.parseInt(vo.getKey1());

				if (user.getUninterruptedLoginCount() < continueLoginCycle) {
					int loginCount = user.getUninterruptedLoginCount() % continueLoginCycle;
					if (loginCount >= continueLoginCountKey) {
						status = checkStringMark(loginMark, continueLoginCountKey) ? ActivityGiftVO.STATUS_GET : ActivityGiftVO.STATUS_NO_GET;
					}
				} else {
					// 超过一个登陆周期，把领取奖励的记录和连续登陆记录都置为初始化
					if (loginMark != null && !loginMark.equals("") && !loginMark.equals("0")) {
						loginMark = "0";
						userStat.setContinuousLoginGiftMark(loginMark);
						user.setUninterruptedLoginCount(1);

						UserStatOpt opt = new UserStatOpt();
						opt.continuousLoginGiftMark = SQLOpt.EQUAL;
						userStatDAO.updateUserStat(opt, userStat);

						Map<String, String> hash = new HashMap<>();
						hash.put("uninterruptedLoginCount", String.valueOf(user.getUninterruptedLoginCount()));
						userDAO.updateUserCache(userId, hash);
					}

					int loginCount = user.getUninterruptedLoginCount() % continueLoginCycle;
					if (loginCount >= continueLoginCountKey) {
						status = checkStringMark(loginMark, continueLoginCountKey) ? ActivityGiftVO.STATUS_GET : ActivityGiftVO.STATUS_NO_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_冲级活动:
				String levelMark = userStat.getLevelGiftMark();
				int levelKey = Integer.parseInt(vo.getKey1());
				if (user.getLevel() >= levelKey) {
					status = checkStringMark(levelMark, levelKey) ? ActivityGiftVO.STATUS_GET : ActivityGiftVO.STATUS_NO_GET;
				}
				break;
			case ActivityDefine.DEFINE_ID_首充活动:
				if (user.getFirstCurrency() > 0) {
					List<ActivityGiftRecord> list = activityGiftRecordDAO.queryActivityGiftRecord(userId, vo.getId());
					if (list == null || list.isEmpty()) {
						status = ActivityGiftVO.STATUS_NO_GET;
					} else {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_限时礼包:
				if (userStat.getActivityRechargeCurrency() >= Integer.parseInt(vo.getKey1())) {
					List<ActivityGiftRecord> list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), vo.getId());
					if (list == null || list.isEmpty()) {
						status = ActivityGiftVO.STATUS_NO_GET;
					} else {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_每日充值送豪礼:
				int rechargeCountKey = Integer.parseInt(vo.getKey1());
				if (userStat.getActivityContinuousRechargeCount() >= rechargeCountKey) {
					if ((userStat.getActivityContinuousRechargeGetMark() & (1 << (rechargeCountKey-1))) == 0) {
						status = ActivityGiftVO.STATUS_NO_GET;
					} else {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_收集送礼:
				// 查看图鉴
				// 查看礼包领取状态
				int soulId = Integer.parseInt(vo.getKey1());
				UserSoulMap soulMap = userSoulMapDAO.getUserMapSoul(userId, soulId);
				
				if (soulMap != null) {
					List<ActivityGiftRecord> list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), vo.getId());
					if (list == null || list.isEmpty()) {
						status = ActivityGiftVO.STATUS_NO_GET;
					} else {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_消费送礼:
				int giftTotalCostKey = Integer.parseInt(vo.getKey1());
				if (userStat.getActivityTotalCostCurrency() >= giftTotalCostKey) {
					List<ActivityGiftRecord> list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), vo.getId());
					if (list == null || list.isEmpty()) {
						status = ActivityGiftVO.STATUS_NO_GET;
					} else {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_全民福利:
				// 有多少人买过成长基金
				long userBuyGrowFundCount = statDAO.findById(Stat.ID_BUY_GROW_FUND_COUNT).getValue();
				// 礼包基金次数
				int buyGrowFoundCount = Integer.parseInt(vo.getKey1());
				if (userBuyGrowFundCount >= buyGrowFoundCount) {
					List<ActivityGiftRecord> list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), vo.getId());
					if (list == null || list.isEmpty()) { 
						status = ActivityGiftVO.STATUS_NO_GET;
					} else {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_七天送礼:
				int[] sevenGiftIds = new int[voList.size()];
				for (int i=0; i<sevenGiftIds.length; i++) {
					sevenGiftIds[i] = voList.get(i).getId();
				}
				List<ActivityGiftRecord> _list = activityGiftRecordDAO.queryActivityGiftRecord(userId, sevenGiftIds);
				
				// 用户领取过几天的礼包
				int userGetCount =  _list == null ? 0 : _list.size();
				// 第几次礼包
				int giftSevenDayGift = Integer.parseInt(vo.getKey1());
				
				// 第一天未领取
				if (userGetCount == 0) {
					if (giftSevenDayGift == 1) {
						status = ActivityGiftVO.STATUS_NO_GET;	
					}
				} else {
					if (userGetCount >= giftSevenDayGift) {
						status = ActivityGiftVO.STATUS_GET;
					} else {
						ActivityGiftRecord agr = _list.get(_list.size() - 1);
						if (vo.getId() - agr.getActivityGiftId() == 1) {
							int diffDays = DateUtils.dateDiff(new Date(), agr.getCreateTime());
							if (diffDays > 0) {
								status = ActivityGiftVO.STATUS_NO_GET;
							}
						}
					}
				}
				break;
			case ActivityDefine.DEFINE_ID_开服活动:
				// 首次创建账号 7 天内的任务, 七天后失效
				Date startDate = user.getCreateTime();
				int startDay = DateUtils.dateDiff(new Date(), startDate) + 1;
				List<ActivityGiftRecord> kfList = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), vo.getId());
				
				int sevenOfDayKey = Integer.parseInt(vo.getKey1());
				int missionId = Integer.parseInt(vo.getKey2());
				
				if (startDay * 100 > sevenOfDayKey) {
					// 过去状态，有记录等于领取过，未领取等于未完成
					if (kfList != null && !kfList.isEmpty()) {
						status = ActivityGiftVO.STATUS_GET;
					}
				}
				
				// 当前天
				if (startDay * 100 < sevenOfDayKey && (startDay + 1)*100 > sevenOfDayKey) {
					if (kfList != null && !kfList.isEmpty()) {
						status = ActivityGiftVO.STATUS_GET;
					} else {
						// 判断任务是否可以完成
						UserMission userMission = userMissionDAO.getMissionFromCache(userId, missionId);
						if(userMissionService.checkMissionCoindition(userMission)){
							status = ActivityGiftVO.STATUS_NO_GET;
						}
					}
				}
				// 未来， 默认不可以未满足状态
				break;
			case ActivityDefine.DEFINE_ID_七天额外奖励:
				List<ActivityGiftRecord> extraList = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), vo.getId());
				if (extraList != null && !extraList.isEmpty()) {
					status = ActivityGiftVO.STATUS_GET;
				} else {
					// 七天所有任务记录奖励
//					int[] giftIds = {1001405, 1001410, 1001415, 1001420, 1001425, 1001430, 1001435};
					int[] giftIds = {1001406, 1001412, 1001418, 1001424, 1001430, 1001436, 1001442};
					int count = activityGiftRecordDAO.queryActivityGiftRecordCount(userId, giftIds);
					if (count == 7) {
						status = ActivityGiftVO.STATUS_NO_GET;
					}
				}
				break;
			default:
				break;
			}
			vo.setStatus(status);
		}

		ActivityVO vo = MessageFactory.getMessage(ActivityVO.class);
		vo.initActivityVO(ad, voList);
		return vo;
	}

	@Override
	public UserAwardVO getActivityGift(int userId, int activityGiftId, int defineId) {
		User user = userService.getExistUserCache(userId);
		ActivityDefine activityDefine = getCurrentActivityByDefineId(defineId);
		ActivityGift gift = activityGiftDAO.queryActivityGiftById(activityGiftId);
		if (activityDefine == null || gift == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		if (!activityIsStart(activityDefine)) {
			throw new GameException(GameException.CODE_活动未开启, "");
		}

		// 判断奖励是否领取
		UserStat userStat = userStatDAO.queryUserStat(userId);
		if (userStat == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		List<ActivityGiftRecord> list;
		// 不同活动，分条件逻辑处理
		switch (gift.getActivityDefineId()) {
		case ActivityDefine.DEFINE_ID_连续登陆活动:
			String loginMark = userStat.getContinuousLoginGiftMark();
			int loginCountKey = Integer.parseInt(gift.getKey1());
			// 判断连续登陆领取标记，发放礼包，修改标记

			int continueLoginCycle = activityGiftDAO.queryActivityGiftByDefineId(defineId).size() + 1;

			int userLoginCount = user.getUninterruptedLoginCount() % continueLoginCycle;
			if (userLoginCount >= loginCountKey) {
				if (!checkStringMark(loginMark, loginCountKey)) {
					userStat.setContinuousLoginGiftMark(updateStringMark(loginMark, loginCountKey));
					UserStatOpt opt = new UserStatOpt();
					opt.continuousLoginGiftMark = SQLOpt.EQUAL;
					userStatDAO.updateUserStat(opt, userStat);
				} else {
					throw new GameException(GameException.CODE_礼包以领过, "");
				}
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_冲级活动:
			String levelMark = userStat.getLevelGiftMark();
			// 判断等级是否达到和冲级领取标记，发放礼包，修改标记
			int giftUserLevel = Integer.parseInt(gift.getKey1());
			if (user.getLevel() >= giftUserLevel && !checkStringMark(levelMark, giftUserLevel)) {
				userStat.setLevelGiftMark(updateStringMark(levelMark, giftUserLevel));
				UserStatOpt opt = new UserStatOpt();
				opt.levelGiftMark = SQLOpt.EQUAL;
				userStatDAO.updateUserStat(opt, userStat);
			} else {
				throw new GameException(GameException.CODE_礼包以领过, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_限时礼包:
			if (userStat.getActivityRechargeCurrency() >= Integer.parseInt(gift.getKey1())) {
				list = activityGiftRecordDAO.queryActivityGiftRecord(userId, activityGiftId);
				if (list != null && !list.isEmpty()) {
					throw new GameException(GameException.CODE_礼包以领过, "");
				}
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_首充活动:
			// 判断是否发过奖励
			if (user.getFirstCurrency() == 0) {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}

			list = activityGiftRecordDAO.queryActivityGiftRecord(userId, activityGiftId);
			if (list != null && !list.isEmpty()) {
				throw new GameException(GameException.CODE_礼包以领过, "");
			}
			// 三倍魂钻
			break;
		case ActivityDefine.DEFINE_ID_每日充值送豪礼:
			int continuousreChargerCountKey = Integer.parseInt(gift.getKey1());
			
			if (continuousreChargerCountKey > userStat.getActivityContinuousRechargeCount()) {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			
			if (((1 << (continuousreChargerCountKey -1 )) & userStat.getActivityContinuousRechargeGetMark()) == 1) {
				throw new GameException(GameException.CODE_礼包以领过, "");
			} else {
				UserStatOpt opt = new UserStatOpt();
				opt.activityContinuousRechargeGetMark = SQLOpt.EQUAL;
				userStat.setActivityContinuousRechargeGetMark(userStat.getActivityContinuousRechargeGetMark() | (1 << (continuousreChargerCountKey -1 )));
				
				// 领取满7天，领取标记置为0，次数置为0
				if (userStat.getActivityContinuousRechargeGetMark() == 127){
					userStat.setActivityContinuousRechargeCount(0);
					userStat.setActivityContinuousRechargeGetMark(0);
					opt.activityContinuousRechargeCount = SQLOpt.EQUAL;
				}
				userStatDAO.updateUserStat(opt, userStat);
			}
			break;
		case ActivityDefine.DEFINE_ID_收集送礼:
			int soulId = Integer.parseInt(gift.getKey1());
			UserSoulMap soulMap = userSoulMapDAO.getUserMapSoul(userId, soulId);
			
			if (soulMap != null) {
				list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), gift.getId());
				if (list != null && !list.isEmpty()) {
					throw new GameException(GameException.CODE_礼包以领过, "");
				}
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_消费送礼:
			int giftTotalCostKey = Integer.parseInt(gift.getKey1());
			if (userStat.getActivityTotalCostCurrency() >= giftTotalCostKey) {
				list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), gift.getId());
				if (list != null && !list.isEmpty()) {
					throw new GameException(GameException.CODE_礼包以领过, "");
				}
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_在线礼包:
			int giftOnlineGiftGetCountKey = Integer.parseInt(gift.getKey1());
			//Date dateFrom = DateUtil.getTodayZero().getTime();
			//Date dateTo = DateUtil.getTodayLastSecond().getTime();
			//list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), gift.getId(), dateFrom, dateTo);
			//if (list != null && !list.isEmpty()) {
			//	throw new GameException(GameException.CODE_礼包以领过, "");
			//}
			
			if (giftOnlineGiftGetCountKey - userStat.getOnlineGiftGetCount() == 0) {
				throw new GameException(GameException.CODE_礼包以领过, "");
			}
			
			if (giftOnlineGiftGetCountKey - userStat.getOnlineGiftGetCount() >= 1){
				userStat.setOnlineGiftGetCount(userStat.getOnlineGiftGetCount() + 1);
				UserStatOpt opt = new UserStatOpt();
				opt.onlineGiftGetCount = SQLOpt.EQUAL;
				userStatDAO.updateUserStat(opt, userStat);
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_全民福利:
			// 有多少人买过成长基金
			long userBuyGrowFundCount = statDAO.findById(Stat.ID_BUY_GROW_FUND_COUNT).getValue();
			// 礼包基金次数
			int buyGrowFundCount = Integer.parseInt(gift.getKey1());
			if (userBuyGrowFundCount >= buyGrowFundCount) {
				list = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), gift.getId());
				if (list != null && !list.isEmpty()) {
					throw new GameException(GameException.CODE_礼包以领过, "");
				}
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_七天送礼:
			List<ActivityGift> giftsList = activityGiftDAO.queryActivityGiftByDefineId(defineId);
			int[] sevenGiftIds = new int[giftsList.size()];
			for (int i=0; i<sevenGiftIds.length; i++) {
				sevenGiftIds[i] = giftsList.get(i).getId();
			}
			list = activityGiftRecordDAO.queryActivityGiftRecord(userId, sevenGiftIds);
			// 用户领取过几天的礼包
			int userGetCount =  list == null ? 0 : list.size();
			// 第几次礼包
			int giftSevenDayGift = Integer.parseInt(gift.getKey1());
			
			// 第一天未领取
			if (userGetCount == 0) {
			} else {
				if (userGetCount >= giftSevenDayGift) {
					throw new GameException(GameException.CODE_礼包以领过, "");
				} else {
					ActivityGiftRecord agr = list.get(list.size() - 1);
					if (gift.getId() - agr.getActivityGiftId() == 1) {
						int diffDays = DateUtils.dateDiff(new Date(), agr.getCreateTime());
						if (diffDays > 0) {
						} else {
							throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
						}
					}
				}
			}
			break;
		case ActivityDefine.DEFINE_ID_开服活动:
			Date startDate = user.getCreateTime();
			int startDays = DateUtils.dateDiff(new Date(), startDate) + 1;
			List<ActivityGiftRecord> kfList = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), gift.getId());
			
			int sevenOfDayKey = Integer.parseInt(gift.getKey1());
			int missionId = Integer.parseInt(gift.getKey2());
			
			// 当前天
			if (startDays * 100 < sevenOfDayKey && (startDays + 1)*100 > sevenOfDayKey) {
				if (kfList != null && !kfList.isEmpty()) {
					throw new GameException(GameException.CODE_礼包以领过, "");
				} else {
					// 判断任务是否可以完成
					UserMission userMission = userMissionDAO.getMissionFromCache(userId, missionId);
					if(!userMissionService.checkMissionCoindition(userMission)){
						throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
					}
					userMissionService.finishMissionCondition(user, MissionCondition.TYPE_开服活动任务, 0, 1);
					userMissionService.missionAward(userMission.getUserId(), userMission.getMissionId());
				}
			} else {
				throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
			}
			break;
		case ActivityDefine.DEFINE_ID_七天额外奖励:
			List<ActivityGiftRecord> extraList = activityGiftRecordDAO.queryActivityGiftRecord(user.getUserId(), gift.getId());
			if (extraList != null && !extraList.isEmpty()) {
				throw new GameException(GameException.CODE_礼包以领过, "");
			} else {
				// 七天所有任务记录奖励
//				int[] giftIds = {1001405, 1001410, 1001415, 1001420, 1001425, 1001430, 1001435};
				int[] giftIds = {1001406, 1001412, 1001418, 1001424, 1001430, 1001436, 1001442};
				int count = activityGiftRecordDAO.queryActivityGiftRecordCount(userId, giftIds);
				if (count != 7) {
					throw new GameException(GameException.CODE_活动礼包领取条件不满足, "");
				}
			}
			break;
		default:
			throw new GameException(GameException.CODE_活动领取未实现, "");
		}

		// 保存礼包领取记录，用于查询 (部分活动判断不是通过领取记录)
		activityGiftRecordDAO.addActivityGiftRecord(new ActivityGiftRecord(0, user.getUserId(), gift.getId(), new Date()));
		return issueGift(user, gift);
	}

	@Override
	public List<ActivityGift> queryAllActivityGift() {
		return activityGiftDAO.queryAllActivityGift();
	}

	@Override
	public UserAwardVO issueGift(User user, ActivityGift gift) {
		List<Goods> list = JSONUtil.toObject(gift.getGift(), new TypeReference<List<Goods>>() {
		});

		if (list == null || list.isEmpty()) {
			throw new GameException(GameException.CODE_活动礼包不能为空, "");
		}

		Backage backage = userGoodsService.getPackage(user.getUserId());
		List<Goods> goodsList = new ArrayList<Goods>();
		UserAwardVO ua = MessageFactory.getMessage(UserAwardVO.class);
		ua.setDeleteSouls(new ArrayList<UserSoulVO>());
		ua.setBakProp(new ArrayList<UserBakPropVO>());
		ua.setUserGoodses(new ArrayList<UserGoodsVO>());
		ua.setUserSouls(new ArrayList<UserSoulVO>());

		// 首冲三倍魂钻
		if (gift.getActivityDefineId() == ActivityDefine.DEFINE_ID_首充活动 && user.getFirstCurrency() > 0) {
			list.add(Goods.create(0, 6, user.getFirstCurrency() * 2, 1));
		}

		List<Goods> soulGoods = new ArrayList<>();
		for (Goods g : list) {
			switch (g.getType()) {
			case Goods.TYPE_SOUL:
				soulGoods.add(g);
				break;
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				goodsList.add(g);
				break;
			case Goods.TYPE_GOLD:
				userService.incrementGold(user, g.getNum(), getLoggerType(gift.getActivityDefineId(), g), "activity");
				break;
			case Goods.TYPE_CURRENCY:
				userService.incrementCurrency(user, g.getNum(), getLoggerType(gift.getActivityDefineId(), g), "activity");
				break;
			case Goods.TYPE_EXP:
				userService.incrementExp(user, g.getNum(), getLoggerType(gift.getActivityDefineId(), g), "activity", null);
				break;
			case Goods.TYPE_FRIENDLY_POINT:
				userService.incrementFriendlyPoint(user.getUserId(), g.getNum(), getLoggerType(gift.getActivityDefineId(), g), "activity");
				break;
			case Goods.TYPE_BAK_PROP:
				userGoodsService.addBakProps(user, g.getGoodsId(), g.getNum(), getLoggerType(gift.getActivityDefineId(), g), "activity");
				UserBakProp prop = UserBakProp.create(user.getUserId(), g.getGoodsId(), g.getNum());
				UserBakPropVO propVo = MessageFactory.getMessage(UserBakPropVO.class);
				propVo.init(prop);
				ua.getBakProp().add(propVo);
				break;
			default:
				break;
			}
		}
		UserStat stat = userStatDAO.queryUserStat(user.getUserId());
		if (goodsList.size() != 0) {
			userGoodsService.checkBackageFull(backage, user);
			ua.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), goodsList, goodsList.get(0).getType(), "activity"));

		}
		if (!soulGoods.isEmpty()) {
			userSoulService.checkSoulFull(user);
			for (Goods goods : soulGoods) {
				for(int i=1; i<=goods.getNum(); i++){
					UserSoul soul = userSoulService.addUserSoul(user, goods.getGoodsId(), goods.getLevel(), goods.getType());
					userSoulService.checkSoulFull(user);
					UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
					vo.init(soul);
					ua.getUserSouls().add(vo);
				}
			}
		}
		ua.setCurrency(user.getCurrency());
		ua.setGold(user.getGold());
		ua.setFriendlyPoint(stat.getFriendlyPoint());
		ua.setExp(user.getExp());
		ua.setLevel(user.getLevel());
		return ua;
	}

	private List<GoodsVO> createGoodsVOList(String jsonGoodsList) {
		List<GoodsVO> listVo = new ArrayList<GoodsVO>();
		List<Goods> goodsList = JSONUtil.toObject(jsonGoodsList, new TypeReference<List<Goods>>() {
		});

		if (goodsList != null && !goodsList.isEmpty()) {
			for (Goods goods : goodsList) {
				listVo.add(createGoodsVO(goods));
			}
		}
		return listVo;
	}

	private GoodsVO createGoodsVO(Goods goods) {
		GoodsVO vo = MessageFactory.getMessage(GoodsVO.class);
		vo.setGoodsId(goods.getGoodsId());
		vo.setLevel(goods.getLevel());
		vo.setNum(goods.getNum());
		vo.setType(goods.getType());
		return vo;
	}

	
	public int getLoggerType(int activityDefineId, Goods goods) {
		int logType = 0;

		// 新增 define_id 5011 以后的才会在此处处理
		switch (activityDefineId) {
		case ActivityDefine.DEFINE_ID_冲级活动:
			logType = LoggerType.TYPE_冲级活动;
			break;
		case ActivityDefine.DEFINE_ID_连续登陆活动:
			logType = LoggerType.TYPE_连续登陆活动;
			break;
		case ActivityDefine.DEFINE_ID_限时礼包:
			logType = LoggerType.TYPE_累计充值活动;
			break;
		case ActivityDefine.DEFINE_ID_首充活动:
			logType = LoggerType.TYPE_首充活动;
			break;
		case ActivityDefine.DEFINE_ID_冲榜赛活动:
			logType = LoggerType.TYPE_冲榜赛活动;
			break;
		case ActivityDefine.DEFINE_ID_每日充值送豪礼:
			logType = LoggerType.TYPE_每日充值送豪礼;
			break;
		case ActivityDefine.DEFINE_ID_收集送礼:
			logType = LoggerType.TYPE_收集送礼;
			break;
		case ActivityDefine.DEFINE_ID_消费送礼:
			logType = LoggerType.TYPE_消费送礼;
			break;
		case ActivityDefine.DEFINE_ID_在线礼包:
			logType = LoggerType.TYPE_在线礼包;
			break;
		case ActivityDefine.DEFINE_ID_每天登录送礼包:
			logType = LoggerType.TYPE_每天登录送礼包;
			break;
		case ActivityDefine.DEFINE_ID_全民福利:
			logType = LoggerType.TYPE_全民福利;
			break;
		case ActivityDefine.DEFINE_ID_七天送礼:
			logType = LoggerType.TYPE_七天送礼;
			break;
		case ActivityDefine.DEFINE_ID_开服活动:
			logType = LoggerType.TYPE_七天送礼;
			break;
		case ActivityDefine.DEFINE_ID_七天额外奖励:
			logType = LoggerType.TYPE_七天额外奖励;
			break;
		default:
			throw new GameException(GameException.CODE_活动定义类型不存在, "defineId=" + activityDefineId);
		}
		return logType;
	}

	/**
	 * 验证标记，用0，1代替标记，0或空表示未领取， 1，表示领取
	 * 
	 * @param mark
	 *            标记字符串
	 * @param pos
	 *            位置
	 * @return
	 */
	private static boolean checkStringMark(String mark, int pos) {
		if (mark == null || mark.trim().equals("")) {
			return false;
		}

		char[] marks = mark.toCharArray();
		if (marks.length > pos && marks[pos] == '1') {
			return true;
		}
		return false;
	}


	/**
	 * 更新位置的标记， 下标第1位开始， 第0位弃用
	 * 
	 * @param mark
	 * @param pos
	 * @return
	 */
	private static String updateStringMark(String mark, int pos) {
		char[] marks;
		if (mark == null || mark.length() <= pos) {
			marks = new char[pos + 1];
			for (int i = 0; i <= pos; i++) {
				marks[i] = '0';
			}
		} else {
			marks = mark.toCharArray();
		}

		if (mark != null && pos >= mark.length()) {
			System.arraycopy(mark.toCharArray(), 0, marks, 0, mark.length());
		}

		marks[pos] = '1';
		return new String(marks);
	}

	@Override
	public List<ActivityVO> queryActivityGift(int userId, List<Integer> defineIds) {
		if (defineIds == null || defineIds.isEmpty()) {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		List<ActivityVO> list = new ArrayList<ActivityVO>();
		
		userMissionService.freshDayMission(userId);

		for (Integer defineId : defineIds) {
			list.add(queryActivityGift(userId, defineId));
		}
		return list;
	}

	@Override
	public List<ActivityGift> queryAcitivityGift(int defineId) {
		return activityGiftDAO.queryActivityGiftByDefineId(defineId);
	}

	/**
	 * 冲榜活动奖励发放
	 */
	@Override
	public void issueActivityRankAward() {
		// 0. 判断时间是否结束，判断奖励是否发送
		// 1. 分别查等级、竞技、推图 top 10 排行信息玩家 ()
		// 2. 查询等级、竞技、推图活动信息，对应礼包信息
		// 3. 分别邮件发送奖励
		// 4. 记录发送奖励标记

		logger.info("开始发送冲榜礼包");

		final int defineId = ActivityDefine.DEFINE_ID_冲榜赛活动;

		ActivityDefine activity = getCurrentActivityByDefineId(defineId);

		if (activity == null) {
			logger.warn("冲榜活动信息未找到");
			return;
		}

		if (System.currentTimeMillis() < activity.getEndTime().getTime()) {
			logger.info("冲榜活动还未结束，活动结束后才发送礼包");
			return;
		}

		// 判断是否发送奖励
		if (activity.getStatus() == ActivityDefine.STATUS_GIFT_ISSUE_NOT_COMPLETE) {
			List<ActivityGift> listGift = queryAcitivityGift(defineId);
			if (listGift == null || listGift.isEmpty()) {
				logger.warn("冲榜活礼包动信息未找到");
				return;
			}

			Map<Integer, List<Goods>> giftMap = new HashMap<Integer, List<Goods>>();
			for (ActivityGift gift : listGift) {
				if (gift.getGift() != null && !gift.getGift().trim().equals("")) {
					giftMap.put(Integer.parseInt(gift.getKey1()), JSONUtil.toObject(gift.getGift(), new TypeReference<List<Goods>>() {
					}));
				}
			}

			// 等级排行前10用户ID,发放奖励
			List<Integer> listLevelUserId = userDAO.gainUserLevel();
			if (listLevelUserId != null && !listLevelUserId.isEmpty()) {
				for (int i = 0; i < listLevelUserId.size(); i++) {
					if (i > 9) {
						break;
					}

					int userId = listLevelUserId.get(i);
					List<Goods> list = giftMap.get(1001 + i);
					Affiche affiche = Affiche.create(userId, Affiche.AFFICHE_TYP_等级排行礼包, "等级排行奖励", "恭喜您在开服冲榜赛中获得等级排行的第" + (i + 1) + "名，并获得丰厚奖励，请查收。", list, Affiche.STATE_未读, "0");
					afficheService.addAffiche(affiche);
				}
			}

			// 竞技排行榜前三十(只发前10),发放奖励
//			List<AthleticsInfo> infoList = athleticsInfoDAO.queryAthleticsInfoBytotalIntegral();
//			if (infoList != null && !infoList.isEmpty()) {
//				for (int i = 0; i < infoList.size(); i++) {
//					if (i > 9) {
//						break;
//					}
//
//					int userId = infoList.get(i).getUserId();
//					List<Goods> list = giftMap.get(2001 + i);
//					Affiche affiche = Affiche.create(userId, Affiche.AFFICHE_TYP_竞技场排行礼包, "竞技场排行奖励", "恭喜您在开服冲榜赛中获得竞技场排行的第" + (i + 1) + "名，并获得丰厚奖励，请查收。", list, Affiche.STATE_未读, "0");
//					afficheService.addAffiche(affiche);
//				}
//			}

			// 推图排行前10用户ID
			List<Integer> listChapterUserId = userDAO.gainChapterRankingTop10();
			if (listChapterUserId != null && !listChapterUserId.isEmpty()) {
				for (int i = 0; i < listChapterUserId.size(); i++) {
					if (i > 9) {
						break;
					}

					int userId = listChapterUserId.get(i);
					List<Goods> list = giftMap.get(3001 + i);
					Affiche affiche = Affiche.create(userId, Affiche.AFFICHE_TYP_关卡进度礼包, "关卡进度排行奖励", "恭喜您在开服冲榜赛中获得关卡进度排行的第" + (i + 1) + "名，并获得丰厚奖励，请查收。", list, Affiche.STATE_未读, "0");
					afficheService.addAffiche(affiche);
				}
			}

			// 发放完成记录发放成功，下次不再发送
			activity.setStatus(ActivityDefine.STATUS_GIFT_ISSUE_COMPLETE);
			updateActivity(activity);
			logger.info("冲榜礼包发送成功");
		} else {
			logger.info("冲榜活动礼包以发送");
		}
	}


	@Override
    public List<ActivityDefine> getActivityByDefineId(int defineId) {
		return activityDAO.queryActivityCacheByDefineId(defineId);
    }
	
	/**
	 * 获取当前有效的活动定义活动信息
	 * @param defineId
	 * @return
	 */
	@Override
	public ActivityDefine getCurrentActivityByDefineId(int defineId){
		List<ActivityDefine> list = activityDAO.queryActivityCacheByDefineId(defineId);
		Map<Integer, ActivityDefine> map = new TreeMap<>();
		
		for (ActivityDefine item : list){
			ActivityDefine activity = map.get(item.getDefineId());
			if (activity == null) {
				map.put(item.getDefineId(), item);
			} else {
				if(activity.getEndTime().getTime() > item.getEndTime().getTime() && System.currentTimeMillis() > item.getEndTime().getTime()) {
					map.put(item.getDefineId(), item);
				}
			}
		}
		return map.get(defineId);
	}
	
	@Override
	public void cleanActivityRecordData(int defineId) {
		try {
			if (defineId == ActivityDefine.DEFINE_ID_连续登陆活动) {
				logger.info("activity data clean start [连续登陆]");
				userStatDAO.resetActivityContinuousLogin();
				logger.info("activity data clean end [连续登陆]");
			}
			
			if (defineId == ActivityDefine.DEFINE_ID_消费送礼) {
				logger.info("activity data clean start [消费送礼]");
				userStatDAO.resetActivityTotalCostCurrency();
				activityGiftRecordDAO.deleteActivityGiftByActivityDefineId(ActivityDefine.DEFINE_ID_消费送礼);
				logger.info("activity data clean end [消费送礼]");
			}
			
			if (defineId == ActivityDefine.DEFINE_ID_限时礼包) {
				logger.info("activity data clean start [限时礼包]");
				userStatDAO.resetActivityRechargeCurrency();
				logger.info("activity data clean end [限时礼包]");
			}
			
			if (defineId == ActivityDefine.DEFINE_ID_每日充值送豪礼) {
				logger.info("activity data clean start [每日充值送豪礼]");
				userStatDAO.resetActivityContinuousRechargeCount();
				logger.info("activity data clean end [每日充值送豪礼]");
			}
			
			if (defineId == ActivityDefine.DEFINE_ID_收集送礼) {
				logger.info("activity data clean start [收集送礼]");
				activityGiftRecordDAO.deleteActivityGiftByActivityDefineId(ActivityDefine.DEFINE_ID_收集送礼);
				logger.info("activity data clean end [收集送礼]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
