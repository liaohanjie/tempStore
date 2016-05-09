package com.ks.protocol.vo.user;

import java.util.List;

import com.ks.protocol.Message;

/**
 * 用户战魂队伍
 * @author ks
 */
public class UserSoulTeamVO extends Message {

	private static final long serialVersionUID = 1L;
	/**队伍信息*/
	private UserTeamVO userTeam;
	/**战魂信息*/
	private List<UserSoulVO> soulList;
	
	public UserTeamVO getUserTeam() {
		return userTeam;
	}
	public void setUserTeam(UserTeamVO userTeam) {
		this.userTeam = userTeam;
	}
	public List<UserSoulVO> getSoulList() {
		return soulList;
	}
	public void setSoulList(List<UserSoulVO> soulList) {
		this.soulList = soulList;
	}
}
