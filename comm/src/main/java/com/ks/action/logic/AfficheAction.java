package com.ks.action.logic;

import java.util.List;

import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;
import com.ks.protocol.vo.affiche.AfficheVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.rpc.Async;

public interface AfficheAction {
	
	/**
	 * 获取公告
	 * @param userId 用户编号
	 * @return 所有公告
	 */
	List<AfficheVO> gainAffiche(int userId);
	/**
	 * 删除公告
	 * @param id 编号
	 * @param userId 用户编号
	 */
	void deleteAffiche(int id,int userId);
	/**
	 * 查看公告
	 * @param userId 用户编号
	 * @param id 公告编号
	 * @return 查看结果
	 */
	GainAwardVO getAfficheGoods(int userId,int id);
	
	/**
	 * 查看所用邮件
	 * @param userId 用户编号
	 * @param id 公告编号
	 * @return 查看结果
	 */
	GainAwardVO getAllAfficheGoods(int userId);
	
	/**
	 * 兑换礼品券
	 * @param userId
	 * @param code
	 * @param goods
	 */
	void useGiftCode(int userId,String code,List<Goods> goods);
	/**
	 * 获取公告
	 * @param userId 用户编号
	 * @return 所有公告
	 */
	List<Affiche> bossGainAffiche(int userId);
	/**
	 * boss增加公告
	 * @param a 公告
	 */
	void addAffiche(Affiche a);
	
	void viewAllAffiche(int userId,List<Integer> ids);
	
	@Async
	void cleanAffiche();
	
}
