package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.event.DataGivePropEvent;
import com.ks.logic.event.DataUsePropEvent;
import com.ks.logic.event.GameLoggerEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserGoodsService;
import com.ks.logic.utils.MarqueeUtils;
import com.ks.model.ZoneConfig;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.buding.Buding;
import com.ks.model.buding.UserBuding;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentRepair;
import com.ks.model.equipment.EquipmentSkill;
import com.ks.model.goods.Backage;
import com.ks.model.goods.BakProp;
import com.ks.model.goods.FightProp;
import com.ks.model.goods.Goods;
import com.ks.model.goods.GoodsSynthesis;
import com.ks.model.goods.GoodsSynthesisRule;
import com.ks.model.goods.Prop;
import com.ks.model.goods.UserBakProp;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.BakPropLogger;
import com.ks.model.logger.GoodsLogger;
import com.ks.model.logger.LoggerType;
import com.ks.model.mission.MissionCondition;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.goods.EqRefiningVO;
import com.ks.protocol.vo.goods.EqRepairRetVO;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.SellGoodsResultVO;
import com.ks.protocol.vo.goods.SellGoodsVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.timer.TimerController;
import com.ks.util.CheckUtil;

public class UserGoodsServiceImpl extends BaseService implements
		UserGoodsService {

	@Override
	public Backage getPackage(int userId) {
		Backage b = new Backage();
		b.init(userGoodsDAO.getUserGoodsesCache(userId));
		return b;
	}

	@Override
	public List<UserGoods> initUserGoods(int userId) {
		List<UserGoods> userGoods = userGoodsDAO.getUserGoodsesCache(userId);
		if(userGoods.size()!=0){
			clearPackage(userId);
		}
		
		userGoods = userGoodsDAO.queryUserGoods(userId);
		List<UserGoods> vos = new ArrayList<>();
		for(UserGoods ug : userGoods){
			if(ug.getGoodsId()!=0){
				vos.add(ug);
			}
			userGoodsDAO.addUserGoodsCache(ug);
		}
		return vos;
	}
	
	@Override
	public List<UserGoodsVO> addGoods(User user,Backage backage,List<Goods> goodses,
			int type, String description) {
		Set<UserGoods> ugs = new HashSet<>();
		for(Goods g : goodses){
			if(g.getType()!=Goods.TYPE_PROP && g.getType()!=Goods.TYPE_EQUIPMENT && g.getType()!=Goods.TYPE_STUFF){
				throw new GameException(GameException.CODE_参数错误, "goods type : " + g.getType());
			}
			
			if(g.getType()==Goods.TYPE_EQUIPMENT){
				// 装备处理
				for(int i=1; i<=g.getNum(); i++){
					UserGoods goods = backage.getNotUseGoodses().poll();
					if(goods == null){
						goods = createUserGoods(user.getUserId());
					}
					goods.setGoodsType(UserGoods.GOODS_TYPE_EQUIPMENT);
					goods.setGoodsId(g.getGoodsId());
					goods.setNum(1);
					userGoodsDAO.updateUserGoodsCache(goods);
					ugs.add(goods);
					backage.addUseGoods(goods);
				}
			}else{
				// 非装备处理(道具和材料)
				int userGoodsType = g.getType()==Goods.TYPE_PROP?UserGoods.GOODS_TYPE_ITEM:UserGoods.GOODS_TYPE_STUFF;
				// 用户包裹指定类型物品
				List<UserGoods> uglist = backage.getItemGoods(userGoodsType, g.getGoodsId());
				int num = g.getNum();
				
				// 找到指定类型物品处理
				if(uglist!=null){
					for(UserGoods ug : uglist){
						if(ug.getNum()<UserGoods.MAX_NUM){
							ug.setNum(ug.getNum()+num);
							if(ug.getNum()>UserGoods.MAX_NUM){
								num = ug.getNum()-UserGoods.MAX_NUM;
								ug.setNum(UserGoods.MAX_NUM);
							}else{
								num=0;
							}
							userGoodsDAO.updateUserGoodsCache(ug);
							ugs.add(ug);
							if(num==0){
								break;
							}
						}
					}
				}
				
				// 没有找到指定物品类型处理
				while(num>0){
					UserGoods goods = backage.getNotUseGoodses().poll();
					if(goods == null){
						goods = createUserGoods(user.getUserId());
					}
					goods.setGoodsType(userGoodsType);
					goods.setGoodsId(g.getGoodsId());
					goods.setNum(num);
					
					if(goods.getNum()>UserGoods.MAX_NUM){
						num = goods.getNum()-UserGoods.MAX_NUM;
						goods.setNum(UserGoods.MAX_NUM);
					}else{
						num=0;
					}
					userGoodsDAO.updateUserGoodsCache(goods);
					backage.addUseGoods(goods);
					ugs.add(goods);
				}
			}
			
			GoodsLogger logger = null;
			switch (g.getType()) {
			case Goods.TYPE_PROP:
				logger = GoodsLogger.createPropLogger(user.getUserId(), type, g.getNum(), g.getGoodsId(), description);
				break;
			case Goods.TYPE_EQUIPMENT:
				logger = GoodsLogger.createEquipmentLogger(user.getUserId(), type, g.getNum(), g.getGoodsId(), description);
				// 添加跑马灯
				Equipment equip = GameCache.getEquipment(g.getGoodsId());
				if (equip != null && equip.getMarquee() == 1 && type != LoggerType.TYPE_高级账号) {
					marqueeService.add(MarqueeUtils.createEquipmentMarquee(user.getPlayerName(), equip.getName()));
				}
				break;
			case Goods.TYPE_STUFF:
				logger = GoodsLogger.createStuffLogger(user.getUserId(), type, g.getNum(), g.getGoodsId(), description);
				break;
			default:
				throw new GameException(GameException.CODE_参数错误, "goods type : " + g.getType());
			}
			GameLoggerEvent event = new GameLoggerEvent(logger);
			TimerController.submitGameEvent(event);
		}
		List<UserGoodsVO> vos = new ArrayList<UserGoodsVO>();
		for(UserGoods o : ugs){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(o);
			vos.add(vo);
		}
		
		//数据统计
		for(Goods goods: goodses){
			DataGivePropEvent gve=new DataGivePropEvent(user.getPartner(), user.getUserId(), user.getUsername(),
			goods.getGoodsId(), goods.getType(), goods.getNum(), type,new Date());
			TimerController.submitGameEvent(gve);
		}
		return vos;
	}

	private UserGoods createUserGoods(int userId) {
		UserGoods goods = new UserGoods();
		goods.setUserId(userId);
		goods.setUpdateTime(new Date());
		goods.setCreateTime(new Date());
		userGoodsDAO.addUserGoods(goods);
		userGoodsDAO.addUserGoodsCache(goods);
		return goods;
	}
	@Override
	public void useEquipment(int userId, long userSoulId, int gridId) {
		userService.getExistUserCache(userId);
				
		UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
		Backage backage = getPackage(userId);
		List<UserGoods> soulEquipments = new ArrayList<>();
		for(UserGoods ug : backage.getUseGoodses().values()){
			if(ug.getUserSoulId()==userSoulId){
				soulEquipments.add(ug);
			}
		}		
		UserGoods currEq = backage.getUseGoods(gridId);//当前要使用的装备
		if(currEq.getGoodsType()!=UserGoods.GOODS_TYPE_EQUIPMENT){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		Equipment e = GameCache.getEquipment(currEq.getGoodsId());		
		
		for(UserGoods ug : soulEquipments){
			Equipment se = GameCache.getEquipment(ug.getGoodsId());
			if(se.getEquipmentType()==e.getEquipmentType()){
				ug.setUserSoulId(0);
				userGoodsDAO.updateUserGoodsCache(ug);
				break;
			}
		}
		currEq.setUserSoulId(soul.getId());
		userGoodsDAO.updateUserGoodsCache(currEq);
		
	}

	@Override
	public void unUseEquipment(int userId, int gridId) {
		Backage backage = getPackage(userId);
		UserGoods ug = backage.getUseGoods(gridId);
		if(ug.getGoodsType()!=UserGoods.GOODS_TYPE_EQUIPMENT){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		ug.setUserSoulId(0);
		userGoodsDAO.updateUserGoodsCache(ug);
	}

	@Override
	public List<FightProp> initFightProp(int userId) {
		List<FightProp> fps = fightPropDAO.getFightPropListFromCache(userId);
		if(fps.size()==0){
			fps = fightPropDAO.queryFightProp(userId);
			if(fps.size()==0){
				for(int i=0;i<5;i++){
					FightProp fp = new FightProp();
					fp.setUserId(userId);
					fp.setGridId(i+1);
					fp.setCreateTime(new Date());
					fp.setUpdateTime(new Date());
					fps.add(fp);
				}
				fightPropDAO.addFightProps(fps);
			}
			fightPropDAO.addFightPropsCache(fps);
		}
		return fps;
	}

	@Override
	public void clearPackage(int userId) {
		userGoodsDAO.clearUserGoodsCache(userId);
	}

	@Override
	public void clearFightProps(int userId) {
		List<FightProp> fps = fightPropDAO.getFightPropListFromCache(userId);
		if(fps.size()>0){
			fightPropDAO.updateFightProps(fps);
			fightPropDAO.clearFightPropCache(userId);
		}
	}

	@Override
	public List<UserGoodsVO> updateFightProp(int userId, List<FightPropVO> fps) {
		User user = userService.getExistUserCache(userId);
		Backage backage = getPackage(userId);
		List<Goods> gs = new ArrayList<Goods>();
		Set<UserGoods> updateGoods = new HashSet<UserGoods>();
		
		List<FightPropVO> fpps = new ArrayList<FightPropVO>();
		
		out:
		for(FightPropVO f1 : fps){
			for(FightPropVO f2 : fpps){
				if(f1.getGridId() == f2.getGridId()){
					continue out;
				}
			}
			fpps.add(f1);
		}
		fps = fpps;
		
		for(FightPropVO fp : fps){
			if(fp.getPropId() != 0){
				Prop item = GameCache.getProp(fp.getPropId());
				if(item.getFightTakeNum()<fp.getNum()||fp.getNum()<0){
					throw new GameException(GameException.CODE_参数错误, "");
				}
			}
			FightProp prop = fightPropDAO.geFightPropFromCache(userId, fp.getGridId());
			if(fp.getPropId()==prop.getPropId()){
				int num = fp.getNum()-prop.getNum();
				if(num>0){
					List<UserGoods> ugs = deleteGoods(backage, UserGoods.GOODS_TYPE_ITEM, fp.getPropId(), num, LoggerType.TYPE_背包放入战斗道具, "");
					updateGoods.addAll(ugs);
				}else{
					Goods goods = new Goods();
					goods.setGoodsId(fp.getPropId());
					goods.setType(Goods.TYPE_PROP);
					goods.setNum(-num);
					gs.add(goods);
				}
			}else{
				Goods goods = new Goods();
				goods.setGoodsId(prop.getPropId());
				goods.setType(Goods.TYPE_PROP);
				goods.setNum(prop.getNum());
				gs.add(goods);
				
				if(fp.getPropId() != 0){
					int num = fp.getNum();
					List<UserGoods> ugs = deleteGoods(backage, UserGoods.GOODS_TYPE_ITEM, fp.getPropId(), num, LoggerType.TYPE_背包放入战斗道具, "");
					updateGoods.addAll(ugs);
				}
			}
			prop.setPropId(fp.getPropId());
			prop.setNum(fp.getNum());
			fightPropDAO.updateFightPropCache(prop);
		}
		List<UserGoodsVO> vos = addGoods(user, backage, gs, LoggerType.TYPE_战斗道具放入背包, "");
		
		for(UserGoods ug : updateGoods){
			boolean has = false;
			for(UserGoodsVO vo : vos){
				if(vo.getGridId()==ug.getGridId()){
					has = true;
					break;
				}
			}
			if(!has){
				UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
				vo.init(ug);
				vos.add(vo);
			}
		}
		return vos;
	}

	/**
	 * 用户物品格子排序规则
	 * 1. 物品数量少的优先
	 * 2. 格子靠前的物品优先
	 * 
	 * 原则1优先原则2
	 * */
	private Comparator<UserGoods> USER_GOODS_COMPARABLE = new Comparator<UserGoods>() {
		@Override
        public int compare(UserGoods o1, UserGoods o2) {
	        if (o1 == null || o2 == null) {
	        	return 0;
	        }
	        
	        if (o1.getNum() > o2.getNum()) {
	        	return 1;
	        } else if (o1.getNum() < o2.getNum()) {
	        	return -1;
	        } else if (o1.getGridId() > o2.getGoodsId()) {
	        	return 1;
	        } else if (o1.getGridId() < o2.getGoodsId()) {
	        	return -1;
	        }
	        return 0;
        }
	};
	
	@Override
	public List<UserGoods> deleteGoods(Backage backage, int goodsType,
			int goodsId, int num, int type, String description) {
		if(num<=0){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		List<UserGoods> updateGoods = new ArrayList<UserGoods>();
		List<UserGoods> goodses = backage.getItemGoods(goodsType, goodsId);
		int userId = 0;
		
		if(goodses == null||goodses.size()==0){
			throw new GameException(GameException.CODE_道具数量不足, "");
		}
		List<UserGoods> resetList=new ArrayList<UserGoods>();
		resetList.addAll(goodses);
		Collections.sort(resetList, USER_GOODS_COMPARABLE);
		
		for(UserGoods ug : resetList){
			userId = ug.getUserId();
			if(ug.getNum()>=num){
				ug.setNum(ug.getNum()-num);
				if(ug.getNum()==0){
					
					backage.resetUserGoods(ug);
				}
				userGoodsDAO.updateUserGoodsCache(ug);
				updateGoods.add(ug);
				num=0;
				break;
			}else{
				num-=ug.getNum();
				ug.setNum(0);
				backage.resetUserGoods(ug);
				userGoodsDAO.updateUserGoodsCache(ug);
				updateGoods.add(ug);
			}
		}
		if(num>0){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		GoodsLogger logger = null;
		switch (goodsType) {
		case UserGoods.GOODS_TYPE_ITEM:
			logger = GoodsLogger.createPropLogger(userId, type, -num, goodsId, description);
			break;
		case UserGoods.GOODS_TYPE_EQUIPMENT:
			logger = GoodsLogger.createEquipmentLogger(userId, type, -num, goodsId, description);
			break;
		case UserGoods.GOODS_TYPE_STUFF:
			logger = GoodsLogger.createStuffLogger(userId,  type, -num, goodsId, description);
			break;
		default:
			throw new GameException(GameException.CODE_参数错误, "goods type : " + goodsType);
		}
		GameLoggerEvent event = new GameLoggerEvent(logger);
		TimerController.submitGameEvent(event);
		
		if(userId!=0){
			User user=userService.getExistUser(userId);
			//数据统计
			DataUsePropEvent gve=new DataUsePropEvent(user.getPartner(), user.getUserId(),
				user.getUsername(),goodsId, goodsType, num, type,new Date());
			TimerController.submitGameEvent(gve);
		}
		return updateGoods;
	}
	
	@Override
	public void useProp(int userId, int gridId) {
		FightProp fightProp = fightPropDAO.geFightPropFromCache(userId, gridId);
		if(fightProp.getNum()>0){
			fightProp.setNum(fightProp.getNum()-1);
			fightPropDAO.updateFightPropCache(fightProp);
			
			int logType = LoggerType.TYPE_战斗使用;
//			// 使用金钥匙,银钥匙
//			if (fightProp.getPropId() == FightProp.GOODS_ID_GOLD_KEY || fightProp.getPropId()  == FightProp.GOODS_ID_SILVER_KEY) {
//				logType = LoggerType.TYPE_钥匙使用;
//				
//				// 添加副本要是时间 buff
//				long endMins = Calendar.getInstance().getTimeInMillis() + UserBuff.TIME_ONE_HOUR;
//				UserBuff addbuff = UserBuff.create(userId, UserBuff.BUFF_ID_副本钥匙, fightProp.getPropId(), new Date(endMins));
//				userBuffService.addUserBuff(userId, addbuff, UserBuff.TIME_ONE_HOUR);
//			}
			
			GoodsLogger logger = GoodsLogger.createPropLogger(userId, logType, -1, fightProp.getPropId(), "");
			GameLoggerEvent event = new GameLoggerEvent(logger);
			TimerController.submitGameEvent(event);
		}else{
			throw new GameException(GameException.CODE_参数错误, "num : "+fightProp.getNum());
		}
	}

	@Override
	public SellGoodsResultVO sellGoods(int userId, List<SellGoodsVO> sellGoods) {
		if(CheckUtil.isRepeatlist(sellGoods)){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		for(SellGoodsVO vo : sellGoods){
			if(vo.getNum()<=0){
				throw new GameException(GameException.CODE_参数错误, "");
			}
		}
		Backage backage = getPackage(userId);
		User user = userService.getExistUserCache(userId);
		int gold = 0;
		List<UserGoods> ugs = new ArrayList<UserGoods>();
		for(SellGoodsVO vo : sellGoods){
			UserGoods goods = backage.getUseGoods(vo.getGridId());
			switch (goods.getGoodsType()) {
			case UserGoods.GOODS_TYPE_ITEM:
				Prop item = GameCache.getProp(goods.getGoodsId());
				gold +=(item.getSellPrice()*vo.getNum());
				break;
			case UserGoods.GOODS_TYPE_EQUIPMENT:
				Equipment e = GameCache.getEquipment(goods.getGoodsId());
				gold += (e.getSellPrice()*vo.getNum());
				break;
			case UserGoods.GOODS_TYPE_STUFF:
				Stuff s = GameCache.getStuff(goods.getGoodsId());
				gold += (s.getSellGold()*vo.getNum());
				break;
			default:
				throw new GameException(GameException.CODE_参数错误, "goods type : "+goods.getGoodsType());
			}
			
			if(goods.getNum()<vo.getNum()){
				throw new GameException(GameException.CODE_参数错误, "");
			}
			
			goods.setNum(goods.getNum()-vo.getNum());
			GoodsLogger logger = null;
			switch (goods.getGoodsType()) {
			case UserGoods.GOODS_TYPE_ITEM:
				logger = GoodsLogger.createPropLogger(goods.getUserId(), LoggerType.TYPE_卖出物品, 0, goods.getGoodsId(), "");
				break;
			case UserGoods.GOODS_TYPE_EQUIPMENT:
				logger = GoodsLogger.createEquipmentLogger(goods.getUserId(), LoggerType.TYPE_卖出物品,  0, goods.getGoodsId(), "");
				break;
			case UserGoods.GOODS_TYPE_STUFF:
				logger = GoodsLogger.createStuffLogger(goods.getUserId(),  LoggerType.TYPE_卖出物品, 0, goods.getGoodsId(), "");
				break;
			default:
				throw new GameException(GameException.CODE_参数错误, "goods type : " + goods.getGoodsType());
			}
			GameLoggerEvent event = new GameLoggerEvent(logger);
			TimerController.submitGameEvent(event);
			
			ugs.add(goods);
			//处理结果
			if(goods.getNum()==0){
				backage.resetUserGoods(goods);
			}
			userGoodsDAO.updateUserGoodsCache(goods);
		}
		
		userService.incrementGold(user, gold, LoggerType.TYPE_卖出物品获得, "");
		SellGoodsResultVO vo = MessageFactory.getMessage(SellGoodsResultVO.class);
		vo.init(ugs, user.getGold());
		return vo;
	}

	@Override
	public void checkBackageFull(Backage backage, User user) {
		int box=GameCache.getUserRule(user.getLevel()).getItemBox();
		if(backage.getUseGoodses().size()>=user.getItemCapacity()+box){
			throw new GameException(GameException.CODE_背包已满, "");
		}
	}

	@Override
	public int addItemCapacity(int userId) {
		User user = userService.getExistUserCache(userId);
		if(user.getItemCapacity()>User.MAX_ITEM_CAPACITY){
			throw new GameException(GameException.CODE_道具仓库已到最大容量, "");
		}
		int currency=User.USER_扩展家园上线价格;
		if(activityService.activityIsStart(ActivityDefine.DEFINE_ID_道具仓库格子打折)){
			ZoneConfig config=activityDAO.queryZoneConfig(ZoneConfig.ID_USER_ID_SEED);
			currency=config.getAcItemCapacityPrice();
		}
		userService.decrementCurrency(user, currency, LoggerType.TYPE_增加道具仓库容量, "");
		user.setItemCapacity(user.getItemCapacity()+5);
		
		Map<String, String> hash = new HashMap<>();
		hash.put("itemCapacity", String.valueOf(user.getItemCapacity()));
		userDAO.updateUserCache(userId, hash);
		return user.getCurrency();
	}

	@Override
	public List<UserGoods> gainUserGoods(int userId) {
		List<UserGoods> userGoods = userGoodsDAO.getUserGoodsesCache(userId);
		if(userGoods.size()==0){
			userGoods = userGoodsDAO.queryUserGoods(userId);
		}
		return userGoods;
	}

	@Override
	public GainAwardVO synthesisGoods(int userId, int id) {
		User user = userService.getExistUserCache(userId);
		int oldPoint = user.getPoint();
		int oldGold = user.getGold();
		int oldCurrency = user.getCurrency();
		GoodsSynthesis gs = GameCache.getGoodsSynthesis(id);
		if(gs==null){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		Backage backage = getPackage(userId);
		checkBackageFull(backage, user);
		UserBuding buding = null;
		if(gs.getGoodsType()==Goods.TYPE_EQUIPMENT){
			buding = userBudingDAO.queryUserBuding(userId, Buding.BUDING_ID_武具屋);
		}else{
			buding = userBudingDAO.queryUserBuding(userId, Buding.BUDING_ID_道具屋);
		}
		if(buding.getLevel()<gs.getBudingLevel()){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		List<UserGoods> updateGoods = new ArrayList<UserGoods>();
		for(GoodsSynthesisRule rule : gs.getRules()){
			switch (rule.getGoodsType()) {
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				List<UserGoods> ugs = deleteGoods(backage, rule.getGoodsType(), rule.getGoodsId(), rule.getNum(), LoggerType.TYPE_合成物品消耗, "id : " + id);
				for(UserGoods ug : ugs){
					updateGoods.add(ug);
				}
				break;
			case Goods.TYPE_GOLD:
				userService.decrementGold(user, rule.getNum(), LoggerType.TYPE_合成物品消耗, "id : " + id);
				break;
			case Goods.TYPE_CURRENCY:
				userService.decrementCurrency(user, rule.getNum(), LoggerType.TYPE_合成物品消耗, "id : " + id);
				break;
			case Goods.TYPE_FRIENDLY_POINT:
				userService.decrementFriendlyPoint(userId, rule.getNum(), LoggerType.TYPE_合成物品消耗, "id : " + id);
				break;
			default:
				break;
			}
		}
		List<UserGoodsVO> vos = new ArrayList<UserGoodsVO>();
		for(UserGoods ug : updateGoods){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(ug);
			vos.add(vo);
		}
		Goods goods = new Goods();
		goods.setGoodsId(gs.getGoodsId());
		goods.setNum(1);
		goods.setType(gs.getGoodsType());
		List<Goods> goodses = new ArrayList<Goods>();
		goodses.add(goods);
		List<UserGoodsVO> ugVOs = addGoods(user, backage, goodses,  LoggerType.TYPE_合成物品获得, "id : "+ id);
		vos.addAll(ugVOs);
		if(gs.getGoodsType()==Goods.TYPE_EQUIPMENT){
			userMissionService.finishMissionCondition(user, MissionCondition.TYPE_装备制造_不限装备, 0, 1);
			
			if (user.getGuideStep() == User.GUIDE_STEP21_锻造屋) {
				userService.nextStep(user, user.getGuideStep() + 1);
			}
		}else{
			userMissionService.finishMissionCondition(user, MissionCondition.TYPE_药剂制造_不限药剂, 0, 1);
			
			if (user.getGuideStep() == User.GUIDE_STEP20_道具屋) {
				userService.nextStep(user, user.getGuideStep() + 1);
			}
		}
		UserStat stat = userStatDAO.queryUserStat(userId);
		GainAwardVO gaVO =MessageFactory.getMessage(GainAwardVO.class);
		gaVO.setGold(user.getGold());
		gaVO.setCurrency(user.getCurrency());
		gaVO.setPoint(user.getPoint());
		gaVO.setFriendlyPoint(stat.getFriendlyPoint());
		gaVO.setGoodses(vos);
		gaVO.setAddPoint(user.getPoint() - oldPoint);
		gaVO.setAddGold(user.getGold() - oldGold);
		gaVO.setAddCurrency(user.getCurrency() - oldCurrency);
		return gaVO;
	}

	@Override
	public EqRepairRetVO repairEuipment(int userId, int gridId) {
		User user = userService.getExistUserCache(userId);
		Backage backage = getPackage(userId);
		UserGoods currEq=backage.getUseGoods(gridId);
		if(currEq==null||currEq.getGoodsType()!=UserGoods.GOODS_TYPE_EQUIPMENT){
			throw new GameException(GameException.CODE_参数错误,"grid."+gridId+" not euquipment");
		}
		if(currEq.getDurable()==0){
			throw new GameException(GameException.CODE_参数错误,"can't repair this equipment,durable is 0");
		}
		List<UserGoods> updateGoods = new ArrayList<UserGoods>();
		Equipment e=GameCache.getEquipment(currEq.getGoodsId());
		List<EquipmentRepair> repairs=e.getRepairs();
		for(EquipmentRepair rule : repairs){
			switch (rule.getGoodsType()) {
			case Goods.TYPE_STUFF:
				List<UserGoods> ugs = deleteGoods(backage,UserGoods.GOODS_TYPE_STUFF, rule.getAssId(), rule.getNum(), LoggerType.TYPE_合成物品消耗,"repair:"+currEq.getGridId());
				for(UserGoods ug : ugs){
					updateGoods.add(ug);
				}
				break;
			case Goods.TYPE_GOLD:
				userService.decrementGold(user, rule.getNum(), LoggerType.TYPE_合成物品消耗, "repair:"+currEq.getGridId());
				break;			
			default:
				break;
			}
		}
		//使用次数为0
		currEq.setDurable(0);
		userGoodsDAO.updateUserGoodsCache(currEq);
		EqRepairRetVO reapriVO=MessageFactory.getMessage(EqRepairRetVO.class);
		List<UserGoodsVO> goodVos = new ArrayList<UserGoodsVO>();
		for(UserGoods ug : updateGoods){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(ug);
			goodVos.add(vo);		}
		reapriVO.init(goodVos, user.getGold());
		return reapriVO;
	}

	@Override
	public List<UserBakPropVO> gainUserBakProps(int userId) {
		List<UserBakProp> props=userBakPropDao.querUserBakPropCache(userId);
		List<UserBakPropVO> vos=new ArrayList<>();
		for(UserBakProp p:props){
			UserBakPropVO vo=MessageFactory.getMessage(UserBakPropVO.class);
			vo.init(p);
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public UserBakProp addBakProps(User user,int bakPropId, int num, int logType,String des) {
		if(num<0){
			throw new GameException(GameException.CODE_参数错误, "num must be over 0");
		}
		int userId = user.getUserId();
		if(user.getBakProps() == null){
			user.setBakProps(new HashMap<Integer, UserBakProp>());
		}
		UserBakProp bak=user.getBakProps().get(bakPropId);
		if(bak == null){
			bak = userBakPropDao.getUserBakPropCache(userId, bakPropId);
		}
		BakProp bakdefine=GameCache.getBakProp(bakPropId);
		if(bakdefine==null){
			throw new GameException(GameException.CODE_参数错误, " bak_prop not define");
		}
		if(bak==null){
			bak=UserBakProp.create(userId, bakPropId, num);
			if(bak.getNum()>bakdefine.getMaxNum()){
				bak.setNum(bakdefine.getMaxNum());
			}
			userBakPropDao.addBakProp(bak);
			userBakPropDao.addUserBakPropCache(bak);
		}else{
			bak.setNum(bak.getNum()+num);
			if(bak.getNum()>bakdefine.getMaxNum()){
				bak.setNum(bakdefine.getMaxNum());
			}
			userBakPropDao.updateUserBakProp(userId, bak);
		}
		user.getBakProps().put(bakPropId, bak);
		BakPropLogger logger = BakPropLogger.createBakPropLogger(userId, logType, num,bakPropId,des);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
		
		//数据统计
		DataGivePropEvent gve=new DataGivePropEvent(user.getPartner(), user.getUserId(), user.getUsername(),bakPropId, Goods.TYPE_BAK_PROP, num, logType,new Date());
		TimerController.submitGameEvent(gve);
		//return currnet num 
		return bak;
	}

	@Override
	public void useBakProps(int userId, int propId, int num, int logType,String des) {
		if(num<0){
			throw new GameException(GameException.CODE_参数错误, "num must be over 0");
		}
		UserBakProp bak=userBakPropDao.getUserBakPropCache(userId, propId);
		if(bak==null||bak.getNum()<num){
			throw new GameException(GameException.CODE_道具数量不足, "prop not enough."+num);
		}else{
			bak.setNum(bak.getNum()-num);
			userBakPropDao.updateUserBakProp(userId, bak);
		}
		BakPropLogger logger = BakPropLogger.createBakPropLogger(userId, logType, num,propId,des);
		TimerController.submitGameEvent(new GameLoggerEvent(logger));
		
		//数据统计
		User user=userService.getExistUser(userId);
		DataUsePropEvent gve=new DataUsePropEvent(user.getPartner(), user.getUserId(), user.getUsername(),propId, Goods.TYPE_BAK_PROP, num, logType,new Date());
		TimerController.submitGameEvent(gve);
	}
	
	@Override
	public List<UserBakProp> initUseBakProp(int userId){
		List<UserBakProp> userBaks = userBakPropDao.querUserBakPropCache(userId);
		boolean cacheHas = true;
		if(userBaks.size()==0){
			userBaks = userBakPropDao.queryUserBakProp(userId);
			cacheHas = false;
		}
		List<UserBakProp> vos = new ArrayList<>();
		for(UserBakProp ug : userBaks){
			vos.add(ug);
			if(!cacheHas){
				userBakPropDao.addUserBakPropCache(ug);
			}
		}
		return vos;
	}
	
	@Override
	public void clearUserBakProps(int userId) {
		List<UserBakProp> props = userBakPropDao.querUserBakPropCache(userId);
		userBakPropDao.updateBakProps(userId, props);
		userBakPropDao.deleteBakPropCache(userId);
	}

	@Override
	public EqRefiningVO eqRefining(int userId, int eqGridId,int propGridId) {
		User user = userService.getExistUserCache(userId);
		Backage backage = getPackage(userId);
		UserGoods currEq=backage.getUseGoods(eqGridId);
		if(currEq==null||currEq.getGoodsType()!=UserGoods.GOODS_TYPE_EQUIPMENT){
			throw new GameException(GameException.CODE_参数错误,"grid."+eqGridId+" not euquipment");
		}
		if(currEq.getEqSkillId()!=0){
			throw new GameException(GameException.CODE_参数错误,"euquipment."+eqGridId+" has skill_id");
		}
		UserGoods propGrid=backage.getUseGoods(propGridId);
		if(propGrid==null||propGrid.getGoodsType()!=UserGoods.GOODS_TYPE_ITEM){
			throw new GameException(GameException.CODE_参数错误,"grid."+propGridId+" not prop");
		}
		if(propGrid.getNum()<=0){
			throw new GameException(GameException.CODE_道具数量不足," prop num is 0");
		}
		Equipment eq=GameCache.getEquipment(currEq.getGoodsId());
		EquipmentSkill skill=GameCache.getEquipmentSkillConfig(propGrid.getGoodsId());
		if(skill==null){
			throw new GameException(GameException.CODE_参数错误,"can't use this prop."+propGridId+" "
					+ "with eq."+currEq.getGoodsId());
		}
		if((eq.getEquipmentType()==Equipment.EQUIPMENT_TYPE_武具&&skill.getSkillType()!=EquipmentSkill.SKILL_TYPE_进攻型)||
				(eq.getEquipmentType()==Equipment.EQUIPMENT_TYPE_饰品&&skill.getSkillType()!=EquipmentSkill.SKILL_TYPE_防御型)){
			
			throw new GameException(GameException.CODE_参数错误,"can't use this prop."+propGridId+" "
					+ "with eq."+currEq.getGoodsId());
		}
		
		currEq.setEqSkillId(skill.getActiveSkillId());
		currEq.setEqSkillLevel(skill.getSkillLevel());
		propGrid.setNum(propGrid.getNum()-1);
		if(propGrid.getNum() <= 0){
			backage.resetUserGoods(propGrid);
		}
		
		userGoodsDAO.updateUserGoodsCache(currEq);
		userGoodsDAO.updateUserGoodsCache(propGrid);
	
		userService.decrementGold(user,skill.getGold(), LoggerType.TYPE_精炼装备消耗, "");
		
		List<UserGoods> updateGoods = new ArrayList<UserGoods>();
		updateGoods.add(currEq);
		updateGoods.add(propGrid);
		
		EqRefiningVO refiningVO=MessageFactory.getMessage(EqRefiningVO.class);
		List<UserGoodsVO> goodVos = new ArrayList<UserGoodsVO>();
		for(UserGoods ug : updateGoods){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(ug);
			goodVos.add(vo);		}
		refiningVO.init(goodVos, user.getGold());
		return refiningVO;
	}
}
