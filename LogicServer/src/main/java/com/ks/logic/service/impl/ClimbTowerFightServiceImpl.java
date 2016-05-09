package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.ClimbTowerFightService;
import com.ks.logic.utils.PropertyUtils;
import com.ks.model.Award;
import com.ks.model.check.BattleType;
import com.ks.model.climb.ClimbTower;
import com.ks.model.climb.ClimbTowerAwardRecord;
import com.ks.model.climb.ClimbTowerRank;
import com.ks.model.climb.ClimbTowerStar;
import com.ks.model.climb.ClimbTowerUser;
import com.ks.model.dungeon.Chapter;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.CurrFight;
import com.ks.model.equipment.Equipment;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.goods.IssueGoods;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.LoggerType;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.dungeon.FightResultVO;
import com.ks.protocol.vo.dungeon.FightRoundResultVO;
import com.ks.protocol.vo.dungeon.MonsterAwardVO;
import com.ks.protocol.vo.goods.UserBakPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.items.GoodsVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.tower.ClimbTowerAwardRecordVO;
import com.ks.protocol.vo.tower.ClimbTowerUserVO;
import com.ks.protocol.vo.user.UserSoulVO;

/**
 * 爬塔试炼战斗
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ClimbTowerFightServiceImpl extends BaseService implements ClimbTowerFightService {

	/**每次战斗购买价格*/
	final static int GAME_COIN = PropertyUtils.SYS_CONFIG.getInt("climb.buy.fight.count.price", 5);
	/**每层免费战斗次数*/
	final static int MAX_FREE_COUNT = PropertyUtils.SYS_CONFIG.getInt("climb.fight.max.free.count", 2);
	/**每层战斗最大购买次数*/
	final static int MAX_BUY_COUNT = PropertyUtils.SYS_CONFIG.getInt("climb.fight.max.buy.count", 5);
	
	/**试炼石编号*/
	final static int GOODS_ID_SLS = 3050004;
	
	@Override
    public ClimbTowerUserVO findByTowerFloor(int userId, int towerFloor) {
		// 判断用户是否在线
		userService.getExistUserCache(userId);
		
		// 查询爬塔指定层数具体信息，没有找到返回初始化默认信息
		ClimbTowerUser ctuEntity = climbTowerUserService.findByUserIdTowerFloor(userId, towerFloor);
		ClimbTowerUserVO ctuVO = MessageFactory.getMessage(ClimbTowerUserVO.class);
		if (ctuEntity != null){
			short remainingCount = (short) (MAX_FREE_COUNT + ctuEntity.getBuyFightCount() - ctuEntity.getFightCount());
			ctuVO.init(ctuEntity, remainingCount);
		}
	    return ctuVO;
    }

	@Override
    public List<ClimbTowerUserVO> queryClimbTower(int userId) {
		// 判断用户是否在线
		userService.getExistUserCache(userId);
		
		// 刷新爬塔数据
		climbTowerUserService.refreshClimbTower(userId);
		
		List<ClimbTowerUserVO> list = new ArrayList<>();
		List<ClimbTowerUser> ctuList = climbTowerUserService.queryByUserId(userId);
		if (ctuList != null && !ctuList.isEmpty()) {
			for (ClimbTowerUser ctu : ctuList) {
				ClimbTowerUserVO vo = MessageFactory.getMessage(ClimbTowerUserVO.class);
				short remainingCount = (short) (MAX_FREE_COUNT + ctu.getBuyFightCount() - ctu.getFightCount());
				vo.init(ctu, remainingCount);
				list.add(vo);
			}
		}
	    return list;
    }
	
	@Override
    public FightResultVO startFight(int userId, int towerFloor, byte teamId) {
		int chapterId = getClimbChapterId(towerFloor);
		
		// 判断用户是否在线
		User user = userService.getExistUserCache(userId);
		
		CurrFight cf = new CurrFight();
		cf.setChapterId(chapterId);
		cf.setJoinMonsters("");
		cf.setUserId(userId);
		
		Chapter chapter = GameCache.getChapter(chapterId);
		ClimbTower currClimbTower = GameCache.getClimbTowerMap().get(towerFloor);
		List<ChapterRound> rounds = GameCache.getChapterRounds(cf.getChapterId());
		
		if (currClimbTower == null || chapter == null) {
			throw new GameException(GameException.CODE_参数错误, "no this chapterId." + chapterId);
		}
		
		// 判断爬塔次数
		ClimbTowerUser ctuEntity = climbTowerUserService.findByUserIdTowerFloor(userId, towerFloor);
		if (ctuEntity != null && ctuEntity.getFightCount() >= MAX_FREE_COUNT + ctuEntity.getBuyFightCount()) {
			throw new GameException(GameException.CODE_爬塔挑战次数不足, " ClimbTowerUser no found. userId=" + userId + " towerFloor=" + towerFloor + " fightCount=" + ctuEntity.getFightCount());
		}
		
		// 判断试炼石是否满足，扣除试炼石相应试炼石
		Backage backage = userGoodsService.getPackage(userId);
		userGoodsService.deleteGoods(backage, Goods.TYPE_PROP, GOODS_ID_SLS, currClimbTower.getCostRock(), LoggerType.TYPE_爬塔试炼, "");
		
		// 战魂
		userGoodsService.checkBackageFull(backage, user);
		List<UserSoulInfoVO> soulInfos = new ArrayList<UserSoulInfoVO>();
		UserTeam team = userTeamService.getExistUserTeamCache(userId, teamId);
		for (long userSoulId : team.getPos()) {
			if (userSoulId != 0) {
				UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
				UserSoulInfoVO vo = userSoulService.gainUserSoulInfo(soul, backage.getUseGoodses().values());
				soulInfos.add(vo);
			}
		}
		// cost检查
		userTeamService.checkUserTeam(user, soulInfos);
		//增加耐久度
		for (UserSoulInfoVO vo : soulInfos) {
			for (UserGoodsVO eq : vo.getEquipments()) {
				UserGoods goods = backage.getUseGoods(eq.getGridId());
				Equipment q = GameCache.getEquipment(goods.getGoodsId());
				if (q.getMaxDurable() > goods.getDurable()) {
					goods.setDurable(goods.getDurable() + 1);
					userGoodsDAO.updateUserGoodsCache(goods);
				}
			}
		}
		
		// 判断是否可以试炼当前层
		if (towerFloor != 1) {
			//ClimbTowerUser currCtuEntity = climbTowerUserService.findByUserIdTowerFloor(userId, towerFloor);
			ClimbTowerUser preCtuEntity = climbTowerUserService.findByUserIdTowerFloor(userId, towerFloor - 1);
			
			// 前一层必须完成
			if (preCtuEntity == null) {
				throw new GameException(GameException.CODE_参数错误, " ClimbTower can't fight current floor. userId=" + userId + " towerFloor=" + towerFloor);
			}
		}
		
		FightResultVO frResult = MessageFactory.getMessage(FightResultVO.class);
		frResult.setRounds(getFightResult(currClimbTower, cf, rounds ,user));
		frResult.setJoinMonsters(cf.getJoinMonsters());
		frResult.setExp(cf.getExp());
		frResult.setJoinGoods(new ArrayList<GoodsVO>());
		frResult.setStamina(user.getStamina());
		frResult.setLastRegainStaminaTime(user.getLastRegainStaminaTime().getTime());
		userChapterDAO.updateCurrFightCache(cf);
	    return frResult;
    }

	@Override
    public FightEndResultVO endFigth(int userId, short starNum, boolean pass) {
		User user = userService.getExistUserCache(userId);
		
		//校验
		if(checkFightService.isPassCheck(userId, BattleType.CLIMTOWER)){
			checkFightService.clearPassCheck(userId);
		}else{
			throw new GameException(GameException.CODE_参数错误, "fight check no pass");
		}
		
		if (starNum <= 0 || starNum > 3) {
			throw new GameException(GameException.CODE_参数错误, "climb star number is wrong. starNum=" + starNum);
		}
		
		FightEndResultVO ferVO = MessageFactory.getMessage(FightEndResultVO.class);
		ferVO.setUserGoodses(new ArrayList<UserGoodsVO>());
		ferVO.setUserSouls(new ArrayList<UserSoulVO>());
		ferVO.setBakProp(new ArrayList<UserBakPropVO>());
		
		// 通过保存或更新爬塔记录信息
		if (pass) {
			CurrFight cf = userChapterDAO.getCurrFight(userId);
			if (cf == null) {
				throw new GameException(GameException.CODE_参数错误, "CurrFight is null");
			}
			
			// 保存玩家爬塔信息
			int towerFloor = cf.getChapterId() - 7060000;
			ClimbTowerUser ctuEntity = climbTowerUserDAO.findClimbTowerUser(userId, towerFloor);
			if (ctuEntity == null) {
				ctuEntity = new ClimbTowerUser();
				ctuEntity.setBuyFightCount((short) 0);
				ctuEntity.setCreateTime(new Date());
				ctuEntity.setUpdateTime(new Date());
				ctuEntity.setFightCount((short) 1);
				ctuEntity.setStartCount(starNum);
				ctuEntity.setUserId(userId);
				ctuEntity.setTowerFloor(towerFloor);
				climbTowerUserDAO.add(ctuEntity);
			} else {
				//ctuEntity.setFightCount((short) (ctuEntity.getFightCount() + 1));
				if (starNum > ctuEntity.getStartCount()) {
					ctuEntity.setStartCount(starNum);
				}
				ctuEntity.setFightCount((short) (ctuEntity.getFightCount() + 1));
				climbTowerUserDAO.update(ctuEntity);
			}
			
			// 更新爬塔排行榜信息
			ClimbTowerRank ctrEntity = climbTowerRankDAO.getClimbTowerRank(userId);
			if (ctrEntity == null) {
				ctrEntity = new ClimbTowerRank();
				ctrEntity.setUserId(userId);
				ctrEntity.setStarCount(starNum);
				ctrEntity.setTowerFloor(towerFloor);
				ctrEntity.setUpdateTime(new Date());
				ctrEntity.setCreateTime(new Date());
				climbTowerRankDAO.add(ctrEntity);
			} else {
				int userStarCount = climbTowerUserDAO.getUserStarCount(userId);
				// 星星数量变多，更新星星数量
				if (userStarCount > ctrEntity.getStarCount()) {
					ctrEntity.setStarCount(userStarCount);
					ctrEntity.setUpdateTime(new Date());
					// 最高层变化更新
					if (towerFloor > ctrEntity.getTowerFloor()) {
						ctrEntity.setTowerFloor(towerFloor);
					}
					climbTowerRankDAO.update(ctrEntity);
				}
			}
			
			// 战斗结束发放奖励物品
			IssueGoods issueGoods = goodsService.issueGoods(cf.getAwards(), user, LoggerType.TYPE_爬塔试炼, "");
			ferVO.setBakProp(IssueGoods.createUserBakPropVOList(issueGoods.getUserBakProp()));
			ferVO.setUserGoodses(IssueGoods.createUserGoodsVOList(issueGoods.getUserGoods()));
			ferVO.setUserSouls(IssueGoods.createUserSoulVOList(issueGoods.getUserSouls()));
			ferVO.setExp(issueGoods.getExp());
			ferVO.setFriendlyPoint(issueGoods.getFriendlyPoint());
			ferVO.setStamina(issueGoods.getStamina());
			ferVO.setCurrency(issueGoods.getCurrency());
			ferVO.setGold(issueGoods.getGold());
			userChapterDAO.delCurrFightCache(userId);
		}
	    return ferVO;
    }

	@Override
    public int buyClimbTowerExtraCount(int userId, int towerFloor, int count) {
		User user = userService.getExistUserCache(userId);
		
		ClimbTowerUser ctuEntity = climbTowerUserService.findByUserIdTowerFloor(userId, towerFloor);
		if (ctuEntity == null) {
			throw new GameException(GameException.CODE_参数错误, " ClimbTowerUser no found. userId=, " + userId + " towerFloor=" + towerFloor);
		}
		 
		if (ctuEntity.getBuyFightCount() >= MAX_BUY_COUNT) {
			throw new GameException(GameException.CODE_超出购买次数, "beyond the purchase number, maxCount=" + MAX_BUY_COUNT);
		}
		
		// 扣去魂钻，更新第几层的战斗次数
		userService.decrementCurrency(user, GAME_COIN*count, LoggerType.TYPE_购买爬塔次数, "climb number");
		ctuEntity.setBuyFightCount((short) (ctuEntity.getBuyFightCount() + 1));
		climbTowerUserDAO.update(ctuEntity);
	    return user.getCurrency();
    }

	@Override
    public int buyClimbTowerExtraCount(int userId, int towerFloor) {
	    return buyClimbTowerExtraCount(userId, towerFloor, 1);
    }

	@Override
    public FightEndResultVO sweep(int userId, int towerFloor, byte teamId) {
		User user = userService.getExistUserCache(userId);
		ClimbTowerUser ctuEntity = climbTowerUserDAO.findClimbTowerUser(userId, towerFloor);
		if (ctuEntity == null) {
			throw new GameException(GameException.CODE_参数错误, " ClimbTowerUser no found. userId=" + userId + " towerFloor=" + towerFloor);
		}
		
		if (ctuEntity.getStartCount() < 3) {
			throw new GameException(GameException.CODE_参数错误, " ClimbTowerUser star of number must be more than 3, " + userId + " towerFloor=" + towerFloor);
		}
		
		if (ctuEntity.getFightCount() >= ctuEntity.getBuyFightCount() + MAX_FREE_COUNT) {
			throw new GameException(GameException.CODE_爬塔挑战次数不足, " exceed max fight number, " + userId + " towerFloor=" + towerFloor);
		}
		
		ClimbTower ct = GameCache.getClimbTowerMap().get(towerFloor);
		
		// 判断试炼石是否满足，扣除试炼石相应试炼石
		Backage backage = userGoodsService.getPackage(userId);
		userGoodsService.deleteGoods(backage, Goods.TYPE_PROP, GOODS_ID_SLS, ct.getCostRock(), LoggerType.TYPE_爬塔试炼, "");
		
		// 判断背包是否已满
		userGoodsService.checkBackageFull(backage, user);
		
		// 更新第几层的战斗次数
		ctuEntity.setFightCount((short) (ctuEntity.getFightCount() + 1));
		climbTowerUserDAO.update(ctuEntity);
		
		// 获取战斗获得随机配置物品, 发放物品
		List<Goods> goodsList = initClimbTowerAward(userId, ct);
		
//		goodsList.add(Goods.create(1010003, 1, 1, 1));
//		goodsList.add(Goods.create(3030008, 2, 1, 0));
//		goodsList.add(Goods.create(3030009, 2, 1, 0));
		
		FightEndResultVO ferVO = MessageFactory.getMessage(FightEndResultVO.class);
		ferVO.setUserGoodses(new ArrayList<UserGoodsVO>());
		ferVO.setUserSouls(new ArrayList<UserSoulVO>());
		ferVO.setBakProp(new ArrayList<UserBakPropVO>());
		
		// 战斗结束发放奖励物品
		IssueGoods issueGoods = goodsService.issueGoods(goodsList, user, LoggerType.TYPE_爬塔试炼, "");
		ferVO.setBakProp(IssueGoods.createUserBakPropVOList(issueGoods.getUserBakProp()));
		ferVO.setUserGoodses(IssueGoods.createUserGoodsVOList(issueGoods.getUserGoods()));
		ferVO.setUserSouls(IssueGoods.createUserSoulVOList(issueGoods.getUserSouls()));
//		ferVO.setExp(user.getExp());
//		ferVO.setFriendlyPoint(userStatDAO.queryUserStat(userId).getFriendlyPoint());
//		ferVO.setStamina(user.getStamina());
//		ferVO.setCurrency(user.getCurrency());
//		ferVO.setGold(user.getGold());
		
		ferVO.setExp(issueGoods.getExp());
		ferVO.setFriendlyPoint(issueGoods.getFriendlyPoint());
		ferVO.setStamina(issueGoods.getStamina());
		ferVO.setCurrency(issueGoods.getCurrency());
		ferVO.setGold(issueGoods.getGold());
	    return ferVO;
    }
	

	@Override
    public UserAwardVO getStarAward(int userId, int starRegion, int starNum) {
		User user = userService.getExistUserCache(userId);
		
		ClimbTowerStar cts = GameCache.getClimbTowerStarMap(starRegion, starNum);
		if (cts == null) {
			throw new GameException(GameException.CODE_参数错误, " ClimbTowerStar no found. userId=, " + userId + " startNum=" + starNum);
		}
		
		// 判断领取过否
		ClimbTowerAwardRecord ctarEntity = climbTowerAwardRecordService.findByUserId(userId, starRegion, starNum);
		if (ctarEntity != null) {
			throw new GameException(GameException.CODE_不能重复领取, " can't require star award again. userId=" + userId + " starRegion=" + starRegion + " startNum=" + starNum);
		}
		
		int regionStarCount = climbTowerUserDAO.getUserRegionStarCount(userId, starRegion);
		if ( regionStarCount < cts.getStartNum()){
			throw new GameException(GameException.CODE_参数错误, "star not enough. userId=" + userId + " starRegion=" + starRegion + " startNum=" + starNum + " regionStarCount=" + regionStarCount);
		}
		
		// 记录领取记录
		ctarEntity = new ClimbTowerAwardRecord();
		ctarEntity.setUserId(userId);
		ctarEntity.setStarRegion((short) starRegion);
		ctarEntity.setStarNum(starNum);
		ctarEntity.setCreateTime(new Date());
		climbTowerAwardRecordService.add(ctarEntity);
		
		// 发放奖励物品
		List<Award> ctaList = GameCache.getClimbTowerAwardMap().get(cts.getAwardId());
		List<Goods> goodsList = new ArrayList<>();
		for (Award cta : ctaList) {
			if (cta.getRate() > Math.random()) {
				goodsList.add(Goods.create(cta.getGoodsId(), cta.getGoodsType(), cta.getNum(), cta.getNum()));
			}
		}
		return goodsService.getUserAwardVO(goodsList, user, LoggerType.TYPE_爬塔试炼集星, "climb star award");
    }
	

	@Override
    public List<ClimbTowerAwardRecordVO> queryClimbTowerUserRegionStar(int userId) {
		List<ClimbTowerAwardRecordVO> listVO = new ArrayList<>();
		List<ClimbTowerAwardRecord> recrodList = climbTowerAwardRecordService.queryClimbTowerRegionStarRecord(userId);
		if (recrodList != null) {
			for (ClimbTowerAwardRecord ctar : recrodList) {
				ClimbTowerAwardRecordVO vo = MessageFactory.getMessage(ClimbTowerAwardRecordVO.class);
				vo.init(ctar);
				listVO.add(vo);
			}
		}
		return listVO;
    }
	
	
	/**
	 * 得到战斗结果
	 * @param climbTower
	 * @param cf
	 * @param rounds
	 * @param user
	 * @return
	 */
	private List<FightRoundResultVO> getFightResult(ClimbTower climbTower, CurrFight cf, List<ChapterRound> rounds, User user) {
		List<FightRoundResultVO> results = new ArrayList<>();
		
		for (int i = 0;i < rounds.size(); i++) {
			
			// 每个回合一条记录
			FightRoundResultVO resultVO = MessageFactory.getMessage(FightRoundResultVO.class);

			List<MonsterAwardVO> maVOList = new ArrayList<MonsterAwardVO>();
			String[] monsterIds = rounds.get(i).getMonsters().split(ChapterRound.MONSTERS_SPLIT);
			for (int pos = 0; pos < 6; pos++) {
				int monsterId = Integer.valueOf(monsterIds[pos]);
				if (monsterId == 0) {
					continue;
				}
				
				//List<Drop> drops = GameCache.getDrops(monsterId);
				//initMonsterAward(cf, maVOList, monsterId, pos, round.getRound(), drops, user);
				cf.getMonsters().add(monsterId);
				MonsterAwardVO maVO = MessageFactory.getMessage(MonsterAwardVO.class);
				maVO.setMonsterId(monsterId);
				maVO.setPos(pos);
				maVO.setRound(i);
				maVOList.add(maVO);
			}
			
			//cf.setJoinGoods(initClimbTowerAward(user.getUserId(), climbTower));
			cf.setAwards(initClimbTowerAward(user.getUserId(), climbTower));
			resultVO.setMonsterAwards(maVOList);
			results.add(resultVO);
		}
		// 爬塔无经验
		cf.setExp(0);
		return results;
	}
	
	/**
	 * 获取会发放的奖励
	 * @param ct
	 * @return
	 */
	private List<Goods> initClimbTowerAward(int userId, ClimbTower ct) {
		List<Goods> goodsList = new ArrayList<>();
		
		// 固定奖励
		List<Award> ctaList = GameCache.getClimbTowerAwardMap().get(ct.getFixAwardId());
		for (Award cta : ctaList) {
			if (cta.getRate() > Math.random()) {
				goodsList.add(Goods.create(cta.getGoodsId(), cta.getGoodsType(), cta.getNum(), cta.getLevel()));
			}
		}
		
		// 判断是否第一次通关的额外奖励
		ClimbTowerUser ctuEntity = climbTowerUserDAO.findClimbTowerUser(userId, ct.getTowerFloor());
		if (ctuEntity == null) {
			List<Award> list = GameCache.getClimbTowerAwardMap().get(ct.getFirstAwardId());
			for (Award cta : list) {
				if (cta.getRate() > Math.random()) {
					goodsList.add(Goods.create(cta.getGoodsId(), cta.getGoodsType(), cta.getNum(), cta.getLevel()));
				}
			}
		}
		return goodsList;
	}
	
	/**
	 * 获得章节编号
	 * @param towerFloor
	 * @return
	 */
	private int getClimbChapterId(int towerFloor) {
		return towerFloor + 7060000;
	}
}
