package com.ks.protocol.sub;

public interface FriendCMD {
	
	/**获得好友*/
	short GAIN_FRIENDS = 1;
	/**请求好友*/
	short APPLY_FRIEND = 2;
	/**获得请求好友列表*/
	short GAIN_APPLY_FRIENDS = 3;
	/**处理好友请求*/
	short EXEC_APPLY = 4;
	/**删除好友*/
	short DELETE_FRIEND = 5;
	/**珍藏好友*/
	short COLLECTION_FRIEND = 6;
	/**取消珍藏好友*/
	short UN_COLLECTION_FRIEND = 7;
	/**获得战斗好友*/
	short GAIN_FIGHT_FRIEND = 8;
	/**增加好友仓库*/
	short ADD_FRIEND_CAPACITY = 9;
	/**获得好友赠品*/
	short GAIN_FRIEND_GIFIS = 10;
	/**编辑想要的赠品*/
	short UPDATE_WANT = 11;
	/**收取好友赠品*/
	short CHARGE_FRIEND_GIFI = 12;
	/**赠送礼物*/
	short HANDSEL_GIFI = 13;
	/**赠送礼物*/
	short GAIN_FRIEND_INFO = 14;
}
