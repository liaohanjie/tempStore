package com.ks.protocol.vo.swaparena;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserSoulTeamVO;

/**
 * 交换排名竞技赛基础信息VO
 * @author hanjie.l
 *
 */
public class SwapArenaBaseVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3020012169573377445L;

	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 玩家等级
	 */
	private int level;
	
	/**
	 * 玩家名
	 */
	private String userName;
	
	/**
	 * 排名
	 */
	private int rank;
	
	/**
	 * 队伍信息
	 */
	private UserSoulTeamVO teamVO;
	
	
	public void init(int userId, int rank,int level, String userName, UserSoulTeamVO teamVO){
		this.userId = userId;
		this.rank = rank;
		this.level = level;
		this.userName = userName;
		this.teamVO = teamVO;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public UserSoulTeamVO getTeamVO() {
		return teamVO;
	}

	public void setTeamVO(UserSoulTeamVO teamVO) {
		this.teamVO = teamVO;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
