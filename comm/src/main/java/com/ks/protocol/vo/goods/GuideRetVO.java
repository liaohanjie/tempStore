package com.ks.protocol.vo.goods;

import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.protocol.vo.user.UserTeamVO;

public class GuideRetVO extends Message  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**用户战魂*/
	private List<UserSoulVO> userSouls;
	/**魂币*/
	private int currency;
	/**队伍*/
	private List<UserTeamVO> userTeam;
	
	public List<UserTeamVO> getUserTeam() {
		return userTeam;
	}
	public void setUserTeam(List<UserTeamVO> userTeam) {
		this.userTeam = userTeam;
	}
	public void init(List<UserSoulVO> userSouls,int gold,List<UserTeamVO> userTeam){
		this.userSouls = userSouls;
		this.userTeam=userTeam;
		this.currency =gold;
	}
	public List<UserSoulVO> getUserSouls() {
	    return userSouls;
    }
	public void setUserSouls(List<UserSoulVO> userSouls) {
	    this.userSouls = userSouls;
    }
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
}
