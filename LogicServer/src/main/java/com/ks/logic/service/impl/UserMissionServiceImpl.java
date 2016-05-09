package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserMissionService;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.goods.UserBakProp;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.Condition;
import com.ks.model.mission.Mission;
import com.ks.model.mission.MissionAward;
import com.ks.model.mission.MissionCondition;
import com.ks.model.mission.UserMission;
import com.ks.model.user.User;
import com.ks.model.user.UserBuff;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.mission.ConditionVO;
import com.ks.protocol.vo.mission.MissionAwardVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.mission.UserMissionVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.util.DateUtil;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月30日 下午5:07:38 类说明
 */
public class UserMissionServiceImpl extends BaseService implements
		UserMissionService {

	@Override
	public void finishMissionCondition(User user, int type, int assId, int num) {
		int userId = user.getUserId();
		List<UserMission> ums = checkMission(userId);
		Set<Integer> missionIds = GameCache.getMissionCond(type, assId);
		if(missionIds!=null && !missionIds.isEmpty()){
			for(int missionId : missionIds){
				UserMission um = null;
				if(ums!=null){
					for(UserMission m : ums){
						if(m.getMissionId() == missionId){
							um = m;
							break;
						}
					}
				}else{
					um = user.getMissions().get(missionId);
				}
				if(um == null){
					um = userMissionDAO.getMissionFromCache(userId, missionId);
					user.getMissions().put(missionId, um);
				}
				if(um.getState() ==UserMission.STASTE_已领奖){  //完成状态
					continue;
				}
				List<MissionCondition> missionConds = GameCache.getMissionConditionList(missionId);
				for(MissionCondition mc : missionConds){
					if(mc.getType() == type && mc.getAssId() == assId){
						for(Condition c : um.getCondition()){
							if(c.getId() == mc.getId()){
								if(c.getNum()<mc.getNum()){
									int a = num+c.getNum();
									a=a<mc.getNum()?a:mc.getNum();
									c.setNum(a);
									userMissionDAO.updateUserMissionCache(um);
								}
								break;
							}
						}
						break;
					}
				}
			}
		}
		
	}

	/**
	 * 检查任务需要重置与否
	 * @param userId
	 */
	private List<UserMission> checkMission(int userId) {
		UserStat userStat = userStatDAO.queryUserStat(userId);
		if(userStat==null){
			 throw new GameException(GameException.CODE_参数错误, "can't find userStart:"+userId);
		}
		return freshDayMission(userStat);
	}
	
	/**
	 * 刷新用户每日任务
	 * @param userStat
	 * @return
	 */
	@Override
	public List<UserMission> freshDayMission(UserStat userStat) {
		Date refreshDate = userStat.getRefreshMissionDate();
		List<UserMission> userMissions = null;
		if (DateUtil.isBeforeToDay(refreshDate)) {
			userMissions = userMissionDAO.queryUserMissions(userStat.getUserId());
			for (UserMission um : userMissions) {
				if(GameCache.getMission(um.getMissionId()) != null && GameCache.getMission(um.getMissionId()).getMissionType()==Mission.MISSION_TYPE_每日任务){
					um.setState(0);
					um.setCondition(getConditionList(userStat.getUserId(), um.getMissionId()));
					um.setAwardList(getMissionAwardList(um.getMissionId(), GameCache.getMission(um.getMissionId()).getNum()));
					userMissionDAO.updateUserMissionCache(um);
				}
					
			}
			userStat.setRefreshMissionDate(new Date());
			UserStatOpt opt = new UserStatOpt();
			opt.refreshMissionDate= SQLOpt.EQUAL;
			userStatDAO.updateUserStat(opt, userStat);
		}
		return userMissions;
	}
	
	/**
	 * 刷新用户每日任务
	 * @param userId
	 * @return
	 */
	@Override
	public List<UserMission> freshDayMission(int  userId) {
		UserStat userStat = userStatDAO.queryUserStat(userId);
		if(userStat==null){
			 throw new GameException(GameException.CODE_参数错误, "can't find userStart:"+userId);
		}
		return freshDayMission(userStat);
	}

	private List<Condition> getConditionList(int userId, int missionId) {
		List<Condition> list = new ArrayList<Condition>();
		List<MissionCondition> mcList = GameCache
				.getMissionConditionList(missionId);
		if (mcList!=null && !mcList.isEmpty()) {
			UserBuff userBuffGoldMonthCard = userBuffDAO.getUserBuff(userId, UserBuff.BUFF_ID_黄金月卡);
			UserBuff userBuffDiamondMonthCard = userBuffDAO.getUserBuff(userId, UserBuff.BUFF_ID_钻石月卡);
			long now = System.currentTimeMillis();
			
			for (MissionCondition mc : mcList) {
				Condition condition = new Condition();
				condition.setId(mc.getId());
				condition.setNum(0);
				
				// 判断用户是否购买黄金月卡，是否超过时限，条件符合就允许领取月卡任务
				if(mc.getType() == MissionCondition.TYPE_黄金月卡 && userBuffGoldMonthCard != null && userBuffGoldMonthCard.getEndTime().getTime() > now) {
					condition.setNum(1);
				}
				// 判断用户是否购买钻石月卡，是否超过时限，条件符合就允许领取月卡任务
				if(mc.getType() == MissionCondition.TYPE_钻石月卡 && userBuffDiamondMonthCard != null && userBuffDiamondMonthCard.getEndTime().getTime() > now) {
					condition.setNum(1);
				}
				list.add(condition);
			}
		}
		return list;
	}
	/**
	 * 随机任务奖励
	 * @param missionId
	 * @param num
	 * @return
	 */
	private List<MissionAward> getMissionAwardList(int missionId, int num) {
		List<MissionAward> list = new ArrayList<MissionAward>();
		List<MissionAward> awardList = GameCache.getMissionAWardList(missionId);
		if (awardList != null && awardList.size() != num) {
			for (int i = 0; i < num; i++) {
				double randmon = Math.random();
				double totalRate = 0;
				for (MissionAward ma : awardList) {
					totalRate += ma.getRate();
					if (totalRate >= randmon) {
						list.add(ma);
						break;
					}
				}
			}
		} else {
			list = awardList;
		}

		return list;
	}

	@Override
	public List<MissionCondition> queryMissionCondition() {
		return userMissionCfgDAO.getMissionCondition();
	}

	@Override
	public List<Mission> queryMission() {
		return userMissionCfgDAO.getMission();
	}

	@Override
	public List<MissionAward> queryMissionAward() {
		return userMissionCfgDAO.getAllMissionAward();
	}

	@Override
	public void initUserMission(int userId) {
		userMissionDAO.clearUserMissionCache(userId);
		List<UserMission> missions = userMissionDAO.queryUserMissions(userId);
		if (missions.size() != GameCache.getMissions().size()) {
			List<UserMission> newMission = new ArrayList<UserMission>();
			out: for (Mission m : GameCache.getMissions()) {
				for (UserMission um : missions) {
					if (m.getMissionId() == um.getMissionId()) {
						continue out;
					}
				}
				UserMission um = createUserMission(userId, m);
				newMission.add(um);
			}
			userMissionDAO.addUserMissionBatch(userId, newMission);
			missions.addAll(newMission);
		}
		userMissionDAO.addUserMissionsCache(missions);
	}

	private UserMission createUserMission(int userId, Mission m) {
		List<Condition> conditionList=null;
		List<MissionAward> maList=null;
		if(m.getMissionType()==Mission.MISSION_TYPE_每日任务){  //每日任务添加任务条件和任务奖励
			conditionList=getConditionList(userId, m.getMissionId());
			maList=getMissionAwardList(m.getMissionId(), m.getNum());
		}else{
			conditionList=new ArrayList<Condition>();
			maList=new ArrayList<MissionAward>();
		}
		return UserMission.createUserMission(userId,
				m.getMissionId(), conditionList, 0,maList, new Date(),
				new Date(), false);
	}

	@Override
	public UserAwardVO missionAward(int userId, int missionId) {
		User user=userService.getExistUser(userId);
		checkMission(userId);
		UserMission userMission=userMissionDAO.getMissionFromCache(userId,missionId);
		Mission mission=GameCache.getMission(missionId);
		FightEndResultVO vo=null;
		
		if(userMission==null){
			throw new GameException(GameException.CODE_参数错误, "param error");
		}
		
		if(userMission.getState()==UserMission.STASTE_已领奖){
			throw new GameException(GameException.CODE_任务已领奖, "mission award");
		}
		
		List<MissionAward> awardList=null;
		if(mission.getMissionType()==Mission.MISSION_TYPE_每日任务){
//			List<Condition> conditionList=userMission.getCondition();
//			if(conditionList==null || conditionList.isEmpty()){
//				throw new GameException(GameException.CODE_参数错误, "param error");
//			}
//			List<MissionCondition> mcList=GameCache.getMissionConditionList(missionId);
//			for (Condition c:conditionList) {
//				for (MissionCondition mc:mcList) {
//					
//					if(c.getId()==mc.getId()){
//						if(mc.getType() == MissionCondition.TYPE_黄金月卡){
//							userBuffService.buffOffTime(userId, UserBuff.BUFF_ID_黄金月卡, 0);
//						}else if(mc.getType() == MissionCondition.TYPE_钻石月卡){
//							userBuffService.buffOffTime(userId, UserBuff.BUFF_ID_钻石月卡, 0);
//						}else if(c.getNum()<mc.getNum()){
//							throw new GameException(GameException.CODE_未完成任务不能领奖, "not mission");
//						}
//						break;
//					}
//				}
//			}
			if(!checkMissionCoindition(userMission)) {
				throw new GameException(GameException.CODE_未完成任务不能领奖, "not mission");
			}
			awardList=userMission.getAwardList();
			
			// 每日任务标记任务状态
			userMission.setState(UserMission.STASTE_已领奖);
			userMissionDAO.updateUserMissionCache(userMission);
		}
		
		if(mission.getMissionType() == Mission.MISSION_TYPE_收集奖励) {
			// 收集兑换任务，可重复收集，不修改任务状态
		    vo=missionExchangeAward(userGoodsService.getPackage(user.getUserId()),userMission,user);
			awardList=GameCache.getMissionAWardList(missionId);
		}
		
		UserAwardVO ua = sendAward(user, awardList,mission.getMissionType());
		if(vo!=null){
			ua.setDeleteSouls(vo.getUserSouls());
			ua.getUserGoodses().addAll(vo.getUserGoodses());
		}
		return ua;
	}

	private int[] getLogType(int type){
		int logType[]=new int[7];;
		if(type==Mission.MISSION_TYPE_每日任务){
			logType[0]=LoggerType.TYPE_每日任务奖励;
			logType[1]=LoggerType.TYPE_每日任务奖励;
			logType[2]=LoggerType.TYPE_每日任务奖励;
			logType[3]=LoggerType.TYPE_每日任务奖励; 
			logType[4]=LoggerType.TYPE_每日任务奖励;
			logType[5]=LoggerType.TYPE_每日任务奖励;
			logType[6]=LoggerType.TYPE_每日任务奖励;
			
		}else{
			logType[0]=LoggerType.TYPE_收集兑换奖励;
			logType[1]=LoggerType.TYPE_收集兑换奖励;
			logType[2]=LoggerType.TYPE_收集兑换奖励;
			logType[3]=LoggerType.TYPE_收集兑换奖励; 
			logType[4]=LoggerType.TYPE_收集兑换奖励;
			logType[5]=LoggerType.TYPE_收集兑换奖励;
			logType[6]=LoggerType.TYPE_收集兑换奖励;
		}
		
		return logType;
	}
	private UserAwardVO sendAward(User user,  List<MissionAward> awardList,int type) {
		Backage backage = userGoodsService.getPackage(user.getUserId());
		List<Goods> goodsList = new ArrayList<Goods>();
		UserAwardVO ua = MessageFactory.getMessage(UserAwardVO.class);
		ua.setDeleteSouls(new ArrayList<UserSoulVO>());
		ua.setBakProp(new ArrayList<UserBakPropVO>());
		ua.setUserGoodses(new ArrayList<UserGoodsVO>());
		ua.setUserSouls(new ArrayList<UserSoulVO>());
		int logType[]=getLogType(type);
		List<Goods> soulGoods=new ArrayList<>();
		// int exp=0;
		if (awardList != null) {
			for (MissionAward ma : awardList) {
				switch (ma.getType()) {
				case Goods.TYPE_SOUL:
					Goods good=Goods.create(ma.getAssId(), ma.getType(), ma.getNum(),1);
					soulGoods.add(good);
					break;
				case Goods.TYPE_PROP:
				case Goods.TYPE_STUFF:
				case Goods.TYPE_EQUIPMENT:
					Goods goods = new Goods();
					goods.setType(ma.getType());
					goods.setGoodsId(ma.getAssId());
					goods.setNum(ma.getNum());
					goods.setLevel(1);
					goodsList.add(goods);
					break;
				case Goods.TYPE_GOLD:
					userService.incrementGold(user, ma.getNum(),
							logType[1], 0 + "");
					break;
				case Goods.TYPE_CURRENCY:
					userService.incrementCurrency(user, ma.getNum(),
							logType[2], 0 + "");
					break;
				case Goods.TYPE_EXP:
					// exp+= ma.getNum();
					userService.incrementExp(user, ma.getNum(),
							logType[3], 0 + "",null);
					break;

				case Goods.TYPE_FRIENDLY_POINT:
					userService.incrementFriendlyPoint(user.getUserId(),
							ma.getNum(), logType[4],
							0 + "mission award");
					break;
				case Goods.TYPE_BAK_PROP:
					userGoodsService.addBakProps(user, ma.getAssId(), ma.getNum(),
							logType[5], "");
					UserBakProp prop = UserBakProp.create(user.getUserId(),
							ma.getAssId(), ma.getNum());
					UserBakPropVO propVo = MessageFactory
							.getMessage(UserBakPropVO.class);
					propVo.init(prop);
					ua.getBakProp().add(propVo);
					break;
				default:
					break;
				}
			}
		}
		
		UserStat stat=userStatDAO.queryUserStat(user.getUserId());
		if(goodsList.size()!=0){
			userGoodsService.checkBackageFull(backage, user);
			ua.setUserGoodses(userGoodsService.addGoods(user,
					userGoodsService.getPackage(user.getUserId()), goodsList,
					logType[6], "mission award"));
			
		}
		if(!soulGoods.isEmpty()){
			userSoulService.checkSoulFull(user);
			for(Goods goods:soulGoods){
				for(int num=1; num<=goods.getNum(); num++){
					UserSoul soul = userSoulService.addUserSoul(user, goods.getGoodsId(),goods.getLevel(),logType[0]);
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
	//收集物品兑换奖励
	private FightEndResultVO missionExchangeAward(Backage backage,UserMission um,User user){
		FightEndResultVO fr = MessageFactory.getMessage(FightEndResultVO.class);
		fr.setUserGoodses(new ArrayList<UserGoodsVO>());
		fr.setUserSouls(new ArrayList<UserSoulVO>());
		List<MissionCondition> mcList=GameCache.getMissionConditionList(um.getMissionId());
		if(mcList.isEmpty()){
			throw new GameException(GameException.CODE_参数错误, "param error");
		}
		List<UserSoul> usList=userSoulService.getUserSoulListFromCache(user.getUserId());
		List<UserSoul> newList=new ArrayList<UserSoul>();
		for (MissionCondition mc:mcList) {
			switch (mc.getType()-100) {
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				List<UserGoods> ugs = userGoodsService.deleteGoods(backage, mc.getType()-100, mc.getAssId(), mc.getNum(), LoggerType.TYPE_收集兑换消耗, "id : ");
				for(UserGoods ug : ugs){
					UserGoodsVO ugvo = MessageFactory.getMessage(UserGoodsVO.class);
					ugvo.init(ug);
					fr.getUserGoodses().add(ugvo);
				}
				break;
			case Goods.TYPE_SOUL:
				for(UserSoul us:usList){
					if(us.getSoulId()==mc.getAssId()&&us.getSoulSafe()==0){
						newList.add(us);
					}
				}
				 if(newList.size()<mc.getNum()){
					 throw new GameException(GameException.CODE_战魂数量不够, "userSoul not enough ");
				 }
				 Collections.sort(newList,new Comparator<UserSoul>(){  
			            @Override  
			            public int compare(UserSoul us1, UserSoul us2) {  
			                return us1.getLevel()-us2.getLevel();
			            }         
			        });
				 for (int i = 0; i < mc.getNum(); i++) {
					 userSoulService.deleteUserSoul(user, newList.get(i), LoggerType.TYPE_收集兑换消耗, "");
					 UserSoulVO usVO=MessageFactory.getMessage(UserSoulVO.class);
					 usVO.init(newList.get(i));
					 fr.getUserSouls().add(usVO);
				}
				 newList.clear();
				break;
			default:
				break;
			}
		}
		return fr;
	}
	
	@Override
	public List<UserMissionVO> getUserMissions(int userId) {
		User user=userService.getExistUser(userId);
		
		//查询所有任务，并刷新每日任务
		List<UserMission> userMissionList = checkMission(user.getUserId());
		
		if(userMissionList==null){
			userMissionList=userMissionDAO.getUserMissionListFromCache(userId);
		}
		List<UserMissionVO> umVO=new ArrayList<UserMissionVO>();
		for (UserMission um:userMissionList) {
			if(um.getState()==UserMission.STASTE_已领奖){
				continue;
			}
			Mission mission=GameCache.getMission(um.getMissionId());
			if (mission == null) {
				continue;
			}
			
			UserMissionVO vo=MessageFactory.getMessage(UserMissionVO.class);
			if(mission.getMissionType()==Mission.MISSION_TYPE_每日任务){
				vo.setAwardList(createMissionAwardVO(um.getAwardList()));
				vo.setCondition(createConditionList(um.getCondition()));
			}
			vo.setMissionId(um.getMissionId());
			vo.setUserId(um.getUserId());
			vo.setState(um.getState());
			umVO.add(vo);
		}
		return umVO;
	}

	public  List<ConditionVO> createConditionList(List<Condition> list){
		List<ConditionVO> voList=new ArrayList<ConditionVO>();
		for (Condition c:list) {
			ConditionVO vo=MessageFactory.getMessage(ConditionVO.class);
			vo.setId(c.getId());
			vo.setNum(c.getNum());
			voList.add(vo);
		}
		return voList;
	}
	
	public List<MissionAwardVO> createMissionAwardVO(List<MissionAward> list){
		List<MissionAwardVO> voList=new ArrayList<MissionAwardVO>();
		if (list != null) {
			for (MissionAward ma:list) {
				MissionAwardVO vo=MessageFactory.getMessage(MissionAwardVO.class);
				vo.setAssId(ma.getAssId());
				vo.setId(ma.getId());
				vo.setNum(ma.getNum());
				vo.setType(ma.getType());
				vo.setMissionId(ma.getMissionId());
				voList.add(vo);
			}
		}
		return voList;
	}
	
	@Override
	public boolean checkMissionCoindition(UserMission userMission) {
		
		List<Condition> conditionList = userMission.getCondition();
		if(conditionList==null || conditionList.isEmpty()){
			throw new GameException(GameException.CODE_参数错误, "param error");
		}
		
		List<MissionCondition> mcList=GameCache.getMissionConditionList(userMission.getMissionId());
		for (Condition c:conditionList) {
			for (MissionCondition mc:mcList) {
				
				if(c.getId()==mc.getId()){
					if(mc.getType() == MissionCondition.TYPE_黄金月卡){
						userBuffService.buffOffTime(userMission.getUserId(), UserBuff.BUFF_ID_黄金月卡, 0);
					}else if(mc.getType() == MissionCondition.TYPE_钻石月卡){
						userBuffService.buffOffTime(userMission.getUserId(), UserBuff.BUFF_ID_钻石月卡, 0);
					}else if(c.getNum()<mc.getNum()){
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	/*@Override
	public boolean checkMissionCoindition(UserMission userMission) {
		
		List<Condition> conditionList = userMission.getCondition();
		if(conditionList==null || conditionList.isEmpty()){
			throw new GameException(GameException.CODE_参数错误, "param error");
		}
		
		List<MissionCondition> mcList=GameCache.getMissionConditionList(userMission.getMissionId());
		for (Condition c:conditionList) {
			for (MissionCondition mc:mcList) {
				
				if(c.getId()==mc.getId()){
					if(mc.getType() == MissionCondition.TYPE_黄金月卡){
						userBuffService.buffOffTime(userMission.getUserId(), UserBuff.BUFF_ID_黄金月卡, 0);
					}else if(mc.getType() == MissionCondition.TYPE_钻石月卡){
						userBuffService.buffOffTime(userMission.getUserId(), UserBuff.BUFF_ID_钻石月卡, 0);
					}else if(c.getNum()<mc.getNum()){
						throw new GameException(GameException.CODE_未完成任务不能领奖, "not mission");
					}
					break;
				}
			}
		}
		return true;
	}*/
	

	@Override
	public void finishShareTask(int userId) {
		User user = userService.getExistUserCache(userId);
		
		finishMissionCondition(user, MissionCondition.TYPE_分享, 0, 1);
	}
}
