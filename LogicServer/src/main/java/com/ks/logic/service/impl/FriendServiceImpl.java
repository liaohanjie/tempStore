package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.ks.event.GameEvent;
import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.logic.event.NotifiEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.FriendService;
import com.ks.logic.utils.DateUtils;
import com.ks.model.ZoneConfig;
import com.ks.model.achieve.Achieve;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.friend.Friend;
import com.ks.model.friend.FriendApply;
import com.ks.model.friend.FriendGifiRule;
import com.ks.model.friend.FriendGift;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.MissionCondition;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserRule;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.friend.FightFriendVO;
import com.ks.protocol.vo.friend.FriendGifiVO;
import com.ks.protocol.vo.friend.FriendVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.timer.TimerController;

public class FriendServiceImpl extends BaseService implements FriendService {

	@Override
	public List<FriendVO> gainFriends(int userId) {
		List<Friend> friends = friendDAO.queryFriends(userId);
		List<Integer> friendIds = new ArrayList<>();
		for(Friend f : friends){
			friendIds.add(f.getFriendId());
		}
		List<UserCap> caps = userTeamDAO.getUserCapsCache(friendIds);
		List<FriendVO> vos = new ArrayList<>();
		for(UserCap cap : caps){
			FriendVO fvo = MessageFactory.getMessage(FriendVO.class);
			UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
			Friend fr = null;
			for(Friend f : friends){
				if(f.getFriendId()==cap.getUserId()){
					fr=f;
					break;
				}
			}
			if(fr!=null){
				fvo.setStatus(fr.getStatus());
			}
			vo.init(cap);
			fvo.setCap(vo);
			vos.add(fvo);
		}
		return vos;
	}

	@Override
	public void applyFriend(int userId, int applyUserId) {
		User user = userService.getExistUserCache(userId);
		UserRule rule = GameCache.getUserRule(user.getLevel());
		int userFriendCount = friendDAO.queryFriendCount(userId);
		if(userFriendCount>=rule.getFriendCapacity()+user.getFriendCapacity()){
			throw new GameException(GameException.CODE_你的好友已满, "");
		}
		User friend = userService.getExistUser(applyUserId);
		rule = GameCache.getUserRule(friend.getLevel());
		userFriendCount = friendDAO.queryFriendCount(applyUserId);
		if(userFriendCount>=rule.getFriendCapacity()+friend.getFriendCapacity()){
			throw new GameException(GameException.CODE_对方好友已满, "");
		}
		FriendApply apply = friendDAO.queryFriendApply(applyUserId, userId);
		if(apply==null){
			apply=new FriendApply();
			apply.setUserId(applyUserId);
			apply.setApplyUserId(userId);
			friendDAO.addFriendApply(apply);
			sentAppFriendNoti(applyUserId);
		}
	}

	@Override
	public List<UserCapVO> gainApplyFriends(int userId) {
		List<FriendApply> applys = friendDAO.queryFriendApplys(userId);
		List<Integer> friendIds = new ArrayList<>();
		for(FriendApply f : applys){
			friendIds.add(f.getApplyUserId());
		}
		List<UserCap> caps = userTeamDAO.getUserCapsCache(friendIds);
		List<UserCapVO> vos = new ArrayList<>();
		for(UserCap cap : caps){
			UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
			vo.init(cap);
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public void execApply(int userId, int applyUserId,boolean pass) {
		if(pass){
			FriendApply apply = friendDAO.queryFriendApply(userId, applyUserId);
			if(apply==null){
				throw new GameException(GameException.CODE_参数错误, "");
			}
			User user = userService.getExistUserCache(userId);
			UserRule rule = GameCache.getUserRule(user.getLevel());
			int userFriendCount = friendDAO.queryFriendCount(userId);
			if(userFriendCount>=rule.getFriendCapacity()+user.getFriendCapacity()){
				throw new GameException(GameException.CODE_你的好友已满, "");
			}
			User friend = userService.getExistUser(applyUserId);
			rule = GameCache.getUserRule(friend.getLevel());
			userFriendCount = friendDAO.queryFriendCount(applyUserId);
			if(userFriendCount>=rule.getFriendCapacity()+friend.getFriendCapacity()){
				throw new GameException(GameException.CODE_对方好友已满, "");
			}
			Friend userFriend=friendDAO.queryFriend(userId, applyUserId);
			if(userFriend!=null){
				friendDAO.deleteFriendApply(userId, applyUserId);
				return;
			}
			Friend f = new Friend();
			f.setUserId(userId);
			f.setFriendId(applyUserId);
			friendDAO.addFriend(f);
			
			f.setFriendId(userId);
			f.setUserId(applyUserId);
			friendDAO.addFriend(f);
			//添加好友荣誉
			userAchieveService.addUserAchieveProcess(userId,Achieve.TYPE_好友数量,0, 1);
			userAchieveService.addUserAchieveProcess(applyUserId,Achieve.TYPE_好友数量,0, 1);
		}
		friendDAO.deleteFriendApply(userId, applyUserId);
	}

	@Override
	public void deleteFriend(int userId, int friendId) {
		if(friendId==User.USER_ID_好友机器人){
			throw new GameException(GameException.CODE_参数错误, " can't not delete this friend");
		}
		friendDAO.deleteFriend(userId, friendId);
		friendDAO.deleteFriend(friendId, userId);
	}

	@Override
	public void collectionFriend(int userId, int friendId) {
		Friend friend = friendDAO.queryFriend(userId,friendId);
		friend.setStatus(friend.getStatus()|Friend.STATUS_COLLECTION);
		friendDAO.updateFriend(friend);
	}

	@Override
	public void unCollectionFriend(int userId, int friendId) {
		Friend friend = friendDAO.queryFriend(userId,friendId);
		friend.setStatus(friend.getStatus()&~Friend.STATUS_COLLECTION);
		friendDAO.updateFriend(friend);
	}

	@Override
	public FightFriendVO gainFightFriend(int userId) {
		User user = userService.getExistUserCache(userId);
		FightFriendVO ff = MessageFactory.getMessage(FightFriendVO.class);
		
		List<FriendVO> friends=gainFriends(userId);
		UserStat stat=userStatDAO.queryUserStat(userId);
		Iterator<FriendVO>  iterable=friends.iterator();
		while(iterable.hasNext()){
			FriendVO f=iterable.next();
			if(f.getCap().getUserId()==User.USER_ID_好友机器人){
				iterable.remove();
			}
			if(stat.getWinFriend().indexOf(f.getCap().getUserId())!=-1){
				iterable.remove();
			}
		}
		ff.setFirends(friends);
		ff.setAdventurers(new ArrayList<FriendVO>());
		
		List<Integer> mustClearIds = new ArrayList<Integer>();
		mustClearIds.add(new Integer(User.USER_ID_好友机器人));
		mustClearIds.add(new Integer(userId));
		for(FriendVO f : friends){
			mustClearIds.add(f.getCap().getUserId());
		}
 		List<Integer> userIds=userDAO.randomAdventurers(user.getLevel());
		userIds.removeAll(mustClearIds);
		
		for(Integer id:userIds){
			if(ff.getAdventurers().size()>=3){
				break;
			}
			if(stat.getWinFriend().indexOf(id)!=-1){
				continue;
			}
			FriendVO f = MessageFactory.getMessage(FriendVO.class);
			UserCap cap = userTeamDAO.getUserCapCache(id);
			if(cap!=null){
				UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
				vo.init(cap);
				f.setCap(vo);
				ff.getAdventurers().add(f);
			}
		}
		//测试代码 要删除
		//ff.getAdventurers().clear();
		if(ff.getAdventurers().size()<3){
			int size = ff.getAdventurers().size();
			UserTeam ut = userTeamDAO.getUserTeamCache(userId, user.getCurrTeamId());
			List<Long> ids=new ArrayList<>();
			for(long id : ut.getPos()){
				if(id!=0){
					ids.add(id);
				}
			}
			List<UserSoul> list = userSoulDAO.getUserSoulsFromCache(userId, ids);
			//用户战魂最大等级，用户战魂最大星级
			UserSoul maxSoul=null;
			for(UserSoul soul:list){
				if(maxSoul==null){
					maxSoul=soul;
					continue;
				}
				maxSoul=maxSoul.getLevel()<soul.getLevel()?soul:maxSoul;
			}			
			int soulRare=GameCache.getSoul(maxSoul.getSoulId()).getSoulRare();
			for(int i=0;i<3-size;i++){
				int soul[]=Friend.ROBOT_SOULS[soulRare-1];
				int soulId=soul[new Random().nextInt(soul.length)];
				int soulMaxLevel=GameCache.getSoul(soulId).getLvMax();
				int randomLevel=Friend.ROBOT_SOUL_LEVEL[new Random().nextInt(Friend.ROBOT_SOUL_LEVEL.length)];
				//随机机器人战魂等级：用户最大战魂等级+【-2，3】
				int robotLevel=maxSoul.getLevel()+randomLevel>soulMaxLevel?soulMaxLevel:maxSoul.getLevel()+randomLevel;
				if(robotLevel<=0){
					robotLevel=1;
				}
				FriendVO f = MessageFactory.getMessage(FriendVO.class);
				UserCap cap = new UserCap();
				cap.setUserId(-1);
				cap.setUserSoulId(1);
				cap.setSoulId(soulId);
				cap.setLevel(robotLevel);
				cap.setUserLevel(user.getLevel());
				cap.setCreateTime(new Date());
				cap.setUpdateTime(new Date());
				cap.setEquipments(new ArrayList<Integer>());
				UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
				vo.init(cap);
				f.setCap(vo);
				ff.getAdventurers().add(f);
			}
		}
		return ff;
	}

	@Override
	public int addFriendCapacity(int userId) {
		User user = userService.getExistUserCache(userId);
		if(user.getFriendCapacity()>User.MAX_FRIEND_CAPACITY){
			throw new GameException(GameException.CODE_好友容量已到最大容量, 
					" capacity is max value."+User.MAX_FRIEND_CAPACITY);
		}
		int currency=User.USER_提升好友上线价格;
		if(activityService.activityIsStart(ActivityDefine.DEFINE_ID_好友上限数量打折)){
			ZoneConfig config=activityDAO.queryZoneConfig(ZoneConfig.ID_USER_ID_SEED);
			currency=config.getAcFriendCapacityPrice();
		}
		userService.decrementCurrency(user, currency, LoggerType.TYPE_增加好友容量, "");
		user.setFriendCapacity(user.getFriendCapacity()+5);
		
		Map<String, String> hash = new HashMap<>();
		hash.put("friendCapacity", String.valueOf(user.getFriendCapacity()));
		userDAO.updateUserCache(userId, hash);
		return user.getCurrency();
	}

	@Override
	public List<FriendGifiVO> gainFriendGifis(int userId) {
		
		//删除过期的礼品(早于今天凌晨0点的都删除)
		friendGifiDAO.deleteFriendGift(userId, DateUtils.changeDateTime(new Date(), 0, 0, 0, 0));
		
		List<FriendGift> gifis = friendGifiDAO.queryFriendGifts(userId);
		List<FriendGifiVO> vos = new ArrayList<FriendGifiVO>();
		for(FriendGift g : gifis){
			FriendGifiVO vo = MessageFactory.getMessage(FriendGifiVO.class);
			vo.init(g);
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public void updateWant(int userId, List<Integer> want) {
		if(want.size()!=3){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		for(int zone : want){
			if(zone!=0){
				if(GameCache.getFriendGifiRule(zone)==null){
					throw new GameException(GameException.CODE_参数错误, "zone rule no found."+zone);
				}
			}
		}
		UserCap cap = userTeamDAO.getUserCapCache(userId);
		cap.setWant(want);
		userTeamDAO.updateUserCapCache(cap);
	}

	@Override
	public List<FriendGifiRule> queryFriendRules() {
		return friendGifiCfgDAO.queryFriendRules();
	}

	@Override
	public GainAwardVO chargeFriendGift(int userId, List<Integer> gifiIds) {
		User user = userService.getExistUserCache(userId);
		int oldPoint = user.getPoint();
		int oldGold = user.getGold();
		int oldCurrency = user.getCurrency();
		List<Goods> goodses = new ArrayList<Goods>();
		for(int id : gifiIds){
			FriendGift gift = friendGifiDAO.queryFriendGift(id, userId);
			if(gift == null || gift.isReceieve()){
				throw new GameException(GameException.CODE_奖励不存在, "no gift:" + id);
			}
			FriendGifiRule rule = GameCache.getFriendGifiRule(gift.getZone());
			if(rule == null){
				throw new GameException(GameException.CODE_配置表不存在, "FriendGifiRule zone:" + gift.getZone());
			}
			switch (rule.getType()) {
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				Goods goods = new Goods();
				goods.setType(rule.getType());
				goods.setNum(rule.getNum());
				goods.setGoodsId(rule.getGifiId());
				goodses.add(goods);
				break;
			case Goods.TYPE_GOLD:
				userService.incrementGold(user, rule.getNum(), LoggerType.TYPE_收取好友赠品, ""+gift.getFriendId());
				break;
			case Goods.TYPE_CURRENCY:
				userService.incrementCurrency(user, rule.getNum(), LoggerType.TYPE_收取好友赠品,""+gift.getFriendId());
				break;
			case Goods.TYPE_FRIENDLY_POINT:
				userService.incrementFriendlyPoint(user.getUserId(), rule.getNum(), LoggerType.TYPE_收取好友赠品, ""+gift.getFriendId());
				break;
			default:
				break;
			}
			gift.setReceieve(true);
			friendGifiDAO.updateFirendGift(gift);
		}
		
		GainAwardVO awards =MessageFactory.getMessage(GainAwardVO.class);
		awards.setCurrency(user.getCurrency());
		awards.setGold(user.getGold());
		awards.setPoint(user.getPoint());
		UserStat stat = userStatDAO.queryUserStat(userId);
		awards.setFriendlyPoint(stat.getFriendlyPoint());
		awards.setGoodses(new ArrayList<UserGoodsVO>());
		if(goodses.size()!=0){
			Backage backage = userGoodsService.getPackage(userId);
			userGoodsService.checkBackageFull(backage, user);
			List<UserGoodsVO> ugs = userGoodsService.addGoods(user, backage, goodses, LoggerType.TYPE_收取好友赠品, "");
			awards.setGoodses(ugs);
		}
		awards.setAddPoint(user.getPoint() - oldPoint);
		awards.setAddGold(user.getGold() - oldGold);
		awards.setAddCurrency(user.getCurrency() - oldCurrency);
		return awards;
	}

	@Override
	public void handselGift(int userId, int zone, List<Integer> friendIds) {
		User user = userService.getExistUserCache(userId);
		UserCap cap = userTeamDAO.getUserCapCache(userId);
		UserStat stat = userStatDAO.queryUserStat(userId);
		FriendGifiRule rule = GameCache.getFriendGifiRule(zone);
		if(rule==null){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		for(int friendId : friendIds){
			Friend f = friendDAO.queryFriend(userId, friendId);
			if(f==null){
				throw new GameException(GameException.CODE_参数错误, "");
			}
			if(stat.getHandselFriend().indexOf(friendId)!=-1){
				throw new GameException(GameException.CODE_参数错误, "");
			}
			stat.getHandselFriend().add(friendId);
			
			
			FriendGift gift = new FriendGift();
			gift.setUserId(friendId);
			gift.setFriendId(userId);
			gift.setWant(cap.getWant());
			gift.setSoulId(cap.getSoulId());
			gift.setSoulLevel(cap.getLevel());
			gift.setZone(zone);
			gift.setFriendName(user.getPlayerName());
			
			//送给电脑就直接送给玩家本人
			if(friendId==User.USER_ID_好友机器人){
				gift.setUserId(userId);
				gift.setFriendId(User.USER_ID_好友机器人);
				gift.setFriendName(User.USER_NAME_机器人);
				UserCap rbootCap  = userTeamDAO.getUserCapCache(User.USER_ID_好友机器人);
				gift.setSoulId(rbootCap.getSoulId());
				gift.setSoulLevel(rbootCap.getLevel());
			}
			friendGifiDAO.addFirendGift(gift);
			//赠送好友任务
			userMissionService.finishMissionCondition(user, MissionCondition.TYPE_好友赠送, 0, 1);
		}
		UserStatOpt dbopt=new UserStatOpt();
		dbopt.handselFriend=SQLOpt.EQUAL;
		userStatDAO.updateUserStat(dbopt, stat);
	}
	
//	public void sendCurrencyGift(int userId, List<Integer> friendIds) {
//		User user = userService.getExistUserCache(userId);
//		UserCap cap = userTeamDAO.getUserCapCache(userId);
//		UserStat stat = userStatDAO.queryUserStat(userId);
//		
//		// 查询魂钻规则
//		int zone = 7;
//		FriendGifiRule rule = GameCache.getFriendGifiRule(zone);
//		if(rule==null){
//			throw new GameException(GameException.CODE_参数错误, "");
//		}
//		for(int friendId : friendIds){
//			Friend f = friendDAO.queryFriend(userId, friendId);
//			if(f==null){
//				throw new GameException(GameException.CODE_参数错误, "");
//			}
//			if(stat.getHandselFriend().indexOf(friendId)!=-1){
//				throw new GameException(GameException.CODE_参数错误, "");
//			}
//			stat.getHandselFriend().add(friendId);
//			
//			
//			FriendGift gift = new FriendGift();
//			gift.setUserId(friendId);
//			gift.setFriendId(userId);
//			gift.setWant(cap.getWant());
//			gift.setSoulId(cap.getSoulId());
//			gift.setSoulLevel(cap.getLevel());
//			gift.setZone(zone);
//			gift.setFriendName(user.getPlayerName());
//			
//			//送给电脑就直接送给玩家本人
//			if(friendId==User.USER_ID_好友机器人){
//				gift.setUserId(userId);
//				gift.setFriendId(User.USER_ID_好友机器人);
//				gift.setFriendName(User.USER_NAME_机器人);
//				UserCap rbootCap  = userTeamDAO.getUserCapCache(User.USER_ID_好友机器人);
//				gift.setSoulId(rbootCap.getSoulId());
//				gift.setSoulLevel(rbootCap.getLevel());
//			}
//			friendGifiDAO.addFirendGift(gift);
//			//赠送好友任务
//			userMissionService.finishMissionCondition(user, MissionCondition.TYPE_好友赠送, 0, 1);
//		}
//		UserStatOpt dbopt=new UserStatOpt();
//		dbopt.handselFriend=SQLOpt.EQUAL;
//		userStatDAO.updateUserStat(dbopt, stat);
//	}

	@Override
	public UserCapVO gainFriendInfo(int userId) {
		UserCap cap = userTeamDAO.getUserCapCache(userId);
		if(cap==null){
			throw new GameException(GameException.CODE_用户不存在," user no found");
		}
		UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
		vo.init(cap);
		return vo;
	}
	private void sentAppFriendNoti(int userId){
		GameEvent event = new NotifiEvent(userId,NotifiEvent.NOTIF_TYPE_好友申请);
		TimerController.submitGameEvent(event);
	}
}
