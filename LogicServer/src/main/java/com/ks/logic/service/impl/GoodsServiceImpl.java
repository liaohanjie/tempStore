package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ks.event.GameEvent;
import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.event.GameLoggerEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.GoodsService;
import com.ks.logic.utils.MarqueeUtils;
import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentEffect;
import com.ks.model.equipment.EquipmentRepair;
import com.ks.model.equipment.EquipmentSkill;
import com.ks.model.filter.MonsterFilter;
import com.ks.model.filter.SoulFilter;
import com.ks.model.goods.Backage;
import com.ks.model.goods.BakProp;
import com.ks.model.goods.Goods;
import com.ks.model.goods.GoodsSynthesis;
import com.ks.model.goods.GoodsSynthesisRule;
import com.ks.model.goods.IssueGoods;
import com.ks.model.goods.Prop;
import com.ks.model.goods.PropEffect;
import com.ks.model.goods.UserBakProp;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.BakPropLogger;
import com.ks.model.logger.GoodsLogger;
import com.ks.model.logger.SoulLogger;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.timer.TimerController;
import com.ks.util.Calculate;

public class GoodsServiceImpl extends BaseService implements GoodsService {

	@Override
	public List<Prop> getAllProp() {
		return propDAO.queryAllProp();
	}

	@Override
	public List<Equipment> getEquipments() {
		return equipmentDAO.queryAllEquipment();
	}

	@Override
	public List<Stuff> getStuffs() {
		return stuffDAO.queryAllStuff();
	}

	@Override
	public List<PropEffect> getItemEffects() {
		return propDAO.queryAllPropEffect();
	}

	@Override
	public List<EquipmentEffect> getEquipmentEffects() {
		return equipmentDAO.queryAllEquipmentEffect();
	}

	@Override
	public List<GoodsSynthesis> queryGoodsSynthesis() {
		return propDAO.queryGoodsSynthesis();
	}

	@Override
	public List<GoodsSynthesisRule> queryGoodsSynthesisRule() {
		return propDAO.queryGoodsSynthesisRule();
	}

	@Override
	public List<EquipmentRepair> getEquipmentRepairs() {
		return equipmentDAO.queryAllEquipmentRepair();
	}

	@Override
	public List<BakProp> queryBakProp() {
		return propDAO.queryBakProp();
	}

	@Override
	public List<Soul> getSouls(SoulFilter soul) {
		return soulCfgDAO.getSouls(soul);
	}

	@Override
	public Integer getSoulsCount(SoulFilter soul) {
		return soulCfgDAO.getSoulsCount(soul);
	}

	@Override
	public List<Monster> getMonster(MonsterFilter filter) {
		return monsterDAO.getMonster(filter);
	}

	@Override
	public Integer getMonsterCount(MonsterFilter filter) {
		return monsterDAO.getMonsterCount(filter);
	}

	@Override
	public List<EquipmentSkill> getEquimentSkillS() {
		return equipmentDAO.queryEquipmentSkill();
	}
	
	@Override
	@Deprecated
    public UserAwardVO issueGoods(List<Goods> list, User user, int[] logTypeIds, String logDesc) {
		Backage backage = userGoodsService.getPackage(user.getUserId());
		List<Goods> goodsList = new ArrayList<Goods>();
		UserAwardVO ua = MessageFactory.getMessage(UserAwardVO.class);
		ua.setDeleteSouls(new ArrayList<UserSoulVO>());
		ua.setBakProp(new ArrayList<UserBakPropVO>());
		ua.setUserGoodses(new ArrayList<UserGoodsVO>());
		ua.setUserSouls(new ArrayList<UserSoulVO>());
		
		List<Goods> soulGoods = new ArrayList<>();
		int goodsListLog = 0;
		int soulListLog = 0;
		
		for(int i = 0; i < list.size(); i++){
			Goods goods = list.get(i);
			int logTypeId = logTypeIds[i];
			
			switch (goods.getType()) {
			case Goods.TYPE_SOUL:
				soulGoods.add(goods);
				soulListLog = logTypeId;
				break;
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				goodsList.add(goods);
				goodsListLog = logTypeId;
				break;
			case Goods.TYPE_GOLD:
				userService.incrementGold(user, goods.getNum(), logTypeId, logDesc);
				break;
			case Goods.TYPE_CURRENCY:
				userService.incrementCurrency(user, goods.getNum(), logTypeId, logDesc);
				break;
			case Goods.TYPE_EXP:
				userService.incrementExp(user, goods.getNum(), logTypeId, logDesc,null);
				break;
			case Goods.TYPE_FRIENDLY_POINT:
				userService.incrementFriendlyPoint(user.getUserId(), goods.getNum(), logTypeId, logDesc);
				break;
			case Goods.TYPE_BAK_PROP:
				userGoodsService.addBakProps(user, goods.getGoodsId(), goods.getNum(), logTypeId, logDesc);
				UserBakProp prop = UserBakProp.create(user.getUserId(), goods.getGoodsId(), goods.getNum());
				UserBakPropVO propVo = MessageFactory.getMessage(UserBakPropVO.class);
				propVo.init(prop);
				ua.getBakProp().add(propVo);
				break;
			default:
				break;
			}
		}
		
		UserStat stat=userStatDAO.queryUserStat(user.getUserId());
		if(goodsList.size()!=0){
			userGoodsService.checkBackageFull(backage, user);
			ua.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), goodsList, goodsListLog, logDesc));
			
		}
		if(!soulGoods.isEmpty()){
			userSoulService.checkSoulFull(user);
			for(Goods goods:soulGoods){
				for(int i=1; i<=goods.getNum(); i++){
					UserSoul soul = userSoulService.addUserSoul(user, goods.getGoodsId(), goods.getLevel(), soulListLog);
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

	/**
	 * 发放所有物品和道具
	 * @param goods
	 * @param user
	 * @param logType
	 * @param logDesc
	 */
	@Override
	public IssueGoods issueGoods(Backage backage, UserStat stat, Goods goods, User user, int logType, String logDesc) {
		IssueGoods issueGoods = new IssueGoods();
		
		// 发放物品和道具
		switch (goods.getType()) {
		case Goods.TYPE_SOUL:
			// 判断背包
			userGoodsService.checkBackageFull(backage, user);
			issueGoods.getUserSouls().add(_addUserSoul(user, goods, logType, logDesc));
			break;
		case Goods.TYPE_PROP:
		case Goods.TYPE_STUFF:
		case Goods.TYPE_EQUIPMENT:
			// 判断背包
			userGoodsService.checkBackageFull(backage, user);
			//issueGoods.setUserGoods(_addGoods(user, backage, goods, logType, logDesc));
			issueGoods.getUserGoods().addAll(_addGoods(user, backage, goods, logType, logDesc));
			break;
		case Goods.TYPE_GOLD:
			userService.incrementGold(user, goods.getNum(), logType, logDesc);
			break;
		case Goods.TYPE_CURRENCY:
			userService.incrementCurrency(user, goods.getNum(), logType, logDesc);
			break;
		case Goods.TYPE_EXP:
			userService.incrementExp(user, goods.getNum(), logType, logDesc,null);
			break;
		case Goods.TYPE_FRIENDLY_POINT:
			UserStat tempstat = userService.incrementFriendlyPoint(user.getUserId(), goods.getNum(), logType, logDesc);
			stat.setFriendlyPoint(tempstat.getFriendlyPoint());
			break;
		case Goods.TYPE_BAK_PROP:
			issueGoods.getUserBakProp().add(_addBakProps(user, goods, logType, logDesc));
			break;
		case Goods.TYPE_STAMINA:
			userService.incrementStamina(user, goods.getNum(), logType, logDesc);
			break;
		default:
			throw new GameException(GameException.CODE_参数错误, "goods type : " + goods.getType());
		}

		return issueGoods;
	}
	
	/**
	 * 批量发放物品
	 * @param goodsList
	 * @param user
	 * @param logType
	 * @param logDesc
	 */
	@Override
	public IssueGoods issueGoods(List<Goods> goodsList, User user, int logType, String logDesc) {
		IssueGoods issueGoods = new IssueGoods();
		
		Backage backage = userGoodsService.getPackage(user.getUserId());
		UserStat stat = userStatDAO.queryUserStat(user.getUserId());
		for (Goods goods : goodsList) {
			issueGoods.addIssueGoods(issueGoods(backage, stat, goods, user, logType, logDesc));
		}
		issueGoods.setGold(user.getGold());
		issueGoods.setCurrency(user.getCurrency());
		issueGoods.setExp(user.getExp());
		issueGoods.setLevel(user.getLevel());
		issueGoods.setFriendlyPoint(stat.getFriendlyPoint());
		issueGoods.setStamina(user.getStamina());
		return issueGoods;
	}
	
	/**
	 * 发放装备、道具、材料
	 * @param user
	 * @param backage
	 * @param goods
	 * @param logType
	 * @param logDesc
	 */
	private List<UserGoods> _addGoods(User user, Backage backage, Goods goods, int logType, String logDesc) {
		List<UserGoods> list = new ArrayList<>();
		
		if(goods.getType()!=Goods.TYPE_PROP && goods.getType()!=Goods.TYPE_EQUIPMENT && goods.getType()!=Goods.TYPE_STUFF){
			throw new GameException(GameException.CODE_参数错误, "goods type : " + goods.getType());
		}
		
		if(goods.getType()==Goods.TYPE_EQUIPMENT){
			// 装备处理
			for(int i=1; i <= goods.getNum(); i++){
				UserGoods userGoods = backage.getNotUseGoodses().poll();
				if(userGoods == null){
					userGoods = new UserGoods();
					userGoods.setUserId(user.getUserId());
					userGoods.setUpdateTime(new Date());
					userGoods.setCreateTime(new Date());
				}
				userGoods.setGoodsType(UserGoods.GOODS_TYPE_EQUIPMENT);
				userGoods.setGoodsId(goods.getGoodsId());
				userGoods.setNum(1);
				backage.addUseGoods(userGoods);
				userGoodsDAO.addUserGoods(userGoods);
				userGoodsDAO.updateUserGoodsCache(userGoods);
				list.add(userGoods);
			}
		}else{
			// 非装备处理(道具和材料)
			int userGoodsType = goods.getType()==Goods.TYPE_PROP?UserGoods.GOODS_TYPE_ITEM:UserGoods.GOODS_TYPE_STUFF;
			// 用户包裹指定类型物品
			List<UserGoods> uglist = backage.getItemGoods(userGoodsType, goods.getGoodsId());
			int num = goods.getNum();
			
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
						list.add(ug);
						if(num==0){
							break;
						}
					}
				}
			}
			
			// 没有找到指定物品类型处理
			while(num>0){
				UserGoods userGoods = backage.getNotUseGoodses().poll();
				if(userGoods == null){
					userGoods = new UserGoods();
					userGoods.setUserId(user.getUserId());
					userGoods.setUpdateTime(new Date());
					userGoods.setCreateTime(new Date());
				}
				userGoods.setGoodsType(userGoodsType);
				userGoods.setGoodsId(goods.getGoodsId());
				userGoods.setNum(num);
				
				if(userGoods.getNum()>UserGoods.MAX_NUM){
					num = goods.getNum()-UserGoods.MAX_NUM;
					goods.setNum(UserGoods.MAX_NUM);
				}else{
					num=0;
				}
				userGoodsDAO.addUserGoods(userGoods);
				userGoodsDAO.updateUserGoodsCache(userGoods);
				
				backage.addUseGoods(userGoods);
				list.add(userGoods);
			}
		}
		
		// 记录日志
		GoodsLogger logger = null;
		switch (goods.getType()) {
		case Goods.TYPE_PROP:
			logger = GoodsLogger.createPropLogger(user.getUserId(), logType, goods.getNum(), goods.getGoodsId(), logDesc);
			break;
		case Goods.TYPE_EQUIPMENT:
			logger = GoodsLogger.createEquipmentLogger(user.getUserId(), logType, goods.getNum(), goods.getGoodsId(), logDesc);
			// 添加跑马灯
			Equipment equip = GameCache.getEquipment(goods.getGoodsId());
			if (equip != null && equip.getMarquee() == 1) {
				marqueeService.add(MarqueeUtils.createEquipmentMarquee(user.getPlayerName(), equip.getName()));
			}
			break;
		case Goods.TYPE_STUFF:
			logger = GoodsLogger.createStuffLogger(user.getUserId(), logType, goods.getNum(), goods.getGoodsId(), logDesc);
			break;
		default:
			throw new GameException(GameException.CODE_参数错误, "goods type : " + goods.getType());
		}
		GameLoggerEvent event = new GameLoggerEvent(logger);
		TimerController.submitGameEvent(event);
		
		return list;
	}
	
	/**
	 * 发放战魂
	 * @param user
	 * @param goods
	 * @param logType
	 * @param logDesc
	 * @return
	 */
	private UserSoul _addUserSoul(User user, Goods goods, int logType, String logDesc) {
		Soul soul = GameCache.getSoul(goods.getGoodsId());
		if (soul == null) {
			throw new GameException(GameException.CODE_战魂不存在, "soulId:" + goods.getGoodsId());
		}
		UserSoul userSoul = new UserSoul();
		userSoul.setUserId(user.getUserId());
		userSoul.setSoulId(goods.getGoodsId());
		userSoul.setLevel(goods.getLevel());
		userSoul.setSkillLv(1);
		userSoul.setSoulType(Calculate.randomContains(UserSoul.SOUL_TYPE_COOL, UserSoul.SOUL_TYPE_STUBBORN));
		userSoul.setUpdateTime(new Date());
		userSoul.setCreateTime(new Date());
		userSoulDAO.addUserSoul(userSoul);
		userSoulDAO.addUserSoulCache(userSoul);
		userSoulService.lightUserSoulMap(user.getUserId(), goods.getGoodsId());
		
		// 添加日志
		SoulLogger logger = SoulLogger.createSoulLogger(user.getUserId(), logType, goods.getNum(), goods.getGoodsId(), userSoul.getId() + "");
		GameEvent event = new GameLoggerEvent(logger);
		TimerController.submitGameEvent(event);
		
		// 添加跑马灯
		if (soul != null && soul.getMarquee() == 1) {
			marqueeService.add(MarqueeUtils.createSoulMarquee(user.getPlayerName(), soul.getName(), soul.getSoulRare()));
		}
		return userSoul;
	}
	
	/**
	 * 发放副本道具
	 * @param user
	 * @param goods
	 * @param logType
	 * @param logDesc
	 */
	private UserBakProp _addBakProps(User user, Goods goods, int logType, String logDesc) {
		int userId = user.getUserId();
		if(user.getBakProps() == null){
			user.setBakProps(new HashMap<Integer, UserBakProp>());
		}
		UserBakProp bak=user.getBakProps().get(goods.getGoodsId());
		if(bak == null){
			bak = userBakPropDao.getUserBakPropCache(userId, goods.getGoodsId());
		}
		BakProp bakdefine=GameCache.getBakProp(goods.getGoodsId());
		if(bakdefine==null){
			throw new GameException(GameException.CODE_参数错误, " bak_prop not define");
		}
		if(bak==null){
			bak=UserBakProp.create(userId, goods.getGoodsId(), goods.getNum());
			if(bak.getNum()>bakdefine.getMaxNum()){
				bak.setNum(bakdefine.getMaxNum());
			}
			userBakPropDao.addBakProp(bak);
			userBakPropDao.addUserBakPropCache(bak);
		}else{
			bak.setNum(bak.getNum()+goods.getNum());
			if(bak.getNum()>bakdefine.getMaxNum()){
				bak.setNum(bakdefine.getMaxNum());
			}
			userBakPropDao.updateUserBakProp(userId, bak);
		}
		
		BakPropLogger bakLogger = BakPropLogger.createBakPropLogger(user.getUserId(), logType, goods.getNum(), goods.getGoodsId(), logDesc);
		TimerController.submitGameEvent(new GameLoggerEvent(bakLogger));
		return bak;
	}

	@Override
    public UserAwardVO getUserAwardVO(List<Goods> list, User user, int logType, String logDesc) {
		Backage backage = userGoodsService.getPackage(user.getUserId());
		List<Goods> goodsList = new ArrayList<Goods>();
		UserAwardVO ua = MessageFactory.getMessage(UserAwardVO.class);
		ua.setDeleteSouls(new ArrayList<UserSoulVO>());
		ua.setBakProp(new ArrayList<UserBakPropVO>());
		ua.setUserGoodses(new ArrayList<UserGoodsVO>());
		ua.setUserSouls(new ArrayList<UserSoulVO>());
		
		List<Goods> soulGoods = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++){
			Goods goods = list.get(i);
			
			switch (goods.getType()) {
			case Goods.TYPE_SOUL:
				soulGoods.add(goods);
				break;
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				goodsList.add(goods);
				break;
			case Goods.TYPE_GOLD:
				userService.incrementGold(user, goods.getNum(), logType, logDesc);
				break;
			case Goods.TYPE_CURRENCY:
				userService.incrementCurrency(user, goods.getNum(), logType, logDesc);
				break;
			case Goods.TYPE_EXP:
				userService.incrementExp(user, goods.getNum(), logType, logDesc,null);
				break;
			case Goods.TYPE_FRIENDLY_POINT:
				userService.incrementFriendlyPoint(user.getUserId(), goods.getNum(), logType, logDesc);
				break;
			case Goods.TYPE_POINT:
				userService.increPoint(user, goods.getNum(), logType, logDesc);
				break;
			case Goods.TYPE_HONOR:
				userService.increHonor(user, goods.getNum(), logType, logDesc);
				break;
			case Goods.TYPE_BAK_PROP:
				userGoodsService.addBakProps(user, goods.getGoodsId(), goods.getNum(), logType, logDesc);
				UserBakProp prop = UserBakProp.create(user.getUserId(), goods.getGoodsId(), goods.getNum());
				UserBakPropVO propVo = MessageFactory.getMessage(UserBakPropVO.class);
				propVo.init(prop);
				ua.getBakProp().add(propVo);
				break;
			default:
				break;
			}
		}
		
		UserStat stat=userStatDAO.queryUserStat(user.getUserId());
		if(goodsList.size()!=0){
			userGoodsService.checkBackageFull(backage, user);
			ua.setUserGoodses(userGoodsService.addGoods(user, userGoodsService.getPackage(user.getUserId()), goodsList, logType, logDesc));
			
		}
		if(!soulGoods.isEmpty()){
			userSoulService.checkSoulFull(user);
			for(Goods goods:soulGoods){
				for(int i=1; i<=goods.getNum(); i++){
					UserSoul soul = userSoulService.addUserSoul(user, goods.getGoodsId(), goods.getLevel(), logType);
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
		ua.setHonor(user.getHonor());
		ua.setPoint(user.getPoint());
		return ua;
    }
}
