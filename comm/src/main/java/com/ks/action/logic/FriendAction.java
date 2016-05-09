package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.friend.FightFriendVO;
import com.ks.protocol.vo.friend.FriendGifiVO;
import com.ks.protocol.vo.friend.FriendVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.user.UserCapVO;

public interface FriendAction {
	/**
	 * 获得好友列表
	 * @param userId 用户编号
	 * @return 好友信息
	 */
	List<FriendVO> gainFriends(int userId);
	
	/**
	 * 请求好友
	 * @param userId 用户编号
	 * @param applyUserId 要请求的好友编号
	 */
	void applyFriend(int userId,int applyUserId);
	
	/**
	 * 获得请求好友列表
	 * @param userId 用户编号
	 * @return 请求好友信息
	 */
	List<UserCapVO> gainApplyFriends(int userId);
	
	/**
	 * 处理好友请求
	 * @param userId 用户编号
	 * @param applyUserId 好友请求编号
	 * @param pass 是否通过
	 */
	void execApply(int userId,int applyUserId,boolean pass);
	/**
	 * 删除好友
	 * @param userId 用户编号
	 * @param friendId 好友编号
	 */
	void deleteFriend(int userId,int friendId);
	
	/**
	 * 珍藏好友
	 * @param userId 用户编号
	 * @param friendId 好友编号
	 */
	void collectionFriend(int userId,int friendId);
	/**
	 * 取消珍藏好友
	 * @param userId 用户编号
	 * @param friendId 好友编号
	 */
	void unCollectionFriend(int userId,int friendId);
	/**
	 * 获得战斗好友
	 * @return 战斗好友
	 */
	FightFriendVO gainFightFriend(int userId);
	/**
	 * 增加好友容量
	 * @param userId 用户编号
	 * @return 剩余金钱
	 */
	int addFriendCapacity(int userId);
	
	/**
	 * 获得好友赠品
	 * @param userId 用户编号
	 * @return 好友赠品
	 */
	List<FriendGifiVO> gainFriendGifis(int userId);
	/**
	 * 编辑想要的赠品
	 * @param userId 用户编号
	 * @param want 想要的赠品
	 */
	void updateWant(int userId,List<Integer> want);
	/**
	 * 收取好友赠品
	 * @param userId 用户编号
	 * @param gifiIds 要收取的编号
	 * @return 收取结果
	 */
	GainAwardVO chargeFriendGifi(int userId,List<Integer> gifiIds);
	/**
	 * 赠送礼物
	 * @param userId 用户编号
	 * @param zone 赠送的礼物编号
	 * @param friendIds 赠送的好友名单
	 */
	void handselGift(int userId,int zone,List<Integer> friendIds);
	
	/**
	 * 获得好友信息
	 * @param userId 用户编号
	 * @return 好友信息
	 */
	UserCapVO gainFriendInfo(int userId);
}
