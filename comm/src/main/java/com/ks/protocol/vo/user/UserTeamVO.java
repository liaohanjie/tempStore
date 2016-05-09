package com.ks.protocol.vo.user;

import java.util.List;

import com.ks.model.user.UserTeam;
import com.ks.protocol.Message;
/**
 * 用户队伍
 * @author ks
 */
public class UserTeamVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**队伍编号*/
	private byte teamId;
	/**队长*/
	private byte cap;
	/**队伍位置*/
	private List<Long> pos;
	
	public void init(UserTeam o){
		this.teamId = o.getTeamId();
		this.cap = o.getCap();
		this.pos = o.getPos();

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

	public List<Long> getPos() {
		return pos;
	}

	public void setPos(List<Long> pos) {
		this.pos = pos;
	}
	
}
