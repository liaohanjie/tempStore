package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.FriendAction;
import com.ks.logic.service.FriendService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.friend.FightFriendVO;
import com.ks.protocol.vo.friend.FriendGifiVO;
import com.ks.protocol.vo.friend.FriendVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.user.UserCapVO;

public class FriendActionImpl implements FriendAction {
	
	private static FriendService friendService = ServiceFactory.getService(FriendService.class);
	
	@Override
	public List<FriendVO> gainFriends(int userId) {
		return friendService.gainFriends(userId);
	}

	@Override
	public void applyFriend(int userId, int applyUserId) {
		friendService.applyFriend(userId, applyUserId);
	}

	@Override
	public List<UserCapVO> gainApplyFriends(int userId) {
		return friendService.gainApplyFriends(userId);
	}

	@Override
	public void execApply(int userId, int applyUserId, boolean pass) {
		friendService.execApply(userId, applyUserId, pass);
	}

	@Override
	public void deleteFriend(int userId, int friendId) {
		friendService.deleteFriend(userId, friendId);
	}

	@Override
	public void collectionFriend(int userId, int friendId) {
		friendService.collectionFriend(userId, friendId);
	}

	@Override
	public void unCollectionFriend(int userId, int friendId) {
		friendService.unCollectionFriend(userId, friendId);
	}

	@Override
	public FightFriendVO gainFightFriend(int userId) {
		return friendService.gainFightFriend(userId);
	}

	@Override
	public int addFriendCapacity(int userId) {
		return friendService.addFriendCapacity(userId);
	}

	@Override
	public List<FriendGifiVO> gainFriendGifis(int userId) {
		return friendService.gainFriendGifis(userId);
	}

	@Override
	public void updateWant(int userId, List<Integer> want) {
		friendService.updateWant(userId, want);
	}

	@Override
	public GainAwardVO chargeFriendGifi(int userId, List<Integer> gifiIds) {
		return friendService.chargeFriendGift(userId, gifiIds);
	}

	@Override
	public void handselGift(int userId, int zone, List<Integer> friendIds) {
		friendService.handselGift(userId, zone, friendIds);
	}

	@Override
	public UserCapVO gainFriendInfo(int userId) {
		return friendService.gainFriendInfo(userId);
	}

}
