package com.ks.game.handler;

import java.util.List;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.FriendCMD;

/**
 * 好友
 * @author ks
 */
@MainCmd(mainCmd=MainCMD.FRIEND)
public class FriendHandler extends ActionAdapter{
	/**
	 * 获取好友
	 * @param handler
	 */
	@SubCmd(subCmd=FriendCMD.GAIN_FRIENDS)
	public void gainFriends(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), friendAction().gainFriends(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 请求好友
	 * @param handler
	 * @param applyUserId
	 */
	@SubCmd(subCmd=FriendCMD.APPLY_FRIEND,args={"int"})
	public void applyFriend(GameHandler handler,int applyUserId){
		friendAction().applyFriend(handler.getPlayer().getUserId(), applyUserId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 获取请求好友信息
	 * @param handler
	 */
	@SubCmd(subCmd=FriendCMD.GAIN_APPLY_FRIENDS)
	public void gainApplyFriends(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), friendAction().gainApplyFriends(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 处理好友请求
	 * @param handler 
	 * @param applyUserId 请求编号
	 * @param pass 是否通过
	 */
	@SubCmd(subCmd=FriendCMD.EXEC_APPLY,args={"int","boolean"})
	public void execApply(GameHandler handler,int applyUserId,boolean pass){
		friendAction().execApply(handler.getPlayer().getUserId(), applyUserId,pass);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 删除好友
	 * @param handler
	 * @param friendId
	 */
	@SubCmd(subCmd=FriendCMD.DELETE_FRIEND,args={"int"})
	public void deleteFriend(GameHandler handler,int friendId){
		friendAction().deleteFriend(handler.getPlayer().getUserId(), friendId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 珍藏好友
	 * @param handler
	 * @param friendId
	 */
	@SubCmd(subCmd=FriendCMD.COLLECTION_FRIEND,args={"int"})
	public void collectionFriend(GameHandler handler,int friendId){
		friendAction().collectionFriend(handler.getPlayer().getUserId(), friendId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	
	/**
	 * 取消珍藏好友
	 * @param handler
	 * @param friendId
	 */
	@SubCmd(subCmd=FriendCMD.UN_COLLECTION_FRIEND,args={"int"})
	public void unCollectionFriend(GameHandler handler,int friendId){
		friendAction().unCollectionFriend(handler.getPlayer().getUserId(), friendId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 获得战斗好友信息
	 * @param handler
	 */
	@SubCmd(subCmd=FriendCMD.GAIN_FIGHT_FRIEND)
	public void gainFightFriend(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), friendAction().gainFightFriend(handler.getPlayer().getUserId()));
	}
	/**
	 * 增加好友容量
	 * @param handler
	 */
	@SubCmd(subCmd=FriendCMD.ADD_FRIEND_CAPACITY)
	public void addFriendCapacity(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), 
				friendAction().addFriendCapacity(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 获得好友赠品
	 * @param handler
	 * @return 好友赠品
	 */
	@SubCmd(subCmd=FriendCMD.GAIN_FRIEND_GIFIS)
	public void gainFriendGifis(GameHandler handler){
		Application.sendMessage(handler.getChannel(), handler.getHead(), 
				friendAction().gainFriendGifis(handler.getPlayer().getUserId()));
	}
	/**
	 * 编辑想要的赠品
	 * @param handler
	 * @param want 想要的赠品
	 */
	@SubCmd(subCmd=FriendCMD.UPDATE_WANT,args={"int_true"})
	public void updateWant(GameHandler handler,List<Integer> want){
		friendAction().updateWant(handler.getPlayer().getUserId(),want);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 收取好友赠品
	 * @param handler
	 * @param gifiIds 要收取的编号
	 * @return 收取结果
	 */
	@SubCmd(subCmd=FriendCMD.CHARGE_FRIEND_GIFI,args={"int_true"})
	public void chargeFriendGifi(GameHandler handler,List<Integer> gifiIds){
		Application.sendMessage(handler.getChannel(), handler.getHead(), 
				friendAction().chargeFriendGifi(handler.getPlayer().getUserId(),gifiIds));
	}
	/**
	 * 赠送礼物
	 * @param handler
	 * @param zone 赠送的礼物编号
	 * @param friendIds 赠送的好友名单
	 */
	@SubCmd(subCmd=FriendCMD.HANDSEL_GIFI,args={"int","int_true"})
	public void handselGift(GameHandler handler,int zone,List<Integer> friendIds){
		friendAction().handselGift(handler.getPlayer().getUserId(),zone,friendIds);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}
	/**
	 * 获得用户性息
	 * @param handler
	 * @param userId
	 */
	@SubCmd(subCmd=FriendCMD.GAIN_FRIEND_INFO,args={"int"})
	public void gainFriendInfo(GameHandler handler,int userId) {
		Application.sendMessage(handler.getChannel(), handler.getHead(),friendAction().gainFriendInfo(userId));
	}
}
