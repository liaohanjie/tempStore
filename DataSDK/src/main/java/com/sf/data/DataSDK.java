package com.sf.data;

import java.util.Date;

import com.ks.util.HttpUtil;
import com.ks.util.JSONUtil;
import com.sf.data.domain.LoginInfo;
import com.sf.data.domain.LoginOutInfo;
import com.sf.data.domain.PayOrderInfo;
import com.sf.data.domain.PropChange;
import com.sf.data.domain.ServerOnline;
import com.sf.data.domain.UserLevelUp;
import com.sf.data.domain.UserRegister;

/**
 * 数据统计SDK
 * @author living.li
 * @date 2014年7月9日
 */
public class DataSDK {

	private static String  baseUrl="";
	private static String serverId="";
	private static int gameId=0;
	private static boolean isOpen=false;
	
	public static void setConfig(String url,String serverId,int gameId,boolean open) {
		DataSDK.baseUrl = url;
		DataSDK.serverId=serverId;
		DataSDK.gameId=gameId;
		isOpen=open;
	}
	/**
	 * 用户注册
	 * @param regist
	 */
	public static void userRegist(UserRegister reg){
		if(!isOpen){
			return;
		}
		reg.setGameId(gameId);
		reg.setServerId(serverId);
		String value="data="+JSONUtil.toJson(reg);
		String url=baseUrl+"/userReg.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 用户升级
	 * @param regist
	 */
	public static void userLevelUp(UserLevelUp up){
		if(!isOpen){
			return;
		}
		up.setGameId(gameId);
		up.setServerId(serverId);
		String value="data="+JSONUtil.toJson(up);
		String url=baseUrl+"/userlevelup.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 登入
	 * @param partner 
	 * @param roleId
	 * @param roleName
	 * @param loginTime
	 */
	public static void login(LoginInfo info){
		if(!isOpen){
			return;
		}
		/*
		LoginInfo info=LoginInfo.create(gameId,serverId, roleId,
				loginTime,ip,pixel,model,net,operator,system);*/
		info.setGameId(gameId);
		info.setServerId(serverId);
		String value="data="+JSONUtil.toJson(info);
		String url=baseUrl+"/login.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 退出
	 * @param partner
	 * @param roleId
	 * @param roleName
	 * @param loginTime
	 * @param loginOutTime
	 */
	public static void loginOut(LoginOutInfo info){
		if(!isOpen){
			return;
		}
		info.setGameId(gameId);
		info.setServerId(serverId);
		//LoginOutInfo info=LoginOutInfo.create(gameId,serverId, roleId, roleName, loginTime, loginOutTime);
		String value="data="+JSONUtil.toJson(info);
		String url=baseUrl+"/loginOut.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 购买道具
	 * @param partner
	 * @param roleId
	 * @param username
	 * @param propId
	 * @param propType
	 * @param num
	 * @param optType
	 */
	public static void buyProp(int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
		if(!isOpen){
			return;
		}
		PropChange prop=PropChange.create(serverId, partner, roleId, username, propId, propType, num, optType,time);
		String value="data="+JSONUtil.toJson(prop);
		String url=baseUrl+"/buyProp.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 得到道具
	 * @param partner
	 * @param roleId
	 * @param username
	 * @param propId
	 * @param propType
	 * @param num
	 * @param optType
	 */
	public static void addProp(int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
		if(!isOpen){
			return;
		}
		PropChange prop=PropChange.create(username, partner, roleId, username, propId, propType, num, optType,time);
		String value="data="+JSONUtil.toJson(prop);
		String url=baseUrl+"/addProp.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 使用道具
	 * @param partner
	 * @param roleId
	 * @param username
	 * @param propId
	 * @param propType
	 * @param num
	 * @param optType
	 */
	public static void useProp(int partner,int roleId,String username,int propId, int propType,int num,int optType,Date time){
		if(!isOpen){
			return;
		}
		PropChange prop=PropChange.create(username, partner, roleId, username, propId, propType, num, optType,time);
		String value="data="+JSONUtil.toJson(prop);
		String url=baseUrl+"/useProp.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 用户在线统计
	 * @param gameId
	 * @param serverId
	 * @param num
	 * @param time
	 */
	public static void serverOnline(int gameId,String serverId,int num,Date time){
		if(!isOpen){
			return;
		}
		if (serverId == null || serverId.trim().equals("")) {
			serverId = DataSDK.serverId;
		}
		
		ServerOnline entity = ServerOnline.create(gameId, serverId, num, time);
		String value = "data=" + JSONUtil.toJson(entity);
		String url = baseUrl+"/online.do";
		HttpUtil.post(url, value, null);
	}
	
	/**
	 * 添加订单
	 * @param gameId
	 * @param orderNo
	 * @param amount
	 * @param gameCoin
	 * @param roleId
	 * @param username
	 */
	public static void addOrder(PayOrderInfo order){
		if(!isOpen){
			return;
		}
		
		if (order.getServerId() == null || order.getServerId().trim().equals("")) {
			order.setServerId(DataSDK.serverId);
		}
		
//		PayOrderInfo entity = new PayOrderInfo();
//		entity.setServerId(serverId);
//		entity.setGameId(gameId);
//		entity.setOrderNo(orderNo);
//		entity.setMoney(amount);
//		entity.setGameCoin(gameCoin);
//		entity.setUsername(username);
//		entity.setRoleId(roleId);
//		entity.setDealTime(new Date());
//		entity.setCreateTime(new Date());
		String value = "data=" + JSONUtil.toJson(order);
		String url = baseUrl+"/order.do";
		HttpUtil.post(url, value, null);
	}
	
	public static  void setIsOpen(boolean open){
		isOpen=open;
	}
	
	public static void main(String[] args) throws InterruptedException {
		DataSDK.setConfig("http://192.168.100.13:8001/GameDataHanlder", "gs_soul_account_1", 1, true);
		/*
		LoginOutInfo entity = new LoginOutInfo();
		entity.setGrade(1);
		entity.setPartner(110);
		entity.setRoleId(11);
		entity.setLoginOutTime(new Date());
		entity.setGrade(1);
		DataSDK.loginOut(entity);*/
		
		for(int i = 0; i<3; i++){
			DataSDK.serverOnline(1, "local_server_1", 10 + i *10, new Date());
			Thread.sleep(1000);
		}
		
		System.out.println("finish");
	}
}
