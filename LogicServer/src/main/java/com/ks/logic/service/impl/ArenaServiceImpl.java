package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ks.logic.cache.GameCache;
import com.ks.logic.service.ArenaService;
import com.ks.logic.service.BaseService;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentEffect;
import com.ks.model.fight.FightBuff;
import com.ks.model.fight.FightModel;
import com.ks.model.fight.FightSkill;
import com.ks.model.fight.FightSkillEffect;
import com.ks.model.goods.Backage;
import com.ks.model.goods.UserGoods;
import com.ks.model.skill.ActiveSkill;
import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillEffect;
import com.ks.model.soul.Soul;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.fight.AttackBoutVO;
import com.ks.protocol.vo.fight.AttackDropVO;
import com.ks.protocol.vo.fight.AttackRoundVO;
import com.ks.protocol.vo.fight.AttackVO;
import com.ks.protocol.vo.fight.BuffImpactVO;
import com.ks.protocol.vo.fight.FightBuffVO;
import com.ks.protocol.vo.fight.FightModelVO;
import com.ks.protocol.vo.fight.FightVO;

public class ArenaServiceImpl extends BaseService implements ArenaService {

	private static final int ATTACK_TYPE_普通攻击 = 1;
	private static final int ATTACK_TYPE_暴击 = 2;
	private static final int ATTACK_TYPE_加血 = 3;
	private static final int ATTACK_TYPE_增加buff = 4;
	private static final int ATTACK_TYPE_解除buff = 5;
	private static final int ATTACK_TYPE_增加技能值 = 6;

	@Override
	public FightVO fighting(int attackerId, int defenderId) {
		User attacker = userService.getExistUserCache(attackerId);
		User defender = userService.getExistUser(defenderId);

		UserTeam attackerTeam = userTeamService.getExistUserTeamCache(attacker.getUserId(), attacker.getCurrTeamId());
		UserTeam defenderTeam = userTeamService.getExistUserTeam(defender.getUserId(), defender.getCurrTeamId());

		List<UserSoul> attackerSoul = new ArrayList<>();
		CapSkill attCapSkill = null;
		for (byte pos = 0; pos < attackerTeam.getPos().size(); pos++) {
			long userSoulId = attackerTeam.getPos().get(pos);
			if (userSoulId != 0) {
				UserSoul userSoul = userSoulService.getExistUserSoulCache(attackerId, userSoulId);
				userSoul.setPos(pos);
				attackerSoul.add(userSoul);
				if (pos == attackerTeam.getCap()) {
					Soul soul = GameCache.getSoul(userSoul.getSoulId());
					if (soul.getCapSkill() != 0) {
						attCapSkill = GameCache.getCapSkill(soul.getCapSkill());
					}
				}
			}
		}

		attCapSkill = checkCapSkill(attackerSoul, attCapSkill);

		List<UserSoul> defenderSoul = new ArrayList<>();
		CapSkill defCapSkill = null;
		for (byte pos = 0; pos < defenderTeam.getPos().size(); pos++) {
			long userSoulId = defenderTeam.getPos().get(pos);
			if (userSoulId != 0) {
				UserSoul userSoul = userSoulService.getExistUserSoul(defenderId, userSoulId);
				userSoul.setPos(pos);
				defenderSoul.add(userSoul);
				if (pos == attackerTeam.getCap()) {
					Soul soul = GameCache.getSoul(userSoul.getSoulId());
					if (soul.getCapSkill() != 0) {
						defCapSkill = GameCache.getCapSkill(soul.getCapSkill());
					}
				}
			}
		}

		defCapSkill = checkCapSkill(defenderSoul, defCapSkill);

		List<FightModel> attackerModel = new ArrayList<FightModel>();
		int fightId = 1;
		Backage backage = userGoodsService.getPackage(attackerId);
		for (UserSoul userSoul : attackerSoul) {
			List<UserGoods> equipments = new ArrayList<UserGoods>();
			for (UserGoods goods : backage.getUseGoodses().values()) {
				if (goods.getUserSoulId() == userSoul.getId() && goods.getGoodsType() == UserGoods.GOODS_TYPE_EQUIPMENT) {
					equipments.add(goods);
				}
			}
			FightModel fm = createFightModel(userSoul, fightId, equipments, attCapSkill);
			attackerModel.add(fm);
			fightId++;
		}

		List<FightModel> defenderModel = new ArrayList<FightModel>();
		List<UserGoods> goodses = userGoodsService.gainUserGoods(defenderId);
		for (UserSoul userSoul : defenderSoul) {
			List<UserGoods> equipments = new ArrayList<UserGoods>();
			for (UserGoods goods : goodses) {
				if (goods.getUserSoulId() == userSoul.getId() && goods.getGoodsType() == UserGoods.GOODS_TYPE_EQUIPMENT) {
					equipments.add(goods);
				}
			}
			FightModel fm = createFightModel(userSoul, fightId, equipments, defCapSkill);
			defenderModel.add(fm);
			fightId++;
		}

		FightVO fight = fighting(attackerModel, defenderModel);
		return fight;
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
	
	/**
	 * 计算各属性值
	 * @param userSoul
	 * @param valueType
	 * 0 生命
	 * 1 攻击
	 * 2 防御
	 * 3 恢复
	 * @return
	 */
	public int calAttributesValue(UserSoul userSoul, int valueType){
		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		switch (valueType) {
		case 0:
			return getSoulEle(soul.getHp(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[valueType], soul.getSoulRare(), soul.getGrowthFactor());
		case 1:
			return getSoulEle(soul.getAtk(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[valueType], soul.getSoulRare(), soul.getGrowthFactor());
		case 2:
			return getSoulEle(soul.getDef(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[valueType], soul.getSoulRare(), soul.getGrowthFactor());
		case 3:
			return getSoulEle(soul.getRep(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[valueType], soul.getSoulRare(), soul.getGrowthFactor());
		default:
			break;
		}
		return 0;
	}
	

	private int getSoulEle(int ele, int level, double typeAdd, int rare, double elePoint) {
		// 升级后属性数值=初始属性值+(等级-1)*属性性格因子*(0.5*战魂稀有度-0.4）*属性因子
		// return (int) (ele + (level-1) * (typeAdd * 1.1 + rare * 1.5) *
		// elePoint);
		return (int) (ele + (level - 1) * typeAdd * (0.5 * rare - 0.4) * elePoint);
	}

	private FightModel createFightModel(UserSoul userSoul, int fightId, List<UserGoods> equipments, CapSkill capSkill) {
		Soul soul = GameCache.getSoul(userSoul.getSoulId());
		FightModel fm = new FightModel();
		fm.setFightId(fightId);
		fm.setUserId(userSoul.getUserId());
		fm.setUserSoulId(userSoul.getId());
		fm.setSoulId(soul.getSoulId());
		fm.setPos(userSoul.getPos());
		fm.setAnger(5);
		fm.setHit(soul.getAttackHit());
		fm.setSoulEle(soul.getSoulEle());
		fm.setBuffs(new ArrayList<FightBuff>());
		fm.setLevel(userSoul.getLevel());
		fm.setMaxHp(getSoulEle(soul.getHp(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[0], soul.getSoulRare(), soul.getGrowthFactor()));
		fm.setAtt(getSoulEle(soul.getAtk(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[1], soul.getSoulRare(), soul.getGrowthFactor()));
		fm.setDef(getSoulEle(soul.getDef(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[2], soul.getSoulRare(), soul.getGrowthFactor()));
		fm.setRep(getSoulEle(soul.getRep(), userSoul.getLevel(), Soul.getSoulTypeAddition(userSoul.getSoulType())[3], soul.getSoulRare(), soul.getGrowthFactor()));
		ActiveSkill skill = GameCache.getActiveSkill(soul.getSkill());
		if (skill != null) {
			FightSkill fs = new FightSkill();
			fs.setEffects(new ArrayList<FightSkillEffect>());
			fs.setSkillId(skill.getSkillId());
			for (ActiveSkillEffect e : skill.getEffects()) {
				if (e.getLevel() == userSoul.getSkillLv()) {
					Soul soulHit = GameCache.getSoul(userSoul.getSoulId());
					FightSkillEffect effect = new FightSkillEffect();
					effect.init(e, soulHit.getSkillHit());
					fs.getEffects().add(effect);
				}
			}
			fm.setSkill(fs);
		}
		int addHpPoint = 0;
		double addHpPercent = 0;
		for (UserGoods goods : equipments) {
			//精炼卷轴技能
			ActiveSkill refiningSkill = GameCache.getActiveSkill(goods.getEqSkillId());
			if(refiningSkill != null){
				for(ActiveSkillEffect e : refiningSkill.getEffects()){
					if(e.getLevel() == goods.getEqSkillLevel()){
						FightSkillEffect effect = new FightSkillEffect();
						effect.init(e, 1);
						fm.getSkill().getEffects().add(effect);
					}
				}
			}
			
			//装备技能
			Equipment eq = GameCache.getEquipment(goods.getGoodsId());
			for (EquipmentEffect e : eq.getEffects()) {
				switch (e.getEffectType()) {
				case EquipmentEffect.EFFECT_TYPE_加血:
					addHpPoint += e.getAddPoint();
					addHpPercent += e.getAddPercent();
					break;
				case EquipmentEffect.EFFECT_TYPE_攻击:
					FightBuff buff = new FightBuff(SkillEffect.EFFETC_TYPE_攻击, e.getAddPoint(), e.getAddPercent(), 0, 1);
					fm.getBuffs().add(buff);
					break;
				case EquipmentEffect.EFFECT_TYPE_防御:
					buff = new FightBuff(SkillEffect.EFFETC_TYPE_攻击, e.getAddPoint(), e.getAddPercent(), 0, 1);
					fm.getBuffs().add(buff);
					break;
				default:
					break;
				}
			}
		}

		if (capSkill != null) {
			for (CapSkillEffect effect : capSkill.getEffects()) {
				if (effect.getTargetEle() != 0) {
					if (soul.getSoulEle() != effect.getTargetEle()) {
						continue;
					}
				}
				switch (effect.getEffectType()) {
				case SkillEffect.EFFETC_TYPE_加血_扣血:
					addHpPoint += effect.getAddPoint();
					addHpPercent += effect.getAddPercent();
					break;

				default:
					FightBuff buff = new FightBuff(effect.getEffectType(), effect.getAddPoint(), effect.getAddPercent(), 0, effect.getSuccessRate());
					fm.getBuffs().add(buff);
					break;
				}
			}
		}

		fm.setMaxHp((int) (fm.getMaxHp() + addHpPoint + (fm.getMaxHp() * addHpPercent)));
		fm.setHp(fm.getMaxHp());
		return fm;
	}

	/** 水晶掉率 */
	private static final double[][] CRY_DROP = new double[][] { { 0.300, 0.800 }, { 0.050, 0.133 } };

	private FightVO fighting(List<FightModel> attackerModel, List<FightModel> defenderModel) {
		FightVO fight = MessageFactory.getMessage(FightVO.class);
		fight.setBouts(new ArrayList<AttackBoutVO>());
		fight.setAttackers(new ArrayList<FightModelVO>());
		for (FightModel fm : attackerModel) {
			FightModelVO vo = MessageFactory.getMessage(FightModelVO.class);
			vo.init(fm);
			fight.getAttackers().add(vo);
		}
		fight.setDefenders(new ArrayList<FightModelVO>());
		for (FightModel fm : defenderModel) {
			FightModelVO vo = MessageFactory.getMessage(FightModelVO.class);
			vo.init(fm);
			fight.getDefenders().add(vo);
		}
		int round = 0;

		List<FightModel> attModel = attackerModel;// 攻击方战斗模型
		List<FightModel> defModel = defenderModel;// 防守方战斗模型

		while (round < 60) {
			int num = 0;
			AttackBoutVO bout = MessageFactory.getMessage(AttackBoutVO.class);
			fight.getBouts().add(bout);
			bout.setRounds(new ArrayList<AttackRoundVO>());
			bout.setBuffImpacts(new ArrayList<BuffImpactVO>());
			bout.setDrops(new ArrayList<AttackDropVO>());

			out: for (FightModel att : attModel) {
				if (defModel.size() == 0 || attModel.size() == 0) {// 一方全部死亡
					break;
				}
				for (FightBuff buff : att.getBuffs()) {
					if (buff.getBuffId() == SkillEffect.EFFETC_TYPE_麻痹) {
						continue out;
					}
				}

				AttackRoundVO attRound = MessageFactory.getMessage(AttackRoundVO.class);
				attRound.setFightId(att.getFightId());
				attRound.setAttacks(new ArrayList<AttackVO>());
				bout.getRounds().add(attRound);
				if (att.getAnger() >= 10 && 0.5 > Math.random() && att.getSkill() != null && !hasCurse(att)) {// 释放技能
					att.setAnger(0);
					FightSkill skill = att.getSkill();
					attRound.setSkillId(skill.getSkillId());
					for (FightSkillEffect effect : skill.getEffects()) {
						if (effect.getSuccessRate() < Math.random()) {
							continue;
						}
						if (defModel.size() == 0 || attModel.size() == 0) {// 一方全部死亡
							break;
						}
						List<FightModel> target = selectTarget(attModel, defModel, att, effect);
						switch (effect.getEffectType()) {
						case SkillEffect.EFFETC_TYPE_加血_扣血:
							for (FightModel fm : target) {
								if (effect.getAddPoint() >= 0 && effect.getAddPercent() >= 0) {
									int hp = fm.getHp();
									int addHp = (int) (effect.getAddPoint() + fm.getMaxHp() * effect.getAddPercent());
									hp += addHp;
									hp = fm.getMaxHp() > hp ? hp : fm.getMaxHp();
									fm.setHp(hp);

									AttackVO attVO = MessageFactory.getMessage(AttackVO.class);
									attVO.setFightId(fm.getFightId());
									attVO.setAttackType(ATTACK_TYPE_加血);
									attVO.setHurt(addHp);
									attVO.setHp(fm.getHp());
									attRound.getAttacks().add(attVO);

								}
							}
							break;

						case SkillEffect.EFFETC_TYPE_虚弱:
						case SkillEffect.EFFETC_TYPE_疾病:
						case SkillEffect.EFFETC_TYPE_负伤:
						case SkillEffect.EFFETC_TYPE_麻痹:
						case SkillEffect.EFFETC_TYPE_诅咒:
						case SkillEffect.EFFETC_TYPE_中毒:
							for (FightModel fm : target) {
								boolean flag = true;
								for (FightBuff buff : fm.getBuffs()) {
									if (buff.getBuffId() == effect.getEffectType() - 13 || buff.getBuffId() == SkillEffect.EFFETC_TYPE_免疫所有异常状态) {
										if (Math.random() < buff.getSuccessRate()) {
											flag = false;
											break;
										}
									}
								}
								if (flag) {
									addBuff(attRound, effect, fm);
								}
							}
							break;
						case SkillEffect.EFFETC_TYPE_免疫虚弱:
						case SkillEffect.EFFETC_TYPE_免疫疾病:
						case SkillEffect.EFFETC_TYPE_免疫负伤:
						case SkillEffect.EFFETC_TYPE_免疫麻痹:
						case SkillEffect.EFFETC_TYPE_免疫诅咒:
						case SkillEffect.EFFETC_TYPE_免疫中毒:
							for (FightModel fm : target) {
								Iterator<FightBuff> it = fm.getBuffs().iterator();
								while (it.hasNext()) {
									FightBuff buff = it.next();
									if (buff.getBuffId() == effect.getEffectType() + 13) {
										it.remove();
									}
								}
								addBuff(attRound, effect, fm);
							}
							break;
						case SkillEffect.EFFETC_TYPE_免疫所有异常状态:
							for (FightModel fm : target) {
								Iterator<FightBuff> it = fm.getBuffs().iterator();
								while (it.hasNext()) {
									FightBuff buff = it.next();
									if (buff.getBuffId() == SkillEffect.EFFETC_TYPE_虚弱 || buff.getBuffId() == SkillEffect.EFFETC_TYPE_疾病 || buff.getBuffId() == SkillEffect.EFFETC_TYPE_负伤
											|| buff.getBuffId() == SkillEffect.EFFETC_TYPE_麻痹 || buff.getBuffId() == SkillEffect.EFFETC_TYPE_诅咒 || buff.getBuffId() == SkillEffect.EFFETC_TYPE_中毒) {
										it.remove();
									}
								}
								addBuff(attRound, effect, fm);
							}
							break;
						case SkillEffect.EFFETC_TYPE_技能值增长:
							for (FightModel fm : target) {
								fm.setAnger(fm.getAnger() + effect.getAddPoint());

								AttackVO attVO = MessageFactory.getMessage(AttackVO.class);
								attVO.setFightId(fm.getFightId());
								attVO.setAttackType(ATTACK_TYPE_增加技能值);
								attVO.setHurt(effect.getAddPoint());
								attVO.setHp(fm.getHp());
								attRound.getAttacks().add(attVO);
							}
							break;
						case SkillEffect.EFFETC_TYPE_攻击:
						case SkillEffect.EFFETC_TYPE_防御:
						case SkillEffect.EFFETC_TYPE_回复力:
						case SkillEffect.EFFETC_TYPE_减少伤害:
						case SkillEffect.EFFETC_TYPE_水晶掉率:
							for (FightModel fm : target) {
								addBuff(attRound, effect, fm);
							}
							break;
						case SkillEffect.EFFETC_TYPE_解除虚弱:
						case SkillEffect.EFFETC_TYPE_解除疾病:
						case SkillEffect.EFFETC_TYPE_解除负伤:
						case SkillEffect.EFFETC_TYPE_解除麻痹:
						case SkillEffect.EFFETC_TYPE_解除诅咒:
						case SkillEffect.EFFETC_TYPE_解除中毒:
							for (FightModel fm : target) {
								Iterator<FightBuff> it = fm.getBuffs().iterator();
								while (it.hasNext()) {
									FightBuff buff = it.next();
									if (buff.getBuffId() == effect.getEffectType() - 7) {
										it.remove();
									}
								}
								AttackVO attVO = MessageFactory.getMessage(AttackVO.class);
								attVO.setFightId(fm.getFightId());
								attVO.setAttackType(ATTACK_TYPE_解除buff);
								attVO.setHurt(effect.getEffectType() - 7);
								attVO.setHp(fm.getHp());
								attRound.getAttacks().add(attVO);
							}

							break;
						case SkillEffect.EFFETC_TYPE_火属性攻击:
						case SkillEffect.EFFETC_TYPE_土属性攻击:
						case SkillEffect.EFFETC_TYPE_气属性攻击:
						case SkillEffect.EFFETC_TYPE_水属性攻击:
						case SkillEffect.EFFETC_TYPE_光属性攻击:
						case SkillEffect.EFFETC_TYPE_暗属性攻击:
						case SkillEffect.EFFETC_TYPE_无属性攻击:
							for (FightModel fm : target) {

								AttackVO attVO = MessageFactory.getMessage(AttackVO.class);
								attVO.setFightId(fm.getFightId());
								attVO.setAttackType(ATTACK_TYPE_普通攻击);

								int hurt = calHurt((int) (effect.getAddPoint() + gainAtk(att) + att.getAtt() * effect.getAddPercent()), gainDef(fm), effect.getEffectType() - 32, fm.getSoulEle(),
										effect.getHit(), att.getLevel());

								if (Math.random() < att.getCritRate()) {// 出现暴击
									attVO.setAttackType(ATTACK_TYPE_暴击);
									hurt *= 1.5;
								}

								fm.setHp(fm.getHp() - hurt);
								if (fm.getHp() <= 0) {
									defModel.remove(fm);
								}
								attVO.setHp(fm.getHp());
								attVO.setHurt(hurt);
								attRound.getAttacks().add(attVO);

								int dropNum = 0;
								for (int i = 0; i < effect.getHit(); i++) {
									double random = Math.random();
									double rate = attVO.getAttackType() == ATTACK_TYPE_暴击 ? CRY_DROP[1][1] : CRY_DROP[1][0];
									if (rate > random) {
										dropNum++;
									}
								}
								attVO.setDropNum(dropNum);
								num += dropNum;
							}
							break;

						default:
							break;
						}
					}
				} else {
					FightModel def = defModel.get((int) (Math.random() * defModel.size()));

					AttackVO attVO = MessageFactory.getMessage(AttackVO.class);
					attVO.setFightId(def.getFightId());
					attVO.setAttackType(ATTACK_TYPE_普通攻击);
					attRound.getAttacks().add(attVO);

					int hurt = calHurt(gainAtk(att), gainDef(def), att.getSoulEle(), def.getSoulEle(), att.getHit(), att.getLevel());

					if (Math.random() < att.getCritRate()) {// 出现暴击
						attVO.setAttackType(ATTACK_TYPE_暴击);
						hurt *= 1.5;
					}
					attVO.setHurt(hurt);

					int hit = att.getHit();
					attRound.setHit(hit);

					int dropNum = 0;

					for (int i = 0; i < hit; i++) {
						double random = Math.random();
						double rate = attVO.getAttackType() == ATTACK_TYPE_暴击 ? CRY_DROP[0][1] : CRY_DROP[0][0];
						if (rate > random) {
							dropNum++;
						}
					}
					attVO.setDropNum(dropNum);
					num += dropNum;
					def.setHp(def.getHp() - hurt);

					attVO.setHp(def.getHp());

					if (def.getHp() <= 0) {
						defModel.remove(def);
					}
				}
			}
			calBuffImpact(attModel, bout);

			calDrop(attModel, num, bout);

			if (defModel.size() == 0 || attModel.size() == 0) {// 一方全部死亡
				break;
			}

			List<FightModel> temp = attModel;
			attModel = defModel;
			defModel = temp;
			round++;
		}
		fight.setAttWin(defenderModel.size() == 0);
		return fight;
	}

	private int gainDef(FightModel def) {
		int addDefPoint = 0;
		double addDefPercent = 0;
		for (FightBuff buff : def.getBuffs()) {
			switch (buff.getBuffId()) {
			case SkillEffect.EFFETC_TYPE_防御:
				addDefPoint += buff.getAddPoint();
				addDefPercent += buff.getAddPercent();
				break;
			case SkillEffect.EFFETC_TYPE_虚弱:
				addDefPercent += -0.1;
				break;
			default:
				break;
			}
		}
		int d = def.getDef();
		d += (d * addDefPercent) + addDefPoint;
		return d < 0 ? 1 : d;
	}

	private int gainAtk(FightModel att) {
		int addAtkPoint = 0;
		double addAtkPercent = 0;
		for (FightBuff buff : att.getBuffs()) {
			switch (buff.getBuffId()) {
			case SkillEffect.EFFETC_TYPE_攻击:
				addAtkPoint += buff.getAddPoint();
				addAtkPercent += buff.getAddPercent();
				break;
			case SkillEffect.EFFETC_TYPE_负伤:
				addAtkPercent += -0.15;
				break;
			default:
				break;
			}
		}
		int atk = att.getAtt();
		atk += (atk * addAtkPercent) + addAtkPoint;
		return atk < 0 ? 1 : atk;
	}

	/**
	 * 计算掉落
	 * 
	 * @param attModel
	 *            攻击方集合
	 * @param num
	 *            总掉落数量
	 * @param bout
	 *            攻击轮
	 */
	private void calDrop(List<FightModel> attModel, int num, AttackBoutVO bout) {
		List<AttackDropVO> drops = bout.getDrops();
		List<FightModel> fms = new ArrayList<FightModel>();
		for (FightModel fm : attModel) {
			if (!hasCurse(fm)) {
				fms.add(fm);
			}
		}
		if (fms.size() == 0) {
			return;
		}
		for (int i = 0; i < num; i++) {
			FightModel fm = fms.get((int) (Math.random() * fms.size()));
			fm.setAnger(fm.getAnger() + 1);
			AttackDropVO drop = null;
			for (AttackDropVO d : drops) {
				if (d.getFightId() == fm.getFightId()) {
					drop = d;
					break;
				}
			}
			if (drop == null) {
				drop = MessageFactory.getMessage(AttackDropVO.class);
				drop.setFightId(fm.getFightId());
				drops.add(drop);
			}
			drop.setNum(drop.getNum() + 1);
		}
	}

	private boolean hasCurse(FightModel fm) {
		for (FightBuff buff : fm.getBuffs()) {
			if (buff.getBuffId() == SkillEffect.EFFETC_TYPE_诅咒) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算buff影响
	 * 
	 * @param attModel
	 *            攻击方集合
	 * @param bout
	 *            攻击轮
	 */
	private void calBuffImpact(List<FightModel> attModel, AttackBoutVO bout) {
		Iterator<FightModel> modelIt = attModel.iterator();
		while (modelIt.hasNext()) {
			FightModel att = modelIt.next();
			Iterator<FightBuff> it = att.getBuffs().iterator();
			while (it.hasNext()) {
				FightBuff buff = it.next();
				if (buff.getRound() != 0) {
					buff.setRound(buff.getRound() - 1);
					if (buff.getRound() <= 0) {
						it.remove();
					}
				}
				switch (buff.getBuffId()) {
				case SkillEffect.EFFETC_TYPE_中毒:

					int val = (int) (att.getMaxHp() * 0.08);
					att.setHp(att.getHp() - val);

					BuffImpactVO vo = MessageFactory.getMessage(BuffImpactVO.class);
					vo.setBuffId(buff.getBuffId());
					vo.setFightId(att.getFightId());
					vo.setVal(val);
					vo.setHp(att.getHp());
					bout.getBuffImpacts().add(vo);
					if (att.getHp() <= 0) {
						modelIt.remove();
					}
					break;

				default:
					break;
				}
			}
		}
	}

	private void addBuff(AttackRoundVO attRound, FightSkillEffect effect, FightModel fm) {
		FightBuff buff = new FightBuff(effect.getEffectType(), effect.getAddPoint(), effect.getAddPercent(), effect.getRound(), effect.getSuccessRate());
		fm.getBuffs().add(buff);

		FightBuffVO vo = MessageFactory.getMessage(FightBuffVO.class);
		vo.init(buff);

		AttackVO attVO = MessageFactory.getMessage(AttackVO.class);
		attVO.setFightId(fm.getFightId());
		attVO.setAttackType(ATTACK_TYPE_增加buff);
		attVO.setHp(fm.getHp());
		attVO.setBuff(vo);
		attRound.getAttacks().add(attVO);
	}

	private List<FightModel> selectTarget(List<FightModel> attModel, List<FightModel> defModel, FightModel att, FightSkillEffect effect) {
		List<FightModel> target = new ArrayList<FightModel>();
		switch (effect.getTarget()) {
		case SkillEffect.TARGET_已方全体:
			for (FightModel fm : attModel) {
				if (effect.getTargetEle() != 0) {
					if (effect.getTargetEle() == fm.getSoulEle()) {
						target.add(fm);
					}
				} else {
					target.add(fm);
				}
			}
			break;
		case SkillEffect.TARGET_已方单体:
			target.add(att);
			break;
		case SkillEffect.TARGET_怪物单体:
			FightModel f = defModel.get((int) (Math.random() * defModel.size()));
			target.add(f);
			break;
		case SkillEffect.TARGET_怪物全体:
			for (FightModel fm : defModel) {
				if (effect.getTargetEle() != 0) {
					if (effect.getTargetEle() == fm.getSoulEle()) {
						target.add(fm);
					}
				} else {
					target.add(fm);
				}
			}
			break;
		default:
			break;
		}
		return target;
	}

	/** {(7*攻击方等级+52)*攻击方ATK}/{(7*攻击方等级+52)+受击方DEF}*属性相克 */
	private int calHurt(int atk, int def, int attEle, int defEle, int hit, int attLevel) {
		return Math.max(hit, (int) ((7 * attLevel + 52) * atk / ((7 * attLevel + 52) + def) * FightModel.getRestraint(attEle, defEle)));
	}

}
