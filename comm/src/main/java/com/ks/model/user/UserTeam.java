package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 用户队伍
 * @author ks
 */
public class UserTeam implements Serializable {

	private static final long serialVersionUID = 1L;
	/**队伍数量*/
	public static final byte TEAM_SIZE = 5;
	/**竞技场队伍*/
	public static final byte ARENA_TEAM = 5;
	
	/**用户编号*/
	private int userId;
	/**队伍编号*/
	private byte teamId;
	/**队长*/
	private byte cap;
	/**队伍位置*/
	private List<Long> pos;
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
	public byte getTeamId() {
		return teamId;
	}
	public void setTeamId(byte teamId) {
		this.teamId = teamId;
	}
	public byte getCap() {
		return cap;
	}
	public void setCap(byte cap) {
		this.cap = cap;
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
	public List<Long> getPos() {
		return pos;
	}
	public void setPos(List<Long> pos) {
		this.pos = pos;
	}
	@Override
	public String toString() {
		return "UserTeam [userId=" + userId + ", teamId=" + teamId + ", cap="
				+ cap + ", pos=" + pos + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
