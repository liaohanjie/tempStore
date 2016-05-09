package com.ks.protocol.vo.friend;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserCapVO;
/**
 * 好友
 * @author ks
 */
public class FriendVO extends Message {

	private static final long serialVersionUID = 1L;
	/**好友状态*/
	private int status;
	/**好友队长*/
	private UserCapVO cap;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserCapVO getCap() {
		return cap;
	}
	public void setCap(UserCapVO cap) {
		this.cap = cap;
	}
}
