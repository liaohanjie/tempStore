package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
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
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.model.user.User;
import com.ks.model.user.UserStat;
import com.ks.protocol.vo.mission.UserAwardVO;

/**
 * 物品
 * 
 * @author ks
 */
public interface GoodsService {
	/**
	 * 获得所有道具
	 * 
	 * @return 所有道具
	 */
	List<Prop> getAllProp();

	/**
	 * 获得所有道具效果
	 * 
	 * @return
	 */
	List<PropEffect> getItemEffects();

	/**
	 * 获得所有装备
	 * 
	 * @return 所有装备
	 */
	List<Equipment> getEquipments();

	/**
	 * 获得所有装备效果
	 * 
	 * @return 所有装备效果
	 */
	List<EquipmentEffect> getEquipmentEffects();

	/**
	 * 装备修复规则
	 * 
	 * @return
	 */
	List<EquipmentRepair> getEquipmentRepairs();

	/**
	 * 获得所有材料
	 * 
	 * @return 所有材料
	 */
	List<Stuff> getStuffs();

	/**
	 * 查询物品合成
	 * 
	 * @return 物品合成
	 */
	public List<GoodsSynthesis> queryGoodsSynthesis();

	/**
	 * 查询物品合成规则
	 * 
	 * @return 物品合成规则
	 */
	public List<GoodsSynthesisRule> queryGoodsSynthesisRule();

	/**
	 * 副本道具定义
	 * 
	 * @return
	 */
	public List<BakProp> queryBakProp();

	/**
	 * 查询战魂
	 * 
	 * @return 所有战魂
	 */
	public List<Soul> getSouls(SoulFilter soul);
	
	/**
	 * 得到战魂总数量
	 * @param soul
	 * @return
	 */
	public Integer getSoulsCount(SoulFilter soul);
	
	/**
	 * 查询怪物
	 * @param name
	 * @return
	 */
	public List<Monster> getMonster(MonsterFilter name);

	/**
	 * 得到怪物总数量
	 * @param name
	 * @return
	 */
	public Integer getMonsterCount(MonsterFilter name);
	/**
	 * 装备技能配置
	 * @return
	 */
	public List<EquipmentSkill> getEquimentSkillS();
	
	/**
	 * 发放物品
	 * @param list 物品信息
	 * @param user 用户信息
	 * @param loggerTypeId 日志记录类型
	 * @param logDesc 日志描述
	 * @return
	 */
	@Transaction
	@Deprecated
	UserAwardVO issueGoods(List<Goods> list, User user, int[] logTypeIds, String logDesc);

	/**
	 * 发放所有物品和道具
	 * @param goods
	 * @param user
	 * @param logType
	 * @param logDesc
	 */
	public IssueGoods issueGoods(Backage backage, UserStat stat, Goods goods, User user, int logType, String logDesc);

	/**
	 * 批量发放物品
	 * @param goodsList
	 * @param user
	 * @param logType
	 * @param logDesc
	 */
	IssueGoods issueGoods(List<Goods> goodsList, User user, int logType, String logDesc);
	
	/**
	 * 发放物品
	 * @param goodsList
	 * @param user
	 * @param logType
	 * @param logDesc
	 * @return
	 */
	UserAwardVO getUserAwardVO(List<Goods> goodsList, User user, int logType, String logDesc);
}
