package com.ks.action.world;

import java.util.List;

import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;

public interface WorldGiftCodeAction {
	
	/**
	 * 发送礼品卡奖励
	 * @param userId
	 * @param code
	 * @param goods
	 */
	public void giveCodeGift(int userId,String code,List<Goods> goods);
	
	/**
	 * 获取所有邮件
	 * @param userId
	 * @return
	 */
	public List<Affiche> gainAffiche(int userId);
}
