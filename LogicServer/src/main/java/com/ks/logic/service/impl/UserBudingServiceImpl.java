package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserBudingService;
import com.ks.model.buding.Buding;
import com.ks.model.buding.BudingDrop;
import com.ks.model.buding.BudingRule;
import com.ks.model.buding.UserBuding;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.MissionCondition;
import com.ks.model.user.User;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.buding.CollectResultVO;
import com.ks.protocol.vo.buding.LevelUpBudingResultVO;
import com.ks.protocol.vo.buding.UserBudingVO;
import com.ks.protocol.vo.goods.UserGoodsVO;

public class UserBudingServiceImpl extends BaseService implements
		UserBudingService {

	@Override
	public List<UserBudingVO> gainUserBudings(int userId) {
		List<UserBuding> userBudings = userBudingDAO.queryUserBudings(userId);
		Collection<Buding> budings = GameCache.getBudings();		
		if(userBudings.size()!=budings.size()){
			for(Buding b : budings){
				boolean has = false;
				for(UserBuding ub : userBudings){
					if(ub.getBudingId()==b.getBudingId()){
						has = true;
						break;
					}
				}
				if(!has){
					UserBuding ub = new UserBuding();
					ub.setUserId(userId);
					ub.setBudingId(b.getBudingId());
					ub.setLevel(1);
					ub.setLastCollectTime(new Date());
					userBudingDAO.addUserBuding(ub);
					userBudings.add(ub);
				}
			}
		}
		List<UserBudingVO> vos = new ArrayList<UserBudingVO>();
		for(UserBuding ub : userBudings){
			BudingRule rule = GameCache.getBudingRule(ub.getBudingId(), ub.getLevel());
			if(rule ==null){
				continue;
			}
			if(new Date().getTime()-ub.getLastCollectTime().getTime()>=(long)rule.getTime()){
				ub.setCollectCount(0);
				ub.setLastCollectTime(new Date());
			}
			UserBudingVO vo = MessageFactory.getMessage(UserBudingVO.class);
			vo.init(ub);
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public LevelUpBudingResultVO levelUpBuding(int userId, int budingId, int gold) {
		if(gold<0||gold>9999999){
			throw new GameException(GameException.CODE_参数错误, "gold : "+ gold);
		}
		UserBuding userBuding = getExistUserBuding(userId, budingId);
		Buding buding = GameCache.getBuding(budingId);
		if(userBuding.getLevel()>=buding.getMaxLevel()){
			throw new GameException(GameException.CODE_建筑等级已满, "");
		}
		BudingRule rule = GameCache.getBudingRule(budingId, userBuding.getLevel());
		userBuding.setGold(userBuding.getGold()+gold);
		while(userBuding.getGold()>=rule.getGold()&&userBuding.getLevel()<buding.getMaxLevel()){
			userBuding.setLevel(userBuding.getLevel()+1);
			userBuding.setGold(userBuding.getGold()-rule.getGold());
			rule = GameCache.getBudingRule(budingId, userBuding.getLevel());
		}
		if(userBuding.getLevel()>=buding.getMaxLevel()){
			gold -= userBuding.getGold();
			userBuding.setGold(0);
		}
		User user = userService.getExistUserCache(userId);
		userService.decrementGold(user, gold, LoggerType.TYPE_建筑升级, "");
		userBudingDAO.updateUserBuding(userBuding);
		LevelUpBudingResultVO vo = MessageFactory.getMessage(LevelUpBudingResultVO.class);
		vo.init(user.getGold(), userBuding);
		return vo;
	}

	@Override
	public UserBuding getExistUserBuding(int userId, int budingId) {
		UserBuding buding = userBudingDAO.queryUserBuding(userId, budingId);
		if(buding==null){
			throw new GameException(GameException.CODE_建筑不存在, "");
		}
		return buding;
	}

	@Override
	public CollectResultVO collectBuding(int userId, int budingId,int count) {
		if(count<0||count>9999999){
			throw new GameException(GameException.CODE_参数错误, "count : "+ count);
		}
		UserBuding userBuding = getExistUserBuding(userId, budingId);
		Buding buding = GameCache.getBuding(budingId);
		if(!buding.isCollect()){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		BudingRule rule = GameCache.getBudingRule(budingId, userBuding.getLevel());
		Date now = new Date();
		if(now.getTime()-userBuding.getLastCollectTime().getTime()>=(long)rule.getTime()){
			userBuding.setCollectCount(0);
			userBuding.setLastCollectTime(now);
		}
		
		userBuding.setCollectCount(userBuding.getCollectCount()+count);
		if(userBuding.getCollectCount()>rule.getCount()){
			return null;
		}
		
		User user = userService.getExistUserCache(userId);
		Backage backage = userGoodsService.getPackage(userId);
		
		userGoodsService.checkBackageFull(backage, user);
		List<Goods> goodses = new ArrayList<Goods>();
		for(int level=1;level<=userBuding.getLevel();level++){
			List<BudingDrop> drops = GameCache.getBudingDrops(budingId, level);
			int maxRate = 0;
			for(BudingDrop drop : drops){
				maxRate+=drop.getRate();
			}
			for(int i=0;i<count;i++){
				int random = (int) (Math.random()*maxRate);
				int rate = 0;
				for(BudingDrop drop : drops){
					rate+=drop.getRate();
					if(rate>random){
						Goods goods = null;
						for(Goods g : goodses){
							if(g.getGoodsId() == drop.getStuffId()){
								goods = g;
								break;
							}
						}
						if(goods == null){
							goods = new Goods();
							goods.setType(Goods.TYPE_STUFF);
							goods.setGoodsId(drop.getStuffId());
							goodses.add(goods);
						}
						goods.setNum(goods.getNum()+drop.getNum());
						break;
					}
				}
			}	
		}
		userBudingDAO.updateUserBuding(userBuding);		
		List<UserGoodsVO> ugs = userGoodsService.addGoods(user, backage, goodses, LoggerType.TYPE_建筑收集, budingId+","+count);		
		CollectResultVO vo = MessageFactory.getMessage(CollectResultVO.class);
		vo.setUserGoods(ugs);
		UserBudingVO ubVO = MessageFactory.getMessage(UserBudingVO.class);
		ubVO.init(userBuding);
		vo.setUserBuding(ubVO);
		//做家园收获任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_家园收获, 0,count);
		return vo;
	}
	
	@Override
	public CollectResultVO collectBuding(int userId, int budingId) {
		
		UserBuding userBuding = getExistUserBuding(userId, budingId);
		Buding buding = GameCache.getBuding(budingId);
		if(!buding.isCollect()){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		BudingRule rule = GameCache.getBudingRule(budingId, userBuding.getLevel());
		Date now = new Date();
		if(now.getTime()-userBuding.getLastCollectTime().getTime()>=(long)rule.getTime()){
			userBuding.setCollectCount(0);
			userBuding.setLastCollectTime(now);
		}
		
		// 剩余收获次数
		int count = rule.getCount() - userBuding.getCollectCount();
		userBuding.setCollectCount(userBuding.getCollectCount()+count);
		if(count <= 0){
			return null;
		}
		
		User user = userService.getExistUserCache(userId);
		Backage backage = userGoodsService.getPackage(userId);
		
		userGoodsService.checkBackageFull(backage, user);
		List<Goods> goodses = new ArrayList<Goods>();
		for(int level=1;level<=userBuding.getLevel();level++){
			List<BudingDrop> drops = GameCache.getBudingDrops(budingId, level);
			int maxRate = 0;
			for(BudingDrop drop : drops){
				maxRate+=drop.getRate();
			}
			for(int i=0;i<count;i++){
				int random = (int) (Math.random()*maxRate);
				int rate = 0;
				for(BudingDrop drop : drops){
					rate+=drop.getRate();
					if(rate>random){
						Goods goods = null;
						for(Goods g : goodses){
							if(g.getGoodsId() == drop.getStuffId()){
								goods = g;
								break;
							}
						}
						if(goods == null){
							goods = new Goods();
							goods.setType(Goods.TYPE_STUFF);
							goods.setGoodsId(drop.getStuffId());
							goodses.add(goods);
						}
						goods.setNum(goods.getNum()+drop.getNum());
						break;
					}
				}
			}	
		}
		userBudingDAO.updateUserBuding(userBuding);		
		List<UserGoodsVO> ugs = userGoodsService.addGoods(user, backage, goodses, LoggerType.TYPE_建筑收集, budingId+","+count);		
		CollectResultVO vo = MessageFactory.getMessage(CollectResultVO.class);
		vo.setUserGoods(ugs);
		UserBudingVO ubVO = MessageFactory.getMessage(UserBudingVO.class);
		ubVO.init(userBuding);
		vo.setUserBuding(ubVO);
		//做家园收获任务
		userMissionService.finishMissionCondition(user, MissionCondition.TYPE_家园收获, 0, 1);
		return vo;
	}

}
