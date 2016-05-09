package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.UserGoodsCMD;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.SellGoodsVO;
/**
 * 用户物品
 * @author ks
 */
@MainCmd(mainCmd=MainCMD.USER_GOODS)
public class UserGoodsHandler extends ActionAdapter{
	
	/**
	 * 使用装备
	 * @param handler
	 * @param userSoulId 战魂编号
	 * @param gridId 格子编号
	 */
	@SubCmd(subCmd=UserGoodsCMD.USE_EQUIPMENT,args={"long","int"})
	public void useEquipment(GameHandler handler,long userSoulId,int gridId){
		userGoodsAction().useEquipment(handler.getPlayer().getUserId(), userSoulId, gridId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 取消使用装备
	 * @param handler
	 * @param gridId 格子编号
	 */
	@SubCmd(subCmd=UserGoodsCMD.UN_USE_EQUIPMENT,args={"int"})
	public void unUseEquipment(GameHandler handler,int gridId){
		userGoodsAction().unUseEquipment(handler.getPlayer().getUserId(),gridId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 使用道具
	 * @param handler
	 * @param gridId
	 */
	@SubCmd(subCmd=UserGoodsCMD.USE_PROP,args={"int"})
	public void useProp(GameHandler handler,int gridId){
		userGoodsAction().useProp(handler.getPlayer().getUserId(),gridId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 修改战斗道具
	 * @param handler
	 * @param fps 要修改的战斗道具
	 */
	@SubCmd(subCmd=UserGoodsCMD.UPDATE_FIGHT_PROP,args={"fightProp_true"})
	public void updateFightProp(GameHandler handler,List<FightPropVO> fps){
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				userGoodsAction().updateFightProp(handler.getPlayer().getUserId(), fps));
	}
	/**
	 * 卖出物品
	 * @param handler
	 * @param sellGoodses
	 */
	@SubCmd(subCmd=UserGoodsCMD.SELL_GOODS,args={"sellGoods_true"})
	public void sellGoods(GameHandler handler,List<SellGoodsVO> sellGoodses){
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				userGoodsAction().sellGoods(handler.getPlayer().getUserId(), sellGoodses));
	}
	
	/**
	 * 增加道具仓库容量
	 * @param handler
	 */
	@SubCmd(subCmd=UserGoodsCMD.ADD_ITEM_CAPACITY)
	public void addSoulCapacity(GameHandler handler){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userGoodsAction().addItemCapacity(handler.getPlayer().getUserId()));
	}
	/**
	 * 合成物品
	 * @param handler
	 * @param id 合成编号
	 */
	@SubCmd(subCmd=UserGoodsCMD.SYNTHESIS_GOODS,args={"int"})
	public void synthesisGoods(GameHandler handler, int gridId){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userGoodsAction().synthesisGoods(handler.getPlayer().getUserId(),gridId));
	}
	
	/**
	 * 合成物品
	 * @param handler
	 * @param id 合成编号
	 */
	@SubCmd(subCmd=UserGoodsCMD.USER_修理装备,args={"int"})
	public void repairEquipment(GameHandler handler, int gridId){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userGoodsAction().repairEquipment(handler.getPlayer().getUserId(),gridId));
	}
	

	@SubCmd(subCmd=UserGoodsCMD.USER_副本道具)
	public void gainBakProps(GameHandler handler){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userGoodsAction().gainUserBakProps(handler.getPlayer().getUserId()));
	}
	
	@SubCmd(subCmd=UserGoodsCMD.USER_精炼装备,args={"int","int"})
	public void eqRefining(GameHandler handler, int eqGridId,int propId){
		Application.sendMessage(handler.getChannel(), 
				handler.getHead(), 
				userGoodsAction().eqRefining(handler.getPlayer().getUserId(),eqGridId,propId));
	}
}
