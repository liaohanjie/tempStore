package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.exceptions.GameException;
import com.ks.logger.LoggerFactory;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BossService;
import com.ks.logic.utils.MarqueeUtils;
import com.ks.model.boss.BossOpenSetting;
import com.ks.model.boss.BossSetting;
import com.ks.model.boss.BossrankRewardSetting;
import com.ks.model.boss.CheckBossOpenResult;
import com.ks.model.boss.UserBossRecord;
import com.ks.model.boss.WorldBossRecord;
import com.ks.model.check.BattleType;
import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentEffect;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.goods.RewardBuilder;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.LoggerType;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillEffect;
import com.ks.model.soul.Soul;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.boss.BossFightEndResultVO;
import com.ks.protocol.vo.boss.BossFightStartResultVO;
import com.ks.protocol.vo.boss.BossVO;
import com.ks.protocol.vo.boss.MoneyChangeVO;
import com.ks.protocol.vo.boss.WorldBossRecordVO;
import com.ks.protocol.vo.dungeon.FightRoundResultVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.items.GoodsVO;
import com.ks.protocol.vo.rank.RankNoticeVO;
import com.ks.protocol.vo.rank.RankerVO;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.user.UserCapVO;

/**
 * boss服务
 * 
 * @author hanjie.l
 * 
 */
public class BossServiceImpl extends BossBaseServiceImpl implements BossService {
	
	private final Logger logger = LoggerFactory.get(getClass());
	
	/**
	 * 缓存10秒内排行榜数据
	 */
	private List<UserBossRecord> topUserBossRecordCache;
	
	/**
	 * 缓存过期时间
	 */
	private long cacheOverTime;

	@Override
	public List<BossSetting> getAllBossSetting() {
		return bossSettingDAO.getAllBossSetting();
	}

	@Override
	public List<BossOpenSetting> getAllBossOpenSetting() {
		return bossSettingDAO.getAllBossOpenSetting();
	}

	@Override
	public List<BossrankRewardSetting> getAllBossRankRewardSetting() {
		return bossSettingDAO.getAllBossRankRewardSetting();
	}

	/**
	 * 获取所有boss的开启状态
	 * 
	 * @return
	 */
	public List<CheckBossOpenResult> getCheckBossOpenResult() {

		List<CheckBossOpenResult> bossOpenResults = new ArrayList<>();

		Collection<BossOpenSetting> allBossOpenSetting = GameCache.getAllBossOpenSetting();
		for (BossOpenSetting openSetting : allBossOpenSetting) {
			CheckBossOpenResult checkBossOpen = checkBossOpen(openSetting.getBossId());
			bossOpenResults.add(checkBossOpen);
		}

		return bossOpenResults;
	}

	/**
	 * 初始化boss
	 * 
	 * @param bossId
	 * @param version
	 */
	public void initWorldBoss(int bossId, String version, long endTime) {

		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(bossId);
		if (worldBossRecord == null) {
			worldBossRecord = new WorldBossRecord();
			BossSetting bossSetting = getBossSetting(bossId, 1);
			worldBossRecord.init(bossSetting);
			worldBossDAO.addWorldBossRecord(worldBossRecord);
		}

		if (!worldBossRecord.isOpen() && !worldBossRecord.getVersion().equals(version)) {
			BossSetting bossSetting = getBossSetting(worldBossRecord.getBossId(), 1);
			worldBossRecord.init(bossSetting);
			worldBossRecord.setVersion(version);
			worldBossRecord.setOpen(true);
			worldBossRecord.setEndTime(endTime);
			worldBossDAO.updateWorldBossRecord(worldBossRecord);
		}

	}

	/**
	 * 结束boss活动
	 * 
	 * @param bossId
	 */
	public void destroyWorldBoss(int bossId, long nextBeginTime) {
		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(bossId);
		if (worldBossRecord == null) {
			worldBossRecord = new WorldBossRecord();
			BossSetting bossSetting = getBossSetting(bossId, 1);
			worldBossRecord.init(bossSetting);
			worldBossRecord.setNextBeginTime(nextBeginTime);
			worldBossDAO.addWorldBossRecord(worldBossRecord);
		}

		if (worldBossRecord.isOpen()) {
			
//			int rankRewardNum = GameCache.getRankRewardNum(bossId);
//			
//			// 发奖励
//			List<UserBossRecord> topUserBossRecord = userBossDAO.getTopUserBossRecord(bossId, worldBossRecord.getVersion(), rankRewardNum);
//			for(int i=0; i<topUserBossRecord.size(); i++){
//				UserBossRecord bossRecord  = topUserBossRecord.get(i);
//				int rank = i+1;
//				BossrankRewardSetting bossRankRewardSetting = GameCache.getBossRankRewardSetting(bossId, rank);
//				
//				Affiche affiche=Affiche.create(bossRecord.getUserId(), Affiche.AFFICHE_TYP_世界boss排名奖励,"世界boss排名奖励","恭喜！您在世界boss中表现神勇，获得"+(rank)+"名", bossRankRewardSetting.getRewards(),Affiche.STATE_未读,"0");
//				afficheService.addAffiche(affiche);
//				
//			}

			// 关闭
			worldBossRecord.setOpen(false);
			worldBossRecord.setNextBeginTime(nextBeginTime);
			worldBossDAO.updateWorldBossRecord(worldBossRecord);
		}

	}

	@Override
	public List<WorldBossRecordVO> getBossInfo(int userId) {

		List<WorldBossRecordVO> bossRecordVOs = new ArrayList<>();

		Collection<BossOpenSetting> allBossOpenSetting = GameCache.getAllBossOpenSetting();
		for (BossOpenSetting openSetting : allBossOpenSetting) {
			WorldBossRecordVO bossRecordVO = MessageFactory.getMessage(WorldBossRecordVO.class);

			WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(openSetting.getBossId());
			BossSetting bossSetting = getBossSetting(worldBossRecord.getBossId(), worldBossRecord.getLevel());
			UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, worldBossRecord.getBossId());

			bossRecordVO.init(worldBossRecord, bossSetting, userBossRecord);
			bossRecordVOs.add(bossRecordVO);
		}
		return bossRecordVOs;
	}

	@Override
	public MoneyChangeVO inspired(int userId, int bossId, byte type) {

		User user = userService.getExistUserCache(userId);

		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(bossId);
		if (worldBossRecord == null || !worldBossRecord.isOpen()) {
			throw new GameException(GameException.CODE_BOSS活动未开启, "");
		}

		UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, bossId);
		if (userBossRecord == null) {
			userBossRecord = new UserBossRecord();
			userBossRecord.setUserId(userId);
			userBossRecord.setCurBossId(bossId);
			userBossDAO.addUserBossRecord(userBossRecord);
		}

		// 记录还是上次战斗的记录
		if (!userBossRecord.getVersion().equals(worldBossRecord.getVersion())) {
			userBossRecord.clearRecord(worldBossRecord.getVersion());
		}

		// 基础数据不存在
		BossOpenSetting bossOpenSetting = GameCache.getBossOpenSetting(bossId);
		if (bossOpenSetting == null) {
			throw new GameException(GameException.CODE_配置表不存在, "");
		}
		
		//鼓舞已达到上限
		if(userBossRecord.getInspiredValue() >= bossOpenSetting.getUplimit()){
			throw new GameException(GameException.CODE_鼓舞已达到上限, "");
		}

		// 1金币鼓舞
		if (type == 1) {
			userService.decrementGold(user, bossOpenSetting.getCostGold(), LoggerType.TYPE_世界boss鼓舞, "");

			int inspiredValue = userBossRecord.getInspiredValue() + bossOpenSetting.getGoldAdd();
			inspiredValue = inspiredValue > bossOpenSetting.getUplimit() ? bossOpenSetting.getUplimit() : inspiredValue;
			userBossRecord.setInspiredValue(inspiredValue);
			// 2钻石鼓舞
		} else if (type == 2) {
			userService.decrementCurrency(user, bossOpenSetting.getCostDiamond(), LoggerType.TYPE_世界boss鼓舞, "");

			int inspiredValue = userBossRecord.getInspiredValue() + bossOpenSetting.getDiamondAdd();
			inspiredValue = inspiredValue > bossOpenSetting.getUplimit() ? bossOpenSetting.getUplimit() : inspiredValue;
			userBossRecord.setInspiredValue(inspiredValue);
		} else {
			throw new GameException(GameException.CODE_参数错误, "");
		}
		
		userBossDAO.updateUserBossRecord(userBossRecord);

		MoneyChangeVO changeVO = MessageFactory.getMessage(MoneyChangeVO.class);
		changeVO.setCurrency(user.getCurrency());
		changeVO.setGold(user.getGold());
		return changeVO;
	}

	@Override
	public BossFightStartResultVO startFight(int userId, byte teamId, int bossId) {

		User user = userService.getExistUserCache(userId);

		// 世界boss信息
		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(bossId);
		if (worldBossRecord == null || !worldBossRecord.isOpen()) {
			throw new GameException(GameException.CODE_BOSS活动未开启, "");
		}
		
		//再检查一次时间，防止Game服务器出现异常，未关闭活动
		CheckBossOpenResult checkBossOpen = checkBossOpen(bossId);
		if(!checkBossOpen.isOpen()){
			throw new GameException(GameException.CODE_BOSS活动未开启, "");
		}
		
		//开放等级不足
		BossOpenSetting bossOpenSetting = GameCache.getBossOpenSetting(bossId);
		if(user.getLevel() < bossOpenSetting.getJoinLevel()){
			throw new GameException(GameException.CODE_开放等级不足, "");
		}

		// 配置表不存在
		BossSetting bossSetting = getBossSetting(bossId, worldBossRecord.getLevel());
		if (bossSetting == null) {
			throw new GameException(GameException.CODE_配置表不存在, "");
		}

		// 我的boss信息
		UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, bossId);
		if (userBossRecord == null) {
			userBossRecord = new UserBossRecord();
			userBossRecord.setUserId(userId);
			userBossRecord.setCurBossId(bossId);
			userBossDAO.addUserBossRecord(userBossRecord);
		}

		// 记录还是上次战斗的记录
		if (!userBossRecord.getVersion().equals(worldBossRecord.getVersion())) {
			userBossRecord.clearRecord(worldBossRecord.getVersion());
		}

		// 检查战魂仓库
		userSoulService.checkSoulFull(user);
		// 检查体力恢复
		userService.checkStamina(user);
		// 检查背包
		Backage backage = userGoodsService.getPackage(userId);
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

		// 增加耐久度
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

		// 修改当前队伍
		if (teamId != user.getCurrTeamId()) {
			user.setCurrTeamId(teamId);
			Map<String, String> hash = new HashMap<>();
			hash.put("currTeamId", String.valueOf(teamId));
			userDAO.updateUserCache(userId, hash);

			UserCap cap = userTeamDAO.getUserCapCache(userId);
			long userSoulId = team.getPos().get(team.getCap() - 1);
			UserSoul soul = userSoulService.getExistUserSoulCache(userId, userSoulId);
			List<UserGoods> eqs = backage.getSoulEquipments(soul);
			List<Integer> eq = new ArrayList<Integer>();
			for (UserGoods e : eqs) {
				eq.add(e.getGoodsId());
			}
			userTeamService.updateUserCap(cap, soul, eq);
		}

		// 记录开始了一场战斗
		userBossRecord.setCurBossLevel(worldBossRecord.getLevel());
		userBossRecord.setNextFightTime(System.currentTimeMillis() + 200*1000);
		userBossDAO.updateUserBossRecord(userBossRecord);

		// 给前端返回战斗需要信息
		BossFightStartResultVO bossFightStartResultVO = MessageFactory.getMessage(BossFightStartResultVO.class);
		List<FightRoundResultVO> fightVO = getFightVO(bossSetting);
		bossFightStartResultVO.setFightResultVO(fightVO);
		return bossFightStartResultVO;
	}
	
	/**
	 * 队伍总最大攻击力
	 * @param user
	 * @return
	 */
	private long getTeamMaxAttack(User user){
		
		//获取队长技能
		CapSkill attCapSkill = null;
		UserCap userCap = userService.getUserCap(user.getUserId());
		if(userCap != null){
			Soul capsoul = GameCache.getSoul(userCap.getSoulId());
			attCapSkill = GameCache.getCapSkill(capsoul.getCapSkill());
		}

		
		Backage backage = userGoodsService.getPackage(user.getUserId());
		UserTeam team = userTeamService.getExistUserTeamCache(user.getUserId(), user.getCurrTeamId());
		//获取当前队伍所有战魂
		List<UserSoul> userSouls = new ArrayList<>();
		for (byte pos = 0; pos < team.getPos().size(); pos++) {
			long userSoulId = team.getPos().get(pos);
			if (userSoulId != 0) {
				UserSoul userSoul = userSoulService.getExistUserSoulCache(user.getUserId(), userSoulId);
				userSoul.setPos(pos);
				userSouls.add(userSoul);
			}
		}
		//检查是触发队长技能
		attCapSkill = checkCapSkill(userSouls, attCapSkill);
		
		//队伍总攻击力
		int teamAttr = 0;
		for (long userSoulId : team.getPos()) {
			if (userSoulId != 0) {
				UserSoul userSoul = userSoulService.getExistUserSoulCache(user.getUserId(), userSoulId);
				Soul soul = GameCache.getSoul(userSoul.getSoulId());
				//查看该战魂佩戴的所有装备
				List<UserGoods> equipments = new ArrayList<UserGoods>();
				for (UserGoods goods : backage.getUseGoodses().values()) {
					if (goods.getUserSoulId() == userSoul.getId() && goods.getGoodsType() == UserGoods.GOODS_TYPE_EQUIPMENT) {
						equipments.add(goods);
					}
				}
				//基本攻击力
				int attr = arenaService.calAttributesValue(userSoul, 1);
				
				//装备技能或队长技能属性增益
				int addAtt = 0; 
				double addAttPercent = 0;
				for (UserGoods goods : equipments){
					//装备技能
					Equipment eq = GameCache.getEquipment(goods.getGoodsId());
					for (EquipmentEffect e : eq.getEffects()) {
						switch (e.getEffectType()) {
						case EquipmentEffect.EFFECT_TYPE_攻击:
							addAtt += e.getAddPoint();
							addAttPercent += e.getAddPercent(); 
							break;
						default:
							break;
						}
					}
				}
				
				//队长技能属性增益
				if(attCapSkill != null){
					for (CapSkillEffect effect : attCapSkill.getEffects()) {
						if (effect.getTargetEle() != 0) {
							if (soul.getSoulEle() != effect.getTargetEle()) {
								continue;
							}
						}
						switch (effect.getEffectType()) {
						case SkillEffect.EFFETC_TYPE_攻击:
							addAtt += effect.getAddPoint();
							addAttPercent += effect.getAddPercent(); 
							break;

						default:
							break;
						}
					}
				}
				
				long userSoulAttr = attr + addAtt + (long)(attr*addAttPercent);
				
				teamAttr += userSoulAttr;
			}
		}
		return teamAttr;
	}
	
	/**
	 * 检查队长技能
	 * 
	 * @param userSouls
	 * @param capSkill
	 * @return
	 */
	private CapSkill checkCapSkill(List<UserSoul> userSouls, CapSkill capSkill) {
		if (capSkill != null) {
			for (int ele : capSkill.getNeedEle()) {
				if (ele == 7 || ele == 0) {
					break;
				}
				boolean flag = false;
				for (UserSoul us : userSouls) {
					Soul soul = GameCache.getSoul(us.getSoulId());
					if (soul.getSoulEle() == ele) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					capSkill = null;
					break;
				}
			}
		}
		return capSkill;
	}

	@Override
	public BossFightEndResultVO endFight(int userId, int bossId, long hurt) {

		User user = userService.getExistUserCache(userId);
		//校验
		if(checkFightService.isPassCheck(userId, BattleType.BOSS)){
			checkFightService.clearPassCheck(userId);
		}else{
			throw new GameException(GameException.CODE_参数错误, "fight check no pass");
		}
		
		// 检查战斗是否存在
		UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, bossId);
		if (userBossRecord == null || userBossRecord.getCurBossLevel() <= 0) {
			throw new GameException(GameException.CODE_请先开始战斗, "");
		}
		
		//鼓舞加成
		double addAttPercent = ((double)userBossRecord.getInspiredValue())/100;
		
		long maxHurt = (long)(getTeamMaxAttack(user)*65*(1+addAttPercent));
		if(hurt <= 0 || hurt > maxHurt){
			throw new GameException(GameException.CODE_参数错误, "hurt:" + hurt);
		}
		
		BossFightEndResultVO bossFightEndResultVO = MessageFactory.getMessage(BossFightEndResultVO.class);

		// 世界boss信息
		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(bossId);
		if (worldBossRecord == null) {
			throw new GameException(GameException.CODE_BOSS活动未开启, "");
		}



		// 配置表
		BossSetting bossSetting = getBossSetting(bossId, worldBossRecord.getLevel());

		// 伤害奖励
		RewardBuilder builder = new RewardBuilder();
		int times = (int) Math.ceil((double) hurt / bossSetting.getHurt());
		for (int i = 1; i <= times; i++) {
			builder.addReward(bossSetting.getHurtReward());
		}
		Backage backage = userGoodsService.getPackage(userId);
		GainAwardVO hurtGoods = afficheService.gainGoods(builder.combine().build(), user, backage, 
				new int[] { LoggerType.TYPE_世界boss伤害奖励,
			LoggerType.TYPE_世界boss伤害奖励, 
			LoggerType.TYPE_世界boss伤害奖励,
			LoggerType.TYPE_世界boss伤害奖励,
			LoggerType.TYPE_世界boss伤害奖励, 
			LoggerType.TYPE_世界boss伤害奖励,
			LoggerType.TYPE_世界boss伤害奖励 }, "");
		bossFightEndResultVO.setHurtReward(hurtGoods);
		
		//是否对boss进行扣血
		if(worldBossRecord.getLevel() == userBossRecord.getCurBossLevel()){
			
			worldBossRecord.setCurBlood(worldBossRecord.getCurBlood() - hurt);
			//被击杀
			if(worldBossRecord.getCurBlood() <= 0){
				//提升等级初始化血量
				worldBossRecord.setLevel(worldBossRecord.getLevel() + 1);
				BossSetting newbossSetting = getBossSetting(bossId, worldBossRecord.getLevel());
				worldBossRecord.setCurBlood(newbossSetting.getBlood());
				worldBossRecord.setMaxBlood(newbossSetting.getBlood());

				//击杀奖励
				GainAwardVO killGoods = afficheService.gainGoods(bossSetting.getKillReward(), user, backage, 
						new int[] { LoggerType.TYPE_世界boss击杀奖励,
					LoggerType.TYPE_世界boss击杀奖励, 
					LoggerType.TYPE_世界boss击杀奖励,
					LoggerType.TYPE_世界boss击杀奖励,
					LoggerType.TYPE_世界boss击杀奖励, 
					LoggerType.TYPE_世界boss击杀奖励,
					LoggerType.TYPE_世界boss击杀奖励 }, "");
				
				bossFightEndResultVO.setKillReward(killGoods);
				
				// 添加跑马灯
				Monster monster = parseWorldMonster(newbossSetting.getMonsters());
				if (monster != null && monster.getMarquee() == 1) {
					marqueeService.add(MarqueeUtils.createBossMarquee(user.getPlayerName(), monster.getName(), monster.getEle()));
				}
			}
			worldBossDAO.updateWorldBossRecord(worldBossRecord);
		}
		
		//活动还在进行中则要累计伤害
		if(worldBossRecord.isOpen()){
			userBossRecord.setTotalHurt(userBossRecord.getTotalHurt() + hurt);
		}
		//清除战斗记录
		userBossRecord.setCurBossLevel(-1);
		userBossDAO.updateUserBossRecord(userBossRecord);

		BossVO bossVO = MessageFactory.getMessage(BossVO.class);
		bossVO.init(worldBossRecord);
		bossFightEndResultVO.setBossVO(bossVO);
		bossFightEndResultVO.setTotalHurt(userBossRecord.getTotalHurt());
		return bossFightEndResultVO;
	}

	@Override
	public MoneyChangeVO clearFightCd(int userId, int bossId) {
		User user = userService.getExistUserCache(userId);
		
		// 检查战斗是否存在
		UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, bossId);
		if (userBossRecord == null) {
			throw new GameException(GameException.CODE_不在CD中, "");
		}
		
		//没有cd
		if(System.currentTimeMillis() > userBossRecord.getNextFightTime()){
			throw new GameException(GameException.CODE_不在CD中, "");
		}
		
		//扣钻石
		userService.decrementCurrency(user, 20, LoggerType.TYPE_世界boss清除战斗cd, "");
		
		userBossRecord.setNextFightTime(0);
		userBossDAO.updateUserBossRecord(userBossRecord);
		
		MoneyChangeVO changeVO = MessageFactory.getMessage(MoneyChangeVO.class);
		changeVO.setCurrency(user.getCurrency());
		changeVO.setGold(user.getGold());
		return changeVO;
	}
	
	@Override
	public RankNoticeVO getBossRankNotice(int userId){
		
		RankNoticeVO noticeVO = MessageFactory.getMessage(RankNoticeVO.class);
		
		//TODO 这里是写死了只取第一个boss的榜，以后有需求可以改
		BossOpenSetting first = GameCache.getAllBossOpenSetting().iterator().next();
		
		// 世界boss信息
		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(first.getBossId());
		if (worldBossRecord == null) {
			throw new GameException(GameException.CODE_BOSS活动未开启, "");
		}
		
		//是否设置了owner
		boolean setOwner = false;
		//是否为缓存数据
		boolean cacheData = false;
		
		//前N名
		List<UserBossRecord> topUserBossRecord;
		if(System.currentTimeMillis() < cacheOverTime && topUserBossRecordCache != null){
			topUserBossRecord = topUserBossRecordCache;
			cacheData = true;
		}else{
			topUserBossRecord = userBossDAO.getTopUserBossRecord(worldBossRecord.getBossId(), worldBossRecord.getVersion(), 30);
			//缓存，10秒过期
			topUserBossRecordCache = topUserBossRecord;
			cacheOverTime = System.currentTimeMillis() + 30*1000;
		}
		
//		if(topUserBossRecord == null || topUserBossRecord.size() <= 0){
//			throw new GameException(GameException.CODE_BOSS排行榜暂无数据, "");
//		}
		
		List<RankerVO> rankers = new ArrayList<>();
		noticeVO.setRankers(rankers);
		
		if(topUserBossRecord != null){
			for(int i=0; i<topUserBossRecord.size(); i++){
				
				UserBossRecord userBossRecord = topUserBossRecord.get(i);
				//队长
				UserCap cap = userTeamDAO.getUserCapCache(userBossRecord.getUserId());
				if(cap == null){
					cap = userService.getUserCap(userBossRecord.getUserId());
				}
				
				RankerVO rankerVO = MessageFactory.getMessage(RankerVO.class);
				rankerVO.setRank(i+1);
				rankerVO.setValue1(userBossRecord.getTotalHurt());
				if(cap != null){
					UserCapVO capvo = MessageFactory.getMessage(UserCapVO.class);
					capvo.init(cap);
					rankerVO.setUserCapVO(capvo);
				}
				rankers.add(rankerVO);
				
				//自己在前十，并且不是缓存数据
				if(userBossRecord.getUserId() == userId && (!cacheData)){
					setOwner = true;
					noticeVO.setOwnRanker(rankerVO);
					
					//设置参与奖励 还有排名奖励(活动进行中无法领取奖励)
					if(noticeVO.getOwnRanker().getRank() > 0 && !worldBossRecord.isOpen()){
						setJoinAndRankRewardVO(noticeVO, userBossRecord, noticeVO.getOwnRanker().getRank());
					}
				}
			}
		}
		
		
		if(!setOwner){
			RankerVO rankerVO = MessageFactory.getMessage(RankerVO.class);
			
			UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, first.getBossId());
			//参加过当前boss站
			if (userBossRecord != null && userBossRecord.getVersion().equals(worldBossRecord.getVersion())) {
				
				//我的排名
				int rank = userBossDAO.getRankByUserId(userId, first.getBossId(), worldBossRecord.getVersion());
				
				rankerVO.setRank(rank);
				rankerVO.setValue1(userBossRecord.getTotalHurt());
			}
			
			//队长
			UserCap cap = userTeamDAO.getUserCapCache(userId);
			if(cap == null){
				cap = userService.getUserCap(userId);
			}
			
			if(cap != null){
				UserCapVO capvo = MessageFactory.getMessage(UserCapVO.class);
				capvo.init(cap);
				rankerVO.setUserCapVO(capvo);
			}
			noticeVO.setOwnRanker(rankerVO);
			
			//设置参与奖励 还有排名奖励(活动进行中无法领取奖励)
			if(noticeVO.getOwnRanker().getRank() > 0 && !worldBossRecord.isOpen()){
				setJoinAndRankRewardVO(noticeVO, userBossRecord, noticeVO.getOwnRanker().getRank());
			}
		}
		

		
		return noticeVO;
	}
	
	/**
	 * 设置参与奖励 与 排名奖励
	 * @param noticeVO
	 * @param userBossRecord
	 */
	private void setJoinAndRankRewardVO(RankNoticeVO noticeVO, UserBossRecord userBossRecord, int rank){
		
		if(!userBossRecord.isReceieveJoin()){
			//参与奖励
			BossOpenSetting bossOpenSetting = GameCache.getBossOpenSetting(userBossRecord.getCurBossId());
			List<GoodsVO> joinRewards = getGoodsVOList(bossOpenSetting.getJoinRewards());
			noticeVO.setJoinRewards(joinRewards);
		}
		
		if(!userBossRecord.isReceieveRank()){
			
			//排名奖励
			BossrankRewardSetting bossRankRewardSetting = GameCache.getBossRankRewardSetting(userBossRecord.getCurBossId(), rank);
			if(bossRankRewardSetting != null){
				List<GoodsVO> rankRewards = getGoodsVOList(bossRankRewardSetting.getRewards());
				noticeVO.setRankRewards(rankRewards);
			}
		}
		
		//设置奖励已领取
		if((userBossRecord.isReceieveJoin() || userBossRecord.isReceieveRank()) && noticeVO.noRewards()){
			noticeVO.setReceieveRewards(true);
		}
	}
	
	/**
	 * 奖励转换成VO
	 * @param goods
	 * @return
	 */
	private List<GoodsVO> getGoodsVOList(List<Goods> goods){
		List<GoodsVO> list = new ArrayList<GoodsVO>();
		for (Goods good : goods) {
			GoodsVO goodsVO = MessageFactory.getMessage(GoodsVO.class);
			goodsVO.init(good);
			list.add(goodsVO);
		}
		return list;
	}

	@Override
	public GainAwardVO getBossJoinRewards(int userId) {
		
		User user = userService.getExistUserCache(userId);
		
		// 检查战魂仓库
		userSoulService.checkSoulFull(user);
		// 检查体力恢复
		userService.checkStamina(user);
		// 检查背包
		Backage backage = userGoodsService.getPackage(userId);
		userGoodsService.checkBackageFull(backage, user);
		
		//TODO 这里是写死了只取第一个boss的榜，以后有需求可以改
		BossOpenSetting first = GameCache.getAllBossOpenSetting().iterator().next();

		// 世界boss信息(活动进行中无法领奖)
		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(first.getBossId());
		if (worldBossRecord == null || worldBossRecord.isOpen()) {
			throw new GameException(GameException.CODE_奖励不存在, "");
		}
		
		UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, first.getBossId());
		//参加过当前boss站
		if (userBossRecord != null && userBossRecord.getVersion().equals(worldBossRecord.getVersion())) {
			
			//已领取
			if(userBossRecord.isReceieveJoin()){
				throw new GameException(GameException.CODE_奖励已领取, "");
			}
			
			BossOpenSetting bossOpenSetting = GameCache.getBossOpenSetting(userBossRecord.getCurBossId());
			//击杀奖励
			GainAwardVO joinReward = afficheService.gainGoods(bossOpenSetting.getJoinRewards(), user, backage, 
					new int[] { LoggerType.TYPE_世界boss参与奖励,
				LoggerType.TYPE_世界boss参与奖励, 
				LoggerType.TYPE_世界boss参与奖励,
				LoggerType.TYPE_世界boss参与奖励,
				LoggerType.TYPE_世界boss参与奖励, 
				LoggerType.TYPE_世界boss参与奖励,
				LoggerType.TYPE_世界boss参与奖励 }, "");
			
			//更新
			userBossRecord.setReceieveJoin(true);
			userBossDAO.updateUserBossRecord(userBossRecord);
			
			return joinReward;
			
		}else{
			throw new GameException(GameException.CODE_奖励不存在, "");
		}
	}

	@Override
	public GainAwardVO getBossRankRewards(int userId) {
		User user = userService.getExistUserCache(userId);
		
		// 检查战魂仓库
		userSoulService.checkSoulFull(user);
		// 检查体力恢复
		userService.checkStamina(user);
		// 检查背包
		Backage backage = userGoodsService.getPackage(userId);
		userGoodsService.checkBackageFull(backage, user);
		
		//TODO 这里是写死了只取第一个boss的榜，以后有需求可以改
		BossOpenSetting first = GameCache.getAllBossOpenSetting().iterator().next();

		// 世界boss信息(活动进行中无法领奖)
		WorldBossRecord worldBossRecord = worldBossDAO.getWorldBossRecord(first.getBossId());
		if (worldBossRecord == null || worldBossRecord.isOpen()) {
			throw new GameException(GameException.CODE_奖励不存在, "");
		}
		
		UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(userId, first.getBossId());
		//参加过当前boss站
		if (userBossRecord != null && userBossRecord.getVersion().equals(worldBossRecord.getVersion())) {
			
			//已领取
			if(userBossRecord.isReceieveRank()){
				throw new GameException(GameException.CODE_奖励已领取, "");
			}
			
			//我的排名
			int rank = userBossDAO.getRankByUserId(userId, first.getBossId(), worldBossRecord.getVersion());
			
			//排名奖励
			BossrankRewardSetting bossRankRewardSetting = GameCache.getBossRankRewardSetting(userBossRecord.getCurBossId(), rank);
			if(bossRankRewardSetting == null){
				throw new GameException(GameException.CODE_奖励不存在, "");
			}
			
			//发放奖励
			GainAwardVO rankReward = afficheService.gainGoods(bossRankRewardSetting.getRewards(), user, backage, 
					new int[] { LoggerType.TYPE_世界boss参与奖励,
				LoggerType.TYPE_世界boss参与奖励, 
					LoggerType.TYPE_世界boss参与奖励,
					LoggerType.TYPE_世界boss参与奖励,
					LoggerType.TYPE_世界boss参与奖励, 
					LoggerType.TYPE_世界boss参与奖励,
					LoggerType.TYPE_世界boss参与奖励 }, "");
			
			//更新
			userBossRecord.setReceieveRank(true);
			userBossDAO.updateUserBossRecord(userBossRecord);
			
			return rankReward;
			
		}else{
			throw new GameException(GameException.CODE_奖励不存在, "");
		}
	}
	
	/**
	 * 解析时界boss怪物
	 * @param monsters
	 * @return
	 */
	private Monster parseWorldMonster(String monsters){
		Monster monster = null;
		try {
			int monsterId = Integer.parseInt(monsters.split("_")[2]);
			monster = GameCache.getMonster(monsterId);
		} catch(Exception e) {
			logger.warn("", e);
		}
		return monster;
	}
}
