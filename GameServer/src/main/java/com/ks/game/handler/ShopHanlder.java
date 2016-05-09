package com.ks.game.handler;

import com.ks.action.logic.MysteryShopAction;
import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.ShopCMD;
import com.ks.rpc.RPCKernel;

@MainCmd(mainCmd=MainCMD.SHOP)
public class ShopHanlder extends ActionAdapter {
	
	
	/**
	 * 购买商品
	 * @param handler
	 * @param activityGiftId
	 * @param defineId
	 */
	@SubCmd(subCmd = ShopCMD.BUY, args = {"int"})
	public void bugProduct(GameHandler handler, int typeId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(), productAction().bugProduct(handler.getPlayer().getUserId(), typeId));
	}
	
	/**
	 * 神秘商店物品显示
	 * @param handler
	 */
	@SubCmd(subCmd = ShopCMD.MYSTERY_SHOP_LIST)
	public void mysteryShopList(GameHandler handler) {
		MysteryShopAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, MysteryShopAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), action.queryAll(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 神秘商店购买记录
	 * @param handler
	 */
	@SubCmd(subCmd = ShopCMD.MYSTER_SHOP_BUY_RECORD)
	public void mysteryShopBuyRecord(GameHandler handler) {
		MysteryShopAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, MysteryShopAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), action.queryCurrentAllRecord(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 刷新神秘商店，返回物品
	 * @param handler
	 */
	@SubCmd(subCmd = ShopCMD.FRESH_MYSTERY_SHOP_LIST, args = {"int"})
	public void freshMysteryShop(GameHandler handler, int typeId) {
		MysteryShopAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, MysteryShopAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), action.fresh(handler.getPlayer().getUserId(), typeId));
	}
	
	/**
	 * 购买神秘商品
	 * @param handler
	 */
	@SubCmd(subCmd = ShopCMD.MYSTER_SHOP_BUY, args = {"int"})
	public void mysteryShopBuy(GameHandler handler, int id) {
		MysteryShopAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, MysteryShopAction.class);
		Application.sendMessage(handler.getChannel(), handler.getHead(), action.mysteryShopBuy(handler.getPlayer().getUserId(), id));
	}
}
