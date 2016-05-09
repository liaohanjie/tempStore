package com.ks.logic.service;

import java.util.Collection;
import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.user.User;
import com.ks.protocol.vo.affiche.AfficheVO;
import com.ks.protocol.vo.goods.GainAwardVO;

public interface AfficheService {
	/**
	 * 增加公告
	 * 
	 * @param a
	 *            公告
	 */
	@Transaction
	Affiche addAffiche(Affiche a);

	/**
	 * 获取公告
	 * 
	 * @param userId
	 *            用户编号
	 * @return 所有公告
	 */
	@Transaction
	List<AfficheVO> gainAffiche(int userId);

	/**
	 * 删除公告
	 * 
	 * @param id
	 *            编号
	 * @param userId
	 *            用户编号
	 */
	@Transaction
	void deleteAffiche(int id, int userId);

	/**
	 * 领取邮件物品
	 * 
	 * @param userId
	 *            用户编号
	 * @param id
	 *            公告编号
	 * @return 查看结果
	 */
	@Transaction
	GainAwardVO getAfficheGoods(int userId, int id);

	/**
	 * 增加公告
	 * 
	 * @param a
	 *            公告
	 */
	@Transaction
	void addAffiches(List<Affiche> as);

	/**
	 * 兑换礼品卡奖励
	 * 
	 * @param userId
	 * @param code
	 * @param goodsList
	 */
	@Transaction
	public void useGiftCode(int userId, String code, List<Goods> goodsList);

	/**
	 * 获取公告
	 * 
	 * @param userId
	 *            用户编号
	 * @return 所有公告
	 */
	List<Affiche> queryAffiche(int userId);

	/**
	 * 查看所有邮件
	 * 
	 * @param userId
	 *            用户编号
	 * @param id
	 *            公告编号
	 * @return 查看结果
	 */
	@Transaction
	GainAwardVO getAllAfficheGoods(int userId);

	/**
	 * 邮件标记为已读
	 * 
	 * @param userId
	 * @param ids
	 */
	@Transaction
	void viewAllAffiche(int userId, Collection<Integer> ids);

	/**
	 * 清除过期邮件
	 */
	@Transaction
	void cleanAffiche();

	/**
	 * 获得物品
	 * 
	 * @param goodslist
	 *            物品list
	 * @param user用户
	 * @param backage
	 *            背包
	 * @param types
	 *            日志类型
	 * @param des
	 *            日志描述
	 * @return 获得的物品
	 */
	@Transaction
	GainAwardVO gainGoods(List<? extends Goods> goodslist, User user, Backage backage, int[] types, String des);
}
