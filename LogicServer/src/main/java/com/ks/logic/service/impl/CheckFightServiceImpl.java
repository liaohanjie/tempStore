package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.ks.logger.LoggerFactory;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.CheckFightService;
import com.ks.model.boss.BossOpenSetting;
import com.ks.model.boss.UserBossRecord;
import com.ks.model.check.BattleCheckReslut;
import com.ks.model.check.BattleType;
import com.ks.model.check.CheckConfig;
import com.ks.model.check.SimpleFightModel;
import com.ks.model.check.UserDoubtCheckLog;
import com.ks.model.check.UserFightCheck;
import com.ks.model.dungeon.Monster;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentEffect;
import com.ks.model.goods.Backage;
import com.ks.model.goods.UserGoods;
import com.ks.model.skill.ActiveSkill;
import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillEffect;
import com.ks.model.soul.Soul;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.vo.check.BattleCheckVO;
import com.ks.protocol.vo.check.CheckVO;
import com.ks.util.JSONUtil;

public class CheckFightServiceImpl extends BaseService implements CheckFightService {
	
	private static final Logger logger = LoggerFactory.get(CheckFightServiceImpl.class);
	
	/**
	 * 服务端提交
	 */
	public static final int SERVER_COMMITER = 0;
	
	/**
	 * 客户端提交
	 */
	public static final int COMMITER_CLIENT = 1;

	@Override
	public void check(int userId, CheckVO checkVO) {
		
		try {
			User user = userService.getExistUserCache(userId);
			
			//获取队长技能
			CapSkill attCapSkill = null;
			UserCap userCap = userService.getUserCap(userId);
			if(userCap != null){
				Soul capsoul = GameCache.getSoul(userCap.getSoulId());
				attCapSkill = GameCache.getCapSkill(capsoul.getCapSkill());
			}
			
			
			//获取当前队伍所有战魂
			UserTeam attackerTeam = userTeamService.getExistUserTeamCache(user.getUserId(), user.getCurrTeamId());
			List<UserSoul> userSouls = new ArrayList<>();
			for (byte pos = 0; pos < attackerTeam.getPos().size(); pos++) {
				long userSoulId = attackerTeam.getPos().get(pos);
				if (userSoulId != 0) {
					UserSoul userSoul = userSoulService.getExistUserSoulCache(userId, userSoulId);
					userSoul.setPos(pos);
					userSouls.add(userSoul);
				}
			}
			
			//机器人队长技能
			CapSkill robotCapSkill = null;
			
			//抽样的战魂id中，只能有一个是非己的战魂
			List<UserSoul> robotSouls = new ArrayList<UserSoul>();
			List<UserSoul> friendSouls = new ArrayList<UserSoul>();
			for(BattleCheckVO battleCheckVO : checkVO.getCheckList()){
				if(!battleCheckVO.isMysoul()){
					//机器人
					if(battleCheckVO.getUserSoulId() <= 0){
						if(robotSouls.isEmpty()){
							UserSoul userSoul = new UserSoul();
							userSoul.setSoulId(battleCheckVO.getSoulId());
							userSoul.setLevel(battleCheckVO.getLevel());
							userSoul.setSkillLv(1);
							robotSouls.add(userSoul);
							
							Soul soul = GameCache.getSoul(userSoul.getSoulId());
							robotCapSkill = GameCache.getCapSkill(soul.getCapSkill());
						}
					}else{
						//是否已经加入这个战魂
						boolean hasThisSoul =false;
						for(UserSoul temp: friendSouls){
							if(temp.getId()==battleCheckVO.getUserSoulId()){
								hasThisSoul = true;
								break;
							}
						}
						if(!hasThisSoul){
							UserSoul friendUserSoul = userSoulService.getExistUserSoul(checkVO.getFriendId(), battleCheckVO.getUserSoulId());
							friendSouls.add(friendUserSoul);
						}
					}
				}
			}
			
			
			if(friendSouls.size() + robotSouls.size()> 1){
				String reason = "存在多个非自己战魂id:";
				createDoubtCheckLog(userId, SERVER_COMMITER, JSONUtil.toJson(checkVO), "", reason);
				return;
			}
			
			//将机器人战魂加入到已有战魂
			userSouls.addAll(robotSouls);
			userSouls.addAll(friendSouls);
			
			//获取好友队长技能
			CapSkill friednCapSkill = null;
			if(checkVO.getFriendId() > 0){
				UserCap friendUserCap = userService.getUserCap(checkVO.getFriendId());
				if(friendUserCap != null){
					UserSoul friendUserSoul = userSoulService.getExistUserSoul(checkVO.getFriendId(), friendUserCap.getUserSoulId());
					if(friendUserSoul != null){
						userSouls.add(friendUserSoul);
					}
					Soul capsoul = GameCache.getSoul(friendUserCap.getSoulId());
					friednCapSkill = GameCache.getCapSkill(capsoul.getCapSkill());
				}
			}
			
			
			//检查是触发队长技能
			robotCapSkill = checkCapSkill(userSouls, robotCapSkill);
			attCapSkill = checkCapSkill(userSouls, attCapSkill);
			friednCapSkill = checkCapSkill(userSouls, friednCapSkill);
			
			//服务端的计算结果
			List<BattleCheckReslut> serverBattleResult = new ArrayList<>();
			for(BattleCheckVO battleCheckVO : checkVO.getCheckList()){
				BattleCheckReslut calServerResult = calServerResult(user, attCapSkill, friednCapSkill, robotCapSkill, checkVO, battleCheckVO);
				if(calServerResult == null){
					return;
				}
				serverBattleResult.add(calServerResult);
			}
			
			//比较客户端与服务端的计算结果是否都在合理范围内
			boolean doubt = compareClientServerResult(checkVO.getCheckList(), serverBattleResult);
			
			if(doubt){
				createDoubtCheckLog(userId, 0, JSONUtil.toJson(checkVO), JSONUtil.toJson(serverBattleResult), "数值校验不一致");
				
			}else{
				//设置验证通过
				UserFightCheck userFightCheck = fightCheckDAO.getUserFightCheck(userId);
				if(userFightCheck == null){
					userFightCheck = new UserFightCheck();
					userFightCheck.setUserId(userId);
					userFightCheck.setBattleType(BattleType.valueOf(checkVO.getBattleType()));
					userFightCheck.setPass(true);
					fightCheckDAO.addUserFightCheck(userFightCheck);
				}else{
					userFightCheck.setBattleType(BattleType.valueOf(checkVO.getBattleType()));
					userFightCheck.setPass(true);
					fightCheckDAO.updateUserFightCheck(userFightCheck);
				}
				
				// TODO 测试
//				createDoubtCheckLog(userId, 0, JSONUtil.toJson(checkVO), JSONUtil.toJson(serverBattleResult), "测试查看数据");
			}
		} catch (Exception e) {
			logger.error(e.getCause());
			createDoubtCheckLog(userId, 0, JSONUtil.toJson(checkVO), e.getMessage(), "未知业务异常");
		}
	}
	
	
	/**
	 * 记录可疑日志
	 * @param userId
	 * @param commiter
	 * @param ClientData
	 * @param serverData
	 * @param reason
	 */
	private void createDoubtCheckLog(int userId, int commiter, String clientData, String serverData, String reason){
		UserDoubtCheckLog doubtCheckLog = new UserDoubtCheckLog();
		doubtCheckLog.setUserId(userId);
		doubtCheckLog.setCommiter(commiter);
		doubtCheckLog.setCreateTime(new Date());
		doubtCheckLog.setClientData(clientData==null? "" : clientData);
		doubtCheckLog.setServerData(serverData==null? "" : serverData);
		doubtCheckLog.setReason(reason);
		doubtCheckLogDAO.addDoubtLog(doubtCheckLog);
	}
	
	
	/**
	 * 比较数据，判断是否可疑
	 * @param cilentCheckList
	 * @param serverBattleResult
	 * @return
	 */
	private boolean compareClientServerResult(List<BattleCheckVO> cilentCheckList, List<BattleCheckReslut> serverBattleResult){
		//是否存在可疑数据
		boolean isdoubt = false;
		for(int i=0; i<cilentCheckList.size(); i++){
			
			BattleCheckVO clientBattleCheckVO = cilentCheckList.get(i);
			BattleCheckReslut serverBattleCheckReslut = serverBattleResult.get(i);
			
			//误差范围
			CheckConfig checkConfig = GameCache.getCheckConfig();
			
			if(clientBattleCheckVO.getAttack() > (int)(serverBattleCheckReslut.getAttack() * checkConfig.getAttRange())){
				isdoubt = true;break;
			}
			
			if(clientBattleCheckVO.getHp() > (int)(serverBattleCheckReslut.getHp() * checkConfig.getHpRange())){
				isdoubt = true;break;
			}
			
			if(clientBattleCheckVO.getDefense() > (int)(serverBattleCheckReslut.getDefense() * checkConfig.getDefRange())){
				isdoubt = true;break;
			}
			
			if(clientBattleCheckVO.getReply() > (int)(serverBattleCheckReslut.getReply() * checkConfig.getReplyRange())){
				isdoubt = true;break;
			}
			
			if(clientBattleCheckVO.getSkillId() > 0){
				if(clientBattleCheckVO.getDamageValue() > (int)(serverBattleCheckReslut.getDamageValue() * checkConfig.getSkillDamageRange())){
					isdoubt = true;break;
				}
			}else{
				if(clientBattleCheckVO.getDamageValue() > (int)(serverBattleCheckReslut.getDamageValue() * checkConfig.getNoSkillDamageRange())){
					isdoubt = true;break;
				}
			}
		}
		return isdoubt;
	}
	
	
	/**
	 * 获取服务端的计算结果
	 * @param user
	 * @param backage
	 * @param attCapSkill
	 * @param battleCheckVO
	 * @return
	 */
	private BattleCheckReslut calServerResult(User user, CapSkill attCapSkill,CapSkill friednCapSkill,CapSkill robotCapSkill, CheckVO checkVO, BattleCheckVO battleCheckVO){
		//获取战魂
		UserSoul userSoul;
		//机器人
		if(!battleCheckVO.isMysoul() && battleCheckVO.getUserSoulId() <= 0){
			userSoul = new UserSoul();
			userSoul.setSoulId(battleCheckVO.getSoulId());
			userSoul.setLevel(battleCheckVO.getLevel());
			userSoul.setSkillLv(1);
			userSoul.setSoulType(1);
		//好友战魂
		}else if(!battleCheckVO.isMysoul() && battleCheckVO.getUserSoulId() > 0){
			userSoul = userSoulService.getExistUserSoul(checkVO.getFriendId(), battleCheckVO.getUserSoulId());
		//自己的战魂
		}else{
			userSoul = userSoulService.getExistUserSoul(user.getUserId(), battleCheckVO.getUserSoulId());
		}
		
		//获取当前战魂佩戴的武器
		Backage backage = userGoodsService.getPackage(user.getUserId());
		List<UserGoods> equipments = new ArrayList<UserGoods>();
		if(userSoul.getUserId() == user.getUserId()){
			for (UserGoods goods : backage.getUseGoodses().values()) {
				if (goods.getUserSoulId() == userSoul.getId() && goods.getGoodsType() == UserGoods.GOODS_TYPE_EQUIPMENT) {
					equipments.add(goods);
				}
			}
		}
		
		
		//假如是好友的战魂
		if(userSoul.getUserId() != user.getUserId() && userSoul.getUserId() != 0){
			Backage friendBackage = userGoodsService.getPackage(checkVO.getFriendId());
			for (UserGoods goods : friendBackage.getUseGoodses().values()) {
				if (goods.getUserSoulId() == userSoul.getId() && goods.getGoodsType() == UserGoods.GOODS_TYPE_EQUIPMENT) {
					equipments.add(goods);
				}
			}
		}
		
		SimpleFightModel simpleFightModel = getSimpleFightModel(attCapSkill, friednCapSkill, robotCapSkill, userSoul, equipments, battleCheckVO.getSkillId());
		Monster monster = GameCache.getMonster(battleCheckVO.getMonsterId());
		
		// 世界boss鼓舞加成
		if(checkVO.getBattleType() == BattleType.BOSS.ordinal()){
			//鼓舞加成
			BossOpenSetting first = GameCache.getAllBossOpenSetting().iterator().next();
			UserBossRecord userBossRecord = userBossDAO.getUserBossRecord(user.getUserId(), first.getBossId());
			
			double addAttPercent = ((double)userBossRecord.getInspiredValue())/100;
			
			simpleFightModel.setAttack((int) (simpleFightModel.getAttack()*(1 + addAttPercent)));
		}
		
		//服务端计算伤害值
		int damage = calculationDamage(simpleFightModel, monster, battleCheckVO.isCrit());
		
		return BattleCheckReslut.valueOf(simpleFightModel, battleCheckVO.getMonsterId(), battleCheckVO.getSkillId(), battleCheckVO.isCrit(), damage);
	}
	
	
	/**
	 * 计算攻击伤害
	 * @param simpleFightModel
	 * @param monster
	 * @param crit
	 * @return
	 */
	private int calculationDamage(SimpleFightModel simpleFightModel, Monster monster, boolean crit){
		
		int damge = 0;
        float percentage = 1;
        float atk = (float)simpleFightModel.getAttack();
        float def = (float)monster.getDef();
        int restraint = RestraintType(simpleFightModel.getSoul().getSoulEle(), monster.getEle());
        if (restraint == 1)
        {
            percentage = 1.5f;
        }
        else if (restraint == 2)
        {
            percentage = 0.5f;
        }
        damge = (int)((30*atk/def) * percentage);
        if (damge <= 0){
        	 damge = 0;
        }
           
        damge = damge + 20;
        
        if(crit){
        	damge = (int) (damge * 1.25);
        }
        
        return damge;
		
	}
	
    /// <summary>
    /// 属性相克
    /// </summary>
    /// <param namelbl="type1"></param>
    /// <param namelbl="type2"></param>
    /// <returns>0=不相克 1=克制 2=被克</returns>
    public static int RestraintType(int type1, int type2)
    {
        switch (type1)
        {
            case 1:
                if (type2 == 2)
                {
                    return 1;
                }
                else if (type2 == 4)
                {
                    return 2;
                }
                break;
            case 2:
                if (type2 == 3)
                {
                    return 1;
                }
                else if (type2 == 1)
                {
                    return 2;
                }
                break;
            case 3:
                if (type2 == 4)
                {
                    return 1;
                }
                else if (type2 == 2)
                {
                    return 2;
                }
                break;
            case 4:
                if (type2 == 1)
                {
                    return 1;
                }
                else if (type2 == 3)
                {
                    return 2;
                }
                break;
            case 5:
                if (type2 == 6)
                {
                    return 1;
                }
                break;
            case 6:
                if (type2 == 5)
                {
                    return 1;
                }
                break;
        }
        return 0;
    } 

	
	/**
	 * 计算攻击战魂的各项属性
	 * @param capSkill
	 * @param userSoul
	 * @param equipments
	 * @return
	 */
	private SimpleFightModel getSimpleFightModel(CapSkill capSkill, CapSkill friednCapSkill, CapSkill robotCapSkill, UserSoul userSoul, List<UserGoods> equipments, int activeSkillId){
		
		SimpleFightModel simpleFightModel = new SimpleFightModel();
		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		simpleFightModel.setSoul(soul);
		simpleFightModel.setUserSoulId(userSoul.getId());
		
		//计算基础属性
		int baseHp = arenaService.calAttributesValue(userSoul, 0);
		int baseAtt = arenaService.calAttributesValue(userSoul, 1);
		int baseDef = arenaService.calAttributesValue(userSoul, 2);
		int baseRep = arenaService.calAttributesValue(userSoul, 3);
		
		//装备技能或队长技能属性增益
		int addHp = 0;  double addHpPercent = 0;
		int addAtt = 0; double addAttPercent = 0;
		int addDef = 0; double addDefPercent = 0;
		int addRep = 0; double addRepPercent = 0;
		for (UserGoods goods : equipments){
			//装备技能
			Equipment eq = GameCache.getEquipment(goods.getGoodsId());
			for (EquipmentEffect e : eq.getEffects()) {
				switch (e.getEffectType()) {
				case EquipmentEffect.EFFECT_TYPE_加血:
					addHp += e.getAddPoint();
					addHpPercent += e.getAddPercent();
					break;
				case EquipmentEffect.EFFECT_TYPE_攻击:
					addAtt += e.getAddPoint();
					addAttPercent += e.getAddPercent(); 
					break;
				case EquipmentEffect.EFFECT_TYPE_防御:
					addDef += e.getAddPoint();
					addDefPercent += e.getAddPercent(); 
					break;
				case EquipmentEffect.EFFECT_TYPE_回复力:
					addRep += e.getAddPoint();
					addRepPercent += e.getAddPercent(); 
					break;
				default:
					break;
				}
			}
		}
		
		//队长技能属性增益
		if(capSkill != null){
			for (CapSkillEffect effect : capSkill.getEffects()) {
				if (effect.getTargetEle() != 0) {
					if (soul.getSoulEle() != effect.getTargetEle()) {
						continue;
					}
				}
				switch (effect.getEffectType()) {
				case SkillEffect.EFFETC_TYPE_增加_减少血量上限:
					addHp += effect.getAddPoint();
					addHpPercent += effect.getAddPercent();
					break;
				case SkillEffect.EFFETC_TYPE_攻击:
					addAtt += effect.getAddPoint();
					addAttPercent += effect.getAddPercent(); 
					break;
				case SkillEffect.EFFETC_TYPE_防御:
					addDef += effect.getAddPoint();
					addDefPercent += effect.getAddPercent(); 
					break;
				case SkillEffect.EFFETC_TYPE_回复力:
					addRep += effect.getAddPoint();
					addRepPercent += effect.getAddPercent(); 
					break;

				default:
					break;
				}
			}
		}
		
		//好友队长技能属性增益
		if(friednCapSkill != null){
			for (CapSkillEffect effect : friednCapSkill.getEffects()) {
				if (effect.getTargetEle() != 0) {
					if (soul.getSoulEle() != effect.getTargetEle()) {
						continue;
					}
				}
				switch (effect.getEffectType()) {
				case SkillEffect.EFFETC_TYPE_增加_减少血量上限:
					addHp += effect.getAddPoint();
					addHpPercent += effect.getAddPercent();
					break;
				case SkillEffect.EFFETC_TYPE_攻击:
					addAtt += effect.getAddPoint();
					addAttPercent += effect.getAddPercent(); 
					break;
				case SkillEffect.EFFETC_TYPE_防御:
					addDef += effect.getAddPoint();
					addDefPercent += effect.getAddPercent(); 
					break;
				case SkillEffect.EFFETC_TYPE_回复力:
					addRep += effect.getAddPoint();
					addRepPercent += effect.getAddPercent(); 
					break;

				default:
					break;
				}
			}
		}
		
		//好友队长技能属性增益
		if(robotCapSkill != null){
			for (CapSkillEffect effect : robotCapSkill.getEffects()) {
				if (effect.getTargetEle() != 0) {
					if (soul.getSoulEle() != effect.getTargetEle()) {
						continue;
					}
				}
				switch (effect.getEffectType()) {
				case SkillEffect.EFFETC_TYPE_增加_减少血量上限:
					addHp += effect.getAddPoint();
					addHpPercent += effect.getAddPercent();
					break;
				case SkillEffect.EFFETC_TYPE_攻击:
					addAtt += effect.getAddPoint();
					addAttPercent += effect.getAddPercent(); 
					break;
				case SkillEffect.EFFETC_TYPE_防御:
					addDef += effect.getAddPoint();
					addDefPercent += effect.getAddPercent(); 
					break;
				case SkillEffect.EFFETC_TYPE_回复力:
					addRep += effect.getAddPoint();
					addRepPercent += effect.getAddPercent(); 
					break;

				default:
					break;
				}
			}
		}
		
		//主动技能
		ActiveSkill activeSkill = GameCache.getActiveSkill(activeSkillId);
		if(activeSkill != null){
			for (ActiveSkillEffect effect : activeSkill.getEffects()) {
				if (effect.getLevel() == userSoul.getSkillLv()) {
					switch (effect.getEffectType()) {
					case SkillEffect.EFFETC_TYPE_加血_扣血:
						addHp += effect.getAddPoint();
						addHpPercent += effect.getAddPercent();
						break;
					case SkillEffect.EFFETC_TYPE_攻击:
						addAtt += effect.getAddPoint();
						addAttPercent += effect.getAddPercent(); 
						break;
					case SkillEffect.EFFETC_TYPE_防御:
						addDef += effect.getAddPoint();
						addDefPercent += effect.getAddPercent(); 
						break;
					case SkillEffect.EFFETC_TYPE_回复力:
						addRep += effect.getAddPoint();
						addRepPercent += effect.getAddPercent(); 
						break;

					default:
						break;
					}
				}
			}
		}
		
		simpleFightModel.setHp((int) (baseHp + addHp + (addHpPercent * baseHp)));
		simpleFightModel.setAttack((int) (baseAtt + addAtt + (addAttPercent * baseAtt)));
		simpleFightModel.setDefense((int) (baseDef + addDef + (addDefPercent * baseDef)));
		simpleFightModel.setReply((int) (baseRep + addRep + (addRepPercent * baseRep)));
		
		return simpleFightModel;
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
	public void reportDoubtLog(int userId, String clientData) {
		UserDoubtCheckLog doubtCheckLog = new UserDoubtCheckLog();
		doubtCheckLog.setUserId(userId);
		doubtCheckLog.setCommiter(COMMITER_CLIENT);
		doubtCheckLog.setCreateTime(new Date());
		doubtCheckLog.setClientData(clientData);
		doubtCheckLog.setReason("修改内存数据");
		doubtCheckLogDAO.addDoubtLog(doubtCheckLog);
	}


	@Override
	public void replay(int userId, int logId) {

		UserDoubtCheckLog doubtLog = doubtCheckLogDAO.getDoubtLogById(logId);
		
		CheckVO object = JSONUtil.toObject(doubtLog.getClientData(), new TypeReference<CheckVO>() {});
		
		check(userId, object);
		
	}


	@Override
	public boolean isPassCheck(int userId, BattleType battleType) {
		
		if(!GameCache.getCheckConfig().isOpen()){
			return true;
		}
		
		UserFightCheck userFightCheck = fightCheckDAO.getUserFightCheck(userId);
		if(userFightCheck == null){
			return false;
		}
		
		if(!userFightCheck.getBattleType().equals(battleType)){
			return false;
		}
		
		if(!userFightCheck.isPass()){
			return false;
		}
		return true;
	}


	@Override
	public void clearPassCheck(int userId) {
		UserFightCheck userFightCheck = fightCheckDAO.getUserFightCheck(userId);
		if(userFightCheck != null){
			userFightCheck.setPass(false);
			fightCheckDAO.updateUserFightCheck(userFightCheck);
		}
	}


	@Override
	public CheckConfig loadConfig() {
		return checkConfigDAO.queryCheckConfig();
	}
}

