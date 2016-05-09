package com.ks.model.arena;

import java.io.Serializable;
import java.util.Date;
/**
 * 竞技场
 * @author ks
 */
public class Arena implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**用户编号*/
	private int userId;
	/**玩家名称*/
	private String playerName;
	/**胜利场次*/
	private int win;
	/**竞技场等级*/
	private int level;
	/**战魂等级*/
	private int soulLevel;
	/**状态*/
	private boolean state;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getSoulLevel() {
		return soulLevel;
	}
	public void setSoulLevel(int soulLevel) {
		this.soulLevel = soulLevel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
