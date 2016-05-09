package com.ks.logic.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ks.exceptions.GameException;
import com.ks.logic.service.ActivityService;
import com.ks.logic.service.AllianceService;
import com.ks.logic.service.AthleticsInfoService;
import com.ks.logic.service.AwardService;
import com.ks.logic.service.BossService;
import com.ks.logic.service.BudingService;
import com.ks.logic.service.CallRuleService;
import com.ks.logic.service.CallRuleSoulService;
import com.ks.logic.service.ChapterAwardService;
import com.ks.logic.service.ChapterChestService;
import com.ks.logic.service.ChapterService;
import com.ks.logic.service.CheckFightService;
import com.ks.logic.service.ClimbTowerService;
import com.ks.logic.service.ClimbTowerStarService;
import com.ks.logic.service.CoinHandRuleService;
import com.ks.logic.service.CoinHandService;
import com.ks.logic.service.FriendService;
import com.ks.logic.service.GoodsService;
import com.ks.logic.service.MallService;
import com.ks.logic.service.MysteryShopItemService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.SkillService;
import com.ks.logic.service.SoulExploretionService;
import com.ks.logic.service.SoulService;
import com.ks.logic.service.StaminaService;
import com.ks.logic.service.SwapArenaSettingService;
import com.ks.logic.service.TotemRuleService;
import com.ks.logic.service.TotemSoulService;
import com.ks.logic.service.UserAchieveService;
import com.ks.logic.service.UserMissionService;
import com.ks.logic.service.UserService;
import com.ks.logic.service.UserTeamService;
import com.ks.model.Award;
import com.ks.model.achieve.Achieve;
import com.ks.model.achieve.AchieveAward;
import com.ks.model.alliance.AllianceSetting;
import com.ks.model.alliance.AllianceShopItem;
import com.ks.model.boss.BossOpenSetting;
import com.ks.model.boss.BossSetting;
import com.ks.model.boss.BossrankRewardSetting;
import com.ks.model.buding.Buding;
import com.ks.model.buding.BudingDrop;
import com.ks.model.buding.BudingRule;
import com.ks.model.check.CheckConfig;
import com.ks.model.climb.ClimbTower;
import com.ks.model.climb.ClimbTowerStar;
import com.ks.model.coin.CoinHand;
import com.ks.model.coin.CoinHandRule;
import com.ks.model.dungeon.Box;
import com.ks.model.dungeon.Chapter;
import com.ks.model.dungeon.ChapterAward;
import com.ks.model.dungeon.ChapterChest;
import com.ks.model.dungeon.ChapterJoin;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.Drop;
import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentEffect;
import com.ks.model.equipment.EquipmentRepair;
import com.ks.model.equipment.EquipmentSkill;
import com.ks.model.explora.ExplorationAward;
import com.ks.model.explora.ExplorationAwardExp;
import com.ks.model.friend.FriendGifiRule;
import com.ks.model.goods.BakProp;
import com.ks.model.goods.GoodsSynthesis;
import com.ks.model.goods.GoodsSynthesisRule;
import com.ks.model.goods.Prop;
import com.ks.model.goods.PropEffect;
import com.ks.model.mission.Mission;
import com.ks.model.mission.MissionAward;
import com.ks.model.mission.MissionCondition;
import com.ks.model.pay.Mall;
import com.ks.model.pvp.AthleticsInfoAward;
import com.ks.model.robot.TeamTemplate;
import com.ks.model.shop.MysteryShopItem;
import com.ks.model.skill.ActiveSkill;
import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SocialSkill;
import com.ks.model.skill.SocialSkillEffect;
import com.ks.model.skill.SocialSkillRule;
import com.ks.model.soul.CallRule;
import com.ks.model.soul.CallRuleSoul;
import com.ks.model.soul.Soul;
import com.ks.model.soul.SoulEvolution;
import com.ks.model.stamina.Stamina;
import com.ks.model.stuff.Stuff;
import com.ks.model.swaparena.SwapArenaBuySetting;
import com.ks.model.swaparena.SwapArenaRewardSetting;
import com.ks.model.totem.TotemRule;
import com.ks.model.totem.TotemSoul;
import com.ks.model.user.GrowthFundRule;
import com.ks.model.user.User;
import com.ks.model.user.UserRule;
import com.ks.model.user.UserSoul;
import com.ks.model.vip.VipPrivilege;
import com.ks.model.vip.VipWeekAward;
import com.ks.util.collect.MultiListMap;

/**
 * 游戏缓存
 * @author ks
 */
public class GameCache {
	/**战魂 key=soulId value=soul*/
	private static Map<Integer,Soul> SOUL_MAP;
	
	/**章节Map key=chapte_idr*/
	private static Map<Integer,Chapter> CHAPTER_MAP;
	
	/**章节Map key=chapte_idr*/
	private static Map<Integer,List<ChapterRound>> CHAPTER_ROUND_MAP;
	
	/***结束关 key=mapId value=dungeonId*/
	private static Map<Integer,Integer> END_DUNGEONS;
	/**地下城怪物 key=monster_id,value=[Monster]*/
	private static Map<Integer,Monster> MONSTERS;
	/**用户规则*/
	private static Map<Integer,UserRule> USER_RULES;
/*	*//**主动技能*//*
	private static Map<Integer,Skill> SKILLS;
	*//**队长技能*//*
	private static Map<Integer,CapSkill> CAP_SKILLS;*/
	/**进化列表*/
	private static Map<Integer,SoulEvolution> EVOLUTIONS;
	/**变异列表*/
	private static Map<Integer,List<SoulEvolution>> VARIATIONS;
	/**道具列表*/
	private static Map<Integer,Prop> props;
	/**材料列表*/
	private static Map<Integer,Stuff> STUFFS;
	/**装备列表*/
	private static Map<Integer,Equipment> EQUIPMENTS;
	/**掉落*/
	private static Map<Integer,List<Drop>> DROPS;
	/**建筑列表*/
	private static Map<Integer,Buding> BUDINGS;
	/**建筑规则 key=budingId value=[key=level,value=budingRule]*/
	private static Map<Integer, Map<Integer, BudingRule>> BUDING_RULES;
	/**建筑掉落*/
	private static Map<Integer, Map<Integer,List<BudingDrop>>> BUDING_DROPS;
	/**查询好友赠品规则*/
	private static Map<Integer,FriendGifiRule> FRIEND_GIFI_RULE;
	/**物品合成*/
	public static Map<Integer,GoodsSynthesis> GOODS_SYNTHESIS;
	/**箱子*/
	public static Map<Integer,Box> BOX_MAP;
	/**成就列表**/
	public static MultiListMap<Integer, Achieve> ACHIEVE_MAP;	
	/**成就奖励*/
	public static Map<Integer,List<AchieveAward>> ACHIEVE_AWARDS;	
	/**主动技能*/
	private static Map<Integer,ActiveSkill> ACTIVE_SKILLS;	
	/**队长技能*/
	private static Map<Integer,CapSkill> CAP_SKILLS;	
	/**合作技能*/
	private static Map<Integer,SocialSkill> SOICAL_SKILLS;
	/**副本道具*/
	private static Map<Integer,BakProp> BAK_PROP_MAP;
	/**合作技能*/
	private static Map<Integer,List<SocialSkillRule>> SOUL_SOCIAL_RULE;
	/**升级称号奖励*/
	private static Map<Integer,AthleticsInfoAward> ATHLETICS_INFO_AWARD;
	/**vip特权*/
	private static Map<Integer,VipPrivilege> VIP_PRIVILEGE_MAP;
	
	private static List<VipPrivilege> VIP_PRIVILEGE_LIST;
	/**关卡乱入信息*/
	private static Map<Integer,ChapterJoin> JOIN_CHPATER_MAP;
	/**成长基金规则*/
	private static Map<Integer,GrowthFundRule> GROWTH_FUND_RULE_MAP;
	/**探索奖励*/
	private static Map<Integer, Map<Integer,List<ExplorationAward>>> EXPLORATION_AWARD_MAP;
	/**vip周奖励*/
	private static Map<Integer,List<VipWeekAward>> VIP_WEEK_AWARD_MAP;
	/**装备技能配置表*/
	private static Map<Integer,EquipmentSkill> EQUIPMENT_SKILL_CONFIG;
	
	/**探索经验奖励*/
	private static Map<Integer,Map<Integer,ExplorationAwardExp>> EXPLORATION_AWARD_EXP_MAP;
	/**任务基础条件*/
	private static Map<Integer,Mission> MISSION_MAP;
	/**任务条件*/
	private static Map<Integer,List<MissionCondition>> MISSION_CONDITION_MAP;
	
	private static Map<Integer,List<MissionAward>> MISSION_AWARD_MAP;
	/**任务条件映射 type,assId,missionIds*/
	private static Map<Integer,Map<Integer,Set<Integer>>> MISSION_CONDITIONS;
	
	/**充值送魂钻活动*/
	private static Map<Integer, Mall> MALL_MAP;
	
	/**神木图腾合成对应规则*/
	private static Map<Integer, TotemRule> TOTEM_RULE_MAP;
	
	/**神木图腾合成对应规则产出战魂*/
	private static Map<Integer, List<TotemSoul>> TOTEM_SOUL_MAP;
	
	/**战魂召唤对应规则*/
	private static Map<Integer, List<CallRule>> CALL_RULE_MAP;
	
	/**战魂召唤对应规则产出战魂*/
	private static Map<Integer, List<CallRuleSoul>> CALL_RULE_SOUL_MAP;
	
	/**点金手*/
	private static Map<Integer, CoinHand> COIN_HAND_MAP;
	
	/**点金手权重*/
	private static Map<Integer, List<CoinHandRule>> COIN_HAND_RULE_MAP;
	
	/**购买体力配置*/
	private static Map<Integer, Stamina> STAMINA_MAP;
	
	/**爬塔试炼配置*/
	private static Map<Integer, ClimbTower> CLIMBTOWER_MAP;
	
	/**爬塔试炼星级配置*/
	private static Map<String, ClimbTowerStar> CLIMBTOWERSTAR_MAP;
	
	/**奖励*/
	private static Map<Integer, List<Award>> AWARD_MAP;
	
	/**boss设置*/
	private static Map<Integer, Map<Integer, BossSetting>> bossSettingMap;
	
	/**boss最大等级配置*/
	private static Map<Integer, BossSetting> bossMaxLevelMap;
	
	/**boss活动开启设置*/
	private static Map<Integer, BossOpenSetting> bossOpenSettingMap;
	
	/**boss排名奖励*/
	private static Map<Integer, Map<Integer, BossrankRewardSetting>> bossRankRewardMap;
	
	/**章节奖励*/
	private static Map<Integer, Integer> CHAPTER_AWARD_MAP;
	
	/**章节宝箱奖励*/
	private static Map<Integer, Integer> CHAPTER_CHEST_MAP;
	
	/**队伍模版Map*/
	private static Map<Integer, TeamTemplate> TEAMTEMPLATE_MAP;
	
	/**交换赛奖励*/
	private static Map<Integer, SwapArenaRewardSetting> SWAP_REWARD_MAP;
	
	/**交换赛购买次数话费配置*/
	private static Map<Integer, SwapArenaBuySetting> SWAP_BUY_MAP;
	
	/**神秘商店物品*/
	private static Map<Integer, List<MysteryShopItem>> MYSTERY_SHOP_ITEM_MAP;
	
	/**战斗校验配置*/
	private static CheckConfig checkConfig;
	
	/**工会配置*/
	private static Map<Integer, AllianceSetting> ALLIANCESETTING_MAP;
	
	/**工会商店配置*/
	private static Map<Integer, AllianceShopItem> ALLIANCE_SHOPITEM_MAP;
	
	public static void init(){
		initSoul();
		initMonster();
		initChapters();
		initChapterRounds();
		initDrops();
		initBox();
		initUserRules();	
		initSoulEvolution();
		initProps();
		initEquipments();
		initStuffs();
		initBudings();
		initBudingRules();
		initBudingDrops();
		initActiveSkills();
		initCapSkills();
		initSoulSocialRule();
		initFriendGifiRule();
		initGoodsSynthesis();
		initAchieve();
		initAchieveAward();
		initBakProp();
		initActivity();
		initAthleticsInfoAward();
		initVipPrivilege();
		initJoinChapter();
		initExplorationAward();
		initGrowthFundRule();
		initVipWeekAward();
		initExplorationAwardExp();
		initMissionAward();
		initMissionCondition();
		initEquipmentSkillConfig();
		initRbootCap();
		initMall();
		initTotemRule();
		initTotemSoul();
		initCallRule();
		initCallRuleSoul();
		initCoinHand();
		initCoinHandRule();
		initStamina();
		initClimbTower();
		initClimbTowerStar();
		initAward();
		initBossSetting();
		initBossOpenSetting();
		initBossRankSetting();
		initChapterAward();
		initChapterChest();
		initTeamTemplateMap();
		initSwapReward();
		initSwapBuy();
		initCheckConfig();
		initRobot();
		initAllianceSettingMap();
		initAllianceShopItemsMap();
	}
	
	public static void initAllianceShopItemsMap(){
		Map<Integer, AllianceShopItem> ALLIANCE_SHOPITEM_MAP_TEMP = new HashMap<>();
		AllianceService service = ServiceFactory.getService(AllianceService.class);
		for(AllianceShopItem item : service.getAllianceShopItems()){
			ALLIANCE_SHOPITEM_MAP_TEMP.put(item.getId(), item);
		}
		ALLIANCE_SHOPITEM_MAP = ALLIANCE_SHOPITEM_MAP_TEMP;
	}
	
	public static void initAllianceSettingMap(){
		Map<Integer, AllianceSetting> ALLIANCESETTING_MAP_TEMP = new HashMap<>();
		AllianceService service = ServiceFactory.getService(AllianceService.class);
		for(AllianceSetting allianceSetting : service.getAllAllianceSetting()){
			ALLIANCESETTING_MAP_TEMP.put(allianceSetting.getLevel(), allianceSetting);
		}
		ALLIANCESETTING_MAP = ALLIANCESETTING_MAP_TEMP;
	}
	
	public static void initRobot(){
		SwapArenaSettingService service = ServiceFactory.getService(SwapArenaSettingService.class);
		service.initRobots();
	}
	
	public static void initCheckConfig(){
		CheckFightService service = ServiceFactory.getService(CheckFightService.class);
		checkConfig = service.loadConfig();
	}
	
	private static void initMysteryShopItem() {
		Map<Integer, List<MysteryShopItem>> map = new HashMap<>();
		MysteryShopItemService service = ServiceFactory.getService(MysteryShopItemService.class);
		for (MysteryShopItem entity : service.queryAll()) {
			List<MysteryShopItem> list = map.get(entity.getType());
			if(list == null) {
				list = new ArrayList<>();
				map.put(entity.getType(), list);
			}
			list.add(entity);
		}
		
		// 分别计算总权重
		for (Map.Entry<Integer, List<MysteryShopItem>> entry : map.entrySet()){
			int totalWeight = 0;
			for (MysteryShopItem entity : entry.getValue()){
				totalWeight = totalWeight + entity.getWeight();
			}
			
			for (MysteryShopItem entity : entry.getValue()){
				entity.setTotalWeight(totalWeight);
			}
		}
		MYSTERY_SHOP_ITEM_MAP = map;
    }

	public static void initSwapBuy(){
		Map<Integer, SwapArenaBuySetting> SWAP_BUY_MAP_TEMP = new HashMap<>();
		SwapArenaSettingService service = ServiceFactory.getService(SwapArenaSettingService.class);
		for(SwapArenaBuySetting buySetting : service.findAllSwapBuy()){
			SWAP_BUY_MAP_TEMP.put(buySetting.getTimes(), buySetting);
		}
		SWAP_BUY_MAP = SWAP_BUY_MAP_TEMP;
	}
	
	public static void initSwapReward(){
		Map<Integer, SwapArenaRewardSetting> SWAP_REWARD_MAP_TEMP = new HashMap<>();
		SwapArenaSettingService service = ServiceFactory.getService(SwapArenaSettingService.class);
		for(SwapArenaRewardSetting rewardSetting : service.findAllReward()){
			SWAP_REWARD_MAP_TEMP.put(rewardSetting.getRank(), rewardSetting);
		}
		SWAP_REWARD_MAP =SWAP_REWARD_MAP_TEMP;
	}
	
	public static void initTeamTemplateMap(){
		Map<Integer, TeamTemplate> TEAMTEMPLATE_MAP_TEMP = new HashMap<>();
		SwapArenaSettingService service = ServiceFactory.getService(SwapArenaSettingService.class);
		for(TeamTemplate teamTemplate : service.findAllTeamTemplate()){
			TEAMTEMPLATE_MAP_TEMP.put(teamTemplate.getTemplateId(), teamTemplate);
		}
		TEAMTEMPLATE_MAP = TEAMTEMPLATE_MAP_TEMP;
	}
	
	public static void initBossSetting(){
		Map<Integer, Map<Integer, BossSetting>> bossSettingMapTemp = new HashMap<>();
		Map<Integer, BossSetting> bossMaxLevelMapTemp =new HashMap<>();
		BossService service = ServiceFactory.getService(BossService.class);
		List<BossSetting> allBossSetting = service.getAllBossSetting();
		for(BossSetting bossSetting : allBossSetting){
			Map<Integer, BossSetting> map = bossSettingMapTemp.get(bossSetting.getBossId());
			if(map == null){
				map = new HashMap<>();
				bossSettingMapTemp.put(bossSetting.getBossId(), map);
			}
			map.put(bossSetting.getLevel(), bossSetting);
			
			
			BossSetting oldBoss = bossMaxLevelMapTemp.get(bossSetting.getBossId());
			if(oldBoss == null){
				bossMaxLevelMapTemp.put(bossSetting.getBossId(), bossSetting);
			}else{
				if(bossSetting.getLevel() > oldBoss.getLevel()){
					bossMaxLevelMapTemp.put(bossSetting.getBossId(), bossSetting);
				}
			}
		}
		
		bossSettingMap = bossSettingMapTemp;
		bossMaxLevelMap = bossMaxLevelMapTemp;
	}
	
	public static void initBossOpenSetting(){
		Map<Integer, BossOpenSetting> bossOpenSettingMapTemp = new HashMap<>();
		BossService service = ServiceFactory.getService(BossService.class);
		List<BossOpenSetting> allBossOpenSetting = service.getAllBossOpenSetting();
		for(BossOpenSetting bossOpenSetting : allBossOpenSetting){
			bossOpenSettingMapTemp.put(bossOpenSetting.getBossId(), bossOpenSetting);
		}
		bossOpenSettingMap = bossOpenSettingMapTemp;
	}
	
	public static void initBossRankSetting(){
		Map<Integer, Map<Integer, BossrankRewardSetting>> bossRankRewardMapTemp = new HashMap<>();
		BossService service = ServiceFactory.getService(BossService.class);
		List<BossrankRewardSetting> allBossRankRewardSetting = service.getAllBossRankRewardSetting();
		for(BossrankRewardSetting bossrankRewardSetting : allBossRankRewardSetting){
			Map<Integer, BossrankRewardSetting> map = bossRankRewardMapTemp.get(bossrankRewardSetting.getBossId());
			if(map == null){
				map = new HashMap<>();
				bossRankRewardMapTemp.put(bossrankRewardSetting.getBossId(), map);
			}
			map.put(bossrankRewardSetting.getRank(), bossrankRewardSetting);
		}
		bossRankRewardMap = bossRankRewardMapTemp;
	}

	public static void initMissionAward(){
		UserMissionService us=ServiceFactory.getService(UserMissionService.class);
		 Map<Integer,List<MissionAward>> map=new HashMap<Integer, List<MissionAward>>();
		 List<MissionAward> missionAwardList=us.queryMissionAward();
		 for( MissionAward ma:missionAwardList) {
			List<MissionAward> l=map.get(ma.getMissionId());
			if(l==null){
				l=new ArrayList<MissionAward>();
				l.add(ma);
				map.put(ma.getMissionId(), l);
			}else{
				l.add(ma);
			}
		}
		 MISSION_AWARD_MAP=map;
	}
	public static void initMissionCondition(){
		UserMissionService us=ServiceFactory.getService(UserMissionService.class);
		 Map<Integer,List<MissionCondition>> map=new HashMap<Integer, List<MissionCondition>>();
		 Map<Integer,Map<Integer,Set<Integer>>> condMap = new HashMap<Integer, Map<Integer,Set<Integer>>>();
		  List<MissionCondition> missionConditionList=us.queryMissionCondition();
		  for (MissionCondition ms:missionConditionList) {
			  List<MissionCondition> l= map.get(ms.getMissionId());
			  if(l==null){
				  l=new ArrayList<MissionCondition>();
				  l.add(ms);
				  map.put(ms.getMissionId(), l);
			  }else{
				  l.add(ms);
			  }
			  
			  Map<Integer,Set<Integer>> assMap = condMap.get(ms.getType());
			  if(assMap == null){
				  assMap = new HashMap<Integer, Set<Integer>>();
				  condMap.put(ms.getType(), assMap);
			  }
			  Set<Integer> missSet = assMap.get(ms.getAssId());
			 if(missSet == null){
				 missSet = new HashSet<>();
				 assMap.put(ms.getAssId(), missSet);
			 }
			 missSet.add(ms.getMissionId());
		  }
		  MISSION_CONDITIONS = condMap;
		  MISSION_CONDITION_MAP=map;
		  Map<Integer,Mission> missionMap=new HashMap<Integer, Mission>();
		  List<Mission> missionList=us.queryMission();
		  for (Mission ms:missionList) {
			  missionMap.put(ms.getMissionId(), ms);
			
		 }
		  MISSION_MAP=missionMap;
		
	}

	public static void initEquipmentSkillConfig(){
		GoodsService service = ServiceFactory.getService(GoodsService.class);
		Map<Integer,EquipmentSkill> map=new HashMap<>();
		List<EquipmentSkill> skills=service.getEquimentSkillS();
		for(EquipmentSkill skill:skills){
			map.put(skill.getPropId(), skill);
		}
		EQUIPMENT_SKILL_CONFIG=map;
	}

	public static void initJoinChapter(){
		ChapterService service = ServiceFactory.getService(ChapterService.class);
		Map<Integer,ChapterJoin> map=new HashMap<>();
		List<ChapterJoin> joins=service.queryChapterJoin();
		for(ChapterJoin join:joins){
			map.put(join.getChapterId(), join);
		}
		JOIN_CHPATER_MAP=map;
	}
	public static void initGrowthFundRule(){
		UserService service = ServiceFactory.getService(UserService.class);
		Map<Integer,GrowthFundRule> map=new HashMap<>();
		List<GrowthFundRule> rules=service.queryGrowthFundRule();
		for(GrowthFundRule rule:rules){
			map.put(rule.getGrade(), rule);
		}
		GROWTH_FUND_RULE_MAP=map;
	}
	public static void initVipWeekAward(){
		Map<Integer,List<VipWeekAward>> map=new HashMap<Integer, List<VipWeekAward>>();
		UserService service = ServiceFactory.getService(UserService.class);
		List<VipWeekAward> list=service.queryListVipWeekAward();
		for(VipWeekAward va : list){
			List<VipWeekAward> awards = map.get(va.getVipGrade());
			if(awards==null){
				awards=new ArrayList<>();
				map.put(va.getVipGrade(), awards);				
			}
			awards.add(va);			
		}
		VIP_WEEK_AWARD_MAP = map;
	}
	public static List<VipWeekAward>  getVipWeekAwards(int vipGrade){		
		return VIP_WEEK_AWARD_MAP.get(vipGrade);
	}
	public static void initVipPrivilege() {
		Map<Integer,VipPrivilege> map=new HashMap<>();
		UserService service = ServiceFactory.getService(UserService.class);
		List<VipPrivilege> list=service.queryListVipPrivilege();
		for(VipPrivilege vp:list){
			map.put(vp.getVipGrade(), vp);
		}
		VIP_PRIVILEGE_LIST = list;
		VIP_PRIVILEGE_MAP=map;	
	}
	public static void initAthleticsInfoAward() {
		
		Map<Integer,AthleticsInfoAward> map=new HashMap<>();
		AthleticsInfoService service = ServiceFactory.getService(AthleticsInfoService.class);
		List<AthleticsInfoAward> list=service.getAthleticsNameAward();
		for(AthleticsInfoAward bakProp:list){
			map.put(bakProp.getId(), bakProp);
		}
		ATHLETICS_INFO_AWARD=map;
		
	}
	public static void initBakProp(){
		HashMap<Integer, BakProp> map=new HashMap<Integer, BakProp>();
		GoodsService service = ServiceFactory.getService(GoodsService.class);
		List<BakProp> list=service.queryBakProp();
		for(BakProp bakProp:list){
			map.put(bakProp.getId(), bakProp);
		}
		BAK_PROP_MAP=map;
	}
	public static void initAchieveAward(){
		HashMap<Integer, List<AchieveAward>> map=new HashMap<Integer, List<AchieveAward>>();
		UserAchieveService service = ServiceFactory.getService(UserAchieveService.class);
		List<AchieveAward> list=service.getAllAchieveAward();
		for(AchieveAward v:list){
			if(map.get(v.getAchieveId())==null){
				map.put(v.getAchieveId(), new ArrayList<AchieveAward>());
			}
			map.get(v.getAchieveId()).add(v);
		}
		ACHIEVE_AWARDS=map;
	}

	private static void initAchieve(){
		MultiListMap<Integer,Achieve> map=new MultiListMap<>();
		UserAchieveService service = ServiceFactory.getService(UserAchieveService.class);
		List<Achieve> list=service.getAchieveRule();
		for(Achieve v:list){
			map.put(v.getType(),v);
		}		
		ACHIEVE_MAP=map;
	}
	public static void initChapterRounds(){
		Map<Integer,List<ChapterRound>> map=new HashMap<Integer, List<ChapterRound>>();
		ChapterService service = ServiceFactory.getService(ChapterService.class);
		
		List<ChapterRound> chapterRounds=service.queryAllRounds() ;
		for(ChapterRound ch : chapterRounds){
			List<ChapterRound> rounds = map.get(ch.getChapterId());
			if(rounds==null){
				rounds=new ArrayList<>();
				map.put(ch.getChapterId(), rounds);				
			}
			rounds.add(ch);			
		}
		CHAPTER_ROUND_MAP = map;
	}
	public static List<ChapterRound>  getChapterRounds(int chapterId){		
		return CHAPTER_ROUND_MAP.get(chapterId);
	}
	public static void initBox(){
		Map<Integer,Box> map=new HashMap<Integer, Box>();
		ChapterService service=ServiceFactory.getService(ChapterService.class);
		List<Box> boxList=service.queryAllBoxs();
		for(Box box:boxList){
			map.put(box.getBoxId(), box);
		}
		BOX_MAP = map;
	}
	public static Box getBox(int boxId){
		return BOX_MAP.get(boxId);
	}
	public static Box getExistBox(int boxId){
		Box box=BOX_MAP.get(boxId);
		if(box==null){
			throw new GameException(GameException.CODE_参数错误,"box no found."+boxId);
		}
		return box;
	}
	public static void initGoodsSynthesis() {
		GoodsService service = ServiceFactory.getService(GoodsService.class);
		Map<Integer,GoodsSynthesis> map = new HashMap<Integer, GoodsSynthesis>();
		List<GoodsSynthesis> gs = service.queryGoodsSynthesis();
		List<GoodsSynthesisRule> rules = service.queryGoodsSynthesisRule();
		for(GoodsSynthesis g : gs){
			g.setRules(new ArrayList<GoodsSynthesisRule>());
			map.put(g.getId(), g);
			for(GoodsSynthesisRule rule : rules){
				if(rule.getId()==g.getId()){
					g.getRules().add(rule);
				}
			}
		}
		GOODS_SYNTHESIS = map;
	}

	public static void initFriendGifiRule() {
		FriendService service = ServiceFactory.getService(FriendService.class);
		Map<Integer,FriendGifiRule> map = new HashMap<Integer, FriendGifiRule>();
		List<FriendGifiRule> rules = service.queryFriendRules();
		for(FriendGifiRule rule : rules){
			map.put(rule.getZone(), rule);
		}
		FRIEND_GIFI_RULE = map;
	}

	

	public static void initBudingDrops() {
		BudingService service = ServiceFactory.getService(BudingService.class);
		Map<Integer,Buding> map = new HashMap<>();
		List<Buding> list = service.queryAllBuding();
		for(Buding b : list){
			map.put(b.getBudingId(), b);
		}
		BUDINGS = map;
	}

	public static void initBudingRules() {
		BudingService service = ServiceFactory.getService(BudingService.class);
		Map<Integer, Map<Integer, BudingRule>> map = new HashMap<>();
		List<BudingRule> list = service.queryAllBudingRule();
		for(BudingRule b : list){
			Map<Integer, BudingRule> m = map.get(b.getBudingId());
			if(m==null){
				m = new HashMap<>();
				map.put(b.getBudingId(), m);
			}
			b.setTime(b.getTime()*1000);
			m.put(b.getLevel(), b);
		}
  		BUDING_RULES = map;
	}

	public static void initBudings() {
		BudingService service = ServiceFactory.getService(BudingService.class);
		Map<Integer, Map<Integer,List<BudingDrop>>> map = new HashMap<>();
		List<BudingDrop> list = service.queryAllBudingDrop();
		for(BudingDrop b : list){
			Map<Integer,List<BudingDrop>> m = map.get(b.getBudingId());
			if(m==null){
				m = new HashMap<Integer, List<BudingDrop>>();
				map.put(b.getBudingId(), m);
			}
			List<BudingDrop> l = m.get(b.getLevel());
			if(l==null){
				l = new ArrayList<BudingDrop>();
				m.put(b.getLevel(), l);
			}
			l.add(b);
		}
		BUDING_DROPS = map;
	}
	//初始化探索奖励物品
	public static void initExplorationAward() {
		SoulExploretionService service = ServiceFactory.getService(SoulExploretionService.class);
		Map<Integer, Map<Integer,List<ExplorationAward>>> map = new HashMap<>();
		List<ExplorationAward> list = service.querAwardList();
		for(ExplorationAward e : list){
			Map<Integer,List<ExplorationAward>> m = map.get(e.getHourTime());
			if(m==null){
				m = new HashMap<Integer, List<ExplorationAward>>();
				map.put(e.getHourTime(), m);
			}
			List<ExplorationAward> l = m.get(e.getSoulRare());
			if(l==null){
				l = new ArrayList<ExplorationAward>();
				m.put(e.getSoulRare(), l);
			}
			l.add(e);
		}
		
		// 分别计算战魂探索奖励的权重和
		for(Map.Entry<Integer, Map<Integer,List<ExplorationAward>>> entry : map.entrySet()) {
			for(Map.Entry<Integer,List<ExplorationAward>> listEntry : entry.getValue().entrySet()) {
				int totalWeight = 0;
				for (ExplorationAward value : listEntry.getValue()) {
					totalWeight = totalWeight + value.getWeight();
				}
				
				for (ExplorationAward value : listEntry.getValue()) {
					value.setTotalWeight(totalWeight);
				}
			}
		}
		EXPLORATION_AWARD_MAP = map;   //探索物品奖励
	}
	//初始化探索奖励经验
	public static void initExplorationAwardExp() {
		SoulExploretionService service = ServiceFactory.getService(SoulExploretionService.class);
		Map<Integer, Map<Integer,ExplorationAwardExp>> map = new HashMap<>();
		List<ExplorationAwardExp> list = service.querAwardExpList();
		for (ExplorationAwardExp exp:list) {
			Map<Integer,ExplorationAwardExp> m=map.get(exp.getHourTime());
			if(m==null){
				m = new HashMap<Integer, ExplorationAwardExp>();
				map.put(exp.getHourTime(), m);
			}
			ExplorationAwardExp eae =m.get(exp.getSoulRare());
			if(eae==null){
				m.put(exp.getSoulRare(), exp);
			}			
		}
		EXPLORATION_AWARD_EXP_MAP=map;
	}
	public static void initDrops() {
		ChapterService service = ServiceFactory.getService(ChapterService.class);
		Map<Integer,List<Drop>> map = new HashMap<>();		
		List<Drop> drops = service.queryAllDrop();
		for(Drop d : drops){
			List<Drop> dropList = map.get(d.getDropId());
			if(dropList==null){
				dropList=new ArrayList<Drop>();
				map.put(d.getDropId(),dropList);				
			}
			dropList.add(d);			
		}
		DROPS = map;
	}

	public static void initStuffs() {
		Map<Integer,Stuff> map = new HashMap<>();
		GoodsService service = ServiceFactory.getService(GoodsService.class);
		
		List<Stuff> stuffs = service.getStuffs();
		
		for(Stuff stuff : stuffs){
			map.put(stuff.getStuffId(), stuff);
		}
		
		STUFFS = map;
	}

	public static void initEquipments() {
		Map<Integer,Equipment> map = new HashMap<>();
		GoodsService service = ServiceFactory.getService(GoodsService.class);
		
		List<Equipment> equipments = service.getEquipments();
		List<EquipmentEffect> effects = service.getEquipmentEffects();
		List<EquipmentRepair> repairs = service.getEquipmentRepairs();
		for(Equipment e : equipments){
			e.setEffects(new ArrayList<EquipmentEffect>());
			e.setRepairs(new ArrayList<EquipmentRepair>());
			for(EquipmentEffect effect : effects){
				if(effect.getEquipmentId() == e.getEquipmentId()){
					e.getEffects().add(effect);
				}
			}
			for(EquipmentRepair repir:repairs){
				if(repir.getEquipmentId()==e.getEquipmentId()){
					e.getRepairs().add(repir);
				}
			}
			map.put(e.getEquipmentId(), e);
		}
		
		EQUIPMENTS = map;
	}

	public static void initProps() {
		Map<Integer,Prop> map = new HashMap<Integer, Prop>();
		
		GoodsService service = ServiceFactory.getService(GoodsService.class);
		
		List<Prop> propsList = service.getAllProp();
		List<PropEffect> effects = service.getItemEffects();
		
		for(Prop prop : propsList){
			prop.setEffects(new ArrayList<PropEffect>());
			for(PropEffect effect : effects){
				if(effect.getPropId()==prop.getPropId()){
					prop.getEffects().add(effect);
				}
			}
			map.put(prop.getPropId(), prop);
		}		
		props = map;
	}

	public static void initSoulEvolution() {
		Map<Integer,SoulEvolution> evos = new HashMap<>();
		Map<Integer,List<SoulEvolution>> varis = new HashMap<>();
		
		SoulService service = ServiceFactory.getService(SoulService.class);
		List<SoulEvolution> ses = service.querySoulEvolution();
		
		for(SoulEvolution se : ses){
			if(se.getEvo()==SoulEvolution.EVO_EVOLUTION){
				evos.put(se.getBassSoul(), se);
			}else{
				List<SoulEvolution> list = varis.get(se.getBassSoul());
				if(list == null){
					list = new ArrayList<SoulEvolution>();
					varis.put(se.getBassSoul(), list);
				}
				list.add(se);
			}
		}
		
		EVOLUTIONS = evos;
		VARIATIONS = varis;
	}
	//初始化主动技能
	public static void initActiveSkills() {
		SkillService service = ServiceFactory.getService(SkillService.class);
		Map<Integer,ActiveSkill> activeSkillmap = new HashMap<>();
		List<ActiveSkill> activeskills = service.queryActiveSkills();
		List<ActiveSkillEffect> activeEffects = service.queryActiveSkillEffects();
		for(ActiveSkill s : activeskills){
			s.setEffects(new ArrayList<ActiveSkillEffect>());
			for(ActiveSkillEffect e : activeEffects){
				if(e.getSkillId() == s.getSkillId()){
					s.getEffects().add(e);
				}
			}
			activeSkillmap.put(s.getSkillId(), s);
		}
		ACTIVE_SKILLS = activeSkillmap;
	}
	//初始化队长技能
	public static void initCapSkills() {
		SkillService service = ServiceFactory.getService(SkillService.class);
		Map<Integer,CapSkill> map = new HashMap<Integer, CapSkill>();
		List<CapSkill> capSkills = service.queryCapSkills();
		List<CapSkillEffect> effect = service.queryCapSkillEffect();
		for(CapSkill capSkill : capSkills){
			capSkill.setEffects(new ArrayList<CapSkillEffect>());
			for(CapSkillEffect e : effect){
				if(e.getSkillId() == capSkill.getSkillId()){
					capSkill.getEffects().add(e);
				}
			}
			map.put(capSkill.getSkillId(), capSkill);
		}
		CAP_SKILLS = map;
	}
	public static void initSoicalSkill(){
		SkillService service = ServiceFactory.getService(SkillService.class);
		Map<Integer,SocialSkill> map = new HashMap<Integer, SocialSkill>();
		List<SocialSkill> capSkills = service.querySocialSkill();
		List<SocialSkillEffect> effects = service.querySoicalSkillEffects();
		for(SocialSkill socialSkill : capSkills){
			socialSkill.setEffects(new ArrayList<SocialSkillEffect>());
			for(SocialSkillEffect e : effects){
				if(e.getSkillId() == socialSkill.getSkillId()){
					socialSkill.getEffects().add(e);
				}
			}
			map.put(socialSkill.getSkillId(), socialSkill);
		}
		SOICAL_SKILLS = map;
	}
	public static void initSoulSocialRule(){
		SkillService service = ServiceFactory.getService(SkillService.class);
		HashMap<Integer, List<SocialSkillRule>> map=new HashMap<Integer, List<SocialSkillRule>>();
		List<SocialSkillRule> skills=service.querySocialSkillRule();
		for(SocialSkillRule s:skills){
			List<SocialSkillRule> rules=map.get(s.getSoulId());
			if(rules==null){
				rules=new ArrayList<>();
				rules.add(s);
				map.put(s.getSkillId(), rules);
			}else{
				rules.add(s);
			}
		}
		SOUL_SOCIAL_RULE = map;
	}
	public static void initUserRules() {
		UserService userService = ServiceFactory.getService(UserService.class);
		Map<Integer,UserRule> map = new HashMap<>();
		List<UserRule> userRule = userService.getUserRules();
		for(UserRule rule : userRule){
			map.put(rule.getLevel(), rule);
		}
		USER_RULES=map;
	}

	public static void initMonster() {
		Map<Integer,Monster> map=new HashMap<>();
		ChapterService service = ServiceFactory.getService(ChapterService.class);
		List<Monster> dms = service.queryAllMonster();
		for(Monster dm : dms){
			map.put(dm.getMonsterId(), dm);
		}
		MONSTERS=map;
	}
	
	public static void initChapters() {
		Map<Integer,Chapter> map=new HashMap<>();
		ChapterService service = ServiceFactory.getService(ChapterService.class);
		List<Chapter> list = service.queryAllChapters();
		for(Chapter d : list){
			map.put(d.getChapterId(), d);
		}
		CHAPTER_MAP=map;
	}

	public static void initSoul() {
		SoulService service = ServiceFactory.getService(SoulService.class);
		Map<Integer,Soul> map = new HashMap<>();
		List<Soul> souls = service.queryAllSoul();
		for(Soul s : souls){
			map.put(s.getSoulId(), s);
		}
		SOUL_MAP = map;
	}
	
	//初始好友机器人
	public static void initRbootCap(){
		UserTeamService service = ServiceFactory.getService(UserTeamService.class);
		UserService userService = ServiceFactory.getService(UserService.class);
		User user=userService.getExistUser(User.USER_ID_好友机器人);
		UserSoul soul=new UserSoul();
		soul.setLevel(1);
		soul.setSoulId(1010170);
		service.initUserCap(user,soul);
		
	}
	public static void initActivity(){
		ActivityService service = ServiceFactory.getService(ActivityService.class);
		service.initActivity();
		//初始化概率活动翻倍
		service.initDropRateMultiple();
	}
	
	/**
	 * 初始化充值送魂钻活动
	 */
	public static void initMall(){
		Map<Integer, Mall> map = new HashMap<>();
		MallService service = ServiceFactory.getService(MallService.class);
		List<Mall> list = service.queryAllMall();
		if(list == null || list.isEmpty()) {
			// 没有充值送魂钻活动
		} else {
			for(Mall entity : list) {
				map.put(entity.getMoney(), entity);
			}
		}
		MALL_MAP = map;
	}
	
	/**
	 * 获得战魂
	 * @param soulId 战魂编号
	 * @return 战魂
	 */
	public static Soul getSoul(int soulId){
		return SOUL_MAP.get(soulId);
	}
	
	
	public static Map<Integer, Soul> getSoulMap() {
		return SOUL_MAP;
	}
	/**
	 * 获得章节
	 * @param dungeonId 地下城编号
	 * @param checkpoint 段数
	 * @param round 回合数
	 * @return 地下城
	 */
	public static Chapter getChapter(int chapterId){	
		return CHAPTER_MAP.get(chapterId);
	}
	
	/**
	 * 获得怪物
	 * @param dungeonId 地下城编号
	 * @param npcId NPC编号
	 * @return 地下城怪物
	 */
	public static Monster getMonster(int monsterId){
		return MONSTERS.get(monsterId);
	}
	/**
	 * 获得结束地下城
	 * @param mapId
	 * @return
	 */
	public static int getEndDungeons(int mapId){
		return END_DUNGEONS.get(mapId);
	}
	/**
	 * 获得用户规则
	 * @param level 等级
	 * @return 用户规则
	 */
	public static UserRule getUserRule(int level){
		UserRule rule = USER_RULES.get(level);
		if(rule == null){
			throw new GameException(GameException.CODE_参数错误, "can't find user rule : "+level);
		}
		return rule;
	}
	/**
	 * 获得主动技能
	 * @param skillId 主动技能编号
	 * @return 主动技能
	 */
	public static ActiveSkill getActiveSkill(int skillId){
		return ACTIVE_SKILLS.get(skillId);
	}
	
	/**
	 * 获得合作技能
	 * @param skillId 主动技能编号
	 * @return 主动技能
	 */
	public static SocialSkill getSocialSkill(int skillId){
		return SOICAL_SKILLS.get(skillId);
	}
	/**
	 * 获得进化配方
	 * @param soulId 战魂编号
	 * @return 进化配方
	 */
	public static SoulEvolution getEvolution(int soulId){
		return EVOLUTIONS.get(soulId);
	}
	/**
	 * 获得变异配方
	 * @param soulId 战魂编号
	 * @return 变异配方
	 */
	public static List<SoulEvolution> getVariation(int soulId){
		return VARIATIONS.get(soulId);
	}
	/**
	 * 获得道具
	 * @param itemId 道具编号
	 * @return 道具
	 */
	public static Prop getProp(int propId){
		return props.get(propId);
	}
	/**
	 * 获得材料
	 * @param stuffId 材料编号
	 * @return 材料
	 */
	public static Stuff getStuff(int stuffId){
		return STUFFS.get(stuffId);
	}
	/**
	 * 获得装备
	 * @param equipmentId 装备编号
	 * @return 装备
	 */
	public static Equipment getEquipment(int equipmentId){
		return EQUIPMENTS.get(equipmentId);
	}
	/**
	 * 获得掉落列表
	 * @param dungeons 关卡编号
	 * @param checkpoint 段
	 * @return 掉落列表
	 */
	public static List<Drop> getDrops(int dropId){
		List<Drop> drops = DROPS.get(dropId);	
		return drops;
	}
	/**
	 * 获得建筑
	 * @param budingId 建筑编号
	 * @return 建筑
	 */
	public static Buding getBuding(int budingId){
		return BUDINGS.get(budingId);
	}
	/**
	 * 获得建筑规则
	 * @param budingId 建筑编号
	 * @param level 建筑等级
	 * @return 建筑规则
	 */
	public static BudingRule getBudingRule(int budingId,int level){
		
		 Map<Integer, BudingRule> map = BUDING_RULES.get(budingId);
		 
		if(map==null){
			return null;
		}
		return map.get(level);
	}
	/**
	 * 获得建筑掉落
	 * @param budingId 建筑编号
	 * @param level 建筑等级
	 * @return 掉落列表
	 */
	public static List<BudingDrop> getBudingDrops(int budingId,int level){
		return BUDING_DROPS.get(budingId).get(level);
	}
	/**
	 * 获取建筑
	 * @return 所有建筑
	 */
	public static Collection<Buding> getBudings(){
		return BUDINGS.values();
	}
	/**
	 * 获得队长技能
	 * @param capSkillId 队长技能编号
	 * @return 队长技能
	 */
	public static CapSkill getCapSkill(int capSkillId){
		return CAP_SKILLS.get(capSkillId);
	}
	/**
	 * 获得好友赠品规则
	 * @param zone 编号
	 * @return 规则
	 */
	public static FriendGifiRule getFriendGifiRule(int zone){
		return FRIEND_GIFI_RULE.get(zone);
	}
	/**
	 * 物品合成
	 * @param id 编号
	 * @return 物品合成
	 */
	public static GoodsSynthesis getGoodsSynthesis(int id){
		return GOODS_SYNTHESIS.get(id);
	}
	public static BakProp getBakProp(int propId){
		return BAK_PROP_MAP.get(propId);
	}
	public static List<SocialSkillRule> getSocialSkillList(int sould){
		List<SocialSkillRule> rules=SOUL_SOCIAL_RULE.get(sould);
		return rules;
	}
	/**
	 * 获得竞技场升级称号奖励
	 * @param id  称号编号
	 * @return 升级称号奖励
	 */
	public static final AthleticsInfoAward getAthleticsInfoAward(int id){
		return ATHLETICS_INFO_AWARD.get(id);
	}
	
	/**
	 * 获得竞技场升级称号奖励
	 * @param id  称号编号
	 * @return 升级称号奖励
	 */
	public static final VipPrivilege getVipPrivilege(int totalCurrency){
		int vipGrade=getVipGrade(totalCurrency);
		return VIP_PRIVILEGE_MAP.get(vipGrade);
	}
	
	public static final Map<Integer,VipPrivilege> getVipPrivilegeMap(){
		return VIP_PRIVILEGE_MAP;
	}
	
	/**
	 * VIP 等级计算， 累计+充值计算
	 * @param totalCurrency
	 * @return
	 */
	public static final int getVipGrade(int totalCurrency){
		int vipGrade=0;
		/*int total = 0;
		for (int i = 0; i < VIP_PRIVILEGE_LIST.size(); i++) {
			total = total + VIP_PRIVILEGE_LIST.get(i).getTotalCurrency();
			if(totalCurrency>0 && totalCurrency>=total){
				vipGrade=vipGrade+1;
			}
		}*/
		
		// VIP 0-9
		for (int i = 0; i < VIP_PRIVILEGE_LIST.size(); i++) {
			if(totalCurrency >= 0 && totalCurrency >= VIP_PRIVILEGE_LIST.get(i).getTotalCurrency() && VIP_PRIVILEGE_LIST.get(i).getVipGrade() > 0){
				vipGrade = vipGrade+1;
			}
		}
		return vipGrade;
		
	}
	public static final Map<Integer,ChapterJoin> getChapterJoinMap(){
		return JOIN_CHPATER_MAP;
	}
	public static final GrowthFundRule getGrowthFundRule(int grade){
		return GROWTH_FUND_RULE_MAP.get(grade);
	}
	
	public static final EquipmentSkill getEquipmentSkillConfig(int propId){
		return EQUIPMENT_SKILL_CONFIG.get(propId);
	}
	
	/**
	 * 获得他们所奖励
	 * @param hour 探索时间
	 * @param level 建筑等级
	 * @return 掉落列表
	 */
	public static List<ExplorationAward> getExplorationAwards(int hour,int soulRare){
		return EXPLORATION_AWARD_MAP.get(hour).get(soulRare);
	}
	
	/**
	 * 获得探索经验
	 * @param hour 探索时间
	 * @param rare 星级
	 * @return 
	 */
	public static ExplorationAwardExp getExplorationAwadExp(int hour,int soulRare){
		return EXPLORATION_AWARD_EXP_MAP.get(hour).get(soulRare);
	}
	/**
	 * 获取任务条件集合
	 * @param missionId
	 * @return
	 */
	public static List<MissionCondition> getMissionConditionList(int missionId){
		return MISSION_CONDITION_MAP.get(missionId);
	}
	/**
	 * 获取任务
	 * @param missionId
	 * @return
	 */
	public static Mission getMission(int missionId){
		return MISSION_MAP.get(missionId);
	}
	/**
	 * 获取对应的任务奖励
	 * @param missionId
	 * @return
	 */
	public static List<MissionAward> getMissionAWardList(int missionId){
		return MISSION_AWARD_MAP.get(missionId);
	}
	/**
	 * 获取任务基础条件
	 * @return
	 */
	public static Collection<Mission> getMissions(){
		return MISSION_MAP.values();
	}
	/**
	 * 获取对应任务基础条件
	 * @return
	 */
	public static Set<Integer> getMissionCond(int type,int assId){
		if(MISSION_CONDITIONS.get(type)==null){
			return null;
		}
		return MISSION_CONDITIONS.get(type).get(assId);
	}
	
	/**
	 * 获取充值送魂钻
	 * @return
	 */
	public static Map<Integer, Mall> getAllMall() {
		return MALL_MAP;
	}
	
	/**
	 * 神木图腾合成对应规则
	 * @return
	 */
	public static void initTotemSoul() {
		TotemSoulService service = ServiceFactory.getService(TotemSoulService.class);
		Map<Integer, List<TotemSoul>> map = new HashMap<>();
		
		for(TotemSoul entity : service.queryAll()){
			List<TotemSoul> list = map.get(entity.getSoulRare());
			if(list == null) {
				list = new ArrayList<>();
				map.put(entity.getSoulRare(), list);
			}
			list.add(entity);
		}
		
		// 分别计算总权重
		for (Map.Entry<Integer, List<TotemSoul>> entry : map.entrySet()){
			int totalWeight = 0;
			for (TotemSoul entity : entry.getValue()){
				totalWeight = totalWeight + entity.getWeight();
			}
			
			for (TotemSoul entity : entry.getValue()){
				entity.setTotalWeight(totalWeight);
			}
		}
		TOTEM_SOUL_MAP = map;
    }

	/**
	 * 神木图腾合成对应规则产出战魂
	 * @return
	 */
	public static void initTotemRule() {
		TotemRuleService service = ServiceFactory.getService(TotemRuleService.class);
		Map<Integer,TotemRule> map = new HashMap<>();
		
		List<TotemRule> list = service.queryAll();
		for(TotemRule entity : list){
			map.put(entity.getSoulRare(), entity);
		}
		TOTEM_RULE_MAP = map;
    }
	
	public static Map<Integer, TotemRule> getAllTotemRule() {
		return TOTEM_RULE_MAP;
	}
	

	public static Map<Integer, List<TotemSoul>> getAllTotemSoul() {
		return TOTEM_SOUL_MAP;
	}
	
	public static List<TotemSoul> getAllTotemSoul(int soulRare) {
		return TOTEM_SOUL_MAP.get(soulRare);
	}
	
	/**
	 * 战魂召唤对应规则产出战魂
	 * @return
	 */
	public static void initCallRuleSoul() {
		CallRuleSoulService service = ServiceFactory.getService(CallRuleSoulService.class);
		Map<Integer, List<CallRuleSoul>> map = new HashMap<>();
		
		for(CallRuleSoul entity : service.queryAll()){
			List<CallRuleSoul> list = map.get(entity.getCallRuleId());
			if(list == null) {
				list = new ArrayList<>();
				map.put(entity.getCallRuleId(), list);
			}
			list.add(entity);
		}
		
		for(Map.Entry<Integer, List<CallRuleSoul>> entry : map.entrySet()) {
			List<CallRuleSoul> list = entry.getValue();
			int totalWeight = 0;
			for (CallRuleSoul entity : list) {
				totalWeight = totalWeight + entity.getWeight();
			}
			
			for (CallRuleSoul entity : list) {
				entity.setTotalWeight(totalWeight);
			}
		}
		
		CALL_RULE_SOUL_MAP = map;
    }

	/**
	 * 战魂召唤对应规则
	 * @return
	 */
	public static void initCallRule() {
		CallRuleService service = ServiceFactory.getService(CallRuleService.class);
		Map<Integer, List<CallRule>> map = new HashMap<>();
		
		for(CallRule entity : service.queryAll()) {
			
			List<CallRule> list = map.get(entity.getTypeId());
			if(list == null) {
				list = new ArrayList<>();
				map.put(entity.getTypeId(), list);
			}
			list.add(entity);
		}
		
		// 计算同类型权重和
		for (Map.Entry<Integer, List<CallRule>> entry : map.entrySet()) {
			int totalWeight = 0;
			for (CallRule entity : entry.getValue()) {
				totalWeight = totalWeight + entity.getWeight();
			}
			
			for (CallRule entity : entry.getValue()) {
				entity.setTotalWeight(totalWeight);
			}
		}
		
		CALL_RULE_MAP = map;
    }
	
	/**
	 * 友情点、魂币召唤战魂
	 * @return
	 */
	public static Map<Integer, List<CallRuleSoul>> getCallRuleSoulMap() {
		return CALL_RULE_SOUL_MAP;
	}
	
	/**
	 * 按照规则编号查找 友情点、魂币召唤战魂规则
	 * @param callRuleId
	 * @return
	 */
	public static List<CallRuleSoul> getNewCallRuleListByCallRuleId(int callRuleId){
		List<CallRuleSoul> list = CALL_RULE_SOUL_MAP.get(callRuleId);
		List<CallRuleSoul> newList = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			for (CallRuleSoul old : list) {
				CallRuleSoul entity = new CallRuleSoul();
				entity.setId(old.getId());
				entity.setCallRuleId(old.getCallRuleId());
				entity.setSoulId(old.getSoulId());
				entity.setWeight(old.getWeight());
				entity.setTotalWeight(old.getTotalWeight());
				
				newList.add(entity);
			}
		}
		return newList;
	}
	
	/**
	 * 魂币、友情点召唤
	 * @return
	 */
	public static Map<Integer, List<CallRule>> getCallRuleMap() {
		return CALL_RULE_MAP;
	}
	
	private static void initCoinHand() {
		CoinHandService service = ServiceFactory.getService(CoinHandService.class);
		Map<Integer, CoinHand> map = new HashMap<>();
		
		for(CoinHand entity : service.queryAll()) {
			map.put(entity.getNum(), entity);
		}
		COIN_HAND_MAP = map;
    }
	

	private static void initCoinHandRule() {
		CoinHandRuleService service = ServiceFactory.getService(CoinHandRuleService.class);
		Map<Integer, List<CoinHandRule>> map = new HashMap<>();
		
		for(CoinHandRule entity : service.queryAll()) {
			
			List<CoinHandRule> list = map.get(entity.getCoinHandId());
			if(list == null) {
				list = new ArrayList<>();
				map.put(entity.getCoinHandId(), list);
			}
			list.add(entity);
		}
		
		// 计算累计权重值
		for (Map.Entry<Integer, List<CoinHandRule>> entry : map.entrySet()) {
			int totalWeight = 0;
			for (CoinHandRule entity : entry.getValue()) {
				totalWeight = totalWeight + entity.getWeight();
			}
			
			for (CoinHandRule entity : entry.getValue()) {
				entity.setTotalWeight(totalWeight);
			}
		}
		
		COIN_HAND_RULE_MAP = map;
    }
	
	/**
	 * 点金手
	 * @return
	 */
	public static Map<Integer, CoinHand> getCoinHandMap() {
		return COIN_HAND_MAP;
	}

	/**
	 * 点金手权重
	 * @return
	 */
	public static Map<Integer, List<CoinHandRule>> getCoinHandRuleMap() {
		return COIN_HAND_RULE_MAP;
	}
	
	/**
	 * 购买体力配置
	 */
	private static void initStamina() {
		StaminaService service = ServiceFactory.getService(StaminaService.class);
		Map<Integer, Stamina> map = new HashMap<>();
		
		for(Stamina entity : service.queryAll()) {
			map.put(entity.getBuyCount(), entity);
		}
		STAMINA_MAP = map;
    }
	
	/**
	 * 购买体力配置
	 * @return
	 */
	public static Map<Integer, Stamina> getStaminaMap() {
		return STAMINA_MAP;
	}
	
	/**
	 * 获取boss
	 */
	public static BossSetting getBossByIdAndLevel(int bossId, int level){
		Map<Integer, BossSetting> map = bossSettingMap.get(bossId);
		if(map != null){
			return map.get(level);
		}
		return null;
	}
	
	/**
	 * 获取等级最大的boss
	 * @param bossId
	 * @return
	 */
	public static BossSetting getMaxLevelBossSetting(int bossId){
		return bossMaxLevelMap.get(bossId);
	}
	
	/**
	 * 获取所有boss开启设置
	 * @return
	 */
	public static Collection<BossOpenSetting> getAllBossOpenSetting(){
		return bossOpenSettingMap.values();
	}
	
	/**
	 * 获取所有boss开启设置
	 * @return
	 */
	public static BossOpenSetting getBossOpenSetting(int bossId){
		return bossOpenSettingMap.get(bossId);
	}
	
	
	/**
	 * 获取排名奖励
	 * @param bossId
	 * @param rank
	 * @return
	 */
	public static BossrankRewardSetting getBossRankRewardSetting(int bossId, int rank){
		
		Map<Integer, BossrankRewardSetting> map = bossRankRewardMap.get(bossId);
		if(map != null){
			return map.get(rank);
		}
		return null;
	}
	
	/**
	 * 排名奖励数量
	 * @param bossId
	 * @return
	 */
	public static int getRankRewardNum(int bossId){
		return bossRankRewardMap.get(bossId).size();
	}
	
		/**
	 * 爬塔试炼奖励配置
	 */
	private static void initAward() {
		AwardService service = ServiceFactory.getService(AwardService.class);
		Map<Integer, List<Award>> map = new HashMap<>();
		
		for(Award entity : service.queryAll()) {
			List<Award> list = map.get(entity.getId());
			if(list == null) {
				list = new ArrayList<>();
				map.put(entity.getId(), list);
			}
			list.add(entity);
		}
		AWARD_MAP = map;
    }
	
	/**
	 * 奖励配置
	 * @return
	 */
	public static Map<Integer, List<Award>> getClimbTowerAwardMap() {
		return AWARD_MAP;
	}

	/**
	 * 爬塔试炼星集配置
	 */
	private static void initClimbTowerStar() {
		ClimbTowerStarService service = ServiceFactory.getService(ClimbTowerStarService.class);
		Map<String, ClimbTowerStar> map = new HashMap<>();
		
		for(ClimbTowerStar entity : service.queryAll()) {
			map.put(entity.getStartRegion() + "." + entity.getStartNum(), entity);
		}
		CLIMBTOWERSTAR_MAP = map;
    }
	
	
	/**
	 * 爬塔试炼星集配置
	 * @return
	 */
	public static Map<String, ClimbTowerStar> getClimbTowerStarMap() {
		return CLIMBTOWERSTAR_MAP;
	}
	
	/**
	 * 爬塔试炼星集配置
	 * @return
	 */
	public static ClimbTowerStar getClimbTowerStarMap(int region, int starNum) {
		return CLIMBTOWERSTAR_MAP.get(region + "." + starNum);
	}

	/**
	 * 爬塔试炼配置
	 */
	private static void initClimbTower() {
		ClimbTowerService service = ServiceFactory.getService(ClimbTowerService.class);
		Map<Integer, ClimbTower> map = new HashMap<>();
		
		for(ClimbTower entity : service.queryAll()) {
			map.put(entity.getTowerFloor(), entity);
		}
		CLIMBTOWER_MAP = map;
    }
	
	/**
	 * 爬塔试炼配置
	 * @return
	 */
	public static Map<Integer, ClimbTower> getClimbTowerMap() {
		return CLIMBTOWER_MAP;
	}
	
	private static void initChapterChest() {
		ChapterChestService service = ServiceFactory.getService(ChapterChestService.class);
		Map<Integer, Integer> map = new HashMap<>();
		
		for(ChapterChest entity : service.queryAll()) {
			map.put(entity.getId(), entity.getAwardId());
		}
		
		CHAPTER_CHEST_MAP = map;
    }

	private static void initChapterAward() {
		ChapterAwardService service = ServiceFactory.getService(ChapterAwardService.class);
		Map<Integer, Integer> map = new HashMap<>();
		
		for(ChapterAward entity : service.queryAll()) {
			map.put(entity.getId(), entity.getAwardId());
		}
		
		CHAPTER_AWARD_MAP = map;
    }
	
	/**
	 * 章节奖励
	 * @param chapterId
	 * @return
	 */
	public static List<Award> getChapterAward(int chapterId) {
		Integer awardId = CHAPTER_AWARD_MAP.get(chapterId);
		List<Award> list = null;
		if (awardId != null) {
			list = AWARD_MAP.get(awardId);
		}
		return list;
	}
	
	/**
	 * 章节宝箱
	 * @param chapterId
	 * @return
	 */
	public static List<Award> getChapterChest(int chapterId){
		Integer awardId = CHAPTER_CHEST_MAP.get(chapterId);
		List<Award> list = null;
		if (awardId != null) {
			list = AWARD_MAP.get(awardId);
		}
		return list;
	}
	
	/**
	 * 队伍模版
	 * @param id
	 * @return
	 */
	public static TeamTemplate getTeamTemplateById(int id){
		return TEAMTEMPLATE_MAP.get(id);
	}
	
	/**
	 * 获取排名奖励
	 * @param rank
	 * @return
	 */
	public static SwapArenaRewardSetting getSwapReward(int rank){
		SwapArenaRewardSetting swapArenaRewardSetting = SWAP_REWARD_MAP.get(rank);
		if(swapArenaRewardSetting == null){
			swapArenaRewardSetting = SWAP_REWARD_MAP.get(SWAP_REWARD_MAP.size());
		}
		return swapArenaRewardSetting;
	}
	
	/**
	 * 购买配置
	 * @param rank
	 * @return
	 */
	public static SwapArenaBuySetting getSwapBuySetting(int time){
		SwapArenaBuySetting swapArenaBuySetting = SWAP_BUY_MAP.get(time);
		if(swapArenaBuySetting == null){
			swapArenaBuySetting = SWAP_BUY_MAP.get(SWAP_BUY_MAP.size());
		}
		return swapArenaBuySetting;
	}
	
	/**
	 * 获取配置
	 * @return
	 */
	public static CheckConfig getCheckConfig(){
		return checkConfig;
	}
	
	/**
	 * 获取工会配置
	 * @param level
	 * @return
	 */
	public static AllianceSetting getAllianceSetting(int level){
		return  ALLIANCESETTING_MAP.get(level);
	}
	
	/**
	 * 获取工会商品
	 * @param id
	 * @return
	 */
	public static AllianceShopItem getAllianceShopItem(int id){
		return ALLIANCE_SHOPITEM_MAP.get(id);
	}
	
	/**
	 * 获取所有工会商品
	 * @param id
	 * @return
	 */
	public static Collection<AllianceShopItem> getAllianceShopItems(){
		return ALLIANCE_SHOPITEM_MAP.values();
	}
	
}
