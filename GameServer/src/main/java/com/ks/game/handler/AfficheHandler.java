package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.AfficheCMD;

@MainCmd(mainCmd = MainCMD.AFFICHE)
public class AfficheHandler extends ActionAdapter{
	/**
	 * 获取公告
	 * @param handler
	 */
	@SubCmd(subCmd = AfficheCMD.GAIN_AFFICHE)
	public void gainAffiche(GameHandler handler) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				afficheAction().gainAffiche(handler.getPlayer().getUserId()));
	}
	/**
	 * 删除公告
	 * @param handler
	 * @param id 公告编号
	 */
	@SubCmd(subCmd=AfficheCMD.DELETE_AFFICHE,args={"int"})
	public void deleteAffiche(GameHandler handler,int id){
		afficheAction().deleteAffiche(id, handler.getPlayer().getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 *收取
	 * @param handler
	 * @param id 公告编号
	 */
	@SubCmd(subCmd=AfficheCMD.VIEW_收取物品,args={"int"})
	public void getAfficheGoods(GameHandler handler,int id){
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				afficheAction().getAfficheGoods(handler.getPlayer().getUserId(),id));
	}
	
	/**
	 *收一件收取
	 * @param handler
	 * @param id 公告编号
	 */
	@SubCmd(subCmd=AfficheCMD.AFFICHE_收取所有物品)
	public void getAllAfficheGoods(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(),
				afficheAction().getAllAfficheGoods(handler.getPlayer().getUserId()));
	}
	/**
	 * 查看邮件
	 * @param handler
	 * @param id 公告编号
	 */
	@SubCmd(subCmd=AfficheCMD.AFFICHE_查看邮件,args={"int_true"})
	public void viewAllAffiche(GameHandler handler,List<Integer> ids){
		afficheAction().viewAllAffiche(handler.getPlayer().getUserId(),ids);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
}
