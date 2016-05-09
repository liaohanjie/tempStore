package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ks.exceptions.GameException;
import com.ks.logger.LoggerFactory;
import com.ks.logic.cache.GameCache;
import com.ks.logic.event.GameLoggerEvent;
import com.ks.logic.service.AthleticsInfoService;
import com.ks.logic.service.BaseService;
import com.ks.model.ZoneConfig;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;
import com.ks.model.logger.AthleticsInfoLog;
import com.ks.model.logger.GameLogger;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.MissionCondition;
import com.ks.model.pvp.AthleticsInfo;
import com.ks.model.pvp.AthleticsInfoAward;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.fight.FightVO;
import com.ks.protocol.vo.pvp.AthleticsInfoModelVO;
import com.ks.protocol.vo.pvp.AthleticsInfoVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.timer.TimerController;

public class AthleticsInfoServiceImpl extends BaseService implements
		AthleticsInfoService {
	
	private Logger logger = LoggerFactory.get(AthleticsInfoServiceImpl.class);

	@Override
	public int regainAthleticsPoint(int userId) {
		
		User user = userService.getExistUserCache(userId);
		if(user.getLevel()<3){
			throw new GameException(GameException.CODE_等级不够, "user not leve."+userId);
		}
		AthleticsInfo info=athleticsInfoDAO.getAthleticsInfo(userId);
		if(info!=null){
			int currency=User.USER_恢复竞技点价格;   //购买竞技点消耗的魂钻
			if(activityService.activityIsStart(ActivityDefine.DEFINE_ID_竞技点打折)){
				ZoneConfig config=activityDAO.queryZoneConfig(ZoneConfig.ID_USER_ID_SEED);
				currency=config.getAcAthleticsPoint();
			}
			userService.decrementCurrency(user, currency, LoggerType.TYPE_购买竞技点, "");
			info.setAthleticsPoint(AthleticsInfo.ATHLETICS_POINT_初始的竞技点数);
			info.setLastBackTime(new Date());
			//添加竞技点日志
			AthleticsInfoLog logger=AthleticsInfoLog.createBakAthleticsInfoLog(
			GameLogger.LOGGER_TYPE_PVP_POINT, userId, LoggerType.竞技点_type_花钱买点,AthleticsInfo.ATHLETICS_POINT_初始的竞技点数-info.getAthleticsPoint(), 
			AthleticsInfoLog.category_竞技点, "buy");
			TimerController.submitGameEvent(new GameLoggerEvent(logger));
			athleticsInfoDAO.updateAthleticsInfo(info);
		}
		return user.getCurrency();
	}

	@Override
	public AthleticsInfoModelVO queryAthleticsInfoBytotalIntegral(int userId) {
		AthleticsInfoModelVO model=MessageFactory.getMessage(AthleticsInfoModelVO.class);
		List<AthleticsInfo> infoList=athleticsInfoDAO.queryAthleticsInfoBytotalIntegral();
		AthleticsInfo afInfo=athleticsInfoDAO.getAthleticsInfo(userId);
		List<AthleticsInfoVO> voList=new ArrayList<AthleticsInfoVO>();
		List<Integer> userIds=new ArrayList<Integer>();
		if(!infoList.isEmpty()){
			for (AthleticsInfo af:infoList) {
				userIds.add(af.getUserId());
				AthleticsInfoVO vo=MessageFactory.getMessage(AthleticsInfoVO.class);
				vo.init(af);
				UserCap cap=userTeamDAO.getUserCapCache(af.getUserId());
				if(cap == null){
					cap = getUserCap(af.getUserId());
				}
				UserCapVO capVO=MessageFactory.getMessage(UserCapVO.class);
				if(cap!=null){
					capVO.init(cap);
					vo.setUserCap(capVO);
				}
				voList.add(vo);
			}
		}
		if(afInfo==null){
			model.setMyRank(-1);
		}else{
			model.setMyRank(athleticsInfoDAO.queryMyRank(afInfo.getTotalIntegral()));
		}
		model.setInfoList(voList);
		return model;
	}

	@Override
	public AthleticsInfoVO queryMatchUserIds(int userId) {
		AthleticsInfo info=athleticsInfoDAO.getAthleticsInfo(userId);
		AthleticsInfoVO vo=null;
		if(info!=null){
			final int point=info.getTotalIntegral();
			List<AthleticsInfo> bigList=athleticsInfoDAO.queryMatchUserIds(AthleticsInfo.ASC_高于自己积分的前10名, point, userId);
			bigList.addAll(athleticsInfoDAO.queryMatchUserIds(AthleticsInfo.ASC_低于自己积分的前10名, point, userId));
			if(bigList!=null && !bigList.isEmpty()){
				AthleticsInfo matchUser=bigList.remove((int)(Math.random()*bigList.size()));
				vo=MessageFactory.getMessage(AthleticsInfoVO.class);
				vo.init(matchUser);
				UserCap cap = userTeamDAO.getUserCapCache(matchUser.getUserId());
				if(cap == null){
					cap = getUserCap(matchUser.getUserId());
				}
				UserCapVO capvo = MessageFactory.getMessage(UserCapVO.class);
				if(cap != null){
					capvo.init(cap);
					vo.setUserCap(capvo);
				}else{
					logger.error("matchUserId："+matchUser.getUserId());
				}

			}
			
		}
		return vo;
	}
	
	private UserCap getUserCap(int userId){
		User user = userService.getExistUser(userId);
		if(user !=null){
			UserTeam userTeam = userTeamDAO.queryUserTeam(userId, user.getCurrTeamId());
			//队长战魂
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
			
			return cap;
		}
		return null;
	}
	
//	private UserCap getUserCap(int userId){
//		User user = userService.getExistUser(userId);
//		if(user !=null){
//			List<UserSoul> uses = userSoulService.initUserSoul(userId);
//
//			List<UserTeam> uts = userTeamService.initUserTeam(user, uses);
//			
//			if (uts.size() != 0) {
//				userTeamService.initUserCap(user, getSoulCap(user, uses, uts));
//			}
//			return userTeamDAO.getUserCapCache(userId);
//		}
//		return null;
//	}
//	
//	private UserSoul getSoulCap(User user, List<UserSoul> uses, List<UserTeam> uts) {
//		UserTeam team = null;
//		for (UserTeam ut : uts) {
//			if (ut.getTeamId() == user.getCurrTeamId()) {
//				team = ut;
//				break;
//			}
//		}
//		long capId = team.getPos().get(team.getCap() - 1);
//		UserSoul us = null;
//		for (UserSoul u : uses) {
//			if (u.getId() == capId) {
//				us = u;
//				break;
//			}
//		}
//		return us;
//	}
	
	@Override
	public AthleticsInfoVO enterArnea(int userId) {
 		User user = userService.getExistUserCache(userId);
		if(user.getLevel()<3){
			throw new GameException(GameException.CODE_等级不够, "user not leve."+userId);
		}
		AthleticsInfo af=athleticsInfoDAO.getAthleticsInfo(userId);
		if(af==null){
			af=new AthleticsInfo();
			af.setUserId(userId);
			af.setAwardTitle(new ArrayList<Integer>());
			af.setAthleticsPoint(AthleticsInfo.ATHLETICS_POINT_初始的竞技点数);
			af.setLastBackTime(new Date());
			AthleticsInfoLog logger=AthleticsInfoLog.createBakAthleticsInfoLog(
			GameLogger.LOGGER_TYPE_PVP_POINT, userId, LoggerType.竞技点_type_第一次送竞技点, AthleticsInfo.ATHLETICS_POINT_初始的竞技点数, AthleticsInfoLog.category_竞技点, "send");
			TimerController.submitGameEvent(new GameLoggerEvent(logger));
			athleticsInfoDAO.addAthleticsInfo(af);
			af=athleticsInfoDAO.getAthleticsInfo(userId);
		}
		//恢复竞技点
		checkAthleticsInfoPoint(af);
		AthleticsInfoVO vo=MessageFactory.getMessage(AthleticsInfoVO.class);
		UserCap cap = userTeamDAO.getUserCapCache(userId);
		UserCapVO capvo = MessageFactory.getMessage(UserCapVO.class);
		capvo.init(cap);
		vo.init(af);
		vo.setUserCap(capvo);
		AthleticsInfoAward award=GameCache.getAthleticsInfoAward(AthleticsInfo.ATHLETICS_勇敢的旅者);
		if(af.getAwardTitle().indexOf(award.getId())==-1){
			List<Goods> goodsList=new ArrayList<Goods>();
				Goods goods=Goods.create(award.getAssId(),award.getType(),award.getNum(),0);
				goodsList.add(goods);
				Affiche affiche=Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_竞技场称号升级奖励,"竞技场称号升级奖励","恭喜！您在竞技场中表现神勇，获得"+AthleticsInfo.getAthleticsNameById(9080001)+"称号，请收下奖励并再接再厉！", goodsList,Affiche.STATE_未读,"0");
				afficheService.addAffiche(affiche);
				af.getAwardTitle().add(award.getId());
		}
		athleticsInfoDAO.updateAthleticsInfo(af);
		return vo;
	}

	@Override
	public AthleticsInfoVO startArneaPK(int attackerId, int defenderId ) {
		AthleticsInfo afAttacker=athleticsInfoDAO.getAthleticsInfo(attackerId);
		AthleticsInfo afDefenderId=athleticsInfoDAO.getAthleticsInfo(defenderId);
		if(afAttacker==null || afDefenderId==null){
			return null;
		}
		//恢复竞技点
		checkAthleticsInfoPoint(afAttacker);
		User user=userService.getExistUser(attackerId);
		if(user.getLevel()<3){ //等级小于3不能进入竞技场
			throw new GameException(GameException.CODE_等级不够, "user not leve."+attackerId);
		}
		if(afAttacker.getAthleticsPoint()-1<0){    //竞技点不够
			throw new GameException(GameException.CODE_竞技点不够, "athleticsinfo not point."+afAttacker.getAthleticsPoint());
		}
		FightVO fightVO=arenaService.fighting(attackerId, defenderId);
		int winNum=0;
		String desc="";
		if(fightVO.isAttWin()){
			desc="win";
			afAttacker.setWins(afAttacker.getWins()+1);
			afAttacker.setStreakWin(afAttacker.getStreakWin()+1);
			int afAttackerNameId=AthleticsInfo.getAthleticsName(afAttacker.getTotalIntegral());
			int afDefenderNameId=AthleticsInfo.getAthleticsName(afDefenderId.getTotalIntegral());
			int grade=afAttackerNameId-afDefenderNameId;
			if(grade>0){
				winNum=AthleticsInfo.ATHLETICS_INTEGRAL_加减分数/grade;
			}else if(grade==0){
				winNum=AthleticsInfo.ATHLETICS_INTEGRAL_加减分数/2;
			}else{
				winNum=-AthleticsInfo.ATHLETICS_INTEGRAL_加减分数*grade;
			}
			afAttacker.setTotalIntegral(afAttacker.getTotalIntegral()+winNum);
			Integer afterNameId=AthleticsInfo.getAthleticsName(afAttacker.getTotalIntegral());
			if(afAttacker.getTotalIntegral()>AthleticsInfo.ATHLETICS_MAX_INTEGRAL){
				afAttacker.setTotalIntegral(AthleticsInfo.ATHLETICS_MAX_INTEGRAL);
			}
			if(afAttacker.getStreakWin()>afAttacker.getHighestWinStreak()){
				afAttacker.setHighestWinStreak(afAttacker.getStreakWin());
			}
			if(afterNameId-afAttackerNameId>0){
				awardLeveUpName(attackerId,afAttackerNameId,afterNameId,afAttacker);
			}
			//awardLeveUpName(attackerId,afAttackerNameId,afterNameId,afAttacker);
		}else{
			desc="lose";
			afAttacker.setLose(afAttacker.getLose()+1);
			if(afAttacker.getStreakWin()>afAttacker.getHighestWinStreak()){
				afAttacker.setHighestWinStreak(afAttacker.getStreakWin());
			}
			afAttacker.setStreakWin(0);
			int afAttackerNameId=AthleticsInfo.getAthleticsName(afAttacker.getTotalIntegral());
			int afDefenderNameId=AthleticsInfo.getAthleticsName(afDefenderId.getTotalIntegral());
			int grade=afAttackerNameId-afDefenderNameId;
			if(afAttacker.getTotalIntegral()>AthleticsInfo.ATHLETICS_MIN_INTEGRAL){
				if(grade>0){
					winNum=-AthleticsInfo.ATHLETICS_INTEGRAL_加减分数*grade;
				}else if(grade==0){
					winNum=-AthleticsInfo.ATHLETICS_INTEGRAL_加减分数/2;
				}else{
					winNum=AthleticsInfo.ATHLETICS_INTEGRAL_加减分数/grade;
					
				}
				int currentIntegral=afAttacker.getTotalIntegral();//当前分数
				afAttacker.setTotalIntegral(afAttacker.getTotalIntegral()+winNum);
				if(afAttacker.getTotalIntegral()<AthleticsInfo.ATHLETICS_MIN_INTEGRAL){
					winNum=AthleticsInfo.ATHLETICS_MIN_INTEGRAL-currentIntegral;
					afAttacker.setTotalIntegral(AthleticsInfo.ATHLETICS_MIN_INTEGRAL);	
				}
			}
			
		}
		
		if(afAttacker.getAthleticsPoint()==3){
			afAttacker.setLastBackTime(new Date());
		}
		//添加竞技点日志
		AthleticsInfoLog logger=AthleticsInfoLog.createBakAthleticsInfoLog(
		GameLogger.LOGGER_TYPE_PVP_POINT, afAttacker.getUserId(), LoggerType.竞技点_type_比赛扣点, -1, AthleticsInfoLog.category_竞技点, "PK");
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
		AthleticsInfoLog loggerIntegral=AthleticsInfoLog.createBakAthleticsInfoLog(
				GameLogger.LOGGER_TYPE_PVP_INTEGRAL, afAttacker.getUserId(), LoggerType.竞技分数_type_胜负奖励, winNum, AthleticsInfoLog.category_竞技分数, desc);
				TimerController.submitGameEvent(new GameLoggerEvent(loggerIntegral));
		afAttacker.setAthleticsPoint(afAttacker.getAthleticsPoint()-1);
		athleticsInfoDAO.updateAthleticsInfo(afAttacker);
		AthleticsInfoVO vo=MessageFactory.getMessage(AthleticsInfoVO.class);
		vo.init(afAttacker);
		vo.setFightVO(fightVO);
	
		UserCap cap = userTeamDAO.getUserCapCache(attackerId);
		cap.setArenaIntegral(afAttacker.getTotalIntegral());
		userTeamDAO.updateUserCapCache(cap);
		
		//竞技场任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_竞技场次数, 0, 1);
		return vo;
	}

	public void awardLeveUpName(int userId,int beforeNameId,int afterNameId, AthleticsInfo afAttacker){
		User user = userService.getExistUserCache(userId);
		for (int i = 0; i < afterNameId-beforeNameId; i++) {
			AthleticsInfoAward award=GameCache.getAthleticsInfoAward(afterNameId-i);
			if(afAttacker.getAwardTitle().indexOf(award.getId())==-1){
				List<Goods> goodsList=new ArrayList<Goods>();
					Goods goods=Goods.create(award.getAssId(),award.getType(),award.getNum(),0);
					goodsList.add(goods);
					Affiche affiche=Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_竞技场称号升级奖励,"竞技场称号升级奖励",
							"恭喜！您在竞技场中表现神勇，获得"+AthleticsInfo.getAthleticsNameById(afterNameId-i)+"称号，请收下奖励并再接再厉！",
							goodsList,Affiche.STATE_未读,"0");
					afficheService.addAffiche(affiche);
					afAttacker.getAwardTitle().add(award.getId());
			}
		}	
		
	}

	@Override
	public void checkAthleticsInfoPoint(AthleticsInfo info) {
		if(info.getAthleticsPoint()<AthleticsInfo.ATHLETICS_POINT_初始的竞技点数){
			long time = System.currentTimeMillis()-info.getLastBackTime().getTime();
			long count = time / AthleticsInfo.REGAIN_STAMINA_TIME;
			int stamina = 0;
			int num=0;//日志
			if(count>0){
				if(count>3){
					stamina = 3;
					num=stamina;
					info.setLastBackTime(new Date());
				}else{
					if(info.getAthleticsPoint()+(int)count>AthleticsInfo.ATHLETICS_POINT_初始的竞技点数){
						stamina = 3;
						num=stamina;
						info.setLastBackTime(new Date());
					}else{
						stamina = (int)count+info.getAthleticsPoint();
						num=(int)count;
						info.setLastBackTime(new Date(System.currentTimeMillis()-(time%User.REGAIN_STAMINA_TIME)));
					}
				}
				info.setAthleticsPoint(stamina);
				AthleticsInfoLog logger=AthleticsInfoLog.createBakAthleticsInfoLog(
				GameLogger.LOGGER_TYPE_PVP_POINT, info.getUserId(), LoggerType.竞技点_type_自动回点, num, AthleticsInfoLog.category_竞技点, "send");
				TimerController.submitGameEvent(new GameLoggerEvent(logger));
				athleticsInfoDAO.updateAthleticsInfo(info);
			}
		}else{
			info.setLastBackTime(new Date());
		}

	}

	@Override
	public List<AthleticsInfoAward> getAthleticsNameAward() {
		return athleticsInfoCfgDAO.getAthleticsNameAward();
	}
}
