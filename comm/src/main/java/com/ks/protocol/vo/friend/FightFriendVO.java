package com.ks.protocol.vo.friend;

import java.util.List;

import com.ks.protocol.Message;
/**
 * 战斗好友VO
 * @author ks
 */
public class FightFriendVO extends Message {

	private static final long serialVersionUID = 1L;
	/**好友*/
	private List<FriendVO> firends;
	/**冒险者*/
	private List<FriendVO> adventurers;
	
	public List<FriendVO> getFirends() {
		return firends;
	}
	public void setFirends(List<FriendVO> firends) {
		this.firends = firends;
	}
	public List<FriendVO> getAdventurers() {
		return adventurers;
	}
	public void setAdventurers(List<FriendVO> adventurers) {
		this.adventurers = adventurers;
	}
}
