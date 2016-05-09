package com.ks.protocol.vo.soul;

import java.util.ArrayList;
import java.util.List;

import com.ks.model.goods.UserGoods;
import com.ks.model.user.UserSoul;
import com.ks.protocol.Message;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.user.UserSoulVO;

public class UserSoulInfoVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**玩家战魂*/
	private UserSoulVO userSoul;
	/**战魂装备*/
	private List<UserGoodsVO> equipments;
	
	public void init(UserSoul soul,List<UserGoods> eqs){
		userSoul = MessageFactory.getMessage(UserSoulVO.class);
		userSoul.init(soul);
		equipments = new ArrayList<UserGoodsVO>();
		for(UserGoods ug : eqs){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(ug);
			equipments.add(vo);
		}
	}

	public UserSoulVO getUserSoul() {
		return userSoul;
	}

	public void setUserSoul(UserSoulVO userSoul) {
		this.userSoul = userSoul;
	}

	public List<UserGoodsVO> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<UserGoodsVO> equipments) {
		this.equipments = equipments;
	}
	
}
